package com.br.apptur.model.restful;

import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*
 * Created by Matheus Crispim on 08/05/18.
 */
public class ReadRest extends AsyncTask<Void, Void, ArrayList<String>> {

    private String auth;
    private String apiPath;
    private String method;

    public  ReadRest(String auth, String apiPath, String method){
        this.auth=auth;
        this.apiPath=apiPath;
        this.method=method;
    }

    @Override
    protected  ArrayList<String> doInBackground(Void... voids) {
 
        StringBuilder resposta = new StringBuilder();
        ArrayList<String> strings;
        //Realiza a leitura do Json gerado pela rest
        try {

            URL url = new URL(this.apiPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(this.method);
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.connect();

            Scanner scanner = new Scanner(url.openStream());
            String word;

            while (scanner.hasNext()) {
                /*Adiciona espaço na frente scanner.next() para
                 *que as palavras não fiquem todas juntas*/
                word = scanner.next()+ " ";

                resposta.append(word);
                //Adicionar fatiação de string para gerar novos objetos
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //O separador entre os dados de cada localidade retornada é fefa253fd04f5712c80e3bf4f4649c64
        String[] array= resposta.toString().split("fefa253fd04f5712c80e3bf4f4649c64");

        //Faz a conversão de array comum para arrayList
        strings= new ArrayList<>(Arrays.asList(array));
        //Remove o ultimo elemento que sempre é null
        strings.remove(strings.size()-1);

        //Caso nada seja retornado, retorna nulo
        if(strings.size()==0){
            strings=null;
        }

        return  strings;
    }
}