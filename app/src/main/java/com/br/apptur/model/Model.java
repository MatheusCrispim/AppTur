package com.br.apptur.model;

/**
 * Created by treck on 12/07/18.
 */

import android.util.Log;

import com.br.apptur.model.exception.NadaEncontradoException;
import com.br.apptur.model.restful.LoadLocalidade;
import com.br.apptur.object.Localidade;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Matheus Crispim on 08/05/18.
 */

public class Model {

    private LoadLocalidade loadLocalidade;
    private ArrayList<Localidade> localidades;

    public Model(){
        this.loadLocalidade=new LoadLocalidade();
        this.localidades=new ArrayList<>();
    }


    //Return the locale for the id
    public Localidade getLocalidade(int id) throws NadaEncontradoException {
        Localidade localidade=null;
        //Verifica se a Localidade já está no array
        for(Localidade localidadeTmp: this.localidades){
            if(localidadeTmp.getId()==id){
                localidade=localidadeTmp;
            }
        }
        /*Caso a Localidade ainda não esteja no array,
         uma requisição na rest é feita, retornando as que foram
         encontradas na base de dados
          */
        if(localidade==null){
            ArrayList<Localidade> localidadesL=this.loadLocalidade.getLocalidades("id", Integer.toString(id));
            if(localidadesL!=null){
                for(Localidade localidadeTmp: localidadesL){
                    this.localidades.add(localidadeTmp);
                }
                localidade=localidades.get(0);
            }
        }
        if(localidade==null){
            throw new NadaEncontradoException();
        }
        return localidade;
    }


    //Return the locale for the value
    public List<Localidade> getLocalidades(String value)  throws NadaEncontradoException{
        ArrayList<Localidade> localidadesL=new ArrayList<>();

        //Verifica se a Localidade já está no array
        for(Localidade localidadeTmp: this.localidades){
            if(localidadeTmp.getNome().equals(value) | localidadeTmp.getDescricao().equals(value)){
                localidadesL.add(localidadeTmp);
            }
        }
        /*Caso a Localidade ainda não esteja no array,
         uma requisição na rest é feita, retornando as que foram
         encontradas na base de dados
          */
        if(localidadesL.size()==0){
            ArrayList<Localidade> localidadesTmp=this.loadLocalidade.getLocalidades("*", value);
            if(localidadesTmp!=null) {
                for (Localidade localidadeTmp : localidadesTmp) {
                    this.localidades.add(localidadeTmp);
                }
            }
            localidadesL=localidadesTmp;
        }

        if(localidadesL==null){
            throw new NadaEncontradoException();
        }
        return localidadesL;
    }


    //Melhorar essa lógica
    public List<Localidade> getLocalidadesProximas(double latitude, double longitude) throws NadaEncontradoException{
        ArrayList<Localidade> localidadesTmp=this.loadLocalidade.getLocalidadesProximas(latitude, longitude);

        if(localidadesTmp!=null){
            for (Localidade localidadeTmp : localidadesTmp) {
                for(Localidade localidadeTmp2: this.localidades){
                    if(localidadeTmp2.getId()==localidadeTmp.getId()){
                        this.localidades.add(localidadeTmp);
                    }
                }
            }
            return localidadesTmp;
        }
        throw new NadaEncontradoException();
    }



}