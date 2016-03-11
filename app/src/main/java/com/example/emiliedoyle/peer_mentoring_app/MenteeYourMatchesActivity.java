package com.example.emiliedoyle.peer_mentoring_app;

// import necessary items for design, menu and connection between views
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// declare class, need to implement on click listener in order to switch views/activities
public class MenteeYourMatchesActivity extends AppCompatActivity implements View.OnClickListener {

    // declare button to return to main menu
    Button HomeButton;

    private ListView mainListView;
    private ArrayAdapter<String> listAdapter;


    // standard onCreate, with added button linking between java and XML
    // and the setting of the onClickListener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_your_matches);
        HomeButton = (Button) findViewById(R.id.HomeButton);
        HomeButton.setOnClickListener(this);

// DEMO THAT WORKS! PRAISE THE LORD! http://windrealm.org/tutorials/android/android-listview.php
        mainListView=(ListView) findViewById(R.id.mainListView);
        String[] planets= new String[]{"Claire A. Durand","Rachel K. King","Daniel J. Douglas","Emilie C. Doyle"};
        ArrayList<String> planetList= new ArrayList<String>();
        planetList.addAll(Arrays.asList(planets));
        listAdapter= new ArrayAdapter<String>(this, R.layout.simplerow, planetList);
        //listAdapter.add("Ceres");
        mainListView.setAdapter(listAdapter);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent newActivity = new Intent(MenteeYourMatchesActivity.this, MentorProfileActivity.class);
                        startActivity(newActivity);
                        break;
                }
            }

            @SuppressWarnings("unused")
            public void onClick(View v) {
            }

            ;
        });

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
    private void HomeButtonClick() {
        startActivity(new Intent(MenteeYourMatchesActivity.this, StudentMainActivity.class));
    }

    // when click occurs, uses switch case to determine which method
    // should be implemented. ADD DEFAULT?
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.HomeButton:
                HomeButtonClick();
                break;
        }

    }
}