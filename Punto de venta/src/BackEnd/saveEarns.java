
package BackEnd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author erikb
 */
public class saveEarns {
    
    //Register = caja
    /**
     * Crea un objeto de tipo caja.
     * @param con 
     * @param id El ID de la caja registradora.
     * @return
     * @throws SQLException 
     */
    public static Register createRegister(Conect con, int id) throws SQLException{
        String select = "SELECT * FROM cajas WHERE id_caja = ?";
        PreparedStatement statement = null;
        ResultSet resultado = null;

           try {
              statement = con.getCx().prepareStatement(select);
              statement.setInt(1, id);
              Register r = new Register();
              resultado = statement.executeQuery();

                if(resultado.next()) {
                   r.setId(resultado.getInt("id_caja"));
                   r.setName(resultado.getString("nombre_establecimiento"));
                   r.setCash(resultado.getDouble("dinero"));
                }

                if(r.getId() == 0) {
                   return null;
                }

              return r;
           }catch(SQLException se) {
               throw se;
           }finally {
             if (resultado != null) resultado.close();
             if (statement != null) statement.close();
           }
    }
    
    /**
     * Guarda la ganacia de la caja 
     * @param con La conexión a la base de datos.
     * @param id El ID de la caja registradora. Este ID esta presente en la información del empleado.
     * @param cash El monto total ganado.
     */
    public static void saveCash(Conect con, int id, double cash) throws SQLException{
        String update = "UPDATE cajas SET dinero = ? WHERE id_caja = ?";
        PreparedStatement toInsert = null;

            try {
               Register r = createRegister(con, id);
                if(r != null) {  
                   double monto = r.getCash() + cash;
                   toInsert = con.getCx().prepareStatement(update);
                   toInsert.setDouble(1, monto); 
                   toInsert.setInt(2, id);       
                   toInsert.executeUpdate();
                }else {
                    throw new SQLException("Registro no encontrado para el id: " + id);
                }
            }catch(SQLException se) {
              throw se;
            }finally {           
               if(toInsert != null) {
                 toInsert.close(); 
               }
            }
    }  
    
 
    public static String getCash(int id) {
        Conect con = new Conect();
        String select = "SELECT dinero FROM cajas WHERE id_caja = ?";
        PreparedStatement statement = null;
        ResultSet resultado = null;
        double cash = 0.0; 
         
           try {            
              statement = con.getCx().prepareStatement(select);
              statement.setInt(1, id);           
              resultado = statement.executeQuery();

                if(resultado.next()) {
                   cash = resultado.getDouble("dinero");
                }

              return String.valueOf(cash);
           }catch(SQLException | NullPointerException se) {
                JOptionPane.showMessageDialog(null, "No se pudo obtener el dienro en caja.", "Atención", JOptionPane.OK_OPTION);
           }finally {
               try {
                   if(resultado != null) resultado.close();
                   if(statement != null) statement.close();
               }catch(SQLException ex) {
                   JOptionPane.showMessageDialog(null, "Error de sistema. \n"+ex, "Atención", JOptionPane.OK_OPTION);
               }
              
           }
      return null;        
    }
    
 
    
}
