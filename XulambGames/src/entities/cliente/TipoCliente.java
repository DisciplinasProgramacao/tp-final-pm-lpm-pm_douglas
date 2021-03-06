package entities.cliente;

public enum TipoCliente implements ITipoCliente {
    CADASTRADO(0, 1),
    EMPOLGADO(10, 0.9),
    FANATICO(25, 0.7);

    private double mensalidade;
    private double desconto;

    TipoCliente(double mensalidade, double desconto){
        this.mensalidade = mensalidade;
        this.desconto = desconto;
    }

    /**
     * Calcula a porcentagem do preço final que o tipo de cliente deve pagar.
     * @return Retorna valor a pagar entre 0 a 1.
     * Exemplo: O cliente tem 20% desconto, sendo assim o valor retornado será 0,8.
     */
    public double calculaDesconto(){
        return desconto;
    }

    @Override
    public double valorAPagar() {
        return desconto;
    }

    /**
     * Calcula a mensalidade do tipo de cliente.
     * @return Retorna o valor da mensalidade.
     */
    @Override
    public double calculaMensalidade(){
        return mensalidade;
    }


}
