package net.mkindustries.notficationtest;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {

    private TextView txtView;
    private NotificationReceiver nReceiver;




    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nReceiver);
    }

    class NotificationReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            String temp = intent.getStringExtra("notification_event");
            txtView.setText(temp);
            timeService.writeString(temp);

        }
    }


    private Button bt;
    private Context context = this;
    private Button timeUpdate;
    Intent mServiceIntent;


    private TextView timeView;
    Button sendString;
    Button connectBtn;
    Button notBtn;
    EditText editString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtView = (TextView) findViewById(R.id.textView);
        nReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("net.mkindustries.notificationtest.NOTIFICATION_LISTENER_EXAMPLE");
        registerReceiver(nReceiver,filter);





        //notifimacations




        //failed notification creation (see error)
        /*notBtn = (Button) findViewById(R.id.notBtn);

        notBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Notification.Builder mBuilder = new Notification.Builder(this).setContentTitle("My notification is cooler than Coolio").setContentText("Hello World!");
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification = mBuilder.build();
                mNotificationManager.notify(19642000, notification);
            }
        });
        */




        sendString = (Button) findViewById(R.id.sendString);
        editString = (EditText) findViewById(R.id.editString);
        connectBtn = (Button) findViewById(R.id.connectBtn);



        sendString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                //writeString(editString);

            }
        });

        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mServiceIntent = new Intent(MainActivity.this, timeService.class);
                //mServiceIntent.setData(Uri.parse("time"));
                MainActivity.this.startService(mServiceIntent);


            }
        });

        //example
        bt = (Button) findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Dialog dialog = new Dialog(context);
                dialog.setTitle("Lol this is not what I thought it was. Really Matt? Really?");
                dialog.show();
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

     @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
