package cadastro.model.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maiara
 */
public class SequenceManager {
    private final ConectorBD conectorBD;

    public SequenceManager() {
        this.conectorBD = new ConectorBD();
    }

    public int getNextValue(String sequenceName) throws SQLException {
        int proximoValor = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = conectorBD.getConnection();
            String sql = "SELECT NEXT VALUE FOR " + sequenceName + " AS NextVal";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                proximoValor = resultSet.getInt("NextVal");
            }
        } finally {
            conectorBD.close(resultSet);
            conectorBD.close(preparedStatement);
            conectorBD.close(connection);
        }

        return proximoValor;
    }
}