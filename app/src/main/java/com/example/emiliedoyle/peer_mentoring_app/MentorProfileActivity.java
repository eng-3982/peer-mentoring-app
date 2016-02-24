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

// declare class, need to implement on click listener in order to switch views/activities
public class MentorProfileActivity extends AppCompatActivity implements View.OnClickListener {

    // declare button 06
    Button requestMentorButton;

    // standard onCreate, with added button linking and setting onClickListener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_profile);
        requestMentorButton=(Button)findViewById(R.id.requestMentor);
        requestMentorButton.setOnClickListener(this);
    }

    // standard
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mentor_profile, menu);
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

    // specifically for when button06 is clicked to change from mentor profile activity
    // to the mentee request mentor activity
    private void requestMentorButtonClick(){
        startActivity(new Intent(MentorProfileActivity.this,MenteeRequestMentorNotificationActivity.class));}

    // uses switch case to determine which method to implement when click occurs
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.requestMentor:
                requestMentorButtonClick();
                break;
        }
    }


}
