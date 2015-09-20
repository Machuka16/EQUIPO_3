--###### CREAR TABLA CLIENTES
CREATE TABLE Clientes(
codigo_cliente SMALLINT NOT NULL PRIMARY KEY,
pais VARCHAR NOT NULL,
estado VARCHAR NOT NULL,
municipio VARCHAR NOT NULL,
calle VARCHAR NOT NULL,
n_exterior VARCHAR  NULL DEFAULT'S/N',
n_interior VARCHAR  NULL DEFAULT'S/N',
colonia VARCHAR NOT NULL ,
localidad VARCHAR NOT NULL,
codigo_postal INTEGER NOT NULL,
correo VARCHAR NOT NULL,
telefono VARCHAR NOT NULL DEFAULT'00-000-00-000-00',
celular VARCHAR NOT NULL DEFAULT'000-000-000-00-00', 
otro VARCHAR NOT NULL DEFAULT'00-000-00-000-00',
tipo VARCHAR NOT NULL,
UNIQUE(correo)
);

--###### CREAR TABLA MORAL_CLIENTE
CREATE TABLE Clientes_Personas_Morales(
id_cliente_moral SMALLINT NOT NULL PRIMARY KEY,
codigo_cliente SMALLINT NOT NULL,
rfc VARCHAR NOT NULL ,
empresa VARCHAR NOT NULL,
nombre VARCHAR NOT NULL,
apellido_paterno VARCHAR NOT NULL,
apellido_materno VARCHAR NOT NULL,
telefono VARCHAR NOT NULL DEFAULT'00-000-00-000-00',
correo VARCHAR NOT NULL,
status BOOLEAN NOT NULL DEFAULT 't',
UNIQUE(empresa)
);


--###### CREAR TABLA FISICO
CREATE TABLE Clientes_Personas_Fisicas(
id_cliente_fisico SMALLINT NOT NULL PRIMARY KEY,
codigo_cliente SMALLINT NOT NULL,
rfc VARCHAR NOT NULL ,
nombre VARCHAR NOT NULL,
apellido_paterno VARCHAR NOT NULL,
apellido_materno VARCHAR NOT NULL,
status BOOLEAN NOT NULL DEFAULT 't'
);

--###### CREAR TABLA PROVEEDORES
CREATE TABLE Proveedores(
codigo_proveedor SMALLINT PRIMARY KEY NOT NULL,
nombre VARCHAR NOT NULL,
pais VARCHAR NOT NULL,
estado VARCHAR NOT NULL,
municipio VARCHAR NOT NULL,
calle VARCHAR NOT NULL,
n_exterior VARCHAR NULL DEFAULT'S/N',
n_interior VARCHAR NULL DEFAULT'S/N',
colonia VARCHAR NOT NULL,
localidad VARCHAR NOT NULL,
codigo_postal INTEGER NOT NULL,
correo VARCHAR NOT NULL,
telefono VARCHAR NOT NULL DEFAULT '00-000-00-000-00',
celular VARCHAR NOT NULL DEFAULT '000-000-000-00-00',
otro VARCHAR NOT NULL DEFAULT '000-000-000-00-00',
status BOOLEAN NOT NULL DEFAULT 't'
);

--###### CREAR TABLA COMPRAS
CREATE TABLE Compras(
folio serial PRIMARY KEY NOT NULL,
codigo_proveedor SMALLINT NOT NULL,
fecha_y_hora TIMESTAMP WITH TIME ZONE NOT NULL
);

--###### CREAR TABLA Detalles_de_Compras
CREATE TABLE Detalles_de_Compras(
codigo_compra serial PRIMARY KEY NOT NULL,
folio INTEGER NOT NULL,
codigo_producto SMALLINT NOT NULL,
cantidad DOUBLE PRECISION NOT NULL,
costo_unitario NUMERIC (8,2) NOT NULL,
porcentaje_precio NUMERIC (8,2) NOT NULL,
iva_unitario NUMERIC (8,2) NOT NULL
);

--###### CREAR TABLA CONTACTO_PROVEEDOR
CREATE TABLE Contactos_Proveedores(
id_contacto SMALLINT NOT NULL PRIMARY KEY,
codigo_proveedor SMALLINT NOT NULL,
nombre VARCHAR NOT NULL,
apellido_paterno VARCHAR NOT NULL,
apellido_materno VARCHAR NOT NULL,
direccion VARCHAR NOT NULL,
telefono VARCHAR NOT NULL DEFAULT'00-000-00-000-00',
correo VARCHAR NOT NULL,
status BOOLEAN NOT NULL DEFAULT 't',
UNIQUE(correo)
);

