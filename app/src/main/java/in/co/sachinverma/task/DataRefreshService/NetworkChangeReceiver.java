package in.co.sachinverma.task.DataRefreshService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.app.NotificationCompat;

import in.co.sachinverma.task.MainActivity;
import in.co.sachinverma.task.Network.NetworkManager;
import in.co.sachinverma.task.Network.OnNetworkTask;
import in.co.sachinverma.task.R;
import in.co.sachinverma.task.Utils.Logger;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NetworkChangeReceiver extends BroadcastReceiver implements OnNetworkTask {

    private Context _context;

    @Override
    public void onReceive(Context context, Intent intent) {
        _context = context;
        String action = intent.getAction();
        Logger.i("CAME INSIDE RECEIVER");
        if(action.equals("android.net.conn.CONNECTIVITY_CHANGE") || action.equals("android.net.wifi.WIFI_STATE_CHANGED")){
            final ConnectivityManager connMgr = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            final android.net.NetworkInfo wifi = connMgr
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            final android.net.NetworkInfo mobile = connMgr
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (wifi.isAvailable() || mobile.isAvailable()) {
                //Make Network Call
                Logger.d("Network is available.");

                NetworkManager networkManager = new NetworkManager(context, this, false);
                networkManager.init();
            }
        }
    }

    @Override
    public void onNetworkTaskFinished() {
        try{
            Intent _intent = new Intent(_context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(_context, 1, _intent, PendingIntent.FLAG_UPDATE_CURRENT);

//            if (isNotificationVisible()){
                NotificationManager mNotificationManager = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID",
                            "YOUR_CHANNEL_NAME",
                            NotificationManager.IMPORTANCE_DEFAULT);
                    channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
                    mNotificationManager.createNotificationChannel(channel);
                }

                NotificationCompat.Builder builder = new NotificationCompat.Builder(_context, "YOUR_CHANNEL_ID")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Data Updated")
                        .setContentText("Check your data.")
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                builder.setContentIntent(pendingIntent);
                mNotificationManager.notify(0, builder.build());
//            }

        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
    }

   /* private boolean isNotificationVisible() {
        Intent notificationIntent = new Intent(_context, MainActivity.class);
        PendingIntent test = PendingIntent.getActivity(_context, 0, notificationIntent, PendingIntent.FLAG_NO_CREATE);
        return test != null;
    }*/

}
