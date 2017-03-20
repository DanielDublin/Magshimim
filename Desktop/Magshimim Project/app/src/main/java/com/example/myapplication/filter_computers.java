package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 10/02/2017.
 */

public class filter_computers extends Activity {

    private Button filterBtn, returnBtn, chooseBtn;
    private AlertDialog.Builder builder;
    private  AlertDialog  dialog;
    private Globals g;
    final String[] items = {"Ratings High -> Low","Creating date New -> Old"," Creating date Old -> New"," Price High -> Low"};
    // arraylist to keep the selected items
    final ArrayList selectedItems=new ArrayList();
    private LinearLayout l;
    private List<String> computers_list, computers_listDialog, backupArrayList;
    private ListView lv, lvDialog;
    private boolean resetList = false, isSelected = false;
    private ArrayAdapter<String> arrayAdapter, arrayAdapterDialog;
    private Computer [] computers, computers_backup;
    private Computer selectedComputer;
    private int filter =0, primeFilter =0;
    private part [] parts;
    private View lastChecked = null;
    private int lastCheckedPosition =-1;
    private RatingBar rb;
    private ImageView filterImageryButton;
    private float newStars = -1;
    private EditText filterET;
    private String currUser = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_computers_activity);



        g=Globals.getInstance();

        filterBtn = (Button)findViewById(R.id.filter_btn);
        returnBtn = (Button)findViewById(R.id.returnBtn);
        chooseBtn = (Button)findViewById(R.id.choose_btn);
        filterET = (EditText)findViewById(R.id.filterComputersET);
        filterImageryButton = (ImageView)findViewById(R.id.searchImageryButton);
        l = (LinearLayout)findViewById(R.id.child);
        lv = (ListView) findViewById(R.id.lv);
        computers_listDialog = new ArrayList<String>();
        computers_list = new ArrayList<String>();
        backupArrayList = new ArrayList<String>();

        currUser = g.getUsername();


        //implementing builder

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Wanted Filter");
        builder.setMultiChoiceItems(items, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    // indexSelected contains the index of item (of which checkbox checked)
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked)
                    {
                        if (isChecked) {

                            for(int i=0; i < items.length;i++)
                            {
                                if(i != indexSelected) {
                                    ((AlertDialog) dialog).getListView().setItemChecked(i, false);
                                }
                            }

                            selectedItems.clear();
                            selectedItems.add(indexSelected);
                        } else if (selectedItems.contains(indexSelected)) {
                            // Else, if the item is already in the array, remove it
                            // write your code when user Uchecked the checkbox
                            ((AlertDialog) dialog).getListView().setItemChecked(indexSelected, false);
                            selectedItems.remove(Integer.valueOf(indexSelected));
                        }


                    }
                })
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        filter =-1;
                        if(selectedItems.size()!=0)
                        {

                                    filter = ((int)selectedItems.get(0))+1;

                        }
                        else
                        {
                            filter = 0;
                        }
                        createListOfComputersAndShowIt();


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        if(selectedItems.size()!=0)
                        {
                            for (int i =0; i <selectedItems.size();i++)
                            {
                                if(i != filter)
                                {
                                    ((AlertDialog) dialog).getListView().setItemChecked(i, false);
                                    selectedItems.remove(Integer.valueOf(i));
                                }
                                else
                                {
                                    ((AlertDialog) dialog).getListView().setItemChecked(i, true);
                                    selectedItems.add(Integer.valueOf(i));
                                }
                            }
                        }

                    }
                });
       dialog= builder.create();


        //implementing listView

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

               if(lastChecked == null)
               {
                   lastCheckedPosition = position;
                   view.setBackgroundColor(Color.CYAN);
                   lastChecked = view;
               }
                else if(lastChecked == view)
               {
                   lastCheckedPosition = -1;
                    view.setBackgroundColor(Color.WHITE);
                   lastChecked = null;
               }
                else
               {
                   lastCheckedPosition = position;
                   lastChecked.setBackgroundColor(Color.WHITE);
                   view.setBackgroundColor(Color.CYAN);
                   lastChecked = view;

               }

                arrayAdapter.notifyDataSetChanged();

            }



        });



       createListOfComputersAndShowIt();

    }



    public void onClickAtFilters(View v)
    {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        if(v == filterBtn) {
            dialog.show();
        }
        else if(v == chooseBtn)
        {
            if(lastCheckedPosition != -1) {
                String code = computers[lastCheckedPosition].getCode();
                showComputerDetailes(code);
            }

        }
        else if(v == returnBtn)
        {
            Intent i = new Intent(this, Computer_choosing_selection_activity.class);
            startActivity(i);
            finish();
        }
        else if (v == filterImageryButton)
        {



            computers_list.clear();
            computers = null;
            if (filterET.getText().toString().length() == 0) {
                computers_list.addAll(backupArrayList);

                computers = new Computer[computers_backup.length];

                for (int i =0; i < computers_backup.length;i++)
                {
                    computers[i] = new Computer(computers_backup[i].getName(), computers_backup[i].getCode());
                }


            }
            else {

                boolean foundView = false;
                for (int i = 0; i < backupArrayList.size(); i++) {
                    String a = backupArrayList.get(i).toLowerCase();


                    a = a.substring("Computer name:    ".length());
                    String b = filterET.getText().toString().toLowerCase();
                    if (a.contains(b)) {
                        computers_list.add(backupArrayList.get(i));

                    }
                }

                computers = new Computer[computers_list.size()];

                for (int i = 0; i < computers_list.size(); i++) {


                    String viewString = "";
                    if(lastChecked == null)
                    {
                        foundView = false;
                    }
                    else {
                        TextView tv = (TextView) lastChecked;
                        viewString = tv.getText().toString();
                    }

                    if(computers_list.get(i).matches(viewString))
                    {
                        foundView = true;
                    }


                    computers[i] = new Computer(computers_backup[i].getName(), computers_backup[i].getCode());


                }

                if((!foundView) && (lastChecked != null))
                {

                    lastChecked.setBackgroundColor(Color.WHITE);
                    lastChecked = null;
                    lastCheckedPosition = -1;
                }

                arrayAdapter.notifyDataSetChanged();

            }

        }
    }



    public void showList()
    {

        if(resetList) {
            if(!computers_list.isEmpty()) {
                computers_list.clear();
            }
            resetList = false;
        }
        int spot = -1;

        for (int i = 0; i < computers.length; i++) {

            computers_list.add(new StringBuilder().append("Computer name:    ").append(computers[i].getName()).toString());

            if(selectedComputer != null) {
                if (computers[i].getCode().matches(selectedComputer.getCode())) {
                    spot = i;
                }
            }
        }

        if(spot == -1) {
            arrayAdapter = new ArrayAdapter<String>(this, R.layout.item, computers_list);
        }
        else
        {
            arrayAdapter = new ArrayAdapter<String>(this, R.layout.selected_item, computers_list);
        }

        backupArrayList.addAll(computers_list);

        lv.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }



    public void deSelectComputer(View item)
    {

        ((TextView) item).setBackgroundColor(Color.WHITE);
        selectedComputer = null;

        int size = lv.getCount();


        for (int i = 0; i <lv.getChildCount(); i++) {
            lv.getChildAt(i).setBackgroundColor(Color.WHITE);
        }


        arrayAdapter.notifyDataSetChanged();


    }



    public void createListOfComputersAndShowIt()
    {

        RequestAndAnswer end = new RequestAndAnswer();

        String output = "320";
        output+=filter;
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

        answer = answer.substring(3);


        String size = answer.substring(0, 2);




        int numOfTypes = amount(size);
        answer = answer.substring(2);



        computers = null;

        computers = new Computer[numOfTypes];
        computers_backup = new Computer[numOfTypes];


        //initializing types
        for (int i = 0; i < numOfTypes; i++) {
            size = "";
            size += answer.substring(0, 2);
            int nameTypeLength = amount(size);
            answer = answer.substring(2);

            String name =  answer.substring(0, nameTypeLength);
            answer = answer.substring(nameTypeLength);
            String code = answer.substring(0, 5);
            answer=answer.substring(5);
            computers[i] = new Computer(name, code);
            computers_backup[i] = new Computer(name, code);


        }

        resetList = true;
        showList();

    }




    public int findPartByName(String name)
    {

        for (int i=0; i < computers.length;i++)
        {
            if(computers[i].getName().equals(name))
            {

                return i;
            }

        }
        return -1;
    }




    public void showComputerDetailes(String code)
    {
        RequestAndAnswer end = new RequestAndAnswer();

        String output = "340";
        output+= code;
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

        answer = answer.substring(3);


        int size = amount(answer.substring(0,2));
        answer = answer.substring(2);
        String computerCreator = answer.substring(0, size);
        answer = answer.substring(size);
        String builderCode = answer.substring(0,4);
        answer = answer.substring(4);
        final String computerCode = answer.substring(0, 5);
        answer = answer.substring(5);
        size = amount(answer.substring(0,2));
        answer = answer.substring(2);
        String computerName = answer.substring(0, size);
        answer = answer.substring(size);
        int numOfParts = amount(answer.substring(0,2));
        answer = answer.substring(2);
        parts = new part[numOfParts];

        for (int i =0; i < numOfParts; i++)
        {
            size = amount(answer.substring(0,2));
            answer = answer.substring(2);
            String partKind = answer.substring(0, size);
            answer = answer.substring(size);
            size = amount(answer.substring(0,2));
            answer = answer.substring(2);
            String partName = answer.substring(0, size);
            answer = answer.substring(size);
            parts[i] = new part(partName);

            String outLine = new StringBuilder("     Part type: ").append(partKind).append("  -   Part name: ").append(partName).toString();
            computers_listDialog.add(outLine);

        }
        final char rate = answer.charAt(0);
        String rating= "";
        rating +=rate;



        arrayAdapterDialog = new ArrayAdapter<String>(this, R.layout.item, computers_listDialog);


        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.computer_detailes_dialog);
        dialog.setCancelable(false);




        // set the custom dialog components - text, image and button
        TextView computerNameTV = (TextView) dialog.findViewById(R.id.computerNameTV);
        final TextView creatorNameTV = (TextView) dialog.findViewById(R.id.creatorTV);
        TextView rateTV = (TextView) dialog.findViewById(R.id.rateTV);
        Button closeBtn = (Button) dialog.findViewById(R.id.closeBtn);
        Button profileBtn = (Button) dialog.findViewById(R.id.profileBtn);
        rb = (RatingBar)dialog.findViewById(R.id.ratingBar);
        lvDialog = (ListView)dialog.findViewById(R.id.lvDialog);
        lvDialog.setAdapter(arrayAdapterDialog);


        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

             newStars = rating;



            }
        });




        computerNameTV.setText("     "+computerName+"   -    "+computerCode);
        creatorNameTV.setText(creatorNameTV.getText()+computerCreator+"   -    "+builderCode);
        double dbRating = Double.parseDouble((rating))/2;
        rating = "";
        rating += dbRating;


        rateTV.setText(rateTV.getText() + rating +" out of 5");

        // if button is clicked, close the custom dialog
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(newStars != -1) {
                    String rateOut = "" + ((int) (newStars * 2) - 1);


                    RequestAndAnswer rateTicket = new RequestAndAnswer();

                    String output = "360" + computerCode + rateOut;
                    String answer = "";

                    g.setOutput(output);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                        rateTicket.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    else
                        rateTicket.execute();


                    do {
                        answer = rateTicket.getResult();
                    }
                    while (answer.matches(""));

                    String flags = answer.substring(0, 3);
                    answer.substring(3);


                    if (flags.matches("3701")) {
                        Toast.makeText(filter_computers.this, "Error in rating", Toast.LENGTH_SHORT).show();
                    }


                }
                dialog.dismiss();
            }

        });


        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //profile_name = this.getIntent().getExtras().get("profile_name").toString();
                //username = this.getIntent().getExtras().get("username").toString();

                String temp = creatorNameTV.getText().toString();
                temp = temp.substring("        Creator: ".length());
                String creator_name ="";
                char c = '1';
                int count =0;
                do {

                    c = temp.charAt(count);
                    if(c != ' ')
                    {
                        creator_name += c;
                    }
                    count++;
                }
                while(c != ' ');

                Intent i = new Intent(filter_computers.this, profile.class);
                i.putExtra("profile_name",creator_name );

                startActivity(i);
            }
        });



        dialog.show();


    }


    public int amount(String str)
    {
        return Integer.parseInt(str);
    }


}





