
package BackEnd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author erikb
 */
public class DataSales {
    
   
    /**
     * Obtiene un objeto Sale que contiene los datos de un registro de venta. 
     */
    public static List<Sale> getSaleRegister(Object parameter, int option) throws SQLException, NullPointerException {
        Conect con = new Conect();
        String sql ;
        PreparedStatement statement = null; 
        List<Sale> log = new ArrayList<>();
            
            try {   
                
              switch(option){
                case 1: 
                   sql = "SELECT * FROM detalle_ventas WHERE MONTHNAME(fecha) = ?";
                   statement = con.getCx().prepareStatement(sql);
                   statement.setString(1, Control.traslate((int) parameter));    
                break;    
                case 2: 
                   sql = "SELECT * FROM detalle_ventas WHERE clave = ?";
                   statement = con.getCx().prepareStatement(sql);
                   statement.setString(1, String.valueOf(parameter)); 
                   // case 3: sql = "SELECT * FROM detalle_ventas WHERE MONTHNAME(fecha) = ?";   
                break;    
                default: statement = null;  
              }  
                
              ResultSet resultado = statement.executeQuery();
        
               while(resultado.next()) {
                   Sale s = new Sale();
                   s.setKey(resultado.getString("clave"));
                   s.setId(resultado.getInt("id_producto"));
                   s.setMonto(resultado.getDouble("monto")); 
                   s.setQuantity(resultado.getInt("cantidad"));
                   String[] str = resultado.getString("fecha").split(" ");                   
                   s.setDate(str[0]); 
                   s.setHour(str[1]);
                   log.add(s); 
               }
              
               if(log.isEmpty()){
                   JOptionPane.showMessageDialog(null, "No hay registros en la opción seleccionada", "Aviso", JOptionPane.WARNING_MESSAGE);
                   return null;
               }else {
                   return log;  
               }
               
            }catch(SQLException | NullPointerException se) {
               throw se;    
            }finally {
                if(statement != null) {
                  statement.close();
                }
                if(con != null) {
                  con.desconectar();  
                }
            }     
    }
    
    /**
     * Crea la tabla de registro de ventas de acuerdo al mes.
     * @param index El índice del mes de la caja de meses.
     * @return El mes traducido a inglés.
     * @throws SQLException 
     */
    public static void loadProductDataOnMainTable(DefaultTableModel dtm, Object parameter, int option) {
        List<Sale> log;   
        try {
            log = getSaleRegister(parameter, option);
               for(Sale s : log) {              
                  dtm.addRow(s.getValues()); 
               }
        }catch(SQLException | NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos: "+ex.getMessage(), "Aviso", JOptionPane.OK_OPTION);  
        }          
    }
    
    public static void clearTable(DefaultTableModel dtm){
        int rowCount = dtm.getRowCount();
        for(int i = rowCount - 1; i >= 0; i--) {
            dtm.removeRow(i);
        }
    }
    
    
    
}
