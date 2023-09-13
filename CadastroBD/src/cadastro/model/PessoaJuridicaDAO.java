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

    // Método para obter uma PessoaJuridica pelo ID
    public PessoaJuridica getPessoaJuridica(int id) {
        PessoaJuridica pessoaJuridica = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = conectorBD.getConnection();
            
            // Consultar a tabela PessoaJuridica
            String sql = "SELECT * FROM PessoaJuridica WHERE idPessoa = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                pessoaJuridica = new PessoaJuridica();
                pessoaJuridica.setId(resultSet.getInt("idPessoa"));
                pessoaJuridica.setNome(resultSet.getString("nome"));
                pessoaJuridica.setCnpj(resultSet.getString("CNPJ"));
                // Definir outros atributos específicos da tabela PessoaJuridica, se houver
            }
        } catch (SQLException e) {
            // Tratar exceção, se necessário
        } finally {
            conectorBD.close(resultSet);
            conectorBD.close(statement);
            conectorBD.close(connection);
        }

        return pessoaJuridica;
    }
    
    // Método para listar todas as pessoas jurídicas
    public List<PessoaJuridica> listarTodasPessoasJuridicas() {
        List<PessoaJuridica> pessoasJuridicas = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = conectorBD.getConnection();
            
            // Consultar a tabela PessoaJuridica
            String sql = "SELECT * FROM PessoaJuridica";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                PessoaJuridica pessoaJuridica = new PessoaJuridica();
                pessoaJuridica.setId(resultSet.getInt("idPessoa"));
                pessoaJuridica.setNome(resultSet.getString("nome"));
                pessoaJuridica.setCnpj(resultSet.getString("CNPJ"));
                // Definir outros atributos específicos da tabela PessoaJuridica, se houver
                pessoasJuridicas.add(pessoaJuridica);
            }
        } catch (SQLException e) {
            // Tratar exceção, se necessário
        } finally {
            conectorBD.close(resultSet);
            conectorBD.close(statement);
            conectorBD.close(connection);
        }

        return pessoasJuridicas;
    }
    
    // Método para incluir uma PessoaJuridica no banco de dados
    public void incluirPessoaJuridica(PessoaJuridica pessoaJuridica) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false); // Inicia uma transação

            // Inserir na tabela PessoaJuridica
            String sql = "INSERT INTO PessoaJuridica (nome, CNPJ) VALUES (?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, pessoaJuridica.getNome());
            statement.setString(2, pessoaJuridica.getCnpj());
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
    
    // Método para alterar uma PessoaJuridica no banco de dados
    public void alterarPessoaJuridica(PessoaJuridica pessoaJuridica) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false); // Inicia uma transação

            // Verifica se a PessoaJuridica existe no banco de dados
            String sqlVerificarExistencia = "SELECT idPessoa FROM PessoaJuridica WHERE idPessoa = ?";
            statement = connection.prepareStatement(sqlVerificarExistencia);
            statement.setInt(1, pessoaJuridica.getId());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Atualizar tabela PessoaJuridica
                String sql = "UPDATE PessoaJuridica SET nome = ?, CNPJ = ? WHERE idPessoa = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, pessoaJuridica.getNome());
                statement.setString(2, pessoaJuridica.getCnpj());
                statement.setInt(3, pessoaJuridica.getId());
                statement.executeUpdate();

                connection.commit(); // Confirma a transação
            } else {
                System.out.println("A PessoaJuridica com o ID especificado não existe no banco de dados.");
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
    
    // Método para obter uma PessoaJuridica pelo ID
public PessoaJuridica getPessoaJuridicaById(int id) {
    PessoaJuridica pessoaJuridica = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
        connection = conectorBD.getConnection();
        
        // Consultar a tabela PessoaJuridica
        String sql = "SELECT * FROM PessoaJuridica WHERE idPessoa = ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        resultSet = statement.executeQuery();

        if (resultSet.next()) {
            pessoaJuridica = new PessoaJuridica();
            pessoaJuridica.setId(resultSet.getInt("idPessoa"));
            pessoaJuridica.setNome(resultSet.getString("nome"));
            pessoaJuridica.setCnpj(resultSet.getString("CNPJ"));
            // Definir outros atributos específicos da tabela PessoaJuridica, se houver
        }
    } catch (SQLException e) {
        // Tratar exceção, se necessário
    } finally {
        conectorBD.close(resultSet);
        conectorBD.close(statement);
        conectorBD.close(connection);
    }

    return pessoaJuridica;
}

    
    // Método para excluir uma PessoaJuridica do banco de dados
    public void excluirPessoaJuridica(int id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false); // Inicia uma transação

            // Verificar se a PessoaJuridica existe no banco de dados
            String sqlVerificarExistencia = "SELECT idPessoa FROM PessoaJuridica WHERE idPessoa = ?";
            statement = connection.prepareStatement(sqlVerificarExistencia);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Excluir da tabela PessoaJuridica
                String sql = "DELETE FROM PessoaJuridica WHERE idPessoa = ?";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, id);
                statement.executeUpdate();

                connection.commit(); // Confirma a transação
                System.out.println("Pessoa jurídica excluída com sucesso.");
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