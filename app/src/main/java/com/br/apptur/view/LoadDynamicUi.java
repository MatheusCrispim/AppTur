package com.br.apptur.view;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.br.apptur.control.Controller;
import com.br.apptur.model.exception.NothingFounException;
import com.br.apptur.object.Localidade;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by treck on 26/07/18.
 */

public class LoadDynamicUi extends AsyncTask<Location, Object, Void> {

    private Controller controller;
    private GoogleMap map;
    private double latitude, longitude;
    private  Marker marker;


    public LoadDynamicUi(GoogleMap map){
        this.controller=new Controller();
        this.map=map;
    }


    @Override
    protected Void doInBackground(Location... locations) {

        try {

            this.latitude=locations[0].getLatitude();
            this.longitude=locations[0].getLongitude();

            List<Localidade> localidades = controller.getLocalidadesProximas(this.latitude,  this.longitude);

            LatLng latg;
            Object[] objects;

            for(Localidade localidade : localidades) {

                latg = new LatLng(localidade.getLatitude(), localidade.getLongitude());
                objects = new Object[]{new MarkerOptions().position(latg).title(String.valueOf(localidade.getId())), localidade};
                publishProgress(objects);

            }

        } catch (NothingFounException e) {
            //Realizar tratamento de forma diferente
        }

        return null;
    }


    @Override
    protected void onProgressUpdate(Object...objects) {

        this.marker=map.addMarker((MarkerOptions) objects[0]);
        this.marker.setTag((Localidade) objects[1]);
        Localidade ld= (Localidade) objects[1];
        Log.e("Retornou", String.valueOf(ld.getId()));
        //LatLng sydney = new LatLng(-33.852, 151.211);
        //map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //ponto.showInfoWindow();
    }

}
