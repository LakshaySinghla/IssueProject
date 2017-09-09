package com.carpenoctem.issue;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Lakshay Singhla on 27-Aug-17.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText inputEmail, inputPassword;
    Button login, signup;
    //TextView signupLink;

    private String email,passsword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        login = (Button) findViewById(R.id.btn_login);
        signup = (Button) findViewById(R.id.btn_signup);
        //signupLink = (TextView) findViewById(R.id.signup_link);

        inputEmail.setText("@");
        inputPassword.setText("12345678");
        login.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_login){
            email = inputEmail.getText().toString();
            passsword = inputPassword.getText().toString();
            if(checkEmmail() && checkPassword()) {
                executeLogin();
            }
        }
        else if(view.getId() == R.id.btn_signup){
            Intent i = new Intent(this,SignupActivity.class);
            startActivity(i);
            finish();

        }
    }

    private void executeLogin(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private boolean checkEmmail(){
        if(email.contains("@")){
            return true;
        }
        else{
            inputEmail.setError("Wrong Id");
            return false;
        }

    }
    private boolean checkPassword(){
        if(passsword.length() >= 8){
            return true;
        }
        else {
            inputPassword.setError("Wrong password");
            return false;
        }
    }
}
