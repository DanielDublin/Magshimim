package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Computer_Creation extends Activity {

    private Button currButton;

    private MenuItem [] chosenItems = new MenuItem [10];
    private Button processor,motherboard,ram,cpu,cooling,opticalDrive,powerSupply,graphicsCard,audioCard,primaryHardDisk,finish;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creating_new_computer);

        currButton = (Button)findViewById(R.id.processorButton);
        Intent thisIntent = this.getIntent();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
       if(currButton == processor)
       {
           chosenItems[0] = item;
         //  RequestAndAnswer chosenItemRequest = new RequestAndAnswer();

       }
       else if(currButton == motherboard)
       {
           chosenItems[1] = item;
       }
       else if(currButton == cooling)
       {
           chosenItems[2] = item;
       }
       else if(currButton == ram)
       {
           chosenItems[3] = item;
       }
       else if(currButton == opticalDrive)
       {
           chosenItems[4] = item;
       }
       else if(currButton == primaryHardDisk)
       {
           chosenItems[5] = item;
       }
       else if(currButton == audioCard)
       {
           chosenItems[6] = item;
       }
       else if(currButton == graphicsCard)
       {
           chosenItems[7] = item;
       }
       else if(currButton == powerSupply)
       {
           chosenItems[8] = item;
       }
       else if(currButton == cpu)
       {
           chosenItems[9] = item;
       }
        return true;

    }



    public void onClickButton(View v)
    {
        if(v != currButton)
        {



        }


    }


    public void onClickFinishBuilding(View v)
    {
        int ok = 1;
        for (int i =0; i < 10;i++)
        {
            if(chosenItems[i] == null)
            {
                ok =0;
            }
        }

        if(ok == 1)
        {
            Intent i = new Intent(this,FinishBuildingScreen.class);
            i.putExtra("items",chosenItems);
            startActivity(i);
            finish();
        }

    }

}