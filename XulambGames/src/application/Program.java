package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TreeMap;

import entities.Cliente;

public class Program {

    public static void main(String[] args) {

        TreeMap<String, Cliente> clientes = new TreeMap<>();
        Cliente c = new Cliente("Leticia", "leticia", "1234");
        Cliente c1 = new Cliente("Leticia", "leticia1", "1234");

        clientes.put(c.getNomeUsuario(), c);
        clientes.put(c1.getNomeUsuario(), c1);

        escreverClientes(clientes);
        lerClientes();
    }

    private static void escreverClientes(TreeMap<String, Cliente> clientes) {

        try {

            FileOutputStream fout = new FileOutputStream("clientes.bin");

            /*
             * A Classe ObjectOutputStream escreve os objetos no FileOutputStream
             */
            ObjectOutputStream oos = new ObjectOutputStream(fout);

            /*
             * Veja aqui a mágica ocorrendo: Estamos gravando um objeto
             * do tipo Address no arquivo address.ser. Atenção: O nosso
             * objeto Address que está sendo gravado, já é gravado de forma
             * serializada
             */
            oos.writeObject(clientes);

            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static void lerClientes() {
        FileInputStream fin;
        try {
            fin = new FileInputStream("clientes.bin");

            /*
             * Responsável por ler o objeto referente ao arquivo
             */
            ObjectInputStream ois;

            ois = new ObjectInputStream(fin);

            /*
             * Aqui a mágica é feita, onde os bytes presentes no arquivo address.ser
             * são convertidos em uma instância de Address.
             */
            TreeMap<String, Cliente> clientes = (TreeMap<String, Cliente>) ois.readObject();
            System.out.println(clientes);

            ois.close();
        } catch (Exception e) {

            e.printStackTrace();
        }

    }
}
