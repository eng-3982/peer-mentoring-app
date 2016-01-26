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

import com.example.emiliedoyle.peer_mentoring_app.R;

public class FindActivity extends AppCompatActivity implements View.OnClickListener{

    Button button04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        button04=(Button)findViewById(R.id.SearchResultsButton);
        button04.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find, menu);
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

    private void button04Click(){startActivity(new Intent(".MenteeSearchResultsActivity"));}

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.SearchResultsButton:
                    button04Click();
                    break;
        }
    }
}
