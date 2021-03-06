package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.*;

import entities.Compra;
import entities.cliente.Cliente;
import entities.cliente.TipoCliente;
import entities.jogo.CategoriaJogo;
import entities.jogo.Jogo;
import util.CadastroClienteException;
import util.CadastroJogoException;
import util.Data;

public class Sistema {

    public static final String ARQ_CLIENTES = "clientes.bin";
    public static final String ARQ_JOGOS = "jogos.bin";
    static Scanner teclado = new Scanner(System.in);

    private static ArrayList<Jogo> jogos = new ArrayList<>();
    private static TreeMap<String, Cliente> clientes = new TreeMap<>();

    static LocalDate today = LocalDate.now();
    static Data hoje = new Data(today.getDayOfMonth(), today.getMonthValue());

    // #region Métodos do MENU
    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pausa(Scanner teclado) {
        System.out.println("Enter para continuar.");
        teclado.nextLine();
    }

    public static int menu(Scanner teclado) {
        limparTela();
        System.out.println("XULAMBS GAMES");
        System.out.println("======== OPERAÇÕES =======");
        System.out.println("1 - Cadastrar Cliente");
        System.out.println("2 - Registrar Compra");
        System.out.println("3 - Alterar Categoria do Cliente");
        System.out.println("4 - Obter Histórico do Cliente");
        System.out.println("5 - Cadastrar Jogo");
        System.out.println("======== CONSULTAS =======");
        System.out.println("6 - Valor Mensal Vendido");
        System.out.println("7 - Valor Médio das Compras");
        System.out.println("8 - Jogo mais Vendido");
        System.out.println("9 - Jogo menos Vendido");
        System.out.println("0 - Sair");
        int opcao = 0;
        try {
            opcao = teclado.nextInt();
            teclado.nextLine();
        } catch (InputMismatchException ex) {
            teclado.nextLine();
            System.out.println("Somente opções numéricas.");
            opcao = -1;
        }
        return opcao;
    }

    private static void menuCadastroDeCliente() {
        limparTela();
        System.out.println("XULAMBS GAMES");
        System.out.println("======== 1 - Cadastrar Cliente =======");
        System.out.println("\nDigite o Nome do Cliente");

        try {
            String nome = teclado.nextLine();
            System.out.println("\nDigite o Nome de Usuário do Cliente");
            String username = teclado.nextLine();
            System.out.println("\nDigite a Senha do Usuário");
            String senha = teclado.nextLine();
            System.out.println("\nSelecione o tipo do Cliente");
            System.out.println("\n1 - Cliente CADASTRADO");
            System.out.println("\n2 - Cliente EMPOLGADO");
            System.out.println("\n3 - Cliente FANÁTICO");
            TipoCliente tipoCliente = TipoCliente.CADASTRADO;
            int nTipoCliente = teclado.nextInt();
            switch (nTipoCliente) {
                case 2:
                    tipoCliente = TipoCliente.EMPOLGADO;
                    break;
                case 3:
                    tipoCliente = TipoCliente.FANATICO;
                    break;
                case 1:
                default:
                    break;
            }
            clientes.put(nome, new Cliente(nome, username, senha, tipoCliente));
            teclado.nextLine();

        } catch (CadastroClienteException ex) {
            teclado.nextLine();
            System.out.println("Erro: " + ex.getMessage());
        } catch (InputMismatchException ex) {
            teclado.nextLine();
            System.out.println("Somente opções numéricas.");
        }
    }

    private static void menuRegistrarCompra() {
        limparTela();
        System.out.println("XULAMBS GAMES");
        System.out.println("======== 2 - Registrar Compra =======");
        System.out.println("\nDigite o nome de usuário do cliente");
        Cliente cliente = buscarCliente(teclado.nextLine());
        if (cliente == null) {
            System.out.println("Cliente não Registrado!");
            System.out.println("\n Deseja repetir a busca?");
            System.out.println("1 - SIM");
            System.out.println("2 - NÃO");
            teclado.nextLine();
            int opcao = -1;

            try {
                opcao = teclado.nextInt();
                teclado.nextLine();
            } catch (InputMismatchException ex) {
                teclado.nextLine();
            }
            if (opcao == 1)
                menuRegistrarCompra();
        }
        ArrayList<Jogo> jogosComprados = menuRegistrarCompra_Jogos();

        Compra novaCompra = cliente.comprar(jogosComprados, hoje);
        System.out.println(novaCompra);
    }

