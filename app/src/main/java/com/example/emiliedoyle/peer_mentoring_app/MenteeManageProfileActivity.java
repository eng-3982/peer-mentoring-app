package com.example.emiliedoyle.peer_mentoring_app;

// import necessary items for design, menu and connection between views
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

// declare class, need to implement on click listener in order to switch views/activities
public class MenteeManageProfileActivity extends AppCompatActivity implements View.OnClickListener {

    Button UpdateButton;

    // standard onCreate, no buttons yet
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_manage_profile);
        UpdateButton=(Button)findViewById(R.id.UpdateButton);
        UpdateButton.setOnClickListener(this);
    }

    // standard
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mentee_manage_profile, menu);
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

    private void UpdateButtonClick(){
        startActivity(new Intent(MenteeManageProfileActivity.this, MenteeUpdatedProfileMessageActivity.class));
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.UpdateButton:
                UpdateButtonClick();
                break;
        }
    }

}
