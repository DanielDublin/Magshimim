package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class profile extends AppCompatActivity implements AlertDialog.OnClickListener{

    private Button settingsBtn, profileBtn, computersBtn, computerInfoBtn, editComputerBtn;
    private String username = "", profile_name = "", currName ="", currFamilyName = "", currPassword = "", currEmail = "";
    private TextView tvUsername;
    private boolean stranger = false;
    private Globals g;
    private Computer [] computers;
    private LinearLayout l;
    private List<String> computers_list, computers_listDialog;
    private ListView lv, lvDialog;
    private boolean resetList = false, inEditMode = false;
    private int worked = 0;
    private ArrayAdapter<String> arrayAdapter, arrayAdapterDialog;
    private EditText firstName, lastName, password, email, userNameET;
    private TextView tv1, tv2, tv3, tv4, tv5, errorTV;
    private float x1, y1, x2, y2, x3, y3, x4, y4;
    private boolean firstTime = true;
    private boolean changedSettings = false, pressedOut = false;
    private String chosenCode ="";
    private int selected =0;
    private View lastChosenView = null;
    private part [] parts;
    private RatingBar rb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

       tvUsername = (TextView) findViewById(R.id.tvUsername);
        settingsBtn = (Button)findViewById(R.id.settingsBtn);
        computerInfoBtn = (Button)findViewById(R.id.computerInfoBtn);
        editComputerBtn = (Button)findViewById(R.id.editBtn);
        profileBtn = (Button)findViewById(R.id.showProfileBtn);
        computersBtn = (Button)findViewById(R.id.showComputersBtn);
        firstName = (EditText) findViewById(R.id.firstNameET);
        lastName = (EditText) findViewById(R.id.lastNameET);
        password = (EditText) findViewById(R.id.passwordET);
        email = (EditText) findViewById(R.id.emailET);
        userNameET = (EditText) findViewById(R.id.usernameET);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        errorTV = (TextView) findViewById(R.id.errorMessageTV);


        firstName.setEnabled(false);
        lastName.setEnabled(false);
        password.setEnabled(false);
        email.setEnabled(false);
        userNameET.setEnabled(false);


        computers_listDialog = new ArrayList<String>();


        tvUsername.setY(tvUsername.getY() +10);

        lv = (ListView)findViewById(R.id.lv);

        computers_list = new ArrayList<String>();

        g = Globals.getInstance();


         profile_name = this.getIntent().getExtras().get("profile_name").toString();
         username = g.getUsername();
        userNameET.setText(profile_name);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //add code here

                if(chosenCode.matches(""))
                {
                    view.setBackgroundColor(Color.CYAN);
                    TextView t = (TextView)view;
                    String temp = t.getText().toString();
                    selected = position;
                    chosenCode = computers[position].getCode();
                    lastChosenView = view;


                }
                else if(view != lastChosenView)
                {

                    lastChosenView.setBackgroundColor(Color.WHITE);
                    view.setBackgroundColor(Color.CYAN);
                    selected = position;
                    chosenCode = computers[position].getCode();
                    lastChosenView = view;


                }
                else
                {
                    view.setBackgroundColor(Color.WHITE);
                    lastChosenView = null;
                    selected = 0;
                    chosenCode = "";
                }
                arrayAdapter.notifyDataSetChanged();

            }
        });


        if(!username.matches(profile_name))
        {
            settingsBtn.setEnabled(false);
            settingsBtn.setBackgroundColor(Color.TRANSPARENT);
            settingsBtn.setTextColor(Color.TRANSPARENT);
            tvUsername.setText(new StringBuilder().append(profile_name).toString());
            stranger = true;
            tv3.setVisibility(View.INVISIBLE);
            tv4.setVisibility(View.INVISIBLE);
        }
        else
        {
            tvUsername.setText(new StringBuilder().append("Welcome  ").append(username).append(" !").toString());
        }



        showProfile();

    }


    public void onClickChoicesInProfile(View v)
    {

        if(firstTime) {
            x1 = firstName.getX();
            y1 =firstName.getY();

            x2 = lastName.getX();
            y2 = lastName.getY();

            x3 = password.getX();
            y3 = password.getY();

            x4 = email.getX();
            y4 = email.getY();

            firstTime = false;
        }


        if(v == settingsBtn)
        {
            if(!inEditMode) {

                changedSettings = true;
                firstName.setEnabled(true);
                lastName.setEnabled(true);
                if(!stranger) {
                    password.setEnabled(true);
                    email.setEnabled(true);
                }
                userNameET.setEnabled(true);

                lv.setVisibility(View.GONE);
                lv.setEnabled(false);
                computerInfoBtn.setVisibility(View.GONE);
                computerInfoBtn.setEnabled(false);
                editComputerBtn.setVisibility(View.GONE);
                editComputerBtn.setEnabled(false);
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                tv5.setVisibility(View.VISIBLE);
                if(!stranger) {
                    tv3.setVisibility(View.VISIBLE);
                    tv4.setVisibility(View.VISIBLE);
                }
                userNameET.setVisibility(View.VISIBLE);
                firstName.setVisibility(View.VISIBLE);
                lastName.setVisibility(View.VISIBLE);
                if(!stranger) {
                    password.setVisibility(View.VISIBLE);
                    email.setVisibility(View.VISIBLE);
                }
                settingsBtn.setText("Save details");
                inEditMode = true;

            }
            else
            {
                worked = 0;
                 worked = changeProfile();

                if(worked != 0) {
                    inEditMode = false;
                    settingsBtn.setText("Update details");
                    firstName.setEnabled(false);
                    lastName.setEnabled(false);
                    password.setEnabled(false);
                    userNameET.setEnabled(true);
                    email.setEnabled(false);
                }

                if(worked == -1)
                {
                    userNameET.setText(username);
                    firstName.setText(currName);
                    lastName.setText(currFamilyName);
                    email.setText(currEmail);
                    password.setText(currPassword);
                    tvUsername.setText("Welcome "+username+" !");
                }




            }

        }
        else if(v == profileBtn)
        {


            if(!inEditMode) {
                userNameET.setEnabled(false);
                firstName.setEnabled(false);
                lastName.setEnabled(false);
                password.setEnabled(false);
                email.setEnabled(false);


                lv.setVisibility(View.GONE);
                lv.setEnabled(false);
                computerInfoBtn.setVisibility(View.GONE);
                computerInfoBtn.setEnabled(false);
                editComputerBtn.setVisibility(View.GONE);
                editComputerBtn.setEnabled(false);
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                if(!stranger) {
                    tv3.setVisibility(View.VISIBLE);
                    tv4.setVisibility(View.VISIBLE);
                }
                firstName.setVisibility(View.VISIBLE);
                lastName.setVisibility(View.VISIBLE);
                tv5.setVisibility(View.VISIBLE);
                userNameET.setVisibility(View.VISIBLE);

                if(!stranger)
                {
                    password.setVisibility(View.VISIBLE);
                    email.setVisibility(View.VISIBLE);
                }


                firstName.setX(x1);
                firstName.setY(y1);

                lastName.setX(x2);
                lastName.setY(y2);

                password.setX(x3);
                password.setY(y3);

                email.setX(x4);
                email.setY(y4);


            }
            else
            {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Save changes?");
                builder.setPositiveButton("Yes", this);
                builder.setNegativeButton("No", this);

                builder.setCancelable(false);


                AlertDialog dialog = builder.create();

                dialog.show();


            }

        }
        else if(v == computersBtn)
        {
            if(!inEditMode) {
                firstName.setEnabled(false);
                lastName.setEnabled(false);
                password.setEnabled(false);
                userNameET.setEnabled(false);
                email.setEnabled(false);

                computerInfoBtn.setVisibility(View.VISIBLE);
                computerInfoBtn.setEnabled(true);
                editComputerBtn.setVisibility(View.VISIBLE);
                editComputerBtn.setEnabled(true);
                lv.setVisibility(View.VISIBLE);
                lv.setEnabled(true);
                tv1.setVisibility(View.INVISIBLE);
                tv2.setVisibility(View.INVISIBLE);
                if(!stranger) {
                    tv3.setVisibility(View.INVISIBLE);
                    tv4.setVisibility(View.INVISIBLE);
                }
                tv5.setVisibility(View.INVISIBLE);
                firstName.setVisibility(View.INVISIBLE);
                lastName.setVisibility(View.INVISIBLE);
                password.setVisibility(View.INVISIBLE);
                email.setVisibility(View.INVISIBLE);
                userNameET.setVisibility(View.INVISIBLE);
            }
             else
            {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Save changes?");
                builder.setPositiveButton("Yes", this);
                builder.setNegativeButton("No", this);

                builder.setCancelable(false);


                AlertDialog dialog = builder.create();

                dialog.show();


            }
        }
        else if(v == computerInfoBtn)
        {
            if(!chosenCode.matches(""))
            {
                showComputerDetailes(chosenCode);
                errorTV.setText("");
            }
        }
        else if (v == editComputerBtn)
        {
            if(!chosenCode.matches("")) {
                Intent i = new Intent(this, Computer_Creation.class);
                g.setUsername(username);
                i.putExtra("chosenCode", chosenCode);
                startActivity(i);
                finish();
            }
        }

    }

    public void showProfile()
    {

        String output="";
        if(!stranger) {
             output = "380";
        }
        else
        {
           
             output = "400"+ intToString(profile_name.length()) + profile_name;
        }


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

        answer = answer.substring(3);
        int sizeOfString = amount(answer.substring(0,2));
        answer = answer.substring(2);
        String uPrivateFirstName = answer.substring(0,sizeOfString);
        answer = answer.substring(sizeOfString);

        sizeOfString = amount(answer.substring(0,2));
        answer = answer.substring(2);
        String uPrivateLastName = answer.substring(0,sizeOfString);
        answer = answer.substring(sizeOfString);

        sizeOfString = amount(answer.substring(0,2));
        answer = answer.substring(2);
        String uUsername = answer.substring(0,sizeOfString);
        answer = answer.substring(sizeOfString);

        String uPassword ="";
        String uEmail = "";

        if(!stranger) {
            sizeOfString = amount(answer.substring(0,2));
            answer = answer.substring(2);
             uPassword = answer.substring(0,sizeOfString);
            answer = answer.substring(sizeOfString);

            sizeOfString = amount(answer.substring(0,2));
            answer = answer.substring(2);
             uEmail = answer.substring(0,sizeOfString);
            answer = answer.substring(sizeOfString);
        }





        firstName.setText(uPrivateFirstName);
        lastName.setText(uPrivateLastName);


        currFamilyName = uPrivateLastName;
        currName = uPrivateFirstName;

        if(!stranger) {

            password.setText(uPassword);
            email.setText(uEmail);
            currEmail = uEmail;
            currPassword = uPassword;
        }
        else
        {
            password.setVisibility(View.INVISIBLE);
            password.setEnabled(false);
            email.setVisibility(View.INVISIBLE);
            email.setEnabled(false);
        }



        //computer showings
        int amountOfComputers = amount(answer.substring(0,2));
        answer = answer.substring(2);

        computers =null;
        computers = new Computer[amountOfComputers];

        for(int i = 0; i < amountOfComputers; i++) {

            sizeOfString = amount(answer.substring(0, 2));
            answer = answer.substring(2);
            String computerName = answer.substring(0,sizeOfString);
            answer = answer.substring(sizeOfString);
            String code = answer.substring(0,5);
            answer = answer.substring(5);
            char rate = answer.charAt(0);
            answer = answer.substring(1);
            int status = (int)answer.charAt(0) -48 ;
            answer = answer.substring(1);
            computers[i] = new Computer(computerName,code, status, rate);


            computers_list.add(new StringBuilder().append("Computer name:    ").append(computers[i].getName()).append("    -   Code: ").append(computers[i].getCode()).append("    -   Rate: ").append(status).append("    -   Status: ").append(status).toString());

        }

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.item, computers_list);
        lv.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        lv.setVisibility(View.GONE);
        lv.setEnabled(false);
        computerInfoBtn.setVisibility(View.GONE);
        computerInfoBtn.setEnabled(false);
        editComputerBtn.setVisibility(View.GONE);
        editComputerBtn.setEnabled(false);



    }


    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        if(pressedOut)
        {  //exiting the activity while setting have changed
            if (which == dialog.BUTTON_POSITIVE) {


                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                dialog.cancel();


            } else if (which == dialog.BUTTON_NEGATIVE) {
                pressedOut = false;
                dialog.cancel();
            }

            finish();
        }
        else
        {

            //saving new settings
            if (which == dialog.BUTTON_POSITIVE) {

               worked = changeProfile();

            }
            else
            {
                worked = -1;
            }

            if(worked != 0) {
                inEditMode = false;
                settingsBtn.setText("Update details");
                firstName.setEnabled(false);
                lastName.setEnabled(false);
                password.setEnabled(false);
                userNameET.setEnabled(true);
                email.setEnabled(false);
            }

            if(worked == -1)
            {
                userNameET.setText(username);
                firstName.setText(currName);
                lastName.setText(currFamilyName);
                email.setText(currEmail);
                password.setText(currPassword);
                tvUsername.setText("Welcome "+username+" !");
            }



        }

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


        final Dialog dialog = new Dialog(profile.this);
        dialog.setContentView(R.layout.computer_detailes_dialog);


        // set the custom dialog components - text, image and button
        TextView computerNameTV = (TextView) dialog.findViewById(R.id.computerNameTV);
        TextView creatorNameTV = (TextView) dialog.findViewById(R.id.creatorTV);
        TextView rateTV = (TextView) dialog.findViewById(R.id.rateTV);
        Button closeBtn = (Button) dialog.findViewById(R.id.closeBtn);
        Button profileCheck = (Button)dialog.findViewById(R.id.profileBtn);
        profileCheck.setEnabled(false);
        profileCheck.setVisibility(View.INVISIBLE);

        rb = (RatingBar)dialog.findViewById(R.id.ratingBar);

        if(username.matches(profile_name))
        {
            rb.setClickable(false);
            float fr = Float.parseFloat((rating))/2;
            rb.setRating(fr);
        }

        float x = computerNameTV.getX();


        lvDialog = (ListView)dialog.findViewById(R.id.lvDialog);
        lvDialog.setAdapter(arrayAdapterDialog);




        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                String rateOut =""+ ((int)(rating*2) -1);


                RequestAndAnswer rateTicket = new RequestAndAnswer();

                String output = "360"+computerCode+rateOut;
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

                if(answer.matches("3701"))
                {
                    Toast.makeText(profile.this, "Error in rating", Toast.LENGTH_SHORT).show();
                }





            }
        });



        computerNameTV.setText(computerName+"   -    "+computerCode);
        creatorNameTV.setText(creatorNameTV.getText()+computerCreator+"   -    "+builderCode);
        double dbRating = Double.parseDouble((rating))/2;
        rating = "";
        rating += dbRating;


        rateTV.setText(rateTV.getText() + rating +" out of 5");

        // if button is clicked, close the custom dialog
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.show();


    }



    public int changeProfile() {
        RequestAndAnswer end = new RequestAndAnswer();

        String output = new StringBuilder("440").append(intToString(firstName.getText().toString().length())).append(firstName.getText().toString())
                .append(intToString(lastName.getText().toString().length())).append(lastName.getText().toString())
                .append(intToString(username.length())).append(username.toString())
                .append(intToString(password.getText().toString().length())).append(password.getText().toString())
                .append(intToString(email.getText().toString().length())).append(email.getText().toString()).toString();

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

        answer = answer.substring(0,4);

        if(answer.matches("4500"))
        {
            inEditMode = false ;
            settingsBtn.setText("Update details");
            firstName.setEnabled(false);
            lastName.setEnabled(false);
            password.setEnabled(false);
            userNameET.setEnabled(true);
            email.setEnabled(false);

            currFamilyName = lastName.getText().toString();
            currPassword = password.getText().toString();
            currEmail = email.getText().toString();
            currName = firstName.getText().toString();
            username = userNameET.getText().toString();



            changedSettings = true;
            return 1;
        }
        else if (answer.matches("4501"))
        {
            errorTV.setText("Email is already in use");
        }
        else if (answer.matches("4502"))
        {
            errorTV.setText("Username is already taken");
        }
        else if (answer.matches("4503"))
        {
            errorTV.setText("Unapproved password: must contain at least 1 digit and 1 upper and smaller case letters");
        }
        else if (answer.matches("4504"))
        {
            errorTV.setText("Invalid username");
        }
        else if (answer.matches("4505"))
        {
            errorTV.setText("Unknown error while saving details");
        }

        return 0;

}



    @Override
    public void onBackPressed()
    {

        if(changedSettings) {
            pressedOut = true;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Save changes?");
            builder.setPositiveButton("Yes", this);
            builder.setNegativeButton("No", this);

            builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else
        {
            super.onBackPressed();
        }

    }


    public int amount(String str)
    {
        return Integer.parseInt(str);
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
