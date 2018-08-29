package com.br.apptur.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.apptur.R;
import com.br.apptur.object.Localidade;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by treck on 12/07/18.
 */

public class ShowInformation extends AppCompatActivity implements OnMapReadyCallback {

    private FragmentManager fragmentManager;
    private Localidade local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_information);

        Intent intent = getIntent();

        Bundle dados = intent.getExtras();

        local = new Localidade(dados.getInt("id"),
                dados.getString("nome"),
                dados.getString("descricao"),
                dados.getString("imagem"),
                dados.getFloat("latitude"),
                dados.getFloat("longitude"));

        TextView titulo = findViewById(R.id.show_descricao);
        TextView descricao = findViewById(R.id.show_location_description);
        ImageView imagem = findViewById(R.id.show_location_imagem);

        //Recuperamos os valores de nosso marcador e o colocamos em nossa view
        titulo.setText(local.getNome());
        descricao.setText(local.getDescricao());
        imagem.setImageBitmap(local.getImagem());

        /* Adicionar isso depois
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mapa, new MapsFragment(),"MapsFragment");
        fragmentTransaction.commitAllowingStateLoss();*/

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {}
}