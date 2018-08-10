package com.br.apptur.model.restful;

/**
 * Created by treck on 12/07/18.
 */
import android.util.Log;

import com.br.apptur.object.Localidade;
import com.google.gson.Gson;


import java.util.ArrayList;


/*
 * Created by treck on 05/05/18.
 */

public class LoadLocalidade{
    private ReadRest rdr;
    private final String API_PATH="http://35.237.214.46/view/listar.php?";
    //search the locale for the column
    public ArrayList<Localidade> getLocalidades(String column, String value){

        ArrayList<String> localidadesStr;
        ArrayList<Localidade> localidades=null;
        String apiPath;

        if(column.equals("*")){
            apiPath=API_PATH+"nome="+value+"&descricao="+value;
        }else{
            apiPath=API_PATH+column+"="+value;
        }

        this.rdr=new ReadRest("Logado", apiPath, "GET");
        try {
            localidadesStr=rdr.execute().get();
            if(localidadesStr!=null){
                localidades=this.createLocalidades(localidadesStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localidades;
    }



    public ArrayList<Localidade> getLocalidadesProximas(double latitude, double longitude){

        ArrayList<String> localidadesStr;
        ArrayList<Localidade> localidades=null;
        String apiPath = API_PATH+"latitude="+latitude+"&longitude="+longitude;
        this.rdr=new ReadRest("Logado", apiPath, "GET");
        try {
            localidadesStr=rdr.execute().get();

            if(localidadesStr!=null){
                localidades=this.createLocalidades(localidadesStr);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localidades;
    }


    protected ArrayList<Localidade> createLocalidades(ArrayList<String> localidadesStr){
        ArrayList<Localidade> localidades=new ArrayList<>();
        for(String localidadeStr: localidadesStr){
            //Cria o objeto localidade a partir do json retornado pela api
            localidades.add(new Gson().fromJson(localidadeStr, Localidade.class));
        }
        return  localidades;
    }


}
