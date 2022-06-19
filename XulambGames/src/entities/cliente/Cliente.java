package entities.cliente;

import entities.Compra;
import entities.jogo.Jogo;
import util.CadastroClienteException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import util.Data;

public class Cliente implements Serializable {

    static final long serialVersionUID = 1;

    //#region Atributos
    protected String nome;
    protected String nomeUsuario;
    protected String senha;
    protected List<Compra> comprasHistorico = new ArrayList<>();

    private TipoCliente tipo;
    //#endregion

    //#region Construtor
    /**
     * Construtor.
     * Não é permitido parâmetro null ou menor que 5 dígitos.
     * @param nome Nome do cliente.
     * @param nomeUsuario Nome de usuário.
     * @param senha Senha do cliente.
     * @throws CadastroClienteException devido os parâmetros forem nulos ou menor que 5 dígitos.
     *
     */
    public Cliente(String nome, String nomeUsuario, String senha, TipoCliente tipo) {
        if (validaStringNullOuMenorQue5(nome))
            throw new CadastroClienteException("Erro ao criar cliente: nome é null ou menor que 5 dígitos");
        if (validaStringNullOuMenorQue5(nomeUsuario))
            throw new CadastroClienteException("Erro ao criar cliente: nomeUsuario é null ou menor que 5 dígitos");
        if (validaStringNullOuMenorQue5(senha))
            throw new CadastroClienteException("Erro ao criar cliente: senha é null ou menor que 5 dígitos");

        this.nome = nome;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.tipo = tipo;
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
            throw new CadastroClienteException("Erro na alteração do nome: senha incorreta");
        if (validaStringNullOuMenorQue5(novoNome))
            throw new CadastroClienteException("Erro na alteração do nome: novo nome nulo ou menor que 5 dígitos");
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
            throw new CadastroClienteException("Erro na alteração do nome de usuário: senha incorreta");
        if (validaStringNullOuMenorQue5(novoNomeUsuario))
            throw new CadastroClienteException("Erro na alteração do nome de usuário: novo nome de usuário nulo ou menor que 5 dígitos");
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
            throw new CadastroClienteException("Erro na alteração de senha: senha incorreta");
        }
        if (validaStringNullOuMenorQue5(senhaNova)) {
            throw new CadastroClienteException("Erro na alteração de senha: nova senha nula ou menor que 5 dígitos");
        }
        this.senha = senhaNova;
    }

    public List<Compra> getComprasHistorico() {
        return comprasHistorico;
    }
    //#endregion

    //#region Métodos Específicos
    /**
     * Cria compra e adiciona ao Histórico do cliente.
     * @param j lista de jogos comprados
     * @param data data da compra
     */
    public void comprar(ArrayList<Jogo> j, Data data) {
        Compra compra = new Compra(j, valorAPagar(), data);
        comprasHistorico.add(compra);
        for (Jogo jogo : j) {
            jogo.registrarVenda();
        }
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
                sb.append("\nCompra ").append(compra.toString());
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
        sb.append(historicoCompleto());
        return sb.toString();
    }

    private boolean verificaSenha(String senha) {
        return this.senha == senha;
    }

    private boolean validaStringNullOuMenorQue5(String s) {
        return s == null || s.length() < 5;
    }

    /**
     * Calcula a porcentagem do preço final que o cliente deve pagar baseado em seu tipo.
     * @return Retorna valor a pagar entre 0 a 1.
     * Exemplo: O cliente tem 20% desconto, sendo assim o valor retornado será 0,8.
     */
    public double valorAPagar() {
        return tipo.valorAPagar();
    }
    //#endregion

}