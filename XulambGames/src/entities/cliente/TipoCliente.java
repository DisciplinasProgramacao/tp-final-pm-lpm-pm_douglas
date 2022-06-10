package entities.cliente;

public enum TipoCliente implements ITipoCliente {
    CADASTRADO(0, 1),
    EMPOLGADO(10, 0.9),
    FANATICO(25, 0.7);

    private double mensalidade;
    private double desconto;

    private TipoCliente(double mensalidade, double desconto){
        this.mensalidade = mensalidade;
        this.desconto = desconto;
    }

    /**
     * Calcula o desconto dado ao cliente.
     * @return Retorna valor a pagar entre 0 a 1.
     * Exemplo: O cliente tem 20% desconto, sendo assim o valor retornado será 0,8.
     */
    public double calculaDesconto(){
        return desconto;
    }

    /**
     * Calcula a mensalidade do cliente.
     * @return Retorna o valor da mensalidade.
     */
    public double calculaMensalidade(){
        return mensalidade;
    }


}
