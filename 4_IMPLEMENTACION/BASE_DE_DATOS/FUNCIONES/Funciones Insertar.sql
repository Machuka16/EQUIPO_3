-----------------------------------------/** Funcion Insertar Categorias_de_Productos **\---------------------------------



create or replace function fn_agregar_categorias_de_productos (nombre text) returns void as $$
declare maximo smallint;
declare total smallint;
begin
	select count(id_categoria_producto) into total from categorias_de_productos;
	if total = 0 then maximo:= 1;
		else	
		select (max(id_categoria_producto)+ 1) into maximo from categorias_de_productos ;
	end if;
	insert into categorias_de_productos (id_categoria_producto, categoria) values (maximo, nombre);
end;

$$ language plpgsql;




----------------------------------------------/** Funcion Insertar Productos **\---------------------------------


create or replace function fn_agregar_productos(categoria integer, medida character varying, des character varying, uno float, dos float, iva float,
			actual float, maxima float, minima float) returns void as $$
declare maximo integer;
declare total integer;
	begin
		select count(codigo_producto) into total from productos;
		if total = 0 then maximo:= 1;
			else
			select (max(codigo_producto)+ 1) into maximo from productos;
		end if;
		insert into productos (codigo_producto, id_categoria_producto, unidad_medida, descripcion, precio1, precio2, porciento_iva, existencia_actual, existencia_maxima, existencia_minima)
		values (maximo,  categoria, medida, des, uno, dos, iva, actual, maxima, minima);
	end
$$ language plpgsql;



----------------------------------------------/** Funcion Insertar Usuarios **\---------------------------------
    

create or replace function fn_agregar_usuario(usser character varying, pass character varying, tipo_fn character varying, nombre_fn character varying, ap_Pa character varying,
					ap_Ma character varying, direc character varying, tel character varying, cel character varying, email character varying) returns void as $$
declare maximo smallint;
declare total smallint;
begin
	select count(codigo_usuario) into total from usuarios;
		if total = 0 then maximo:= 1;
			else
			select (max(codigo_usuario)+ 1) into maximo from usuarios;
		end if;
	insert into usuarios (codigo_usuario, usuario, contrasenia, tipo, nombre, apellido_paterno, apellido_materno, direccion, telefono, celular, correo)
	values (maximo, usser, pass, tipo_fn, nombre_fn, ap_Pa, ap_Ma, direc, tel, cel, email);
end;
$$ language plpgsql;




----------------------------------------------/** Funcion Insertar Proveedor y Contacto-Proveedor **\---------------------------------
create or replace function fn_agregar_proveedor(c_nombre character,c_apellido_paterno character,c_apellido_materno character,c_direccion character,c_telefono character,c_correo character,
						p_nombre character,p_pais character, p_estado character,p_municipio character,p_calle character,p_n_exterior character, p_n_interior character,
						p_colonia character,p_localidad character,p_codigo_postal integer,p_correo character, p_telefono character,p_celular character,p_otro character) 
returns void as $$ 
declare maximo_contacto smallint;
declare total_contacto smallint;
declare maximo_proveedor smallint;
declare total_proveedor smallint;

begin select count(id_contacto) into total_contacto from contactos_proveedores; 
	if total_contacto = 0 then maximo_contacto:= 1;
	else select(max(id_contacto) +1) into maximo_contacto from contactos_proveedores; 
	end if;

	select count(codigo_proveedor) into total_proveedor from proveedores; 
	if total_proveedor = 0 then maximo_proveedor:= 1;
	else select(max(codigo_proveedor) +1) into maximo_proveedor from proveedores; 

	end if;
	

	
	insert into proveedores (codigo_proveedor,nombre,pais,estado,municipio,calle,n_exterior,n_interior,colonia,localidad,codigo_postal,correo,telefono,celular,otro) 
	
				    values(maximo_proveedor,p_nombre,p_pais,p_estado,p_municipio,p_calle,p_n_exterior,p_n_interior,
						p_colonia,p_localidad,p_codigo_postal,p_correo,p_telefono,p_celular,p_otro); 
	
	 
        insert into contactos_proveedores (id_contacto,codigo_proveedor,nombre,apellido_paterno,apellido_materno,direccion,telefono,correo) 
				    values(maximo_contacto,maximo_proveedor,c_nombre,c_apellido_paterno,c_apellido_materno,c_direccion,c_telefono,c_correo);
	
        end;
$$ language plpgsql;





/**********************************************************************************************************************************************************************/
/*****************************************************************Insertar clientes fisicos****************************************************************************/
/**********************************************************************************************************************************************************************/

