package com.example.emiliedoyle.peer_mentoring_app;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MenteeSearchByAttributeActivity extends AppCompatActivity implements View.OnClickListener {

    Button SearchButton;
    private RequestQueue queue;

    public static final String KEY_MAJOR="major";
    public String major;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_search_by_attribute);
        SearchButton=(Button) findViewById(R.id.SearchButton);
        SearchButton.setOnClickListener(this);

        //create request queue
        queue = Volley.newRequestQueue(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mentee_search_by_attribute, menu);
        return true;
    }

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

    private void SearchButtonClick(){
        startActivity(new Intent(MenteeSearchByAttributeActivity.this, MenteeSearchResultsActivity.class));
    }
    public void getDBMatches() {

        //create new ListView to display data
        final TextView mTextView = (TextView) findViewById(R.id.stuff);

        //define our url
        Uri.Builder uri = new Uri.Builder();
        uri.scheme("https");
        uri.authority("pma.piconepress.com");
        uri.path("data/");
        final String url = uri.build().toString();



        // Request a json response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest (Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the response string (items of the DB)
                        //mTextView.setText("Response is: " + response);
                        /*try {

                            //String result = response.getString("name"); //must substitute in a name
                            //mTextView.setText("Response is: " + result);
                            Iterator<?> keys= response.keys();
                            int i=0;

                            while(keys.hasNext()) {

                                String key = (String) keys.next();

                                if (response.get(key) instanceof JSONObject) {
                                    JSONObject person = response.getJSONObject(key);
                                    //name[i] = person.getString("name");
                                    i++;
                                }
                            }

                        }
                        //if there is a problem with the JSON exchance
                        catch (JSONException e) {
                            mTextView.setText("THAT DON'T WORK");

                        }*/

                    }
                }, new Response.ErrorListener() {
            @Override
            //if there is an error, print it!
            public void onErrorResponse(VolleyError error) {
                mTextView.setText(error.toString());
            }
        }) {
            @Override
            //OH LOOK AT ME, I'M VOLLEY AND MY HEADER IS WRONG
            //modifies the header to contain the encoded username and password info. authenticates
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String creds = "eng-3982:isipjuice";
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                //headers.put("Content-Type: application/json");
                headers.put("Authorization", auth);
                headers.put(KEY_MAJOR, major);
                return headers;
            }

        };
        // Add the request to the RequestQueue.
        queue.add(jsonRequest);

        //something I needed to do so that there wouldn't be time-outs
        //we may not need this? who on earth knows.
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                27000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox:
                if (checked)
                    major = "Engineering";
                Toast.makeText(MenteeSearchByAttributeActivity.this, major , Toast.LENGTH_LONG).show();
                break;
            case R.id.checkBox2:
                if (checked)
                    major = "Business";
                break;
            case R.id.checkBox3:
                if (checked)
                    major = "Liberal Arts";
                break;
            case R.id.checkBox4:
                if (checked)
                    major = "Fine Arts";
                break;
            case R.id.checkBox5:
                if (checked)
                    major = "Science";
                break;
            case R.id.checkBox6:
                if (checked)
                    major = "Other";
                break;
        }
    }

    @Override
    public void onClick(View v){

        switch(v.getId()){
            case R.id.SearchButton:
                //onCheckboxClicked();
                getDBMatches();
                SearchButtonClick();
                break;
        }
    }
}
