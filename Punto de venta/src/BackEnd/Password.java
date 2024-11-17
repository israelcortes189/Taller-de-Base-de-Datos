
package BackEnd;

import java.awt.event.KeyEvent;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author erikb
 */
public class Password {
     
    private static StringBuilder securePassword = new StringBuilder();
    private static StringBuilder hide = new StringBuilder();
        
    public static boolean isRegularKey(int keyCode){
        return (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9) 
            || (keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z);
    }

    public static String getSecurePassword() {
        return securePassword.toString();
    }
    
    public static String setChar(int keyCode, Character key){
        
        if(hide.length() >= 0){
            if(isRegularKey(keyCode)){
                hide.append('\u2022'); 
                securePassword.append(key); 
            }else if(keyCode == 8){
                if(hide.length() > 0)
                   hide.deleteCharAt(hide.length() - 1); 
                   securePassword.deleteCharAt(securePassword.length() - 1);  
            }
        }
      return hide.toString();
    }
    
    public static Boolean selectPassword(String userKey, String password) throws SQLException, Exception {
        Conect con = new Conect();
        String sql = "SELECT `password`, clave FROM administradores_sistema WHERE clave = ?";

        PreparedStatement statement = con.getCx().prepareStatement(sql);
        statement.setString(1, userKey);  
        ResultSet resultado = statement.executeQuery();
    
        String sqlPassword = null; 
                
            while(resultado.next()) {
                sqlPassword = resultado.getString("password");
            }
    
       con.desconectar();         
      return comparePassword(encrypt(password), sqlPassword); 
    }

    private static Boolean comparePassword(String encryptedPassword, String sqlPassword){
        return encryptedPassword.equals(sqlPassword);
    }
    
    public static String encrypt(String password){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            
              for(byte b : hash) {
                  String hex = String.format("%02x", b);
                  hexString.append(hex);
              }
              
          return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
         
    }
    
   
}
