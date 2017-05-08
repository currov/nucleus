package com.example.frvalent.myapplication;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DisplayMessageActivity extends AppCompatActivity {

    TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        //TextView textView = (TextView) findViewById(R.id.textView);
        //textView.setText(message);


        TelephonyManager tm=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        textView1=(TextView) findViewById(R.id.textView);
        String IMEINumber=tm.getDeviceId();
        String subscriberID=tm.getSubscriberId();
        String SIMSerialNumber=tm.getSimSerialNumber();
        String networkCountryISO=tm.getNetworkCountryIso();
        String SIMCountryISO=tm.getSimCountryIso();
        String softwareVersion=tm.getDeviceSoftwareVersion();
        String voiceMailNumber=tm.getVoiceMailNumber();
        String networkOperator=tm.getNetworkOperator();
        String networkOperatorName=tm.getNetworkOperatorName();

        List<CellInfo> allCells;
        try {
            allCells = tm.getAllCellInfo();
        } catch (NoSuchMethodError e) {
            allCells = null;

        }
        if ((allCells == null) || allCells.isEmpty()) {


        }



        String strphoneType="";

        int phoneType=tm.getPhoneType();

        switch (phoneType)
        {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                strphoneType="CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                strphoneType="GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                strphoneType="NONE";
                break;
        }

        //getting information if phone is in roaming
        boolean isRoaming=tm.isNetworkRoaming();

        String info="Phone Details:\n";
        info+="\n IMEI Number:"+IMEINumber;
        info+="\n SubscriberID:"+subscriberID;
        info+="\n Sim Serial Number:"+SIMSerialNumber;
        info+="\n Network Country ISO:"+networkCountryISO;
        info+="\n SIM Country ISO:"+SIMCountryISO;
        info+="\n Software Version:"+softwareVersion;
        info+="\n Voice Mail Number:"+voiceMailNumber;
        info+="\n Phone Network Type:"+strphoneType;
        info+="\n In Roaming? :"+isRoaming;
        info+="\n Operator number :"+networkOperator;
        info+="\n Operator name :"+networkOperatorName;


        //BEGIN OF CELL INFO

        List<Location> rslt = new ArrayList<Location>();
        for (android.telephony.CellInfo inputCellInfo : allCells) {
            Location cellLocation = null;
            if (inputCellInfo instanceof CellInfoGsm) {
                CellInfoGsm gsm = (CellInfoGsm) inputCellInfo;
                CellIdentityGsm id = gsm.getCellIdentity();
                //cellLocation = db.query(id.getMcc(), id.getMnc(), id.getCid(), id.getLac());
                info+="\n GSM CELL :";
                info+="\n GSM CELL LOCATION :"+id.getMcc()+" "+id.getMnc()+" "+id.getCid()+" "+id.getLac();
            } else if (inputCellInfo instanceof CellInfoWcdma) {

                CellInfoWcdma wcdma = (CellInfoWcdma) inputCellInfo;
                CellIdentityWcdma id = wcdma.getCellIdentity();
                //cellLocation = db.query(id.getMcc(), id.getMnc(), id.getCid(), id.getLac());
                info+="\n WDCMA CELL :";
                info+="\n WDCMA CELL LOCATION :"+id.getMcc()+" "+id.getMnc()+" "+id.getCid()+" "+id.getLac();

            }
            else if (inputCellInfo instanceof CellInfoLte) {

                CellInfoLte lte = (CellInfoLte) inputCellInfo;
                CellIdentityLte id = lte.getCellIdentity();
                //cellLocation = db.query(id.getMcc(), id.getMnc(), id.getCid(), id.getLac());
                info += "\n LTE CELL :" ;
                info += "\n LTE CELL LOCATION :" + id.getMcc() + " " + id.getMnc() + " " + id.getCi() + " " + id.getTac();

            }


            if ((cellLocation != null)) {
                rslt.add(cellLocation);
            }
            System.out.println("No cell type identified");
        }


        ///END OF CELL INFO





        textView1.setText(info);//displaying the information in the textView



    }



}