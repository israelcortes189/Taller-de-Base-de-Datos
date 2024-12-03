-- Inserciones
USE Pundo_de_venta;
INSERT INTO productos (id_producto, nombre, tipo, precio) VALUES
(1, 'Laptop Pro 15"', 'Electrónica', 1200.50),
(2, 'Smartphone X12', 'Electrónica', 899.99),
(3, 'Silla de Oficina Ergonómica', 'Mobiliario', 150.75),
(4, 'Mesa de Conferencia', 'Mobiliario', 349.99),
(5, 'Cafetera Automática', 'Electrodomésticos', 89.90),
(6, 'Refrigerador Compacto', 'Electrodomésticos', 299.50),
(7, 'Auriculares Bluetooth', 'Accesorios', 49.99),
(8, 'Monitor 24" Full HD', 'Electrónica', 180.00),
(9, 'Teclado Mecánico', 'Accesorios', 79.99),
(10, 'Impresora Láser', 'Oficina', 210.25);

delete from productos;

INSERT INTO info_productos (id_producto, existencias, proveedor) VALUES
(1, 100, 'Proveedor A'),
(2, 120, 'Proveedor B'),
(3, 150, 'Proveedor C'),
(4, 80, 'Proveedor D'),
(5, 90, 'Proveedor E'),
(6, 200, 'Proveedor F'),
(7, 110, 'Proveedor G'),
(8, 95, 'Proveedor H'),
(9, 130, 'Proveedor I'),
(10, 105, 'Proveedor J'),
(11, 115, 'Proveedor K'),
(12, 140, 'Proveedor L'),
(13, 160, 'Proveedor M'),
(14, 125, 'Proveedor N'),
(15, 175, 'Proveedor O'),
(16, 145, 'Proveedor P'),
(17, 155, 'Proveedor Q'),
(18, 135, 'Proveedor R'),
(19, 85, 'Proveedor S'),
(20, 165, 'Proveedor T'),
(21, 70, 'Proveedor U'),
(22, 185, 'Proveedor V'),
(23, 95, 'Proveedor W'),
(24, 150, 'Proveedor X'),
(25, 190, 'Proveedor Y'),
(26, 120, 'Proveedor Z'),
(27, 175, 'Proveedor AA'),
(28, 80, 'Proveedor BB'),
(29, 140, 'Proveedor CC'),
(30, 105, 'Proveedor DD'),
(31, 115, 'Proveedor EE'),
(32, 155, 'Proveedor FF'),
(33, 135, 'Proveedor GG'),
(34, 100, 'Proveedor HH'),
(35, 95, 'Proveedor II'),
(36, 165, 'Proveedor JJ'),
(37, 130, 'Proveedor KK'),
(38, 120, 'Proveedor LL'),
(39, 90, 'Proveedor MM'),
(40, 85, 'Proveedor NN'),
(41, 200, 'Proveedor OO'),
(42, 145, 'Proveedor PP'),
(43, 115, 'Proveedor QQ'),
(44, 160, 'Proveedor RR'),
(45, 105, 'Proveedor SS'),
(46, 195, 'Proveedor TT'),
(47, 110, 'Proveedor UU'),
(48, 170, 'Proveedor VV'),
(49, 80, 'Proveedor WW'),
(50, 140, 'Proveedor XX');

INSERT INTO clientes (id_cliente, nombre) VALUES
(1, 'Tech Solutions S.A.'),
(2, 'Global Industries Ltd.'),
(3, 'Innovatech Co.'),
(4, 'Constructora Atlas'),
(5, 'Servicios Financieros Omega'),
(6, 'Distribuidora Nova'),
(7, 'Grupo Logística Integral'),
(8, 'Manufacturas Alfa'),
(9, 'Consultoría BetaCorp'),
(10, 'Energía Verde Global');

UPDATE productos SET iva = 0.16 WHERE id_producto = 1;
UPDATE productos SET iva = 0.16 WHERE id_producto = 2;
UPDATE productos SET iva = 0.10 WHERE id_producto = 3;
UPDATE productos SET iva = 0.18 WHERE id_producto = 4;
UPDATE productos SET iva = 0.12 WHERE id_producto = 5;
UPDATE productos SET iva = 0.08 WHERE id_producto = 6;
UPDATE productos SET iva = 0.20 WHERE id_producto = 7;
UPDATE productos SET iva = 0.15 WHERE id_producto = 8;
UPDATE productos SET iva = 0.10 WHERE id_producto = 9;
UPDATE productos SET iva = 0.14 WHERE id_producto = 10;

INSERT INTO clientes (id_cliente, nombre) VALUES
(1, 'Juan Pérez'),
(2, 'María González'),
(3, 'Carlos Sánchez'),
(4, 'Ana López'),
(5, 'Pedro Martínez'),
(6, 'Lucía Torres'),
(7, 'Miguel Ramírez'),
(8, 'Laura Morales'),
(9, 'Jorge Herrera'),
(10, 'Paula Ruiz'),
(11, 'Diego Castro'),
(12, 'Elena Ortega'),
(13, 'Manuel Flores'),
(14, 'Isabel Jiménez'),
(15, 'Ricardo Navarro'),
(16, 'Carmen Vega'),
(17, 'Sergio Romero'),
(18, 'Marta Suárez'),
(19, 'Antonio Reyes'),
(20, 'Nuria Delgado'),
(21, 'Hugo Ramos'),
(22, 'Sara Ferrer'),
(23, 'Daniel Cortés'),
(24, 'Clara León'),
(25, 'Raúl Mendoza'),
(26, 'Victoria Peña'),
(27, 'David Soto'),
(28, 'Eva Molina'),
(29, 'Alberto Arias'),
(30, 'Natalia Cano'),
(31, 'Francisco Paredes'),
(32, 'Sonia Velasco'),
(33, 'Ángel Serrano'),
(34, 'Rosa Carrasco'),
(35, 'Javier Bautista'),
(36, 'Silvia Cuevas'),
(37, 'Luis Vázquez'),
(38, 'Beatriz Méndez'),
(39, 'Andrés Gallardo'),
(40, 'Monica Ríos'),
(41, 'Tomás Salazar'),
(42, 'Alicia Domínguez'),
(43, 'Oscar Gil'),
(44, 'Patricia Álvarez'),
(45, 'Gabriel Nieto'),
(46, 'Lorena Blanco'),
(47, 'Fernando Iglesias'),
(48, 'Berta Calvo'),
(49, 'Ignacio Castaño'),
(50, 'Julia Montes');



