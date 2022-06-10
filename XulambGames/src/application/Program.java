package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.TreeMap;

import entities.Compra;
import entities.cliente.Cliente;
import entities.cliente.TipoCliente;
import entities.jogo.CategoriaJogo;
import entities.jogo.Jogo;
import util.Data;

public class Program {

    public static final String ARQ_DADOS = "clientes.bin";

    public static void main(String[] args) {

       TreeMap<String, Cliente> clientes = new TreeMap<>();
       ArrayList<Compra> compras = new ArrayList<Compra>();
       ArrayList<Jogo> jogos = new ArrayList<Jogo>();

       Cliente c = new Cliente("Leticia", "leticia", "123456", TipoCliente.FANATICO);
       Cliente c1 = new Cliente("Leticia", "leticia1", "123456", TipoCliente.FANATICO);

       jogos.add(new Jogo("BTD6", CategoriaJogo.LANCAMENTO, 100, 110));

       Data hoje = new Data(10, 6, 2022);
       
       c.comprar(jogos, hoje);

       clientes.put(c.getNomeUsuario(), c);
       clientes.put(c1.getNomeUsuario(), c1);

       // escreverClientes(clientes, ARQ_DADOS);
       lerClientes(ARQ_DADOS);
    }

    public static void escreverClientes(TreeMap<String, Cliente> clientes, String nomeArq) {

        try {

            FileOutputStream fout = new FileOutputStream(nomeArq);
            ObjectOutputStream arqSaida = new ObjectOutputStream(fout);
            arqSaida.writeObject(clientes);
            arqSaida.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void lerClientes(String nomeArq) {
        FileInputStream arq;
        try {
            arq = new FileInputStream(nomeArq);

            ObjectInputStream arqLeitura;
            arqLeitura = new ObjectInputStream(arq);

            TreeMap<String, Cliente> clientes = (TreeMap<String, Cliente>) arqLeitura.readObject();

            for (Cliente c : clientes.values()) {
                System.out.println(c);
            }

            arqLeitura.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
