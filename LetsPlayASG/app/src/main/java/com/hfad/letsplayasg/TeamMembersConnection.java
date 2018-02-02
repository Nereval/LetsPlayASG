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
import java.util.LinkedList;

/**
 * Created by Piotrek on 02.02.2018.
 */

public class TeamMembersConnection extends AsyncTask<String, Void, String> {

    private String teamNumber;
    private MapsActivity mapsActivity;
    private Context context;
    private String username;
    private String userID;
    private String id;
    private int status = 0 ;
    private String query_result;
    private LinkedList<TeamMemberCoords> teamCoords = new LinkedList<>();


    public TeamMembersConnection(MapsActivity mapsActivity) {
        this.mapsActivity = mapsActivity;
        this.context = mapsActivity.getBaseContext();
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... arg0) {
        userID = arg0[0];
        teamNumber = arg0[1];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result = null;

        if(userID != null && teamNumber != null) {
            try {
                data = "?userID=" + URLEncoder.encode(userID, "UTF-8");
                data += "&teamNumber=" + URLEncoder.encode(teamNumber, "UTF-8");

                link = "http://pzasg.j.pl/getteamcoords.php" + data;
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

        if (jsonStr != null) {

            JSONArray jsonArr;
            try {
                jsonArr = new JSONArray(jsonStr);
                for(int i = 0; i < jsonArr.length(); i++){
                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                    String userName = jsonObj.getString("userName");
                    String x = jsonObj.getString("x");
                    String y = jsonObj.getString("y");
                    teamCoords.add(new TeamMemberCoords(userName, x, y));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }

        if (status == 0){

        } else if (status == 1) {
            mapsActivity.setTeamCoordinates(teamCoords);
        } else if (status == 4){

        }
    }
}