--###### CREAR TABLA PRODUCTOS
CREATE TABLE Productos(
codigo_producto SMALLINT PRIMARY KEY NOT NULL,
id_categoria_producto SMALLINT NOT NULL,
clave VARCHAR NOT NULL,
unidad_medida VARCHAR NOT NULL,
descripcion VARCHAR NOT NULL,
precio1 NUMERIC (8,2) NOT NULL,
precio2 NUMERIC (8,2) NOT NULL,
porciento_iva NUMERIC (8,2) NOT NULL,
existencia_actual DOUBLE PRECISION NOT NULL,
existencia_maxima DOUBLE PRECISION NOT NULL,
existencia_minima DOUBLE PRECISION NOT NULL,
causa VARCHAR DEFAULT 'N/A',
status BOOLEAN NOT NULL DEFAULT 't',
UNIQUE(clave)
);

--###### CREAR TABLA CATEGORIA_PRODUCTO
CREATE TABLE Categorias_de_Productos(
id_Categoria_Producto SMALLINT PRIMARY KEY NOT NULL,
categoria VARCHAR NOT NULL,
status BOOLEAN NOT NULL DEFAULT 't'
);

--###### CREAR TABLA VENTAS
CREATE TABLE Ventas(
folio serial PRIMARY KEY NOT NULL,
codigo_cliente SMALLINT NOT NULL,
codigo_usuario SMALLINT NOT NULL, 
fecha_y_hora TIMESTAMP WITH TIME ZONE NOT NULL
);

--###### CREAR TABLA DETALLE_DE_VENTA
CREATE TABLE Detalles_de_Ventas(
codigo_detalle serial PRIMARY KEY NOT NULL,
folio INTEGER NOT NULL,
codigo_producto SMALLINT NOT NULL,
cantidad DOUBLE PRECISION NOT NULL,
precio_unitario NUMERIC (8,2) NOT NULL,
iva_unitario NUMERIC (8,2) NOT NULL
);

--###### CREAR TABLA USUARIOS
CREATE TABLE Usuarios(
codigo_usuario SMALLINT PRIMARY KEY NOT NULL,
usuario VARCHAR NOT NULL,
contrasenia VARCHAR NOT NULL,
tipo VARCHAR NOT NULL,
nombre VARCHAR NOT NULL,
apellido_paterno VARCHAR NOT NULL,
apellido_materno VARCHAR NOT NULL,
direccion VARCHAR NOT NULL,
telefono VARCHAR NOT NULL DEFAULT '00-000-00-000-00',
celular VARCHAR NOT NULL DEFAULT '000-000-000-00-00',
correo VARCHAR NOT NULL,
status BOOLEAN NOT NULL DEFAULT 't',
UNIQUE (correo, usuario)
);




------############# CONSTRAINT DE LAS LLAVES FORANEAS #############------
ALTER TABLE Contactos_Proveedores 	 ADD CONSTRAINT fk_Contactos_Proveedores_Proveedor FOREIGN KEY (codigo_proveedor)      REFERENCES proveedores (codigo_proveedor);

ALTER TABLE Compras 			 ADD CONSTRAINT fk_compras			   FOREIGN KEY (codigo_proveedor)      REFERENCES proveedores (codigo_proveedor);

ALTER TABLE Detalles_de_Compras        	 ADD CONSTRAINT fk_folio 			   FOREIGN KEY (folio) 		       REFERENCES Compras (folio);

ALTER TABLE Detalles_de_Compras 	 ADD CONSTRAINT fk_codigo_producto		   FOREIGN KEY (codigo_producto)       REFERENCES Productos (codigo_producto);

ALTER TABLE Productos 			 ADD CONSTRAINT fk_id_categoria 		   FOREIGN KEY (id_categoria_producto) REFERENCES Categorias_de_Productos (id_categoria_producto);

ALTER TABLE Detalles_de_Ventas		 ADD CONSTRAINT fk_folio 			   FOREIGN KEY (folio) 		       REFERENCES  Ventas (folio);

ALTER TABLE Detalles_de_Ventas		 ADD CONSTRAINT fk_codigo_producto		   FOREIGN KEY (codigo_producto)       REFERENCES  Productos (codigo_producto);

ALTER TABLE Ventas 			 ADD CONSTRAINT fk_codigo_usuario 		   FOREIGN KEY (codigo_usuario)        REFERENCES  Usuarios (codigo_usuario);

ALTER TABLE Ventas 			 ADD CONSTRAINT fk_codigo_cliente		   FOREIGN KEY (codigo_cliente)        REFERENCES  Clientes (codigo_cliente);

ALTER TABLE Clientes_Personas_Morales 	 ADD CONSTRAINT fk_Clientes_Morales		   FOREIGN KEY (codigo_cliente)        REFERENCES Clientes (codigo_cliente);

