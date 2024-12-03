/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import BackEnd.Conect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class cliente {
    private String nombre;
    private int id;

    public cliente() {
    }
    
    public cliente(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
    }

    @Override
    public String toString() {
        return "cliente{" + "nombre=" + nombre + ", id=" + id + '}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    //Metodo para obtener una Lista de Clientes existentes
    public static List<cliente> getClientes() throws SQLException {
        Conect con = new Conect();
        String sql = "SELECT * FROM clientes";
        PreparedStatement statement = con.getCx().prepareStatement(sql);
        ResultSet resultado = statement.executeQuery();

        List<cliente> clientes = new ArrayList<>();  // Lista para almacenar todos los productos

        while (resultado.next()) {
            cliente c = new cliente();
            c.setId(resultado.getInt("id_cliente"));
            c.setNombre(resultado.getString("nombre"));

            clientes.add(c);  // Agrega el producto a la lista
        }
        con.desconectar();
        return clientes;  // Devuelve la lista completa de productos
    }
    
    public static cliente buscarCliente(int codigo) throws SQLException {
        // Establece la conexión
        Conect con = new Conect();
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
        cliente cliente = null;

        try (PreparedStatement statement = con.getCx().prepareStatement(sql)) {
            // Establece el valor del parámetro en la consulta
            statement.setInt(1, codigo);
            // Ejecuta la consulta
            ResultSet resultado = statement.executeQuery();

            // Procesa el resultado
            if (resultado.next()) {
                cliente = new cliente();
                cliente.setId(resultado.getInt("id_cliente"));
                cliente.setNombre(resultado.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Lanza la excepción para que pueda ser manejada en otro nivel
        } finally {
            // Cierra la conexión
            con.desconectar();
        }
        // Retorna el producto encontrado o null si no existe
        return cliente;
    }
    
    public static void eliminarCliente(int idCliente) throws SQLException {
    Conect con = new Conect();
    String sql = "{CALL EliminarCliente(?)}";  // Llama al Stored Procedure

    try (PreparedStatement statement = con.getCx().prepareStatement(sql)) {
        // Establece el valor del parámetro para el Stored Procedure
        statement.setInt(1, idCliente);
        
        // Ejecuta el procedimiento
        statement.executeUpdate();
        System.out.println("Cliente con ID " + idCliente + " ha sido eliminado.");
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;  // Lanza la excepción para que pueda ser manejada más arriba
    } finally {
        con.desconectar();  // Cierra la conexión
    }
    }
    
    public static void editarCliente(int idCliente, String nuevoNombre) throws SQLException {
    Conect con = new Conect();
    String sql = "{CALL EditarCliente(?, ?)}";  // Llama al Stored Procedure

    try (PreparedStatement statement = con.getCx().prepareStatement(sql)) {
        // Establece los valores de los parámetros para el Stored Procedure
        statement.setInt(1, idCliente);        // Establece el id_cliente
        statement.setString(2, nuevoNombre);   // Establece el nuevo nombre
        
        // Ejecuta el procedimiento
        statement.executeUpdate();
        System.out.println("Cliente con ID " + idCliente + " ha sido actualizado.");
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;  // Lanza la excepción para que pueda ser manejada más arriba
    } finally {
        con.desconectar();  // Cierra la conexión
    }
}
    
    public static void agregarCliente(int idCliente, String nuevoNombre) throws SQLException {
    Conect con = new Conect();
    String sql = "{CALL AgregarCliente(?, ?)}";  // Llama al Stored Procedure

    try (PreparedStatement statement = con.getCx().prepareStatement(sql)) {
        // Establece los valores de los parámetros para el Stored Procedure
        statement.setInt(1, idCliente);        // Establece el id_cliente
        statement.setString(2, nuevoNombre);   // Establece el nuevo nombre
        
        // Ejecuta el procedimiento
        statement.executeUpdate();
        System.out.println("Cliente con ID " + idCliente + " ha sido agregado.");
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;  // Lanza la excepción para que pueda ser manejada más arriba
    } finally {
        con.desconectar();  // Cierra la conexión
    }
}


}
