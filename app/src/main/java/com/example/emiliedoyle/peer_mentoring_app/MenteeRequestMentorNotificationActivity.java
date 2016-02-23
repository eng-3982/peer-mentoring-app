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
public class MenteeRequestMentorNotificationActivity extends AppCompatActivity implements View.OnClickListener{

    // declare button07 (to see your connections) and button08(to return to main menu)
    Button ViewConnectionsButton;
    Button HomeButton;

    // standard onCreate, with buttons linked from XML to java and onclick listener set
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_request_mentor_notification);
        ViewConnectionsButton=(Button)findViewById(R.id.ViewConnectionsButton);
        ViewConnectionsButton.setOnClickListener(this);
        HomeButton=(Button)findViewById(R.id.HomeButton);
        HomeButton.setOnClickListener(this);
    }

    // standard
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mentee_request_mentor_notification, menu);
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

    // specifically for when button07 is clicked to start new intent to switch
    // activitiy frm the mentee request mentor notification to the your connections
    // activity
    private void ViewConnectionsButtonClick(){
        startActivity(new Intent(MenteeRequestMentorNotificationActivity.this,MenteeYourConnectionsActivity.class));}

    // specifically for when button08 is clicked to start new intent to switch
    // activitiy from the mentee request mentor notification to the main menu page
    private void HomeButtonClick(){
        startActivity(new Intent(MenteeRequestMentorNotificationActivity.this,StudentMainActivity.class));}

    // when click occurs, uses switch statement to determine which method should
    // be implemented. ADD DEFAULT.
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ViewConnectionsButton:
                ViewConnectionsButtonClick();
                break;
            case R.id.HomeButton:
                HomeButtonClick();
                break;
        }
    }
}
