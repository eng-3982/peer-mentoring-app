package com.example.emiliedoyle.peer_mentoring_app;

// import necessary items for design, menu and connection between views
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import java.util.Arrays;
import java.util.ArrayList;
import android.widget.ListView;

// declare class, need to implement on click listener in order to switch views/activities
public class MenteeYourConnectionsActivity extends AppCompatActivity implements View.OnClickListener {

    // declare button to return to main menu
    Button HomeButton;

    private ListView mainListView;
    private ArrayAdapter<String> listAdapter;

    // standard onCreate,with added linking of buttons from xml and java
    // and setting the on click listener to change activities/views
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_your_connections);
        HomeButton=(Button)findViewById(R.id.HomeButton);
        HomeButton.setOnClickListener(this);

        // DEMO THAT WORKS! PRAISE THE LORD! http://windrealm.org/tutorials/android/android-listview.php
        mainListView=(ListView) findViewById(R.id.mainListView);
        final String[] planets= new String[]{"Claire A. Durand"};
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
                        Intent newActivity = new Intent(MenteeYourConnectionsActivity.this, MentorProfileActivity.class);
                        Bundle bundle= new Bundle();
                        bundle.putString("name", planets[position]);
                        Log.i("before", bundle.getString("name"));
                        newActivity.putExtras(bundle);
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
        getMenuInflater().inflate(R.menu.menu_mentee_your_connections, menu);
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

    // specifically for when button11 is clicked to change from the your connections
    // activity to the main menu activity
    private void HomeButtonClick(){
        startActivity(new Intent(MenteeYourConnectionsActivity.this, StudentMainActivity.class));}

    // when click occurs, uses switch case which takes the ID to determine
    // action to complete in response. ADD DEFAULT.
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.HomeButton:
                HomeButtonClick();
                break;
        }
    }
}

