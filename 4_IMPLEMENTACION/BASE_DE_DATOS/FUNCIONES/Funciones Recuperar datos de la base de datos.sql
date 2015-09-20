create or replace function recuperar_valores()
returns table(codigo_proveedor smallint,
		nombre character varying, 
		pais character varying,
		estado character varying,
		municipio character varying,
		calle character varying,
		n_exterior character varying,
		n_interior character varying,
		colonia character varying,
		localidad character varying,
		codigo_postal integer,
		correo character varying,
		telefono character varying,
		celular character varying,
		otro character varying,
		c_nombre character varying,
		c_apellido_paterno character varying,
		c_apellido_materno character varying,
		c_direccion character varying,
		c_telefono character varying,
		c_correo character varying)
 as
$faltal$
begin
	return query
		with proveedor_y_contactos as
		(select status as statuss from contactos_proveedores)
		
		select  proveedores.codigo_proveedor,proveedores.nombre, proveedores.pais,proveedores.estado,proveedores.municipio,
		proveedores.calle,proveedores.n_exterior,proveedores.n_interior,proveedores.colonia,proveedores.localidad,
		proveedores.codigo_postal,proveedores.correo,proveedores.telefono,proveedores.celular,proveedores.otro ,
		contactos_proveedores.nombre as c_nombre,contactos_proveedores.apellido_paterno as c_apellido_paterno,
		contactos_proveedores.apellido_materno as c_apellido_materno,contactos_proveedores.direccion as c_direccion,
		contactos_proveedores.telefono as c_telefono,contactos_proveedores.correo as c_correo 
		from proveedores inner join 
		contactos_proveedores on proveedores.codigo_proveedor = contactos_proveedores.codigo_proveedor where proveedores.status
		= TRUE and contactos_proveedores.status = TRUE ;
		end
		$faltal$ language plpgsql;




create or replace function recuperar_usuarios()
returns table(codigo_usuario smallint, 
	      usuario character varying, 
	      contrasenia character varying, 
	      tipo character varying, 
	      nombre character varying, 
	      completo text, 
	      apellido_paterno character varying, 
	      apellido_materno character varying, 
	      direccion character varying, 
	      telefono character varying, 
	      celular character varying, 
	      correo character varying)
 as
$faltal$
begin
	return query
		with recuperar_usuarios as
		(select u.status from usuarios u )
		
		select 
		 usuarios.codigo_usuario,
		 usuarios.usuario, 
		 usuarios.contrasenia, 
		 usuarios.tipo,
		 usuarios.nombre, 
		 concat(usuarios.nombre, ' ', usuarios.apellido_paterno, ' ', usuarios.apellido_materno) as completo, 
		 usuarios.apellido_paterno,
		 usuarios.apellido_materno, 
		 usuarios.direccion, 
		 usuarios.telefono,
		 usuarios.celular, 
		 usuarios.correo 
		from usuarios where usuarios.status = TRUE;
		
		end
		$faltal$ language plpgsql;

 
--select * from recuperar_usuarios();

-------------------------------------------------------------------------

--select * from recuperar_clientesFisicos()

create or replace function recuperar_clientesFisicos ()
returns table (codigoFisico smallint,
		rfc character varying,		
		nombre character varying,
		apaterno character varying,
		amaterno character varying,
		nombreCompleto text,
		pais character varying,
		estado character varying,
		municipio character varying,
		calle character varying,
		nExterior character varying,
		nInterior character varying,
		colonia character varying,
		localidad character varying,
		codPostal integer,
		direccionCompleta text,			
		telefono character varying,
		correo character varying,
		celular character varying,
		otro character varying,
		tipo character varying)
as
$body$
begin
	return query
	with recuperar_clientesFisicos as (select cf.status from clientes_personas_fisicas cf)
		select
		c.codigo_cliente, 
		cf.rfc,		
		cf.nombre,
		cf.apellido_paterno,
		cf.apellido_materno,
		concat(cf.nombre, ' ', cf.apellido_paterno, ' ', cf.apellido_materno) as nombreCompleto,
		c.pais,
		c.estado,
		c.municipio,
		c.calle,
		c.n_exterior,
		c.n_interior,
		c.colonia,
		c.localidad,
		c.codigo_postal,
		concat(c.pais, ' ', c.estado, ' ', c.municipio, ' ', c.calle, ' ',c.n_exterior, '-',c.n_interior, ' ', c.colonia, ' ', c.codigo_postal) as direccionCompleta,
		c.telefono,
		c.correo,
		c.celular,
		c.otro,
		c.tipo
		from clientes c inner join clientes_personas_fisicas cf on c.codigo_cliente = cf.codigo_cliente
		where cf.status = TRUE;
end	
$body$
language plpgsql;


---------------------------------------------------------------------MORALES------------------------


create or replace function recuperar_clientesMorales ()
returns table (codigoMoral smallint,
		rfc character varying,
		empresa character varying,
		nombre character varying,
		apaterno character varying,
		amaterno character varying,
		telefonoContacto character varying,
		correoContacto character varying,
		pais character varying,
		estado character varying,
		municipio character varying,
		calle character varying,
		nExterior character varying,
		nInterior character varying,
		colonia character varying,
		localidad character varying,
		codPostal integer,
		direccionCompleta text,		
		telefono character varying,
		correo character varying,
		celular character varying,
		otro character varying,
		tipo character varying)