    private static ArrayList<Jogo> menuRegistrarCompra_Jogos() {
        ArrayList<Jogo> jogosComprados = new ArrayList<>();
        System.out.println("Digite a quantidade de jogos da compra");
        int qtdJogos = teclado.nextInt();
        for (int i = 0; i < qtdJogos; i++) {

            System.out.println("\nJogos disponiveis: ");
            for (int j = 0; j < jogos.size(); j++) {
                System.out.println(j + " - " + jogos.get(j));
            }
            System.out.println("\nDigite o numero do jogo: ");
            int opcao = -1;
            try {
                opcao = teclado.nextInt();
                teclado.nextLine();
            } catch (InputMismatchException ex) {
                teclado.nextLine();
                System.out.println("Somente opções numéricas.");
                opcao = -1;
            }
            if (opcao >= 0 && opcao < jogos.size()) {
                Jogo jogoProcurado = jogos.get(opcao);
                jogosComprados.add(jogoProcurado);
            } else {
                System.out.println("Jogo não encontrado!");
                System.out.println("\n Deseja repetir a busca de todos os jogos?");
                System.out.println("1 - SIM");
                System.out.println("2 - NÃO");
                opcao = teclado.nextInt();
                if (opcao == 1) {
                    menuRegistrarCompra_Jogos();
                } else {
                    return null;
                }
            }

        }
        return jogosComprados;
    }

    private static void menuAlterarCategoriaCliente() {
        limparTela();
        System.out.println("XULAMBS GAMES");
        System.out.println("======== 3 - Alterar categoria do cliente =======");
        System.out.println("\nDigite o nome de usuário do cliente");
        Cliente cliente = buscarCliente(teclado.nextLine());
        if (cliente == null) {
            System.out.println("Cliente não Registrado!");
            System.out.println("\n Deseja repetir a busca?");
            System.out.println("1 - SIM");
            System.out.println("2 - NÃO");
            teclado.nextLine();
            int opcao = getOpcaoIntTeclado();
            if (opcao == 1)
                menuRegistrarCompra();
        }
        System.out.println("\nSelecione o tipo do Cliente");
        System.out.println("\n1 - Cliente CADASTRADO");
        System.out.println("\n2 - Cliente EMPOLGADO");
        System.out.println("\n3 - Cliente FANÁTICO");
        TipoCliente tipoCliente = TipoCliente.CADASTRADO;
        int nTipoCliente;
        try {
            nTipoCliente = teclado.nextInt();
            teclado.nextLine();
            switch (nTipoCliente) {
                case 2:
                    tipoCliente = TipoCliente.EMPOLGADO;
                    break;
                case 3:
                    tipoCliente = TipoCliente.FANATICO;
                    break;
                case 1:
                default:
                    break;
            }
            cliente.alterarTipo(tipoCliente);
        } catch (InputMismatchException ex) {
            teclado.nextLine();
            System.out.println("Somente opções numéricas.");
        }

    }

    private static void menuObterHistoricoDoCliente() {
        limparTela();
        System.out.println("XULAMBS GAMES");
        System.out.println("======== 4 - Obter Histórico do Cliente =======");
        System.out.println("\nDigite o nome de usuário do Cliente");
        Cliente cliente = buscarCliente(teclado.nextLine());
        if (cliente == null) {
            System.out.println("Cliente não Registrado!");
            System.out.println("\n Deseja repetir a busca?");
            System.out.println("1 - SIM");
            System.out.println("2 - NÃO");
            int opcao = -1;

            try {
                opcao = teclado.nextInt();
                teclado.nextLine();
            } catch (InputMismatchException ex) {
                teclado.nextLine();
            }
            if (opcao == 1)
                menuObterHistoricoDoCliente();
        } else {
            System.out.println(cliente.toString());
        }
    }

