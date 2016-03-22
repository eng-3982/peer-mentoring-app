package com.example.emiliedoyle.peer_mentoring_app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Rachel on 3/17/2016.
 */


public class VolleyRequest {

    public String Response;
    public JSONObject JSONGetResponse;
    public JSONObject JSONPostResponse;
    public String info;



    //POST Request for Strings
    public StringRequest postString(String url, final String username, final String password) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Response = response;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("VolleyRequest", "QQQQ:  " + error.toString());
                        //mTextView.setText(error.networkResponse.statusCode);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //encode server authorization
                String creds = "eng-3982:isipjuice";
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", auth);
                //encode username and password credentials
                headers.put("username", username);
                headers.put("password", password);
                return headers;
            }

        };
        return stringRequest;
    }

    //GET Request for JSON Objects
    public JsonObjectRequest getJSON(final VolleyCallback callback, String url, final String uname, final String pw) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        callback.onSuccess(JSONGetResponse);
                        JSONGetResponse = response;
                        Log.i("SearchResultActivity", "QQQQ" + response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            //if there is an error, print it!
            public void onErrorResponse(VolleyError error) {
                Log.i("SearchResultActivity", "QQQQ" + error.toString());

            }
        }) {
            @Override
            //OH LOOK AT ME, I'M VOLLEY AND MY HEADER IS WRONG
            //modifies the header to contain the encoded username and password info. authenticates
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String creds = "eng-3982:isipjuice";
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                //headers.put("Content-Type: application/json");
                headers.put("Authorization", auth);
                headers.put("username", uname);
                headers.put("password", pw);
                return headers;
            }

        };
        // Add the request to the RequestQueue.

        //something I needed to do so that there wouldn't be time-outs
        //we may not need this? who on earth knows.
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                27000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Log.i("SearchResultActivity", "QQQQ" + "this just does not work");

        return jsonRequest;
    }

    public JsonObjectRequest postJSON(final VolleyCallback callback, String url, final String uname, final String pword)
    {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        JSONPostResponse = response;
                        callback.onSuccess(JSONPostResponse);
                        //JSONPostResponse = response;
                        Log.i("VolleyRequest", "PPPP"+ JSONPostResponse.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            //if there is an error, print it!
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            //modifies the header to contain the encoded username and password info. authenticates
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String creds = "eng-3982:isipjuice";
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                //headers.put("Content-Type: application/json");
                headers.put("Authorization", auth);
                headers.put("username", uname);
                headers.put("password", pword);
                return headers;
            }

        };

        //something I needed to do so that there wouldn't be time-outs
        //we may not need this? who on earth knows.
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                27000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return jsonRequest;

    }


    public interface VolleyCallback{
        void onSuccess(JSONObject result);
    }


    public String[] parseJSON(JSONObject response, String value)
    {
        String[] item = new String[50];
        try {
            Iterator<?> keys = response.keys();
            int i = 0;

            while(keys.hasNext())
            {
                String key = (String) keys.next();

                if (response.get(key) instanceof JSONObject) {
                    JSONObject person = response.getJSONObject(key);
                    item[i] = person.getString(value);
                    i++;
                }
            }
        }
        catch (JSONException e)
        {
            Log.i("VolleyReqest", "THAT DON'T WORK");
        }
        return item;
}

}
