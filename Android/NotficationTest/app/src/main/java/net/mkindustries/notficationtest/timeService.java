package net.mkindustries.notficationtest;

import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * Created by Matt on 4/88/2025.
 */
public class timeService extends IntentService {
    static String hour;
    static String minute;
    static String second;
    static String time;
    static String timeWithSeconds;
    public static BluetoothSocket btSocket;
    public static OutputStream out;



    public timeService() {
        super("timeService");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        //String dataString = workIntent.getDataString();
        connect();
        startPushing();
        // Do work here, based on the contents of dataString

    }

    public static void startPushing() {


        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();

            }

        }, 0, 10000);
    }

    //method to update clock
    public static void TimerMethod(){

        //sketchy homemade clock
//        timeView = (TextView)findViewById(R.id.timeView);
//        timeUpdate = (Button) findViewById(R.id.timeUpdate);
//        timeUpdate.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
        Calendar c = Calendar.getInstance();
        hour = String.valueOf(c.get(Calendar.HOUR));
        minute = String.valueOf(c.get(Calendar.MINUTE));
        second = String.valueOf(c.get(Calendar.SECOND));
        if(c.get(Calendar.MINUTE) < 10) {
            time = hour + ":0" + minute;
            timeWithSeconds = hour + ":0" + minute + ":" + second;
        } else {
            time = hour + ":" + minute;
            timeWithSeconds = hour + ":" + minute + ":" + second;
        }
        //}

        //});

        writeString(time);


    }

    //initialize bluetooth connection

    public static BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    public static BluetoothDevice btDevice = mAdapter.getRemoteDevice("20:14:08:26:26:70");
    public static void connect()
    {
        try {

            try {
                if(btSocket != null){
                    btSocket.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }


            btSocket = btDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"));
            btSocket.connect();
            out = btSocket.getOutputStream();




        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeString(String string)
    {
        byte[] overByte = string.getBytes();

        try {
            out.write(overByte);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

