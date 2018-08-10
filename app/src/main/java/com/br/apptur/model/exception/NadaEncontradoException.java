package com.br.apptur.model.exception;

/**
 * Created by treck on 12/07/18.
 */

public class NadaEncontradoException extends Exception {
    private static final long serialId=1L;

    public NadaEncontradoException(String msg){
        super(msg);
    }

    public  NadaEncontradoException(){
        super("Nada foi encontrado.");
    }


}