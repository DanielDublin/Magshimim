package com.example.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class Login extends Activity
{

    TextView errorText;
    String IP = "10.0.0.7";
    int PORT = 8888;
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

    }

    public void onClick(View v)
    {
        if(v == loginButton)
        {

            String userNameString =  userName.getText().toString();
            String passwordString = password.getText().toString();
            String output ="100"+ intToString(userNameString.length())+userNameString + intToString(passwordString.length())+passwordString;
            RequestAndAnswer myClient = new RequestAndAnswer(IP, PORT, output);
            myClient.execute();
            String answer = myClient.getResult();

            if(answer.charAt(3) == '0')
            {
                Intent i = new Intent(this,MainScreen.class);
                i.putExtra("username",userNameString);
                startActivity(i);
                finish();
            }
            else
            {
                String error = "The username and password do not match";
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