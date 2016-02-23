package com.example.emiliedoyle.peer_mentoring_app;

// import necessary items for design, menu and connection between views
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

// declare class, need to implement on click listener in order to switch views/activities
public class MenteeRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    // declare (register) button
    Button button05;

    // standard onCreate, with button linked to XML content and onClickListener set
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_register);
        button05=(Button)findViewById(R.id.menteeRegisterButton);
        button05.setOnClickListener(this);
    }

    // standard
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mentee_register, menu);
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

    // method for switching activities from register to the main activity on click
    private void button05Click(){
        startActivity(new Intent(MenteeRegisterActivity.this, StudentMainActivity.class));}

    // when click occurs, uses switch case to determine which method should be
    // implemented. ADD DEFAULT
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menteeRegisterButton:
                button05Click();
                break;
        }
    }
}
