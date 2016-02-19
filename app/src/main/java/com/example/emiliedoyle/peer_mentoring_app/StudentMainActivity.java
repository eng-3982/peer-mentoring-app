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

public class StudentMainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button01;
    Button button02;
    Button button03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        button01=(Button)findViewById(R.id.FindButton);
        button01.setOnClickListener(this);
        button02=(Button)findViewById(R.id.ManageProfileButton);
        button02.setOnClickListener(this);
        button03=(Button)findViewById(R.id.YourConnectionsButton);
        button03.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_main, menu);
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


    private void button01Click(){startActivity(new Intent(StudentMainActivity.this, MenteeFindOptionsActivity.class));}
    private void button02Click(){startActivity(new Intent(StudentMainActivity.this, MenteeManageProfileActivity.class));}
    private void button03Click(){startActivity(new Intent(StudentMainActivity.this, MenteeYourConnectionsActivity.class));}

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.FindButton:
                button01Click();
                break;
            case R.id.ManageProfileButton:
                button02Click();
                break;
            case R.id.YourConnectionsButton:
                button03Click();
                break;
        }
    }
}
