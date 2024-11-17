
package BackEnd;

/**
 *
 * @author erikb
 */
public class Sale {
    
    private String key;
    private int id;
    private double monto;
    private int quantity;
    private String hour;
    private String date;

    public Sale() {
    }
       
    public Sale(String key, int id, double monto, int quantity, String date, String hour) {
        this.key = key;
        this.id = id;
        this.monto = monto;
        this.quantity = quantity;
        this.date = date;
        this.hour = hour;
    }

    public Object[] getValues(){
       Object[] values = {key, id, monto, quantity, date, hour};  
      return values;
    }
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
    
    
    
    
}
