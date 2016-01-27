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


public class MenteeFindOptionsActivity extends AppCompatActivity implements View.OnClickListener {

    Button button07;
    Button button08;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_find_options);
        button07=(Button)findViewById(R.id.YourMatchesButton);
        button07.setOnClickListener(this);
        button08=(Button)findViewById(R.id.SearchButton);
        button08.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mentee_find_options, menu);
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

    private void button07Click() {
        startActivity(new Intent(MenteeFindOptionsActivity.this, MenteeYourMatchesActivity.class));}
    private void button08Click() {
        startActivity(new Intent(MenteeFindOptionsActivity.this, MenteeSearchResultsActivity.class));}
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
