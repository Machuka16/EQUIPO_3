package modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class ClientesFisicos {
	public StringProperty Nombre, Apellido_paterno, Apellido_materno,Rfc, completo;
	private IntegerProperty Id_cliente_fisico;
	
	private Conexion con;
	
	
	public ClientesFisicos(){
		
		Rfc=Nombre = Apellido_paterno = Apellido_materno = new SimpleStringProperty();
		Id_cliente_fisico = new SimpleIntegerProperty();
		con = Conexion.getInstancia();
		
	}

	public String getRfc() {
		return Rfc.get();
	}

	public void setRfc(StringProperty rfc) {
		Rfc = rfc;
	}
	
	
	public String getCompleto() {
		return completo.get();
	}

	public void setCompleto(StringProperty completo) {
		this.completo = completo;
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

	public Integer getId_cliente_fisico() {
		return Id_cliente_fisico.get();
	}

	public void setId_cliente_fisico(IntegerProperty id_cliente_fisico) {
		Id_cliente_fisico = id_cliente_fisico;
	}

	public String toString(){
		return Rfc.getValue();
	}

	
}
