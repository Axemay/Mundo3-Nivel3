package cadastro.model.util;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Maiara
 */
public class SequenceManager {
        private final ConectorBD conectorBD; 

    public SequenceManager() {
        this.conectorBD = new ConectorBD(); 
    }

    public int getValue(String sequenceName) throws java.sql.SQLException {
        int proximoValor = 0; 
        String consultaSql = "SELECT nextval('" + sequenceName + "')"; 
        ResultSet rs = null;
        Statement statement = null; 

        statement = conectorBD.getStatement();
        rs = statement.executeQuery(consultaSql); 
        if (rs.next()) { 
            proximoValor = rs.getInt(1); 
        }
        conectorBD.close(rs); 
        conectorBD.close(statement); 

        return proximoValor; 
    }
}
