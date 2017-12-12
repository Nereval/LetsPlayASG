package com.hfad.letsplayasg;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindTeamFragment extends Fragment {

    private String username;

    public FindTeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_find_team,
                container, false);
        Button buttonCreate = (Button) view.findViewById(R.id.createTeam);
        buttonCreate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getActivity()).createTeamFragment();
            }
        });
        Button buttonJoin = (Button) view.findViewById(R.id.joinTeam);
        buttonJoin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                JoinTeamFragment joinTeamFragment = new JoinTeamFragment();
                ((MainActivity)getActivity()).replaceFragment(joinTeamFragment);
            }
        });

        return view;
    }

    public void setUsername(String username){
        this.username = username;
    }
}
