package com.example.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Computer_choosing_selection_activity extends Activity
{

    private Button moveToCreationButton,moveToBrowsingButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.computer_choosing_selection);

        moveToCreationButton = (Button)findViewById(R.id.createButton);
        moveToBrowsingButton = (Button)findViewById(R.id.browseButton);


    }


    public void onPrimarySelection(View v)
    {

        if(v == moveToCreationButton)
        {



            RequestAndAnswer request = new RequestAndAnswer("120");
            String answer = request.getResult();
            if(!answer.matches("1301"))
            {
                answer  = answer.substring(4);

                for(int i = 0; i < 10;i++)
                {
                    int length = amount(answer);
                    answer = answer.substring(2);

                    for (int j = 0; j < length; j++)
                    {


                    }
                }

            }

            Intent i = new Intent(this,Computer_Creation.class);
            startActivity(i);
            finish();

        }
        else if(v == moveToBrowsingButton)
        {
            //do something
        }

    }


    public int amount(String str)
    {
        String length ="";
        length+=str.charAt(0) + str.charAt(1);

        return Integer.parseInt(length);
    }

}



