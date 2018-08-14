package com.br.apptur.control;

/**
 * Created by treck on 12/07/18.
 */

import com.br.apptur.model.Model;
import com.br.apptur.model.exception.NothingFounException;
import com.br.apptur.object.Localidade;

import java.util.List;

public class Controller {

    private Model model;
    //private View view;

    public Controller(){
        this.model=new Model();
        //this.view=new View();
    }

    public Localidade getLocalidade(int id) throws NothingFounException {
        return this.model.getLocalidade(id);
    }

    public List<Localidade> getLocalidades(String key) throws NothingFounException {
        return this.model.getLocalidades(key);
    }

    public  List<Localidade> getLocalidadesProximas(double latitude, double longitude) throws NothingFounException {
        return this.model.getLocalidadesProximas(latitude, longitude);
    }
}
