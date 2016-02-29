package com.example.emiliedoyle.peer_mentoring_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


public class AdminMainActivity extends AppCompatActivity implements View.OnClickListener {

    Button FindButton;
    Button AddMentorButton;
    Button ManageConnectionsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        FindButton=(Button)findViewById(R.id.FindButton);
        FindButton.setOnClickListener(this);
        AddMentorButton=(Button)findViewById(R.id.AddMentorButton);
        AddMentorButton.setOnClickListener(this);
        ManageConnectionsButton=(Button)findViewById(R.id.ManageConnectionsButton);
        ManageConnectionsButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_main, menu);
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

    // specifically for when findbutton is clicked to switch activity from
    // main activity to the find activity
    private void FindButtonClick(){
        startActivity(new Intent(AdminMainActivity.this, FindActivity.class));}

    // specifically for when button02 is clicked to switch activty from
    // main activity to the manage profile activity
    private void AddMentorButtonClick(){
        startActivity(new Intent(AdminMainActivity.this, MenteeManageProfileActivity.class));}

    // specifically for when button03 is clicked to switch activity from
    // main activity for your connections
    private void ManageConnectionsButtonClick(){
        startActivity(new Intent(AdminMainActivity.this, MenteeYourConnectionsActivity.class));}

    // when click occurs, uses a switch statement that takes the ID to determine
    // action to complete in response. ADD DEFAULT?
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.FindButton:
                FindButtonClick();
                break;
            case R.id.AddMentorButton:
                AddMentorButtonClick();
                break;
            case R.id.ManageConnectionsButton:
                ManageConnectionsButtonClick();
                break;
        }
    }
}
