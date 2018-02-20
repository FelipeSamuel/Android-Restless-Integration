package br.com.awaketecnologia.dipaolo.data.model;

/**
 * Criado por Felipe Samuel em 09/02/2018.
 */

public class Condicao {

    public String name;
    public String op ;
    public String val ;

    public Condicao(String name, String op, String val) {
        this.name = name;
        this.op = op;
        this.val = val;
    }

    public Condicao() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
