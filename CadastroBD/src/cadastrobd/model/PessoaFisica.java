
package cadastrobd.model;

/**
 *
 * @author Maiara
 */
import cadastro.model.PessoaFisicaDAO;
import java.io.Serializable;
import java.util.Scanner;

public class PessoaFisica extends Pessoa implements Serializable {
    private String cpf;
    
    public PessoaFisica(){}

    public PessoaFisica(int id, String nome, String logradouro, int numero, String complemento, String cidade, String estado, String telefone, String email, String cpf) {
        super(id, nome, logradouro, numero, complemento, cidade, estado, telefone, email);
        this.cpf = cpf;
        
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

 

    @Override
    public void exibir(){
        super.exibir();
        System.out.println("CPF: "+this.getCpf());
    }

       
}

