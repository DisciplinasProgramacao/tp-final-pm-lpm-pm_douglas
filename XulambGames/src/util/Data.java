package util;

import java.io.Serializable;
import java.util.Objects;

/**
 * Minha Data
 *
 *  Só podemos criar datas válidas.
 *  Adicionar dias a uma data (sem modificar a data atual).
 *  Verificar, entre duas datas, qual é a mais futura.
 *  Imprimir a data no formato DD/MM/AAAA.
 */

public class Data implements Comparable, Serializable {

    //#region CONSTANTES
    //constante: dias de cada mês
    private static final int[] DIASDOMES = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private static final int ANO_ATUAL = 2022;
    //#endregion

    //#region Atributos
    private int dia;
    private int mes;
    private int ano;
    //#endregion

    //#region Construtores
    /**
     * Construtor para data padrão: 01/01/1990
     */
    public Data(){
        this.init(1,1,1990);
    }

    /**
     * Construtor para ano atual: recebe dia, mês e completa com ano atual (2022).
     * Datas inválidas vão para 01/01/1990
     * @param dia Dia
     * @param mes Mês
     *
     */
    public Data(int dia, int mes){
        this.init(dia, mes, ANO_ATUAL);
    }

    /**
     * Construtor completo: recebe dia, mês e ano e valida a data. Datas inválidas vão para 01/01/1990
     * @param dia Dia
     * @param mes Mês
     * @param ano Ano
     */
    public Data(int dia, int mes, int ano){
        this.init(dia, mes, ano);
    }

    private void init(int dia, int mes, int ano){
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        if (!this.dataValida()){     //se a data não é válida... (método da própria classe)
            throw new DataInvalidaException("A data fornecida é inválida");
        }
    }
    //#endregion

    //#region Getters

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }


    //#endregion

    //#region Métodos
    /**
     * Retorna se o ano da data armazenada é bissexto
     * Para regras do ano bissexto:
     * http://educacao.uol.com.br/disciplinas/matematica/ano-bissexto-eles-se-repetem-a-cada-4-anos.htm
     * http://www.sogeografia.com.br/Curiosidades/?pg=4
     * @return Se o ano é bissexto (true) ou não (false)
     */
    public boolean anoBissexto(){
        if(this.ano%400==0)
            return true;
        else return this.ano % 4 == 0 && this.ano % 100 != 0;
    }

    /**
     * Verifica se a data armazenada é válida (método privado)
     * @return TRUE se é válida ; FALSE se não é válida
     */
    private boolean dataValida()
    {
        boolean resposta = true;        //resposta sobre a validade
        if(this.ano<1900)
            resposta = false;
        else{
            if (this.mes < 1 || this.mes > 12)                           //mês<1 ou mês>12 --> data inválida
                resposta = false;
            else {
                if (this.anoBissexto()) //senão, caso de 29/02 em ano bissexto --> data válida
                    DIASDOMES[2] = 29;
                if (this.dia > DIASDOMES[this.mes])                //senao, verifica validade de acordo com o mês atual
                    resposta = false;
                DIASDOMES[2] = 28;
            }
        }
        return resposta;    //retorna a resposta obtida
    }

    /**
     * Acrescenta alguns dias à data e retorna a nova data (sem modificar a atual)
     * @param quant Quantos dias
     * @return Nova data com os dias adicionados
     */
    public Data acrescentaDias(int quant){
        Data aux = new Data(this.dia, this.mes, this.ano);

        aux.dia += quant;      //acrescenta a quantidade ao dia atual e, em seguida, devemos ajustar mês e ano

        if (aux.anoBissexto()) DIASDOMES[2] = 29; //se o ano é bissexto, altera fevereiro para 29 dias

        while (aux.dia > DIASDOMES[aux.mes]){     //enquanto os dias ultrapassam o limite de dias do mês atual... ajustar

            aux.dia = aux.dia - DIASDOMES[aux.mes]; // desconta a quantidade de dias do mês
            aux.mes++; //avança o mês

            if (aux.mes > 12){      //se passar de 12 meses...
                aux.mes = aux.mes - 12;       //desconta-se 1 ano
                aux.ano++;                     //avança o ano.
                if (aux.anoBissexto()) DIASDOMES[2] = 29; //verifica se o novo ano é bissexto para ajustar os dias de fevereiro.
                else DIASDOMES[2] = 28;
            }
        }
        return aux;
    }

    /**
     * Verifica, entre duas datas, qual está no futuro em relação à outra.
     * Comparação feita pela transformação da data em String AAAAMMDD
     * @param outra Outra data a ser comparada
     * @return TRUE se esta for futura, FALSE caso contrário
     */
    public boolean maisFutura(Data outra){
        boolean resposta = false;

        String esta = String.format("%4d", this.ano)+String.format("%2d", this.mes)+String.format("%2d", this.dia);
        String aOutra = String.format("%4d", outra.ano)+String.format("%2d", outra.mes)+String.format("%2d", outra.dia);

        if(esta.compareTo(aOutra)>1)
            resposta = true;

        return resposta;
    }

    /**
     * Retorna a data formatada
     * @return String com a data no formato dd/mm/aaaa
     */
    public String dataFormatada(){

        return (String.format("%02d",this.dia)+ "/" + String.format("%02d",this.mes)+ "/" + String.format("%4d",this.ano));
    }

    @Override
    public String toString() {
        return (String.format("%02d",this.dia)+ "/"
                + String.format("%02d",this.mes)+ "/"
                + String.format("%4d",this.ano));
    }

    @Override
    public int compareTo(Object o) {

        String esta = String.format("%4d", this.ano)+String.format("%2d", this.mes)+String.format("%2d", this.dia);
        String aOutra = String.format("%4d", ((Data) o).ano)+String.format("%2d", ((Data) o).mes)+String.format("%2d", ((Data) o).dia);
        return esta.compareTo(aOutra);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Data Data)) return false;
        return dia == Data.dia && mes == Data.mes && ano == Data.ano;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dia, mes, ano);
    }

    //#endregion
}

