package com.example.emiliedoyle.peer_mentoring_app;

// import necessary items for design, menu and connection between views
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// declare class, need to implement on click listener in order to switch views/activities
public class MentorProfileActivity extends AppCompatActivity implements View.OnClickListener {

    // declare button 06
    Button requestMentorButton;
    public RequestQueue queue;

    // standard onCreate, with added button linking and setting onClickListener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_profile);
        requestMentorButton=(Button)findViewById(R.id.requestMentor);
        requestMentorButton.setOnClickListener(this);
        //get the bundle from the previous activity that should hold the name of the person they were trying to select
        Bundle bundle=getIntent().getExtras();
        String selectedName= bundle.getString("name");
        selectedName = selectedName.replaceAll("[^a-zA-Z]", "");
        Log.i("QQQQ", selectedName);

        String wut = getUserProfile(selectedName);
        SharedPreferences ProfPref = getSharedPreferences("testing", 0);
        String items = ProfPref.getString("test", "missing");

        //THIS IS WHERE OUR PROBLEM IS
        Log.i("QQQQ prob", wut);
        final String[] info = wut.replace("{", "").replace("name", "").replace("[", "").replace("]", "").replace("\"", "").replace("}", "").split(",");

        Log.i("QQQQ", Arrays.toString(info));

        //string to parse into and then set text.
        String firstname="a";
        String lastname="b";
        String major="c";
        String gender="d";
        String year="e";

        for(int i=0; i<info.length; i++){
            String delims="[:]";
            String[] tokens=info[i].split(delims);
            switch( tokens[0]){
                case "first":
                    firstname= tokens[1].substring(0,1).toUpperCase()+ tokens[1].substring(1);
                    break;
                case "last":
                    lastname= tokens[1].substring(0,1).toUpperCase()+ tokens[1].substring(1);
                    break;
                case "year":
                    year= tokens[1].substring(0,1).toUpperCase()+ tokens[1].substring(1);
                    break;
                case "major":
                    major= tokens[1].substring(0,1).toUpperCase()+ tokens[1].substring(1);
                    break;
                case "gender":
                    gender= tokens[1].toUpperCase();
                    break;
            }

        }
        //associate the textviews in java with the ones in xml
        TextView nameText = (TextView) findViewById(R.id.mentorName);
        TextView majorText = (TextView) findViewById(R.id.mentorMajor);
        TextView genderText= (TextView) findViewById(R.id.mentorGender);
        TextView yearText= (TextView) findViewById(R.id.mentorClassYear);

        //set the text views to contain the contents from parsing
        nameText.setText(firstname + " "+ lastname);
        majorText.setText("Major: "+major);
        genderText.setText("Gender: "+gender);
        yearText.setText("Class Year: "+year);

        //Log.i("passed" , selectedName);

        //need to now get the results from the database for that name and parse and then display
    }
    public String getUserProfile(String name)
        {
        //define our url
        Uri.Builder uri = new Uri.Builder();
        uri.scheme("https");
        uri.authority("pma.piconepress.com");
        uri.path("data/" + name.substring(0, 1).toLowerCase() + name.substring(1) + "/");

        //uri.path("query/match/");
        final String url = uri.build().toString();
            Log.i("URLLLL", url);
        SharedPreferences mSettings = getSharedPreferences("Login", 0);
        final String username = mSettings.getString("Username", "missing");
        final String password = mSettings.getString("Password", "missing");

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        if(response != null) {
                            SharedPreferences test = getSharedPreferences("testing", 0);
                            SharedPreferences.Editor editor = test.edit();
                            editor.clear();
                            editor.putString("test", response.toString());
                            editor.commit();
                            Log.i("BBB", response.toString());
                        }
                        else
                            Log.i("BBB", "null");
                    }
                }, new Response.ErrorListener() {
            @Override
            //if there is an error, print it!
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            //modifies the header to contain the encoded username and password info. authenticates
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String creds = "eng-3982:isipjuice";
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", auth);
                headers.put("username", username);
                headers.put("password", password);
                /*
                headers.put("attribute", "matches");*/
                return headers;
            }

        };

        //something I needed to do so that there wouldn't be time-outs
        //we may not need this? who on earth knows.
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                27000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


            SharedPreferences test = getSharedPreferences("testing", 0);
            String resp = test.getString("test", "missing");
            Log.i("HALP", resp);

        queue.add(jsonRequest);
            Log.i("weird", jsonRequest.toString());

            resp = test.getString("test", "missing");
            Log.i("HALP2", resp);

            return resp;

    }
    // standard
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mentor_profile, menu);
        return true;
    }

    // standard
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // specifically for when button06 is clicked to change from mentor profile activity
    // to the mentee request mentor activity
    private void requestMentorButtonClick(){
        startActivity(new Intent(MentorProfileActivity.this,MenteeRequestMentorNotificationActivity.class));}

    // uses switch case to determine which method to implement when click occurs
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.requestMentor:
                requestMentorButtonClick();
                break;
        }
    }


}
