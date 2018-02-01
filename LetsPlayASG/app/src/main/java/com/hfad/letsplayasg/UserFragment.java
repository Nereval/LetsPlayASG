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
        if(teamNumber != 0){
            View view = getView();
            TextView team = (TextView) view.findViewById(R.id.teamNumber);
            team.setText(Integer.toString(teamNumber));
        }
    }

    public void setTeamNumber(int teamNumber){
        this.teamNumber = teamNumber;
    }

}
