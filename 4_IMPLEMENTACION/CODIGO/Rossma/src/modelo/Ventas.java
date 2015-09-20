package modelo;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class Ventas {
	private Clientes cl;
	private Productos pr;
	private NuevoUsuario nu;
	private DetalleVenta dv;
	private Integer ventaId, detalleVenta_Id ;
	private Float precio, cantidad, total, exitencias;
	private StringProperty fecha;
	private Conexion con;
	private ObservableList<DetalleVenta> listaDetalle;
	private ObservableList<Ventas> idVenta;
		
	public Ventas(){
		con = Conexion.getInstancia();
		cl = new Clientes();
		pr = new Productos();
		nu = new NuevoUsuario();
		dv = new DetalleVenta();
		ventaId = detalleVenta_Id =  0;
		precio = cantidad = exitencias = 0.0F;
		fecha = new SimpleStringProperty();
		listaDetalle = FXCollections.observableArrayList();
		idVenta = FXCollections.observableArrayList();
	}
	
	

	public Float getExitencias() {
		return exitencias;
	}

	public void setExitencias(Float exitencias) {
		this.exitencias = exitencias;
	}

	public Clientes getCl() {
		return cl;
	}


	public void setCl(Clientes cl) {
		this.cl = cl;
	}


	public Productos getPr() {
		return pr;
	}


	public void setPr(Productos pr) {
		this.pr = pr;
	}


	public NuevoUsuario getNu() {
		return nu;
	}


	public void setNu(NuevoUsuario nu) {
		this.nu = nu;
	}


	public Integer getVentaId() {
		return ventaId;
	}


	public void setVentaId(Integer ventaId) {
		this.ventaId = ventaId;
	}


	public Integer getDetalleVenta_Id() {
		return detalleVenta_Id;
	}


	public void setDetalleVenta_Id(Integer detalleVenta_Id) {
		this.detalleVenta_Id = detalleVenta_Id;
	}


	public Float getCantidad() {
		return cantidad;
	}


	public void setCantidad(Float cantidad) {
		this.cantidad = cantidad;
	}


	public Float getPrecio() {
		return precio;
	}


	public void setPrecio(Float precio) {
		this.precio = precio;
	}


	public StringProperty getFecha() {
		return fecha;
	}


	public void setFecha(StringProperty fecha) {
		this.fecha = fecha;
	}
	
	
	public Float getTotal() {
		return total;
	}


	public void setTotal(Float total) {
		this.total = total;
	}


	public int id() throws SQLException{
		ResultSet rs = null;
		int id = 0;
		try {
			String sql = "select max(folio) as id from ventas";
			con.conectar();
			PreparedStatement recuperarId = con.getConexion().prepareStatement(sql);
			rs = recuperarId.executeQuery();
			while (rs.next()){
				id = rs.getInt("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			rs.close();
			con.desconectar();
		}
		return id;
	}
	
	public boolean existencia() throws SQLException{
		boolean bandera = false;
		ResultSet rs = null;
		try {
			String sql = "select existencia_actual from productos where codigo_producto = ?";
			con.conectar();
			PreparedStatement recuperar = con.getConexion().prepareStatement(sql);
			
			recuperar.setInt(1, dv.getProducto_id());
			System.out.println("base "+ dv.getProducto_id());
			rs = recuperar.executeQuery();
			while(rs.next()){
				Ventas v = new Ventas();
				v.exitencias = new Float(rs.getFloat("existencia_actual"));
				bandera = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			bandera = false;
		}finally{
			rs.close();
			con.desconectar();
		}		
		System.out.println("desde base de datos id "+ bandera);
		return bandera;
	}
	
	public boolean agregarDetalle(){
		boolean existe = false;
		try {				
			if(listaDetalle.isEmpty() == false){
				
				for(int i = 0; i<listaDetalle.size(); i++){
					if(listaDetalle.get(i).getProducto_id() == pr.getId_codigo()){
						
						
						DetalleVenta d = listaDetalle.get(i);
						float nuevo = d.getCantidad();
						System.out.println("nuevo "+ nuevo);
						d.setExistencia(nuevo);
						
						float nuevaCantidad = d.getCantidad() + cantidad;
						d.setCantidad(new Float(nuevaCantidad));
												
						float nuevoSubtotal = d.getPrecio() * nuevaCantidad;						
						d.setSubtotal(new Float(nuevoSubtotal));						
						listaDetalle.set(i, d);						
						
						existe = true;						
						break;						
					}
				}
			}
			
			if(listaDetalle.isEmpty() || existe == false){
				boolean bandera = false;
				DetalleVenta dv = new DetalleVenta();
				dv.setProducto_id(pr.getId_codigo());
				dv.setCantidad(cantidad);
				dv.setPrecio(pr.getPrecio1());
				dv.setPrecio1(pr.getPrecio2());
				System.out.println(pr.getPrecio2());
				dv.setSubtotal(cantidad * pr.getPrecio1());				
				dv.setProducto(pr.getDescripcion());
				
				//System.out.println(dv.getExistencia());
				listaDetalle.add(dv);							
			}
			return true;			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("falso");
			return false;
		}
	}
	
	public boolean agregarDetallePublico(){
		boolean existe = false;
		try {				
			if(listaDetalle.isEmpty() == false){
				
				for(int i = 0; i<listaDetalle.size(); i++){
					if(listaDetalle.get(i).getProducto_id() == pr.getId_codigo()){
						
						
						DetalleVenta d = listaDetalle.get(i);
						float nuevo = d.getCantidad();
						System.out.println("nuevo "+ nuevo);
						d.setExistencia(nuevo);
						
						float nuevaCantidad = d.getCantidad() + cantidad;
						d.setCantidad(new Float(nuevaCantidad));
												
						float nuevoSubtotal = d.getPrecio() * nuevaCantidad;						
						d.setSubtotal(new Float(nuevoSubtotal));						
						listaDetalle.set(i, d);						
						
						existe = true;						
						break;						
					}
				}
			}
			
			if(listaDetalle.isEmpty() || existe == false){
				//boolean bandera = false;
				DetalleVenta dv = new DetalleVenta();
				dv.setProducto_id(pr.getId_codigo());
				dv.setCantidad(cantidad);
				dv.setPrecio(pr.getPrecio2());
				dv.setExistencia(pr.getActual());
				System.out.println("Probando"+ pr.getActual());
				
				dv.setSubtotal(cantidad * pr.getPrecio2());				
				dv.setProducto(pr.getDescripcion());
				
				listaDetalle.add(dv);							
			}
			return true;			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("falso");
			return false;
		}
	}
	
	
	
	
	public String getPrecioMayoreo(){
		String precio1 = null;
		for(DetalleVenta d: listaDetalle){
			float precio = d.getPrecio1();
			precio1 = String.valueOf(precio);
		}
		return precio1;
	}
	
	
	public String getActual(){
		String actual = null;
		for(DetalleVenta d: listaDetalle){
			float actual1 = pr.getActual() - d.getCantidad();
			actual = String.valueOf(actual1);
						
		}
		return actual; 
		
	}
	
	
	
	public float getTotales(){
		total = 0.0F;
		for(DetalleVenta d: listaDetalle){
			total += d.getSubtotal();
		}
		return total;
	}
	
	
	
	public ObservableList<DetalleVenta> obtenerDetalle(){
		return listaDetalle;
	}
	
	
	public boolean guardarVenta(int vendedor, int cliente){
		ResultSet rs = null;
		try {
			String sql1 = "select max(folio) as maxFolio from ventas";
			con.conectar();
			con.getConexion().setAutoCommit(false);
			PreparedStatement guardar2 = con.getConexion().prepareStatement(sql1);			
			rs = guardar2.executeQuery();			
				if(rs.next()){
					int folio = rs.getInt("maxFolio");
					
					ventaId = folio + 1;
					
					String sql = "insert into ventas values (?,?,?, now())";
					con.conectar();
					PreparedStatement guardar1 = con.getConexion().prepareStatement(sql);
					guardar1.setInt(1, ventaId);
					guardar1.setInt(2, cliente);					
					guardar1.setInt(3, vendedor);					
					guardar1.execute();			
				}					
					for (DetalleVenta dv: listaDetalle){
						String sql3 = "insert into detalles_de_ventas values(default,?,?,?,?,?)";
						con.conectar();
						PreparedStatement guardar3 = con.getConexion().prepareStatement(sql3);					
						guardar3.setInt(1, ventaId);
						
						guardar3.setInt(2, dv.getProducto_id());
						guardar3.setFloat(3, dv.getCantidad());
						guardar3.setFloat(4, dv.getPrecio());
						
						guardar3.setFloat(5, (float) (dv.getPrecio() * 0.16));
						guardar3.executeUpdate();
					}
			
			//con.getConexion().commit();
			con.getConexion().setAutoCommit(true);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			con.desconectar();
		}
	}	
}
