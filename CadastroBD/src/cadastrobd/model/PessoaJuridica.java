
package cadastrobd.model;

/**
 *
 * @author Maiara
 */
import cadastro.model.PessoaJuridicaDAO;
import java.io.Serializable;
import java.util.Scanner;

public class PessoaJuridica extends Pessoa implements Serializable{
    private String cnpj;
    public PessoaJuridica(){}
        
    public PessoaJuridica(int id, String nome, String logradouro, String cidade, String estado, String telefone, String email, String cnpj) {
        super(id, nome, logradouro, cidade, estado, telefone, email);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public void exibir(){
        super.exibir();
        System.out.println("CNPJ: "+this.getCnpj());
    }
}

