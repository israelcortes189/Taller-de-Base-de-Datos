
package BackEnd;

/**
 *
 * @author erikb
 */
public class Register {
    
    private int id;
    private String name;
    private double cash;

    public Register() {
    }
    
    public Register(int id, String key, String name, double cash) {
        this.id = id;
        this.name = name;
        this.cash = cash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    @Override
    public String toString() {
        return "Register{" + "id=" + id + ", name=" + name + ", cash=" + cash + '}';
    }
    
    
    
}
