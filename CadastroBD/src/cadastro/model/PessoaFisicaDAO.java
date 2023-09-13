package cadastro.model;

import cadastro.model.util.ConectorBD;
import cadastrobd.model.PessoaFisica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maiara
 */


public class PessoaFisicaDAO {

    private final ConectorBD conectorBD;

    public PessoaFisicaDAO() {
        this.conectorBD = new ConectorBD();
    }

    
    public PessoaFisica getPessoaFisica(int id) {
        PessoaFisica pessoaFisica = null;
        Connection connection = null;
        PreparedStatement statementPessoa = null;
        PreparedStatement statementPessoaFisica = null;
        ResultSet resultSetPessoa = null;
        ResultSet resultSetPessoaFisica = null;

        try {
            connection = conectorBD.getConnection();
            
            
            
            String sqlPessoa = "SELECT * FROM Pessoa WHERE idPessoa = ?";

            
            statementPessoa = connection.prepareStatement(sqlPessoa);
            statementPessoa.setInt(1, id);
            resultSetPessoa = statementPessoa.executeQuery();

            if (resultSetPessoa.next()) {
                pessoaFisica = new PessoaFisica();
                pessoaFisica.setId(resultSetPessoa.getInt("idPessoa"));
                pessoaFisica.setNome(resultSetPessoa.getString("nome"));
                pessoaFisica.setLogradouro(resultSetPessoa.getString("logradouro"));
                pessoaFisica.setCidade(resultSetPessoa.getString("cidade"));
                pessoaFisica.setEstado(resultSetPessoa.getString("estado"));
                pessoaFisica.setEmail(resultSetPessoa.getString("email"));
                pessoaFisica.setTelefone(resultSetPessoa.getString("telefone"));

            }

            String sqlPessoaFisica = "SELECT * FROM PessoaFisica WHERE idPessoa = ?";
            statementPessoaFisica = connection.prepareStatement(sqlPessoaFisica);
            statementPessoaFisica.setInt(1, id);
            resultSetPessoaFisica = statementPessoaFisica.executeQuery();

            if (resultSetPessoaFisica.next()) {
                pessoaFisica.setCpf(resultSetPessoaFisica.getString("CPF"));
            }
            statementPessoaFisica.execute();
        } catch (SQLException e) {
            System.out.println("Erro. Não foi possível concluir a solicitação");
        } finally {
            conectorBD.close(resultSetPessoaFisica);
            conectorBD.close(statementPessoaFisica);
            conectorBD.close(resultSetPessoa);
            conectorBD.close(statementPessoa);
            conectorBD.close(connection);
        }

        return pessoaFisica;
    }
        

     public List<PessoaFisica> listarTodasPessoasFisicas() {
        List<PessoaFisica> pessoasFisicas = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
             connection = conectorBD.getConnection();
            
            String sql = "SELECT * FROM Pessoa RIGHT JOIN PessoaFisica ON Pessoa.idPessoa = PessoaFisica.idPessoa";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
            PessoaFisica pessoaFisica = new PessoaFisica();
            pessoaFisica.setId(resultSet.getInt("idPessoa"));
            pessoaFisica.setNome(resultSet.getString("nome"));
            pessoaFisica.setLogradouro(resultSet.getString("logradouro"));
            pessoaFisica.setCidade(resultSet.getString("cidade"));
            pessoaFisica.setEstado(resultSet.getString("estado"));
            pessoaFisica.setTelefone(resultSet.getString("telefone"));
            pessoaFisica.setEmail(resultSet.getString("email"));
            pessoaFisica.setCpf(resultSet.getString("CPF"));
            
            pessoasFisicas.add(pessoaFisica);
        }
    } catch (SQLException e) {

    } finally {
        conectorBD.close(resultSet);
        conectorBD.close(statement);
        conectorBD.close(connection);
    }

    return pessoasFisicas;
}
    

    public void incluirPessoaFisica(PessoaFisica pessoaFisica) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int idPessoa = 0;
        try {
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false); 

        String sqlPessoa = "INSERT INTO Pessoa (nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS);
        
        
  
        preparedStatement.setString(1, pessoaFisica.getNome());
        preparedStatement.setString(2, pessoaFisica.getLogradouro());
        preparedStatement.setString(3, pessoaFisica.getCidade());
        preparedStatement.setString(4, pessoaFisica.getEstado());
        preparedStatement.setString(5, pessoaFisica.getEmail());
        preparedStatement.setString(6, pessoaFisica.getTelefone());
        
        preparedStatement.execute();
       

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        
        if (generatedKeys.next()) {
            idPessoa = generatedKeys.getInt(1);
        }

            String sql = "INSERT INTO PessoaFisica (idPessoa, CPF) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idPessoa);
            preparedStatement.setString(2, pessoaFisica.getCpf());
            preparedStatement.execute();

            connection.commit(); 
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); 
                } catch (SQLException ex) {
                }
            }
        } finally {

            conectorBD.close(preparedStatement);
            conectorBD.close(connection);
        }
    }
    

    public void alterarPessoaFisica(PessoaFisica pessoaFisica) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false); 


            String sqlVerificarExistencia = "SELECT idPessoa FROM PessoaFisica WHERE idPessoa = ?";
            statement = connection.prepareStatement(sqlVerificarExistencia);
            statement.setInt(1, pessoaFisica.getId());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                String sql = "UPDATE PessoaFisica SET nome = ?, CPF = ? WHERE idPessoa = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, pessoaFisica.getNome());
                statement.setString(2, pessoaFisica.getCpf());
                statement.setInt(3, pessoaFisica.getId());
                statement.executeUpdate();

                connection.commit(); 
            } else {
                System.out.println("A PessoaFisica com o ID especificado não existe no banco de dados.");
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
    
    // Opção 4 do Menu
    public PessoaFisica getPessoaFisicaById(int id) {
    PessoaFisica pessoaFisica = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
        connection = conectorBD.getConnection();
        

        String sql = "SELECT * FROM Pessoa RIGHT JOIN PessoaFisica ON Pessoa.idPessoa = PessoaFisica.idPessoa WHERE Pessoa.idPessoa = ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        resultSet = statement.executeQuery();

        if (resultSet.next()) {

            pessoaFisica = new PessoaFisica();
            pessoaFisica.setId(resultSet.getInt("idPessoa"));
            pessoaFisica.setNome(resultSet.getString("nome"));
            pessoaFisica.setLogradouro(resultSet.getString("logradouro"));
            pessoaFisica.setCidade(resultSet.getString("cidade"));
            pessoaFisica.setEstado(resultSet.getString("estado"));
            pessoaFisica.setTelefone(resultSet.getString("telefone"));
            pessoaFisica.setEmail(resultSet.getString("email"));
            pessoaFisica.setCpf(resultSet.getString("CPF"));
            
        }
    } catch (SQLException e) {
        System.out.println(e);
    } finally {
        conectorBD.close(resultSet);
        conectorBD.close(statement);
        conectorBD.close(connection);
    }

    return pessoaFisica;
}
    

    public void excluirPessoaFisica(int id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false); 


            String sqlVerificarExistencia = "SELECT idPessoa FROM PessoaFisica WHERE idPessoa = ?";
            statement = connection.prepareStatement(sqlVerificarExistencia);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                String sql = "DELETE FROM PessoaFisica WHERE idPessoa = ?";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, id);
                statement.executeUpdate();

                connection.commit(); // Confirma a transação
                System.out.println("Pessoa física excluída com sucesso.");
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