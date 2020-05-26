import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

   static {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
      } 
      catch (ClassNotFoundException e) {
         throw new RuntimeException(e);
      }
   }

   public Connection conectar() throws SQLException {
      String servidor = "localhost";
      String porta = "3306";
      String database = "clima";
      String usuario = "root";
      String senha = "Theus2020";
      return DriverManager.getConnection("jdbc:mysql://"+servidor+":"+porta+"/"+database+"?useTimezone=true&serverTimezone=UTC", usuario, senha);
   }
}