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

public class NuevoUsuario {

	private StringProperty  nombre, apellido_paterno,categoria,apellido_materno, 
							direccion, correo, telefono, celular, usuario, contrasenia;
	private Boolean status;
	
	private String tipo, completo;
	
	private IntegerProperty codigo_usuario;

	private ObservableList<NuevoUsuario> elementos;
	private ObservableList<NuevoUsuario> reciclar;
	private Conexion con;
	private ControladorErrores error;

	public NuevoUsuario() {
		error = new ControladorErrores();
		con = Conexion.getInstancia();
		nombre = apellido_paterno = apellido_materno = direccion = correo = telefono = celular = usuario = new SimpleStringProperty();
		codigo_usuario = new SimpleIntegerProperty();		
	}

	
	public Integer getCodigo_usuario() {
		return codigo_usuario.get();
	}

	public void setCodigo_usuario(IntegerProperty codigo_usuario) {
		this.codigo_usuario = codigo_usuario;
	}

	public String getNombre() {
		return nombre.get();
	}

	public void setNombre(StringProperty nombre) {
		this.nombre = nombre;
	}

	public String getApellido_paterno() {
		return apellido_paterno.get();
	}

	public void setApellido_paterno(StringProperty apellido_paterno) {
		this.apellido_paterno = apellido_paterno;
	}

	public String getApellido_materno() {
		return apellido_materno.get();
	}

	public void setApellido_materno(StringProperty apellido_materno) {
		this.apellido_materno = apellido_materno;
	}

	public String getDireccion() {
		return direccion.get();
	}

	public void setDireccion(StringProperty direccion) {
		this.direccion = direccion;
	}

	public String getCorreo() {
		return correo.get();
	}

	public void setCorreo(StringProperty correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono.get();
	}

	public void setTelefono(StringProperty telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular.get();
	}

	public void setCelular(StringProperty celular) {
		this.celular = celular;
	}

	public String getUsuario() {
		return usuario.get();
	}	
	

	public void setUsuario(StringProperty usuario) {
		this.usuario = usuario;
	}
	

	public String getContrasenia() {
		return contrasenia.get();
	}

	public void setContrasenia(StringProperty contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getCategoria() {
		return categoria.get();
	}

	public void setCategoria(StringProperty categoria) {
		this.categoria = categoria;
	}	
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}	


	public String getCompleto() {
		return completo;
	}


	public void setCompleto(String completo) {
		this.completo = completo;
	}


	public ObservableList<NuevoUsuario> getReciclar() throws SQLException{
		ResultSet rs = null;
		try {
			String sql = "select * from usuarios where status = 'f'";
			con.conectar();
			PreparedStatement reci = con.getConexion().prepareStatement(sql);
			rs = reci.executeQuery();
			reciclar = FXCollections.observableArrayList();
			while(rs.next()){
				NuevoUsuario nu = new NuevoUsuario();
				nu.codigo_usuario = new SimpleIntegerProperty(rs.getInt("codigo_usuario"));
				nu.usuario = new SimpleStringProperty(rs.getString("usuario"));
				nu.contrasenia = new SimpleStringProperty(rs.getString("contrasenia"));
				nu.categoria = new SimpleStringProperty(rs.getString("tipo"));
				nu.nombre = new SimpleStringProperty(rs.getString("nombre"));
				nu.apellido_paterno = new SimpleStringProperty(rs.getString("apellido_paterno"));
				nu.apellido_materno = new SimpleStringProperty(rs.getString("apellido_materno"));
				nu.direccion = new SimpleStringProperty(rs.getString("direccion"));
				nu.telefono = new SimpleStringProperty(rs.getString("telefono"));
				nu.celular = new SimpleStringProperty(rs.getString("celular"));
				nu.correo = new SimpleStringProperty(rs.getString("correo"));
				nu.status = new Boolean(rs.getBoolean("status"));
				reciclar.add(nu);
			}
		} catch (Exception e) {
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
		}finally{
			rs.close();
			con.desconectar();
		}
		return reciclar;
	}
	
