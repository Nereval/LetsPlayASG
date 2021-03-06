package com.hfad.letsplayasg;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Piotrek on 30.01.2018.
 */

public class LoginConnection extends AsyncTask<String, Void, String> {

    private String username;
    private String password;
    private String id;
    private Context context;
    private int status = 0 ;
    private String query_result;

    public LoginConnection(Context context) {

        this.context = context;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... arg0) {
        username = arg0[0];
        password = arg0[1];
        id = arg0[2];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result = null;

        if(username != null && password != null && id == null) {
            try {
                data = "?username=" + URLEncoder.encode(username, "UTF-8");
                data += "&password=" + URLEncoder.encode(password, "UTF-8");

                link = "http://pzasg.j.pl/login.php" + data;
                URL url = new URL(link);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                result = bufferedReader.readLine();
                return result;
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        } else if(id != null){
                try {
                    data = "?id=" + URLEncoder.encode(id, "UTF-8");

                    link = "http://pzasg.j.pl/logout.php" + data;
                    URL url = new URL(link);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    result = bufferedReader.readLine();

                    return result;
                } catch (Exception e){
                    return new String("Exception: " + e.getMessage());
                }
           // }

        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        String jsonStr = result;

        JSONObject jsonObj;
        if (jsonStr != null) {
            try {
                JSONArray jsonArr = new JSONArray(jsonStr);
                int jsonLength = jsonArr.length();

                switch(jsonLength){
                    case 1:
                        jsonObj = jsonArr.getJSONObject(0);
                        query_result = jsonObj.getString("query_result");
                        break;

                    case 2:
                        jsonObj = jsonArr.getJSONObject(0);
                        query_result = jsonObj.getString("query_result");
                        jsonObj = jsonArr.getJSONObject(1);
                        id = jsonObj.getString("id");
                        break;
                }

                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "Login successful.", Toast.LENGTH_SHORT).show();
                    status = 1;


                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Login failed.", Toast.LENGTH_SHORT).show();
                    //status = 2;
                } else if (query_result.equals("LOGOUT")) {
                    Toast.makeText(context, "You're logged out!", Toast.LENGTH_SHORT).show();
                    status = 4;
                } else {
                    Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                    //status = 3;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }

        if (status == 0){
            //do something
        } else if (status == 1) {

            Intent mapIntent = new Intent(context, MapsActivity.class);
            mapIntent.putExtra("username", username);
            mapIntent.putExtra("id", id);
            context.startActivity(mapIntent);
        } else if (status == 4){
            Intent loginIntent = new Intent(context, LoggingActivity.class);
            context.startActivity(loginIntent);
        }
    }
}
