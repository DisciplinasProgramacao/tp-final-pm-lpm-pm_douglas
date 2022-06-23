package entities.jogo;

import util.CadastroJogoException;

import java.io.Serializable;

public class Jogo implements Serializable {

    static final long serialVersionUID = 3;

    //#region Atributos
    private String nome;
    private double precoOriginal;
    private double precoAtual;
    private int qtdVendas;



    private CategoriaJogo categoria;
    //#endregion

    //#region Construtor
    public Jogo(String nome, CategoriaJogo categoria, double precoOriginal, double precoAtual) {
        if (validaStringNullOuMenorQue1(nome))
            throw new CadastroJogoException("Erro ao criar jogo: nome é null ou menor que 1 dígito");
        if (categoria == null)
            throw new CadastroJogoException("Erro ao criar jogo: categoria é null");

        this.nome = nome;
        this.categoria = categoria;
        this.precoOriginal = precoOriginal;
        setPreco(precoAtual);
    }
    //#endregion

    //#region Get e Set

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (validaStringNullOuMenorQue1(nome))
            throw new CadastroJogoException("Erro ao criar jogo: nome é null ou menor que 1 dígito");
        this.nome = nome;
    }

    public CategoriaJogo getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaJogo categoria) {
        if (categoria == null)
            throw new CadastroJogoException("Erro ao criar jogo: categoria é null");
        this.categoria = categoria;
    }

    public int getQtdVendas() {
        return qtdVendas;
    }

    public void setPreco(double novoPreco) {
        double pct = novoPreco/precoOriginal;
        if (pct > categoria.maiorPreco() || pct < categoria.menorPreco()) {
            throw new CadastroJogoException("PrecoInvalido");
        }
        else {
            precoAtual = novoPreco;
        }      
    }

    public double getPreco() {
        return precoAtual;
    }
    //#endregion

    //#region Métodos Específicos
    public String toString() {
        return nome + " - " + categoria.nome();
    }

    private boolean validaStringNullOuMenorQue1(String s) {
        return s == null || s.length() < 1;
    }

    public void registrarVenda() {
        this.qtdVendas++;
    }

    //#endregion

}