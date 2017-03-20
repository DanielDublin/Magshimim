package com.example.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Computer_Creation extends Activity {


    private Button currButton, finishBuildBtn, saveAndExitBtn, showInfo;
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
    private boolean resetList = false;
    private  ArrayAdapter<String> arrayAdapter;
    private AlertDialog.Builder alertDialog;
    private String name="";
    private int selected =0;
    private View lastView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creating_new_computer);

        g = Globals.getInstance();
        p = (HorizontalScrollView)findViewById(R.id.horizontalScrollView);
        l = (LinearLayout)findViewById(R.id.child);
        lv = (ListView) findViewById(R.id.lv);
        finishBuildBtn = (Button)findViewById(R.id.finishButton);
        saveAndExitBtn = (Button)findViewById(R.id.saveAndQuitBtn);
        showInfo = (Button)findViewById(R.id.infoBtn);

        parts_list = new ArrayList<String>();

        String username = g.getUsername();
        String chosenCode =this.getIntent().getExtras().get("chosenCode").toString();


        //set onClickListener for list view
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                String item = "", preSelectedItem = "";
                boolean foundNewPart = false, foundOldPart = false, addToSelected = false;
                int oldSpot = -1;


                for (int i =0; i < parts.length; i++)
                {
                    TextView tv = ((TextView) view);
                    String textInView = tv.getText().toString();
                    textInView = textInView.substring(("Part name:    ").length());
                    textInView = textInView.substring(0, parts[i].getName().length());


                    if(parts[i].getName().matches(textInView))
                    {
                        foundNewPart = true;
                        item = textInView;

                    }


                    if ((selectedParts[currBtn] != null) && (oldSpot == -1))
                    {
                        if(selectedParts[currBtn].getName().matches(parts[i].getName()))
                        {
                            oldSpot = i;
                        }
                    }

                    if((selectedParts[currBtn] != null) && (!item.matches("")) && (!foundOldPart))
                    {
                        if(selectedParts[currBtn].getName().matches(item)) {
                            foundOldPart = true;
                            preSelectedItem = selectedParts[currBtn].getName();
                        }
                    }

                }






                if(foundNewPart && foundOldPart)
                {
                    if(!item.matches(preSelectedItem))
                    {
                        addToSelected = true;
                    }

                }
                else if(item.matches(preSelectedItem))
                {
                    addToSelected = false;
                }
                else
                {
                    addToSelected = true;
                }





                int spot = findPartByName(item);





                if ((addToSelected) && (spot != -1))
                {

                    if(oldSpot != -1)
                    {
                        //theres already a chosen part
                        send200(oldSpot, view);
                        lastView.setBackgroundColor(Color.WHITE);
                        lastView = null;
                    }
                    else
                    {
                        //first time choosing
                        lastView = view;
                        view.setBackgroundColor(Color.CYAN);
                    }

                    //adding the part
                    send180(spot, view);

                }
                else if((!addToSelected) && (spot != -1))
                {
                    lastView.setBackgroundColor(Color.WHITE);
                    lastView =null;
                    send200(oldSpot, view);
                }

                arrayAdapter.notifyDataSetChanged();

            }
        });



        String answer = this.getIntent().getExtras().get("msg").toString();
        String msgHodler = answer;
        String flag = answer.substring(0,4);





        if(!answer.matches("1301")) {
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
                buttonTypes[i].setBackgroundColor(Color.WHITE);
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
            currButton.setBackgroundColor(Color.CYAN);


            if(!chosenCode.matches("")) { //***************************************************************************************************************************************


                RequestAndAnswer editTicket = new RequestAndAnswer();
                String output = "460"+chosenCode;
                g.setOutput(output);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)  //Protection from the ex mistakes
                    editTicket.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                else
                    editTicket.execute();

                answer = "";

                do {
                    answer = editTicket.getResult();
                }
                while (answer.matches(""));

                String flags = answer.substring(0,4);
                answer = answer.substring(4);
                if(flags.matches("4700"))
                {

                     size = answer.substring(0,2);
                    numOfTypes = amount(size);
                    answer = answer.substring(2);
                    for (int i =0; i < numOfTypes; i++)
                    {
                        int lengthOfName = amount(answer.substring(0,2));
                        answer = answer.substring(2);
                        String name = answer.substring(0, lengthOfName);
                        answer=  answer.substring(lengthOfName);
                        String code = answer.substring(3);
                        answer = answer.substring(3);


                    }

                }



            }

                createListOfPartsForTypeAndShowIt();

        }
        else{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
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


            String flag = answer.substring(0,4);
        answer = answer.substring(4);

        if(flag.matches("1500")) {


            String size = "";
            size += answer.substring(0, 2);
            int amountOfParts = amount(size);
            parts = null;
            parts = new part[amountOfParts];
            answer = answer.substring(2);

            for (int j = 0; j < amountOfParts; j++) {
                size = "";
                size += answer.substring(0, 2);
                int sizeName = amount(size);
                answer = answer.substring(2);
                String partName = (answer.substring(0, sizeName));
                answer = answer.substring(sizeName);

                String partCode = answer.substring(0, 6);
                answer = answer.substring(6);

                parts[j] = new part(partName, partCode);

            }
        }
        else
        {
            String size = "";

            parts = null;
            parts = new part[1];


            size += answer.substring(0, 2);
            int sizeName = amount(size);
            answer = answer.substring(2);
            String partName = (answer.substring(0, sizeName));
            answer = answer.substring(sizeName);

            String partCode = answer.substring(0, 6);
            answer = answer.substring(6);

            parts[0] = new part(partName, partCode);
        }

        resetList = true;
        showList();




}






    public void showList()
    {

            if(resetList) {
                parts_list.clear();
                resetList = false;
            }
            int spot = -1;

            for (int i = 0; i < parts.length; i++) {

                parts_list.add(new StringBuilder().append("Part name:    ").append(parts[i].getName()).append("    -   Code: ").append(parts[i].getCode()).toString());

                if(selectedParts[currBtn] != null) {
                    if (parts[i].getCode().matches(selectedParts[currBtn].getCode())) {
                        spot = i;
                    }
                }
            }

                if(spot == -1) {
                    arrayAdapter = new ArrayAdapter<String>(this, R.layout.item, parts_list);
                }
                else
                {
                    arrayAdapter = new ArrayAdapter<String>(this, R.layout.selected_item, parts_list);
                }


                lv.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();

    }






    public void onClickButtonType(View v)
    {

        if(v != currButton)
        {
            currButton.setBackgroundColor(Color.WHITE);
                currButton =(Button)v;
                for (int i=0; i < buttonTypes.length; i++)
                {
                    if(buttonTypes[i] == v)
                    {
                        currBtn = i;
                    }
                }

            v.setBackgroundColor(Color.CYAN);

            createListOfPartsForTypeAndShowIt();

        }



    }


    // delete selected part

    public void deSelectPart(View item)
    {

        ((TextView) item).setBackgroundColor(Color.WHITE);
        selectedParts[currBtn] = null;

        int size = lv.getCount();


        for (int i = 0; i <lv.getChildCount(); i++) {
            lv.getChildAt(i).setBackgroundColor(Color.WHITE);
        }


        arrayAdapter.notifyDataSetChanged();


    }



    public void onClickComputerCreation(View v)
    {
        if(v == finishBuildBtn) {
            int ok = 1;

            for (int i = 0; i < selectedParts.length; i++) {
                if (selectedParts[i] == null) {
                    ok = 0;
                }
            }


            if (ok == 1) {


                Intent i = new Intent(this, FinishBuildingScreen.class);


                String[] selected = new String[buttonTypes.length];
                String[] codes = new String[buttonTypes.length];
                String[] types = new String[buttonTypes.length];

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

                for (int j = 0; j < buttonTypes.length; j++) {
                    selected[j] = selectedParts[j].getName();
                    codes[j] = selectedParts[j].getCode();
                    types[j] = buttonTypes[j].getText().toString();
                }


                Bundle b1 = new Bundle();
                Bundle b2 = new Bundle();
                Bundle b3 = new Bundle();
                b1.putStringArray("itemNames", selected);
                b2.putStringArray("itemCodes", codes);
                b3.putStringArray("itemTypes", types);
                i.putExtras(b1);
                i.putExtras(b2);
                i.putExtras(b3);


                startActivity(i);
                finish();

            } else {
                Toast.makeText(this, "Could not finish computer building", Toast.LENGTH_SHORT).show();
            }
        }
        else if(v == saveAndExitBtn)
        {
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

            Intent i = new Intent(this, Computer_choosing_selection_activity.class);
            startActivity(i);
            finish();

        }
        else if(v == showInfo) {

            if (selectedParts[currBtn] != null) {


                RequestAndAnswer end = new RequestAndAnswer();

                String output = "260" + selectedParts[currBtn].getCode();
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

                if (!answer.matches("1701")) {

                    answer.substring(9);

                    final Dialog dialog = new Dialog(this);
                    dialog.setContentView(R.layout.part_info);

                    TextView partNameTV = (TextView) dialog.findViewById(R.id.partNameTV);
                    TextView partCodeTV = (TextView) dialog.findViewById(R.id.partCodeTV);
                    TextView partDetailesTV = (TextView) dialog.findViewById(R.id.partDetailesTV);
                    Button closeBtn = (Button) dialog.findViewById(R.id.closeBtn);

                    String s = "Part's code: " + selectedParts[currBtn].getCode();

                    partNameTV.setText(selectedParts[currBtn].getName());
                    partCodeTV.setText(s);

                    int size = amount(answer.substring(0, 3));
                    String detailesCarrier = answer.substring(0, size);
                    partDetailesTV.setText(detailesCarrier);

                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.setCancelable(false);
                    dialog.show();
                } else {
                    Toast.makeText(this, "The part does not exist", Toast.LENGTH_SHORT).show();
                }

            }
        }
        else
        {
            Toast.makeText(this, "Cannot show an incompatible component", Toast.LENGTH_SHORT).show();
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


    public void send180(int spot, View view)
    {

        RequestAndAnswer select = new RequestAndAnswer();

        String output = "180";
        output += parts[spot].getCode();
        String answer = "";

        g.setOutput(output);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            select.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            select.execute();




        do {
            answer = select.getResult();
        }
        while (answer.matches(""));

        String flags = answer.substring(0,4);
        answer = answer.substring(4);


        if ((flags.matches("1900") && (spot != -1))) {

            view.setBackgroundColor(Color.CYAN);
            selectedParts[currBtn] = parts[spot];
            arrayAdapter.notifyDataSetChanged();
            createListOfPartsForTypeAndShowIt();


        } else if (flags.matches("1901")) {
            Toast.makeText(getBaseContext(), "The item's code is not valid", Toast.LENGTH_LONG).show();
        }
        else if (flags.matches("1902")) {
            String reason = "";
            String partCode = answer.substring(0,3);
            answer = answer.substring(3);
           /** int size = amount(answer.substring(0,3));  //server sends ??Aa
            answer = answer.substring(3);
             reason = answer.substring(0,size);
            reason += " " + partCode;
            **/
           String chosenPartInfliction="";
           for (int i =0; i < selectedParts.length; i++)
           {
               if(selectedParts[i] != null) {
                   if (selectedParts[i].getCode().matches(partCode)) {
                       chosenPartInfliction = selectedParts[i].getName();
                   }
               }
           }

           reason = "The selected component is not compatible with "+chosenPartInfliction;
            Toast.makeText(getBaseContext(), reason, Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getBaseContext(), "unknownProblem", Toast.LENGTH_LONG).show();
        }

    }


    public void send200(int spot, View view)
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

        if (answer.matches("2100"))
        {
            //removing the item
            deSelectPart(view);
            arrayAdapter.notifyDataSetChanged();
            createListOfPartsForTypeAndShowIt();

        } else if (answer.matches("2101")) {
            Toast.makeText(getBaseContext(), "The item's code is not valid", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(), "unknownProblem", Toast.LENGTH_LONG).show();
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