package application;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeMap;

import entities.Cliente;

public class Program {

    public static void main(String[] args)
    {

        TreeMap<String, Cliente> clientes = new TreeMap<>();
        Cliente c = new Cliente("Leticia", "leticia", "1234");
        Cliente c1 = new Cliente("Leticia", "leticia1", "1234");
        clientes.put(c.getNomeUsuario(), c);
        clientes.put(c1.getNomeUsuario(), c1);
        escreverClientes(clientes);
    }

    private static void escreverClientes(TreeMap<String, Cliente> clientes) {

        Path path = Paths.get("clientes.bin");
        StringBuilder sb = new StringBuilder();

        for (Cliente c : clientes.values()) {
            sb.append(c.toString());
            sb.append("\n");
        }
        
        byte[] bytes = sb.toString().getBytes(StandardCharsets.UTF_8);
 
        try {
            Files.write(path, bytes);
            System.out.println("Sucesso");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
