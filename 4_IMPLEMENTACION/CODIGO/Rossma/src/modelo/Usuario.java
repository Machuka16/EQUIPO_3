package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Usuario {
	
	// #region Atributos
	private String nombre, contrasenia, privilegio;
	public static Integer codigo;
	private Boolean estatus;
	private Conexion con;
	/*
	 * Objeto de conexion
	 */
	private Connection miconexion;
	/*
	 * Para ejecutar una instruccion SQL
	 */
	private PreparedStatement comando;
	
	// #endregion 
	
	// #region Constructor
	public Usuario() {
		this.nombre=null;
		this.contrasenia=null;
		this.privilegio=null;
		this.estatus=null;
		//this.codigo=null;
	}
	// #endregion
	
	// #region Getters and Setters
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getPrivilegio() {
		return privilegio;
	}

	public void setPrivilegio(String privilegio) {
		this.privilegio = privilegio;
	}

	public Boolean getEstatus() {
		return estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}
	
	
	// #endregion
	
	
	// #region  Métodos de acceso a datos 
	
	/*
	 * Método para verificar que existen en la base de datos.
	 */ 
	
	

	public boolean Existe(){
		con = Conexion.getInstancia();
		boolean bandera=false;
		try {
			String sql = "select tipo,nombre,codigo_usuario from usuarios where usuario=? and"+ " contrasenia=? and"+" status = TRUE";
			//Abrir conexion
			con.conectar();
			//Se recupera la conexion
			//miconexion= con.getConexion();
			//Asociamos el comando con la conexion
			comando = con.getConexion().prepareStatement(sql);
			//Parï¿½metros
			comando.setString(1, this.nombre);
			comando.setString(2, this.contrasenia);
			
			//Se ejecuta la instruccion
			//ResultSet es una tabla temporal
			ResultSet rs=comando.executeQuery();
			// Validar los datos
			//Si existen datos en el resultset
			while(rs.next()){
				this.privilegio=(rs.getString("tipo"));
				this.nombre = (rs.getString("nombre"));
				codigo = (rs.getInt("codigo_usuario"));
				bandera=true;
			}	
			rs.close();
		} catch (Exception e) {
			System.out.println("*********Error*********");
			e.printStackTrace();
		}
		finally{
			//El bloque Finally se ejecuta exista o no un error.
			con.desconectar();
		}		
		return bandera;
	}

	
	// #endregion 
}
