package com.example.myapplication;

import android.app.Application;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by User on 22/01/2017.
 */

public class Globals
{
    public String output="";
    public Socket socket;
    private static Globals instance;


    public Globals()
    {

    }

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }

    public void setOutput(String output)
    {
        this.output = output;
    }

    public String getOutput()
    {
        return output;
    }

    public void  closeSock()
    {
        try {
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startSocket(Socket s)
    {
        this.socket = s;
    }

    public Socket getSocket()
    {
        return this.socket;
    }



}
