package entities.cliente;

public interface ITipoCliente {
    /**
     * Calcula o desconto dado ao cliente.
     * @return Retorna valor a pagar entre 0 a 1.
     * Exemplo: O cliente tem 20% desconto, sendo assim o valor retornado será 0,8.
     */
    public double calculaDesconto();

    /**
     * Calcula a mensalidade do cliente.
     * @return Retorna o valor da mensalidade.
     */
    public double calculaMensalidade();

    
}
