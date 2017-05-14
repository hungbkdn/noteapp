package com.example.admin.bknote;



import android.content.BroadcastReceiver;

import android.content.Context;

import android.content.Intent;

import android.media.MediaPlayer;

import android.widget.Toast;



public class MyBroadcastReceiver extends BroadcastReceiver {

    //MediaPlayer mp;

    @Override

    public void onReceive(Context context, Intent intent) {

        //this mp use to alarm device

        //mp = MediaPlayer.create(context, R.raw.nhacchuong);

        //mp.start();

        String action = intent.getAction();

        String note, time, hour, uId;

        note= intent.getStringExtra("note");

        time = intent.getStringExtra("time");

        hour = intent.getStringExtra("hour");

        uId = intent.getStringExtra("uId");



        if (AppConstant.EDIT_ACTION.equals(action)) {

            Toast.makeText(context, "Edit this note:" + note, Toast.LENGTH_SHORT).show();

            Intent intent1 = new Intent(context.getApplicationContext(), Edit.class);

            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            intent1.putExtra("uId",uId);

            intent1.putExtra("note", note);

            intent1.putExtra("time", time);

            intent1.putExtra("hour", hour);

            context.startActivity(intent1);

        }

        else  if (AppConstant.DELETE_ACTION.equals(action)) {

            Toast.makeText(context, "Delete this note: *We have not procress it", Toast.LENGTH_SHORT).show();

        }

    }

}