package com.br.apptur.control;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by treck on 26/07/18.
 */

public class Permissions {


    public void setAccessFineLocation(Activity activity){
        //Check if permission already is active
        int permissionCheck = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION);

        //If permission is not active, is solicited the permission
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // ask permissions here using below code
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    102);
        }
    }


}

