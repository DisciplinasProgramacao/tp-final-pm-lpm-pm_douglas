package entities;

import entities.cliente.Cliente;
import entities.jogo.Categoria;
import entities.jogo.Jogo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Compra implements Serializable {

    static final long serialVersionUID = 2;

    //#region Atributos
    private Cliente cliente;
    private double valorPago;
    private List<Jogo> jogos;
    //#endregion

    //#region Construtor
    public Compra(double v){
        valorPago = v;
    }

    public Compra(double v, ArrayList<Jogo> j, Cliente c){
        valorPago = v;
        jogos = j;
        cliente = c;
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

    public double calcularValorFinal() {
        double valor = calcularValorSemDesconto();

        // aplicar desconto da compra
        valor = calcularValorAposDesconto(valor);

        // aplicar desconto do cliente
        valor = calcularValorAposDescontoCliente(valor);

        return valor;
    }

//    public double calcularValorFinal() {
//        double valorInicial = calcularValorSemDesconto();
//
//        // aplicar desconto da compra
//        valor = calcularValorAposDesconto(valor);
//        // Desconto precisa ser a porcentagem de desconto, e não a porcentagem do valor final da compra
//        // Exemplo: Desconto de 30%, os métodos estão retornando 0.7,
//
//        // aplicar desconto do cliente
//        valor = calcularValorAposDescontoCliente(valor);
//
//        return valor;
//    }

    private double calcularValorAposDescontoCliente(double valor) {
        return valor * cliente.calculaDesconto();
    }

    //#region Calcular Preço Total Sem Desconto
    private double calcularValorSemDesconto() {
        return getJogos().stream()
                .mapToDouble(Jogo::getPreco)
                .sum();
    }

    //#endregion

    //#region Cálculo de desconto da Compra
    private double calcularValorAposDesconto(double valor) {
        if(temDesconto20())
            return valor * 0.8;
        if(temDesconto20())
            return valor * 0.9;
        else
            return valor;
    }

    private int getQtdJogosPorCategoria(Categoria c) {
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
        return (getQtdJogosPorCategoria(Categoria.LANCAMENTO) >= 2);
    }

    private boolean desconto2PremiumMais1() {
        return (getQtdJogosPorCategoria(Categoria.PREMIUM) >= 2) && getQtdJogos() >= 3;
    }

    private boolean desconto3Premium() {
        return getQtdJogosPorCategoria(Categoria.PREMIUM) >= 3;
    }

    private boolean desconto3RegularesMais1() {
        int qtdRegulares = getQtdJogosPorCategoria(Categoria.REGULAR);
        return (qtdRegulares >= 3 && qtdRegulares < 5) && (getQtdJogos() > qtdRegulares);
    }

    private boolean desconto5Regulares() {
        return getQtdJogosPorCategoria(Categoria.REGULAR) >= 5;
    }
    //#endregion

    //#region Condições para desconto de 10%
    private boolean desconto2Premium() {
        return getQtdJogosPorCategoria(Categoria.PREMIUM) == 2;
    }

    private boolean desconto4Regulares() {
        return getQtdJogosPorCategoria(Categoria.REGULAR) == 4;
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
