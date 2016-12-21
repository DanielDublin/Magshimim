package com.example.myapplication;


import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class registration extends Activity
{

   public TextView errorText;
   public EditText password, email,userName;
    public  Button register;
    public String errorMessageUsername = "Error! All letters must be in English and contain at least 8 characters";
    public String errorMessagePassword = "Error! The username must contain at least 1 number, one small and one big letters, and at least 8 letters";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        register = (Button) findViewById(R.id.registerButton);

        errorText = (TextView) findViewById(R.id.errorText);

    }

    public void onClick(View v)
    {
        if ((validUsername(userName.getText().toString())) && (validPassword(password.getText().toString())))
        {
        //do somethingk
        }

    }


    public boolean validUsername(String userName)
    {
        if(userName.length() >= 8)
        {
            int bigCaps = 0;
            int smallCaps = 0;
            int nums = 0;
            for (int i = 0; i < userName.length(); i++)
            {
                if ((userName.charAt(i) >= 'A') && (userName.charAt(i) <= 'Z')) {
                    bigCaps++;
                } else if ((userName.charAt(i) >= 'a') && (userName.charAt(i) <= 'z')) {
                    smallCaps++;
                } else if ((userName.charAt(i) >= '0') && (userName.charAt(i) <= '9')) {
                    nums++;
                }
            }
                if (bigCaps + smallCaps + nums == userName.length())
                {
                    return true;
                } else
                {
                    String error = "Error! Your username must only contain English letters and numbers only!";
                    errorText.setText(error);

                    return false;
                }


        }
        else
        {
            String error = "Error! Your username must contain at least 8 characters!";
            errorText.setText(error);
            return false;
        }

    }

    public boolean validPassword(String password)
    {
        if(password.length() >= 8)
        {
            int bigCaps =0;
            int smallCaps =0;
            int nums =0;
            for(int i=0; i < password.length();i++)
            {
                if((password.charAt(i) >='A') && (password.charAt(i) <='Z'))
                {
                    bigCaps++;
                }
                else if((password.charAt(i) >='a') && (password.charAt(i) <='z'))
                {
                    smallCaps++;
                }
                else if((password.charAt(i) >='0') && (password.charAt(i) <='9'))
                {
                    nums++;
                }
            }

            if((bigCaps ==0) || (smallCaps == 0) || (nums ==0 ))
            {
                errorText.setText(errorMessagePassword);
                return false;
            }

        }
        else
        {
            String error = "Your password must contain at least 8 characters";
            errorText.setText(error);
            return false;
        }
        return true;
    }




}