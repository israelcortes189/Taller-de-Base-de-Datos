/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

/**
 *
 * @author ADMIN
 */
public class InfoProducto {
    private int idProducto; // Clave foránea a la tabla productos
    private int existencias;
    private String proveedor;

    // Constructor sin parámetros
    public InfoProducto() {
    }

    // Constructor con parámetros
    public InfoProducto(int idProducto,int existencias, String proveedor) {
        this.idProducto = idProducto;
        this.existencias = existencias;
        this.proveedor = proveedor;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        if (existencias >= 0) {
            this.existencias = existencias;
        } else {
            throw new IllegalArgumentException("Las existencias no pueden ser negativas.");
        }
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public String toString() {
        return "InfoProducto{" +
                ", idProducto=" + idProducto +
                ", existencias=" + existencias +
                ", proveedor='" + proveedor + '\'' +
                '}';
    }
}
