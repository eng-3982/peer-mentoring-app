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
public class StudentMainActivity extends AppCompatActivity implements View.OnClickListener {

    // declares buttons in java
    Button FindButton;
    Button ManageProfileButton;
    Button YourConnectionsButton;

    // basic onCreate with added linking of buttons frm XML to java
    // as well as setting onClickListeners for said buttons
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        FindButton=(Button)findViewById(R.id.FindButton);
        FindButton.setOnClickListener(this);
        ManageProfileButton=(Button)findViewById(R.id.ManageProfileButton);
        ManageProfileButton.setOnClickListener(this);
        YourConnectionsButton=(Button)findViewById(R.id.YourConnectionsButton);
        YourConnectionsButton.setOnClickListener(this);

    }

    // standard
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_main, menu);
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

    // specifically for when button01 is clicked to switch activity from
    // main activity to the find option activity
    private void FindButtonClick(){
        startActivity(new Intent(StudentMainActivity.this, MenteeFindOptionsActivity.class));}

    // specifically for when button02 is clicked to switch activty from
    // main activity to the manage profile activity
    private void ManageProfileButtonClick(){
        startActivity(new Intent(StudentMainActivity.this, MenteeManageProfileActivity.class));}

    // specifically for when button03 is clicked to switch activity from
    // main activity for your connections
    private void YourConnectionsButtonClick(){
        startActivity(new Intent(StudentMainActivity.this, MenteeYourConnectionsActivity.class));}

    // when click occurs, uses a switch statement that takes the ID to determine
    // action to complete in response. ADD DEFAULT?
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.FindButton:
                FindButtonClick();
                break;
            case R.id.ManageProfileButton:
                ManageProfileButtonClick();
                break;
            case R.id.YourConnectionsButton:
                YourConnectionsButtonClick();
                break;
        }
    }
}
