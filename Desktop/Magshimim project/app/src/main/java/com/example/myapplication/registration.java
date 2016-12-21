package com.example.myapplication;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class registration extends Activity
{
    String IP = "10.0.0.7";
    int PORT = 8888;
   public TextView errorText;
   public EditText password, email,userName,firstName,lastName;
    public  Button register,returnBtn;

    public String errorMessagePassword = "Error! The username must contain at least 1 number, one small and one big letters, and at least 8 letters";

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

    public void onClick(View v)
    {
        if(v ==  register)
        {
            String firstNameString = userName.getText().toString();
            String lastNameString = userName.getText().toString();
            String usernameString = userName.getText().toString();
            String passwordString = password.getText().toString();
            String emailString = email.getText().toString();

            if((!firstNameString.matches("")) && (!lastNameString.matches("")) && (!usernameString.matches("")) &&(!passwordString.matches("")) &&(!emailString.matches(""))) {
                if ((validUsername(userName.getText().toString())) && (validPassword(password.getText().toString())))
                {


                    String output = "160" + intToString(firstNameString.length())+ firstNameString+ intToString(lastNameString.length())+ lastNameString+ intToString(usernameString.length())+ usernameString+ intToString(passwordString.length())+ passwordString+ intToString(emailString.length())+ emailString;
                    RequestAndAnswer ticket = new RequestAndAnswer(IP, PORT, output);
                    ticket.execute();
                    String response = ticket.getResult();
                    switch(response.charAt(3))
                    {
                        case '0':
                        {
                            response = "You have successfuly registered!";
                            errorText.setTextColor(Color.GREEN);
                            break;
                        }
                        case '1':
                        {
                            response = "The username is already taken";
                            errorText.setTextColor(Color.RED);
                            break;
                        }
                        case '2':
                        {
                            response = "The username is already taken";
                            errorText.setTextColor(Color.RED);
                            break;
                        }
                        case '3':
                        {
                            response = "The password is invalid";
                            errorText.setTextColor(Color.RED);
                            break;
                        }
                        case '4':
                        {
                            response = "The username is invalid";
                            errorText.setTextColor(Color.RED);
                            break;
                        }


                    }

                    errorText.setText(response);


                }
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