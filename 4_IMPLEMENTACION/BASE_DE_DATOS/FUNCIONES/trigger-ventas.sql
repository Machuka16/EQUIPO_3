CREATE OR REPLACE FUNCTION funcion_ventas() RETURNS TRIGGER AS $actualizar_producto$
DECLARE canti boolean;
BEGIN 

	IF (NEW.cantidad < (select existencia_actual from productos where codigo_producto = NEW.codigo_producto)) 
	THEN
	 UPDATE productos set existencia_actual = existencia_actual - NEW.cantidad WHERE codigo_producto = NEW.codigo_producto;
           RETURN NEW;
              
        ELSE
         RETURN NULL;
        END IF;
END; 
$actualizar_producto$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_ventas AFTER INSERT OR UPDATE OR DELETE
ON detalles_de_ventas
FOR EACH ROW
EXECUTE PROCEDURE funcion_ventas();

--insert into detalles_de_ventas values(12,11,2,1,200.00,35.00);
--insert into ventas values(12,22,4,now());