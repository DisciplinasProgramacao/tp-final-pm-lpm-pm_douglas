package entities;

import entities.cliente.Cliente;
import entities.jogo.CategoriaJogo;
import entities.jogo.Jogo;

import java.io.Serializable;
import java.util.List;

import util.Data;

public class Compra implements Serializable {

    static final long serialVersionUID = 2;

    //#region Atributos
    private Cliente cliente;
    private double valorPago;
    private List<Jogo> jogos;
    private double descontoCliente;
    private Data data;
    //#endregion

    //#region Construtor
    public Compra(List<Jogo> j, double dC, Data d){
        jogos = j;
        descontoCliente = dC;
        data = d;
        valorPago = calcularValorFinal();
    }
    //#endregion

    //#region Get e Set
    public Cliente getComprador() {
        return cliente;
    }

    public double getValorPago() {
        return valorPago;
    }

    public List<Jogo> getJogos() {
        return jogos;
    }

    public int getQtdJogos() {
        return jogos.size();
    }

    //#endregion

    //#region Métodos Específicos

    private double calcularValorFinal() {
        double valor = calcularValorSemDesconto();

        // aplicar desconto dos jogos da compra
        valor = calcularValorAposDescontoJogos(valor);

        // aplicar desconto do cliente
        valor = calcularValorAposDescontoCliente(valor);

        return valor;
    }

    private double calcularValorAposDescontoCliente(double valor) {
        return valor * descontoCliente;
    }

    //#region Calcular Preço Total Sem Desconto
    private double calcularValorSemDesconto() {
        return getJogos().stream()
                .mapToDouble(Jogo::getPreco)
                .sum();
    }

    //#endregion

    //#region Cálculo de desconto dos Jogos da Compra
    private double calcularValorAposDescontoJogos(double valor) {
        if(temDesconto20())
            return valor * 0.8;
        if(temDesconto10())
            return valor * 0.9;
        else
            return valor;
    }

    private int getQtdJogosPorCategoria(CategoriaJogo c) {
        return (int)jogos.stream().filter(n -> n.getCategoria() == c).count();
    }

    private boolean temDesconto20() {
        return desconto2Lancamentos() || desconto2PremiumMais1() || desconto3Premium()
                || desconto3RegularesMais1() || desconto5Regulares();
    }

    private boolean temDesconto10() {
        return desconto2Premium() || desconto4Regulares();
    }

    //#region Condições para desconto de 20%
    private boolean desconto2Lancamentos() {
        return (getQtdJogosPorCategoria(CategoriaJogo.LANCAMENTO) >= 2);
    }

    private boolean desconto2PremiumMais1() {
        return (getQtdJogosPorCategoria(CategoriaJogo.PREMIUM) >= 2) && getQtdJogos() >= 3;
    }

    private boolean desconto3Premium() {
        return getQtdJogosPorCategoria(CategoriaJogo.PREMIUM) >= 3;
    }

    private boolean desconto3RegularesMais1() {
        int qtdRegulares = getQtdJogosPorCategoria(CategoriaJogo.REGULAR);
        return (qtdRegulares >= 3 && qtdRegulares < 5) && (getQtdJogos() > qtdRegulares);
    }

    private boolean desconto5Regulares() {
        return getQtdJogosPorCategoria(CategoriaJogo.REGULAR) >= 5;
    }
    //#endregion

    //#region Condições para desconto de 10%
    private boolean desconto2Premium() {
        return getQtdJogosPorCategoria(CategoriaJogo.PREMIUM) == 2;
    }

    private boolean desconto4Regulares() {
        return getQtdJogosPorCategoria(CategoriaJogo.REGULAR) == 4;
    }
    //#endregion
    //#endregion

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(valorPago);
        for (Jogo jogo : jogos) {
            sb.append("\n + Jogo: ").append(jogo.toString());
        }
        return sb.toString();
    }
    //endregion
}
