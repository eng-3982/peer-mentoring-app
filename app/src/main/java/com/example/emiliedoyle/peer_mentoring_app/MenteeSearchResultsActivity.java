package com.example.emiliedoyle.peer_mentoring_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
// import necessary items for design, menu and connection between views

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
/*import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;*/

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

    String[] name = new String[50];

    // declare button
    Button HomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mentee_search_results); //needed for listView

        queue = Volley.newRequestQueue(this);
        //url = "https://pma.piconepress.com/data";
        getDBItems();

        HomeButton = (Button) findViewById(R.id.HomeButton);
        HomeButton.setOnClickListener(this);

        getDBItems();

        // DEMO THAT WORKS! PRAISE THE LORD! http://windrealm.org/tutorials/android/android-listview.php
        /*mainListView=(ListView) findViewById(R.id.mainListView);
        String[] planets= new String[]{"Claire A. Durand","Rachel K. King","Daniel J. Douglas","Emilie C. Doyle"};
        ArrayList<String> planetList= new ArrayList<String>();
        planetList.addAll(Arrays.asList(planets));
        listAdapter= new ArrayAdapter<String>(this, R.layout.simplerow, planetList);//listAdapter.add("Ceres");
        mainListView.setAdapter(listAdapter);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent newActivity = new Intent(MenteeSearchResultsActivity.this, MentorProfileActivity.class);
                        startActivity(newActivity);
                        break;
                }
            }

            @SuppressWarnings("unused")
            public void onClick(View v) {
            }

            ;
        });*/



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

    public void getDBItems() {
        //create new ListView to display data

        final TextView mTextView = (TextView) findViewById(R.id.stuff);

        //define our url
        Uri.Builder uri = new Uri.Builder();
        uri.scheme("https");
        uri.authority("pma.piconepress.com");
        uri.path("data/");
        final String url = uri.build().toString();

        //create new SP object to access callback data
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences("JSON", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences.edit();



        VolleyRequest parse = new VolleyRequest();
        /*JsonRequest things = parse.getJSON(new VolleyRequest.VolleyCallback() {
               @Override
               public void onSuccess(JSONObject result) {
                   if (result != null) {
                       mTextView.setText(result.toString());
                       editor.putString("jsonData", result.toString());
                       Log.i("SearchResultActivity", "QQQQR" + result.toString());
                       editor.commit();
                   } else {
                       editor.putString("jsonData", null);
                       Log.i("SearchResultActivity", "QQQQ" + "null");
                       editor.commit();
                   }
               }
           },
                url, "Raquel", "Sloths");

        String[] response = null;
        sharedpreferences = getSharedPreferences("JSON", MODE_PRIVATE);
        String stuff = (sharedpreferences.getString("jsonData", ""));*/

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.i("VolleyRequest", "PPPP"+ response.toString());
                        mTextView.setText(response.toString());

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
                //headers.put("Content-Type: application/json");
                headers.put("Authorization", auth);
                headers.put("username", "Raquel");
                headers.put("password", "Sloths");
                headers.put("attribute", "matches");
                return headers;
            }

        };

        //something I needed to do so that there wouldn't be time-outs
        //we may not need this? who on earth knows.
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                27000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        /*try {
            if(stuff != null)
            {
                JSONObject jsonData = new JSONObject(stuff);
                response = parse.parseJSON(jsonData, "name");
                Log.i("SearchResultActivity", "RRRR");

            }
            else {
                response[0] = "null";
                Log.i("SearchResultActivity", "SSSS");
            }

        }
        catch (JSONException e)
        {
            Log.i("SearchResultActivity", "JJJJ" + e.toString());

        }*/
        queue.add(jsonRequest);
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