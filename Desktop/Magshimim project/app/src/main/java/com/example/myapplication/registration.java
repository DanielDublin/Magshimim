package com.example.myapplication;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class registration extends Activity
{

   public TextView errorText;
   public EditText password, email,userName,firstName,lastName;
    public  Button register,returnBtn;

    public String errorMessagePassword = "Error! The password must contain at least 1 number, one small and one big letters, and at least 8 letters";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        register = (Button) findViewById(R.id.registerButton);
        returnBtn  = (Button) findViewById(R.id.returnBtn);

        errorText = (TextView) findViewById(R.id.errorText);

    }

    public void onClickRegister(View v)
    {
        if(v ==  register)
        {
            String firstNameString = firstName.getText().toString();
            String lastNameString = lastName.getText().toString();
            String usernameString = userName.getText().toString();
            String passwordString = password.getText().toString();
            String emailString = email.getText().toString();

            if((!firstNameString.matches("")) && (!lastNameString.matches("")) && (!usernameString.matches("")) &&(!passwordString.matches("")) &&(!emailString.matches("")))
            {
                if ((validUsername(userName.getText().toString())) && (validPassword(password.getText().toString())))
                {


                    String output = "160" + intToString(firstNameString.length())+ firstNameString+ intToString(lastNameString.length())+ lastNameString+ intToString(usernameString.length())+ usernameString+ intToString(passwordString.length())+ passwordString+ intToString(emailString.length())+ emailString;
                    RequestAndAnswer ticket = new RequestAndAnswer(output);
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)
                        ticket.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    else
                        ticket.execute();

                    String response ="";
                    do {
                        response = ticket.getResult();
                    }
                    while(response.matches(""));

                        if (response.matches("1700"))
                        {
                            response = "You have successfuly registered!";
                            errorText.setTextColor(Color.GREEN);

                            firstName.setText("");
                            lastName.setText("");
                            userName.setText("");
                            password.setText("");
                            email.setText("");

                        }
                        else if (response.matches("1701"))
                        {
                            response = "The email is already taken";
                            errorText.setTextColor(Color.RED);

                        }
                        else if (response.matches("1702"))
                        {
                            response = "The username is already taken";
                            errorText.setTextColor(Color.RED);

                        }
                        else if (response.matches("1703"))
                        {
                            response = "The password is invalid";
                            errorText.setTextColor(Color.RED);

                        }
                        else if (response.matches("1704"))
                        {
                            response = "The username is invalid";
                            errorText.setTextColor(Color.RED);

                        }




                    errorText.setText(response);


                }
            }
            else
            {
                String error = "You must fill out all the boxes in order to register!";
                errorText.setTextColor(Color.RED);
                errorText.setText(error);
            }
        }
        else
        {
            finish();
        }

    }

    public String intToString(int num)
    {
        String len="";
        if(num<10)
        {
            len+="0"+Integer.toString(num);
        }
        else
        {
            len = Integer.toString(num);
        }

        return  len;
    }

    public boolean validUsername(String userName)
    {
        if((userName.length() <= 8) && (userName.length()>0))
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
                    errorText.setTextColor(Color.RED);
                    errorText.setText(error);

                    return false;
                }


        }
        else
        {
            String error = "Error! Your username must contain at least 8 characters!";
            errorText.setTextColor(Color.RED);
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
            boolean notEnglish = false;
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
                errorText.setTextColor(Color.RED);
                return false;
            }

        }
        else
        {
            String error = "Your password must contain at least 8 characters";
            errorText.setTextColor(Color.RED);
            errorText.setText(error);
            return false;
        }
        return true;
    }




}