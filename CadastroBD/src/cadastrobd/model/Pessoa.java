
package cadastrobd.model;

/**
 *
 * @author Maiara
 */
import java.io.Serializable;

public class Pessoa implements Serializable {
    private int id;
    private String nome;
    private String logradouro;
    private int numero;
    private String complemento;
    private String cidade;
    private String estado;
    private String telefone;
    private String email;

    public Pessoa(){}
    
    public Pessoa(int id, String nome, String logradouro, int numero, String complemento, String cidade, String estado, String telefone, String email){
        this.nome = nome;
        this.id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;
        this.telefone = telefone;
        this.email = email;
    }
    
    public String getNome() {
        return nome;
    }

    public String getLogradouro() {
        return logradouro;
    }
    
        public int getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public String getTelefone() {
        return telefone;
    }


    public String getEmail() {
        return email;
    }


    public int getId() {
        return id;
    }

 
    
        public void exibir(){
        System.out.println("Id: "+this.getId());
        System.out.println("Nome: "+this.getNome());
        System.out.println("logradouro: "+this.getLogradouro());
        System.out.println("numero: "+this.getNumero());
        System.out.println("complemento: "+this.getComplemento());
        System.out.println("cidade: "+this.getCidade());
        System.out.println("estado: "+this.getEstado());
        System.out.println("telefone: "+this.getTelefone());
        System.out.println("email: "+this.getEmail());
    }

}
