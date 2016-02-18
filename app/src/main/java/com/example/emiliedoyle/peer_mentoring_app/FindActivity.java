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
public class FindActivity extends AppCompatActivity implements View.OnClickListener{

    // declare find button
    Button button04;

    // standard onCreate, needed to add the buttons to link it to the XML button via ID, and
    // to set the on click listener for changing activities/views
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        button04=(Button)findViewById(R.id.SearchResultsButton);
        button04.setOnClickListener(this);
    }

    // standard
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find, menu);
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

    // specifically for when the button04 (find) is clicked, this will enable that
    // a new activity is started through the use of an intent. the intent takes
    // the current activity and the activity that you wish to change to
    private void button04Click() {
        startActivity(new Intent(FindActivity.this, MenteeSearchResultsActivity.class));}

    // when click occurs, uses switch case which takes the ID to determine which
    // action to complete in response. ADD DEFAULT?
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.SearchResultsButton:
                    button04Click();
                    break;
        }
    }
}
