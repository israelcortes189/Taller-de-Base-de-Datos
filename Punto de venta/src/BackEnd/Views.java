
package BackEnd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author erikb
 */
public class Views {
    
    /**
     * Carga y muestra la vista indicada en la barra de menú de la ventana SalesLog.
     * De acuerdo a la opción seleccionada se carga la vista desde la base de datos.
     * @param dtm El modelo de la tabla donde se van a cargar los datos de la vista seleccionada.
     * @param option Un entero que representa qué vista se va a mostrar.
     */
    public static void getView(DefaultTableModel dtm, int option, String month, String year){
        Conect con = null;
        PreparedStatement statement = null;
        ResultSet resultado = null;
        String sql = null;

        Object[] row = null;
        String[] data = null;

        switch(option){
            case 1: 
               sql = "SELECT folio, mes, clave_empleado, nombre_empleado, total_venta, detalles "
                        + "FROM ventas_mensuales WHERE mes = CONCAT(?, '-', ?)"; 
               data = getColumns(option);
               row = new Object[6]; 
            break; 
            case 2: 
               sql = "SELECT empleado, total, cantidad_de_ventas FROM ventas_por_empleado WHERE mes = CONCAT(?,'-',?)"; 
               data = getColumns(option);
               row = new Object[3];
            break;
            case 3: 
               sql = "SELECT producto, trimestre_1, trimestre_2, trimestre_3, trimestre_4 FROM ventas_por_trimestre WHERE anio = ?"; 
               
               //sql = "SELECT * FROM ventas_por_trimestre WHERE YEAR(fecha) = ?";
               data = getColumns(option);
               row = new Object[5];
            break;                
        }
        
        try {
           con = new Conect();
           statement = con.getCx().prepareStatement(sql);
           statement.setString(1, year);
           if(option <= 2)statement.setString(2, month);
           resultado = statement.executeQuery();
           dtm.setRowCount(0);
          
           while(resultado.next()) {
               
                for(int i = 0; i < data.length; i++) {
                   row[i] = resultado.getString(data[i]);  
                }  
               
             dtm.addRow(row);
           }
           
           if(dtm.getRowCount() == 0){
              JOptionPane.showMessageDialog(null, "No existen registros de la fecha indicada.", "Aviso", JOptionPane.WARNING_MESSAGE);
           }
           
        }catch(SQLException | NullPointerException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar la vista: " + ex.getMessage(), "Aviso", JOptionPane.ERROR_MESSAGE);
        }finally {
           try {
              if (resultado != null) resultado.close();
              if (statement != null) statement.close();
              if (con != null) con.desconectar();
           }catch(SQLException ex) {
              
           }
        }
    } 
    
    private static String[] getColumns(int option){
        switch(option){
            case 1: 
                String[] str = {"folio","mes","clave_empleado","nombre_empleado","total_venta","detalles"};
                return str;
            case 2: 
                String[] str2 = {"empleado","total","cantidad_de_ventas"};
                return str2;
            case 3: 
                String[] str3 = {"producto","trimestre_1","trimestre_2","trimestre_3","trimestre_4"};
                return str3;  
            default: return null;    
        }
    }
    
    
}
