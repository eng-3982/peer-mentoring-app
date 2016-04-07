package com.example.emiliedoyle.peer_mentoring_app;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
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
    //private ListView mainListView;
    //public TextView resultsView;
    //private ArrayAdapter<String> listAdapter;
    int i = 0;
    public static final String KEY_MAJOR="major";
    public static final int MAJOR_ARRAY_LEN = 6;
    public String[] major = new String[MAJOR_ARRAY_LEN];
    MyCustomAdapter dataAdapter= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Arrays.fill(major, null);
        //Log.i("Majorz", Arrays.toString(major));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_search_by_attribute);
        SearchButton=(Button) findViewById(R.id.SearchButton);
        SearchButton.setOnClickListener(this);

        //create request queue
        queue = Volley.newRequestQueue(this);

        //mainListView=(ListView) findViewById(R.id.mainListView);
        // majors[] was moved further down into the country constructor
        // final String[] majors= new String[]{"Engineering","Science", "Fine Art","Art", "Acting","Liberal Arts","Other","Business","Sports"};

        displayListView();
        checkButtonClick();
}

// START
    //Resource: http://www.mysamplecode.com/2012/07/android-listview-checkbox-example.html
    public class Attribute {

        //String code = null;
        String name = null;
        boolean selected = false;

        public Attribute(/*String code,*/ String name, boolean selected) {
            super();
            //this.code = code;
            this.name = name;
            this.selected = selected;
        }
/*
        public String getCode() {
            return code;
        }
        public void setCode(String code) {
            this.code = code;
        }*/
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelected() {
            return selected;
        }
        public void setSelected(boolean selected) {
            this.selected = selected;
        }

    }

    private void displayListView() {

        final String[] majors= new String[]{"Engineering","Science", "Fine Art","Art", "Acting","Liberal Arts","Other","Business","Sports"};
        //Array list of countries
        ArrayList<Attribute> attList = new ArrayList<Attribute>();

        for(i=0; i<MAJOR_ARRAY_LEN; i++){
            Attribute attribute= new Attribute(majors[i], false);
            attList.add(attribute);
        }

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this,
                R.layout.checkboxrow, attList);
        ListView listView = (ListView) findViewById(R.id.mainListView);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Attribute attribute = (Attribute) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + attribute.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }
    private class MyCustomAdapter extends ArrayAdapter<Attribute> {

        private ArrayList<Attribute> attList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Attribute> attList) {
            super(context, textViewResourceId, attList);
            this.attList = new ArrayList<Attribute>();
            this.attList.addAll(attList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.checkboxrow, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.textView1);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Attribute attribute = (Attribute) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        attribute.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Attribute attribute = attList.get(position);
            //holder.code.setText(" (" +  country.getCode() + ")");
            holder.name.setText(attribute.getName());
            holder.name.setChecked(attribute.isSelected());
            holder.name.setTag(attribute);

            return convertView;

        }

    }

    private void checkButtonClick() {


        Button myButton = (Button) findViewById(R.id.SearchButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<Attribute> attList = dataAdapter.attList;
                for(int i=0;i<attList.size();i++){
                    Attribute attribute = attList.get(i);
                    if(attribute.isSelected()){
                        responseText.append("\n" + attribute.getName());
                        major[i]=attribute.getName().toString();
                        Log.i("mAJOR list", major[i]);
                    }
                }
                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();
            }
        });

    }

    //END


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
        editor.clear();
        editor.putString("matches", Arrays.toString(major));
        //Log.i("stringy!", major.toString());
        editor.commit();

        Intent newActivity= new Intent(MenteeSearchByAttributeActivity.this, MenteeAttributeSearchResultActivity.class);
        startActivity(newActivity);
    }


    @Override
    public void onClick(View v){

        switch(v.getId()){
            case R.id.SearchButton:
                //String response = getDBItems(major);
                SearchButtonClick();
                break;
            default:
                break;
        }
    }
}
