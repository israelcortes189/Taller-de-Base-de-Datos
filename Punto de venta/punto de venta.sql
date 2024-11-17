CREATE SCHEMA Pundo_de_venta;
USE Pundo_de_venta;

drop table administradores_sistema;
CREATE TABLE administradores_sistema (
     id_admin INT AUTO_INCREMENT,
     nombre VARCHAR(15) NOT NULL,
     apellido_paterno VARCHAR(20) NOT NULL,
     apellido_materno VARCHAR(20) NOT NULL,
     clave VARCHAR(30) NOT NULL UNIQUE,
     id_caja INT NOT NULL,
     `password` VARBINARY(128) NOT NULL,
FOREIGN KEY (id_caja) REFERENCES cajas(id_caja),      
PRIMARY KEY(id_admin)); 

INSERT INTO administradores_sistema (nombre, apellido_paterno, apellido_materno, clave, id_caja, `password`)
VALUES ("Erik", "Barrera", "Barrera", "E7774748", 417, SHA2('123456', 256));

CREATE TABLE productos (
     id_producto INT,
     nombre VARCHAR(50) NOT NULL,
     tipo VARCHAR(30) NOT NULL,
     precio DECIMAL NOT NULL,
PRIMARY KEY(id_producto)); 

ALTER TABLE productos ADD COLUMN iva DOUBLE;
-- UPDATE productos SET iva = 2.0 WHERE tipo="botanas"; 

CREATE TABLE info_productos (
     id INT AUTO_INCREMENT,
	 id_producto INT,
     existencias INT NOT NULL,
     proveedor VARCHAR(150) NOT NULL,
     CHECK (existencias >= 0),
CONSTRAINT fk_producto FOREIGN KEY (id_producto) REFERENCES productos(id_producto) ON DELETE CASCADE,     
PRIMARY KEY(id)); 

-- esta no
drop table cajas;
CREATE TABLE cajas (
     id INT AUTO_INCREMENT,
     id_caja INT NOT NULL UNIQUE,
     nombre_establecimiento VARCHAR(100),
     dinero DECIMAL(10, 2),       
PRIMARY KEY(id)); 

INSERT INTO cajas (id_caja, nombre_establecimiento, dinero) VALUES (417, "CANACA", 1000);

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
PRIMARY KEY(id)); 

drop table detalle_ventas;
DELETE FROM detalle_ventas WHERE clave = "E7774748";
INSERT INTO detalle_ventas (clave, id_producto, monto, cantidad) VALUES ("?","?","?","?");
UPDATE info_productos SET existencias = 2 WHERE id_producto=147; 