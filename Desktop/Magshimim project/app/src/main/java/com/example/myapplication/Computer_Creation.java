package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Computer_Creation extends Activity {


    private Button currButton;
    private Globals g;
    private HorizontalScrollView p;
    private LinearLayout l;
    public static int currBtn = 0;
    private String [] codeForTypes;
    private part [] selectedParts;
    private Button [] buttonTypes;
    private part [] parts;
    private List<String> parts_list;
    private ListView lv;
    private  ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creating_new_computer);

        g = Globals.getInstance();
        p = (HorizontalScrollView)findViewById(R.id.horizontalScrollView);
        l = (LinearLayout)findViewById(R.id.child);
         lv = (ListView) findViewById(R.id.lv);
        parts_list = new ArrayList<String>();

        String username = this.getIntent().getExtras().get("username").toString();



        //set onClickListener for list view
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                boolean found = false;
                for (int i =0; i < selectedParts.length; i++)
                {
                    TextView tv = ((TextView) view);
                    String textInView = tv.getText().toString();
                    if(selectedParts[i].getName().matches(textInView))
                    {
                        found = true;
                    }
                }
                String item = ((TextView) view).getText().toString();
                int spot = findPartByName(item);


                if ((!found) && (spot != -1))
                {


                    RequestAndAnswer select = new RequestAndAnswer();

                    String output = "180";
                    output += parts[spot].getCode();

                    g.setOutput(output);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                        select.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    else
                        select.execute();

                    String answer = "";

                    do {
                        answer = select.getResult();
                    }
                    while (answer.matches(""));



                    if ((answer.matches("1900") && (spot != -1))) {

                        view.setBackgroundColor(Color.BLUE);
                        selectedParts[currBtn] = parts[spot];
                        arrayAdapter.notifyDataSetChanged();
                    } else if (answer.matches("1901")) {
                        Toast.makeText(getBaseContext(), "The item's code is not valid", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getBaseContext(), "unknownProblem", Toast.LENGTH_LONG).show();
                    }
                }
                else if((found) && (spot != -1))
                {


                    RequestAndAnswer select = new RequestAndAnswer();

                    String output = "200";
                    output += parts[spot].getCode();



                    g.setOutput(output);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                        select.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    else
                        select.execute();

                    String answer = "";

                    do {
                        answer = select.getResult();
                    }
                    while (answer.matches(""));

                    if (answer.matches("2000"))
                    {
                        //removing the item
                        deSelectPart(view);
                        arrayAdapter.notifyDataSetChanged();

                    } else if (answer.matches("2001")) {
                        Toast.makeText(getBaseContext(), "The item's code is not valid", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getBaseContext(), "unknownProblem", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });





        String output = "120";
        output+= intToString(username.length()) + username;
        g.setOutput(output);
         RequestAndAnswer request = new RequestAndAnswer();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            request.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            request.execute();

        String answer = "";

        do {
            answer = request.getResult();
        }
        while (answer.matches(""));
        request.cancel(true);

        boolean check = request.isCancelled();

        String msgHodler = answer;
        String flag = answer.substring(0,4);

        if(!flag.matches("1301")) {
            answer = answer.substring(4);
            String size = answer.substring(0, 2);

            int numOfTypes = amount(size);
            buttonTypes = new Button[numOfTypes];
            codeForTypes = new String[numOfTypes];
            selectedParts = new part[numOfTypes];
            parts = new part[numOfTypes];
            answer = answer.substring(2);

            //initializing types
            for (int i = 0; i < numOfTypes; i++) {
                size = "";
                size += answer.substring(0, 2);
                int nameTypeLength = amount(size);
                answer = answer.substring(2);

                buttonTypes[i] = new Button(this);
                buttonTypes[i].setText(answer.substring(0, nameTypeLength));
                buttonTypes[i].setWidth(145);
                buttonTypes[i].setBackgroundColor(Color.GRAY);
                buttonTypes[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickButtonType(v);
                    }
                });
                l.addView(buttonTypes[i]);


                answer = answer.substring(nameTypeLength);
                codeForTypes[i] = (answer.substring(0, 3));
                answer = answer.substring(3);

            }

            currBtn = 0;
            currButton = buttonTypes[currBtn];
            currButton.setBackgroundColor(Color.BLUE);

            createListOfPartsForTypeAndShowIt();

        }

    }




    public void createListOfPartsForTypeAndShowIt()
    {


            RequestAndAnswer lists = new RequestAndAnswer();
            g.setOutput("140"+codeForTypes[currBtn]);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                lists.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else
                lists.execute();


            String answer = "";
            do {
                answer = lists.getResult();
            }
            while (answer.matches(""));


            answer  = answer.substring(4);

            String size="";
            size += answer.substring(0,2);
            int amountOfParts = amount(size);
            parts = null;
            parts = new part[amountOfParts];
            answer  = answer.substring(2);

            for (int j = 0; j < amountOfParts; j++)
            {
                size ="";
                size += answer.substring(0,2);
                int sizeName = amount(size);
                answer  = answer.substring(2);
                String partName = (answer.substring(0, sizeName));
                answer = answer.substring(sizeName);

                String partCode = answer.substring(0,6);
                answer = answer.substring(6);

                parts[j] = new part(partName ,partCode);

            }

        showList();



}






    public void showList()
    {
        if(!parts_list.isEmpty())
        {
            parts_list.clear();
        }

        for (int i=0; i < parts.length;i++)
        {
            parts_list.add(parts[i].getName());
        }

         arrayAdapter = new ArrayAdapter<String>(this, R.layout.item, parts_list);
        lv.setAdapter(arrayAdapter);



    }



    public void onClickButtonType(View v)
    {

        if(v != currButton)
        {
            currButton.setBackgroundColor(Color.GRAY);
                currButton =(Button)v;
                for (int i=0; i < buttonTypes.length; i++)
                {
                    if(buttonTypes[i] == v)
                    {
                        currBtn = i;
                    }
                }

            v.setBackgroundColor(Color.BLUE);
                showList();

        }



    }


    // delete selected part

    public void deSelectPart(View item)
    {

        ((TextView) item).setBackgroundColor(Color.GRAY);

        for (int i = 0; i < lv.getCount(); i++) {

            if (lv.getAdapter().getView(i, null, null) == item) {

                selectedParts[i] = null;
            }
        }





    }



    public void onClickFinishBuilding(View v)
    {
        int ok = 1;

        for (int i =0; i < 10;i++)
        {
            if(selectedParts[i] == null)
            {
                ok =0;
            }
        }


        if(ok == 1)
        {
            Intent i = new Intent(this,FinishBuildingScreen.class);


            String [] selected = new String[buttonTypes.length];
            String [] codes = new String[buttonTypes.length];
            String [] types = new String[buttonTypes.length];

            /*  for checking buttons and finished building screen
            selected[0] = "Part 1";
            selected[1] = "Part 2";
            selected[2] = "Part 3";
            selected[3] = "Part 4";
            selected[4] = "Part 5";
            codes[0] = "Code 1";
            codes[1] = "Code 2";
            codes[2] = "Code 3";
            codes[3] = "Code 4";
            codes[4] = "Code 5";
            */

            for (int j =0; j < buttonTypes.length;j++)
            {
                selected[j] = selectedParts[j].getName();
                codes[j] = selectedParts[j].getCode();
                types[j] = buttonTypes[j].getText().toString();
            }




            Bundle b1=new Bundle();
            Bundle b2 = new Bundle();
            Bundle b3 = new Bundle();
            b1.putStringArray("itemNames",selected);
            b2.putStringArray("itemCodes",codes);
            b3.putStringArray("itemTypes",types);
            i.putExtras(b1);
            i.putExtras(b2);
            i.putExtras(b3);

            startActivity(i);
            finish();
        }

    }

    public int amount(String str)
    {

        return Integer.parseInt(str);
    }

    public int findPartByName(String name)
    {

        for (int i=0; i < parts.length;i++)
        {
            if(parts[i].getName().equals(name))
            {

                return i;
            }

        }
        return -1;
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