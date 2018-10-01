package com.br.apptur.model.exception;

/**
 * Created by treck on 12/07/18.
 */

public class NothingFounException extends Exception {
    private static final long serialId=1L;

    public NothingFounException(String msg){
        super(msg);
    }

    public NothingFounException(){
        super("Nada foi encontrado.");
    }


}