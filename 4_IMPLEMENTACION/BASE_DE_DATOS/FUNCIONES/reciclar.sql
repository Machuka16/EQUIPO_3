
----------------------------------------------------------Restaurar Reciclaje Productos-----------------------------------------------------
--select * from productos;

create or replace function fn_restaurar_productos (_idProducto integer)
returns void as
$body$
begin
	update productos set status = 't' where codigo_producto = _idProducto; 
end
$body$
language plpgsql;

--select fn_restaurar_productos (7);

-----------------------------------------------------------Restaurar Reciclaje Usuario----------------------------------------------------------
--select * from usuarios where status = 'f'

create or replace function fn_restaurar_usuarios(_idUsuario integer)
returns void as
$body$
begin
	update usuarios set status = 't' where codigo_usuario = _idUsuario;
end
$body$
language plpgsql;
