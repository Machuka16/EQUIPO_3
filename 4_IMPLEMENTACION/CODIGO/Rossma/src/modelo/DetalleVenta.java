package modelo;

public class DetalleVenta {

	private String producto;
	private Integer  producto_id;
	private float precio, precio1, subtotal, cantidad, total, iva, existencia;
	private Productos pr;
	
	
	
	public DetalleVenta(){
		producto = "";		
		precio = precio1 = subtotal = cantidad =  total = 0.0F;
		iva = (float) (precio * 0.16);
		producto_id = 0;

	}

	
	
	
	public Float getExistencia() {
		return existencia;
	}

	public void setExistencia(float existencia) {
		this.existencia = existencia;
	}

	public float getIva() {
		return iva;
	}

	public void setIva(float iva) {
		this.iva = iva;
	}


	public String getProducto() {
		return producto;
	}


	public void setProducto(String producto) {
		this.producto = producto;
	}


	public int getProducto_id() {
		return producto_id;
	}


	public void setProducto_id(int producto_id) {
		this.producto_id = producto_id;
	}


	public Float getPrecio() {
		return precio;
	}


	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public float getPrecio1() {
		return precio1;
	}

	public void setPrecio1(float precio1) {
		this.precio1 = precio1;
	}




	public float getSubtotal() {
		return subtotal;
	}


	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

	
	public float getTotal() {
		return total;
	}


	public void setTotal(float total) {
		this.total = total;
	}


	public Float getCantidad() {
		return cantidad;
	}


	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}

	
}