as
$body$
begin
	return query
	with recuperar_clientesMorales as (select cm.status from clientes_personas_morales cm)
		select
		c.codigo_cliente,
		cm.rfc,
		cm.empresa,
		cm.nombre,
		cm.apellido_paterno,
		cm.apellido_materno,
		cm.telefono,
		cm.correo,
		c.pais,
		c.estado,
		c.municipio,
		c.calle,
		c.n_exterior,
		c.n_interior,
		c.colonia,
		c.localidad,
		c.codigo_postal,
		concat(c.pais, ' ', c.estado, ' ', c.municipio, ' ', c.calle, ' ',c.n_exterior, '-',c.n_interior, ' ', c.colonia, ' ', c.codigo_postal) as direccionCompleta,
		c.telefono,		
		c.correo,
		c.celular,
		c.otro,
		c.tipo
		from clientes c inner join clientes_personas_morales cm on c.codigo_cliente = cm.codigo_cliente
		where cm.status = TRUE;
end	
$body$
language plpgsql;

create or replace function fn_clientes()
returns table(codigoCliente smallint, rfc character varying, cliente text, direccion text, correo character varying,
		telefono character varying, celular character varying, otro character varying, tipo character varying)
as $body$
begin	
	return query
	with fn_clientes as (select cm.status, cf.status from clientes_personas_morales cm, clientes_personas_fisicas cf)
	select c.codigo_cliente, cf.rfc, concat(cf.nombre, ' ', cf.apellido_paterno, ' ', cf.apellido_materno) as nombreCombo, concat(c.pais, ' ', c.estado, ' ', c.municipio, ' ', c.calle, ' ',c.n_exterior, '-',c.n_interior, ' ', c.colonia, ' ', c.codigo_postal) as direccionCompleta, c.correo, c.telefono, c.celular, c.otro, c.tipo  from clientes_personas_fisicas cf 
		inner join clientes c on c.codigo_cliente = cf.codigo_cliente where cf.status = TRUE 
	union
	select c.codigo_cliente, cm.rfc, cm.empresa, concat(c.pais, ' ', c.estado, ' ', c.municipio, ' ', c.calle, ' ',c.n_exterior, '-',c.n_interior, ' ', c.colonia, ' ', c.codigo_postal) as direccionCompleta, c.correo, c.telefono, c.celular, c.otro, c.tipo  from clientes_personas_morales cm 
		inner join clientes c on c.codigo_cliente = cm.codigo_cliente where cm.status = TRUE ;
end;
$body$
language plpgsql;

select c.codigo_cliente, cf.rfc, concat(cf.nombre, ' ', cf.apellido_paterno, ' ', cf.apellido_materno) as nombreCombo, concat(c.pais, ' ', c.estado, ' ', c.municipio, ' ', c.calle, ' ',c.n_exterior, '-',c.n_interior, ' ', c.colonia, ' ', c.codigo_postal) as direccionCompleta, c.correo, c.telefono, c.celular, c.otro, c.tipo  from clientes_personas_fisicas cf 
		inner join clientes c on c.codigo_cliente = cf.codigo_cliente where cf.status = TRUE order by nombreCombo asc 

--select * from fn_clientes()

---------------------------------------------
create or replace function recuperar_valores_inactivos()
returns table(codigo_proveedor smallint,
		nombre character varying, 
		pais character varying,
		estado character varying,
		municipio character varying,
		calle character varying,
		n_exterior character varying,
		n_interior character varying,
		colonia character varying,
		localidad character varying,
		codigo_postal integer,
		correo character varying,
		telefono character varying,
		celular character varying,
		otro character varying,
		c_nombre character varying,
		c_apellido_paterno character varying,
		c_apellido_materno character varying,
		c_direccion character varying,
		c_telefono character varying,
		c_correo character varying)
 as
$faltal$
begin
	return query
		with proveedor_y_contactos as
		(select status as statuss from contactos_proveedores)
		
		select  proveedores.codigo_proveedor,proveedores.nombre, proveedores.pais,proveedores.estado,proveedores.municipio,
		proveedores.calle,proveedores.n_exterior,proveedores.n_interior,proveedores.colonia,proveedores.localidad,
		proveedores.codigo_postal,proveedores.correo,proveedores.telefono,proveedores.celular,proveedores.otro ,
		contactos_proveedores.nombre as c_nombre,contactos_proveedores.apellido_paterno as c_apellido_paterno,
		contactos_proveedores.apellido_materno as c_apellido_materno,contactos_proveedores.direccion as c_direccion,
		contactos_proveedores.telefono as c_telefono,contactos_proveedores.correo as c_correo 
		from proveedores inner join 
		contactos_proveedores on proveedores.codigo_proveedor = contactos_proveedores.codigo_proveedor where proveedores.status
		= FALSE and contactos_proveedores.status = FALSE ;
		end
		$faltal$ language plpgsql;




