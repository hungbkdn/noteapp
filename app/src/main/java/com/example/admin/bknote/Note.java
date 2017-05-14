package com.example.admin.bknote;



import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.Locale;



/**

 * Created by Admin on 4/11/2017.

 */



public class Note {

    public String note;

    public String date;

    public String hour;

    public String uId;

    public Note()

    {



    }

    public Note(String note, String date, String date1,String uid)

    {

        this.note = note;

        this.date = date;

        this.hour = date1;

        this.uId = uid;

    }



    public void add(String uid)

    {

        this.uId = uid;

    }

    public String getNote()

    {

        return note;

    }

    public String getuId()

    {

        return uId;

    }

    public String getDate()

    {

        return this.date;

    }



    public String getTime()

    {

        return this.hour;

    }

    @Override

    public String toString() {

        return this.date+"-"+ this.hour;

    }

}