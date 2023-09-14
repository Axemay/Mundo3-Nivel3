package cadastro.model;

import cadastro.model.util.ConectorBD;
import cadastro.model.util.SequenceManager;
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
            pessoaJuridica.setEmail(resultSet.getString("email"));
            pessoaJuridica.setTelefone(resultSet.getString("telefone"));
            pessoaJuridica.setCnpj(resultSet.getString("CNPJ"));
            
            pessoasJuridicas.add(pessoaJuridica);
        }
    } catch (SQLException e) {
         System.out.println("Erro. Não foi possível concluir a solicitação "+e);
    } finally {
        conectorBD.close(resultSet);
        conectorBD.close(statement);
        conectorBD.close(connection);
    }

    return pessoasJuridicas;
}
    
   public void incluirPessoaJuridica(PessoaJuridica pessoaJuridica) {
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
        preparedStatement.setString(2, pessoaJuridica.getNome());
        preparedStatement.setString(3, pessoaJuridica.getLogradouro());
        preparedStatement.setString(4, pessoaJuridica.getCidade());
        preparedStatement.setString(5, pessoaJuridica.getEstado());
        preparedStatement.setString(7, pessoaJuridica.getEmail());
        preparedStatement.setString(6, pessoaJuridica.getTelefone());


        preparedStatement.execute();

        String sql = "INSERT INTO PessoaFisica (idPessoa, CNPJ) VALUES (?, ?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, idPessoa);
        preparedStatement.setString(2, pessoaJuridica.getCnpj());
        preparedStatement.execute();
        try{
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
    
   public void alterarPessoaJuridica(PessoaJuridica pessoaJuridicaAntiga, PessoaJuridica pessoaJuridicaNova) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
        connection = conectorBD.getConnection();
        connection.setAutoCommit(false);


        String sqlConsulta = "SELECT idPessoa FROM Pessoa WHERE idPessoa = ?";
        preparedStatement = connection.prepareStatement(sqlConsulta);
        preparedStatement.setInt(1, pessoaJuridicaAntiga.getId());
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {

            String sqlPessoa = "UPDATE Pessoa SET nome = ?, logradouro = ?, cidade = ?, estado = ?, email = ?, telefone = ? WHERE idPessoa = ?";
            preparedStatement = connection.prepareStatement(sqlPessoa);
            preparedStatement.setString(1, pessoaJuridicaNova.getNome());
            preparedStatement.setString(2, pessoaJuridicaNova.getLogradouro());
            preparedStatement.setString(3, pessoaJuridicaNova.getCidade());
            preparedStatement.setString(4, pessoaJuridicaNova.getEstado());
            preparedStatement.setString(5, pessoaJuridicaNova.getEmail());
            preparedStatement.setString(6, pessoaJuridicaNova.getTelefone());
            preparedStatement.setInt(7, pessoaJuridicaNova.getId()); 

            preparedStatement.executeUpdate();

            String sqlConsultaPJ = "SELECT idPessoa FROM PessoaJuridica WHERE idPessoa = ?";
            preparedStatement = connection.prepareStatement(sqlConsultaPJ);
            preparedStatement.setInt(1, pessoaJuridicaAntiga.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String sqlPessoaFisica = "UPDATE PessoaJuridica SET CNPJ = ? WHERE idPessoa = ?";
                preparedStatement = connection.prepareStatement(sqlPessoaFisica);
                preparedStatement.setString(1, pessoaJuridicaNova.getCnpj());
                preparedStatement.setInt(2, pessoaJuridicaAntiga.getId()); 

                preparedStatement.executeUpdate();

                try {
                    connection.commit();
                } catch (SQLException e) {
                    System.out.println("Erro no commit");
                }
            } else {
                System.out.println("ID não encontrado para Pessoa Jurídica na base de dados.");
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
            }
        }
    } finally {
        conectorBD.close(preparedStatement);
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
            pessoaJuridica.setEmail(resultSet.getString("email"));
            pessoaJuridica.setTelefone(resultSet.getString("telefone"));
            pessoaJuridica.setCnpj(resultSet.getString("CNPJ"));
            
            
        }
    } catch (SQLException e) {
        System.out.println("Erro. Não foi possível concluir a solicitação "+e);
    } finally {
        conectorBD.close(resultSet);
        conectorBD.close(statement);
        conectorBD.close(connection);
    }

    return pessoaJuridica;
}

        
    public void excluirPessoaJuridica(int id) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
        connection = conectorBD.getConnection();
        connection.setAutoCommit(false);

        String sqlConsultaPJ = "SELECT idPessoa FROM PessoaJuridica WHERE idPessoa = ?";
        preparedStatement = connection.prepareStatement(sqlConsultaPJ);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String sqlDeletaPJ = "DELETE FROM PessoaJuridica WHERE idPessoa = ?";
            preparedStatement = connection.prepareStatement(sqlDeletaPJ);
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
            System.out.println("ID não encontrado para Pessoa Jurídica na base de dados.");
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