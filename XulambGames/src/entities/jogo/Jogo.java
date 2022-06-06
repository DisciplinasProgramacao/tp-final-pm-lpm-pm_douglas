package entities.jogo;

import util.XulambException;

import java.io.Serializable;

public class Jogo implements Serializable {

    static final long serialVersionUID = 3;

    //#region Atributos
    private String nome;
    private Categoria categoria;
    //#endregion

    //#region Construtor
    public Jogo(String nome, Categoria categoria) {
        if (validaStringNullOuMenorQue1(nome))
            throw new XulambException("Erro ao criar jogo: nome é null ou menor que 1 dígito");
        if (categoria == null)
            throw new XulambException("Erro ao criar jogo: categoria é null");

        this.nome = nome;
        this.categoria = categoria;
    }
    //#endregion

    //#region Get e Set

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (validaStringNullOuMenorQue1(nome))
            throw new XulambException("Erro ao criar jogo: nome é null ou menor que 1 dígito");
        this.nome = nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        if (categoria == null)
            throw new XulambException("Erro ao criar jogo: categoria é null");
        this.categoria = categoria;
    }


    //#endregion

    //#region Métodos Específicos
    public String toString() {
        return nome;
    }

    private boolean validaStringNullOuMenorQue1(String s) {
        if (s == null || s.length() < 1)
            return true;
        return false;
    }
    //#endregion

}