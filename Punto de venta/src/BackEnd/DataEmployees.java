
package BackEnd;

import java.awt.TextField;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author erikb
 */
public class DataEmployees {
    
    
    public static void addEmployee(String name, String lastName, String lastName2, String key, int register, String password) {
        Conect con = null;
        PreparedStatement statement = null;
        String sql = "INSERT INTO administradores_sistema (nombre, apellido_paterno, apellido_materno, clave, id_caja, `password`) VALUES (?, ?, ?, ?, ?, ?)";
                           
        try {
            con = new Conect();
            statement = con.getCx().prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setString(3, lastName2);
            statement.setString(4, key);
            statement.setInt(5, register);
            statement.setString(6, Password.encrypt(password));
            statement.executeUpdate();
            
        }catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar el regsitro. \n"+ex, "Atención", JOptionPane.ERROR_MESSAGE);
        }catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar el regsitro. \n"+ex, "Atención", JOptionPane.ERROR_MESSAGE);
        }finally {
            try {
                if (statement != null) statement.close();
                if (con != null) con.desconectar();
            } catch (SQLException e) {
                Logger.getLogger(DataEmployees.class.getName()).log(Level.SEVERE, null, e);
            }
        }
            
    }
    
    public static Employee getEmployee(String id) {
        String sql = "SELECT * FROM administradores_sistema WHERE clave = ?";
        PreparedStatement statement = null;  
        Employee e = null;
        
        try { 
           Conect con = new Conect(); 
           statement = con.getCx().prepareStatement(sql);
           statement.setString(1, id);
           ResultSet resultado = statement.executeQuery();
           e = new Employee();
           
            if(resultado.next()) {
                e.setName(resultado.getString("nombre"));
                e.setLastName(resultado.getString("apellido_paterno"));
                e.setLastName2(resultado.getString("apellido_materno"));
                e.setRegister(resultado.getInt("id_caja"));   
                e.setKey(resultado.getString("clave")); 
                e.setPassword(resultado.getString("password")); 
            }
            
            resultado.close();
            statement.close();
            con.desconectar();
            
        }catch(SQLException se){
            se.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al mostrar los regsitros. \n"+se, "Atención", JOptionPane.WARNING_MESSAGE);
        } 
        
      return e;     
    } 
    
    public static void getEmployees(DefaultTableModel dtm) {
        String sql = "SELECT * FROM administradores_sistema";
        PreparedStatement statement;   
        
        try { 
           Conect con = new Conect(); 
           Employee e = new Employee();
           statement = con.getCx().prepareStatement(sql);
           ResultSet resultado = statement.executeQuery(sql);
            
            while(resultado.next()) {
                e.setName(resultado.getString("nombre"));
                e.setLastName(resultado.getString("apellido_paterno"));
                e.setLastName2(resultado.getString("apellido_materno"));
                e.setRegister(resultado.getInt("id_caja"));   
                e.setKey(resultado.getString("clave")); 
                e.setPassword(resultado.getString("password")); 
                dtm.addRow(validate(e.getValues()));
            }
          
        }catch(SQLException se ){
            JOptionPane.showMessageDialog(null, "Error al mostrar los regsitros."+se, "Atención", JOptionPane.WARNING_MESSAGE);
        } 
           
    } 
    
    public static void deleteEmployee(String key) {
       String sql = "DELETE FROM administradores_sistema WHERE clave = ?";
       PreparedStatement statement = null;
       Conect con = null;
    
        try {
           con = new Conect();               
           statement = con.getCx().prepareStatement(sql);
           statement.setString(1, key);
           statement.executeUpdate();
        }catch(SQLException ex) {
             ex.printStackTrace(); 
        }finally {
            try {
               if (statement != null) statement.close();
               if (con != null) con.desconectar();
            }catch(SQLException e) {
              e.printStackTrace();
            }
        }
    }

    
    public static void updateEmployee(String column, String newValue, String key) {
    // Lista de columnas permitidas para actualización
    List<String> allowedColumns = Arrays.asList("nombre", "apellido_paterno", "apellido_materno", "`password`", "id_caja");
    
    // Verifica si la columna está permitida
    if (!allowedColumns.contains(column)) {
        JOptionPane.showMessageDialog(null, "Columna no válida.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    Conect con = new Conect();
    PreparedStatement statement = null;

    try {
        String update = "UPDATE administradores_sistema SET " + column + " = ? WHERE clave = ?";
        statement = con.getCx().prepareStatement(update);

        statement.setString(1, newValue); // Nuevo valor para la columna especificada
        statement.setString(2, key);      // Clave del registro a actualizar

        // Ejecutar la actualización
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected < 0) {
             JOptionPane.showMessageDialog(null, "No se encontró un registro con la clave especificada.", "Atención", JOptionPane.WARNING_MESSAGE);
        } else {
           
        }
JOptionPane.showMessageDialog(null, "Actualización realizada.", "Atención", JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (statement != null) statement.close();
            if (con != null) con.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

    
    /**
     * Acomoda los datos del empleado de acuerdo a las columnas de la tabla en la ventana de empleados.
     * @param values
     * @return 
     */
    private static Object[] validate(Object[] values){
        String name = (String) values[1] +" "+(String) values[2];
        Object[] validation =  {values[3], values[0], name, values[4]};        
      return validation;
    }
    
    public static void clearTable(DefaultTableModel dtm){
        int rowCount = dtm.getRowCount();
        for(int i = rowCount - 1; i >= 0; i--) {
            dtm.removeRow(i);
        }
    }
    
    
    
}
