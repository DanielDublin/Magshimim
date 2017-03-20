package com.example.myapplication;

public class Computer {

    private String name, code, computerCreator, creatorsCode;
    private int status;
    private char rate;
    private part [] parts;

    public Computer(String name, String code)
    {
        this.name = name;
        this.code = code;
        this.status=0;
        this.rate='0';
    }


    public Computer(String name, String code, int status, char rate)
    {
        this.name = name;
        this.code = code;
        this.status = status;
        this.rate = rate;


    }


    public Computer(String computerCreator, String creatorsCode, String name, String code, int status, char rate, part [] parts)
    {
        this.name = name;
        this.code = code;
        this.status = status;
        this.rate = rate;
        this.parts = new part[parts.length];
        for (int i =0; i < parts.length; i++)
        {
            this.parts[i] = parts[i];
        }

    }



    public String getName()
    {
        return name;
    }

    public String getCode()
    {
        return  code;
    }

    public int getStatus() {return  status;}

    public char getRate()
    {
        return  rate;
    }





}
