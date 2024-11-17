
package BackEnd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author erikb
 */
public class Conect {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pundo_de_venta";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Ortiz123#";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static Connection cx;

    
    public Conect() {
        conectar();
    }
    
    public void conectar(){
         try{
             Class.forName(driver);
             cx = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         }catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Error de sistema. "+ex, "Atención", JOptionPane.OK_OPTION);
         }  
    }
    
    public void desconectar(){
        try{
            cx.close();
        }catch(SQLException | NullPointerException ex){
            JOptionPane.showMessageDialog(null, "Error de sistema. "+ex, "Atención", JOptionPane.OK_OPTION);
        }
    }

    public Connection getCx() {
        return cx;
    }
    
    public boolean isConected() throws SQLException{
        return cx.isClosed();
    }
    
}
