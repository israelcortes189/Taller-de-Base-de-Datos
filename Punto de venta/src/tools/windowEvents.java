
package tools;

import java.awt.Window;

/**
 *
 * @author erikb
 */
public class windowEvents {
    
    public static void closeWindow(Window window) {
        if (window != null) {
            window.dispose();  
        }
    }

    public static Boolean removeRow(int selectedRow){        
      return selectedRow != -1; 
    }
    
     
}
