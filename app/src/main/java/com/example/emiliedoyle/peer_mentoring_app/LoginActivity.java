package com.example.emiliedoyle.peer_mentoring_app;

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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{

    Button button00;
    Button button000;
    EditText Password;
    AutoCompleteTextView Email;
    String dbpassword= "password";
    String dbemail="email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button00=(Button)findViewById(R.id.email_sign_in_button);
        button00.setOnClickListener(this);
        button000=(Button)findViewById(R.id.email_register_button);
        button000.setOnClickListener(this);
        Password = (EditText)findViewById(R.id.password);
        Email=(AutoCompleteTextView)findViewById(R.id.email);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    private void button00Click(){
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

    private void button000Click(){
        startActivity(new Intent(LoginActivity.this, MenteeRegisterActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.email_sign_in_button:
                button00Click();
                break;
            case R.id.email_register_button:
                button000Click();
                break;
        }
    }
}
