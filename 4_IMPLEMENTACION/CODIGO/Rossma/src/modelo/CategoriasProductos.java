package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import controlador.ControladorErrores;

public class CategoriasProductos {
	private StringProperty categoria;
	private IntegerProperty id_categoria;
	private Boolean status;
	private Conexion con;
	private ObservableList<CategoriasProductos> catProd;
	private ControladorErrores error;
	
	
	public CategoriasProductos(){
		error = new ControladorErrores();
		con = Conexion.getInstancia();
		
		categoria = new SimpleStringProperty();
		id_categoria = new SimpleIntegerProperty();
	}

	
	/*
	 * #Region Getters and Setters
	 */
	public String getCategoria() {
		return categoria.get();
	}

	
	public void setCategoria(StringProperty categoria) {
		this.categoria = categoria;
	}


	public Integer getId_categoria() {
		return id_categoria.get();
	}


	public void setId_categoria(IntegerProperty id_categoria) {
		this.id_categoria = id_categoria;
	}
	
	
	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}


	public ObservableList<CategoriasProductos> getCategoriasProductos() throws SQLException{
		ResultSet rs = null;
		try {
			String sql = "select * from categorias_de_productos where status = 't'";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs = comando.executeQuery();
			catProd = FXCollections.observableArrayList();
				while (rs.next()){
					CategoriasProductos catP = new CategoriasProductos();
					catP.categoria = new SimpleStringProperty(rs.getString("categoria"));
					catP.id_categoria = new SimpleIntegerProperty(rs.getInt("id_categoria_producto"));
					catP.status = new Boolean(rs.getBoolean("status"));
					catProd.add(catP);					
				}
		} catch (Exception e) {
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
		}finally{
			con.desconectar();
			rs.close();
				}
				return catProd;	
		
		}
	
	public boolean getID(){
		int a = this.getId_categoria();
		return true;
	}
	
	public boolean insertar(){	
		ResultSet rs = null;
		
		try {
			String sq = "select id_categoria_producto from categorias_de_productos where id_categoria_producto = ?";
			con.conectar();
			PreparedStatement comp = con.getConexion().prepareStatement(sq);
			comp.setInt(1, this.getId_categoria());
			rs = comp.executeQuery();
			if(rs.next()){
				String sql = "select fn_actualizar_categoria_de_productos (?,?)";
				con.conectar();
				PreparedStatement actualizar = con.getConexion().prepareStatement(sql);
				actualizar.setInt(1, this.getId_categoria());
				actualizar.setString(2, this.getCategoria());
				actualizar.execute();
			}else{
				//CategoriasProductos ca = new CategoriasProductos();
				//ca.getId_categoria();
				String sql = "select fn_agregar_categorias_de_productos (?)";
				con.conectar();
				PreparedStatement insertar = con.getConexion().prepareStatement(sql);
				insertar.setString(1, this.getCategoria());
				insertar.execute();
				//return true;
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
	
	public boolean eliminar(){
		try {			
				String sql = "select fn_eliminar_categorias_de_productos (?)";
				con.conectar();
				PreparedStatement eliminar = con.getConexion().prepareStatement(sql);
				eliminar.setInt(1, this.getId_categoria());
				int c = this.getId_categoria();
				System.out.println(c);
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
	
	public boolean actualizar(){
		try {
			String sql = "select fn_actualizar_categoria_de_productos (?,?)";
			con.conectar();
			PreparedStatement actualizar = con.getConexion().prepareStatement(sql);
			actualizar.setInt(1, this.getId_categoria());
			actualizar.setString(2, this.getCategoria());
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
	
	public String toString(){
		return categoria.getValue();
	}
}
