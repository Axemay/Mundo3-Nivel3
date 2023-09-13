
package cadastro.model.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/* 
    Autor: Maiara
*/



public class ConectorBD {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=loja;encrypt=true;trustServerCertificate=true";
    private static final String USUARIO = "loja";
    private static final String SENHA = "loja";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        Connection connection = getConnection();
        return connection.prepareStatement(sql);
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            return resultSet;
        } finally {
            close(statement);
        }
    }

    public void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
           System.out.println(e);
        }
    }

    public void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    Statement getStatement() {
        throw new UnsupportedOperationException("Sem suporte!"); 
    }
}
