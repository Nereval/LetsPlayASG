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
public class JoinTeamFragment extends Fragment {

    private View viewAll;

    public JoinTeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewAll = inflater.inflate(R.layout.fragment_join_team,
                container, false);

        Button button = (Button) viewAll.findViewById(R.id.joinTeam);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).joinTeam(getInt());
            }
        });

        return viewAll;
    }

    public int getInt(){
        EditText editText = (EditText) viewAll.findViewById(R.id.number);
        int text = Integer.parseInt(editText.getText().toString());
        return text;
    }
}
