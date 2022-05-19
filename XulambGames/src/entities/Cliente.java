package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cliente implements Serializable {

    static final long serialVersionUID = 1;
    protected String nome;
    protected String nomeUsuario;
    protected String senha;
    protected List<Compra> compraList = new ArrayList<>();

    public Cliente(String nome, String nomeUsuario, String senha) {
        this.nome = nome;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    /**
     * Modifica o nome do cliente desde que a senha informada esteja correta.
     * @param nome novo nome do cliente.
     * @param senha senha do cliente.
     * @return true se foi atendido, false se não foi atendido.
     */
    public boolean setNome(String nome, String senha) {
        if(verificaSenha(senha)) {
            this.nome = nome;
            return true;
        }
        else
            return false;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    /**
     * Modifica o nome do cliente desde que a senha informada esteja correta.
     * @param nomeUsuario novo nome de usuário do cliente.
     * @param senha senha do cliente.
     * @return true se foi atendido, false se não foi atendido.
     */
    public boolean setNomeUsuario(String nomeUsuario, String senha) {
        if(verificaSenha(senha)) {
            this.nomeUsuario = nome;
            return true;
        }
        else
            return false;
    }

    /**
     * Obtém a senha do cliente desde que a senha informada esteja correta.
     * @param senha senha do cliente.
     * @return String conforme resultado.
     * Se a senha informada estiver correta, o retorno será a senha.
     * Se a senha informada estiver incorreta, o retorno será "Senha incorreta!".
     *
     */
    public String getSenha(String senha) {
        if(verificaSenha(senha))
            return senha;
        return "Senha incorreta!";
    }

    /**
     * Modifica a senha do cliente desde que a senha informada esteja correta.
     * @param nomeUsuario novo nome de usuário do cliente.
     * @param senha senha do cliente.
     * @return true se foi atendido, false se não foi atendido.
     */

    /**
     * Modifica a senha do cliente desde que a senha informada esteja correta.
     * @param senhaAtual senha atual do cliente.
     * @param senhaNova nova senha do cliente.
     * @return true se foi atendido, false se não foi atendido.
     */
    public boolean setSenha(String senhaAtual, String senhaNova) {
        if(verificaSenha(senhaAtual)) {
            this.senha = senhaNova;
            return true;
        }
        return false;
    }

    public List<Compra> getCompraList() {
        return compraList;
    }

    public void setCompraList(List<Compra> compraList) {
        this.compraList = compraList;
    }

    private boolean verificaSenha(String senha) {
        return (this.senha == senha) ? true : false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nome);
        sb.append("\t");
        sb.append(nomeUsuario);
        sb.append("\t");
        sb.append(senha);

        for (Compra compra : compraList) {
            sb.append("\nCompra " + compra.toString());
        }

        return sb.toString();
    }

    //#region PENDÊNCIAS
    /*
     * Pendência 1- futuramente tem que fazer um "check" no construtor (ou outro meio)
     * para conferir se a senha está dentro de um padrão (exemplo: mínimo 6 dígitos)
     *
     * Pendência 2-
     * */
    //#endregion
}
