
package BackEnd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import tools.Ticket;
import static tools.Ticket.generateTicket;

/**
 *
 * @author erikb
 */
public class TRANSACTION {
    
    private static double total = 0;
    private static double iva = 0;
    private static double monto = 0;
    private static Stack<Product> log = new Stack<>();
    
    
    public static Product searchProduct(int id, Conect con) throws SQLException{
        String sql = "SELECT * FROM productos WHERE id_producto = "+id;
        PreparedStatement statement;   
       // statement.setInt(1, Integer.parseInt(id)); 
        
        try { 
           Product p = new Product();
           statement = con.getCx().prepareStatement(sql);
           ResultSet resultado = statement.executeQuery(sql);
            
            while(resultado.next()) {
                p.setId(resultado.getInt("id_producto"));
                p.setName(resultado.getString("nombre"));
                p.setType(resultado.getString("tipo"));
                p.setPrice(resultado.getDouble("precio"));   
                p.setIva(resultado.getDouble("iva")); 
            }
          
            if(p.getId() == 0){
              return null; 
            }  
            
          return p;  
        }catch(SQLException se){
            throw se;
        } 
           
    } 
      
    /**
     * Este método hace la transacción. -----------------------TRANSACTION----------------------------------------------------
     * @param dtm 
     */
    public static void saveSale(DefaultTableModel dtm) {
      Conect con = new Conect();
      String insert = "INSERT INTO detalle_ventas (clave, id_producto, monto, cantidad) VALUES (?,?,?,?)"; 
      PreparedStatement toInsert = null;

        try {
           con.getCx().setAutoCommit(false);
           toInsert = con.getCx().prepareStatement(insert);
           HashMap<Integer, Integer> sale = new HashMap<>();
           sale.putAll(quantity()); 
        
            if(!sale.isEmpty()){
                for(int key : sale.keySet()){
                   Product p = searchProduct(key, con);
                   toInsert.setString(1, Control.getEmployee().getKey());
                   toInsert.setInt(2, p.getId());
                   toInsert.setDouble(3, (sale.get(key) * p.getPrice()));
                   toInsert.setString(4, String.valueOf(sale.get(key)));
                   toInsert.executeUpdate();  
                   updateExistencias(con, key, sale.get(key));    
                   String str = p.getName()+" "+p.getPrice()+" "+sale.get(key); 
                   Ticket.saveStrings(str);
                   saveEarns.saveCash(con, Control.getEmployee().getRegister(), total);  
                }
                
             
              generateTicket(String.valueOf(monto), String.valueOf(iva), String.valueOf(total));
              clearAll(dtm); 
              
              con.getCx().commit();    //TRANSACCIÓN
              
            }else{
                JOptionPane.showMessageDialog(null, "Lista de productos vacía.", "Aviso", JOptionPane.OK_OPTION);
            }      
            
        }catch(SQLException | NullPointerException e) {
            try {
                con.getCx().rollback(); //--------------------------ROLLBACK------------------------------------------
                JOptionPane.showMessageDialog(null, "Transacción denegada. " +e.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);  
            }catch(SQLException rollbackEx) {
                JOptionPane.showMessageDialog(null, "Error en transacción." + 
                    rollbackEx.getMessage(), "Aviso", JOptionPane.OK_OPTION); 
            }
        }finally {
            if(toInsert != null) {
                try {
                    toInsert.close();
                }catch(SQLException closeEx) {
                     JOptionPane.showMessageDialog(null, "Transacción denegada. " +closeEx.getMessage(), "Aviso", JOptionPane.ERROR_MESSAGE); 
                }
            }
        }     
    }
    
    private static void updateExistencias(Conect con, int id, int monto) throws SQLException{    
        try {
           String select = "SELECT existencias FROM info_productos WHERE id_producto = "+id;
           String update = "UPDATE info_productos SET existencias = ? WHERE id_producto = ?";
           PreparedStatement statement = con.getCx().prepareStatement(select);  
           //statement.setInt(1, id); 
           ResultSet resultado = statement.executeQuery(select);
        
           int existencia = 0;
             while(resultado.next()) {
               existencia = resultado.getInt("existencias");
             }

       
           statement = con.getCx().prepareStatement(update);  
           statement.setInt(1, existencia - monto); 
           statement.setInt(2, id); 
           int rowsUpdated = statement.executeUpdate(); 
        
             if(rowsUpdated == 0) {
                throw new SQLException("No se encontró el producto: "+id);
             }
       
        }catch(SQLException e) {
             throw new SQLException("No hay existencias del producto: "+id);
        }
    }
    
    public static void exists(){
        
    }
    
    /**
     * Crea una coleccion de id para usarse como filtros 
     * @param id 
     */
    private static Collection filter(){
        Set<Integer> ids = new HashSet<>();
        
            for(Product p : TRANSACTION.log){
               ids.add(p.getId());
            }
            
      return ids;  
    }
    
    /**
     * Genera un registro de toda la compra realizada
     * @return 
     */
    private static Map quantity(){        
        ArrayList<Integer> ids = new ArrayList<Integer>(); 
        ids.addAll(filter()); 
        Map<Integer, Integer> montos = new HashMap<>();
          
        int monto = 0; 
        for(int i = 0; i < ids.size(); i++){           
            for(Product p : TRANSACTION.log){
                if(p.getId() == ids.get(i)){
                   monto++;                
                }        
            } 
          montos.put(ids.get(i), monto);
          monto = 0;  
        }
        
      return montos;   
    }
    
    /**
     * Pone el producto en tabla y en registro.
     */
    public static void setProduct(JTable table, int id) {
        Product product; 
        if(id > 0){
            Conect con = new Conect();
            try {
                product = searchProduct(id, con);
                if(product == null){
                   JOptionPane.showMessageDialog(null, "No se encontró el producto", "Aviso", JOptionPane.OK_OPTION);
                }else{
                   loadProductDataOnMainTable(table, product);
                   loadProductOnlog(product); 
                   monto += product.getPrice(); 
                   iva += product.getIva(); 
                   setMontoTotal();    
                   con.desconectar(); 
                }
            }catch(SQLException ex) {
               ex.printStackTrace();
            }               
        }else{
           JOptionPane.showMessageDialog(null, "No se ingresó el código", "Aviso", JOptionPane.OK_OPTION);
        }       
    }
    
    private static void loadProductDataOnMainTable(JTable table, Product product){
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        String data[] = {product.getName(), String.valueOf(product.getPrice())}; 
        modelo.addRow(data); 
    }
    
    private static void clearTable(DefaultTableModel dtm){
        int rowCount = dtm.getRowCount();
        for(int i = rowCount - 1; i >= 0; i--) {
            dtm.removeRow(i);
        }
    }
    
    public static String getIva(){
        return String.valueOf(iva);
    }
    
    public static String getMonto(){
        return String.valueOf(monto);
    } 
     
    public static void clearLogs(){
        log.removeAllElements();
    }
    
    public static void loadProductOnlog(Product product){
        TRANSACTION.log.push(product);       
    }
   
    public static void removeFromLog(int index){
        monto -= (monto > 0)? log.get(index).getPrice() : 0;
        iva -= (iva > 0)? log.get(index).getIva() : 0; 
        TRANSACTION.log.remove(index); 
    }

    private static void clearAll(DefaultTableModel dtm){
        monto = 0; iva = 0; 
        clearTable(dtm);
        clearLogs();
    }
    
    public static void setMontoTotal(){
        total = ((monto * iva)/100)+monto;
    }

    public static String getTotal() {
        return String.valueOf(total);
    }
    
    
    
}
