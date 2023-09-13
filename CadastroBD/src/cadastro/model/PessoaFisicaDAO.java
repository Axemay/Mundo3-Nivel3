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
        
//        PessoaFisica pessoaFisica = null;
//        Connection connection = null;
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = conectorBD.getConnection();
//            
//            // Consultar a tabela PessoaFisica
//            String sql = "SELECT * FROM PessoaFisica WHERE idPessoa = ?";
//            statement = connection.prepareStatement(sql);
//            statement.setInt(1, id);
//            resultSet = statement.executeQuery();
//
//            if (resultSet.next()) {
//                pessoaFisica = new PessoaFisica();
//                pessoaFisica.setId(resultSet.getInt("idPessoa"));
//                pessoaFisica.setNome(resultSet.getString("nome"));
//                pessoaFisica.setCpf(resultSet.getString("CPF"));
//                // Definir outros atributos específicos da tabela PessoaFisica, se houver
//            }
//        } catch (SQLException e) {
//            // Tratar exceção, se necessário
//        } finally {
//            conectorBD.close(resultSet);
//            conectorBD.close(statement);
//            conectorBD.close(connection);
//        }
//
//        return pessoaFisica;
//    }
    
    // Método para listar todas as pessoas físicas
     public List<PessoaFisica> listarTodasPessoasFisicas() {
        List<PessoaFisica> pessoasFisicas = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
             connection = conectorBD.getConnection();
            
            // Consultar a tabela Pessoa
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
        // Tratar exceção, se necessário
    } finally {
        conectorBD.close(resultSet);
        conectorBD.close(statement);
        conectorBD.close(connection);
    }

    return pessoasFisicas;
}
    
    // Método para incluir uma PessoaFisica no banco de dados
    public void incluirPessoaFisica(PessoaFisica pessoaFisica) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false); // Inicia uma transação

                    // Inserir na tabela Pessoa
        String sqlPessoa = "INSERT INTO Pessoa (nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?)";
        statement = connection.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, pessoaFisica.getNome());
        statement.setString(2, pessoaFisica.getLogradouro());
        statement.setString(3, pessoaFisica.getCidade());
        statement.setString(4, pessoaFisica.getEstado());
        statement.setString(5, pessoaFisica.getTelefone());
        statement.setString(6, pessoaFisica.getEmail());
        statement.executeUpdate();

        // Obter o ID gerado para a Pessoa
        ResultSet generatedKeys = statement.getGeneratedKeys();
        int idPessoa;
        if (generatedKeys.next()) {
            idPessoa = generatedKeys.getInt(1);
        }
            // Inserir na tabela PessoaFisica
            String sql = "INSERT INTO PessoaFisica (nome, CPF) VALUES (?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, pessoaFisica.getNome());
            statement.setString(2, pessoaFisica.getCpf());
            statement.executeUpdate();

            connection.commit(); // Confirma a transação
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Desfaz a transação em caso de erro
                } catch (SQLException ex) {
                }
            }
        } finally {
            conectorBD.close(statement);
            conectorBD.close(connection);
        }
    }
    
    // Método para alterar uma PessoaFisica no banco de dados
    public void alterarPessoaFisica(PessoaFisica pessoaFisica) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false); // Inicia uma transação

            // Verifica se a PessoaFisica existe no banco de dados
            String sqlVerificarExistencia = "SELECT idPessoa FROM PessoaFisica WHERE idPessoa = ?";
            statement = connection.prepareStatement(sqlVerificarExistencia);
            statement.setInt(1, pessoaFisica.getId());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Atualizar tabela PessoaFisica
                String sql = "UPDATE PessoaFisica SET nome = ?, CPF = ? WHERE idPessoa = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, pessoaFisica.getNome());
                statement.setString(2, pessoaFisica.getCpf());
                statement.setInt(3, pessoaFisica.getId());
                statement.executeUpdate();

                connection.commit(); // Confirma a transação
            } else {
                System.out.println("A PessoaFisica com o ID especificado não existe no banco de dados.");
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Desfaz a transação em caso de erro
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
        
        // Consultar a tabela PessoaFisica
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
    
    // Método para excluir uma PessoaFisica do banco de dados
    public void excluirPessoaFisica(int id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false); // Inicia uma transação

            // Verificar se a PessoaFisica existe no banco de dados
            String sqlVerificarExistencia = "SELECT idPessoa FROM PessoaFisica WHERE idPessoa = ?";
            statement = connection.prepareStatement(sqlVerificarExistencia);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Excluir da tabela PessoaFisica
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
                    connection.rollback(); // Desfaz a transação em caso de erro
                } catch (SQLException ex) {
                }
            }
        } finally {
            conectorBD.close(statement);
            conectorBD.close(connection);
       
        }
    }
}