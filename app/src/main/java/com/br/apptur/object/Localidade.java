package com.br.apptur.object;

/**
 * Created by treck on 12/07/18.
 */

import android.graphics.Bitmap;

/*
 * Created by Matheus Crispim on 08/05/18.
 */


public class Localidade {

    private int id;
    private String nome;
    private String descricao;
    private String imagem;
    private float latitude, longitude;


    public Localidade(int id, String nome, String descricao, String imagem, float latitude, float longitude){
        this.id=id;
        this.nome=nome;
        this.descricao=descricao;
        this.imagem=imagem;
        this.latitude=latitude;
        this.longitude=longitude;
    }


    public Localidade(){
        this.nome="";
        this.descricao="";
        this.imagem="";
        this.latitude=0;
        this.longitude=0;
    }


    //Setters
    public void setId(int id){
        this.id=id;
    }

    public void setNome(String nome){
        this.nome=nome;
    }

    public void setDescricao(String descricao){
        this.descricao=descricao;
    }

    public void setImagem(String imagem){
        this.imagem=imagem;
    }

    public void setLatitude(float lat){
        this.latitude=lat;
    }

    public void setLongitude(float longi){
        this.longitude=longi;
    }

    //Getters
    public int getId(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    public String getDescricao(){ return this.descricao;}

    //Retorna o bitmap da imagem
    public Bitmap getImagem(){

        Bitmap imagem=null;
        try {
            imagem=new Imagem(this.imagem).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagem;
    }

    public String getURLImagem()
    {
        return this.imagem;
    }

    public float getLatitude(){
        return this.latitude;
    }

    public float getLongitude(){
        return this.longitude;
    }
}