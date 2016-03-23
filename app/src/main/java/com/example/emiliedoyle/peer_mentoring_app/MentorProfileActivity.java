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

        String items = getUserProfile(selectedName);
        final String[] info = items.replace("{", "").replace("name", "").replace("[", "").replace("]", "").replace("\"", "").replace("}", "").split(",");

        Log.i("QQQQ", Arrays.toString(info));

        TextView nameText = (TextView) findViewById(R.id.mentorName);
        TextView majorText = (TextView) findViewById(R.id.mentorName);

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
                            SharedPreferences ProfPref = getSharedPreferences("Profile", 0);
                            SharedPreferences.Editor editor = ProfPref.edit();
                            editor.putString("ProfileResponse", response.toString());
                            editor.commit();
                            Log.i("stuff", response.toString());
                        }

                        //mTextView.setText("it's null yo");
                        //String[]names= parse.parseJSON(response,"name");
                        //Log.i("VR string", names[0]);
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


            SharedPreferences ProfPref = getSharedPreferences("Profile", 0);
            final String resp = ProfPref.getString("ProfileResponse", "missing");
            Log.i("HALP", resp);

        queue.add(jsonRequest);
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
