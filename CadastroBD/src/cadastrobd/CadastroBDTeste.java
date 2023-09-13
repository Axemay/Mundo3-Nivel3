
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
                                CadastroBDService.executarIncluir
                                (scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    break;
                    
                    case "2": 
                                CadastroBDService.executarAlterar
                                (scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    break;
                    
                    case "3": 
                                CadastroBDService.executarExcluir
                                (scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    break;
                    
                    case "4":
                                CadastroBDService.executarObterID
                                (scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    break;
                    
                    case "5": 
                                CadastroBDService.executarListar
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
}

