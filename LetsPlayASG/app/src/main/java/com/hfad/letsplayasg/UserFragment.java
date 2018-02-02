package com.hfad.letsplayasg;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {
    private int teamNumber = 0;
    private String userName;
    private String teamName;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        View view = getView();
        if(teamNumber != 0){
            TextView team = (TextView) view.findViewById(R.id.teamNumber);
            team.setText(Integer.toString(teamNumber));
        }
        if(userName != null){
            TextView user = (TextView) view.findViewById(R.id.username);
            user.setText(userName);
        }
        if(teamName != null){
            TextView team = (TextView) view.findViewById(R.id.teamName);
            team.setText(teamName);
        }
    }

    public void setTeamNumber(int teamNumber){
        this.teamNumber = teamNumber;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTeamName(String teamName){
        this.teamName = teamName;
    }

    public int getTeamNumber(){
        return teamNumber;
    }

    public String getUserName(){
        return userName;
    }

    public String getTeamName(){
        return teamName;
    }

}
