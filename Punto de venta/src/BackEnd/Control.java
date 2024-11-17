
package BackEnd;

/**
 *
 * @author erikb
 */
public class Control {
    
    private static Employee employee;

    public static Employee getEmployee() {
        return employee;
    }

    public static void setEmployee(Employee employee) {
        Control.employee = employee;
    }
    
       
    
}
