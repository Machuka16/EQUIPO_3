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
import controlador.ControladorErrores;

public class Productos {
	private StringProperty descripcion, clave,causa;
	private String unidad;
	private Boolean status;
	private IntegerProperty id_codigo;
	private FloatProperty precio1, precio2, iva, actual, maxima, minima, nueva;
	private CategoriasProductos cp;
	private ObservableList<Productos> elementos;
	private ObservableList<Productos> recicla;
	private ObservableList<Productos> lisProducto;
	private Conexion con;
	private ControladorErrores error;
	
	
	public Productos(){
		error = new ControladorErrores();
		descripcion = causa = new SimpleStringProperty();
		id_codigo = new SimpleIntegerProperty();
		unidad = new String();
		precio1 = precio2 = iva = actual = maxima = minima = nueva = new SimpleFloatProperty();
		cp = new CategoriasProductos();
		con = Conexion.getInstancia();		
	}

	

	/*
	 * #Region Getters and Setters
	 */	
	public String getUnidad() {
		return unidad;
	}


	public void setUnidad(String string) {
		this.unidad = string;
	}


	public String getDescripcion() {
		return descripcion.get();
	}


	public void setDescripcion(StringProperty descripcion) {
		this.descripcion = descripcion;
	}
	
	
	public String getClave() {
		return clave.get();
	}

	public void setClave(StringProperty clave) {
		this.clave = clave;
	}



	public Integer getId_codigo() {
		return id_codigo.get();
	}


	public void setId_codigo(IntegerProperty id_codigo) {
		this.id_codigo = id_codigo;
	}


	public Float getPrecio1() {
		return precio1.get();
	}


	public void setPrecio1(FloatProperty precio1) {
		this.precio1 = precio1;
	}


	public Float getPrecio2() {
		return precio2.get();
	}


	public void setPrecio2(FloatProperty precio2) {
		this.precio2 = precio2;
	}


	public Float getIva() {
		return iva.get();
	}


	public void setIva(FloatProperty iva) {
		this.iva = iva;
	}


	public Float getActual() {
		return actual.get();
	}


	public void setActual(FloatProperty actual) {
		this.actual = actual;
	}


	public Float getMaxima() {
		return maxima.get();
	}


	public void setMaxima(FloatProperty maxima) {
		this.maxima = maxima;
	}


	public Float getMinima() {
		return minima.get();
	}


	public void setMinima(FloatProperty minima) {
		this.minima = minima;
	}


	public CategoriasProductos getCp() {
		return cp;
	}


