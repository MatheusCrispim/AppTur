package com.br.apptur.view;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;
import android.widget.Toast;

import com.br.apptur.R;

/**
 * Created by treck on 26/07/18.
 */

public class MainActivity extends AppCompatActivity {

    SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        search = (SearchView) findViewById(R.id.searchView);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getBaseContext(), query, Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) { return false; }
        });

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




