package entities;

import java.io.Serializable;

public class Jogo implements Serializable {

    static final long serialVersionUID = 3;
    private String nome;

    public Jogo(String n) {
        nome = n;
    }

    public String toString() {
        return nome;
    }

}