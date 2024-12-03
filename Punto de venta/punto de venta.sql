-- Creación del esquema y uso del mismo
CREATE SCHEMA Pundo_de_venta;
USE Pundo_de_venta;       

-- -----------------------------------------------------------------
-- TABLA administradores_sistema
-- -----------------------------------------------------------------
DROP TABLE administradores_sistema;
CREATE TABLE administradores_sistema (
     id_admin INT AUTO_INCREMENT,                       
     nombre VARCHAR(15) NOT NULL,                      
     apellido_paterno VARCHAR(20) NOT NULL,           
     apellido_materno VARCHAR(20) NOT NULL,           
     clave VARCHAR(30) NOT NULL UNIQUE,               
     id_caja INT NOT NULL,                             
     `password` VARBINARY(128) NOT NULL,              
     FOREIGN KEY (id_caja) REFERENCES cajas(id_caja), 
     PRIMARY KEY(id_admin)                            
); 

-- -----------------------------------------------------------------
-- TABLA productos
-- -----------------------------------------------------------------
CREATE TABLE productos (
     id_producto INT,                               
     nombre VARCHAR(50) NOT NULL,                    
     tipo VARCHAR(30) NOT NULL,                      
     precio DECIMAL(10,2) NOT NULL,                  
     iva DOUBLE(10,2),                               
     PRIMARY KEY(id_producto)                        
); 

-- -----------------------------------------------------------------
-- TABLA info_productos
-- -----------------------------------------------------------------
CREATE TABLE info_productos (
     id INT AUTO_INCREMENT,                          
     id_producto INT,                               
     existencias INT NOT NULL,                       
     proveedor VARCHAR(150) NOT NULL,               
     CHECK (existencias >= 0),                      
     CONSTRAINT fk_producto FOREIGN KEY (id_producto) REFERENCES productos(id_producto) ON DELETE CASCADE, 
     PRIMARY KEY(id)                               
); 

-- -----------------------------------------------------------------
-- PROCEDIMIENTO generarInfo_productos()
-- -----------------------------------------------------------------
DELIMITER $$
CREATE PROCEDURE generarInfo_productos()
BEGIN
    DECLARE contador INT DEFAULT 0;                 
    DECLARE idProducto INT;                          
    DECLARE existencias INT;                        
    DECLARE proveedor VARCHAR(150);      

    -- Cursor para iterar sobre todos los productos
    DECLARE done INT DEFAULT FALSE;
    DECLARE cur CURSOR FOR SELECT id_producto FROM productos;

    -- Abrir cursor
    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO idProducto;
        IF done THEN
            LEAVE read_loop;                       
        END IF;

        -- Generar datos aleatorios para existencias y proveedor
        SET existencias = FLOOR(RAND() * 101);      
        SET proveedor = ELT(FLOOR(RAND() * 15) + 1, 
            'Señores del campo', 'Central de Abastos', 'CANACA', 'Odebrech Agricultura', 
            'P Mex', 'Don Lalo Industries', 'Bimbo', 'Marinela', 'Rotten Foods', 
            'FEMSA', 'Granjas Doña Marta', 'CONASUPO');

        -- Insertar datos generados en la tabla info_productos
        INSERT INTO info_productos (id_producto, existencias, proveedor)
        VALUES (idProducto, existencias, proveedor);
        SET contador = contador + 1;                 
    END LOOP;
    CLOSE cur;                                      
END$$
DELIMITER ;

-- -----------------------------------------------------------------
-- LLAMAR AL PROCEDIMIENTO generarInfo_productos()
-- -----------------------------------------------------------------
CALL generarInfo_productos(); 

-- -----------------------------------------------------------------
-- TABLA cajas
-- -----------------------------------------------------------------
CREATE TABLE cajas (
     id INT AUTO_INCREMENT,                         
     id_caja INT NOT NULL UNIQUE,                    
     nombre_establecimiento VARCHAR(100),            
     dinero DECIMAL(10, 2),                          
     PRIMARY KEY(id)
); 

