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
public class MenteeFindOptionsActivity extends AppCompatActivity implements View.OnClickListener {

    // declare buttons (by attribute or already matched)
    Button button07;
    Button button08;

    // standard onCreate, with added button functionality, which links
    // the buttons in the java code to that of the XML. it also sets
    // the onClickListener, so that if they are clicked, we will know
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_find_options);
        button07=(Button)findViewById(R.id.YourMatchesButton);
        button07.setOnClickListener(this);
        button08=(Button)findViewById(R.id.SearchButton);
        button08.setOnClickListener(this);
    }

    // standard
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mentee_find_options, menu);
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

    // specifically for the button07 click, which will use a new Intent
    // in order to switch to the 'your matches' activity
    private void button07Click() {
        startActivity(new Intent(MenteeFindOptionsActivity.this, MenteeYourMatchesActivity.class));}

    // specifically for button08 click, which will use a new Intent
    // in order to switch to the 'Search' activity
    private void button08Click() {
        startActivity(new Intent(MenteeFindOptionsActivity.this, MenteeSearchResultsActivity.class));}

    // when click occurs, uses switch case to take ID of item clicked
    // and find which corresponding method should be executed. ADD DEFAULT
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.YourMatchesButton:
                button07Click();
                break;
            case R.id.SearchButton:
                button08Click();
                break;
        }
    }
}
