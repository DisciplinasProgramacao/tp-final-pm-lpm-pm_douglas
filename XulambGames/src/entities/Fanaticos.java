package entities;

public class Fanaticos extends Cliente{
    /**
     * Construtor.
     * Não é permitido parâmetro null ou menor que 5 dígitos.
     *
     * @param nome        Nome do cliente.
     * @param nomeUsuario Nome de usuário.
     * @param senha       Senha do cliente.
     */
    public Fanaticos(String nome, String nomeUsuario, String senha) {
        super(nome, nomeUsuario, senha);
    }

    @Override
    public int calculaDesconto() {
        return 0;
    }

    @Override
    public boolean pagaMensalidade() {
        return true;
    }

    @Override
    public int calculaMensalidade() {
        return 0;
    }
}
