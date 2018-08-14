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

import com.br.apptur.control.Controller;
import com.br.apptur.model.exception.NothingFounException;
import com.br.apptur.model.restful.LoadLocalidade;
import com.br.apptur.object.Localidade;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;

public class GPSFragment extends SupportMapFragment implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener, LocationListener, GoogleMap.OnMarkerClickListener, GoogleMap.InfoWindowAdapter {

    private static final String TAG = "GPSFragment";
    private int num = 0;
    private Controller controller = new Controller();
    private GoogleMap mMap;
    private LocationManager locationManager;
    PolylineOptions rectOptions = new PolylineOptions();
    ArrayList<LatLng> rotas = new ArrayList<LatLng>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

            mMap = googleMap;
            mMap.setOnMapClickListener(this);

            // Botão de zoom
            mMap.getUiSettings().setZoomControlsEnabled(true);

            // Ativa uma opção para buscar a localização
            mMap.setMyLocationEnabled(true);

            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            mMap.animateCamera(CameraUpdateFactory.zoomTo(18));


            locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

            LoadDynamicUi load=new LoadDynamicUi(mMap);
            Location[] locations= {locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)};


            //cria uma piscina de execuções, onde varias threads podem ser executadas simultaneamente.
            load.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, locations);

            /*Location[] locations= {locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)};
            LoadDynamicUi loadDynamicUi=new LoadDynamicUi(mMap);
            loadDynamicUi.execute(locations);
            */

            // Log.e(TAG, "Irr", ex);
            //LatLng sydney = new LatLng(-33.852, 151.211);
            //googleMap.addMarker(new MarkerOptions().position(sydney)
              ///      .title("Marker in Sydney"));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        } catch (SecurityException ex) {
            Log.e(TAG, "Error", ex);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
    }

    @Override
    public void onResume() {
        super.onResume();

        // Ativa o GPS
        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

      //  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, this);

    }

    @Override
    public void onPause() {
        super.onPause();

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {

        // Define das coordenadas
        /*
        if(location != null)
        {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            if (num != 0)
            {
                //mMap.clear();

                Marker ponto = mMap.addMarker(new MarkerOptions().position(latLng).visible(false));

                //addRaio(latLng);
                adicionaPontos(latLng);

                rotas.add(latLng);

                rectOptions.add(latLng);
                Polyline polyline = mMap.addPolyline(rectOptions);

                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(18));
            }

            else
            {
                Marker ponto = mMap.addMarker(new MarkerOptions().position(latLng).title("Você!"));
                ponto.showInfoWindow();
                //addRaio(latLng);

                adicionaPontos(latLng);

                rotas.add(latLng);

                rectOptions.add(latLng);
                Polyline polyline = mMap.addPolyline(rectOptions);

                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(18));
                num++;
            }

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker)
                {
                    try
                    {
                        Localidade local = (Localidade) marker.getTag();

                        Intent i = new Intent(getActivity(), ShowInformation.class);

                        i.putExtra("id", local.getId());
                        i.putExtra("nome", local.getNome());
                        i.putExtra("descricao", local.getDescricao());
                        i.putExtra("imagem", local.getURLImagem());
                        i.putExtra("latitude", local.getLatitude());
                        i.putExtra("longitude", local.getLongitude());
                        startActivity(i);

                        return true;
                    }
                    catch (Exception e){ return false; }

                }
            });
        }*/
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}


    // Procura por pontos próximos à localidade atual
    // Adiciona os pontos encontrados no mapa
    public void adicionaPontos(LatLng coordenadas)
    {
        ArrayList<Localidade> local;

        try {
            local = (ArrayList<Localidade>) controller.getLocalidadesProximas(coordenadas.latitude, coordenadas.longitude);

            if(local != null)
            {
                for(Localidade localidade : local)
                {
                    LatLng latg = new LatLng(localidade.getLatitude(), localidade.getLongitude());
                    Marker ponto = mMap.addMarker(new MarkerOptions().position(latg).title(localidade.getNome()));
                    ponto.showInfoWindow();
                    ponto.setTag(localidade);

                    // A cada novo ponto adicionado, uma nova notificação é lançada
                    gerarNotificacao(localidade.getNome(), localidade.getDescricao());

                }
            }
        }
        catch (NothingFounException e) {
            e.printStackTrace();
        }
    }

    /*
    public void addRaio(LatLng latLng)
    {
        CircleOptions circleOptions = new CircleOptions()
                .center(latLng)
                .radius(100)
                .visible(true);
        mMap.addCircle(circleOptions);
    }
    */


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
        return false;
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
