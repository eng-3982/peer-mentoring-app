package com.example.emiliedoyle.peer_mentoring_app;

// import necessary items for design, menu and connection between views
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

// declare class, need to implement on click listener in order to switch views/activities
public class MenteeRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    // declare (register) button
    Button menteeRegisterButton;

    public static final String KEY_USERNAME="username";
    public static final String KEY_EMAIL="email";
    public static final String KEY_PASSWORD="password";
    public static final String KEY_CONFIRM_PASSWORD="confirm password";
    public static final String KEY_MAJOR="major";
    public static final String KEY_YEAR="year";

    public String username;
    public String email;
    public String password;
    public String c_password;
    public String major;
    public String year;



    // standard onCreate, with button linked to XML content and onClickListener set
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_register);
        menteeRegisterButton=(Button)findViewById(R.id.menteeRegisterButton);
        menteeRegisterButton.setOnClickListener(this);
    }

    // standard
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mentee_register, menu);
        return true;
    }
    private void addUser() {
        //define our url
        Uri.Builder uri = new Uri.Builder();
        uri.scheme("https");
        uri.authority("pma.piconepress.com");
        uri.path("data/");
        final String url = uri.build().toString();

        EditText n = (EditText)findViewById(R.id.enterUserName);
        username = n.getText().toString();

        EditText e = (EditText)findViewById(R.id.enterEmail);
        email = e.getText().toString();

        EditText p = (EditText)findViewById(R.id.enterPassword);
        password = p.getText().toString();

        EditText c = (EditText)findViewById(R.id.confirmPassword);
        c_password = c.getText().toString();

        EditText m = (EditText)findViewById(R.id.enterMajor);
        major = m.getText().toString();

        EditText y = (EditText)findViewById(R.id.enterClassYear);
        year = y.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("super admin")){
                            startActivity(new Intent(MenteeRegisterActivity.this, StudentMainActivity.class));
                            //do something
                        }

                        else if(response.trim().equals("admin"))
                        {
                            startActivity(new Intent(MenteeRegisterActivity.this, StudentMainActivity.class));
                        }

                        else if (response.trim().equals("mentor") || response.trim().equals("mentee"))
                        {
                            startActivity(new Intent(MenteeRegisterActivity.this, StudentMainActivity.class));
                        }
                        else{
                            Toast.makeText(MenteeRegisterActivity.this, response, Toast.LENGTH_LONG).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MenteeRegisterActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        //mTextView.setText(error.networkResponse.statusCode);
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String,String>();
                //encode server authorization
                String creds = "eng-3982:isipjuice";
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", auth);
                //encode username and password credentials
                headers.put(KEY_USERNAME, username);
                headers.put(KEY_EMAIL, email);
                headers.put(KEY_PASSWORD, password);
                headers.put(KEY_CONFIRM_PASSWORD, c_password);
                headers.put(KEY_MAJOR, major);
                headers.put(KEY_YEAR, year);
                return headers;
            }

            // @Override
            /*protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_USERNAME, username);
                map.put(KEY_PASSWORD, password);
                return map;
            }*/
        };

        //create a new request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //do not save volley responses in Cache
        stringRequest.setShouldCache(false);
        //add the string response to the queue
        requestQueue.add(stringRequest);
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

    // method for switching activities from register to the main activity on click
<<<<<<< HEAD
    private void button05Click(){
        //startActivity(new Intent(MenteeRegisterActivity.this, StudentMainActivity.class));
        addUser();
    }

=======
    private void menteeRegisterButtonClick(){
        startActivity(new Intent(MenteeRegisterActivity.this, StudentMainActivity.class));}
>>>>>>> 1a50cf55038dad32dfdab71550e17d94e5c08377

    // when click occurs, uses switch case to determine which method should be
    // implemented. ADD DEFAULT
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menteeRegisterButton:
                menteeRegisterButtonClick();
                break;
        }
    }
}
