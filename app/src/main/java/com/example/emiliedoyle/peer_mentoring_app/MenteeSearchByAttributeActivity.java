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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MenteeSearchByAttributeActivity extends AppCompatActivity implements View.OnClickListener {

    Button SearchButton;
    private RequestQueue queue;
    int i = 0;
    public static final String KEY_MAJOR="major";
    public static final int MAJOR_ARRAY_LEN = 7;
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

        // majors[] was moved further down into display listview

        displayListView();
}

    // Resource: http://www.mysamplecode.com/2012/07/android-listview-checkbox-example.html
    // This class is necessary for the checkbox listview. We need a way to keep track of which checkboxes are checked
    // and what attribute they correspond to. We also need to be able to dynamically set them, currently based on a
    // hard coded array of majors, but ideally we would query the database for majors that we have mentors for
    public class Attribute {

        String name = null;
        boolean selected = false;

        public Attribute(String name, boolean selected) {
            super();
            this.name = name;
            this.selected = selected;
        }

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

        // string array of majors or potentially other attributes ie student athlete, study abroad, etc
        final String[] majors= new String[]{"Engineering","Science", "Fine Art","Art", "Acting","Liberal Arts","Other","Business","Sports", "Electrical Engineering"};
        //Array list of attributes
        ArrayList<Attribute> attList = new ArrayList<Attribute>();

        // add the majors to the attribute list
        for(i=0; i<MAJOR_ARRAY_LEN; i++){
            Attribute attribute= new Attribute(majors[i], false);
            attList.add(attribute);
        }

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this, R.layout.checkboxrow, attList);
        ListView listView = (ListView) findViewById(R.id.mainListView);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Attribute attribute = (Attribute) parent.getItemAtPosition(position);
                /*Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + attribute.getName(),
                        Toast.LENGTH_LONG).show();*/ // for debuggin purposes
            }
        });

    }

    private class MyCustomAdapter extends ArrayAdapter<Attribute> {

        private ArrayList<Attribute> attList;

        public MyCustomAdapter(Context context, int textViewResourceId, ArrayList<Attribute> attList) {
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
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.checkboxrow, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.textView1);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Attribute attribute = (Attribute) cb.getTag();
                        /*Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();*/
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
        StringBuffer responseText = new StringBuffer();
        //responseText.append("The following were selected...\n");

        ArrayList<Attribute> attList = dataAdapter.attList;
        int j=0;
        for(int i=0;i<attList.size();i++){
            Attribute attribute = attList.get(i);
            if(attribute.isSelected()){
                //responseText.append("\n" + attribute.getName());
                major[j]=attribute.getName().toString();
                //Log.i("mAJOR list", major[j]);
                //Log.i("major num", String.valueOf(j));
                j++;
            }
        }


        /*Toast.makeText(getApplicationContext(),
                responseText, Toast.LENGTH_LONG).show();*/ // for debuggin purposes

        //Log.i("mm", Arrays.toString(major));
        Intent newActivity= new Intent(MenteeSearchByAttributeActivity.this, MenteeAttributeSearchResultActivity.class);
        Bundle bundle= new Bundle();
        bundle.putString("selectedMajors", Arrays.toString(Arrays.copyOfRange(major,0,j)));
        newActivity.putExtras(bundle);
        //Log.i("mmMm", bundle.getString("selectedMajors"));
        startActivity(newActivity);
    }


    @Override
    public void onClick(View v){

        switch(v.getId()){
            case R.id.SearchButton:
                //String response = getDBItems(major);
                SearchButtonClick();
                break;
        }
    }
}