	public void setCp(CategoriasProductos cp) {
		this.cp = cp;
	}	
	
	
	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}	

	public String getCausa() {
		return causa.get();
	}

	public void setCausa(StringProperty causa) {
		this.causa = causa;
	}

	public Float getNueva() {
		return nueva.get();
	}

	public void setNueva(FloatProperty nueva) {
		this.nueva = nueva;
	}

	public int contador() throws SQLException {
		int o = 0;
		ResultSet rs = null;
		try {
			String sql = "select count(codigo_producto) from productos where status = TRUE ";

			con.conectar();
			PreparedStatement reci = con.getConexion().prepareStatement(sql);
			rs = reci.executeQuery();
			while (rs.next()){
				o = rs.getInt("count");
			}			
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}finally{
			rs.close();
			con.desconectar();
		}
		return o;
			
		}
	
	public boolean actualizarExistencia(){		
		boolean bandera = false;
		try {
			String sql = "select fn_stock(?,?,?)";
			con.conectar();
			PreparedStatement actualizar = con.getConexion().prepareStatement(sql);
			actualizar.setInt(1, this.getId_codigo());
			actualizar.setFloat(2, this.getActual());
			actualizar.setString(3, this.getCausa());
			actualizar.execute();
			bandera = true;
		} catch (Exception e) {
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
			bandera =false;
		}finally{
			con.desconectar();
		}
		return bandera;
	}

	public ObservableList<Productos> getReciclar() throws SQLException{
		ResultSet rs = null;
		try {
			String sql = "select * from vwproductos where status = 'f'";
			con.conectar();
			PreparedStatement reciclar = con.getConexion().prepareStatement(sql);
			rs = reciclar.executeQuery();
			elementos = FXCollections.observableArrayList();
			while (rs.next()){
				Productos pr = new Productos();
				pr.id_codigo = new SimpleIntegerProperty(rs.getInt("codigo_producto"));
				pr.cp.setId_categoria(new SimpleIntegerProperty(rs.getInt("id_categoria_producto")));
				pr.cp.setCategoria(new SimpleStringProperty(rs.getString("categoria")));
				pr.clave =new SimpleStringProperty(rs.getString("clave"));
				pr.unidad = new String(rs.getString("unidad_medida"));
				pr.descripcion = new SimpleStringProperty(rs.getString("descripcion"));
				pr.precio1 = new SimpleFloatProperty(rs.getFloat("precio1"));
				pr.precio2 = new SimpleFloatProperty(rs.getFloat("precio2"));
				pr.iva = new SimpleFloatProperty(rs.getFloat("porciento_iva"));
				pr.actual = new SimpleFloatProperty(rs.getFloat("existencia_actual"));
				pr.maxima = new SimpleFloatProperty(rs.getFloat("existencia_maxima"));
				pr.minima = new SimpleFloatProperty(rs.getFloat("existencia_minima"));
				pr.status = new Boolean(rs.getBoolean("status"));
				elementos.add(pr);
			}
		} catch (Exception e) {
			e.printStackTrace();	
			error.printLong(e.getMessage(), this.getClass().toString());
		}finally{
			rs.close();
			con.desconectar();
		}
		return elementos;
	}
	
	public ObservableList<Productos> getProductos() throws SQLException {
		ResultSet rs = null;
		try {
			String sql = "select * from vwproductos where status = 't' order by clave asc";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);			
			rs = comando.executeQuery();
			elementos = FXCollections.observableArrayList();
			while (rs.next()){
				Productos pr = new Productos();
				pr.id_codigo = new SimpleIntegerProperty(rs.getInt("codigo_producto"));				
				pr.cp.setId_categoria(new SimpleIntegerProperty(rs.getInt("id_categoria_producto")));
				pr.cp.setCategoria(new SimpleStringProperty(rs.getString("categoria")));
				pr.unidad = new String(rs.getString("unidad_medida"));
				pr.clave =new SimpleStringProperty(rs.getString("clave"));
				pr.descripcion = new SimpleStringProperty(rs.getString("descripcion"));
				pr.precio1 = new SimpleFloatProperty(rs.getFloat("precio1"));
				pr.precio2 = new SimpleFloatProperty(rs.getFloat("precio2"));
				pr.iva = new SimpleFloatProperty(rs.getFloat("porciento_iva"));
				pr.actual = new SimpleFloatProperty(rs.getFloat("existencia_actual"));
				pr.maxima = new SimpleFloatProperty(rs.getFloat("existencia_maxima"));
				pr.minima = new SimpleFloatProperty(rs.getFloat("existencia_minima"));
				pr.causa = new SimpleStringProperty(rs.getString("causa"));
				elementos.add(pr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
		}finally{
			rs.close();
			con.desconectar();
		}
		return elementos;
	}
	
	
	
	public boolean insertarProducto(){
		ResultSet rs = null;
		try {
			String sq = "select codigo_producto from productos where codigo_producto = ?";
			con.conectar();
			PreparedStatement comp = con.getConexion().prepareStatement(sq);
			comp.setInt(1, this.getId_codigo());
			rs = comp.executeQuery();
			if(rs.next()){
				String sql = "select fn_actualizar_productos (?,?,?,?,?,?,?,?,?,?,?)";
				con.conectar();
				PreparedStatement actualizar = con.getConexion().prepareStatement(sql);
				actualizar.setInt(1, this.getId_codigo());
				actualizar.setInt(2, this.getCp().getId_categoria());
				actualizar.setString(3, this.getClave());
				actualizar.setString(4, this.getUnidad());
				actualizar.setString(5, this.getDescripcion());
				actualizar.setFloat(6, this.getPrecio1());
				actualizar.setFloat(7, this.getPrecio2());
				actualizar.setFloat(8, this.getIva());
				actualizar.setFloat(9, this.getActual());
				actualizar.setFloat(10, this.getMaxima());
				actualizar.setFloat(11, this.getMinima());
				actualizar.execute();
			}else{
				String sql = "select fn_agregar_productos(?,?,?,?,?,?,?,?,?,?)";
				con.conectar();
				PreparedStatement insertar = con.getConexion().prepareStatement(sql);						
				insertar.setInt(1, this.getCp().getId_categoria());			
				insertar.setString(2, this.getUnidad());
				insertar.setString(3, this.getClave());
				insertar.setString(4, this.getDescripcion());			
				insertar.setFloat(5, this.getPrecio1());			
				insertar.setFloat(6, this.getPrecio2());			
				insertar.setFloat(7, this.getIva());			
				insertar.setFloat(8, this.getActual());			
				insertar.setFloat(9, this.getMaxima());			
				insertar.setFloat(10, this.getMinima());			
				insertar.execute();		
			}				
			return true;
		} catch (Exception e) {			
			e.printStackTrace();	
			error.printLong(e.getMessage(), this.getClass().toString());
			return false;
		}finally{
			con.desconectar();
		}
	}
	
	public ObservableList<Productos> getListaProductos(){
		ResultSet rs = null;
		try {
			String sql = "select descripcion from productos";
			con.conectar();
			PreparedStatement lisP = con.getConexion().prepareStatement(sql);
			rs = lisP.executeQuery();
			while(rs.next()){
				Productos pr = new Productos();
				pr.descripcion = new SimpleStringProperty(rs.getString("descripcion"));
				lisProducto.add(pr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
		}finally{
			con.desconectar();
		}
		return lisProducto;
	}
	
	public boolean eliminarProducto(){
		try {
			String sql = "select fn_eliminar_productos (?)";
			con.conectar();
			PreparedStatement eliminar = con.getConexion().prepareStatement(sql);
			eliminar.setInt(1, this.getId_codigo());
			System.out.println("eliminar "+ this.getId_codigo());
			eliminar.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
			return false;
		}finally{
			con.desconectar();
		}
	}
	
	public boolean actualizarProducto(){
		try {
			String sql = "select fn_actualizar_productos (?,?,?,?,?,?,?,?,?,?)";
			con.conectar();
			PreparedStatement actualizar = con.getConexion().prepareStatement(sql);
			actualizar.setInt(1, this.getId_codigo());
			actualizar.setInt(2, this.getCp().getId_categoria());
			actualizar.setString(3, this.getUnidad());
			actualizar.setString(4, this.getDescripcion());
			actualizar.setFloat(5, this.getPrecio1());
			actualizar.setFloat(6, this.getPrecio2());
			actualizar.setFloat(7, this.getIva());
			actualizar.setFloat(8, this.getActual());
			actualizar.setFloat(9, this.getMaxima());
			actualizar.setFloat(10, this.getMinima());
			actualizar.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
			return false;
		}finally{
			con.desconectar();			
		}
	}
	
	public boolean actualizarReciclaje(){
		try {
			String sql = "select fn_restaurar_productos (?)";
			con.conectar();
			PreparedStatement actReci = con.getConexion().prepareStatement(sql);
			actReci.setInt(1, this.getId_codigo());			
			actReci.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
			return false;
		}finally{
			con.desconectar();
		}
	}
	
	public String toString(){
		return descripcion.get();
	}
}
