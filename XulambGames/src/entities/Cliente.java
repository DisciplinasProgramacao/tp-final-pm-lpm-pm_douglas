package entities;

import util.XulambException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Cliente implements Serializable {

    static final long serialVersionUID = 1;

    //#region Atributos
    protected String nome;
    protected String nomeUsuario;
    protected String senha;
    protected List<Compra> comprasHistorico = new ArrayList<>();
    //#endregion

    //#region Construtor
    /**
     *Construtor.
     * Não é permitido parâmetro null ou menor que 5 dígitos.
     * @param nome Nome do cliente.
     * @param nomeUsuario Nome de usuário.
     * @param senha Senha do cliente.
     *
     */
    public Cliente(String nome, String nomeUsuario, String senha) {
        if (validaStringNullOuMenorQue5(nome))
            throw new XulambException("Erro ao criar cliente: nome nulo ou menor que 5 dígitos");
        if (validaStringNullOuMenorQue5(nomeUsuario))
            throw new XulambException("Erro ao criar cliente: nomeUsuario nulo ou menor que 5 dígitos");
        if (validaStringNullOuMenorQue5(senha))
            throw new XulambException("Erro ao criar cliente: senha nula ou menor que 5 dígitos");

        this.nome = nome;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
    }
    //#endregion

    //#region Get e Set
    public String getNome() {
        return nome;
    }

    /**
     * Modifica o nome do cliente.
     * Não é permitido parâmetro null ou menor que 5 dígitos.
     * Necessário fornecer a senha.
     * @param novoNome Novo nome do cliente.
     * @param senha Senha do cliente.
     */
    public void setNome(String novoNome, String senha) {
        if(!verificaSenha(senha))
            throw new XulambException("Erro na alteração do nome: senha incorreta");
        if (validaStringNullOuMenorQue5(novoNome))
            throw new XulambException("Erro na alteração do nome: novo nome nulo ou menor que 5 dígitos");
        this.nome = novoNome;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    /**
     * Modifica o nome de usuário do cliente.
     * Não é permitido parâmetro null ou menor que 5 dígitos.
     * Necessário fornecer a senha.
     * @param novoNomeUsuario Novo nome de usuário do cliente.
     * @param senha Senha do cliente.
     */
    public void setNomeUsuario(String novoNomeUsuario, String senha) {
        if(!verificaSenha(senha))
            throw new XulambException("Erro na alteração do nome de usuário: senha incorreta");
        if (validaStringNullOuMenorQue5(novoNomeUsuario))
            throw new XulambException("Erro na alteração do nome de usuário: novo nome de usuário nulo ou menor que 5 dígitos");
        this.nomeUsuario = novoNomeUsuario;
    }

    /**
     * Modifica a senha do cliente.
     * Não é permitido parâmetro null ou menor que 5 dígitos.
     * Necessário fornecer a senha.
     * @param senhaNova Nova senha do cliente.
     * @param senha Senha atual do cliente.
     */
    public void setSenha(String senhaNova, String senha) {
        if(!verificaSenha(senha)) {
            throw new XulambException("Erro na alteração de senha: senha incorreta");
        }
        if (validaStringNullOuMenorQue5(senhaNova)) {
            throw new XulambException("Erro na alteração de senha: nova senha nula ou menor que 5 dígitos");
        }
        this.senha = senhaNova;
    }

    public List<Compra> getComprasHistorico() {
        return comprasHistorico;
    }
    //#endregion

    //#region Métodos Específicos
    /**
     * Adiciona compra ao Histórico do cliente.
     * Não é permitido parâmetro null.
     * @param compra Compra a ser adicionada.
     */
    public void addCompraAoHistorico(Compra compra) {
        if(compra == null)
            throw new XulambException("Erro ao adicionar compra ao histórico do cliente: compra é null!");
        comprasHistorico.add(compra);
    }

    /*
    * ###ATENÇÃO ###
    * Necessário fazer 2 tipos de relatórios
    * 1 - por categoria de jogo
    * 2 - por data
    * O méotodo "historicoCompleto" está sendo usado pelo "toString"
    * Talvez seja melhor fazer um "tostring" curto somente com nome e nome de usuário ou algo semelhante
    * Talvez vale a pena deixar esse "historicoCompleto" ou simplesmente tirar tudo
    * */
    public String historicoCompleto() {
        StringBuilder sb = new StringBuilder();
        sb.append("Histórico:");

        if (!comprasHistorico.isEmpty()) {

            for (Compra compra : comprasHistorico) {
                sb.append("\nCompra " + compra.toString());
            }
        }
        else {
            sb.append("\nNão existem compras");
        }

        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nome);
        sb.append("\t");
        sb.append(nomeUsuario);
        sb.append("\t");
        sb.append(senha);
        sb.append('\n');
        sb.append(historicoCompleto());
        return sb.toString();
    }

    private boolean verificaSenha(String senha) {
        return (this.senha == senha) ? true : false;
    }

    private boolean validaStringNullOuMenorQue5(String s) {
        if (s == null || s.length() < 5)
            return true;
        return false;
    }
    //#endregion

    //#region Métodos Abstract
    /**
     * Calcula o desconto dado ao cliente.
     * @return Retorna valor entre 0 a 1.
     * Onde 0 significa 100% de desconto e 1 significa 0% de desconto.
     * Exemplo: O cliente tem 20% desconto, sendo assim o valor retornado será 0,8.
     */
    public abstract int calculaDesconto();

    /**
     * Verifica se o cliente é do tipo que paga mensalidade.
     * @return true se o cliente paga mensalidade.
     */
    public abstract boolean pagaMensalidade();

    /**
     * Calcula a mensalidade do cliente.
     * @return Retorna o valor da mensalidade.
     */
    public abstract int calculaMensalidade();

    //#endregion
}
