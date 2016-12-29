package com.example.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class Login extends Activity
{

    TextView errorText;

    EditText userName, password;
    Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (EditText) findViewById(R.id.Username);
        password = (EditText) findViewById(R.id.email);
        loginButton = (Button) findViewById(R.id.sendButton);
        registerButton = (Button)findViewById(R.id.registerButton);

        errorText = (TextView) findViewById(R.id.errorText);
        userName.setText("Tod");
        password.setText("Aa123456");


    }

    public void onClickLogin(View v)
    {
        if(v == loginButton)
        {

            String userNameString =  userName.getText().toString();
            String passwordString = password.getText().toString();
            String output ="100"+ intToString(userNameString.length())+userNameString + intToString(passwordString.length())+passwordString;
            RequestAndAnswer myClient = new RequestAndAnswer(output);

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)
                myClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else
                myClient.execute();

            String answer="";

            do {
                answer = myClient.getResult();
            }
            while(answer.matches(""));


            if(answer.matches("1100"))
            {
                Intent i = new Intent(this,Computer_choosing_selection_activity.class);
                i.putExtra("username",userNameString);
                startActivity(i);
                finish();
            }
            else if(answer.matches("1101"))
                {
                String error = "The username and password do not match";
                errorText.setTextColor(Color.RED);
                errorText.setText(error);
            }
            else
            {
                String error = "Could not connect";
                errorText.setTextColor(Color.RED);
                errorText.setText(error);
            }

        }
        else
        {
            Intent i = new Intent(this,registration.class);
            startActivity(i);
        }
    }


   public String intToString(int num)
{
    String len="";
    if(userName.length()<10)
    {
        len+="0"+Integer.toString(num);
    }
    else
    {
        len = Integer.toString(num);
    }

    return  len;
}


}