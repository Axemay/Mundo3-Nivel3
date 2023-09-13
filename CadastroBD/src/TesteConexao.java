
import cadastro.model.util.ConectorBD;
import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {
    public static void main(String[] args) {
        ConectorBD conector = new ConectorBD();
        try {
            Connection connection = conector.getConnection();
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            connection.close();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}