    private static void menuCadastroDeJogo() {
        limparTela();
        System.out.println("XULAMBS GAMES");
        System.out.println("======== 5 - Cadastrar Jogo =======");
        System.out.println("\nDigite o Nome do Jogo");

        try {
            String nome = teclado.nextLine();

            System.out.println("\nSelecione o tipo de jogo");
            System.out.println("\n1 - Lancamento");
            System.out.println("\n2 - Premium");
            System.out.println("\n3 - Regular");
            System.out.println("\n4 - Promocao");
            CategoriaJogo categoriaJogo = CategoriaJogo.LANCAMENTO;

            int nTipoJogo = teclado.nextInt();
            switch (nTipoJogo) {
                case 2:
                    categoriaJogo = CategoriaJogo.PREMIUM;
                    break;
                case 3:
                    categoriaJogo = CategoriaJogo.REGULAR;
                    break;
                case 4:
                    categoriaJogo = CategoriaJogo.PROMOCAO;
                    break;
                default:
                    break;

            }
            System.out.println("\nDigite o preço base do jogo");
            double preco = teclado.nextDouble();
            System.out.println("\nDigite o preço atual do jogo");
            double precoAtual = teclado.nextDouble();

            jogos.add(new Jogo(nome, categoriaJogo, preco, precoAtual));
            teclado.nextLine();
        } catch (CadastroJogoException ex) {
            teclado.nextLine();
            System.out.println("Erro: " + ex.getMessage());
        } catch (InputMismatchException ex) {
            teclado.nextLine();
            System.out.println("Somente opções numéricas.");
        }

    }

    private static void menuValorMensalVendido() {
        limparTela();
        System.out.println("XULAMBS GAMES");
        System.out.println("======== 6 - Valor Mensal Vendido =======");
        System.out.println("Informe o mês e o ano buscado (Ex: 06/2022)");
        try {
            
            String[] str = teclado.nextLine().split("/");
            int mes = Integer.parseInt(str[0]);
            int ano = Integer.parseInt(str[1]);
            double valorMensalProcurado = valorMensalVendido(mes, ano);
            System.out.println("O valor mensal vendido de " + mes + "/" + ano + " foi de R$" + valorMensalProcurado);
            System.out.println("\nPressione qualquer tecla para voltar ao menu principal");

        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Erro no formato inserido. Insira no formato MM/YYYY (Ex: 06/2022)");
            teclado.nextLine();
        } catch (NumberFormatException ex) {
            System.out.println("Erro no formato inserido. Insira no formato MM/YYYY (Ex: 06/2022)");
            teclado.nextLine();
        }
    }

    private static void menuValorMedioDasCompras() {
        limparTela();
        System.out.println("XULAMBS GAMES");
        System.out.println("======== 7 - Valor Médio das Compras =======");
        double valorMedio = valorMedioCompras();
        System.out.println("O valor médio das compras é de: R$" + valorMedio);
    }

    private static void menuJogoMaisVendido() {
        limparTela();
        System.out.println("XULAMBS GAMES");
        System.out.println("======== 8 - Jogo Mais Vendido =======");
        Jogo maisVendido = jogoMaisVendido();
        System.out.println("O Jogo mais vendido é: " + maisVendido.getNome() + " com " + maisVendido.getQtdVendas()
                + " unidades vendidas");
    }

    private static void menuJogoMenosVendido() {
        limparTela();
        System.out.println("XULAMBS GAMES");
        System.out.println("======== 9 - Jogo Menos Vendido =======");
        Jogo menosVendido = jogoMenosVendido();
        System.out.println("O Jogo menos vendido é: " + menosVendido.getNome() + " com " + menosVendido.getQtdVendas()
                + " unidades vendidas");
    }

    /**
     * Opções inválidas são desconsideradas
     * 
     * @param opcao
     * @return
     */
    private static int getOpcaoIntTeclado() {
        int opcao = -1;
        try {
            opcao = teclado.nextInt();
            teclado.nextLine();
        } catch (InputMismatchException ex) {
            teclado.nextLine();
        }
        return opcao;
    }

    // #endregion

