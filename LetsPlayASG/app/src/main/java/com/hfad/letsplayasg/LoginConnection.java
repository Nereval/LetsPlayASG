package com.hfad.letsplayasg;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

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

    private Context context;
    private int status = 0 ;

    public LoginConnection(Context context) {

        this.context = context;
    }

    protected void onPreExecute() {
        //super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... arg0) {
        String username = arg0[0];
        String password = arg0[1];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

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
    }

    @Override
    protected void onPostExecute(String result) {
        String jsonStr = result;
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "Login successful.", Toast.LENGTH_SHORT).show();
                    status = 1;

                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Login failed.", Toast.LENGTH_SHORT).show();
                    status = 2;
                } else {
                    Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                    status = 3;
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
            context.startActivity(mapIntent);
        } else if (status == 2){
            // do something
        }
    }
}