ALTER TABLE Clientes_Personas_Fisicas	 ADD CONSTRAINT fk_Clientes_Fisicos		   FOREIGN KEY (codigo_cliente)        REFERENCES Clientes (codigo_cliente);

	



------############# CHECKS REGLAS DE NEGOCIO #############------

-----Tabla Clientes
ALTER TABLE Clientes ADD CHECK (codigo_cliente > 0);
ALTER TABLE Clientes ADD CHECK (LENGTH(LTRIM(RTRIM(pais)))>3);
ALTER TABLE Clientes ADD CHECK (estado IN ('Aguascalientes', 'Baja california norte', 'Baja california sur', 'Campeche', 'Coahuila', 'Chiapas',
					   'Chihuahua', 'Durango', 'Mexico df', 'Guanajuato', 'Guerrero', 'Hidalgo', 'Jalisco', 'Michoacan', 
					   'Morelos', 'Mexico,df', 'Nayarit', 'Nuevo leon','Oaxaca', 'Puebla', 'Queretaro', 'Quintana roo', 
					   'San luis potosi', 'Sinaloa', 'Sonora', 'Tabasco', 'Tamaulipas', 'Tlaxcala', 'Veracruz', 'Yucatan', 'Zacatecas'));

ALTER TABLE Clientes ADD CHECK (LENGTH(LTRIM(RTRIM(municipio)))>3);
ALTER TABLE Clientes ADD CHECK (LENGTH(LTRIM(RTRIM(calle)))=>1);
ALTER TABLE Clientes ADD CHECK (LENGTH(LTRIM(RTRIM(colonia)))>3);
ALTER TABLE Clientes ADD CHECK (LENGTH(LTRIM(RTRIM(localidad)))>3);
ALTER TABLE Clientes ADD CHECK (LENGTH(LTRIM(RTRIM(correo)))>3);
ALTER TABLE Clientes ADD CHECK (correo LIKE '%@%');


-----Tabla Clientes_Morales
ALTER TABLE Clientes_Personas_Morales ADD CHECK (id_cliente_moral > 0);
ALTER TABLE Clientes_Personas_Morales ADD CHECK (LENGTH(LTRIM(RTRIM(empresa)))>3);
 
ALTER TABLE Clientes_Personas_Morales ADD CHECK ((length(rtrim(rfc)) - length(trim(rfc))) = 1 and length(trim(rfc)) =12)
ALTER TABLE Clientes_Personas_Morales ADD CHECK (LENGTH(LTRIM(RTRIM(nombre)))>2);
ALTER TABLE Clientes_Personas_Morales ADD CHECK (LENGTH(LTRIM(RTRIM(apellido_paterno)))>3);
ALTER TABLE Clientes_Personas_Morales ADD CHECK (LENGTH(LTRIM(RTRIM(apellido_materno)))>3);
ALTER TABLE Clientes_Personas_Morales ADD CHECK (LENGTH(LTRIM(RTRIM(telefono)))>3);
ALTER TABLE Clientes_Personas_Morales ADD CHECK (LENGTH(LTRIM(RTRIM(correo)))>3);
ALTER TABLE Clientes_Personas_Morales ADD CHECK (correo LIKE '%@%');


-----Tabla Clientes_Fisicos
ALTER TABLE Clientes_Personas_Fisicas ADD CHECK (id_cliente_fisico > 0);
ALTER TABLE Clientes_Personas_Fisicas ADD CHECK (LENGTH(LTRIM(RTRIM(rfc))) = 13);
ALTER TABLE Clientes_Personas_Fisicas ADD CHECK (LENGTH(LTRIM(RTRIM(nombre)))>2);
ALTER TABLE Clientes_Personas_Fisicas ADD CHECK (LENGTH(LTRIM(RTRIM(apellido_paterno)))>3);
ALTER TABLE Clientes_Personas_Fisicas ADD CHECK (LENGTH(LTRIM(RTRIM(apellido_materno)))>3);


-----Tabla Proveedores
ALTER TABLE Proveedores ADD CHECK (codigo_proveedor > 0);
ALTER TABLE Proveedores ADD CHECK (LENGTH(LTRIM(RTRIM(nombre)))>3);
ALTER TABLE Proveedores ADD CHECK (LENGTH(LTRIM(RTRIM(pais)))>3);
ALTER TABLE Proveedores ADD CHECK (estado IN ('Aguascalientes', 'Baja california norte', 'Baja california sur', 'Campeche', 'Coahuila', 'Chiapas',
					   'Chihuahua', 'Durango', 'Mexico df', 'Guanajuato', 'Guerrero', 'Hidalgo', 'Jalisco', 'Michoacan', 
					   'Morelos', 'Mexico,df', 'Nayarit', 'Nuevo leon','Oaxaca', 'Puebla', 'Queretaro', 'Quintana roo', 
					   'San luis potosi', 'Sinaloa', 'Sonora', 'Tabasco', 'Tamaulipas', 'Tlaxcala', 'Veracruz', 'Yucatan', 'Zacatecas'));	 
