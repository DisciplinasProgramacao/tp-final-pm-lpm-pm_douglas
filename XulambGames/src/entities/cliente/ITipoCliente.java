package entities.cliente;

public interface ITipoCliente {
    /**
     * Calcula a porcentagem do preço final que o tipo de cliente deve pagar.
     * @return Retorna valor a pagar entre 0 a 1.
     * Exemplo: O cliente tem 20% desconto, sendo assim o valor retornado será 0,8.
     */
    public double valorAPagar();

    /**
     * Calcula a mensalidade do tipo de cliente.
     * @return Retorna o valor da mensalidade.
     */
    public double calculaMensalidade();

    
}
