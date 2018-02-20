package br.com.awaketecnologia.dipaolo.data.model;

import java.util.List;

/**
 * Criado por Felipe Samuel em 09/02/2018.
 */

public class Model<T> {

    public int num_results;
    public List<T> objects;
    public int page;
    public int total_pages;


    public Class getClasse(){
        return getClass();
    }
}
