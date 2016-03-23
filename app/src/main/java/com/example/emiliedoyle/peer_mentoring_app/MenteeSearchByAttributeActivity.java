package com.example.emiliedoyle.peer_mentoring_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MenteeSearchByAttributeActivity extends AppCompatActivity implements View.OnClickListener {

    Button SearchButton;
    private RequestQueue queue;
    private ListView mainListView;
    public TextView resultsView;
    private ArrayAdapter<String> listAdapter;
    public String Major="Nothing was checked :(";

    public static final String KEY_MAJOR="major";
    public String major = "Other";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_search_by_attribute);
        SearchButton=(Button) findViewById(R.id.SearchButton);
        SearchButton.setOnClickListener(this);

        //create request queue
        queue = Volley.newRequestQueue(this);
        /*
        mainListView=(ListView) findViewById(R.id.mainListView);
        final String[] majors= new String[]{"Engineering","Science", "Fine Art","Art", "Acting","Liberal Arts","Other","Business","Sports"};
        ArrayList<String> majorList= new ArrayList<String>();
        majorList.addAll(Arrays.asList(majors));
        listAdapter= new ArrayAdapter<String>(this, R.layout.checkboxrow, majorList);//listAdapter.add("Ceres");
        mainListView.setAdapter(listAdapter);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //somehow set majors to be accessible to be used in SearchButtonClic
                //SharedPreferences sharedpref=getSharedPreferences("Attribute",0);
                //SharedPreferences.Editor editor= sharedpref.edit();
                //editor.putString("Major", majors[position]);
                //editor.commit();
                //Log.i("stuff", majors[position]);


            }
            
            @SuppressWarnings("unused")
            public void onClick(View v) {
            }

            ;
        });*/

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mentee_search_by_attribute, menu);
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

    private void SearchButtonClick(){
        SharedPreferences attribute = getSharedPreferences("Attribute", 0);
        SharedPreferences.Editor editor = attribute.edit();
        editor.putString("matches", major);
        editor.commit();
        Intent newActivity= new Intent(MenteeSearchByAttributeActivity.this, MenteeAttributeSearchResultActivity.class);
        startActivity(newActivity);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
             case R.id.checkBox:
                 if (checked)
                     major = "Engineering";
                 break;
             case R.id.checkBox2:
                 if (checked)
                     major = "Business";
                 break;
             case R.id.checkBox3:
                 if (checked)
                     major = "Liberal Arts";
                 break;
             case R.id.checkBox4:
                 if (checked)
                     major = "Fine Arts";
                 break;
             case R.id.checkBox5:
                 if (checked)
                     major = "Science";
                 break;
             case R.id.checkBox6:
                 if (checked)
                     major = "Other";
                 break;
            default:
                major = "Other";
                break;
         }

    }

    @Override
    public void onClick(View v){

        switch(v.getId()){
            case R.id.SearchButton:
                //String response = getDBItems(major);
                SearchButtonClick();
                break;
            default:
                onCheckboxClicked(v);
                break;
        }
    }
}
