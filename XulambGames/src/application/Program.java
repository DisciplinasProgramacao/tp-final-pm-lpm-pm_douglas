package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.stream.Collectors;

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

        // inicializacao: leitura de dados do arquivo
        clientes = lerClientes(ARQ_DADOS);

        // Cliente c = new Cliente("Leticia", "leticia", "123456", TipoCliente.FANATICO);
        // Cliente c1 = new Cliente("Leticia", "leticia1", "123456", TipoCliente.EMPOLGADO);

        // jogos.add(new Jogo("BTD6", CategoriaJogo.LANCAMENTO, 100, 110));

        // Data hoje = new Data(10, 6, 2022);

        // c.comprar(jogos, hoje);

        // c1.comprar(jogos, hoje);

        // clientes.put(c.getNomeUsuario(), c);
        // clientes.put(c1.getNomeUsuario(), c1);

        // testes
        compras = getComprasClientes(clientes);
        for (Compra compra : compras) {
            System.out.println(compra);
        }
        for (Cliente cl : clientes.values()) {
            System.out.println(cl);
        }

        escreverClientes(clientes, ARQ_DADOS);
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

    public static TreeMap<String, Cliente> lerClientes(String nomeArq) {

        TreeMap<String, Cliente> clientes = new TreeMap<>();
        FileInputStream arq;
        try {
            arq = new FileInputStream(nomeArq);

            ObjectInputStream arqLeitura;
            arqLeitura = new ObjectInputStream(arq);

            clientes = (TreeMap<String, Cliente>) arqLeitura.readObject();

            arqLeitura.close();
        } catch (Exception e) {

            e.printStackTrace();
        }

        return clientes;
    }

    public static ArrayList<Compra> getComprasClientes(TreeMap<String, Cliente> clientes) {
        ArrayList<Compra> compras = new ArrayList<>();
        for (Cliente c : clientes.values()) {
            compras.addAll(c.getComprasHistorico());
        }
        return compras;
    }
}
