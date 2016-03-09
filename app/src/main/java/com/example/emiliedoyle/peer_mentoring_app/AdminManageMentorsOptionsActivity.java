package com.example.emiliedoyle.peer_mentoring_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class AdminManageMentorsOptionsActivity extends AppCompatActivity implements View.OnClickListener {

    Button AddMentorButton;
    Button DeleteMentorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_mentors_options);
        AddMentorButton=(Button)findViewById(R.id.AddMentorButton);
        AddMentorButton.setOnClickListener(this);
        DeleteMentorButton=(Button)findViewById(R.id.DeleteMentorButton);
        DeleteMentorButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_manage_mentors_options, menu);
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

    private void AddMentorButtonClick(){
        startActivity(new Intent(AdminManageMentorsOptionsActivity.this, MenteeRegisterActivity.class));
    }
    //need to change this to delete!
    private void DeleteMentorButtonClick(){
        startActivity(new Intent(AdminManageMentorsOptionsActivity.this,MenteeRegisterActivity.class ));
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.AddMentorButton:
                AddMentorButtonClick();
                break;
            case R.id.DeleteMentorButton:
               DeleteMentorButtonClick();
                break;
        }
    }
}
