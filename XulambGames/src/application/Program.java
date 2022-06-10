package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeMap;

import entities.cliente.Cliente;

public class Program {

    public static final String ARQ_DADOS = "clientes.bin";

    public static void main(String[] args) {

//        TreeMap<String, Cliente> clientes = new TreeMap<>();
//        ArrayList<Compra> compras = new ArrayList<Compra>();
//        ArrayList<Jogo> jogos = new ArrayList<Jogo>();
//
//        Cliente c = new Cliente("Leticia", "leticia", "1234");
//        Cliente c1 = new Cliente("Leticia", "leticia1", "1234");
//
//        jogos.add(new Jogo("BTD6"));
//        compras.add(new Compra(200, jogos));
//
//        c.setComprasHistorico(compras);
//
//        clientes.put(c.getNomeUsuario(), c);
//        clientes.put(c1.getNomeUsuario(), c1);
//
//        // escreverClientes(clientes, ARQ_DADOS);
//        lerClientes(ARQ_DADOS);
    }

    public static void escreverClientes(TreeMap<String, Cliente> clientes, String nomeArq) {

        try {
            FileOutputStream fout = new FileOutputStream(nomeArq);
            ObjectOutputStream arqSaida = new ObjectOutputStream(fout);
            arqSaida.writeObject(clientes);
            arqSaida.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erro na escrita de dados: arquivo não encontrado");
        } catch (IOException e) {
            System.out.println("Erro na escrita de dados: " + e);
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

            for (Cliente c : clientes.values()) {
                System.out.println(c);
            }

            arqLeitura.close();

        } catch (FileNotFoundException e) {
            System.out.println("Erro na leitura de dados: arquivo não encontrado");
        } catch (IOException e) {
            System.out.println("Erro na leitura de dados: " + e);
        } catch (ClassNotFoundException e) {
            System.out.println("Erro na leitura de dados: Os dados não estão no formato correto");
        }

        return clientes;
    }
}
