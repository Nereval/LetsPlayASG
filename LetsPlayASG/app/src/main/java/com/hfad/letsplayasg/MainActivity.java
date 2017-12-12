package com.hfad.letsplayasg;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends Activity {

    private boolean logged = false;
    private String username;
    private int teamNumber;
    private String team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null){
            logged = savedInstanceState.getBoolean("logged");
            username = savedInstanceState.getString("username");
            team = savedInstanceState.getString("team");
            teamNumber = savedInstanceState.getInt("teamNumber");
        }
    }

    @Override
    protected void onStart(){
        super.onStart();

        if(getIntent() != null) {
            Intent intent = getIntent();
            this.username = intent.getStringExtra("username");
            if(username != null)    logged = true;
            try {
                this.team = intent.getStringExtra("team");
                this.teamNumber = intent.getIntExtra("teamNumber", 0);
            } catch(Exception x){
                team = null;
                teamNumber = 0;
            }
        }

        if(logged){
            TextView username = (TextView) findViewById(R.id.username);
            username.setText(this.username);
            TextView teamName = (TextView) findViewById(R.id.teamName);
            teamName.setText(this.team);
        } else {
            Intent intent = new Intent(this, LoggingActivity.class);
            startActivity(intent);
        }

        if(team == null) {
            FindTeamFragment findTeamFragment = new FindTeamFragment();
            findTeamFragment.setUsername(username);
            replaceFragment(findTeamFragment);
        } else{
            InTeamFragment inTeamFragment = new InTeamFragment();
            replaceFragment(inTeamFragment);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("logged", logged);
        savedInstanceState.putString("username", username);
        savedInstanceState.putString("team", team);
        savedInstanceState.putInt("teamNumber", teamNumber);
    }

    public void leaveTeam(){
        teamNumber = 0;
        team = null;

        TextView teamName = (TextView) findViewById(R.id.teamName);
        teamName.setText(this.team);

        FindTeamFragment findTeamFragment = new FindTeamFragment();
        findTeamFragment.setUsername(username);
        replaceFragment(findTeamFragment);
    }

    public void createTeam(int teamNumber, String team){
        this.teamNumber = teamNumber;
        this.team = team;

        TextView teamName = (TextView) findViewById(R.id.teamName);
        teamName.setText(this.team);

        InTeamFragment inTeamFragment = new InTeamFragment();
        replaceFragment(inTeamFragment);
    }

    public void joinTeam(int teamNumber){
        this.teamNumber = teamNumber;
        //wyszukuje nazwę teamu

        TextView teamName = (TextView) findViewById(R.id.teamName);
        teamName.setText("in team");

        InTeamFragment inTeamFragment = new InTeamFragment();
        replaceFragment(inTeamFragment);
    }

    public void createTeamFragment(){
        CreateTeamFragment createTeamFragment = new CreateTeamFragment();
        replaceFragment(createTeamFragment);
    }

    public void replaceFragment(Fragment fragment){
        View fragmentContainer = findViewById(R.id.fragmentContainer);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment);
        ft.commit();
    }

    public void replaceFragmentWithBackstack(Fragment fragment){
        View fragmentContainer = findViewById(R.id.fragmentContainer);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}
