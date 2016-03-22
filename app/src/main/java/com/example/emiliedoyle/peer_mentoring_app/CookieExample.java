package com.example.emiliedoyle.peer_mentoring_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.example.emiliedoyle.peer_mentoring_app.LoginActivity;
/**
 * Created by Rachel on 3/16/2016.
 */
/*
public class CookieExample extends AsyncTask<String, String, String>
{
    CookieManager cookieManager = new CookieManager();
    CookieHandler.setDefault(cookieManager);
    CookieStore cookieStore = cookieManager.getCookieStore();
    List cookieList = cookieStore.getCookies();
    cookieList.add(Arrays.toString(things).replace("[", "").replace("]", ""));
    //Log.i("CookieExample", "RAQUEL" + cookieList.toString());
    public String doInBackground(String... things) {


        //return 1;
        return null;
    }
    protected void onProgressUpdate(String... progress) {
        //Log.d(TAG, "wha");



    }
    protected void onPostExecute(String... result) {
        //showDialog("Downloaded " + result + " bytes");

    }

}*/

public class CookieExample extends AsyncTask<String[], String, Void>
{

    protected Void doInBackground(String[]... stuff) {
        /*String stuff1 = Arrays.toString(stuff).replace("[", "").replace("]", "").replace(" ", "");
        String[] stuff2 = stuff1.split(",");

        Log.i("CookieExample", "BBBB  " + Arrays.toString(stuff));// + "  " + Arrays.toString(stuff[1]) + "  " + Arrays.toString(stuff[2]));*/
        try {

            String url = "http://pma.piconepress.com/login/";
            CookieManager cookieManager = new CookieManager();
            CookieHandler.setDefault(cookieManager);
            //url = Arrays.toString(url).replace("[", "").replace("]", "");
            //URL url_1 = new URL(Arrays.toString(stuff[0]).replace("[", "").replace("]", ""));

            String urlParameters =
                    "Username=" + URLEncoder.encode("Raquel", "UTF-8") +
                            "Password=" + URLEncoder.encode("Sloths", "UTF-8");
            String charset = "UTF-8";
            URL url_1 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url_1.openConnection();
            String userCredentials = "username:password";
            String basicAuth = "Basic " + Base64.encodeToString(userCredentials.getBytes(), Base64.NO_WRAP);
            connection.setRequestProperty("Authorization", basicAuth);
            //connection.setRequestProperty("Username", "Raquel");
            //connection.setRequestProperty("Password", "Sloths");
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send Request
            /*OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(urlParameters);
            writer.flush();
            writer.close();*/

            OutputStream output = connection.getOutputStream();
            output.write(urlParameters.getBytes(charset));

            InputStream response = connection.getInputStream();

            //Get Response
            /*
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();*/
            connection.connect();

            Log.i("CookieExample", "AAAA"); //+ cookieList.get(0).toString());

        } catch (MalformedURLException e) {
            Log.i("CookieExample", "BBBB" + e.toString() + "url" + Arrays.toString(stuff[0]));

        } catch (IOException i) {
            Log.i("CookieExample", "CCCC" + i.toString());

        }
        return null;
    }

    protected void OnProgressUpdate(String... what)
    {

    }

}