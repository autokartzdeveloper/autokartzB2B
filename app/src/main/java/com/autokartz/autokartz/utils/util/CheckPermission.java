package com.autokartz.autokartz.utils.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.autokartz.autokartz.activities.MainDashboardActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2/2/2018.
 */

public class CheckPermission {

    private static boolean isDeviceSupportCamera(Context context) {
        // this device has a camera
// no camera on this device
        return context.getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    public static boolean checkAndRequestPermissions(Context mContext) {

        MainDashboardActivity activity = new MainDashboardActivity();


        if (!isDeviceSupportCamera(mContext)) {
            Toast.makeText(mContext.getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            activity.finish();
        }

        int camera = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);
        int storageWrite = ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int storageRead = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE);
        int phone = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (storageWrite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (storageRead != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (phone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) mContext, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }


}