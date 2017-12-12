package com.hfad.letsplayasg;


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

    private int teamNumber;
    private View viewAll;

    public CreateTeamFragment() {
        // Required empty public constructor
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
                ((MainActivity)getActivity()).createTeam(teamNumber, getText());
            }
        });

        return viewAll;
    }

    public String getText(){
        EditText editText = (EditText) viewAll.findViewById(R.id.teamName);
        String text = (String) editText.getText().toString();
        return text;
    }

}
