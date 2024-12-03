
package BackEnd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author erikb
 */
public class Product {
    
    private int id;
    private String name;
    private String type;
    private double price;
    private double iva;

    public Product() {
    }
    
    public Product(int id, String name, String type, double price, double iva) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.iva = iva;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", type=" + type + ", price=" + price + ", iva=" + iva + '}';
    }
    
    //Metodo para obtener una Lista de Productos existentes
    public static List<Product> getProducts() throws SQLException {
        Conect con = new Conect();
        String sql = "SELECT * FROM productos";
        PreparedStatement statement = con.getCx().prepareStatement(sql);
        ResultSet resultado = statement.executeQuery();

        List<Product> products = new ArrayList<>();  // Lista para almacenar todos los productos

        while (resultado.next()) {
            Product p = new Product();
            p.setId(resultado.getInt("id_producto"));
            p.setName(resultado.getString("nombre"));
            p.setType(resultado.getString("tipo"));
            p.setPrice(resultado.getDouble("precio"));

            products.add(p);  // Agrega el producto a la lista
        }

        con.desconectar();
        return products;  // Devuelve la lista completa de productos
    }

    public static List<InfoProducto> getInfoProductos() throws SQLException {
    // Establece la conexión
    Conect con = new Conect();
    // Lista para almacenar los resultados
    List<InfoProducto> infoProductos = new ArrayList<>();
    
    // Sentencia SQL para realizar el JOIN
    String sql = "SELECT p.id_producto, i.existencias, i.proveedor FROM info_productos i JOIN productos p ON i.id_producto = p.id_producto";
    PreparedStatement statement = con.getCx().prepareStatement(sql);
    
    // Ejecuta la consulta
    ResultSet resultSet = statement.executeQuery();
    // Recorre los resultados
    while (resultSet.next()) {
        int id= resultSet.getInt("id_producto");
        int existencias = resultSet.getInt("existencias");
        String proveedor = resultSet.getString("proveedor");
        
        // Crea un objeto InfoProducto para almacenar los datos
        InfoProducto infoProducto = new InfoProducto(id, existencias, proveedor);
        infoProductos.add(infoProducto);
    }
    // Cierra la conexión
    con.desconectar();
    // Devuelve la lista con los resultados
    return infoProductos;
    }
    
    // Método que busca un producto por ID y devuelve un objeto Product
    public static Product buscarProduct(int codigo) throws SQLException {
        // Establece la conexión
        Conect con = new Conect();
        String sql = "SELECT * FROM productos WHERE id_producto = ?";
        Product producto = null;

        try (PreparedStatement statement = con.getCx().prepareStatement(sql)) {
            // Establece el valor del parámetro en la consulta
            statement.setInt(1, codigo);

            // Ejecuta la consulta
            ResultSet resultado = statement.executeQuery();

            // Procesa el resultado
            if (resultado.next()) {
                producto = new Product();
                producto.setId(resultado.getInt("id_producto"));
                producto.setName(resultado.getString("nombre"));
                producto.setType(resultado.getString("tipo"));
                producto.setPrice(resultado.getDouble("precio"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Lanza la excepción para que pueda ser manejada en otro nivel
        } finally {
            // Cierra la conexión
            con.desconectar();
        }
        // Retorna el producto encontrado o null si no existe
        return producto;
    }
    
    public static InfoProducto buscarInfoProducto(int codigo) throws SQLException {
    // Establece la conexión
    Conect con = new Conect();
    String sql = "SELECT i.existencias, i.proveedor FROM info_productos i JOIN productos p ON i.id_producto = p.id_producto WHERE i.id_producto = ?";
    InfoProducto infoProducto = null;

    try (PreparedStatement statement = con.getCx().prepareStatement(sql)) {
        // Establece el valor del parámetro en la consulta
        statement.setInt(1, codigo);

        // Ejecuta la consulta
        ResultSet resultado = statement.executeQuery();

        // Procesa el resultado
        if (resultado.next()) {
            infoProducto = new InfoProducto();
            infoProducto.setExistencias(resultado.getInt("existencias"));
            infoProducto.setProveedor(resultado.getString("proveedor"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e; // Lanza la excepción para que pueda ser manejada en otro nivel
    } finally {
        // Cierra la conexión
        con.desconectar();
    }
    // Retorna la información encontrada o null si no existe
    return infoProducto;
}
    
    public static void eliminarProducto(int codigo) throws SQLException {
    // Establece la conexión
    Conect con = new Conect();
    String sql = "DELETE FROM productos WHERE id_producto = ?";

    try (PreparedStatement statement = con.getCx().prepareStatement(sql)) {
        // Establece el valor del parámetro en la consulta
        statement.setInt(1, codigo);
        // Ejecuta la consulta
        int filasAfectadas = statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        throw e; // Lanza la excepción para que pueda ser manejada en otro nivel
    } finally {
        // Cierra la conexión
        con.desconectar();
    }
}
    
    public static void deleteInfoProducto(int idProducto) throws SQLException {
    // Establece la conexión
    Conect con = new Conect();
    String sql = "DELETE i FROM info_productos i JOIN productos p ON i.id_producto = p.id_producto WHERE i.id_producto = ?";

    try (PreparedStatement statement = con.getCx().prepareStatement(sql)) {
        // Configura el parámetro de la consulta
        statement.setInt(1, idProducto);

        // Ejecuta la eliminación
        int rowsDeleted = statement.executeUpdate();

        if (rowsDeleted > 0) {
            System.out.println("Registros eliminados correctamente.");
        } else {
            System.out.println("No se encontraron registros para eliminar.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e; // Propaga la excepción
    } finally {
        // Cierra la conexión
        con.desconectar();
    }
}
    
    public static void updateProduct(Product product) throws SQLException {
    // Establece la conexión
    Conect con = new Conect();
    // Sentencia SQL para actualizar un producto
    String sql = "UPDATE productos SET nombre = ?, tipo = ?, precio = ? WHERE id_producto = ?";
    PreparedStatement statement = con.getCx().prepareStatement(sql);
    // Configura los parámetros en la consulta
    statement.setString(1, product.getName());
    statement.setString(2, product.getType());
    statement.setDouble(3, product.getPrice());
    statement.setInt(4, product.getId());  // La ID del producto para la cláusula WHERE
    // Ejecuta la actualización
    int rowsUpdated = statement.executeUpdate();
    if (rowsUpdated > 0) {
        System.out.println("Producto actualizado exitosamente.");
    } else {
        System.out.println("No se encontró el producto con el ID especificado.");
    }

    // Cierra la conexión
    con.desconectar();
    }
    
    public static void updateInfoProduct(InfoProducto infoproduct) throws SQLException {
    // Establece la conexión
    Conect con = new Conect();
    // Sentencia SQL para actualizar un producto
    String sql = "UPDATE info_productos SET existencias = ?, proveedor = ? WHERE id_producto = ?";
    PreparedStatement statement = con.getCx().prepareStatement(sql);
    // Configura los parámetros en la consulta
    
    statement.setInt(1, infoproduct.getExistencias());
    statement.setString(2, infoproduct.getProveedor());
    statement.setInt(3, infoproduct.getIdProducto());  // La ID del producto para la cláusula WHERE
    // Ejecuta la actualización
    int rowsUpdated = statement.executeUpdate();
    if (rowsUpdated > 0) {
        System.out.println("Producto actualizado exitosamente.");
    } else {
        System.out.println("No se encontró el producto con el ID especificado.");
    }

    // Cierra la conexión
    con.desconectar();
}
    
    public static boolean addProduct(Product product) {
    Conect con = new Conect();
    String sql = "INSERT INTO productos (id_producto, nombre, tipo, precio) VALUES (?, ?, ?, ?)";

    try (PreparedStatement statement = con.getCx().prepareStatement(sql)) {
        // Configura los parámetros en la consulta
        statement.setInt(1, product.getId());
        statement.setString(2, product.getName());
        statement.setString(3, product.getType());
        statement.setDouble(4, product.getPrice());

        // Ejecuta la inserción
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(null, "Producto agregado exitosamente.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo agregar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (SQLIntegrityConstraintViolationException ex) {
        // Maneja la violación de clave foránea específicamente
        JOptionPane.showMessageDialog(null, "Error: No se puede agregar el producto debido a una violación de clave foránea.\n" 
                                      + "Verifica que el producto relacionado exista en la tabla 'productos'.",
                                      "Violación de integridad referencial", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException ex) {
        // Maneja otras excepciones SQL generales
        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(),
                                      "Error en la inserción", JOptionPane.ERROR_MESSAGE);
    } finally {
        con.desconectar();  // Cierra la conexión
    }
        return false;
}
    
    public static void addInfoProduct(InfoProducto infoproduct) throws SQLException {
    // Establece la conexión
    Conect con = new Conect();
    // Sentencia SQL para actualizar un producto
    String sql = "INSERT INTO info_productos (id_producto, existencias, proveedor) VALUES (?, ?, ?)";
    PreparedStatement statement = con.getCx().prepareStatement(sql);
    // Configura los parámetros en la consulta
    statement.setInt(1, infoproduct.getIdProducto()); 
    statement.setInt(2, infoproduct.getExistencias());
    statement.setString(3, infoproduct.getProveedor());
    // Ejecuta la actualización
    int rowsUpdated = statement.executeUpdate();
    if (rowsUpdated > 0) {
        System.out.println("Producto actualizado exitosamente.");
    } else {
        System.out.println("No se encontró el producto con el ID especificado.");
    }
    // Cierra la conexión
    con.desconectar();
}      
}
