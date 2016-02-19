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
public class MenteeYourMatchesActivity extends AppCompatActivity implements View.OnClickListener {

    // declare button to return to main menu
    Button button09;

    // standard onCreate, with added button linking between java and XML
    // and the setting of the onClickListener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_your_matches);
        button09 = (Button) findViewById(R.id.HomeButton);
        button09.setOnClickListener(this);
    }

    // standard
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mentee_your_matches, menu);
        return true;
    }

    //standard
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

    // specifically for when button 9 is clicked, to change from
    // your matches activity, back to the main menu
    private void button09Click() {
        startActivity(new Intent(MenteeYourMatchesActivity.this, StudentMainActivity.class));
    }

    // when click occurs, uses switch case to determine which method
    // should be implemented. ADD DEFAULT?
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.HomeButton:
                button09Click();
                break;
        }

    }
}