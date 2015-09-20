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
import modelo.Productos;

public class ControladorReciclajeProductos implements Initializable {
	private Productos pro;
	
	@FXML TableColumn<Productos, String> tcDescripcion, tcUnidad, tcCategoria;
	@FXML TableView<Productos> tablaReciclar;

	@FXML TextField txtNombre;
	
	@FXML ObservableList<Productos> reci;
	@FXML ObservableList<Productos> rest;

	private ControladorErrores error;
	
	public ControladorReciclajeProductos() {
		error = new ControladorErrores();
		pro = new Productos();
		reci = FXCollections.observableArrayList();
	}
	
	public ObservableList<Productos> restaurar(){
		rest = FXCollections.observableArrayList();
		Productos p = new Productos();
		p.setId_codigo(new SimpleIntegerProperty());
		p.setStatus(new Boolean(true));
		rest.add(p);
		return rest;
	}
	
	public void actualizarTabla(){
		try {
			pro = new Productos();
			reci = pro.getReciclar();
			tablaReciclar.setItems(reci);
		} catch (Exception e) {
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
		}
	}
	
	@FXML public void click_tabla(){
		if(tablaReciclar.getSelectionModel().getSelectedItem() != null){
			pro = tablaReciclar.getSelectionModel().getSelectedItem();
			txtNombre.setText(pro.getDescripcion());	
			
		}
	}
	
	@FXML public void click_restaurar(){
		try {			
			if(txtNombre.getText().trim().isEmpty()){
				System.out.println("faltan datos");
			}else{
				
				if(pro.actualizarReciclaje() == true){
					pro = new Productos();
					pro.getId_codigo();
					actualizarTabla();
					System.out.println("se restauro");
					txtNombre.clear();
				}else{
					System.out.println("no se restauro");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Productos pro = new Productos();
		try {			
			
				tcDescripcion.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcion"));
				tcUnidad.setCellValueFactory(new PropertyValueFactory<Productos, String>("unidad"));
				tcCategoria.setCellValueFactory(new PropertyValueFactory<Productos, String>("cp"));
				reci = pro.getReciclar();
				tablaReciclar.setItems(reci);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
		}
		
	}

}
