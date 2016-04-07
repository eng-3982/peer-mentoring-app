package com.example.emiliedoyle.peer_mentoring_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import android.database.DataSetObserver;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
//package com.realco.claire.practice;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MenteeSearchResultsActivity extends AppCompatActivity implements View.OnClickListener {


    // Instantiate the RequestQueue.
    private RequestQueue queue;

    private ListView mainListView;
    public TextView resultsView;
    private ArrayAdapter<String> listAdapter;
    //SharedPreferences sharedpreferences;
    public String listClick;
    String[] name = new String[50];

    // declare button
    Button HomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //resultsView = (TextView) findViewById(R.id.stuff);
        setContentView(R.layout.activity_mentee_search_results); //needed for listView

        queue = Volley.newRequestQueue(this);
        //url = "https://pma.piconepress.com/data";
        //getDBItems();

        HomeButton = (Button) findViewById(R.id.HomeButton);
        HomeButton.setOnClickListener(this);

        Bundle majorBundle=getIntent().getExtras();
        //String selectedMajor= majorBundle.getString("major");
        //Log.i("afterSelectedMajor",majorBundle.getString("major"));
        //Log.i("passed major", selectedMajor);

        String items = getDBItems();
        Log.i("shared",items);
        //resultsView.setText(items);


        final String[] planets = items.replace("{", "").replace(":", " ").replace("name", "").replace("[", "").replace("]", "").replace("\"", "").replace("}", "").split(",");
        if (planets.length < 1)
            planets[0] = "No Matches Found";
        else {
            for (int i = 0; i < planets.length; i++) {
                planets[i] = planets[i].substring(0, 1).toUpperCase() + planets[i].substring(1);

            }
        }

        Log.i("XXXX", Arrays.toString(planets));

        // DEMO THAT WORKS! PRAISE THE LORD! http://windrealm.org/tutorials/android/android-listview.php
        mainListView=(ListView) findViewById(R.id.mainListView);
        //final String[] planets= new String[]{"Claire A. Durand","Rachel K. King","Daniel J. Douglas","Emilie C. Doyle"};
        ArrayList<String> planetList= new ArrayList<String>();
        planetList.addAll(Arrays.asList(planets));
        listAdapter= new ArrayAdapter<String>(this, R.layout.simplerow, planetList);//listAdapter.add("Ceres");
        mainListView.setAdapter(listAdapter);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    default:
                        Intent newActivity = new Intent(MenteeSearchResultsActivity.this, MentorProfileActivity.class);
                        //attempt to pass the username or name of the person that was clicked to the mentor profile activity
                        Bundle bundle= new Bundle();
                        bundle.putString("name", planets[position]);
                        Log.i("XXXX", planets[position].toString());
                        //Log.i("before", bundle.getString("name"));
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mentee_search_results, menu);
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

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, null,
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

    private void HomeButtonClick() {
        startActivity(new Intent(MenteeSearchResultsActivity.this, StudentMainActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.HomeButton:
                HomeButtonClick();
                break;
        }
    }
}