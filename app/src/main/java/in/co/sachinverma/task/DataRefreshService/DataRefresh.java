package in.co.sachinverma.task.DataRefreshService;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import in.co.sachinverma.task.Database.DatabaseHandler;

public class DataRefresh extends Service {

    private NetworkChangeReceiver _Receiver;

    @Override
    public void onCreate() {
        super.onCreate();
        _Receiver = new NetworkChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(_Receiver, filter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(_Receiver);
    }
}
