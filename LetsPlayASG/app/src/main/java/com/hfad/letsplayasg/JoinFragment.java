package com.hfad.letsplayasg;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class JoinFragment extends Fragment {

    private View viewAll;

    public JoinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewAll = inflater.inflate(R.layout.fragment_join,
                container, false);

        Button buttonJoin = (Button) viewAll.findViewById(R.id.joinButton);
        buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText number = (EditText) viewAll.findViewById(R.id.joinTeam);
                ((MapsActivity)getActivity()).joinTeam(Integer.parseInt(number.getText().toString()));
            }
        });

        return viewAll;
    }

}
