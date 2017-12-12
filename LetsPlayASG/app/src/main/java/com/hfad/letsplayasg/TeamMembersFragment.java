package com.hfad.letsplayasg;


import android.app.ListFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * A simple {@link ListFragment} subclass.
 */
public class TeamMembersFragment extends ListFragment {


    public TeamMembersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] names = new String[1];
        names[0] = "Jestem przykładowym członkiem drużyny";

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1,
                names);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
