package modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClientesMorales {
	public StringProperty Rfc,Empresa,Nombre,Apellido_paterno,Apellido_materno,Telefono,Correo;
	
	private IntegerProperty Id_cliente_moral, Id_contacto;
	private Conexion con;
	
	



	public ClientesMorales(){
		
		Rfc = Empresa=Nombre=Apellido_paterno=Apellido_materno=Telefono=Correo = new SimpleStringProperty();
		Id_cliente_moral = Id_contacto = new SimpleIntegerProperty();
		con = Conexion.getInstancia();
	}

	
	public String getRfc() {
		return Rfc.get();
	}



	public void setRfc(StringProperty rfc) {
		Rfc = rfc;
	}
	public String getEmpresa() {
		return Empresa.get();
	}

	public void setEmpresa(StringProperty empresa) {
		Empresa = empresa;
	}
	
	public String toString1(){
		return Empresa.get();
	}

	public String getNombre() {
		return Nombre.get();
	}

	public void setNombre(StringProperty nombre) {
		Nombre = nombre;
	}

	public String getApellido_paterno() {
		return Apellido_paterno.get();
	}

	public void setApellido_paterno(StringProperty apellido_paterno) {
		Apellido_paterno = apellido_paterno;
	}

	public String getApellido_materno() {
		return Apellido_materno.get();
	}

	public void setApellido_materno(StringProperty apellido_materno) {
		Apellido_materno = apellido_materno;
	}

	public String getTelefono() {
		return Telefono.get();
	}

	public void setTelefono(StringProperty telefono) {
		Telefono = telefono;
	}

	public String getCorreo() {
		return Correo.get();
	}

	public void setCorreo(StringProperty correo) {
		Correo = correo;
	}

	public Integer getId_cliente_moral() {
		return Id_cliente_moral.get();
	}

	public void setId_cliente_moral(IntegerProperty id_cliente_moral) {
		Id_cliente_moral = id_cliente_moral;
	}

	public Integer getId_contacto() {
		return Id_contacto.get();
	}

	public void setId_contacto(IntegerProperty id_contacto) {
		this.Id_contacto = id_contacto;
	}
	
	
	public String toString(){
		return Rfc.getValue();
	}
	
	
	
}
