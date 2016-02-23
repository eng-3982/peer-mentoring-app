package com.example.emiliedoyle.peer_mentoring_app;

// import necessary items for design, menu and connection between views
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

// declare class, need to implement on click listener in order to switch views/activities
public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    // declare buttons (login and sign up), edit text and autocomplete text
    // which are user input in the XML view (once linked), additionally 2
    // strings which will be the email and password from the database
    // when that is up and running. RN it is hard coded
    Button email_sign_in_button;
    Button email_register_button;
    EditText Password;
    AutoCompleteTextView Email;
    String dbpassword= "password";
    String dbemail="email";

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
        Password = (EditText)findViewById(R.id.password);
        Email=(AutoCompleteTextView)findViewById(R.id.email);

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
        if(((Password.getText().toString()).equals(dbpassword)) && (((Email.getText().toString()).equals(dbemail)))){
            // go to menu
            startActivity(new Intent(LoginActivity.this, StudentMainActivity.class));
        }
        else {
            // show error message
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

            dlgAlert.setMessage("Incorrect email or password");
            dlgAlert.setTitle("Error Message...");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();

            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
        }
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
        switch (v.getId()){
            case R.id.email_sign_in_button:
                email_sign_in_buttonClick();
                break;
            case R.id.email_register_button:
                email_register_buttonClick();
                break;
        }
    }
}
