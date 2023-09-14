
package cadastrobd;

import cadastro.model.PessoaFisicaDAO;
import cadastro.model.PessoaJuridicaDAO;
import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaJuridica;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;

/**
 * 
 *
 * @author Maiara
 *
 */

public class CadastroBD {
   public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
            PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();

            boolean continuar = true;
            while (continuar) {
                exibirMenu();

                String opcao = lerOpcao(scanner);

                switch (opcao) {
                    case "1":    
                                executarIncluir
                                (scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    break;
                    
                    case "2": 
                                executarAlterar
                                (scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    break;
                    
                    case "3": 
                                executarExcluir
                                (scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    break;
                    
                    case "4":
                                executarObterID
                                (scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    break;
                    
                    case "5": 
                                executarListar
                                (scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    break;
                    
                    case "0": 
                        continuar = false;
                    break;
                    
                    default: System.out.println("Opção inválida. Tente novamente.");
                    break;
                    }
            }
        }
    }
   
    public static void exibirMenu() {
        String menu = "====================================" +
                "\n1 - Incluir Pessoa" +
                "\n2 - Alterar Pessoa" +
                "\n3 - Excluir Pessoa" +
                "\n4 - Buscar pelo ID" +
                "\n5 - Exibir todos" +
                "\n0 - Finalizar programa" +
                "\n====================================\n";
          System.out.println(menu);
        
    }


    public static String lerOpcao(Scanner scanner) {
        String opcao = null;
        boolean opcaoValida = false;
        while (!opcaoValida) {
            try {
                System.out.print("\nEscolha uma opcao: ");
                opcao = scanner.nextLine();
                opcaoValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Opcao inválida. Insira uma opção válida do MENU.");

            }
        }
        return opcao;
    }

    public static String selecionarTipo(Scanner scanner) {
        String tipo = "";
        boolean tipoValido = false;
        while (!tipoValido) {
            System.out.print("\nF - Pessoa Fisica | J - Pessoa Juridica ");
            tipo = scanner.next();
            if (tipo.equalsIgnoreCase("F") || tipo.equalsIgnoreCase("J")) {
                tipoValido = true;
            } else {
                System.out.println("Tipo invalido. Digite F ou J.");
            }
        }
        return tipo;
    }
        public static void executarIncluir(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) {
        String tipoInclusao = selecionarTipo(scanner);
        if (tipoInclusao.equalsIgnoreCase("F")) {

                try{
                    PessoaFisica pessoaFisica;
                    pessoaFisica = incluirPessoaFisica(scanner);
                    
                    pessoaFisicaDAO.incluirPessoaFisica(pessoaFisica);
                }catch (Exception e) {
                    System.out.println("Erro. Não foi possível concluir a solicitação "+e);
                }
                
        } else if (tipoInclusao.equalsIgnoreCase("J")) {
            incluirPessoaJuridica(scanner, pessoaJuridicaDAO);
        }
    }
        
        
        

    public static void executarAlterar(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) {
        String tipoAlteracao = selecionarTipo(scanner);
        
        if (tipoAlteracao.equalsIgnoreCase("F")) {
            alterarPessoaFisica(scanner, pessoaFisicaDAO);
            
        } else if (tipoAlteracao.equalsIgnoreCase("J")) {
            alterarPessoaJuridica(scanner, pessoaJuridicaDAO);
        }
    }
    

    public static void executarExcluir(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) {
        String tipoExclusao = selecionarTipo(scanner);
        
        
        
        
        if (tipoExclusao.equalsIgnoreCase("F")) {
            System.out.print("Digite o ID da pessoa física para exclusão: ");
        int id_Pessoa = scanner.nextInt();
            pessoaFisicaDAO.excluirPessoaFisica(id_Pessoa);
            System.out.println("Pessoa física excluida com sucesso.");
            
        
        
        } else if (tipoExclusao.equalsIgnoreCase("J")) {
            System.out.print("Digite o ID da pessoa jurídica para exclusão: ");
            int id_Pessoa = scanner.nextInt();
            pessoaJuridicaDAO.excluirPessoaJuridica(id_Pessoa);
            System.out.println("Pessoa jurídica excluida com sucesso.");
            
        }
        scanner.nextLine();
}


    public static void executarObterID(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) {
        String tipoObtencao = selecionarTipo(scanner);
        if (tipoObtencao.equalsIgnoreCase("F")) {
            exibirPFId(scanner, pessoaFisicaDAO);
        } else if (tipoObtencao.equalsIgnoreCase("J")) {
            exibirPJID(scanner, pessoaJuridicaDAO);
        }
    }

    public static void executarListar(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) {
    String tipoListagem = selecionarTipo(scanner);
    scanner.nextLine(); 
    if (tipoListagem.equalsIgnoreCase("F")) {
        List<PessoaFisica> pessoasFisicas = pessoaFisicaDAO.listarTodasPessoasFisicas();
        System.out.println("\nLista de Pessoas Físicas");
        System.out.println("====================================");

        for (PessoaFisica pessoaFisica : pessoasFisicas) {
            pessoaFisica.exibir();
            System.out.println("====================================\n");
        }
    } else if (tipoListagem.equalsIgnoreCase("J")) {
        List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaDAO.listarTodasPessoasJuridicas();
                System.out.println("\nLista de Pessoas Jurídicas");
                System.out.println("====================================");
        for (PessoaJuridica pessoaJuridica : pessoasJuridicas) {
            pessoaJuridica.exibir();
            System.out.println("====================================\n");
        }
        
    }
}


    public static PessoaFisica incluirPessoaFisica(Scanner scanner) {
        PessoaFisica pessoaFisica = new PessoaFisica();
        scanner.nextLine(); 

        System.out.print("Digite o nome da pessoa fisica: ");
        String nome = scanner.nextLine();
        pessoaFisica.setNome(nome);

        System.out.print("Digite o CPF da pessoa fisica: ");
        String cpf = scanner.nextLine();
        pessoaFisica.setCpf(cpf);
        
        System.out.print("Digite o Logradouro da pessoa fisica: ");
        String logradouro = scanner.nextLine();
        pessoaFisica.setLogradouro(logradouro);
        
        System.out.print("Digite o cidade da pessoa fisica: ");
        String cidade = scanner.nextLine();
        pessoaFisica.setCidade(cidade);
        
        System.out.print("Digite o estado da pessoa fisica: ");
        String estado = scanner.nextLine();
        pessoaFisica.setEstado(estado);
        
        System.out.print("Digite o telefone da pessoa fisica: ");
        String telefone = scanner.nextLine();
        pessoaFisica.setTelefone(telefone);
        
        System.out.print("Digite o email da pessoa fisica: ");
        String email = scanner.nextLine();
        pessoaFisica.setEmail(email);

        System.out.println("Pessoa fisica criada.");
        return pessoaFisica;
     
    }

    public static void incluirPessoaJuridica(Scanner scanner, PessoaJuridicaDAO pessoaJuridicaDAO) {
        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        scanner.nextLine(); 

        System.out.print("Digite o nome da pessoa jurídica: ");
        String nome = scanner.nextLine();
        pessoaJuridica.setNome(nome);

        System.out.print("Digite o CNPJ da pessoa jurídica: ");
        String cnpj = scanner.nextLine();
        pessoaJuridica.setCnpj(cnpj);

        System.out.print("Digite o Logradouro da pessoa jurídica: ");
        String logradouro = scanner.nextLine();
        pessoaJuridica.setLogradouro(logradouro);

        System.out.print("Digite a cidade da pessoa jurídica: ");
        String cidade = scanner.nextLine();
        pessoaJuridica.setCidade(cidade);

        System.out.print("Digite o estado da pessoa jurídica: ");
        String estado = scanner.nextLine();
        pessoaJuridica.setEstado(estado);

        System.out.print("Digite o telefone da pessoa jurídica: ");
        String telefone = scanner.nextLine();
        pessoaJuridica.setTelefone(telefone);

        System.out.print("Digite o email da pessoa jurídica: ");
        String email = scanner.nextLine();
        pessoaJuridica.setEmail(email);

        pessoaJuridicaDAO.incluirPessoaJuridica(pessoaJuridica);
        System.out.println("Pessoa jurídica incluída com sucesso.");
    }


    public static void alterarPessoaFisica(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO) {
        System.out.print("Digite o ID da pessoa física a ser alterada: ");
        int id = scanner.nextInt();
        
        PessoaFisica pessoaFisica = new PessoaFisica();
        PessoaFisica pessoaFisicaAntiga = pessoaFisicaDAO.getPessoaFisica(id);
        if (pessoaFisicaAntiga != null) {
            System.out.println("Pessoa física encontrada:");
            pessoaFisicaAntiga.exibir();

            scanner.nextLine(); 

            System.out.print("Digite o nome da pessoa física: ");
            String nome = scanner.nextLine();
            pessoaFisica.setNome(nome);

            System.out.print("Digite o CPF da pessoa física: ");
            String cpf = scanner.nextLine();
            pessoaFisica.setCpf(cpf);

            System.out.print("Digite o Logradouro da pessoa física: ");
            String logradouro = scanner.nextLine();
            pessoaFisica.setLogradouro(logradouro);

            System.out.print("Digite o cidade da pessoa física: ");
            String cidade = scanner.nextLine();
            pessoaFisica.setCidade(cidade);

            System.out.print("Digite o estado da pessoa física: ");
            String estado = scanner.nextLine();
            pessoaFisica.setEstado(estado);

            System.out.print("Digite o telefone da pessoa física: ");
            String telefone = scanner.nextLine();
            pessoaFisica.setTelefone(telefone);

            System.out.print("Digite o email da pessoa física: ");
            String email = scanner.nextLine();
            pessoaFisica.setEmail(email);

            
            pessoaFisicaDAO.alterarPessoaFisica(pessoaFisicaAntiga, pessoaFisica);
            System.out.println("Pessoa física alterada com sucesso.");
        } else {
            System.out.println("Pessoa física não encontrada com o ID informado.");
        }
    }
    
    
        public static void alterarPessoaJuridica(Scanner scanner, PessoaJuridicaDAO pessoaJuridicaDAO) {
        System.out.print("Digite o ID da pessoa jurídica a ser alterada: ");
        int id = scanner.nextInt();

        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        PessoaJuridica pessoaJuridicaAntiga = pessoaJuridicaDAO.getPessoaJuridica(id);
        if (pessoaJuridicaAntiga != null) {
            System.out.println("Pessoa jurídica encontrada:");
            pessoaJuridica.exibir();

            scanner.nextLine(); 

            System.out.print("Digite o nome da pessoa física: ");
            String nome = scanner.nextLine();
            pessoaJuridica.setNome(nome);

            System.out.print("Digite o CNPJ da pessoa física: ");
            String cnpj = scanner.nextLine();
            pessoaJuridica.setCnpj(cnpj);

            System.out.print("Digite o Logradouro da pessoa física: ");
            String logradouro = scanner.nextLine();
            pessoaJuridica.setLogradouro(logradouro);

            System.out.print("Digite o cidade da pessoa física: ");
            String cidade = scanner.nextLine();
            pessoaJuridica.setCidade(cidade);

            System.out.print("Digite o estado da pessoa física: ");
            String estado = scanner.nextLine();
            pessoaJuridica.setEstado(estado);

            System.out.print("Digite o telefone da pessoa física: ");
            String telefone = scanner.nextLine();
            pessoaJuridica.setTelefone(telefone);

            System.out.print("Digite o email da pessoa física: ");
            String email = scanner.nextLine();
            pessoaJuridica.setEmail(email);

            
            pessoaJuridicaDAO.alterarPessoaJuridica(pessoaJuridicaAntiga, pessoaJuridica);
            System.out.println("Pessoa física alterada com sucesso.");
        } else {
            System.out.println("Pessoa física não encontrada com o ID informado.");
        }
    }


    public static void listarPessoasJuridicas(PessoaJuridicaDAO pessoaJuridicaDAO) {
    List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaDAO.listarTodasPessoasJuridicas();
    System.out.println("Dados de Pessoa Jurídica");
    for (PessoaJuridica pessoaJuridica : pessoasJuridicas) {
        System.out.println(pessoaJuridica.toString());
    }
}


public static void listarPessoasFisicas(PessoaFisicaDAO pessoaFisicaDAO) {
    List<PessoaFisica> pessoasFisicas = pessoaFisicaDAO.listarTodasPessoasFisicas();
    if (pessoasFisicas.isEmpty()) {
        System.out.println("Não há pessoas físicas cadastradas.");
    } else {
        System.out.println("Dados de Pessoa Física");
        for (PessoaFisica pessoaFisica : pessoasFisicas) {
            System.out.println(pessoaFisica.toString());
        }
    }
}

    public static void exibirPFId(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO) {
        System.out.println("Digite o ID da pessoa Física para exibição:");
        int id_Pessoa = scanner.nextInt();
        scanner.nextLine(); 

        PessoaFisica pessoaFisica = pessoaFisicaDAO.getPessoaFisicaById(id_Pessoa);
        if (pessoaFisica != null) {
            System.out.println("====================================\n");
            pessoaFisica.exibir();
            
        } else {
            System.out.println("ID não encontrado para Pessoa Física");
        }
    }


    public static void exibirPJID(Scanner scanner, PessoaJuridicaDAO pessoaJuridicaDAO) {
        System.out.println("Digite o ID da pessoa Jurídica para exibição:");
        int id_Pessoa = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner
        
        PessoaJuridica pessoaJuridica = pessoaJuridicaDAO.getPessoaJuridicaById(id_Pessoa);
        if (pessoaJuridica != null) {
            System.out.println("====================================\n");
            pessoaJuridica.exibir();
        } else {
            System.out.println("ID não encontrado para Pessoa Jurídica.");
        }
    }
    
//public static int selecionarIdPessoa(Scanner scanner) {
//    int id = 0;
//    boolean idValido = false;
//    while (!idValido) {
//        try {
//            System.out.print("Digite o ID da pessoa: ");
//            id = scanner.nextInt();
//            idValido = true;
//        } catch (InputMismatchException e) {
//            System.out.println("ID inválido. Insira um ID válido.");
//
//        }
//    }
//    return id;
//}

}

