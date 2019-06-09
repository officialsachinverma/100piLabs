package in.co.sachinverma.task.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;

import java.util.ArrayList;

import in.co.sachinverma.task.Database.DatabaseHandler;
import in.co.sachinverma.task.Model.Result;
import in.co.sachinverma.task.R;

public class TaskAppWidgetProvider extends AppWidgetProvider {

    private static final String ACTION_UPDATE_CLICK_NEXT = "action.UPDATE_CLICK_NEXT";
    private static final String ACTION_UPDATE_CLICK_PREVIOUS = "action.UPDATE_CLICK_PREVIOUS";
    private ArrayList<Result> _resultArrayList = new ArrayList<>();
    private DatabaseHandler _database;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        if (_database==null){
            _database = new DatabaseHandler(context.getApplicationContext(), null, null, 1);
            _database.CreateDatabase();
            _database.OpenDatabase();
            Cursor _cursor = _database.getCurrencyData();
            if (_cursor!=null && _cursor.moveToFirst()){
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
        }

        for (int i=0; i<appWidgetIds.length; i++){
            int widgetId = appWidgetIds[i];

            Intent intent = new Intent(context, TaskAppWidgetProvider.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews _remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget_main);

            if (_resultArrayList!=null && _resultArrayList.size() > 0) {
                _remoteViews.setTextViewText(R.id.tvWidgetCurrencyLong, _resultArrayList.get(0).getCurrencyLong());
                _remoteViews.setTextViewText(R.id.tvWidgetCurrency, _resultArrayList.get(0).getCurrency());
                _remoteViews.setTextViewText(R.id.tvWidgetTaxFee, String.format("%.5f", _resultArrayList.get(0).getTxFee()));
            } else {
                _remoteViews.setTextViewText(R.id.tvWidgetCurrencyLong, "No Data");
                _remoteViews.setTextViewText(R.id.tvWidgetCurrency, "No Data");
                _remoteViews.setTextViewText(R.id.tvWidgetTaxFee, "No Data");
            }

            Intent _intentNext = new Intent(context, TaskAppWidgetProvider.class);
            _intentNext.setAction(ACTION_UPDATE_CLICK_NEXT);
            _intentNext.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent _nextPendingIntent = PendingIntent.getBroadcast(context, 0, _intentNext,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Intent _intentPre = new Intent(context, TaskAppWidgetProvider.class);
            _intentPre.setAction(ACTION_UPDATE_CLICK_PREVIOUS);
            _intentPre.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent _prePendingIntent = PendingIntent.getBroadcast(context, 0, _intentPre,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            _remoteViews.setOnClickPendingIntent(R.id.btnWidgetPre, _prePendingIntent);
            _remoteViews.setOnClickPendingIntent(R.id.btnWidgetNext, _nextPendingIntent);
            appWidgetManager.updateAppWidget(widgetId, _remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    /*private PendingIntent getPendingSelfIntent(Context context, String action, int[] appWidgetIds) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }*/

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (_database==null){
            _database = new DatabaseHandler(context.getApplicationContext(), null, null, 1);
            _database.CreateDatabase();
            _database.OpenDatabase();
            Cursor _cursor = _database.getCurrencyData();
            if (_cursor!=null && _cursor.moveToFirst()){
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
        }

        if (_resultArrayList != null && _resultArrayList.size() > 0) {
            RemoteViews _remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget_main);

            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            int viewIndex = intent.getIntExtra("INDEX", 0);

            if (ACTION_UPDATE_CLICK_NEXT.equals(intent.getAction())) {
                viewIndex++;
                _remoteViews.setTextViewText(R.id.tvWidgetCurrencyLong, _resultArrayList.get(viewIndex).getCurrencyLong());
                _remoteViews.setTextViewText(R.id.tvWidgetCurrency, _resultArrayList.get(viewIndex).getCurrency());
                _remoteViews.setTextViewText(R.id.tvWidgetTaxFee, String.format("%.5f", _resultArrayList.get(viewIndex).getTxFee()));
            }
            else if (ACTION_UPDATE_CLICK_PREVIOUS.equals(intent.getAction())) {
                viewIndex--;
                if (viewIndex < 0){
                    viewIndex = 0;
                }
                _remoteViews.setTextViewText(R.id.tvWidgetCurrencyLong, _resultArrayList.get(viewIndex).getCurrencyLong());
                _remoteViews.setTextViewText(R.id.tvWidgetCurrency, _resultArrayList.get(viewIndex).getCurrency());
                _remoteViews.setTextViewText(R.id.tvWidgetTaxFee, String.format("%.5f", _resultArrayList.get(viewIndex).getTxFee()));
            }
            intent.putExtra("INDEX", viewIndex);

            Intent _intentNext = new Intent(context, TaskAppWidgetProvider.class);
            _intentNext.setAction(ACTION_UPDATE_CLICK_NEXT);
            _intentNext.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            _intentNext.putExtra("INDEX", viewIndex);
            _intentNext.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent _nextPendingIntent = PendingIntent.getBroadcast(context, 0, _intentNext,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Intent _intentPre = new Intent(context, TaskAppWidgetProvider.class);
            _intentPre.setAction(ACTION_UPDATE_CLICK_PREVIOUS);
            _intentPre.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            _intentPre.putExtra("INDEX", viewIndex);
            _intentPre.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent _prePendingIntent = PendingIntent.getBroadcast(context, 0, _intentPre,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            _remoteViews.setOnClickPendingIntent(R.id.btnWidgetPre, _prePendingIntent);
            _remoteViews.setOnClickPendingIntent(R.id.btnWidgetNext, _nextPendingIntent);

            AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, _remoteViews);
        }
    }
}
