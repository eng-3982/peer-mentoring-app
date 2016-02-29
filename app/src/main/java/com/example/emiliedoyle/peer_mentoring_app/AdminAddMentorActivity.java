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

public class AdminAddMentorActivity extends AppCompatActivity implements View.OnClickListener {

    Button AddMentorButton;
    Button HomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_mentor);
        AddMentorButton=(Button)findViewById(R.id.AddMentorButton);
        AddMentorButton.setOnClickListener(this);
        HomeButton=(Button)findViewById(R.id.HomeButton);
        HomeButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_add_mentor, menu);
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

    // specifically for when button01 is clicked to switch activity from
    // main activity to the find option activity
    private void AddMentorButtonClick(){
        startActivity(new Intent(AdminAddMentorActivity.this, AdminAddMentorConfirmationActivity.class));}

    // specifically for when button02 is clicked to switch activty from
    // main activity to the manage profile activity
    private void HomeButtonClick(){
        startActivity(new Intent(AdminAddMentorActivity.this, AdminMainActivity.class));}

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.AddMentorButton:
                AddMentorButtonClick();
                break;
            case R.id.HomeButton:
                HomeButtonClick();
                break;

        }
    }
}
