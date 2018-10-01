package com.br.apptur.view;

/**
 * Created by treck on 24/07/18.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.br.apptur.object.Localidade;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;


import static android.content.Context.LOCATION_SERVICE;

public class GPSFragment extends SupportMapFragment implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener, LocationListener, GoogleMap.OnMarkerClickListener, GoogleMap.InfoWindowAdapter {

    private GoogleMap map;
    private LocationManager locationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            //user's locale manager
            this.locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
            this.map = googleMap;

            //Enable some settings for the maps
            this.map.setMyLocationEnabled(true);
            this.map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            this.map.setOnMarkerClickListener(this);
            this.map.getUiSettings().setZoomControlsEnabled(true);

            //Zoom into user's locale when launching app
            this.map.animateCamera(CameraUpdateFactory.zoomTo(18));


            //Loads the dynamic elements in the map
            LoadDynamicUi load = new LoadDynamicUi(map);
            //Get the user's locale
            Location[] locations = { locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) };
            Log.i("Locations", Double.toString(locations.length));
            //Executes the pool of threads that loads the Dynamic UI in the map
            load.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, locations);

        } catch (SecurityException ex) {
            //Carry out treatment after
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker)
    {
        return null;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        try {
            Localidade local = (Localidade) marker.getTag();
            Intent i = new Intent(getContext(), ShowInformation.class);

            i.putExtra("id", local.getId());
            i.putExtra("nome", local.getNome());
            i.putExtra("descricao", local.getDescricao());
            i.putExtra("imagem", local.getURLImagem());
            i.putExtra("latitude", local.getLatitude());
            i.putExtra("longitude", local.getLongitude());
            Log.e("Retorno", String.valueOf(i));
            startActivity(i);

            return true;
        }catch (Exception e){ return false; }
    }



    public void gerarNotificacao(String nome, String descricao)
    {
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 1, new Intent(getActivity(), GPSFragment.class), 0);

        Notification notification = new NotificationCompat.Builder(getActivity(), "0")
                //Title of the notification
                .setContentTitle(nome)
                //Content of the notification once opened
                .setContentText(descricao)
                //This bit will show up in the notification area in devices that support that
                .setTicker("Novo Ponto")
                //Icon that shows up in the notification area
                //.setSmallIcon(R.drawable.ic_notificacao)
                //Set the intent
                .setContentIntent(pendingIntent)
                //Auto-Clean Notification when clicked
                .setAutoCancel(true)
                //Add a Vibration
                .setVibrate(new long[]{150, 300} )
                //Build the notification with all the stuff you've just set.
                .build();

        NotificationManager notificationManager = (NotificationManager)
                getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, notification);
    }

}
