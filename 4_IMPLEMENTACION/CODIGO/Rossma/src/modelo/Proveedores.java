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

import javax.swing.JOptionPane;

import controlador.ControladorErrores;
import controlador.ControladorProveedores;

public class Proveedores {
	
	private StringProperty  nombreProveedor, pais,municipio, 
	calle, numeroExterior, numeroInterior,colonia,localidad,email,telefono,celular,otro,c_nombre,c_paterno,c_materno,c_direccion,c_telefono,c_correo;
	
	private String estado;



	private IntegerProperty id_proveedor,cp;

private ObservableList<Proveedores> elementos;


private Conexion con;
private ControladorProveedores provee;





private ControladorErrores error;

public Proveedores() {
	error = new ControladorErrores();
	nombreProveedor = pais = municipio = calle = numeroExterior = numeroInterior = colonia = localidad  = email = telefono 
			= celular = otro= c_nombre= c_paterno= c_materno =c_direccion =c_telefono= c_correo =new SimpleStringProperty();
	id_proveedor = cp = new SimpleIntegerProperty();
	estado = new String();
	con = Conexion.getInstancia();
}



public int contador(boolean status) throws SQLException {
int o = 0;
ResultSet rs = null;
String sql = " ";
try {
	if (status) {
		sql = "select count(codigo_proveedor) from proveedores where status = TRUE ";
	}else {
		sql = "select count(codigo_proveedor) from proveedores where status = FALSE ";
	}

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

public int insertar(){
	
	int i = 0;
	ResultSet resultado= null;
		String sql = "select codigo_proveedor from proveedores where codigo_proveedor= ?";
		try {
		con.conectar();
		PreparedStatement comando1 =con.getConexion().prepareStatement(sql);
		comando1.setInt(1, this.getId_proveedor());
		System.out.println(this.getId_proveedor());
		resultado = comando1.executeQuery();
		if (resultado.next()) {
			sql = "select fn_actualizar_proveedor(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			comando1 =con.getConexion().prepareStatement(sql);
			
			comando1.setInt(	  1, this.getId_proveedor());
			comando1.setString(2, this.getC_nombre());
			comando1.setString(3, this.getC_paterno());
			comando1.setString(4, this.getC_materno());
			comando1.setString(5, this.getC_direccion());
			comando1.setString(6, this.getC_telefono());
			comando1.setString(7, this.getC_correo());
			comando1.setString(8, this.getNombreProveedor());
			comando1.setString(9, this.getPais());
			comando1.setString(10, this.getEstado());
			comando1.setString(11, this.getMunicipio());
			comando1.setString(12, this.getCalle());
			comando1.setString(13, this.getNumeroExterior());
			comando1.setString(14, this.getNumeroInterior());
			comando1.setString(15, this.getColonia());
			comando1.setString(16, this.getLocalidad());
			comando1.setInt(	  17, this.getCp());
			comando1.setString(18, this.getEmail());
			comando1.setString(19, this.getTelefono());
			comando1.setString(20, this.getCelular());
			comando1.setString(21, this.getOtro());
			comando1.execute();
			i = 1;
			
		}else{
			con.conectar();
			sql = "select correo from proveedores where correo = ? and codigo_proveedor != ?";
			PreparedStatement comando2 = con.getConexion().prepareStatement(sql);
			comando2.setString(1, this.getEmail());
			comando2.setInt(   2, this.getId_proveedor());
			resultado = comando2.executeQuery();
			if (!resultado.next()) {
				con.conectar();
				sql = "select correo from contactos_proveedores where correo = ? and codigo_proveedor != ? ";
				PreparedStatement comando3 =con.getConexion().prepareStatement(sql);
				comando3.setString(1, this.getC_correo());
				comando3.setInt(2, this.getId_proveedor());
				resultado = comando3.executeQuery();
				
				if (!resultado.next()) {
					sql = "select fn_agregar_proveedor(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
					con.conectar();
					PreparedStatement comando4 = con.getConexion().prepareStatement(sql);
					comando4.setString(1, this.getC_nombre());
					comando4.setString(2, this.getC_paterno());
					comando4.setString(3, this.getC_materno());
					comando4.setString(4, this.getC_direccion());
					comando4.setString(5, this.getC_telefono());
					comando4.setString(6, this.getC_correo());
					comando4.setString(7, this.getNombreProveedor());
					comando4.setString(8, this.getPais());
					comando4.setString(9, this.getEstado());
					comando4.setString(10, this.getMunicipio());
					comando4.setString(11, this.getCalle());
					comando4.setString(12, this.getNumeroExterior());
					comando4.setString(13, this.getNumeroInterior());
					comando4.setString(14, this.getColonia());
					comando4.setString(15, this.getLocalidad());
					comando4.setInt(16, this.getCp());
					comando4.setString(17, this.getEmail());
					comando4.setString(18, this.getTelefono());
					comando4.setString(19, this.getCelular());
					comando4.setString(20, this.getOtro());
					comando4.execute();
					i= 4;
				}else {
					i = 3;
					
				}
			}else {
				i = 2;
			}
		}
		
			
	} catch (Exception e) {
		e.printStackTrace();
		error.printLong(e.getMessage(), this.getClass().toString());
		i = 0;
	}
		return i;
	
}


/*public boolean insertar() {
	try {
		String sql1 = "select correo from proveedores where correo = ? ";
		con.conectar();
		PreparedStatement comando1 =con.getConexion().prepareStatement(sql1);
		comando1.setString(1, this.getEmail());
		ResultSet resultado1 = comando1.executeQuery();
		
		//compa = resultado1.getInt("correo");
		
		if (!resultado1.next()) {
				String sql2 = "select correo from contactos_proveedores where correo = ? ";
				con.conectar();
				PreparedStatement comando2 =con.getConexion().prepareStatement(sql2);
				comando2.setString(1, this.getC_correo());
				ResultSet resultado2 = comando2.executeQuery();				
				if (!resultado2.next()) {					
				return true;					
				}else {
					JOptionPane.showMessageDialog(null, " EL CORREO DE CONTACTO PROVEEDOR YA EXISTE ","Confirmación",JOptionPane.INFORMATION_MESSAGE);
					return false;
				}			
		}else {
			
			JOptionPane.showMessageDialog(null, " EL CORREO DE PROVEEDOR YA EXISTE ","Confirmación",JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

	} catch (Exception e) {
		e.printStackTrace();
		error.printLong(e.getMessage(), this.getClass().toString());
		return false;
		
		// TODO: handle exception
	}
	finally {
		con.desconectar();
	}
	
}


public boolean actualizarProveedor() {
	try {
		String sql1 = "select correo from proveedores where correo = ? and codigo_proveedor != ?";
		con.conectar();
		PreparedStatement comando1 =con.getConexion().prepareStatement(sql1);
		comando1.setString(1, this.getEmail());
		comando1.setInt(   2, this.getId_proveedor());
		ResultSet resultado1 = comando1.executeQuery();
		
		//compa = resultado1.getInt("correo");
		
		if (!resultado1.next()) {
				String sql2 = "select correo from contactos_proveedores where correo = ? and codigo_proveedor != ? ";
				con.conectar();
				PreparedStatement comando2 =con.getConexion().prepareStatement(sql2);
				comando2.setString(1, this.getC_correo());
				comando2.setInt(2, this.getId_proveedor());
				ResultSet resultado2 = comando2.executeQuery();
				
				if (!resultado2.next()) {
					
					String sql = "select fn_actualizar_proveedor(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					con.conectar();
					PreparedStatement comando =con.getConexion().prepareStatement(sql);
					
					comando.setInt(	  1, this.getId_proveedor());
					comando.setString(2, this.getC_nombre());
					comando.setString(3, this.getC_paterno());
					comando.setString(4, this.getC_materno());
					comando.setString(5, this.getC_direccion());
					comando.setString(6, this.getC_telefono());
					comando.setString(7, this.getC_correo());
					comando.setString(8, this.getNombreProveedor());
					comando.setString(9, this.getPais());
					comando.setString(10, this.getEstado());
					comando.setString(11, this.getMunicipio());
					comando.setString(12, this.getCalle());
					comando.setString(13, this.getNumeroExterior());
					comando.setString(14, this.getNumeroInterior());
					comando.setString(15, this.getColonia());
					comando.setString(16, this.getLocalidad());
					comando.setInt(	  17, this.getCp());
					comando.setString(18, this.getEmail());
					comando.setString(19, this.getTelefono());
					comando.setString(20, this.getCelular());
					comando.setString(21, this.getOtro());
					boolean coma = comando.execute();
					
					
					if(coma) {
						
						System.out.println("PROVEEDOR ACTUALIZADO(DAO)");
						return true;
						
					}else {
						System.out.println("HA OCURRIDO UN ERROR EN EL SERVIDOR");
						return false;
					}
					
		
					
				}else {
					JOptionPane.showMessageDialog(null, " EL CORREO DE CONTACTO ESTA SIENDO UTILIZADO POR OTRO PROVEEDOR ","Confirmación",JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
			
			
		}else {
			
			JOptionPane.showMessageDialog(null, " EL CORREO DE PROVEEDOR ESTA SIENDO UTILIZADO POR OTRO PROVEEDOR ","Confirmación",JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

	} catch (Exception e) {
		e.printStackTrace();
		error.printLong(e.getMessage(), this.getClass().toString());
		return false;
		
		// TODO: handle exception
	}
	finally {
		con.desconectar();
	}
	
}*/

public ObservableList<Proveedores> getProveedores(boolean status) throws SQLException {
ResultSet rs = null;
try {
	String sql;
	if (status) {
		sql = "select * from recuperar_valores();";
		
	}else {
		sql = "select * from recuperar_valores_inactivos();;";
	}

con.conectar();
PreparedStatement comando = con.getConexion().prepareStatement(sql);
rs = comando.executeQuery();
elementos = FXCollections.observableArrayList();
while (rs.next()) {
	
Proveedores P = new Proveedores();

P.id_proveedor =	 new SimpleIntegerProperty(rs.getInt("codigo_proveedor"));
P.nombreProveedor =  new SimpleStringProperty(rs.getString("nombre"));
P.pais = 			 new SimpleStringProperty(rs.getString("pais"));
P.estado = 			 new String(rs.getString("estado"));
P.municipio = 		 new SimpleStringProperty(rs.getString("municipio"));
P.calle = 			 new SimpleStringProperty(rs.getString("calle"));
P.numeroExterior =   new SimpleStringProperty(rs.getString("n_exterior"));
P.numeroInterior =   new SimpleStringProperty(rs.getString("n_interior"));
P.colonia =			 new SimpleStringProperty(rs.getString("colonia"));
P.localidad = 		 new SimpleStringProperty(rs.getString("localidad"));
P.cp =				 new SimpleIntegerProperty(rs.getInt("codigo_postal"));
P.email = 			 new SimpleStringProperty(rs.getString("correo"));
P.telefono = 		 new SimpleStringProperty(rs.getString("telefono"));
P.celular =			 new SimpleStringProperty(rs.getString("celular"));
P.otro = 			 new SimpleStringProperty(rs.getString("otro"));

P.c_nombre=			 new SimpleStringProperty(rs.getString("c_nombre"));
P.c_paterno	=		 new SimpleStringProperty(rs.getString("c_apellido_paterno"));
P.c_materno = 		 new SimpleStringProperty(rs.getString("c_apellido_materno"));
P.c_direccion =		 new SimpleStringProperty(rs.getString("c_direccion"));
P.c_correo = 		 new SimpleStringProperty(rs.getString("c_correo"));
P.c_telefono = 		 new SimpleStringProperty(rs.getString("c_telefono"));

elementos.add(P);
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

public boolean eliminar() {
	try {
		String sql = "select fn_eliminar_proveedor (?)";
		con.conectar();
		PreparedStatement comando = con.getConexion().prepareStatement(sql);
		comando.setInt(1, this.getId_proveedor());

		comando.execute();
		return true;
	} catch (Exception e) {
		e.printStackTrace();
		error.printLong(e.getMessage(), this.getClass().toString());
		return false;
		
		// TODO: handle exception
	}
	finally {
		con.desconectar();
	}
}

public String getNombreProveedor() {
	return nombreProveedor.get();
}

public void setNombreProveedor(StringProperty nombreProveedor) {
	this.nombreProveedor = nombreProveedor;
}

public String getPais() {
	return pais.get();
}

public void setPais(StringProperty pais) {
	this.pais = pais;
}


public String getEstado() {
	return estado;
}

public void setEstado(String estado) {
	this.estado = estado;
}

public String getMunicipio() {
	return municipio.get();
}

public void setMunicipio(StringProperty municipio) {
	this.municipio = municipio;
}

public String getCalle() {
	return calle.get();
}

public void setCalle(StringProperty calle) {
	this.calle = calle;
}

public String getNumeroExterior() {
	return numeroExterior.get();
}

public void setNumeroExterior(StringProperty numeroExterior) {
	this.numeroExterior = numeroExterior;
}

public String getNumeroInterior() {
	return numeroInterior.get();
}

public void setNumeroInterior(StringProperty numeroInterior) {
	this.numeroInterior = numeroInterior;
}

public String getColonia() {
	return colonia.get();
}

public void setColonia(StringProperty colonia) {
	this.colonia = colonia;
}

public String getLocalidad() {
	return localidad.get();
}

public void setLocalidad(StringProperty localidad) {
	this.localidad = localidad;
}

public String getEmail() {
	return email.get();
}

public void setEmail(StringProperty email) {
	this.email = email;
}

public String getTelefono() {
	return telefono.get();
}

public void setTelefono(StringProperty telefono) {
	this.telefono = telefono;
}

public Integer getId_proveedor() {
	return id_proveedor.get();
}

public void setId_proveedor(IntegerProperty id_proveedor) {
	this.id_proveedor = id_proveedor;
}

public Integer getCp() {
	return cp.get();
}

public void setCp(IntegerProperty cp) {
	this.cp = cp;
}

public String getCelular() {
	return celular.get();
}

public void setCelular(StringProperty celular) {
	this.celular = celular;
}

public String getOtro() {
	return otro.get();
}

public void setOtro(StringProperty otro) {
	this.otro = otro;
}

public String getC_nombre() {
	return c_nombre.get();
}

public void setC_nombre(StringProperty c_nombre) {
	this.c_nombre = c_nombre;
}

public String getC_paterno() {
	return c_paterno.get();
}

public void setC_paterno(StringProperty c_paterno) {
	this.c_paterno = c_paterno;
}

public String getC_direccion() {
	return c_direccion.get();
}

public void setC_direccion(StringProperty c_direccion) {
	this.c_direccion = c_direccion;
}

public String getC_telefono() {
	return c_telefono.get();
}

public void setC_telefono(StringProperty c_telefono) {
	this.c_telefono = c_telefono;
}

public String getC_correo() {
	return c_correo.get();
}

public void setC_correo(StringProperty c_correo) {
	this.c_correo = c_correo;
}


public String getC_materno() {
	return c_materno.get();
}

public void setC_materno(StringProperty c_materno) {
	this.c_materno = c_materno;
}










}
