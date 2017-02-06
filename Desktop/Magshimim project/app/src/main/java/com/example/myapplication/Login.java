package com.example.myapplication;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class Login extends Activity implements AlertDialog.OnClickListener
{

    TextView errorText;
    String userNameString ="";
    EditText userName, password;
    Button loginButton, registerButton;
    public static Globals g;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (EditText) findViewById(R.id.Username);
        password = (EditText) findViewById(R.id.email);
        loginButton = (Button) findViewById(R.id.sendButton);
        registerButton = (Button)findViewById(R.id.registerButton);
        g  = Globals.getInstance();

        errorText = (TextView) findViewById(R.id.errorText);

        userName.setText("Aa123456");
        password.setText("Aa123456");


    }


    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        if(which == dialog.BUTTON_POSITIVE)
        {


            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            dialog.cancel();
            System.exit(0);

        }
        else if(which == dialog.BUTTON_NEGATIVE)
        {
            dialog.cancel();
        }


    }


    public void onClickLogin(View v)
    {
        if(v == loginButton)
        {

             
			userNameString = userName.getText().toString();
			String passwordString = password.getText().toString();

            if ((!userNameString.matches("")) && (!passwordString.matches("")))
            {
                String output = "100" + intToString(userNameString.length()) + userNameString + intToString(passwordString.length()) + passwordString;
                g.setOutput(output);

                RequestAndAnswer myClient = new RequestAndAnswer();


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)  //Protection from the ex mistakes
                    myClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                else
                    myClient.execute();

                String answer = "";

                do {
                    answer = myClient.getResult();
                }
                while (answer.matches(""));


                if (answer.matches("1100")) {  //Match
                    Intent i = new Intent(this, Computer_choosing_selection_activity.class);
                    i.putExtra("username", userNameString);
                    startActivity(i);
                    finish();
                } else if (answer.matches("1101")) {  //No matck
                    String error = "The username and password do not match";
                    errorText.setTextColor(Color.RED);
                    errorText.setText(error);
                } else 
				{                                //Mistakes
                    String error = "Could not connect";
                    errorText.setTextColor(Color.RED);
                    errorText.setText(error);
                }
            }
            else
            {
                String error = "You must fill all the boxes in order to login";
                errorText.setTextColor(Color.RED);
                errorText.setText(error);
            }

        }
        else
            { //Registration
                Intent i = new Intent(this, registration.class);
                startActivity(i);
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

    return len;
}



    @Override
    public void onBackPressed()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure that you want to exit?");
        builder.setPositiveButton("Yes",this);
        builder.setNegativeButton("No",this);

        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();


    }



}