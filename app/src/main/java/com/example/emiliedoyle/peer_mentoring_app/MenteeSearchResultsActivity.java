package com.example.emiliedoyle.peer_mentoring_app;

import android.net.Uri;
// import necessary items for design, menu and connection between views

import android.database.DataSetObserver;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MenteeSearchResultsActivity extends AppCompatActivity implements View.OnClickListener {


    // Instantiate the RequestQueue.
    private RequestQueue queue;
    private String url;

    String[] user;
    String[] name = new String[50];
    String[] value;
    String[] nameAndValue;

    //declare listview
    ListView listView;

    EditText txtInput;
    // declare button
    Button HomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //HomeButton = (Button) findViewById(R.id.HomeButton);
        //HomeButton.setOnClickListener(this);

        setContentView(R.layout.activity_mentee_search_results);
        queue = Volley.newRequestQueue(this);
        //url = "https://pma.piconepress.com/data";
        //getDBItems();
        //getDBandAuthenticate();
        HomeButton = (Button) findViewById(R.id.HomeButton);
        HomeButton.setOnClickListener(this);
/*
        //create ListView where find results will be displayed
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_mentee_search_results,mobileArray );
        ListView listView= (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);*/

        //ListView resource: http://androidexample.com/Create_A_Simple_Listview_-_Android_Example/index.php?view=article_discription&aid=65&aaid=90
        //get list view from XML file
        /*listView = (ListView) findViewById(R.id.listview);

        //define array values to show in ListView, fill with request results that hae been parsed
        String[] values = new String[]{"Android List View", "Adapter Implementation", "Simple List View in Android",
                "Create List View in Android", "Android Example", "List View soURCE cODE", "List View array adapter",
                "Android Example List View"};

        //define new adapter. First Parameter: Context, Second Parameter: Layout for the row, Third Parameter: ID of the
        //TextView to which the data is written(ex android.R.id.text1), Fouth Parameter:the array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,
                (TextView) findViewById(R.id.name), values);
*/



        getDBItems();
        //final TextView mTextView = (TextView) findViewById(R.id.searchResults);
        //mTextView.setText("Response is: ");
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



        // Request a json response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest (Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the response string (items of the DB)
                        //mTextView.setText("Response is: " + response);
                        try {
                            //String result = response.getString("name"); //must substitute in a name
                            //mTextView.setText("Response is: " + result);
                            Iterator<?> keys= response.keys();
                            int i=0;

                            while(keys.hasNext()) {

                                String key = (String) keys.next();

                                if (response.get(key) instanceof JSONObject) {
                                    JSONObject person = response.getJSONObject(key);
                                    name[i] = person.getString("name");
                                    i++;
                                }
                            }
                            mTextView.setText("ll" + name[0] + name[1] + name[2]);
                        }


                        catch (JSONException e) {
                            mTextView.setText("THAT DON'T WORK");

                        }

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
