package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Compra implements Serializable {

    static final long serialVersionUID = 2;
    private double valorPago;
    private List<Jogo> jogos;

    public Compra(double v){
        valorPago = v;
    };

    public Compra(double v, ArrayList<Jogo> j){
        valorPago = v;
        jogos = j;
    };

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(valorPago);
        for (Jogo jogo : jogos) {
            sb.append("\nJogo: " + jogo.toString());
        }
        return sb.toString();
    }
}
