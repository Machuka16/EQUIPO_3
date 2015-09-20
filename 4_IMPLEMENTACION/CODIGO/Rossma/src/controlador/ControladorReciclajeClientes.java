package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import modelo.Clientes;
import modelo.ClientesFisicos;
import modelo.Productos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControladorReciclajeClientes implements Initializable {
	private Clientes cl;
	
	@FXML TextField txtNombre;
	@FXML TableView<Clientes> tablaClientes;
	@FXML TableColumn<Clientes, String> tcRfc, tcNombre;
	
	@FXML ObservableList<Clientes> reci;
	@FXML ObservableList<Clientes> rest;
	
	public ControladorReciclajeClientes() {
		cl = new Clientes();
		
		reci = FXCollections.observableArrayList();
		rest = FXCollections.observableArrayList();		
	}
	

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Clientes cl = new Clientes();
	
		try {
			tcRfc.setCellValueFactory(c->c.getValue().getF().Rfc);
			tcNombre.setCellValueFactory(c->c.getValue().getF().Nombre);
			//tcNombre.setCellValueFactory(new PropertyValueFactory<Clientes, String>("F"));
			reci = cl.getReciclar();
			tablaClientes.setItems(reci);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
	}

}
