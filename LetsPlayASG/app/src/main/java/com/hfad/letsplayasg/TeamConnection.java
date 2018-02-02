package com.hfad.letsplayasg;

import android.app.Activity;
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
 * Created by Piotrek on 01.02.2018.
 */

public class TeamConnection extends AsyncTask<String, Void, String> {

    private String userName;
    private String teamNumber;
    private String teamName;
    private String teamID;
    private Context context;
    private int status = 0 ;
    private String query_result;
    private MapsActivity mapActivity;


    public TeamConnection(MapsActivity mapActivity){
        this.context = mapActivity.getBaseContext();
        this.mapActivity = mapActivity;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... arg0) {
        userName = arg0[0];
        teamNumber = arg0[1];
        teamName = arg0[2];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result = null;

        if(userName != null && teamNumber != null && teamName != null) {
            try {
                data = "?userName=" + URLEncoder.encode(userName, "UTF-8");
                data += "&teamNumber=" + URLEncoder.encode(teamNumber, "UTF-8");
                data += "&teamName=" + URLEncoder.encode(teamName, "UTF-8");

                link = "http://pzasg.j.pl/createteam.php" + data;
                URL url = new URL(link);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                result = bufferedReader.readLine();
                return result;
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        } else if (userName != null && teamNumber != null && teamName == null) {
                try {
                    data = "?userName=" + URLEncoder.encode(userName, "UTF-8");
                    data += "&teamNumber=" + URLEncoder.encode(teamNumber, "UTF-8");

                    link = "http://pzasg.j.pl/jointeam.php" + data;
                    URL url = new URL(link);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    result = bufferedReader.readLine();
                    return result;
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
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
                int length = jsonArr.length();

                switch(length) {
                    case 1:
                        jsonObj = jsonArr.getJSONObject(0);
                        query_result = jsonObj.getString("query_result");
                        break;
                    case 2:
                        jsonObj = jsonArr.getJSONObject(0);
                        query_result = jsonObj.getString("query_result");
                        jsonObj = jsonArr.getJSONObject(1);
                        teamName = jsonObj.getString("teamName");

                }

                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "Team created!", Toast.LENGTH_SHORT).show();
                    status = 1;
                } else if (query_result.equals("JOINED")) {
                    Toast.makeText(context, "Joined team!", Toast.LENGTH_SHORT).show();
                    status = 2;
                }
                  else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Login failed.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }

        if (status == 1){
            mapActivity.setTeamName(teamName);
        } else if (status == 2) {
            mapActivity.setTeamName(teamName);
        }
    }
}