package modelo;

import java.sql.Connection;
import java.sql.DriverManager;

import controlador.ControladorErrores;

public class Conexion {

	private String servidor, usuario, contrasenia, bd, ip, puerto;
	private static Conexion instancia;
	private Connection con;
	private ControladorErrores error;
	
	/*
	 * Constructor sin paramétros que permitirá definir un dato por default de la clase 
	 */
	
	private Conexion(){
		error = new ControladorErrores();
		bd="rossma";
		usuario="postgres";
		contrasenia="Vivi_utcv";
		servidor="jdbc:postgresql://";
		puerto="5432";
		ip="127.0.0.1";
		con= null;
	}
	
	/*
	 * Constructor con parámetros que permita inicializar con valores personalizados 
	 */
	private Conexion(String usuario, String contrasenia, String bd, String puerto, String ip){
		this.servidor="jdbc:postgresql://";
		this.contrasenia=contrasenia;
		this.usuario=usuario;
		this.bd=bd;
		this.ip=ip;
		this.puerto=puerto;
		con=null;
	}
	
	/*
	 * Método para recuperar la instancia de la clase de conexión
	 */
	public static Conexion getInstancia(){
		if(instancia==null){
				//Inicializar
				instancia = new Conexion();
		}
		return instancia;
	}
	
	/*
	 * Método para conectar al servidor Postgresql
	 */
	
	public String conectar(){
		try {
			//Verifica que este el driver en el proyecto
			Class.forName("org.postgresql.Driver"); 
			//Establecemos conexión		
			servidor="jdbc:postgresql://";
			con = DriverManager.getConnection(servidor+ip+":"+puerto+"/"+bd, usuario, contrasenia);
			System.out.println("Se ha establecido la conexión");
			return "Conexión éxitosa";
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			error.printLong(ex.getMessage(), this.getClass().toString());
			return "No se establecio la conexión. Consulte a su administrador.";
		}
	}
	
	/*
	 * Método para desconectar del servidor de Postgresql
	 */
	
	public String desconectar(){
		try {
			//Cerrar la conexión
			con.close();
			System.out.println("Se ha desconectado del servidor");
			return "Se ha desconectado del servidor";
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			error.printLong(ex.getMessage(), this.getClass().toString());
			return "La conexión está siendo ocupada. No se puede desconectar.";
		}
	}
	
	/*
	 * Método para recuperar la conexion abierta
	 */
	
	public Connection getConexion(){
		return con;
	}
	
	
	
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getBd() {
		return bd;
	}

	public void setBd(String bd) {
		this.bd = bd;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPuerto() {
		return puerto;
	}

	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}
	
	/*public static void main(String[] args){
		Conexion conec = new Conexion();
		conec.conectar();
	}*/
}
