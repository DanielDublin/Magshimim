package com.example.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Computer_choosing_selection_activity extends Activity
{

    private Button moveToCreationButton, moveToBrowsingButton;
    private Globals g;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.computer_choosing_selection);

        moveToCreationButton = (Button)findViewById(R.id.createButton);
        moveToBrowsingButton = (Button)findViewById(R.id.browseButton);
        g = Globals.getInstance();

    }


    public void onPrimarySelection(View v)
    {

        if(v == moveToCreationButton)
        {


            Intent i = new Intent(this,Computer_Creation.class);
            i.putExtra("username", this.getIntent().getExtras().get("username").toString());
            startActivity(i);
            finish();

        }
        else if(v == moveToBrowsingButton)
        {
            //do something
        }

    }
}



