package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FinishBuildingScreen extends Activity {

    RelativeLayout r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_finished_build);

        r = (RelativeLayout)findViewById(R.id.rlFinished);

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
}