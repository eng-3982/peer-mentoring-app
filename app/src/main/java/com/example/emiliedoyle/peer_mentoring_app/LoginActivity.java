package com.example.emiliedoyle.peer_mentoring_app;
import com.android.volley.NetworkResponse;
import com.android.volley.toolbox.JsonRequest;
import com.example.emiliedoyle.peer_mentoring_app.CookieExample;
// import necessary items for design, menu and connection between views
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
//import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
import android.net.Uri;
import android.content.ContentUris;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Base64;

import org.json.JSONObject;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// declare class, need to implement on click listener in order to switch views/activities
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    // declare buttons (login and sign up), edit text and autocomplete text
    // which are user input in the XML view (once linked), additionally 2
    // strings which will be the email and password from the database
    // when that is up and running. RN it is hard coded


    private final static String URL_STRING = "https://www.pma.piconepress.com/login/";

    //testing shared preferences
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    SharedPreferences sharedpreferences;

    //define our url
    private EditText editTextUsername;
    private EditText editTextPassword;
    //String dbpassword = "password";
    //String dbemail = "email";
    public static final String KEY_USERNAME="username";
    public static final String KEY_PASSWORD="password";

    public String username;
    private String password;

   //final TextView mTextView = (TextView) findViewById(R.id.debug);
    Button email_sign_in_button;
    Button email_register_button;

    // standard onCreate, need to add the buttons to link it to the XML button via ID, and
    // to set the on click listener for changing activities/views. Additionally, we read in
    // the user inputted email and password, but finidng the content of the XML
    // corresponding values and setting that input to the declared edit text & auto complete

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email_sign_in_button=(Button)findViewById(R.id.email_sign_in_button);
        email_sign_in_button.setOnClickListener(this);
        email_register_button=(Button)findViewById(R.id.email_register_button);
        email_register_button.setOnClickListener(this);
        String args= "whoa";

        sharedpreferences = getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("Name", Name);
        editor.putString("Phone", Phone);
        editor.putString("Email", Email);
        editor.commit();

        //testing Cookies functionality
        String[] stuff = new String[3];
        stuff[0] = URL_STRING;
        stuff[1] = "Raquel";
        stuff[2] = "Sloths";
        //new CookieExample().execute(stuff);
        //MEH commented this out to get rid of error, probs going to need to bring back
       // Password = (EditText) findViewById(R.id.password);
        //Email = (AutoCompleteTextView) findViewById(R.id.email);
    }

    /************
    POST request function to verify user-given login information
     - takes username and password from the EditText fields and encodes them in the volley header
     - listens for response string from server to verify valid credentials
        - if correct ("success"), user is taken to the main page
        - if incorrect, a pop-up message informs the user of invalid credentials
    *************/
    private void userLogin() {
        //define our url
        Uri.Builder uri = new Uri.Builder();
        uri.scheme("https");
        uri.authority("pma.piconepress.com");
        uri.path("login/");
        final String url = uri.build().toString();

        EditText un = (EditText)findViewById(R.id.editTextUsername);
        username = un.getText().toString();

        EditText pw = (EditText)findViewById(R.id.editTextPassword);
        password = pw.getText().toString();

        VolleyRequest volleyRequest = new VolleyRequest();
        //create a new request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //add the string response to the queue
        JsonRequest things = volleyRequest.postJSON(new VolleyRequest.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                if (result != null) {
                    openProfile();
                }
                else if (result == null) {
                    Toast.makeText(LoginActivity.this, "null", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(LoginActivity.this, "Authentication Failed. Please try again.", Toast.LENGTH_SHORT).show();

            }
        },
                url, username, password);

        requestQueue.add(things);
    }

    private void openProfile(){
        Intent intent = new Intent(this, StudentMainActivity.class);
        //intent.putExtra(KEY_USERNAME, username);
        startActivity(intent);
    }
    // standard
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    // specifically for when button00 is clicked. This should check the user
    // inputted fields that were stored as Password and Email, and compare the
    // string value to that provided from the database. If they match, then the
    // main menu will be started thanks to a new intent. Again, the intent takes
    // in the current activity, and then the one you wish to go to. If the email
    // and password do not match, then an error message should appear. The error
    // message code was found online and fit to our needs

    private void email_sign_in_buttonClick(){
        startActivity(new Intent(LoginActivity.this, StudentMainActivity.class));
    }

    // specifically for when the button000(register/sign up) is clicked
    // this will change the activity through the use of a new Intent.

    private void email_register_buttonClick(){
        startActivity(new Intent(LoginActivity.this, MenteeRegisterActivity.class));
    }
    //  when a click occurs, this uses a switch statement that takes in
    // the ID of the item clicked and the cases are the optional buttons
    // at which point, it will activate the corresponding button's click
    // function. ADD DEFAULT?


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email_sign_in_button:
                //button00Click();
                userLogin();
                break;
            case R.id.email_register_button:
                //button000Click();
                email_register_buttonClick();
                break;

        }
    }
}

/*
public class CustomRequest extends StringRequest
{
    public CustomRequest(int method, String url, String stringRequest,
                         Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, stringRequest, listener, errorListener);
    }

    private Map<String, String> headers = new HashMap<>();

    public void setCookies(List<String> cookies) {
        StringBuilder sb = new StringBuilder();
        for (String cookie : cookies) {
            sb.append(cookie).append("; ");
        }
        //headers.put("Cookie", sb.toString());
        //Toast.makeText(LoginActivity.this, sb.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers;
    }

}*/

