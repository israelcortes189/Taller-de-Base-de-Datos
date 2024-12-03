
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
    
    /**
     * Traduce los meses almacenados en el comboBox de meses a inglés.
     * @param index El índice que ocupa el mes en el comboBox.
     * @return El mes traducido a inglés.
     */
    public static String traslate(int index){
        switch(index){
            case(0): return "January";
            case(1): return "February";
            case(2): return "March";
            case(3): return "April";
            case(4): return "May";
            case(5): return "June";
            case(6): return "July";
            case(7): return "Auguts";
            case(8): return "September";
            case(9): return "October";
            case(10): return "November";
            case(11): return "December";
            default: return "";    
        }
    }
       
    
}
