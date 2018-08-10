package com.br.apptur.object;

/**
 * Created by treck on 12/07/18.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.URL;

/*
 * Created by Matheus Crispim on 08/05/18.
 */

public class Imagem  extends AsyncTask<Void, Void, Bitmap> {
    private String url;

    public Imagem(String url){ this.url=url; }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        Bitmap bmp = null;

        try {
            URL url = new URL(this.url);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        } catch (Exception e) { e.printStackTrace(); }
        return bmp;

    }
}

