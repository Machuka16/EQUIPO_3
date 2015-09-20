package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.prism.impl.Disposer.Record;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Clientes {
	private String Pais;
	private StringProperty Localidad,Colonia,Calle,NumeroExterior,NumeroInterior,Telefono,Celular,Email,
	Estado,Tipo, Municipio,Otro, direccionCompleta,nombreCompleto, nombresCombo;
	
	private IntegerProperty Codigo_cliente,Codigo_postal;
	private ObservableList<Clientes> fisicas;
	private ObservableList<Clientes> morales;
	private ObservableList<Clientes> reciclar;
	private ObservableList<Clientes> nomCombo;
	
	
	
	private Conexion con;
	private ClientesFisicos F;
	private ClientesMorales M;
	
	
	
	public Clientes(){
		Pais= new String("Mexico");
		Estado =  Tipo = direccionCompleta = nombreCompleto = Localidad=Colonia=Calle=NumeroExterior=
		NumeroInterior=Telefono=Celular= Email= nombresCombo= new SimpleStringProperty();		
		Codigo_cliente=Codigo_postal = new SimpleIntegerProperty();
		F = new ClientesFisicos();
		M = new ClientesMorales();
		con = Conexion.getInstancia();		 
		nomCombo = FXCollections.observableArrayList();
	}
	
		

	public String getDireccionCompleta() {
		return direccionCompleta.get();
	}

	public void setDireccionCompleta(StringProperty direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}

	public String getNombreCompleto() {
		return nombreCompleto.get();
	}

	public void setNombreCompleto(StringProperty nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	
	/*public String toString(){
		if(nombreCompleto.isEmpty().get()==false)
		return nombreCompleto.get();
		else
		return M.getEmpresa();	
	}*/
	
	public String toString(){
		return nombresCombo.get();
	}


	public String getPais() {
		return Pais;
	}


	public void setPais(String pais) {
		Pais = pais;
	}

	public String getEstado() {
		return Estado.get();
	}

	public void setEstado(StringProperty estado) {
		Estado = estado;
	}
	
	public String getMunicipio() {
		return Municipio.get();
	}

	public void setMunicipio(StringProperty municipio) {
		Municipio = municipio;
	}

	public String getLocalidad() {
		return Localidad.get();
	}

	public void setLocalidad(StringProperty localidad) {
		Localidad = localidad;
	}

	public String getColonia() {
		return Colonia.get();
	}

	public void setColonia(StringProperty colonia) {
		Colonia = colonia;
	}

	public String getCalle() {
		return Calle.get();
	}

	public void setCalle(StringProperty calle) {
		Calle = calle;
	}
	
	public String getNumeroExterior() {
		return NumeroExterior.get();
	}

	public void setNumeroExterior(StringProperty numeroExterior) {
		NumeroExterior = numeroExterior;
	}

	public String getNumeroInterior() {
		return NumeroInterior.get();
	}

	public void setNumeroInterior(StringProperty numeroInterior) {
		NumeroInterior = numeroInterior;
	}
	
	public String getTelefono() {
		return Telefono.get();
	}

	public void setTelefono(StringProperty telefono) {
		Telefono = telefono;
	}

	public String getCelular() {
		return Celular.get();
	}

	public void setCelular(StringProperty celular) {
		Celular = celular;
	}

	public String getOtro() {
		return Otro.get();
	}

	public void setOtro(StringProperty otro) {
		Otro = otro;
	}

	public String getEmail() {
		return Email.get();
	}

	public void setEmail(StringProperty email) {
		Email = email;
	}

	public Integer getCodigo_cliente() {
		return Codigo_cliente.get();
	}

	public void setCodigo_cliente(IntegerProperty codigo_cliente) {
		Codigo_cliente = codigo_cliente;
	}

	public Integer getCodigo_postal() {
		return Codigo_postal.get();
	}
	
	public void setCodigo_postal(IntegerProperty codigo_postal) {
		Codigo_postal = codigo_postal;
	}
	
	public ClientesFisicos getF() {
		return F;
	}

	public void setF(ClientesFisicos f) {
		F = f;
	}

	public String getTipo() {
		return Tipo.get();
	}

	public void setTipo(StringProperty tipo) {
		Tipo = tipo;
	}
	
	
	public ClientesMorales getM() {
		return M;
	}

	public void setM(ClientesMorales m) {
		M = m;
	}	
	
	public StringProperty getNombresCombo() {
		return nombresCombo;
	}

	public void setNombresCombo(StringProperty nombresCombo) {
		this.nombresCombo = nombresCombo;
	}

	
	

	public int contador(boolean cliente) throws SQLException {
	int o = 0;
	ResultSet rs = null;
	try {
		String sql = " ";
		if (cliente) {
			sql = "select count(codigo_cliente) from clientes_personas_fisicas where status = true";
		}else {
			sql = "select count(id_cliente_moral) from clientes_personas_morales where status = true";
		}
		con.conectar();
		PreparedStatement reci = con.getConexion().prepareStatement(sql);
		rs = reci.executeQuery();
//		reciclar = FXCollections.observableArrayList();
		while (rs.next()){
			o = rs.getInt("count");
//			o = rs.getInt("id_cliente_moral");
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
	
	public ObservableList<Clientes> getNombreCombo() throws SQLException{
		ResultSet rs = null;
		try {
			String sql = "select concat(nombre, ' ', apellido_paterno, ' ', apellido_materno) as nombreCombo from clientes_personas_fisicas cf where cf.status = TRUE UNION select empresa from clientes_personas_morales cm where cm.status = TRUE";
			con.conectar();
			PreparedStatement combo = con.getConexion().prepareStatement(sql);
			rs = combo.executeQuery();
			while(rs.next()){
				Clientes cl = new Clientes();
				cl.nombresCombo = new SimpleStringProperty(rs.getString("nombreCombo"));
				nomCombo.add(cl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			rs.close();
			con.desconectar();
		}
		return nomCombo;
	}
	
	/*******************Metodos de personas fisicas*************************/
	
	public ObservableList<Clientes> getReciclar() throws SQLException{
		ResultSet rs = null;
		try {
			String sql = "select codigo_cliente, rfc, concat(nombre, ' ', apellido_paterno, ' ', apellido_materno) as nombreCompleto, nombre, apellido_paterno, apellido_materno, status from clientes_personas_fisicas where status = 'f'";
			con.conectar();
			PreparedStatement reci = con.getConexion().prepareStatement(sql);
			rs = reci.executeQuery();
			reciclar = FXCollections.observableArrayList();
			while (rs.next()){
				Clientes cl = new Clientes();
				cl.Codigo_cliente = new SimpleIntegerProperty(rs.getInt("codigo_cliente"));
				cl.F.setRfc(new SimpleStringProperty(rs.getString("rfc")));
				cl.F.setCompleto(new SimpleStringProperty(rs.getString("nombreCompleto")));
				cl.F.setNombre(new SimpleStringProperty(rs.getString("nombre")));
				cl.F.setApellido_paterno(new SimpleStringProperty(rs.getString("apellido_paterno")));
				cl.F.setApellido_materno(new SimpleStringProperty(rs.getString("apellido_materno")));
				reciclar.add(cl);
			}			
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}finally{
			rs.close();
			con.desconectar();
		}
		return reciclar;
	}
	
	
	
	public ObservableList<Clientes> getClientesFisicos() throws SQLException{
		ResultSet rs = null;
		try {
			String sql = "select * from recuperar_clientesFisicos ()";
			
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs = comando.executeQuery();
			fisicas = FXCollections.observableArrayList();
			while (rs.next()){
				Clientes C = new Clientes();
				C.Codigo_cliente = new SimpleIntegerProperty(rs.getInt("codigoFisico"));
				C.F.setRfc(new SimpleStringProperty(rs.getString("rfc").toString()));
				C.F.setNombre (new SimpleStringProperty(rs.getString("nombre")));
				C.F.setApellido_paterno (new SimpleStringProperty(rs.getString("apaterno")));
				C.F.setApellido_materno(new SimpleStringProperty(rs.getString("amaterno")));
				C.nombreCompleto = new SimpleStringProperty(rs.getString("nombreCompleto"));				
				C.Pais = new String(rs.getString("pais"));
				C.Estado = new SimpleStringProperty(rs.getString("estado"));
				C.Municipio = new SimpleStringProperty(rs.getString("municipio"));				
				C.Calle = new SimpleStringProperty(rs.getString("calle"));
				C.NumeroExterior = new SimpleStringProperty(rs.getString("nExterior"));
				C.NumeroInterior = new SimpleStringProperty(rs.getString("nInterior"));
				C.Colonia = new SimpleStringProperty(rs.getString("colonia"));
				C.Localidad = new SimpleStringProperty(rs.getString("localidad"));
				C.Codigo_postal =  new SimpleIntegerProperty(rs.getInt("codPostal"));
				C.direccionCompleta = new SimpleStringProperty(rs.getString("direccionCompleta"));
				C.Telefono = new SimpleStringProperty(rs.getString("telefono"));				
				C.Email =	new SimpleStringProperty(rs.getString("correo"));
				C.Celular = new SimpleStringProperty(rs.getString("celular"));
				C.Otro = new SimpleStringProperty(rs.getString("otro"));
				C.Tipo = new SimpleStringProperty(rs.getString("tipo"));
				fisicas.add(C);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			rs.close();
			con.desconectar();
		}
		return fisicas;
	}
	
	public boolean insertarClientesFisicos(){
		ResultSet rs = null;
		try {
			String sq= "select codigo_cliente from clientes where codigo_cliente = ?";
			con.conectar();
			PreparedStatement comparar = con.getConexion().prepareStatement(sq);
			comparar.setInt(1, this.getCodigo_cliente());
			rs = comparar.executeQuery();
			if(rs.next()){
				String sql = "select fn_actualizar_clientesFisicos(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				con.conectar();
				PreparedStatement actualizar = con.getConexion().prepareStatement(sql);
				actualizar.setInt(1, this.getCodigo_cliente());
				actualizar.setString(2, this.getPais());
				actualizar.setString(3, this.getEstado());
				actualizar.setString(4, this.getMunicipio());
				actualizar.setString(5, this.getCalle());
				actualizar.setString(6, this.getNumeroExterior());
				actualizar.setString(7, this.getNumeroInterior());
				actualizar.setString(8, this.getColonia());
				actualizar.setString(9, this.getLocalidad());
				actualizar.setInt(10, this.getCodigo_postal());
				actualizar.setString(11, this.getEmail());
				actualizar.setString(12, this.getTelefono());
				actualizar.setString(13, this.getCelular());
				actualizar.setString(14, this.getOtro());
				actualizar.setString(15, this.getTipo());
				actualizar.setString(16, this.getF().getRfc());
				actualizar.setString(17, this.getF().getNombre());
				actualizar.setString(18, this.getF().getApellido_paterno());
				actualizar.setString(19, this.getF().getApellido_materno());
				actualizar.execute();
			}else{
				String sql = "select fn_agregar_cliente_fisico(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				con.conectar();
				PreparedStatement insertarFisicos = con.getConexion().prepareStatement(sql);
				insertarFisicos.setString(1, this.getPais());
				insertarFisicos.setString(2, this.getEstado());
				insertarFisicos.setString(3, this.getMunicipio());
				insertarFisicos.setString(4, this.getCalle());
				insertarFisicos.setString(5, this.getNumeroExterior());
				insertarFisicos.setString(6, this.getNumeroInterior());
				insertarFisicos.setString(7,  this.getColonia());
				insertarFisicos.setString(8,  this.getLocalidad());
				insertarFisicos.setInt(9, this.getCodigo_postal());
				insertarFisicos.setString(10, this.getEmail());
				insertarFisicos.setString(11, this.getTelefono());
				insertarFisicos.setString(12, this.getCelular());
				insertarFisicos.setString(13, this.getOtro());
				insertarFisicos.setString(14, this.getF().getRfc());
				insertarFisicos.setString(15, this.getF().getNombre());
				insertarFisicos.setString(16, this.getF().getApellido_paterno());
				insertarFisicos.setString(17, this.getF().getApellido_materno());
				insertarFisicos.setString(18, this.getTipo());
				insertarFisicos.execute();
			}
			return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}finally{
				con.desconectar();
		}
	}
	
	public boolean eliminarClienteFisico(){
		try{
			String sql = "select fn_eliminar_clienteFisico (?)";
			con.conectar();
			PreparedStatement eliminarFisico = con.getConexion().prepareStatement(sql);
			eliminarFisico.setInt(1, this.getCodigo_cliente());
			eliminarFisico.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			con.desconectar();
		}
	}
	
	public boolean actualizarClienteFisico(){
		try {
			String sql = "select fn_actualizar_cliente_fisico (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			con.conectar();
			PreparedStatement actualizarFisico = con.getConexion().prepareStatement(sql);
			actualizarFisico.setInt(1,this.getCodigo_cliente());
			actualizarFisico.setInt(2, this.getF().getId_cliente_fisico());
			actualizarFisico.setString(3, this.getF().getRfc().toString());
			actualizarFisico.setString(4, this.getF().getNombre());
			actualizarFisico.setString(5, this.getF().getApellido_paterno());
			actualizarFisico.setString(6, this.getF().getApellido_materno());
			actualizarFisico.setString(7, this.getPais());
			actualizarFisico.setString(8, this.getEstado());
			actualizarFisico.setString(9,this.getMunicipio());
			actualizarFisico.setString(10, this.getCalle());
			actualizarFisico.setString(11, this.getNumeroExterior());
			actualizarFisico.setString(12, this.getNumeroInterior());
			actualizarFisico.setString(13,  this.getColonia());
			actualizarFisico.setString(14,  this.getLocalidad());
			actualizarFisico.setInt(15, this.getCodigo_postal());
			actualizarFisico.setString(16, this.getEmail());
			actualizarFisico.setString(17, this.getTelefono());
			actualizarFisico.setString(18, this.getCelular());
			actualizarFisico.setString(19, this.getOtro());
			actualizarFisico.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			con.desconectar();
		}
	}
	
	/*******************Metodos de personas Morales*************************/
	
	
	public ObservableList<Clientes> getClientesMorales() throws SQLException{
		ResultSet rs = null;
		try {
			String sql = "select * from recuperar_clientesMorales()";
			
			con.conectar();
			PreparedStatement comando = con.getConexion().prepareStatement(sql);
			rs = comando.executeQuery();
			morales = FXCollections.observableArrayList();
			while (rs.next()){
				Clientes C = new Clientes();
				C.Codigo_cliente = new SimpleIntegerProperty(rs.getInt("codigoMoral"));
				C.M.setRfc(new SimpleStringProperty(rs.getString("rfc").toString()));
				C.M.setEmpresa(new SimpleStringProperty(rs.getString("empresa")));
				C.M.setNombre(new SimpleStringProperty(rs.getString("nombre")));
				C.M.setApellido_paterno(new SimpleStringProperty(rs.getString("apaterno")));
				C.M.setApellido_materno(new SimpleStringProperty(rs.getString("amaterno")));
				C.M.setTelefono(new SimpleStringProperty(rs.getString("telefonoContacto")));
				C.M.setCorreo(new SimpleStringProperty(rs.getString("correoContacto")));
				C.Pais = new String(rs.getString("pais"));
				C.Estado = new SimpleStringProperty(rs.getString("estado"));
				C.Municipio = new SimpleStringProperty(rs.getString("municipio"));				
				C.Calle = new SimpleStringProperty(rs.getString("calle"));
				C.NumeroExterior = new SimpleStringProperty(rs.getString("nExterior"));
				C.NumeroInterior = new SimpleStringProperty(rs.getString("nInterior"));
				C.Colonia = new SimpleStringProperty(rs.getString("colonia"));
				C.Localidad = new SimpleStringProperty(rs.getString("localidad"));
				C.Codigo_postal =  new SimpleIntegerProperty(rs.getInt("codPostal"));
				C.direccionCompleta = new SimpleStringProperty(rs.getString("direccionCompleta"));
				C.Telefono = new SimpleStringProperty(rs.getString("telefono"));				
				C.Email =	new SimpleStringProperty(rs.getString("correo"));
				C.Celular = new SimpleStringProperty(rs.getString("celular"));
				C.Otro = new SimpleStringProperty(rs.getString("otro"));
				C.Tipo = new SimpleStringProperty(rs.getString("tipo"));
				morales.add(C);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			rs.close();
			con.desconectar();
		}
		return morales;
	}
	
	public boolean insertarClientesMorales(){		
			ResultSet rs = null;
			try {
				String sq= "select codigo_cliente from clientes where codigo_cliente = ?";
				con.conectar();
				PreparedStatement comparar = con.getConexion().prepareStatement(sq);
				comparar.setInt(1, this.getCodigo_cliente());
				rs = comparar.executeQuery();
				if(rs.next()){
					String sql = "select fn_actualizar_clientesMorales(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					con.conectar();
					PreparedStatement actualizar = con.getConexion().prepareStatement(sql);
					actualizar.setInt(1, this.getCodigo_cliente());
					actualizar.setString(2, this.getPais());
					actualizar.setString(3, this.getEstado());
					actualizar.setString(4, this.getMunicipio());
					actualizar.setString(5, this.getCalle());
					actualizar.setString(6, this.getNumeroExterior());
					actualizar.setString(7, this.getNumeroInterior());
					actualizar.setString(8, this.getColonia());
					actualizar.setString(9, this.getLocalidad());
					actualizar.setInt(10, this.getCodigo_postal());
					actualizar.setString(11, this.getEmail());
					actualizar.setString(12, this.getTelefono());
					actualizar.setString(13, this.getCelular());
					actualizar.setString(14, this.getOtro());
					actualizar.setString(15, this.getTipo());
					actualizar.setString(16, this.getM().getRfc());
					actualizar.setString(17, this.getM().getEmpresa());
					actualizar.setString(18, this.getM().getNombre());
					actualizar.setString(19, this.getM().getApellido_paterno());
					actualizar.setString(20, this.getM().getApellido_materno());
					actualizar.setString(21, this.getM().getTelefono());
					actualizar.setString(22, this.getM().getCorreo());
					actualizar.execute();
				}else{
					String sql = "select fn_agregar_cliente_moral(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					con.conectar();
					PreparedStatement insertarMorales = con.getConexion().prepareStatement(sql);
					insertarMorales.setString(1, this.getPais());
					insertarMorales.setString(2, this.getEstado());
					insertarMorales.setString(3,this.getMunicipio());
					insertarMorales.setString(4, this.getCalle());
					insertarMorales.setString(5, this.getNumeroExterior());
					insertarMorales.setString(6, this.getNumeroInterior());
					insertarMorales.setString(7,  this.getColonia());
					insertarMorales.setString(8,  this.getLocalidad());
					insertarMorales.setInt(9, this.getCodigo_postal());
					insertarMorales.setString(10, this.getEmail());
					insertarMorales.setString(11, this.getTelefono());
					insertarMorales.setString(12, this.getCelular());
					insertarMorales.setString(13, this.getOtro());
					insertarMorales.setString(14, this.getTipo());
					insertarMorales.setString(15, this.getM().getRfc());
					insertarMorales.setString(16, this.getM().getEmpresa());
					insertarMorales.setString(17, this.getM().getNombre());
					insertarMorales.setString(18, this.getM().getApellido_paterno());
					insertarMorales.setString(19, this.getM().getApellido_materno());
					insertarMorales.setString(20, this.getM().getTelefono());
					insertarMorales.setString(21, this.getM().getCorreo());			
					insertarMorales.execute();
				}
			return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}finally{
				con.desconectar();
		}
	}
	
	public boolean eliminarClienteMorales(){
		try{
			String sql = "select fn_eliminar_clienteMorales(?)";
			con.conectar();
			PreparedStatement eliminarFisico = con.getConexion().prepareStatement(sql);
			eliminarFisico.setInt(1, this.getCodigo_cliente());
			eliminarFisico.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			con.desconectar();
		}
	}
	
	public boolean actualizarClienteMorales(){
		try {
			String sql = "select fn_actualizar_cliente_morales (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			con.conectar();
			PreparedStatement actualizarMoral = con.getConexion().prepareStatement(sql);
			actualizarMoral.setInt(1, this.getCodigo_cliente());
			actualizarMoral.setString(2, this.getPais());
			actualizarMoral.setString(3, this.getEstado());
			actualizarMoral.setString(4,this.getMunicipio());
			actualizarMoral.setString(5, this.getCalle());
			actualizarMoral.setString(6, this.getNumeroExterior());
			actualizarMoral.setString(7, this.getNumeroInterior());
			actualizarMoral.setString(8,  this.getColonia());
			actualizarMoral.setString(9,  this.getLocalidad());
			actualizarMoral.setInt(10, this.getCodigo_postal());
			actualizarMoral.setString(11, this.getEmail());
			actualizarMoral.setString(12, this.getTelefono());
			actualizarMoral.setString(13, this.getCelular());
			actualizarMoral.setString(14, this.getOtro());
			actualizarMoral.setInt(15, this.getM().getId_cliente_moral());
			actualizarMoral.setString(16, this.getM().getRfc());
			actualizarMoral.setString(17, this.getM().getEmpresa());
			actualizarMoral.setInt(18, this.getM().getId_contacto());
			actualizarMoral.setString(19, this.getM().getNombre());
			actualizarMoral.setString(20, this.getM().getApellido_paterno());
			actualizarMoral.setString(21, this.getM().getApellido_materno());
			actualizarMoral.setString(22, this.getM().getTelefono());
			actualizarMoral.setString(23, this.getM().getCorreo());
			actualizarMoral.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			con.desconectar();
		}
	}
	
	
	
	
	
}