INSERT INTO cajas (id_caja, nombre_establecimiento, dinero) VALUES (417, "CANACA", 1000);
-- -----------------------------------------------------------------
-- TABLA detalle_ventas
-- -----------------------------------------------------------------
CREATE TABLE detalle_ventas (
     id INT AUTO_INCREMENT,                          
     clave VARCHAR(30) NOT NULL,                    
     id_producto INT,                                
     monto DECIMAL(10, 2) NOT NULL,                  
     cantidad DECIMAL NOT NULL,                      
     CHECK (cantidad >= 0),                         
     fecha DATETIME DEFAULT CURRENT_TIMESTAMP,      
     FOREIGN KEY (clave) REFERENCES administradores_sistema(clave), 
     FOREIGN KEY (id_producto) REFERENCES productos(id_producto), 
     PRIMARY KEY(id)                                
); 

-- STORED PROCEDURES ============================================================================
-- VENTAS SIMULADAS ALEATORIAS===================================================================
DELIMITER $$
CREATE PROCEDURE VentaAleatoria(IN cantidad INT)
BEGIN
    DECLARE claveEmpleado VARCHAR(30);
    DECLARE idProducto INT;
    DECLARE precioProducto DECIMAL(10, 2);
    DECLARE fechaVenta DATETIME;

    -- Generar clave de empleado aleatoria
    SET claveEmpleado = ELT(FLOOR(RAND() * 2 + 1), 'E7774748', 'C194539');

    -- Generar ID de producto aleatorio (entre 1 y 50)
    SET idProducto = FLOOR(1 + RAND() * 50);

    -- Obtener el precio del producto desde la tabla productos
    SELECT precio INTO precioProducto
    FROM productos
    WHERE id_producto = idProducto;

    -- Verificar si el precio es NULL (producto no encontrado)
    IF precioProducto IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Producto no encontrado';
    END IF;

    -- Generar fecha aleatoria en los últimos 365 días
    SET fechaVenta = DATE_ADD(NOW(), INTERVAL -FLOOR(RAND() * 365) DAY);

    -- Insertar en la tabla detalle_ventas
    INSERT INTO detalle_ventas (clave, id_producto, monto, cantidad, fecha)
    VALUES (claveEmpleado, idProducto, precioProducto * cantidad, cantidad, fechaVenta);
END$$
DELIMITER ;
CALL VentaAleatoria(5);

-- Llamar VentaAleatoria 5000 veces
DELIMITER $$
CREATE PROCEDURE generar5000Ventas()
BEGIN
    DECLARE contador INT DEFAULT 0;
    DECLARE cantidad_random INT;

    WHILE contador < 5000 DO
        SET cantidad_random = FLOOR(1 + RAND() * 20);
        CALL VentaAleatoria(cantidad_random);
        SET contador = contador + 1;
    END WHILE;
END$$
DELIMITER ;

CALL generar5000Ventas();

-- VISTAS ============================================
-- VISTA DE VENTAS POR MES ===========================
CREATE VIEW ventas_mensuales AS
SELECT 
    MIN(dv.id) AS folio,                           -- Elige un identificador representativo
    DATE_FORMAT(dv.fecha, '%Y-%m') AS mes,
    dv.clave AS clave_empleado,
    e.nombre AS nombre_empleado,
    SUM(dv.monto) AS total_venta,                  -- Total de dinero generado en el mes
    COUNT(dv.id) AS detalles                       -- Número de detalles o ventas realizadas
FROM detalle_ventas dv
JOIN administradores_sistema e ON dv.clave = e.clave
GROUP BY mes, dv.clave, e.nombre;                 -- Asegúrate de incluir todas las columnas no agregadas

-- =================================================================================================================================
-- VISTA DE VENTAS POR EMPLEADO ====================================================================================================
CREATE VIEW ventas_por_empleado AS
SELECT 
    e.nombre AS empleado, 
    DATE_FORMAT(dv.fecha, '%Y-%m') AS mes, -- Extraer el mes y el año
    SUM(dv.monto) AS total, 
    COUNT(dv.id) AS cantidad_de_ventas
FROM detalle_ventas dv
JOIN administradores_sistema e ON dv.clave = e.clave
GROUP BY e.nombre, mes;  -- Agrupar también por mes

-- =================================================================================================================================
-- VISTA DE VENTAS POR TRIMESTRE ====================================================================================================
CREATE VIEW ventas_por_trimestre AS
SELECT 
    p.nombre AS producto,
    YEAR(dv.fecha) AS anio,  -- Agrega el año aquí
    SUM(CASE WHEN QUARTER(dv.fecha) = 1 THEN dv.monto ELSE 0 END) AS trimestre_1,
    SUM(CASE WHEN QUARTER(dv.fecha) = 2 THEN dv.monto ELSE 0 END) AS trimestre_2,
    SUM(CASE WHEN QUARTER(dv.fecha) = 3 THEN dv.monto ELSE 0 END) AS trimestre_3,
    SUM(CASE WHEN QUARTER(dv.fecha) = 4 THEN dv.monto ELSE 0 END) AS trimestre_4
