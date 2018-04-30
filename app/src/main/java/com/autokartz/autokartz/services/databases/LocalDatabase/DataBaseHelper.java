package com.autokartz.autokartz.services.databases.LocalDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.autokartz.autokartz.utils.util.constants.AppConstantKeys;

/**
 * Created by user on 1/3/2018.
 */
public class DataBaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME="autokartz.db";
    private static final int DATABASE_VERSION=1;
    private SQLiteDatabase sqLiteDatabase;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when no database exists in disk and the helper class needs
// to create a new one.
    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableUser(db);
        createCarBrandTable(db);
        createCarInfoTable(db);
        createCatPartInfoTable(db);
    }

    private void createCarBrandTable(SQLiteDatabase db) {
        String CREATE_CAR_BRANDS_TABLE="CREATE TABLE IF NOT EXISTS "
                + AppConstantKeys.CAR_BRAND_TABLE+" ( "
                + AppConstantKeys.CAR_BRAND_ID+" INTEGER NOT NULL PRIMARY KEY, "
                + AppConstantKeys.CAR_BRAND_NAME+" TEXT "
                + ")";
        db.execSQL(CREATE_CAR_BRANDS_TABLE);
    }

    private void createCarInfoTable(SQLiteDatabase db) {
        String CREATE_CAR_INFO_TABLE="CREATE TABLE IF NOT EXISTS "
                + AppConstantKeys.CAR_INFO_TABLE +" ( "
                + AppConstantKeys.CAR_INFO_ID +" INTEGER NOT NULL PRIMARY KEY, "
                + AppConstantKeys.CAR_INFO_BRAND_ID + " INTEGER, "
                + AppConstantKeys.CAR_MODEL_NAME + " TEXT, "
                + AppConstantKeys.CAR_VARIANT + " TEXT, "
                + AppConstantKeys.CAR_ENGINE + " TEXT, "
                + AppConstantKeys.CAR_YEAR + " TEXT, FOREIGN KEY ("+ AppConstantKeys.CAR_INFO_BRAND_ID +") REFERENCES "+ AppConstantKeys.CAR_BRAND_TABLE + " ("+ AppConstantKeys.CAR_BRAND_ID +")"
                + ")";
        db.execSQL(CREATE_CAR_INFO_TABLE);
    }

    private void createCatPartInfoTable(SQLiteDatabase db) {
        String CREATE_CAT_PART_TABLE="CREATE TABLE IF NOT EXISTS "
                + AppConstantKeys.CAT_PART_TABLE +" ( "
                + AppConstantKeys.CAT_PART_ID +" INTEGER NOT NULL PRIMARY KEY, "
                + AppConstantKeys.CAT_ID + " TEXT, "
                + AppConstantKeys.CAT_NAME + " TEXT, "
                + AppConstantKeys.SUB_CAT_ID + " TEXT, "
                + AppConstantKeys.SUB_CAT_NAME + " TEXT, "
                + AppConstantKeys.PART_ID + " TEXT, "
                + AppConstantKeys.PART_NAME + " TEXT "+")";
                db.execSQL(CREATE_CAT_PART_TABLE);
    }

    private void createTableUser(SQLiteDatabase db) {
        String CREATE_USER_TABLE="CREATE TABLE IF NOT EXISTS "
                + AppConstantKeys.USER_TABLE+" ( "
                + AppConstantKeys.USER_ID +" INTEGER NOT NULL PRIMARY KEY, "
                + AppConstantKeys.USER_NAME + " TEXT, "
                + AppConstantKeys.USER_PASSWORD + " TEXT, "
                + AppConstantKeys.USER_EMAIL + " TEXT, "
                +AppConstantKeys.USER_MOBILE + " TEXT "
                +")";
        db.execSQL(CREATE_USER_TABLE);
    }

    // Called when there is a database version mismatch meaning that the version
// of the database on disk needs to be upgraded to the current version.
    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
// Log the version upgrade.
        Log.w("TaskDBAdapter", "Upgrading from version" + _oldVersion + "to " + _newVersion + ", which will destroy all old data");

// Upgrade the existing database to conform to the new version. Multiple
// previous versions can be handled by comparing _oldVersion and _newVersion
// values.
// The simplest case is to drop the old table and create a new one.
        _db.execSQL(" DROP TABLE If EXISTS " + "TEMPLATE ");
// Create a new one.
        onCreate(_db);
    }

    public void openDatabase() {
        sqLiteDatabase=getWritableDatabase();
    }

    public void closeDatabase() {
        if(sqLiteDatabase!=null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }

    public SQLiteDatabase getDatabaseReadable() {
        return this.getReadableDatabase();
    }

}