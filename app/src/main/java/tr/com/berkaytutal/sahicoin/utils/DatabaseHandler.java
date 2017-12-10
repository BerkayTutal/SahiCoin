package tr.com.berkaytutal.sahicoin.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import tr.com.berkaytutal.sahicoin.model.AlarmPOJO;
import tr.com.berkaytutal.sahicoin.model.PricePOJO;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "coin";

    private static final String TABLE_HISTORY = "history";
    private static final String TABLE_ALARMS = "alarms";

    private static final String KEY_ID = "id";
    private static final String KEY_DATA = "data";

    private static final String KEY_VALUE = "name";
    private static final String KEY_TIMESTAMP = "timestamp";

    private Gson gson;
    private Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        gson = new Gson();
        this.context = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COIN_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_HISTORY + "("
                + KEY_TIMESTAMP + " LONG PRIMARY KEY NOT NULL,"
                + KEY_VALUE + " LONG NOT NULL"
                + ")";
        db.execSQL(CREATE_COIN_TABLE);


        String CREATE_ALARMS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ALARMS + "("
                + KEY_ID + " TEXT PRIMARY KEY NOT NULL,"
                + KEY_DATA + " BLOB NOT NULL"
                + ")";
        db.execSQL(CREATE_COIN_TABLE);
        db.execSQL(CREATE_ALARMS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARMS);

        onCreate(db);
    }


    public void addAlarm(AlarmPOJO alarmPOJO) {

        Gson gson = new Gson();
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID, alarmPOJO.getUUID());
        values.put(KEY_DATA, gson.toJson(alarmPOJO));

        db.insert(TABLE_ALARMS, null, values);
        db.close();

    }

    public void deleteAlarm(AlarmPOJO alarmPOJO){
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_ALARMS, KEY_ID + " = ?",
                    new String[]{alarmPOJO.getUUID()});
            db.close();
    }
    public AlarmPOJO getSingleAlarm(String uuid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ALARMS, new String[]{KEY_DATA}, KEY_ID + "=?",
                new String[]{uuid}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            AlarmPOJO alarmPOJO = gson.fromJson(cursor.getString(0), AlarmPOJO.class);
            // return contact
            return alarmPOJO;

        } else {
            return null;
        }

    }

    public List<AlarmPOJO> getAllAlarms() {
        List<AlarmPOJO> alarmList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ALARMS;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AlarmPOJO sound = gson.fromJson(cursor.getString(1), AlarmPOJO.class);
                alarmList.add(sound);
            } while (cursor.moveToNext());
        }

        // return contact list
        return alarmList;
    }


    public void addPrice(PricePOJO price) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TIMESTAMP, price.getDate());
        values.put(KEY_VALUE, price.getValue());

        db.insert(TABLE_HISTORY, null, values);
        db.close();
    }


    public void addAllPrices(List<PricePOJO> prices) {
        for (PricePOJO price :
                prices) {
            addPrice(price);
        }
    }

    public PricePOJO getLatestPrice() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_HISTORY, new String[]{KEY_VALUE, KEY_TIMESTAMP}, null, null, null, null, KEY_TIMESTAMP + " ASC", "1");
//
//        Cursor cursor = db.query(TABLE_HISTORY, new String[]{KEY_DATA}, KEY_VALUE + "=?",
//                new String[]{resourceName}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            PricePOJO pricePOJO = new PricePOJO(cursor.getDouble(0), cursor.getLong(1));
            // return contact
            return pricePOJO;

        } else {
            return null;
        }

    }

    public List<PricePOJO> getAllPrices() {
        List<PricePOJO> priceList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_HISTORY;

        SQLiteDatabase db = this.getWritableDatabase();

        String orderBy = " ORDER BY " + KEY_TIMESTAMP + " ASC";
        Cursor cursor = db.rawQuery(selectQuery + orderBy, null);

        if (cursor.moveToFirst()) {
            do {
                PricePOJO pricePOJO = new PricePOJO(cursor.getDouble(1), cursor.getLong(0));
                priceList.add(pricePOJO);
            } while (cursor.moveToNext());
        }

        return priceList;
    }

}