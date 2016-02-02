package net.mkindustries.notficationtest;

/**
 * Created by Matt on 4/29/2015.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

public class NLService extends NotificationListenerService {

    private String TAG = this.getClass().getSimpleName();
    private NLServiceReceiver nlservicereciver;
    @Override
    public void onCreate() {
        super.onCreate();
        nlservicereciver = new NLServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("net.mkindustries.notificationtest.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        registerReceiver(nlservicereciver,filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nlservicereciver);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

//        Log.i(TAG,"**********  onNotificationPosted");
//        Log.i(TAG,"ID :" + sbn.getId() + "t" + sbn.getNotification().tickerText + "t" + sbn.getPackageName());
        Intent i = new Intent("net.mkindustries.notificationtest.NOTIFICATION_LISTENER_EXAMPLE");
//        i.putExtra("notification_event","onNotificationRemoved :" + sbn.toString());
//        sendBroadcast(i);
        String mTitle = "";
        String mText = "";
        try {
            RemoteViews remoteView = sbn.getNotification().contentView;
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewGroup localView = (ViewGroup) inflater.inflate(remoteView.getLayoutId(), null);
            remoteView.reapply(getApplicationContext(), localView);
            TextView tvTitle = (TextView) localView.findViewById(android.R.id.title);
            mTitle = (String)tvTitle.getText();
            i.putExtra("notification_event","n" + mTitle);
        } catch (Exception e){
            Log.e(TAG, "Error getting notification title/text: " + e);
        }
        sendBroadcast(i);

    }

//    @Override
//    public void onNotificationRemoved(StatusBarNotification sbn) {
//        Log.i(TAG,"********** onNOtificationRemoved");
//        Log.i(TAG,"ID :" + sbn.getId() + "t" + sbn.getNotification().tickerText +"t" + sbn.getPackageName());
//        Intent i = new  Intent("net.mkindustries.notificationtest.NOTIFICATION_LISTENER_EXAMPLE");
//        //i.putExtra("notification_event","onNotificationRemoved :" + sbn.getPackageName() + "n");
//        i.putExtra("notification_event","onNotificationRemoved :" + sbn.toString());
//
//        sendBroadcast(i);
//    }

    class NLServiceReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getStringExtra("command").equals("clearall")){
                NLService.this.cancelAllNotifications();
            }
            else if(intent.getStringExtra("command").equals("list")){
                Intent i1 = new  Intent("net.mkindustries.net.NOTIFICATION_LISTENER_EXAMPLE");
                i1.putExtra("notification_event","=====================");
                sendBroadcast(i1);
                int i=1;
                for (StatusBarNotification sbn : NLService.this.getActiveNotifications()) {
                    Intent i2 = new  Intent("net.mkindustries.net.NOTIFICATION_LISTENER_EXAMPLE");
                    i2.putExtra("notification_event",i +" " + sbn.getPackageName() + "n");
                    sendBroadcast(i2);
                    i++;
                }
                Intent i3 = new  Intent("net.mkindustries.net.NOTIFICATION_LISTENER_EXAMPLE");
                i3.putExtra("notification_event","===== Notification List ====");
                sendBroadcast(i3);

            }

        }
    }

}