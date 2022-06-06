package entities.cliente.test;

import entities.cliente.Cadastrado;
import entities.cliente.Cliente;
import entities.cliente.Empolgado;
import entities.cliente.Fanatico;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClienteTest {
    @Test
    void criaTest() {
        Cliente clienteC = new Cadastrado("Cadastrado", "Cadastrado", "12345");
        Cliente clienteE = new Empolgado("Empolgado", "Empolgado", "12345");
        Cliente clienteF = new Fanatico("Fanatico", "Fanatico", "12345");

        List<Cliente> clientes = new ArrayList<>();

        clientes.add(clienteC);
        clientes.add(clienteE);
        clientes.add(clienteF);

        for (Cliente c: clientes) {
            System.out.println(1 * c.calculaDesconto());
        }

        for (Cliente c: clientes) {
            System.out.println("Mensalidade " + c.calculaMensalidade());
        }


    }

}