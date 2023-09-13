package cadastro.model;

import cadastro.model.util.ConectorBD;
import cadastrobd.model.PessoaJuridica;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maiara
 */


public class PessoaJuridicaDAO {

    private final ConectorBD conectorBD;

    public PessoaJuridicaDAO() {
        this.conectorBD = new ConectorBD();
    }


    public PessoaJuridica getPessoaJuridica(int id) {
        PessoaJuridica pessoaJuridica = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = conectorBD.getConnection();
            
            String sql = "SELECT * FROM PessoaJuridica WHERE idPessoa = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                pessoaJuridica = new PessoaJuridica();
                pessoaJuridica.setId(resultSet.getInt("idPessoa"));
                pessoaJuridica.setNome(resultSet.getString("nome"));
                pessoaJuridica.setCnpj(resultSet.getString("CNPJ"));

            }
        } catch (SQLException e) {

        } finally {
            conectorBD.close(resultSet);
            conectorBD.close(statement);
            conectorBD.close(connection);
        }

        return pessoaJuridica;
    }
    
    public List<PessoaJuridica> listarTodasPessoasJuridicas() {
        List<PessoaJuridica> pessoasJuridicas = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
             connection = conectorBD.getConnection();
            
            String sql = "SELECT * FROM Pessoa RIGHT JOIN PessoaJuridica ON Pessoa.idPessoa = PessoaJuridica.idPessoa";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
            PessoaJuridica pessoaJuridica = new PessoaJuridica();
            pessoaJuridica.setId(resultSet.getInt("idPessoa"));
            pessoaJuridica.setNome(resultSet.getString("nome"));
            pessoaJuridica.setLogradouro(resultSet.getString("logradouro"));
            pessoaJuridica.setCidade(resultSet.getString("cidade"));
            pessoaJuridica.setEstado(resultSet.getString("estado"));
            pessoaJuridica.setTelefone(resultSet.getString("telefone"));
            pessoaJuridica.setEmail(resultSet.getString("email"));
            pessoaJuridica.setCnpj(resultSet.getString("CNPJ"));
            
            pessoasJuridicas.add(pessoaJuridica);
        }
    } catch (SQLException e) {
         System.out.println("Erro do tipo: "+e);
    } finally {
        conectorBD.close(resultSet);
        conectorBD.close(statement);
        conectorBD.close(connection);
    }

    return pessoasJuridicas;
}
    
    public void incluirPessoaJuridica(PessoaJuridica pessoaJuridica) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false); 

                           // Inserir na tabela Pessoa
        String sqlPessoa = "INSERT INTO Pessoa (nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?)";
        statement = connection.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, pessoaJuridica.getNome());
        statement.setString(2, pessoaJuridica.getLogradouro());
        statement.setString(3, pessoaJuridica.getCidade());
        statement.setString(4, pessoaJuridica.getEstado());
        statement.setString(5, pessoaJuridica.getTelefone());
        statement.setString(6, pessoaJuridica.getEmail());
        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        int idPessoa;
        if (generatedKeys.next()) {
            idPessoa = generatedKeys.getInt(1);
        }
            // Inserir na tabela PessoaJuridica
            String sql = "INSERT INTO PessoaJuridica (nome, CNPJ) VALUES (?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, pessoaJuridica.getNome());
            statement.setString(2, pessoaJuridica.getCnpj());
            statement.executeUpdate();

            connection.commit(); 
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); 
                } catch (SQLException ex) {
                }
            }
        } finally {
            conectorBD.close(statement);
            conectorBD.close(connection);
        }
    }
    
    public void alterarPessoaJuridica(PessoaJuridica pessoaJuridica) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false); 


            String sqlVerificarExistencia = "SELECT idPessoa FROM PessoaJuridica WHERE idPessoa = ?";
            statement = connection.prepareStatement(sqlVerificarExistencia);
            statement.setInt(1, pessoaJuridica.getId());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                String sql = "UPDATE PessoaJuridica SET nome = ?, CNPJ = ? WHERE idPessoa = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, pessoaJuridica.getNome());
                statement.setString(2, pessoaJuridica.getCnpj());
                statement.setInt(3, pessoaJuridica.getId());
                statement.executeUpdate();

                connection.commit(); 
            } else {
                System.out.println("A PessoaJuridica com o ID especificado não existe no banco de dados.");
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); 
                } catch (SQLException ex) {
                }
            }
        } finally {
            conectorBD.close(statement);
            conectorBD.close(connection);
        }
    }
    
public PessoaJuridica getPessoaJuridicaById(int id) {
    PessoaJuridica pessoaJuridica = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
        connection = conectorBD.getConnection();
        
        String sql = "SELECT * FROM Pessoa RIGHT JOIN PessoaJuridica ON Pessoa.idPessoa = PessoaJuridica.idPessoa WHERE Pessoa.idPessoa = ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        resultSet = statement.executeQuery();

        if (resultSet.next()) {

            pessoaJuridica = new PessoaJuridica();
            pessoaJuridica.setId(resultSet.getInt("idPessoa"));
            pessoaJuridica.setNome(resultSet.getString("nome"));
            pessoaJuridica.setLogradouro(resultSet.getString("logradouro"));
            pessoaJuridica.setCidade(resultSet.getString("cidade"));
            pessoaJuridica.setEstado(resultSet.getString("estado"));
            pessoaJuridica.setTelefone(resultSet.getString("telefone"));
            pessoaJuridica.setEmail(resultSet.getString("email"));
            pessoaJuridica.setCnpj(resultSet.getString("CNPJ"));
            
            
        }
    } catch (SQLException e) {

    } finally {
        conectorBD.close(resultSet);
        conectorBD.close(statement);
        conectorBD.close(connection);
    }

    return pessoaJuridica;
}

    
    public void excluirPessoaJuridica(int id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false); 

            String sqlVerificarExistencia = "SELECT idPessoa FROM PessoaJuridica WHERE idPessoa = ?";
            statement = connection.prepareStatement(sqlVerificarExistencia);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String sql = "DELETE FROM PessoaJuridica WHERE idPessoa = ?";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, id);
                statement.executeUpdate();

                connection.commit(); 
                System.out.println("Pessoa jurídica excluída com sucesso.");
            } else {
                System.out.println("A pessoa com o ID especificado não foi encontrada.");
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); 
                } catch (SQLException ex) {
                }
            }
        } finally {
            conectorBD.close(statement);
            conectorBD.close(connection);
        }
    }
}