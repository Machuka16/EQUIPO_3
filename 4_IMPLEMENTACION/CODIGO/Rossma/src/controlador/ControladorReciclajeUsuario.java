package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.NuevoUsuario;

public class ControladorReciclajeUsuario implements Initializable {
	private NuevoUsuario nu;
	
	@FXML TableColumn<NuevoUsuario, String> tcUsuario, tcNombre, tcTipo;
	@FXML TableView<NuevoUsuario> tablaReciclaje;
	
	@FXML TextField txtRestaurar;
	
	@FXML ObservableList<NuevoUsuario> reci;
	@FXML ObservableList<NuevoUsuario> rest;

	private ControladorErrores error;
	
	public ControladorReciclajeUsuario() {
		error = new ControladorErrores();
		nu = new NuevoUsuario();
		reci = FXCollections.observableArrayList();
	}
	
	public ObservableList<NuevoUsuario> restaurar(){
		rest = FXCollections.observableArrayList();
		NuevoUsuario u = new NuevoUsuario();
		u.setCodigo_usuario(new SimpleIntegerProperty());
		u.setStatus(new Boolean(true));
		rest.add(u);
		return rest;
	}
	
	public void actualizarTabla(){
		try {
			nu = new NuevoUsuario();
			reci = nu.getReciclar();
			tablaReciclaje.setItems(reci);
		} catch (Exception e) {
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
		}
	}
	
	@FXML public void click_tabla(){
		if(tablaReciclaje.getSelectionModel().getSelectedItem() != null){
			nu = tablaReciclaje.getSelectionModel().getSelectedItem();
			txtRestaurar.setText(nu.getUsuario());
			nu.getCodigo_usuario();
		}
	}
	
	@FXML public void click_restaurar(){
		try {
			if(txtRestaurar.getText().trim().isEmpty()){
				System.out.println("faltan datos");				
			}else{
				if(nu.actualizarReciclaje() == true){
					nu = new NuevoUsuario();
					nu.getCodigo_usuario();
					actualizarTabla();
					txtRestaurar.clear();					
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			error.printLong(e.getMessage(), this.getClass().toString());
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		NuevoUsuario nu = new NuevoUsuario();
		try {
			tcNombre.setCellValueFactory(new PropertyValueFactory<NuevoUsuario, String>("nombre"));
			tcTipo.setCellValueFactory(new PropertyValueFactory<NuevoUsuario, String>("categoria"));
			tcUsuario.setCellValueFactory(new PropertyValueFactory<NuevoUsuario, String>("usuario"));
			reci= nu.getReciclar();
			tablaReciclaje.setItems(reci);
		}catch(Exception e) {
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
		}
		
	}

}
