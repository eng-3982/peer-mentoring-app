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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.widget.ListView;

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

// declare class, need to implement on click listener in order to switch views/activities
public class MenteeYourConnectionsActivity extends AppCompatActivity implements View.OnClickListener {

    // declare button to return to main menu
    Button HomeButton;

    private ListView mainListView;
    private ArrayAdapter<String> listAdapter;
    public RequestQueue queue;

    // standard onCreate,with added linking of buttons from xml and java
    // and setting the on click listener to change activities/views
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_your_connections);
        HomeButton=(Button)findViewById(R.id.HomeButton);
        HomeButton.setOnClickListener(this);
        String items = getDBItems();
        // DEMO THAT WORKS! PRAISE THE LORD! http://windrealm.org/tutorials/android/android-listview.php
        final String[] planets = items.replace("{", "").replace(":", " ").replace("name", "").replace("[", "").replace("]", "").replace("\"", "").replace("}", "").split(",");
        if (planets.length < 1)
            planets[0] = "No Connections Yet";
        else {
            for (int i = 0; i < planets.length; i++) {
                planets[i] = planets[i].substring(0, 1).toUpperCase() + planets[i].substring(1);

            }
        }

        mainListView=(ListView) findViewById(R.id.mainListView);
        ArrayList<String> planetList= new ArrayList<String>();
        planetList.addAll(Arrays.asList(planets));
        listAdapter= new ArrayAdapter<String>(this, R.layout.simplerow, planetList);
        //listAdapter.add("Ceres");
        mainListView.setAdapter(listAdapter);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent newActivity = new Intent(MenteeYourConnectionsActivity.this, MentorProfileActivity.class);
                        Bundle bundle= new Bundle();
                        bundle.putString("name", planets[position]);
                        Log.i("before", bundle.getString("name"));
                        newActivity.putExtras(bundle);
                        startActivity(newActivity);
                        break;
                }
            }

            @SuppressWarnings("unused")
            public void onClick(View v) {
            }

            ;
        });
    }
    public String getDBItems() {

        Uri.Builder uri = new Uri.Builder();
        uri.scheme("https");
        uri.authority("pma.piconepress.com");
        //uri.path("query/search/");
        uri.path("query/match/");
        final String url = uri.build().toString();
        SharedPreferences mSettings = getSharedPreferences("Login", 0);
        final String username = mSettings.getString("Username", "missing");
        final String password = mSettings.getString("Password", "missing");

        JSONObject json = new JSONObject();
        try {
            json.put("name", username);
            json.put("something", "something");
        }
        catch(JSONException e)
        {

        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        if(response != null) {
                            SharedPreferences matches = getSharedPreferences("Matches", 0);
                            SharedPreferences.Editor editor = matches.edit();
                            editor.clear();
                            editor.putString("mat", response.toString());
                            Log.i("shared", response.toString());
                            editor.commit();

                        }
                        else
                            Log.i("BLAH", "null ");
                    }
                }, new Response.ErrorListener() {
            @Override
            //if there is an error, print it!
            public void onErrorResponse(VolleyError error) {
                Log.i("BLAH", error.toString());

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
                return headers;
            }

        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                27000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        SharedPreferences matches = getSharedPreferences("Matches", 0);
        String resp = matches.getString("mat", "missing");
        Log.i("shared", resp.toString());
        queue.add(jsonRequest);
        return resp;
    }
    // standard
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mentee_your_connections, menu);
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

    // specifically for when button11 is clicked to change from the your connections
    // activity to the main menu activity
    private void HomeButtonClick(){
        startActivity(new Intent(MenteeYourConnectionsActivity.this, StudentMainActivity.class));}

    // when click occurs, uses switch case which takes the ID to determine
    // action to complete in response. ADD DEFAULT.
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.HomeButton:
                HomeButtonClick();
                break;
        }
    }
}

