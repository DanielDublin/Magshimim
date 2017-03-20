package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FinishBuildingScreen extends Activity {

    private RelativeLayout r;
    private Button  saveBtn, cancleBtn, saveAndFinish;
    private Globals g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_finished_build);


        saveBtn = (Button)findViewById(R.id.saveAndExitBtn);
        cancleBtn = (Button)findViewById(R.id.cancleBtn);
        saveAndFinish = (Button)findViewById(R.id.saveAndFinish);
        r = (RelativeLayout)findViewById(R.id.rlFinished);

        g=Globals.getInstance();

        Bundle itemNames = new Bundle();
        Bundle itemCodes = new Bundle();
        int y = 900;
        int x = 50;

        String selectedItems[] = getIntent().getExtras().getStringArray("itemNames"); // itemNames = (Bundle)this.getIntent().getExtras().get("itemNames");
        String codes[] = getIntent().getExtras().getStringArray("itemCodes"); //  itemCodes = (Bundle)this.getIntent().getExtras().get("itemCodes");
        String types[] = getIntent().getExtras().getStringArray("itemTypes");
        for (int i =selectedItems.length-1 ; i >=0 ; i--)
        {
            String line = new StringBuilder().append("Part type: ").append(types[i]).append("    -  Part name: ").append(selectedItems[i]).append("    -  Part code: ").append(codes[i]).toString();
            TextView tv = new TextView(this);
            tv.setText(line);
            tv.setTextSize(15);
            tv.setWidth(1000);
            tv.setHeight(200);
            tv.setX(x);
            tv.setY(y);
            tv.setTextColor(Color.BLACK);
            y = y - 150;

            r.addView(tv);

        }


    }

    public void onClickFinishedShowing(View v)
    {
        if(v == saveBtn) {

            RequestAndAnswer end = new RequestAndAnswer();

            String output = "220";
            String answer = "";

            g.setOutput(output);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                end.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else
                end.execute();


            do {
                answer = end.getResult();
            }
            while (answer.matches(""));


            if(!answer.matches("2300"))
            {


                Toast.makeText(this, "Saving failed", Toast.LENGTH_SHORT).show();
            }
        }
        else if(v == cancleBtn)
        {
            RequestAndAnswer end = new RequestAndAnswer();

            String output = "240";
            String answer = "";

            g.setOutput(output);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                end.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else
                end.execute();


            do {
                answer = end.getResult();
            }
            while (answer.matches(""));

            if(answer.matches("2501")) {

                Toast.makeText(this, "Deleting failed", Toast.LENGTH_SHORT).show();
            }


        }
        else if(v == saveAndFinish) {


        RequestAndAnswer end = new RequestAndAnswer();

        String output = "280";
        String answer = "";

        g.setOutput(output);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            end.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            end.execute();


        do {
            answer = end.getResult();
        }
        while (answer.matches(""));

        String flags = answer.substring(0, 4);

        if(!flags.matches("2900"))
        {
            Toast.makeText(this, "Saving failed", Toast.LENGTH_SHORT).show();
        }

    }
        Intent i = new Intent(this, Computer_choosing_selection_activity.class);
        startActivity(i);
        finish();



        }



}