package com.example.emiliedoyle.peer_mentoring_app;

// import necessary items for design, menu and connection between views
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import android.net.Uri;
import android.content.ContentUris;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Base64;

import java.util.HashMap;
import java.util.Map;

// declare class, need to implement on click listener in order to switch views/activities
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    // declare buttons (login and sign up), edit text and autocomplete text
    // which are user input in the XML view (once linked), additionally 2
    // strings which will be the email and password from the database
    // when that is up and running. RN it is hard coded
    //define our url


    Button button00;
    Button button000;
    private EditText editTextUsername;
    private EditText editTextPassword;
    //String dbpassword = "password";
    //String dbemail = "email";
    public static final String KEY_USERNAME="username";
    public static final String KEY_PASSWORD="password";

    public String username;
    private String password;

   //final TextView mTextView = (TextView) findViewById(R.id.debug);
    // standard onCreate, need to add the buttons to link it to the XML button via ID, and
    // to set the on click listener for changing activities/views. Additionally, we read in
    // the user inputted email and password, but finidng the content of the XML
    // corresponding values and setting that input to the declared edit text & auto complete
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button00 = (Button) findViewById(R.id.email_sign_in_button);
        button00.setOnClickListener(this);
        button000 = (Button) findViewById(R.id.email_register_button);
        button000.setOnClickListener(this);

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
        uri.path("private/");
        final String url = uri.build().toString();

        EditText un = (EditText)findViewById(R.id.editTextUsername);
        username = un.getText().toString();

        EditText pw = (EditText)findViewById(R.id.editTextPassword);
        password = pw.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            openProfile();
                            Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_LONG).show();
                            //do something
                        }else{
                            Toast.makeText(LoginActivity.this, response, Toast.LENGTH_LONG).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG).show();
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
                headers.put(KEY_USERNAME,username);
                headers.put(KEY_PASSWORD,password);
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
    private void button00Click() {

        startActivity(new Intent(LoginActivity.this, StudentMainActivity.class));
    }

    // specifically for when the button000(register/sign up) is clicked
    // this will change the activity through the use of a new Intent.
    private void button000Click() {
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
                button000Click();
                //
                break;
        }
    }

   /* @Override
   public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.emiliedoyle.peer_mentoring_app/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }
*/
    /*
    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.emiliedoyle.peer_mentoring_app/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
    */
}
