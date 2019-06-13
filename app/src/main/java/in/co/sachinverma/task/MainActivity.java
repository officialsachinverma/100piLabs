package in.co.sachinverma.task;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.co.sachinverma.task.AdapterAndListeners.CurrencyAdapter;
import in.co.sachinverma.task.DataRefreshService.DataRefresh;
import in.co.sachinverma.task.Database.DatabaseHandler;
import in.co.sachinverma.task.Model.Result;
import in.co.sachinverma.task.Network.NetworkManager;
import in.co.sachinverma.task.Network.OnNetworkTask;
import in.co.sachinverma.task.Utils.CustomProgressDialog;
import in.co.sachinverma.task.Utils.Logger;
import in.co.sachinverma.task.Utils.Utils;

public class MainActivity extends AppCompatActivity implements OnNetworkTask {

    public DatabaseHandler _database;
    private Context _context;
    private ArrayList<Result> _resultArrayList;
    private CurrencyAdapter _adapter;
    private CustomProgressDialog customProgressDialog = null;

    @BindView(R.id.rvCurrency)
    RecyclerView _rvCurrency;
    @BindView(R.id.tvStatus)
    TextView tvStatus;

    void init(){
        _context = MainActivity.this;
        _database = new DatabaseHandler(_context, null, null, 1);
        _database.CreateDatabase();
        _database.OpenDatabase();
        _resultArrayList = new ArrayList<>();

        Intent serviceIntent = new Intent(this, DataRefresh.class);
        startService(serviceIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (requestAppPermissions()) {
            init();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (_database != null) {
            Cursor _cursor = _database.getCurrencyData();
            if (_cursor != null && _cursor.moveToFirst()){
                displayCurrencyList();
            } else {
                if (isWifiConnected()) {
                    NetworkManager networkManager = new NetworkManager(_context, MainActivity.this, true);
                    networkManager.init();
                } else {
                    Toast.makeText(_context, getResources().getString(R.string.network_not_connected_msg), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(_database != null) {
            _database.close();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100){
            if (hasWritePermissions() && hasReadPermissions()){
                init();
                onResume();
            } else {
                requestAppPermissions();
            }
        }
    }

    private boolean isWifiConnected() {
        boolean isWifiConnected = false;
        try {
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (mWifi.isConnected() || mMobile.isConnected()) {
                isWifiConnected = true;
            }

        } catch (Exception e) {
            isWifiConnected = false;
        } finally {
            return isWifiConnected;
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onNetworkTaskFinished() {
        if (customProgressDialog != null)
            customProgressDialog.dismiss();
        displayCurrencyList();
    }

    void displayCurrencyList(){
        try{
            new FetchList().execute();
        } catch (Exception e){
            Logger.e(e.getMessage());
        }
    }

    private class FetchList extends AsyncTask{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgressDialog = CustomProgressDialog.getInstance(_context);
            customProgressDialog.setMessage("Please wait...");
            customProgressDialog.setTitle(_context.getResources().getString(R.string.loding));
            customProgressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            Cursor _cursor = _database.getCurrencyData();
            if (_cursor != null && _cursor.moveToFirst()){
                do {
                    Result _result = new Result();
                    _result.setCurrency(_cursor.getString(_cursor.getColumnIndex(DatabaseHandler.KEY_CURRENCY)));
                    _result.setCurrencyLong(_cursor.getString(_cursor.getColumnIndex(DatabaseHandler.KEY_CURRENCY_LONG)));
                    _result.setMinConfirmation(_cursor.getInt(_cursor.getColumnIndex(DatabaseHandler.KEY_MIN_CONFIRMATION)));
                    _result.setTxFee(_cursor.getDouble(_cursor.getColumnIndex(DatabaseHandler.KEY_TAX_FEE)));
                    _result.setIsActive(_cursor.getInt(_cursor.getColumnIndex(DatabaseHandler.KEY_IS_ACTIVE))==1?true:false);
                    _result.setIsRestricted( _cursor.getInt(_cursor.getColumnIndex(DatabaseHandler.KEY_IS_RESTRICTED))==1?true:false);
                    _result.setCoinType(_cursor.getString(_cursor.getColumnIndex(DatabaseHandler.KEY_COIN_TYPE)));
                    _result.setBaseAddress(_cursor.getString(_cursor.getColumnIndex(DatabaseHandler.KEY_BASE_ADDRESS)));
                    _result.setNotice(_cursor.getString(_cursor.getColumnIndex(DatabaseHandler.KEY_NOTICE)));
                    _resultArrayList.add(_result);
                } while (_cursor.moveToNext());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (customProgressDialog != null) {
                customProgressDialog.mProgessDismiss();
            }
            if (_resultArrayList.size() > 0){
                tvStatus.setVisibility(View.GONE);
                _rvCurrency.setVisibility(View.VISIBLE);
                _rvCurrency.setLayoutManager(new LinearLayoutManager(_context));
                _adapter = new CurrencyAdapter(_context, _resultArrayList);
                _rvCurrency.setAdapter(_adapter);
            } else {
                tvStatus.setVisibility(View.VISIBLE);
                _rvCurrency.setVisibility(View.GONE);
            }
        }
    }

    private boolean requestAppPermissions() {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }

        if (hasReadPermissions() && hasWritePermissions()) {
            return true;
        }

        ActivityCompat.requestPermissions(this,
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, Utils.REQUEST_WRITE_STORAGE_REQUEST_CODE);

        return false;
    }

    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

}