FROM detalle_ventas dv
JOIN productos p ON dv.id_producto = p.id_producto
GROUP BY p.nombre, anio;  -- Agrupa también por el año
-- CLIENTES========================================================
CREATE TABLE clientes (
    id_cliente INT,
    nombre VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_cliente)
);

DELIMITER $$
CREATE PROCEDURE EliminarCliente(IN p_idCliente INT)
BEGIN
    -- Eliminar cliente de la tabla clientes
    DELETE FROM clientes WHERE id_cliente = p_idCliente;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE EditarCliente(
    IN p_id_cliente INT,
    IN p_nombre VARCHAR(255)
)
BEGIN
    UPDATE clientes
    SET nombre = p_nombre
    WHERE id_cliente = p_id_cliente;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE AgregarCliente(
    IN p_id_cliente INT,
    IN p_nombre VARCHAR(255)
)
BEGIN
    insert into clientes(id_cliente, nombre) values (p_id_cliente, p_nombre);
END $$
DELIMITER ;

-- -------------------------------------------------------------------------------------------
-- -------------------------------------- Triggers -------------------------------------------

-- Tabla Auditoria
CREATE TABLE auditoria_detalle_ventas (
    id_auditoria INT AUTO_INCREMENT PRIMARY KEY,
    accion VARCHAR(10) NOT NULL,
    id_detalle INT,
    clave VARCHAR(30),
    id_producto INT,
    monto DECIMAL(10, 2),
    cantidad DECIMAL,
    fecha_auditoria TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_accion VARCHAR(50) NOT NULL
);


-- ============================================================
-- Trigger Insert Detalles Ventas ============================
DELIMITER $$
CREATE TRIGGER insert_detalle_ventas
AFTER INSERT ON detalle_ventas
FOR EACH ROW
BEGIN
    INSERT INTO auditoria_detalle_ventas (accion, id_detalle, clave, id_producto, monto, cantidad, usuario_accion)
    VALUES ('INSERT', NEW.id, NEW.clave, NEW.id_producto, NEW.monto, NEW.cantidad, USER());
END$$
DELIMITER ;

-- ============================================================
-- Trigger Updates Detalles Ventas ============================
DELIMITER $$
CREATE TRIGGER update_detalle_ventas
AFTER UPDATE ON detalle_ventas
FOR EACH ROW
BEGIN
    INSERT INTO auditoria_detalle_ventas (accion, id_detalle, clave, id_producto, monto, cantidad, usuario_accion)
    VALUES ('UPDATE', OLD.id, OLD.clave, OLD.id_producto, OLD.monto, OLD.cantidad, USER());
END$$
DELIMITER ;

-- ============================================================
-- Trigger Delete Detalles Ventas ============================
DELIMITER $$
CREATE TRIGGER delete_detalle_ventas
AFTER DELETE ON detalle_ventas
FOR EACH ROW
BEGIN
    INSERT INTO auditoria_detalle_ventas (accion, id_detalle, clave, id_producto, monto, cantidad, usuario_accion)
    VALUES ('DELETE', OLD.id, OLD.clave, OLD.id_producto, OLD.monto, OLD.cantidad, USER());
END$$
DELIMITER ;

-- ============================================================
-- Trigger Validaciones para productos ============================
DELIMITER $$
CREATE TRIGGER validar_productos
BEFORE INSERT ON productos
FOR EACH ROW
BEGIN
    -- Validación del precio no negativo
    IF NEW.precio < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El precio no puede ser negativo';
    END IF;

    -- Validación del nombre del producto no vacío o solo con espacios
    IF TRIM(NEW.nombre) = '' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El nombre del producto no puede estar vacío o tener solo espacios';
    END IF;

    -- Validación del id_producto: debe ser NULL o estar entre 8 y 20 caracteres
    IF NEW.id_producto IS NOT NULL AND (LENGTH(CAST(NEW.id_producto AS CHAR)) < 8 OR LENGTH(CAST(NEW.id_producto AS CHAR)) > 20) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El id_producto debe tener entre 8 y 20 caracteres';
    END IF;
END$$
DELIMITER ;