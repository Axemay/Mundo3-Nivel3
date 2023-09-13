
package cadastrobd;

import cadastro.model.PessoaFisicaDAO;
import cadastro.model.PessoaJuridicaDAO;
import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaJuridica;
import java.util.Scanner;
import cadastrobd.CadastroBDService;

/**
 * 
 *
 * @author Maiara
 *
 */

public class CadastroBDTeste {
   public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
            PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();

            boolean continuar = true;
            while (continuar) {
                CadastroBDService.exibirMenu();

                String opcao = CadastroBDService.lerOpcao(scanner);

                switch (opcao) {
                    case "1":    
                                CadastroBDService.realizarInclusao
                                (scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    break;
                    
                    case "2": 
                                CadastroBDService.realizarAlteracao
                                (scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    break;
                    
                    case "3": 
                                CadastroBDService.realizarExclusao
                                (scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    break;
                    
                    case "4":
                                CadastroBDService.realizarObtencaoPorID
                                (scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    break;
                    
                    case "5": 
                                CadastroBDService.realizarListagem
                                (scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    break;
                    
                    case "0": 
                        continuar = false;
                    break;
                    
                    default: System.out.println("Opção inválida. Tente novamente.");
                    
                    }
            }
        }
    }
}

