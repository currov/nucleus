package com.example.frvalent.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Curro on 09/05/2017.
 */



public class MyOutgoingCallHandler extends BroadcastReceiver{

    Boolean isTest = true; // stops hijacking the dialer
    public static final String EXTRA_MESSAGE  = "com.example.myfirstapp.MESSAGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Extract phone number reformatted by previous receivers
        String phoneNumber = getResultData();
        System.out.println("Inside the Broadcast Receiver with phone" +phoneNumber);
        if (phoneNumber == null) {
            // No reformatted number, use the original
            phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        }
        // My app will bring up the call, so cancel the broadcast
        String broadcastreturn = isTest?phoneNumber:null;

        setResultData(broadcastreturn);
        // Start my DIAL OUT activity

        intent.putExtra(EXTRA_MESSAGE, phoneNumber);
        intent.setClassName("com.example.frvalent.myapplication", "com.example.frvalent.myapplication.DialOutActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);


    }


}
