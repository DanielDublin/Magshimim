package com.example.myapplication;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Computer_choosing_selection_activity extends Activity
{

    private Button moveToCreationButton, moveToBrowsingButton, moveToProfile, addNewComponent;
    private Globals g;
    private String name;
    private String answer= "";
    private boolean ok =false;
    private AlertDialog.Builder alertDialog;


@Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.computer_choosing_selection);

    moveToCreationButton = (Button) findViewById(R.id.createButton);
    moveToBrowsingButton = (Button) findViewById(R.id.browseButton);
    moveToProfile = (Button) findViewById(R.id.profileButton);
    addNewComponent = (Button)findViewById(R.id.addNewComponentBtn);
    g = Globals.getInstance();

    String status = g.getStatus();

    if(!status.matches("admin"))
    {
        addNewComponent.setEnabled(false);
        addNewComponent.setVisibility(View.GONE);
    }



}




    public void onPrimarySelection(View v)
    {

        if(v == moveToCreationButton)
        {
            alertDialog = new AlertDialog.Builder(this);

            //AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Creating computer");

            // Setting Dialog Message
            alertDialog.setMessage("Enter Your Computer's Name");
            final EditText input = new EditText(this);
            input.setSingleLine(true);
            alertDialog.setView(input);

            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    name = input.getText().toString();
                    if (!name.matches("")) {
                        RequestAndAnswer getName = new RequestAndAnswer();

                        String output = "120";
                        answer = "";
                        output += intToString(name.length());
                        output += name;

                        g.setOutput(output);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                            getName.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        else
                            getName.execute();


                        do {
                            answer = getName.getResult();
                        }
                        while (answer.matches(""));

                        if (!answer.matches("1302")) {
                            ok = true;
                        }


                        dialog.cancel();


                        if (ok) {

                            Intent ia = new Intent(Computer_choosing_selection_activity.this, Computer_Creation.class);
                            ia.putExtra("msg", answer);
                            ia.putExtra("chosenCode", "");
                            startActivity(ia);
                            finish();
                        }


                    }
                }
            });

            alertDialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                }
            });



            alertDialog.show();



        }
        else if(v == moveToBrowsingButton)
        {
            Intent i = new Intent(this,filter_computers.class);
            startActivity(i);
            finish();
        }
        else if(v == moveToProfile)
        {
            Intent i = new Intent(this,profile.class);
            i.putExtra("profile_name", g.getUsername());

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

        return  len;
    }
}



