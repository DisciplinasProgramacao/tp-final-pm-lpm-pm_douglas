package entities.cliente;

public class Cadastrado extends Cliente {
    /**
     * Construtor.
     * Não é permitido parâmetro null ou menor que 5 dígitos.
     *
     * @param nome        Nome do cliente.
     * @param nomeUsuario Nome de usuário.
     * @param senha       Senha do cliente.
     */
    public Cadastrado(String nome, String nomeUsuario, String senha) {
        super(nome, nomeUsuario, senha);
    }

    @Override
    public double calculaDesconto() {
        return 1.0;
    }

    @Override
    public boolean pagaMensalidade() {
        return false;
    }

    @Override
    public double calculaMensalidade() {
        return 0.0;
    }
}
