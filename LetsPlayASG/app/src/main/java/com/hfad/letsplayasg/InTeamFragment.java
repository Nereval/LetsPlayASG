package com.hfad.letsplayasg;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class InTeamFragment extends Fragment {


    public InTeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_in_team,
                container, false);

        Button buttonLeave = (Button) view.findViewById(R.id.leaveTeam);
        buttonLeave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getActivity()).leaveTeam();
            }
        });

        Button buttonTeamMembers = (Button) view.findViewById(R.id.teamMembers);
        buttonTeamMembers.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TeamMembersFragment teamMembersFragment = new TeamMembersFragment();
                ((MainActivity)getActivity()).replaceFragmentWithBackstack(teamMembersFragment);
            }
        });

        return view;
    }

}
