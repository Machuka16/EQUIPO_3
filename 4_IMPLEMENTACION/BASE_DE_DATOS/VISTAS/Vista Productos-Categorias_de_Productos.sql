---------////////////////////***** Crear vista de tablas Productos-Categorias_de_Productos *****\\\\\\\\\\\\\\\\\---------------------------

create view vwproductos
as select codigo_producto, cp.id_categoria_producto, cp.categoria,clave, unidad_medida, descripcion, precio1, precio2, porciento_iva, existencia_actual,
	existencia_maxima, existencia_minima, causa,  p.status
from productos p join categorias_de_productos cp on p.id_categoria_producto = cp.id_categoria_producto;

