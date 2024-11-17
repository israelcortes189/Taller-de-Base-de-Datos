
package BackEnd;

/**
 *
 * @author erikb
 */
public class Employee {
    
    private String name;
    private String lastName;
    private String lastName2;
    private String key;
    private int register;
    private String password;

    public Employee() {
    }

    @Override
    public String toString() {
        return "[Nombre: " + name + " Apellidos: " + lastName + " " + lastName2 + " Clave: " + key + "]";
    }
    
    public Object[] getValues(){
       Object[] values = {name, lastName, lastName2, key, register, password};  
      return values;
    }
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastNsme) {
        this.lastName = lastNsme;
    }

    public String getLastName2() {
        return lastName2;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getRegister() {
        return register;
    }

    public void setRegister(int register) {
        this.register = register;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
    
}
