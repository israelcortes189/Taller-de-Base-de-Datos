
package tools;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import BackEnd.Product;

/**
 *
 * @author erikb
 */
public class Ticket {
    
    private static List<String> sales = new ArrayList<>();
    
    public static void saveStrings(String str){
        sales.add(str);
    }
    
    
    public static void generateTicket(String monto, String iva, String total){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Compra"))) {
        writer.write("==================== Tienda =======================");
        writer.newLine();
        writer.write("Producto           Cantidad              Precio");
        writer.newLine();
            for(String line : sales) {
                writer.write(line);
                writer.newLine();
            }
        writer.write("=====================================================");
        writer.newLine();
        writer.write("Monto: "+monto);
        writer.newLine();
        writer.write("I.V.A: "+iva);
        writer.newLine();
        writer.write("Total: "+total);
        writer.newLine();
        sales.clear();
        }catch(IOException e) {
            e.printStackTrace();
        }       
    }
    
}