    public static void main(String[] args) {

        // inicializacao: leitura de dados do arquivo
        clientes = lerClientes(ARQ_CLIENTES);
        jogos = lerJogos(ARQ_JOGOS);

        int opcao;
        do {

            opcao = menu(teclado);

            switch (opcao) {
                case 1:
                    menuCadastroDeCliente();
                    break;
                case 2:
                    menuRegistrarCompra();
                    break;
                case 3:
                    menuAlterarCategoriaCliente();
                    break;
                case 4:
                    menuObterHistoricoDoCliente();
                    break;
                case 5:
                    menuCadastroDeJogo();
                    break;
                case 6:
                    menuValorMensalVendido();
                    break;
                case 7:
                    menuValorMedioDasCompras();
                    break;
                case 8:
                    menuJogoMaisVendido();
                    break;
                case 9:
                    menuJogoMenosVendido();
                    break;
            }
            pausa(teclado);
            limparTela();
        } while (opcao != 0);

        // escrita de dados no arquivo
        escreverClientes(clientes, ARQ_CLIENTES);
        escreverJogos(jogos, ARQ_JOGOS);
    }

    // #region Leitura e Escrita
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

            for (Cliente c : clientes.values()) {
                System.out.println(c);
            }

            clientes = (TreeMap<String, Cliente>) arqLeitura.readObject();

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

    public static void escreverJogos(ArrayList<Jogo> jogos, String nomeArq) {

        try {
            FileOutputStream fout = new FileOutputStream(nomeArq);
            ObjectOutputStream arqSaida = new ObjectOutputStream(fout);
            arqSaida.writeObject(jogos);
            arqSaida.close();

        } catch (FileNotFoundException e) {
            System.out.println("Erro na escrita de dados: arquivo não encontrado");
        } catch (IOException e) {
            System.out.println("Erro na escrita de dados: " + e);
        }
    }

    public static ArrayList<Jogo> lerJogos(String nomeArq) {

        ArrayList<Jogo> jogos = new ArrayList<>();
        FileInputStream arq;
        try {
            arq = new FileInputStream(nomeArq);

            ObjectInputStream arqLeitura;
            arqLeitura = new ObjectInputStream(arq);

            jogos = (ArrayList<Jogo>) arqLeitura.readObject();

            for (Jogo jogo : jogos) {
                System.out.println(jogo);
            }

            arqLeitura.close();

        } catch (FileNotFoundException e) {
            System.out.println("Erro na leitura de dados: arquivo não encontrado");
        } catch (IOException e) {
            System.out.println("Erro na leitura de dados: " + e);
        } catch (ClassNotFoundException e) {
            System.out.println("Erro na leitura de dados: Os dados não estão no formato correto");
        }

        return jogos;
    }

    // #endregion

    // #region Consultas XulambGames

    private static Cliente buscarCliente(String nomeUsuario) {
        return clientes.values().stream().filter(c -> c.getNomeUsuario().equals(nomeUsuario)).findFirst().orElse(null);
    }

    private static Jogo buscarJogo(String nomeJogo) {
        return jogos.stream().filter(j -> j.getNome().equals(nomeJogo)).findFirst().orElse(null);
    }

    public static double valorMensalVendido(int mesProcurado, int anoProcurado) {
        return Math.round(todasAsCompras().stream()
                .filter(c -> c.getData().getMes() == mesProcurado && c.getData().getAno() == anoProcurado)
                .mapToDouble(Compra::getValorPago)
                .sum() * 100.0) / 100.0;
    }

    public static double valorMedioCompras() {
        ArrayList<Compra> todasAsCompras = todasAsCompras();
        double valorTotal = Math.round(todasAsCompras.stream()
                .mapToDouble(Compra::getValorPago)
                .sum() * 100.0) / 100.0;
        return valorTotal / todasAsCompras.size();
    }

    public static Jogo jogoMaisVendido() {

        return jogos.stream()
                .max(Comparator.comparingInt(Jogo::getQtdVendas))
                .orElse(null);
    }

    public static Jogo jogoMenosVendido() {
        return jogos.stream()
                .min(Comparator.comparingInt(Jogo::getQtdVendas))
                .orElse(null);
    }

    public static ArrayList<Compra> todasAsCompras() {
        ArrayList<Compra> todasAsCompras = new ArrayList<>();
        for (Cliente c : clientes.values()) {
            todasAsCompras.addAll(c.getComprasHistorico());
        }
        return todasAsCompras;
    }
    // #endregion
}
