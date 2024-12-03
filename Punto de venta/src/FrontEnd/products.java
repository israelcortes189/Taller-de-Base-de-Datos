package FrontEnd;

import BackEnd.InfoProducto;
import BackEnd.Product;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author erikb
 */
public class products extends javax.swing.JDialog{
    public void cargarProductos() {
    try {
        // Llama al método que obtiene los productos desde la base de datos
        List<Product> products = Product.getProducts();
        List<InfoProducto> infoProducts = Product.getInfoProductos();

        // Configura el modelo de tabla para `jTable1`
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Tipo");
        model.addColumn("Precio");
        model.addColumn("Existencias");
        model.addColumn("Proveedor");

        // Agrega los datos de los productos y su información adicional al modelo de la tabla
        for (Product product : products) {
            // Encuentra la información correspondiente en `infoProducts` por `idProducto`
            InfoProducto matchingInfo = infoProducts.stream()
                .filter(info -> info.getIdProducto() == product.getId())
                .findFirst()
                .orElse(null);

            // Si hay información adicional, úsala; de lo contrario, coloca valores predeterminados
            int existencias = (matchingInfo != null) ? matchingInfo.getExistencias() : 0;
            String proveedor = (matchingInfo != null) ? matchingInfo.getProveedor() : "Desconocido";

            // Agrega una fila con los datos combinados
            model.addRow(new Object[]{
                product.getId(),
                product.getName(),
                product.getType(),
                product.getPrice(),
                existencias,
                proveedor
            });
        }

        // Establece el modelo en `jTable1`
        jTable1.setModel(model);

    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Error al cargar los productos", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

    public void BuscarProductos(int codigo) {
    try {
        // Llama a los métodos que obtienen los datos desde la base de datos
        Product product = Product.buscarProduct(codigo);
        InfoProducto infoProducto = Product.buscarInfoProducto(codigo);

        if (product != null && infoProducto != null) {
            // Configura el modelo de tabla para `jTable1`
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Tipo");
            model.addColumn("Precio");
            model.addColumn("Existencias");
            model.addColumn("Proveedor");

            // Agrega los datos del producto y su información relacionada al modelo de la tabla
            model.addRow(new Object[]{
                product.getId(),
                product.getName(),
                product.getType(),
                product.getPrice(),
                infoProducto.getExistencias(),
                infoProducto.getProveedor()
            });

            // Asigna el modelo al `jTable1`
            jTable1.setModel(model);
        } else if (product == null) {
            javax.swing.JOptionPane.showMessageDialog(null, "Producto no encontrado", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "No se encontró información adicional para el producto", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Error al buscar el producto", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}


    public void EliminarProducto(int codigo) {
        try {
            // Llama al método que obtiene los productos desde la base de datos
            Product.eliminarProducto(codigo);
            Product.eliminarProducto(codigo);
            javax.swing.JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente");
            cargarProductos();
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Error al eliminar el producto", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    public products(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtIdProduct.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyTyped(java.awt.event.KeyEvent evt) {
            char caracter = evt.getKeyChar();
            // Verifica si el caracter no es un número
            if ((caracter < '0' || caracter > '9') && caracter != KeyEvent.VK_BACK_SPACE) {
                evt.consume();  // Cancela el evento si no es un número
            }
        }
        });
        cargarProductos();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtIdProduct = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jToolBar1.setRollover(true);

        jButton1.setText("Editar producto");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton2.setText("Eliminar producto");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton4.setText("Agregar Producto");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 102));
        jLabel1.setText("ID Producto");

        txtIdProduct.setBackground(new java.awt.Color(153, 153, 153));
        txtIdProduct.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        txtIdProduct.setForeground(new java.awt.Color(0, 51, 0));
        txtIdProduct.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIdProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdProductActionPerformed(evt);
            }
        });

        btnSearch.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnSearch.setText("Buscar");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jButton3.setText("Atrás");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtIdProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch)
                .addContainerGap(250, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSearch)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtIdProduct, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        int codigo = Integer.parseInt(txtIdProduct.getText());
        BuscarProductos(codigo);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(null, "Tienes que seleccionar un producto", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        } else {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            int id = (int) model.getValueAt(selectedRow, 0); // Columna 0 para ID
            EdtarProducto productWindow = new EdtarProducto(null, true, id, this);
            productWindow.setLocationRelativeTo(this); // Asigna el listener para recibir la notificación
            productWindow.setVisible(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtIdProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdProductActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdProductActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(null, "Tienes que seleccionar un producto", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        } else {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            // Accede a los valores de las celdas en la fila seleccionada
            int id = (int) model.getValueAt(selectedRow, 0); // Columna 0 para ID
            int opcion = javax.swing.JOptionPane.showConfirmDialog(
                    null,
                    "¿Estás seguro de que deseas eliminar el producto con ID: " + id + "?",
                    "Confirmación de eliminación",
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );

            if (opcion == javax.swing.JOptionPane.YES_OPTION) {
                EliminarProducto(id);

            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Operación cancelada");
            }
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            AgregarProducto productWindow = new AgregarProducto(null, true, this);
            productWindow.setLocationRelativeTo(this); // Asigna el listener para recibir la notificación
            productWindow.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField txtIdProduct;
    // End of variables declaration//GEN-END:variables
}
