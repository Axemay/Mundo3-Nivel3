package cadastro.model;

import cadastro.model.util.ConectorBD;
import cadastro.model.util.SequenceManager;
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
            System.out.println("Erro. Não foi possível concluir a solicitação " + e);
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
                pessoaFisica.setEmail(resultSet.getString("email"));
                pessoaFisica.setTelefone(resultSet.getString("telefone"));
                pessoaFisica.setCpf(resultSet.getString("CPF"));

                pessoasFisicas.add(pessoaFisica);
            }
        } catch (SQLException e) {
            System.out.println("Erro. Não foi possível concluir a solicitação: " + e);
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

        try {
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false);

            SequenceManager sequenceManager = new SequenceManager();
            int idPessoa = sequenceManager.getNextValue("PessoaSequence");

            String sqlPessoa = "INSERT INTO Pessoa (idPessoa, nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sqlPessoa);

            preparedStatement.setInt(1, idPessoa);
            preparedStatement.setString(2, pessoaFisica.getNome());
            preparedStatement.setString(3, pessoaFisica.getLogradouro());
            preparedStatement.setString(4, pessoaFisica.getCidade());
            preparedStatement.setString(5, pessoaFisica.getEstado());
            preparedStatement.setString(7, pessoaFisica.getEmail());
            preparedStatement.setString(6, pessoaFisica.getTelefone());

            preparedStatement.execute();

            String sql = "INSERT INTO PessoaFisica (idPessoa, CPF) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idPessoa);
            preparedStatement.setString(2, pessoaFisica.getCpf());
            preparedStatement.execute();

            try {
                connection.commit();
            } catch (SQLException e) {
                System.out.println("Erro no commit");
            }

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    System.out.println("Erro ao fazer rollback: " + ex);
                }
            }
            System.out.println("Erro. Não foi possível concluir a solicitação: " + e);
        } finally {
            conectorBD.close(preparedStatement);
            conectorBD.close(connection);
        }
    }

    public void alterarPessoaFisica(PessoaFisica pessoaFisicaAntiga, PessoaFisica pessoaFisicaNova) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false);

            String sqlConsulta = "SELECT idPessoa FROM Pessoa WHERE idPessoa = ?";
            preparedStatement = connection.prepareStatement(sqlConsulta);
            preparedStatement.setInt(1, pessoaFisicaAntiga.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                String sqlPessoa = "UPDATE Pessoa SET nome = ?, logradouro = ?, cidade = ?, estado = ?, email = ?, telefone = ? WHERE idPessoa = ?";
                preparedStatement = connection.prepareStatement(sqlPessoa);
                preparedStatement.setString(1, pessoaFisicaNova.getNome());
                preparedStatement.setString(2, pessoaFisicaNova.getLogradouro());
                preparedStatement.setString(3, pessoaFisicaNova.getCidade());
                preparedStatement.setString(4, pessoaFisicaNova.getEstado());
                preparedStatement.setString(5, pessoaFisicaNova.getEmail());
                preparedStatement.setString(6, pessoaFisicaNova.getTelefone());
                preparedStatement.setInt(7, pessoaFisicaAntiga.getId()); // 

                preparedStatement.executeUpdate();

                String sqlConsultaPF = "SELECT idPessoa FROM PessoaFisica WHERE idPessoa = ?";
                preparedStatement = connection.prepareStatement(sqlConsultaPF);
                preparedStatement.setInt(1, pessoaFisicaAntiga.getId());
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String sqlPessoaFisica = "UPDATE PessoaFisica SET CPF = ? WHERE idPessoa = ?";
                    preparedStatement = connection.prepareStatement(sqlPessoaFisica);
                    preparedStatement.setString(1, pessoaFisicaNova.getCpf());
                    preparedStatement.setInt(2, pessoaFisicaAntiga.getId());

                    preparedStatement.executeUpdate();

                    try {
                        connection.commit();
                    } catch (SQLException e) {
                        System.out.println("Erro no commit");
                    }
                } else {
                    System.out.println("ID não encontrado para Pessoa Física na base de dados.");
                }
            } else {
                System.out.println("ID não encontrado para Pessoa na base de dados.");
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    System.out.println("Erro. Não foi possível concluir a solicitação: " + e);
                    System.out.println("Código de Erro SQL: " + e.getErrorCode());
                }
            }
        } finally {
            conectorBD.close(preparedStatement);
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
                pessoaFisica.setEmail(resultSet.getString("email"));
                pessoaFisica.setTelefone(resultSet.getString("telefone"));
                pessoaFisica.setCpf(resultSet.getString("CPF"));

            }
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Código de Erro SQL: " + e.getErrorCode());
        } finally {
            conectorBD.close(resultSet);
            conectorBD.close(statement);
            conectorBD.close(connection);
        }

        return pessoaFisica;
    }

    
    public void excluirPessoaFisica(int id) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
        connection = conectorBD.getConnection();
        connection.setAutoCommit(false);

        String sqlConsultaPF = "SELECT idPessoa FROM PessoaFisica WHERE idPessoa = ?";
        preparedStatement = connection.prepareStatement(sqlConsultaPF);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String sqlDeletaPF = "DELETE FROM PessoaFisica WHERE idPessoa = ?";
            preparedStatement = connection.prepareStatement(sqlDeletaPF);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            String sqlConsultaPessoa = "SELECT idPessoa FROM Pessoa WHERE idPessoa = ?";
            preparedStatement = connection.prepareStatement(sqlConsultaPessoa);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String sqlPessoa = "DELETE FROM Pessoa WHERE idPessoa = ?";
                preparedStatement = connection.prepareStatement(sqlPessoa);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();

                try {
                    connection.commit();
                    System.out.println("Commit realizado");
                } catch (SQLException e) {
                    System.out.println("Erro no commit");
                }

            } else {
                System.out.println("ID não encontrado para Pessoa na base de dados.");
            }
        } else {
            System.out.println("ID não encontrado para Pessoa Física na base de dados.");
        }
    } catch (SQLException e) {
        System.out.println("Erro. Não foi possível concluir a solicitação: " + e);
        System.out.println("Código de Erro SQL: " + e.getErrorCode());

        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Erro ao fazer rollback: " + ex);
            }
        }
    } finally {
        conectorBD.close(preparedStatement);
        conectorBD.close(connection);
    }
}  
}
