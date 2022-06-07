package entities.jogo;

public enum CategoriaJogo {
    LANCAMENTO(1.1, 1.1, "Lancamento"),
    PREMIUM(1, 1, "Premium"),
    REGULAR(0.7, 1, "Regular"),
    PROMOCAO(0.3, 0.5, "Promocao");

    private double multiplicadorMax;
    private double multiplicadorMin;
    private String nome;

    private CategoriaJogo(double multMin, double multMax, String n){
        multiplicadorMax = multMax;
        multiplicadorMin = multMin;
        nome = n;
    }

    public double maiorPreco(){
        return this.multiplicadorMax;
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
