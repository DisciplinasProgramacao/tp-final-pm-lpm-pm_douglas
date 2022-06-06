package entities.cliente;

public class Empolgado extends Cliente {
    /**
     * Construtor.
     * Não é permitido parâmetro null ou menor que 5 dígitos.
     *
     * @param nome        Nome do cliente.
     * @param nomeUsuario Nome de usuário.
     * @param senha       Senha do cliente.
     */
    public Empolgado(String nome, String nomeUsuario, String senha) {
        super(nome, nomeUsuario, senha);
    }

    @Override
    public double calculaDesconto() {
        return 0.9;
    }

    @Override
    public boolean pagaMensalidade() {
        return true;
    }

    @Override
    public double calculaMensalidade() {
        return 10.0;
    }
}