	/*public ObservableList<NuevoUsuario> getNewUsser() throws SQLException {
		ResultSet rs = null;
		try {
			String sql = "select codigo_usuario, usuario, contrasenia, tipo, nombre, apellido_paterno, apellido_materno, direccion, telefono, celular, correo from usuarios where status = TRUE";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs = comando.executeQuery();
			elementos = FXCollections.observableArrayList();
			while (rs.next()) {
				NuevoUsuario U = new NuevoUsuario();
				U.codigo_usuario = new SimpleIntegerProperty(rs.getInt("codigo_usuario"));
				U.usuario = new SimpleStringProperty(rs.getString("usuario"));
				U.contrasenia = new SimpleStringProperty(rs.getString("contrasenia"));
				U.categoria = new SimpleStringProperty(rs.getString("tipo"));
				U.nombre = new SimpleStringProperty(rs.getString("nombre"));
				U.apellido_paterno = new SimpleStringProperty(rs.getString("apellido_paterno"));
				U.apellido_materno = new SimpleStringProperty(rs.getString("apellido_materno"));
				U.direccion = new SimpleStringProperty(rs.getString("direccion"));
				U.telefono = new SimpleStringProperty(rs.getString("telefono"));
				U.celular = new SimpleStringProperty(rs.getString("celular"));
				U.correo = new SimpleStringProperty(rs.getString("correo"));				
				elementos.add(U);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			con.desconectar();
		}
		return elementos;

	}*/
	
	
	public ObservableList<NuevoUsuario> getNewUsser() throws SQLException {
		ResultSet rs = null;
		try {
			String sql = "select * from recuperar_usuarios();";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs = comando.executeQuery();
			elementos = FXCollections.observableArrayList();
			while (rs.next()) {
				NuevoUsuario U = new NuevoUsuario();
				U.codigo_usuario = new SimpleIntegerProperty(rs.getInt("codigo_usuario"));
				U.usuario = new SimpleStringProperty(rs.getString("usuario"));
				U.contrasenia = new SimpleStringProperty(rs.getString("contrasenia"));
				U.categoria = new SimpleStringProperty(rs.getString("tipo"));
				U.nombre = new SimpleStringProperty(rs.getString("nombre"));
				U.completo = new String(rs.getString("completo"));
				U.apellido_paterno = new SimpleStringProperty(rs.getString("apellido_paterno"));
				U.apellido_materno = new SimpleStringProperty(rs.getString("apellido_materno"));
				U.direccion = new SimpleStringProperty(rs.getString("direccion"));
				U.telefono = new SimpleStringProperty(rs.getString("telefono"));
				U.celular = new SimpleStringProperty(rs.getString("celular"));
				U.correo = new SimpleStringProperty(rs.getString("correo"));				
				elementos.add(U);
			}
		} catch (Exception e) {
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
		} finally {
			rs.close();
			con.desconectar();
		}
		return elementos;

	}


	public boolean insertar(){
		ResultSet rs = null;
		try {
			String sq = "select codigo_usuario from usuarios where codigo_usuario = ?";
			con.conectar();
			PreparedStatement comp = con.getConexion().prepareStatement(sq);
			comp.setInt(1, this.getCodigo_usuario());
			rs = comp.executeQuery();
			if(rs.next()){
				String sql = "select fn_actualizar_usuarios (?,?,?,?,?,?,?,?,?,?,?)";
				con.conectar();
				PreparedStatement actualizar = con.getConexion().prepareStatement(sql);
				actualizar.setInt(1, this.getCodigo_usuario());
				actualizar.setString(2, this.getUsuario());
				actualizar.setString(3, this.getContrasenia());
				actualizar.setString(4, this.getTipo());
				actualizar.setString(5, this.getNombre());
				actualizar.setString(6, this.getApellido_paterno());
				actualizar.setString(7, this.getApellido_materno());
				actualizar.setString(8, this.getDireccion());
				actualizar.setString(9, this.getTelefono());
				actualizar.setString(10, this.getCelular());
				actualizar.setString(11, this.getCorreo());			
				actualizar.execute();
			}else{
				String sql = "select fn_agregar_usuario (?,?,?,?,?,?,?,?,?,?)";
				con.conectar();
				PreparedStatement comando = con.getConexion().prepareStatement(sql);
				comando.setString(1, this.getUsuario());			
				comando.setString(2, this.getContrasenia());
				comando.setString(3, this.getTipo());
				comando.setString(4, this.getNombre());
				comando.setString(5, this.getApellido_paterno());
				comando.setString(6, this.getApellido_materno());
				comando.setString(7, this.getDireccion());
				comando.setString(8, this.getTelefono());
				comando.setString(9, this.getCelular());			
				comando.setString(10, this.getCorreo());			
				comando.execute();
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
			String sql = "select fn_eliminar_usuario (?)";
			con.conectar();
			PreparedStatement eliminar = con.getConexion().prepareStatement(sql);
			eliminar.setInt(1, this.getCodigo_usuario());
			int c = this.getCodigo_usuario();
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
	
	public boolean actualizarReciclaje(){
		try {
			String sql = "select fn_restaurar_usuarios (?)";
			con.conectar();
			PreparedStatement actRe = con.getConexion().prepareStatement(sql);
			actRe.setInt(1, this.getCodigo_usuario());
			actRe.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
			return false;
		}
	}


	public int contarUsuarios() {
		int contar = 0;
		try {
			
			String sql = "select count(codigo_usuario) as contar from usuarios where status = true";
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);			
			ResultSet resultado = comando.executeQuery();
			
			if (resultado.next()) {
				contar = resultado.getInt("contar");
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
						
		}finally {
			con.desconectar();
		}
		return contar;
	}
	
}
