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

public class MenteeYourConnectionsActivity extends AppCompatActivity implements View.OnClickListener {

    Button button11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_your_connections);
        button11=(Button)findViewById(R.id.HomeButton);
        button11.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mentee_your_connections, menu);
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

    private void button11Click(){startActivity(new Intent(MenteeYourConnectionsActivity.this, StudentMainActivity.class));}

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.HomeButton:
                button11Click();
                break;
        }
    }
}

