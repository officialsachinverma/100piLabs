package in.co.sachinverma.task.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import in.co.sachinverma.task.Model.Result;
import in.co.sachinverma.task.Utils.Logger;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_PATH = Environment.getExternalStorageDirectory().getPath() + "/100piLabs/";
    private static final String DB_NAME = "Database.db";
    private SQLiteDatabase _database;

    //Tables
    public static final String TBL_CURRENCY = "Currency";

    //Columns
    public static final String KEY_id = "_id";
    public static final String KEY_CURRENCY = "currency";
    public static final String KEY_CURRENCY_LONG = "currencyLong";
    public static final String KEY_MIN_CONFIRMATION = "minConfirmation";
    public static final String KEY_TAX_FEE = "txFee";
    public static final String KEY_IS_ACTIVE = "isActive";
    public static final String KEY_IS_RESTRICTED = "isRestricted";
    public static final String KEY_COIN_TYPE = "coinType";
    public static final String KEY_BASE_ADDRESS = "baseAddress";
    public static final String KEY_NOTICE = "notice";

    String QUERY_CREATE_TABLE_CURRENCY = "CREATE TABLE IF NOT EXISTS " + TBL_CURRENCY + "("
            + KEY_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_CURRENCY + " TEXT,"
            + KEY_CURRENCY_LONG + " TEXT,"
            + KEY_MIN_CONFIRMATION + " INTEGER,"
            + KEY_TAX_FEE + " REAL,"
            + KEY_IS_ACTIVE + " INTEGER,"
            + KEY_IS_RESTRICTED + " INTEGER,"
            + KEY_COIN_TYPE + " TEXT,"
            + KEY_BASE_ADDRESS + " TEXT,"
            + KEY_NOTICE + " TEXT )";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_PATH + DB_NAME, null, DB_VERSION);
    }

    public void CreateDatabase() {
        try {
            _database = this.getReadableDatabase();
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
    }

    public void OpenDatabase() {
        try {
            _database = this.getWritableDatabase();
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(QUERY_CREATE_TABLE_CURRENCY);
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getCurrencyData(){
        return getWritableDatabase().rawQuery("SELECT * FROM " + TBL_CURRENCY, null);
    }

    public long saveCurrencyData(Result _result){
        ContentValues _cv = new ContentValues();
        _cv.put(KEY_CURRENCY, _result.getCurrency());
        _cv.put(KEY_CURRENCY_LONG, _result.getCurrencyLong());
        _cv.put(KEY_MIN_CONFIRMATION, _result.getMinConfirmation());
        _cv.put(KEY_TAX_FEE, _result.getTxFee());
        _cv.put(KEY_IS_ACTIVE, _result.getIsActive()? 1:0);
        _cv.put(KEY_IS_RESTRICTED, _result.getIsRestricted()?1:0);
        _cv.put(KEY_COIN_TYPE, _result.getCoinType());
        _cv.put(KEY_BASE_ADDRESS, _result.getBaseAddress());
        if (_result.getNotice() != null)
            _cv.put(KEY_NOTICE, _result.getNotice().toString());
        return getWritableDatabase().insert(TBL_CURRENCY, null, _cv);
    }
}
