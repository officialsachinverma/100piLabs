package in.co.sachinverma.task.Network;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import in.co.sachinverma.task.MainActivity;
import in.co.sachinverma.task.Model.ResPojo;
import in.co.sachinverma.task.Model.Result;
import in.co.sachinverma.task.R;
import in.co.sachinverma.task.Utils.CustomProgressDialog;
import in.co.sachinverma.task.Utils.Logger;

public class NetworkManager {

    private String API = "https://bittrex.com/api/v1.1/public/getcurrencies";
    private CustomProgressDialog customProgressDialog = null;
    private Context _context;
    private OnNetworkTask _onNetworkTask;

    public NetworkManager(Context _context, OnNetworkTask _onNetworkTask){
        this._context = _context;
        this._onNetworkTask = _onNetworkTask;
    }

    public void init(){
        new Network().execute();
    }

    class Network extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgressDialog = CustomProgressDialog.getInstance(_context);
            customProgressDialog.setMessage("Please wait...");
            customProgressDialog.setTitle(_context.getResources().getString(R.string.downloading));
            customProgressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String _reponse = "";
            try{
                URL _url = new URL(API);
                HttpURLConnection _urlConnection = (HttpURLConnection) _url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(_urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    Gson _gson = new Gson();
                    ResPojo _resPojo = _gson.fromJson(stringBuilder.toString(), ResPojo.class);
                    if (_resPojo.getSuccess()){
                        _reponse = "" + _resPojo.getSuccess();
                        List<Result> _resultArrayList = _resPojo.getResult();
                        for(Result _result: _resultArrayList){
                            MainActivity._database.saveCurrencyData(_result);
                        }
                    } else {
                        _reponse = null;
                    }
                }
                finally{
                    _urlConnection.disconnect();
                }
            } catch (Exception e){
                Logger.e(e.getMessage());
                _reponse = null;
            }

            return _reponse;
        }

        protected void onPostExecute(String response) {
            if (customProgressDialog != null) {
                customProgressDialog.mProgessDismiss();
            }
            if(response != null) {
                Toast.makeText(_context, "Download success.", Toast.LENGTH_LONG).show();
                if (_onNetworkTask != null)
                    _onNetworkTask.onNetworkTaskFinished();
            } else {
                Toast.makeText(_context, "Download failed.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