ALTER TABLE Proveedores ADD CHECK (LENGTH(LTRIM(RTRIM(municipio)))>3);
ALTER TABLE Proveedores ADD CHECK (LENGTH(LTRIM(RTRIM(calle)))>3);
ALTER TABLE Proveedores ADD CHECK (LENGTH(LTRIM(RTRIM(colonia)))>3);
ALTER TABLE Proveedores ADD CHECK (LENGTH(LTRIM(RTRIM(localidad)))>3);
ALTER TABLE Proveedores ADD CHECK (LENGTH(LTRIM(RTRIM(correo)))>3);
ALTER TABLE Proveedores ADD CHECK (correo LIKE '%@%');



-----Tabla Compras
ALTER TABLE Compras ADD CHECK (folio > 0);


-----Tabla Detalles_de_Compras
ALTER TABLE Detalles_de_Compras ADD CHECK (codigo_compra > 0);
ALTER TABLE Detalles_de_Compras ADD CHECK (cantidad > 0);


-----Tabla Contactos_Proveedores
ALTER TABLE Contactos_Proveedores ADD CHECK (id_contacto > 0);
ALTER TABLE Contactos_Proveedores ADD CHECK (LENGTH(LTRIM(RTRIM(nombre)))>2);
ALTER TABLE Contactos_Proveedores ADD CHECK (LENGTH(LTRIM(RTRIM(apellido_paterno)))>3);
ALTER TABLE Contactos_Proveedores ADD CHECK (LENGTH(LTRIM(RTRIM(apellido_materno)))>3);
ALTER TABLE Contactos_Proveedores ADD CHECK (LENGTH(LTRIM(RTRIM(direccion)))>3);
ALTER TABLE Contactos_Proveedores ADD CHECK (LENGTH(LTRIM(RTRIM(correo)))>3);
ALTER TABLE Contactos_Proveedores ADD CHECK (correo LIKE '%@%');


-----Tabla Productos
ALTER TABLE Productos ADD CHECK (codigo_producto > 0);
ALTER TABLE Productos ADD CHECK (LENGTH(LTRIM(RTRIM(unidad_medida)))> 0);
ALTER TABLE Productos ADD CHECK (LENGTH(LTRIM(RTRIM(descripcion)))>3);
ALTER TABLE Productos ADD CHECK (precio1 > precio2);
ALTER TABLE Productos ADD CHECK (existencia_maxima > existencia_minima);
ALTER TABLE Productos ADD CHECK (existencia_actual < existencia_maxima);
ALTER TABLE Productos ADD CHECK (existencia_actual > existencia_minima);
ALTER TABLE Productos ADD CHECK (existencia_maxima > 0);
ALTER TABLE Productos ADD CHECK (existencia_minima > 0);
ALTER TABLE Productos ADD CHECK (existencia_actual > 0);


-----Tabla Categorias_de_Productos
ALTER TABLE Categorias_de_Productos ADD CHECK (id_categoria_producto > 0);
ALTER TABLE Categorias_de_Productos ADD CHECK (LENGTH(LTRIM(RTRIM(categoria)))>3);


-----Tabla Ventas
ALTER TABLE Ventas ADD CHECK (folio > 0);

-----Tabla Detalles_de_Ventas
ALTER TABLE Detalles_de_Ventas ADD CHECK (codigo_detalle > 0);
ALTER TABLE Detalles_de_Ventas ADD CHECK (cantidad > 0);

-----Tabla Usuarios
ALTER TABLE Usuarios ADD CHECK (codigo_usuario > 0);
ALTER TABLE Usuarios ADD CHECK (LENGTH(LTRIM(RTRIM(usuario)))>3);
ALTER TABLE Usuarios ADD CHECK (LENGTH(LTRIM(RTRIM(contrasenia)))>3);
ALTER TABLE Usuarios ADD CHECK (LENGTH(LTRIM(RTRIM(tipo)))>3);
ALTER TABLE Usuarios ADD CHECK (LENGTH(LTRIM(RTRIM(nombre)))>2);
ALTER TABLE Usuarios ADD CHECK (LENGTH(LTRIM(RTRIM(apellido_paterno)))>3);
ALTER TABLE Usuarios ADD CHECK (LENGTH(LTRIM(RTRIM(apellido_materno)))>3);
ALTER TABLE Usuarios ADD CHECK (LENGTH(LTRIM(RTRIM(direccion)))>3);
ALTER TABLE Usuarios ADD CHECK (LENGTH(LTRIM(RTRIM(correo)))>3);
ALTER TABLE Usuarios ADD CHECK (correo LIKE '%@%');