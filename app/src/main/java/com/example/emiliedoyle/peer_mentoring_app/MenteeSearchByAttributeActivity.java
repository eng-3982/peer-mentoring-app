package com.example.emiliedoyle.peer_mentoring_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MenteeSearchByAttributeActivity extends AppCompatActivity implements View.OnClickListener {

    Button SearchButton;
    private RequestQueue queue;
    private ListView mainListView;
    public TextView resultsView;
    private ArrayAdapter<String> listAdapter;
    String Major="Nothing was checked :(";

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

        mainListView=(ListView) findViewById(R.id.mainListView);
        final String[] majors= new String[]{"Engineering","Science", "Fine Art","Art", "Acting","Liberal Arts","Other","Business","Sports"};
        ArrayList<String> majorList= new ArrayList<String>();
        majorList.addAll(Arrays.asList(majors));
        listAdapter= new ArrayAdapter<String>(this, R.layout.checkboxrow, majorList);//listAdapter.add("Ceres");
        mainListView.setAdapter(listAdapter);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //somehow set majors to be accessible to be used in SearchButtonClic
                SharedPreferences sharedpref=getSharedPreferences("Attribute",0);
                SharedPreferences.Editor editor= sharedpref.edit();
                editor.putString("Major", majors[position]);
                editor.commit();
                Log.i("stuff", majors[position]);
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
       SharedPreferences sharedpref= getSharedPreferences("Attribute",0);
        final String major= sharedpref.getString("Major", "missing");
        Log.i("stuff2", major);
        Intent newActivity= new Intent(MenteeSearchByAttributeActivity.this, MenteeSearchResultsActivity.class);
        //attempt to pass the username or name of the person that was clicked to the mentor profile activity
        Bundle majorBundle= new Bundle();
        majorBundle.putString("major", major);
        Log.i("before", majorBundle.getString("major"));
        newActivity.putExtras(majorBundle);
        startActivity(newActivity);
    }
    public String getDBItems(String majorAttr) {

        Uri.Builder uri = new Uri.Builder();
        uri.scheme("https");
        uri.authority("pma.piconepress.com");
        uri.path("query/search/");
        //uri.path("query/match/");
        final String url = uri.build().toString();
        SharedPreferences mSettings = getSharedPreferences("Login", 0);
        final String username = mSettings.getString("Username", "missing");
        final String password = mSettings.getString("Password", "missing");

        JSONObject json = new JSONObject();
        try {

            json.put("major", majorAttr);

        }
        catch (JSONException e)
        {

        }
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        if(response != null) {
                            Log.i("RRRR", response.toString());
                            SharedPreferences sharedpreferences;
                            sharedpreferences = getSharedPreferences("JSON", 0);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("Response", response.toString());
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

        SharedPreferences sharedPreferences = getSharedPreferences("JSON", 0);
        final String resp = sharedPreferences.getString("Response", "missing");
        queue.add(jsonRequest);
        return resp;
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
                        Toast.makeText(MenteeSearchByAttributeActivity.this, "This iz a Response" , Toast.LENGTH_LONG).show();
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
    }

    @Override
    public void onClick(View v){

        switch(v.getId()){
            case R.id.SearchButton:
                //onCheckboxClicked(v);
                //getDBMatches();
                SearchButtonClick();
                break;
        }
    }
}
