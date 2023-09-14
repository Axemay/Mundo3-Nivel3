package cadastro.model.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maiara
 */
//public class SequenceManager {
//        private final ConectorBD conectorBD; 
//
//    public SequenceManager() {
//        this.conectorBD = new ConectorBD(); 
//    }
//
//    public int getValue(String sequenceName) throws java.sql.SQLException {
//        int proximoValor = 0; 
//        String consultaSql = "SELECT nextval('" + sequenceName + "')"; 
//        ResultSet rs = null;
//        Statement statement = null; 
//
//        statement = conectorBD.getStatement();
//        rs = statement.executeQuery(consultaSql); 
//        if (rs.next()) { 
//            proximoValor = rs.getInt(1); 
//        }
//        conectorBD.close(rs); 
//        conectorBD.close(statement); 
//
//        return proximoValor; 
//    }
//}

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