package com.br.apptur.view;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.br.apptur.R;
import com.br.apptur.control.Controller;
import com.br.apptur.control.Permissions;
import com.br.apptur.model.exception.NadaEncontradoException;
import com.br.apptur.model.restful.ReadRest;
import com.br.apptur.object.Localidade;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by treck on 26/07/18.
 */

public class MainActivity extends AppCompatActivity {


    private Permissions permissions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //this.permissions.setAccessFineLocation(this);


        //Check if permission already is active
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        //If permission is not active, is solicited the permission
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // ask permissions here using below code
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    102);
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.map, new GPSFragment(), "GPSFragment");
        fragmentTransaction.commit();

    }


}