create or replace function fn_agregar_cliente_fisico(pais character varying,estado character varying, municipio character varying, calle character varying, n_exterior character varying, 
							n_interior character varying, colonia character varying, localidad character varying, codigo_postal integer, correo character varying, 
							telefono character varying, celular character varying, otro character varying,rfc character varying, nombre character varying,
							apellido_paterno character varying,apellido_materno character varying , tipos character varying) 
returns void as $$ 

declare total smallint;
declare maximo smallint;
declare totalf smallint;
declare maximof smallint;

begin select count(codigo_cliente) into total from clientes;
if total= 0 then maximo:=1;
else select(max(codigo_cliente)+1) into maximo from clientes ;
end if;

select count(id_cliente_fisico) into totalf from clientes_personas_fisicas;
if totalf= 0 then maximof:=1;
else select (max(id_cliente_fisico)+1) into maximof from clientes_personas_fisicas ;
end if;

insert into clientes (codigo_cliente, pais, estado, municipio, calle, n_exterior, n_interior, colonia, localidad, codigo_postal, correo, telefono, celular, otro,tipo) 
values (maximo , pais,estado, municipio, calle, n_exterior, n_interior, colonia, localidad, codigo_postal, correo, telefono, celular, otro,tipos);

insert into clientes_personas_fisicas (id_cliente_fisico, codigo_cliente, rfc, nombre, apellido_paterno, apellido_materno) 
values (maximof,maximo, rfc, nombre, apellido_paterno, apellido_materno) ;

end;
$$ language plpgsql;

--select fn_agregar_cliente_fisico('Mexico','Veracruz', 'Cordoba', 'Calle 5', '516', '4', 'El carmen', 'Cordoba', 94580, 'paco_jmr_15@hotmail.com', '7126649', '2711206217', '12*134*12', 
				--'ROMF931216DE5' , 'Francisco Javier', 'Machuca', 'Rojas','Fisico');


/**********************************************************************************************************************************************************************/
/***********************************************************Agregar clientes morales***********************************************************************************/
/**********************************************************************************************************************************************************************/

create or replace function fn_agregar_cliente_moral(pais character varying,estado character varying, municipio character varying, calle character varying, n_exterior character varying, 
							n_interior character varying, colonia character varying, localidad character varying, codigo_postal integer, correo character varying, 
							telefono character varying, celular character varying, otro character varying,tipos character varying,
							rfc character varying, empresa character varying,
							nombre character varying, apellido_paterno character varying, apellido_materno character varying, telefono_c character varying, correo_c character varying) 
returns void as $$ 

declare total smallint;
declare maximo smallint;
declare totalm smallint;
declare maximom smallint;


begin 
select count(codigo_cliente) into total from clientes;
if total= 0 then maximo:=1;
else select(max(codigo_cliente)+1) into maximo from clientes ;
end if;

select count(id_cliente_moral) into totalm from clientes_personas_morales;
if totalm= 0 then maximom:=1;
else select (max(id_cliente_moral)+1) into maximom from clientes_personas_morales ;
end if;

insert into clientes (codigo_cliente, pais, estado, municipio, calle, n_exterior, n_interior, colonia, localidad, codigo_postal, correo, telefono, celular, otro,tipo) 
values (maximo , pais,estado, municipio, calle, n_exterior, n_interior, colonia, localidad, codigo_postal, correo, telefono, celular, otro,tipos);

insert into clientes_personas_morales (id_cliente_moral, codigo_cliente, rfc, empresa, nombre, apellido_paterno, apellido_materno, telefono, correo) 
values (maximom,maximo, rfc, empresa,nombre, apellido_paterno, apellido_materno, telefono_c, correo_c);

end;

$$ language plpgsql;

--select fn_agregar_cliente_moral('Mexico','Veracruz', 'Cordoba', 'Calle 5', '5', '3', 'Dorado', 'Cordoba', 94500, 'Kinser@hotmail.com', '7126649', '2711206217', '12*134*12', 'Moral',
				--'LAFV931216DE','Imaginary',
				--'Luis Angel', 'Fernandez', 'Vazquez', '2711234567', 'thekinser@gmail.com');		

