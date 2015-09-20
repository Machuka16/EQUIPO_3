--------------------//////////////////////////////// Tabla usuarios \\\\\\\\\\\\\\\\\\\\\\\\\\----------------------

create or replace function fn_actualizar_usuarios ( _id integer, _usuario character varying, _contrasenia character varying, _tipo character varying, _nombre character varying, _paterno character varying,
					_materno character varying, _direccion character varying, _telefono character varying, _celular character varying, _correo character varying)
returns void as
$body$
	begin
		update usuarios set 
		usuario = _usuario,
		contrasenia = _contrasenia,
		tipo = _tipo,
		nombre = _nombre,
		apellido_paterno = _paterno,
		apellido_materno = _materno,
		direccion = _direccion,
		telefono = _telefono,
		celular = _celular,
		correo = _correo
		where codigo_usuario = _id;
	end
$body$
language plpgsql;



--------------------//////////////////////////////// Tabla Productos \\\\\\\\\\\\\\\\\\\\\\\\\\----------------------



create or replace function fn_actualizar_productos (_idProducto integer, _idCategoria integer,_clave character varying, _unidad character varying, _descripcion character varying, _precio1 float, _precio2 float,
							_iva float, _actual float, _maxima float, _minima float)
returns void as
$body$
	begin
		update productos
		set id_categoria_producto = _idCategoria,
		clave = _clave,
		unidad_medida = _unidad,
		descripcion = _descripcion,
		precio1 = _precio1,
		precio2 = _precio2,
		porciento_iva = _iva,
		existencia_actual = _actual,
		existencia_maxima = _maxima,
		existencia_minima = _minima
		where codigo_producto = _idProducto;
	end
$body$
language plpgsql;


--------------------//////////////////////////////// Tabla categorias_de_productos \\\\\\\\\\\\\\\\\\\\\\\\\\----------------------



create or replace function fn_actualizar_categoria_de_productos (_idCategoria integer, _categoria character varying)
returns void as
$body$
	begin
		update categorias_de_productos
		set categoria = _categoria
		where id_categoria_producto = _idCategoria;
	end
$body$
language plpgsql;



----------------------------/////////////Tabla Proveedores///////----------------------------------------

create or replace function fn_actualizar_proveedor(id_proveedor integer,c_nombre character,c_apellido_paterno character,c_apellido_materno character,c_direccion character,c_telefono character,c_correo character,
						p_nombre character,p_pais character, p_estado character,p_municipio character,p_calle character,p_n_exterior character,p_n_interior character,
						p_colonia character,p_localidad character,p_codigo_postal integer,p_correo character, p_telefono character,p_celular character,p_otro character) 
returns void as $$ 

begin


	
	update proveedores set nombre = p_nombre,pais = p_pais ,estado = p_estado,municipio = p_municipio,calle = p_calle, 
			       n_exterior = p_n_exterior, n_interior = p_n_interior, colonia = p_colonia, localidad = p_localidad, codigo_postal = p_codigo_postal, 
			       correo = p_correo, telefono = p_telefono,celular = p_celular, otro = p_otro where codigo_proveedor = id_proveedor;
		 
        update contactos_proveedores set nombre = c_nombre,apellido_paterno = c_apellido_paterno,apellido_materno = c_apellido_materno,direccion = c_direccion,
					telefono = c_telefono,correo = c_correo where codigo_proveedor = id_proveedor;
	
        end;
$$ language plpgsql;


----------------------------/////////////Tabla Clientes-Fisicos///////----------------------------------------
--select * from clientes
--select * from clientes_personas_fisicas


create or replace function fn_actualizar_clientesFisicos(id_clienteFisico integer, c_pais character, c_estado character, c_municipio character, c_calle character, c_nExterior character, c_nInterior character,
							c_colonia character, c_localidad character, c_codigoPostal integer, c_correo character, c_telefono character, c_celular character, c_otro character, c_tipo character,
							cf_rfc character, cf_nombre character, cf_aPaterno character, cf_aMaterno character)
returns void as
$body$
begin
	update clientes set pais = c_pais, estado = c_estado, municipio = c_municipio, calle = c_calle, n_exterior = c_nExterior, n_interior = c_nInterior, colonia = c_colonia, 
			localidad = c_localidad, codigo_postal = c_codigoPostal, correo = c_correo, telefono = c_telefono, celular = c_celular, otro = c_otro, tipo = c_tipo 
	where codigo_cliente = id_clienteFisico;

	update clientes_personas_fisicas set rfc = cf_rfc, nombre = cf_nombre, apellido_paterno = cf_aPaterno, apellido_materno = cf_aMaterno
	where codigo_cliente = id_clienteFisico;
end
$body$
language plpgsql;

--select 	fn_actualizar_clientesFisicos (36,'Mexico', 'Veracruz','Cordoba','Azucena','007', 'S/N', 'Sidosa', 'Nogales', 89677, 'enero@mail.com', '7243344', '04427123456', '98*87*7*76', 'Fisico',
					--'HUGI786543PW4', 'Francisco','Rojas', 'Machuca')

----------------------------/////////////Tabla Clientes-Morales///////----------------------------------------
--select * from clientes
--select * from clientes_personas_morales



create or replace function fn_actualizar_clientesMorales(id_clienteMoral integer, c_pais character, c_estado character, c_municipio character, c_calle character, c_nExterior character, c_nInterior character,
							c_colonia character, c_localidad character, c_codigoPostal integer, c_correo character, c_telefono character, c_celular character, c_otro character, c_tipo character,
							cm_rfc character, cm_empresa character, cm_nombre character, cm_aPaterno character, cm_aMaterno character, cm_telefono character, cm_correo character)
returns void as
$body$
begin
	update clientes set pais = c_pais, estado = c_estado, municipio = c_municipio, calle = c_calle, n_exterior = c_nExterior, n_interior = c_nInterior, colonia = c_colonia, 
			localidad = c_localidad, codigo_postal = c_codigoPostal, correo = c_correo, telefono = c_telefono, celular = c_celular, otro = c_otro, tipo = c_tipo 
	where codigo_cliente = id_clienteMoral;

	update clientes_personas_morales set rfc = cm_rfc, empresa = cm_empresa, nombre = cm_nombre, apellido_paterno = cm_aPaterno, apellido_materno = cm_aMaterno, telefono = cm_telefono, correo = cm_correo
	where codigo_cliente = id_clienteMoral;

end
$body$	
language plpgsql;

--select fn_actualizar_clientesMorales (37,'Mexico', 'Veracruz', 'Orizaba', 'claveles', '234', 'h', 'Ojo de agua', 'ojo de agua', 99887, 'marzo@mail.com', '7285679', '04427234598', '98*6*76*5', 'Moral',
					--'HYTR676578HY', 'Omega', 'Oswaldo', 'martinez', 'lopez', '7149988', 'lopez@mail.com')


-------------------------------------------ACTUALIZAR STOCK-------------------------------------------------
create or replace function fn_stock(codigoProducto integer, existenciaActual float, _causa character)
returns void as
$body$
begin
	update productos set existencia_actual = existenciaActual, causa = _causa where codigo_producto = codigoProducto;
end
$body$
language plpgsql;

