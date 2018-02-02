package com.hfad.letsplayasg;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTeamFragment extends Fragment {

    private View viewAll;
    private int teamNumber;
    private String userName;
    private String userID;

    public CreateTeamFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewAll = inflater.inflate(R.layout.fragment_create_team,
                container, false);

        Random rand = new Random();
        teamNumber = rand.nextInt()%99000+10000;
        if(teamNumber < 0)  teamNumber *= (-1);
        String text = String.valueOf(teamNumber);
        // Sprawdzenie czy zajęty numer
        TextView textNumber = (TextView) viewAll.findViewById(R.id.teamNumber);
        textNumber.setText(text);

        Button buttonCreate = (Button) viewAll.findViewById(R.id.createTeam);
        buttonCreate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //((MapsActivity)getActivity()).createTeam(teamNumber, getText());
                TextView teamName = (TextView) viewAll.findViewById(R.id.teamName);
                String name = teamName.getText().toString();

                userID = ((MapsActivity)getActivity()).getUserID();

                AsyncTask<String, Void, String> task = new TeamConnection(((MapsActivity)getActivity())).execute(userID, Integer.toString(teamNumber), name);
            }
        });

        return viewAll;
    }

    public String getText(){
        EditText editText = (EditText) viewAll.findViewById(R.id.teamName);
        String text = (String) editText.getText().toString();
        return text;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

}
