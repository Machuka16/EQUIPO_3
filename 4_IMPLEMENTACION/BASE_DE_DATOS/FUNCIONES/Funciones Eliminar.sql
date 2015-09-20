-----------------------------------------/////////////// Tabla usuarios \\\\\\\\\\\\\\---------------------------------------


create or replace function fn_eliminar_usuario (codigo integer)
returns void as
$body$
	begin
		update usuarios set status = 'f' where codigo_usuario = codigo;
	end
$body$
language plpgsql;



-----------------------------------------/////////////// Tabla categorias_de_productos \\\\\\\\\\\\\\---------------------------------------

create or replace function fn_eliminar_categorias_de_productos (_idCategoria integer)
returns void as
$body$
begin
	update categorias_de_productos set status = 'f' where id_categoria_producto = _idCategoria;
end
$body$
language plpgsql;



-----------------------------------------/////////////// Tabla productos \\\\\\\\\\\\\\---------------------------------------


create or replace function fn_eliminar_productos (_idProducto integer)
returns void as
$body$
begin
	update productos set status = 'f' where codigo_producto = _idProducto; 
end
$body$
language plpgsql;

-----------------------------------------/////////////// Tabla Proveedores \\\\\\\\\\\\\\---------------------------------------

create or replace function fn_eliminar_proveedor(_idProveedor integer)
returns void as
$body$
begin
	update proveedores set status = 'f' where codigo_proveedor = _idProveedor;
	update contactos_proveedores set status = 'f' where codigo_proveedor = _idProveedor;
end
$body$
language plpgsql;

-----------------------------------------/////////////// Tabla Clientes Fisicos \\\\\\\\\\\\\\---------------------------------------

create or replace function fn_eliminar_clienteFisico(id_clienteFisico integer) 
returns void as
$body$
begin
	update clientes_personas_fisicas set status = 'f' where codigo_cliente = id_clienteFisico;
end
$body$
language plpgsql;

--select * from clientes_personas_fisicas
--select fn_eliminar_clienteFisico (36)


-----------------------------------------/////////////// Tabla Clientes Morales \\\\\\\\\\\\\\---------------------------------------
create or replace function fn_eliminar_clienteMorales(id_clienteMorales integer) 
returns void as
$body$
begin
	update clientes_personas_morales set status = 'f' where codigo_cliente = id_clienteMorales;
end
$body$
language plpgsql;

