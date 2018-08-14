package com.br.apptur.view;

import android.location.Location;
import android.os.AsyncTask;

import com.br.apptur.control.Controller;
import com.br.apptur.model.exception.NothingFounException;
import com.br.apptur.model.restful.ReadRest;
import com.br.apptur.object.Localidade;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by treck on 26/07/18.
 */

public class LoadDynamicUi extends AsyncTask<Location, MarkerOptions, Void> {
    private Controller controller;
    private GoogleMap map;
    //private MapsFunction mapsFunction;

    public LoadDynamicUi(GoogleMap map){
        this.map=map;
      //  this.mapsFunction=new MapsFunction(map);
        this.controller=new Controller();
    }

    @Override
    protected Void doInBackground(Location... locations) {

        ArrayList<Localidade> local = null;
        LatLng latLng=new LatLng(locations[0].getLatitude(), locations[0].getLongitude());
        try {
            local = (ArrayList<Localidade>) controller.getLocalidadesProximas(-6.80663, -35.0772);
            LatLng latg;
            MarkerOptions[] markerOptions;
            for(Localidade localidade : local) {
                latg = new LatLng(localidade.getLatitude(), localidade.getLongitude());
                markerOptions = new MarkerOptions[]{new MarkerOptions().position(latg).title(localidade.getNome())};

                publishProgress(markerOptions);
                // A cada novo ponto adicionado, uma nova notificação é lançada
                //gerarNotificacao(localidade.getNome(), localidade.getDescricao());
            }

        } catch (NothingFounException e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    protected void onProgressUpdate(MarkerOptions...markerOptions) {

        Marker ponto = map.addMarker(markerOptions[0]);
        //LatLng sydney = new LatLng(-33.852, 151.211);
        //map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //ponto.showInfoWindow();

        //ponto.setTag(localidade);
    }

}
