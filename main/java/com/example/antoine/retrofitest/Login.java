package com.example.antoine.retrofitest;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import static com.example.antoine.retrofitest.R.layout.activity_login;

public class Login extends Activity {

    private String pseudotocheck;
    private String pswdtocheck;
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        setContentView(activity_login);
        info = (TextView)findViewById(R.id.info);
        loginButton = (LoginButton)findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intent = new Intent(Login.this, travel_plan.class);
                startActivity(intent);
            }
            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");
            }
        });

        final EditText pseudoEt = (EditText) findViewById(R.id.Pseudo);
        final EditText passwordEt = (EditText) findViewById(R.id.Password);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("pseudo") && intent.hasExtra("pswd")) {
                pseudotocheck = intent.getStringExtra("pseudo");
                pswdtocheck = intent.getStringExtra("pswd");
            }
        }

        final Button connectButton = (Button) findViewById(R.id.connect);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pseudo = pseudoEt.getText().toString().trim();
                final String password = passwordEt.getText().toString().trim();
                if (pseudo.equals(pseudotocheck) && password.equals(pswdtocheck)) {
                    Intent intent = new Intent(Login.this, travel_plan.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getBaseContext(), "Incorrect Pseudo or password please try again",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        final Button registerButton = (Button) findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
