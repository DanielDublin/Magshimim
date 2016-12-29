package com.example.myapplication;

/**
 * Created by user on 25/12/2016.
 */

public class itemInfo
{
   private String type,name,code;

    public itemInfo(String type,String name,String code)
    {
        this.type=type;
        this.name = name;
        this.code = code;

    }


    public String getType()
    {
        return this.type;
    }
    public String getName()
    {
        return this.name;
    }
    public String getCode()
    {
        return this.code;
    }


}
