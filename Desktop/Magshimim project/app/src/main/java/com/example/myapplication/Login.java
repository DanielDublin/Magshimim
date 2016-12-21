package com.example.myapplication;


import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class Login extends Activity
{

    TextView response;
    String IP = "10.0.0.7";
    int PORT = 8888;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAddress = (EditText) findViewById(R.id.Username);
        editTextPort = (EditText) findViewById(R.id.email);
        buttonConnect = (Button) findViewById(R.id.sendButton);

        response = (TextView) findViewById(R.id.errorText);

    }

    public void onClick(View v)
    {
        //RequestAndAnswer myClient = new RequestAndAnswer(IP, PORT, response);
        myClient.execute();
        String answer = myClient.getResult();
        if(answer.charAt(0) == 'n')
        {
            response.setText(myClient.getResult());
        }


    }




}