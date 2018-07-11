package com.autokartz.autokartz.services.databases.LocalDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.autokartz.autokartz.utils.pojoClasses.CarInformation;
import com.autokartz.autokartz.utils.pojoClasses.CatItem;
import com.autokartz.autokartz.utils.pojoClasses.CategoryInformation;
import com.autokartz.autokartz.utils.pojoClasses.UserDetails;
import com.autokartz.autokartz.utils.util.Logger;
import com.autokartz.autokartz.utils.util.constants.AppConstantKeys;

import java.util.ArrayList;

/**
 * Created by user on 1/3/2018.
 */

public class DatabaseCURDOperations {


    private Context mContext;
    private static final String LOG_TAG = DatabaseCURDOperations.class.getName();
    private DataBaseHelper dataBaseHelper;

    public DatabaseCURDOperations(Context context) {
        mContext = context;
        dataBaseHelper=new DataBaseHelper(mContext);
    }

    public void insertUserInfo(UserDetails userDetails) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();

        if (sqLiteDatabase != null && sqLiteDatabase.isOpen() && !sqLiteDatabase.isReadOnly()) {
            try {
                String query = "insert into " + AppConstantKeys.USER_TABLE + " ("
                        + AppConstantKeys.USER_NAME + ","
                        + AppConstantKeys.USER_EMAIL + ","
                        + AppConstantKeys.USER_PASSWORD + ","
                        + AppConstantKeys.USER_MOBILE + ") values (?,?,?,?)";
                SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(query);
                insertStmt.clearBindings();
                insertStmt.bindString(1, userDetails.getUserName());
                insertStmt.bindString(2, userDetails.getUserEmail());
                insertStmt.bindString(3, userDetails.getUserPassword());
                insertStmt.bindString(4, userDetails.getUserMobile());

                insertStmt.executeInsert();
                Logger.LogDebug(LOG_TAG, "New User Inserted Successfully");
            } catch (Exception ex) {
                Logger.LogDebug(LOG_TAG, ex.getMessage());
            }
            dataBaseHelper.close();
            sqLiteDatabase.close();
        }
    }

    private void insertCarBrands(String brandName) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();

        if (sqLiteDatabase != null && sqLiteDatabase.isOpen() && !sqLiteDatabase.isReadOnly()) {
            try {
                String query = "insert into " + AppConstantKeys.CAR_BRAND_TABLE + " ("
                        + AppConstantKeys.CAR_BRAND_NAME + ") values (?)";
                SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(query);
                insertStmt.clearBindings();
                insertStmt.bindString(1, brandName);
                insertStmt.executeInsert();
                Logger.LogDebug(LOG_TAG, "Brand Added Successfully");
            } catch (Exception ex) {
                Logger.LogDebug(LOG_TAG, ex.getMessage());
            }
            dataBaseHelper.close();
            sqLiteDatabase.close();
        }
    }

    public void insertCarInfo(CarInformation carInformation) {
        String brandName = carInformation.getmBarnd();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        long car_brand_info_id = getBrandId(brandName);
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen() && !sqLiteDatabase.isReadOnly()) {
            try {
                String query = "insert into " + AppConstantKeys.CAR_INFO_TABLE + " ("
                        + AppConstantKeys.CAR_INFO_BRAND_ID + ","
                        + AppConstantKeys.CAR_MODEL_NAME + ","
                        + AppConstantKeys.CAR_VARIANT + ","
                        + AppConstantKeys.CAR_ENGINE + ","
                        + AppConstantKeys.CAR_YEAR + ""
                        + ") values (?,?,?,?,?)";
                SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(query);
                insertStmt.clearBindings();
                insertStmt.bindLong(1, car_brand_info_id);
                insertStmt.bindString(2, carInformation.getmModel());
                insertStmt.bindString(3, carInformation.getmVariant());
                insertStmt.bindString(4, carInformation.getmEnginne());
                insertStmt.bindString(5, carInformation.getmYear());
                insertStmt.executeInsert();
                Logger.LogDebug(LOG_TAG, "Car Info Added Successfully");
            } catch (Exception ex) {
                Logger.LogDebug(LOG_TAG, ex.getMessage());
            }
            dataBaseHelper.close();
            sqLiteDatabase.close();
        }
    }

    public void insertCatInfo(CategoryInformation catInfo) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen() && !sqLiteDatabase.isReadOnly()) {
            try {
                String query = "insert into " + AppConstantKeys.CAT_PART_TABLE + " ("
                        + AppConstantKeys.CAT_ID + ","
                        + AppConstantKeys.CAT_NAME + ","
                        + AppConstantKeys.SUB_CAT_ID+ ","
                        + AppConstantKeys.SUB_CAT_NAME + ","
                        + AppConstantKeys.PART_ID + ","
                        + AppConstantKeys.PART_NAME+ ""
                        + ") values (?,?,?,?,?,?)";
                SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(query);
                insertStmt.clearBindings();
                insertStmt.bindString(1,catInfo.getmCatId());
                insertStmt.bindString(2,catInfo.getmCatName());
                insertStmt.bindString(3,catInfo.getmSubCatId());
                insertStmt.bindString(4,catInfo.getmSubCatName());
                insertStmt.bindString(5,catInfo.getmPartId());
                insertStmt.bindString(6,catInfo.getmPartName());
                insertStmt.executeInsert();
                Logger.LogDebug(LOG_TAG, "Part Info Added Successfully");
            } catch (Exception ex) {
                Logger.LogDebug(LOG_TAG, ex.getMessage());
            }
            dataBaseHelper.close();
            sqLiteDatabase.close();
        }
    }

    public long deleteUserInfo(UserDetails userDetails) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        String query = AppConstantKeys.USER_EMAIL + "=\"" + userDetails.getUserEmail() + "\"";
        return sqLiteDatabase.delete(AppConstantKeys.USER_TABLE, query, null);
    }

    public void deleteCarInfo() {
        deleteCarBrand();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+AppConstantKeys.CAR_INFO_TABLE);
        dataBaseHelper.close();
        sqLiteDatabase.close();
    }

    public void deleteCatPartInfo() {
        //deleteCarBrand();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+AppConstantKeys.CAT_PART_TABLE);
        dataBaseHelper.close();
        sqLiteDatabase.close();
    }

    public void deleteCarBrand() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+AppConstantKeys.CAR_BRAND_TABLE);
        dataBaseHelper.close();
        sqLiteDatabase.close();
    }

    public UserDetails getUserInfo(String userEmail) {
        String selectQuery = "SELECT * FROM " + AppConstantKeys.USER_TABLE + " where " + AppConstantKeys.USER_EMAIL + " =='" + userEmail + "'";
        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        UserDetails userDetails = new UserDetails();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String email = cursor.getString(cursor.getColumnIndex(AppConstantKeys.USER_EMAIL));
                if (email.equalsIgnoreCase(userEmail)) {
                    String id = cursor.getString(cursor.getColumnIndex(AppConstantKeys.USER_ID));
                    String name = cursor.getString(cursor.getColumnIndex(AppConstantKeys.USER_NAME));
                    String pass = cursor.getString(cursor.getColumnIndex(AppConstantKeys.USER_PASSWORD));
                    String mobile = cursor.getString(cursor.getColumnIndex(AppConstantKeys.USER_MOBILE));
                    userDetails = new UserDetails(name, pass, email, mobile);
                    break;
                }
            }
            cursor.close();
        }
        dataBaseHelper.close();
        sqLiteDatabase.close();
        return userDetails;
    }

    public ArrayList<CatItem> getCarCategories() {
        String selectQuery = "SELECT DISTINCT " + AppConstantKeys.CAT_NAME + ","+AppConstantKeys.CAT_ID+" FROM " + AppConstantKeys.CAT_PART_TABLE;
        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        ArrayList<CatItem> catList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id=cursor.getString(cursor.getColumnIndex(AppConstantKeys.CAT_ID));
                String name=cursor.getString(cursor.getColumnIndex(AppConstantKeys.CAT_NAME));
                catList.add(new CatItem(id,name));
            }
            cursor.close();
        }
        dataBaseHelper.close();
        sqLiteDatabase.close();
        return catList;
    }

    public ArrayList<CatItem> getSubCategories(String catId) {
        String selectQuery = "SELECT DISTINCT " + AppConstantKeys.SUB_CAT_NAME +", "+AppConstantKeys.SUB_CAT_ID + " FROM " + AppConstantKeys.CAT_PART_TABLE +
                " WHERE " + AppConstantKeys.CAT_ID + " = '" + catId + "'";
        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        ArrayList<CatItem> subCatList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id=cursor.getString(cursor.getColumnIndex(AppConstantKeys.SUB_CAT_ID));
                String name=cursor.getString(cursor.getColumnIndex(AppConstantKeys.SUB_CAT_NAME));
                subCatList.add(new CatItem(id,name));
            }
            cursor.close();
        }
        dataBaseHelper.close();
        sqLiteDatabase.close();
        return subCatList;
    }

    public ArrayList<CatItem> getCarParts(String subCatId) {
        String selectQuery = "SELECT DISTINCT " + AppConstantKeys.PART_NAME + ", "+AppConstantKeys.PART_ID+" FROM " + AppConstantKeys.CAT_PART_TABLE + " WHERE "
                + AppConstantKeys.SUB_CAT_ID + " = '" + subCatId+"'";
        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        ArrayList<CatItem> partList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id=cursor.getString(cursor.getColumnIndex(AppConstantKeys.PART_ID));
                String name=cursor.getString(cursor.getColumnIndex(AppConstantKeys.PART_NAME));
                partList.add(new CatItem(id,name));
            }
            cursor.close();
        }
        dataBaseHelper.close();
        sqLiteDatabase.close();
        return partList;
    }

    private long getBrandId(String brandName) {
        String selectQuery = "SELECT " + AppConstantKeys.CAR_BRAND_ID + " FROM " + AppConstantKeys.CAR_BRAND_TABLE + " WHERE "
                + AppConstantKeys.CAR_BRAND_NAME + " = '" + brandName + "'";
        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0) {
            long car_brand_info_id = 0;
            while (cursor.moveToNext()) {
                car_brand_info_id = cursor.getInt(cursor.getColumnIndex(AppConstantKeys.CAR_BRAND_ID));
            }
            cursor.close();
            return car_brand_info_id;
        }
        insertCarBrands(brandName);
        return getBrandId(brandName);
    }

    public ArrayList<String> getCarBrands() {
        String selectQuery = "SELECT DISTINCT " + AppConstantKeys.CAR_BRAND_NAME + " FROM " + AppConstantKeys.CAR_BRAND_TABLE;
        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        ArrayList<String> brandList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                brandList.add(cursor.getString(cursor.getColumnIndex(AppConstantKeys.CAR_BRAND_NAME)));
            }
            cursor.close();
        }
        dataBaseHelper.close();
        sqLiteDatabase.close();
        return brandList;
    }

    public ArrayList<String> getCarModels(String brandName) {
        long car_info_brand_id = getBrandId(brandName);
        String selectQuery = "SELECT DISTINCT " + AppConstantKeys.CAR_MODEL_NAME + " FROM " + AppConstantKeys.CAR_INFO_TABLE +
                " WHERE " + AppConstantKeys.CAR_INFO_BRAND_ID + " = '" + car_info_brand_id + "'";
        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        ArrayList<String> modelList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                modelList.add(cursor.getString(cursor.getColumnIndex(AppConstantKeys.CAR_MODEL_NAME)));
            }
            cursor.close();
        }
        dataBaseHelper.close();
        sqLiteDatabase.close();
        return modelList;
    }

    public ArrayList<String> getCarVariants(String brandName, String modelName) {
        long car_info_brand_id = getBrandId(brandName);
        String selectQuery = "SELECT DISTINCT " + AppConstantKeys.CAR_VARIANT + " FROM " + AppConstantKeys.CAR_INFO_TABLE + " WHERE "
                + AppConstantKeys.CAR_INFO_BRAND_ID + " = '" + car_info_brand_id + "' AND "
                + AppConstantKeys.CAR_MODEL_NAME + " = '" + modelName + "'";
        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        ArrayList<String> variantList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                variantList.add(cursor.getString(cursor.getColumnIndex(AppConstantKeys.CAR_VARIANT)));
            }
            cursor.close();
        }
        dataBaseHelper.close();
        sqLiteDatabase.close();
        return variantList;
    }

    public ArrayList<String> getCarEngine(String brandName, String modelName, String variantName) {
        long car_info_brand_id = getBrandId(brandName);
        String selectQuery = "SELECT DISTINCT " + AppConstantKeys.CAR_ENGINE + " FROM " + AppConstantKeys.CAR_INFO_TABLE + " WHERE "
                + AppConstantKeys.CAR_INFO_BRAND_ID + " = '" + car_info_brand_id + "' AND "
                + AppConstantKeys.CAR_MODEL_NAME + " = '" + modelName + "' AND "
                + AppConstantKeys.CAR_VARIANT + " = '" + variantName + "'";
        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        ArrayList<String> engineList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                engineList.add(cursor.getString(cursor.getColumnIndex(AppConstantKeys.CAR_ENGINE)));
            }
            cursor.close();
        }
        dataBaseHelper.close();
        sqLiteDatabase.close();
        return engineList;
    }

    public ArrayList<String> getCarYear(String brandName, String modelName, String variantName,String engineName) {
        long car_info_brand_id = getBrandId(brandName);
        String selectQuery = "SELECT DISTINCT " + AppConstantKeys.CAR_YEAR + " FROM " + AppConstantKeys.CAR_INFO_TABLE + " WHERE "
                + AppConstantKeys.CAR_INFO_BRAND_ID + " = '" + car_info_brand_id + "' AND "
                + AppConstantKeys.CAR_MODEL_NAME + " = '" + modelName + "' AND "
                + AppConstantKeys.CAR_VARIANT + " = '" + variantName + "' AND "
                + AppConstantKeys.CAR_ENGINE + " = '" + engineName + "'";
        DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        ArrayList<String> yearList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                yearList.add(cursor.getString(cursor.getColumnIndex(AppConstantKeys.CAR_YEAR)));
            }
            cursor.close();
        }
        dataBaseHelper.close();
        sqLiteDatabase.close();
        return yearList;
    }

}