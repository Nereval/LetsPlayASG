package com.hfad.letsplayasg;

import android.content.Intent;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class LoggingActivity extends Activity {

    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging);
    }

    /*
    onBackPressed - zamyka aplikację po kliknięciu przyciusku powrotu
     */
    @Override
    public void onBackPressed() {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                System.exit(0);
    }

    public void onLoginClick(View view){
        //Jeżeli weryfikacja konta przebiegła pomyślnie
        EditText name = (EditText) findViewById(R.id.username);
        EditText pass = (EditText) findViewById(R.id.password);
        String username = name.getText().toString();
        String password = pass.getText().toString();

        Toast.makeText(this, "Signing up...", Toast.LENGTH_SHORT).show();
        AsyncTask<String, Void, String> task = new LoginConnection(this).execute(username, password, null);


        //Intent intent = new Intent(this, MainActivity.class);
       // intent.putExtra("username", username);
       // startActivity(intent);
    }

    public void onRegisterClick(View view){
    Intent registrationIntent = new Intent(this, RegistrationActivity.class);
    startActivity(registrationIntent);
    }
}
