package entities.jogo;

public enum Categoria {
    LANCAMENTO(1.1, 1.1, "Lancamento"),
    PREMIUM(1, 1, "Premium"),
    REGULAR(0.7, 1, "Regular"),
    PROMOCAO(0.3, 0.5, "Promocao");

    private double miltiplicadorMax;
    private double multiplicadorMin;
    private String nome;

    public double maiorPreco(){
        return this.multiplicadorMin;
    }

    public double menorPreco(){
        return this.multiplicadorMin;
    }

    public String nome(){
        return this.nome;
    }

    @Override
    public String toString(){
        return this.nome();
    }
}
