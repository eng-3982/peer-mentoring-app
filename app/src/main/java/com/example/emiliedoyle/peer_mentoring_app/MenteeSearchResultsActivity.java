package com.example.emiliedoyle.peer_mentoring_app;

// import necessary items for design, menu and connection between views
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

//package com.realco.claire.practice;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MenteeSearchResultsActivity extends AppCompatActivity implements View.OnClickListener {


    // Instantiate the RequestQueue.
    private RequestQueue queue;
    private String url;

    EditText txtInput;
    // declare button
    Button button10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_search_results);

        // test string array
        String[] mobileArray={"Android","iPhone", "Windows lol"};
        //run cluirrr's functions
        //postDBItem();
        queue = Volley.newRequestQueue(this);
        url = "http://159.203.68.208:5000/data";
        getDBItems();
        button10 = (Button) findViewById(R.id.HomeButton);
        button10.setOnClickListener(this);

        //create ListView where find results will be displayed
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_mentee_search_results,mobileArray );
        ListView listView= (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
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

    /*public void postDBItem(){

        // This is from the Volley tutorial
        final TextView mTextView = (TextView) findViewById(R.id.text_view2);

        // Get the string from the input edit text box
        txtInput = (EditText) findViewById(R.id.edit_message);
        String inName = txtInput.getText().toString();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("GETTING", "onResponse for getDBItems happening");
                        getDBItems();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Do nothing
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("major", txtInput.getText().toString());

                return params;
            }
        };

        queue.add(postRequest);

    }
*/
    public void getDBItems() {
        // This is from the Volley tutorial

        final TextView mTextView = (TextView) findViewById(R.id.results);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string (items of the DB)
                        mTextView.setText("Response is: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });
        // End of tutorial from Volley

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private void button10Click() {
        startActivity(new Intent(MenteeSearchResultsActivity.this, StudentMainActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.HomeButton:
                button10Click();
                break;
        }
    }
}
