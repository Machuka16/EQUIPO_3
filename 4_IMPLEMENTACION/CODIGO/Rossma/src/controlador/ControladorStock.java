package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.beans.InvalidationListener;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Productos;

public class ControladorStock  implements Initializable  {

	private Productos pro;
	
	@FXML TableColumn<Productos, String>tcDescripcion, tcUnidad, tcCategoria, tcCausa;
	@FXML TableColumn<Productos, Float>tcMinima, tcMaxima, tcActual;
	@FXML TableView<Productos> tablaStock;
	@FXML TextField txtClave, txtProducto, txtActual, txtNueva;
	@FXML TextArea txaCausa;
	
	private ObservableList<Productos> stock;
	private ControladorErrores error;
	private Integer id;
	
	public ControladorStock() {
		error = new ControladorErrores();
		pro = new Productos();
		stock = FXCollections.observableArrayList();
	}
	
	public void actualizarTabla(){
		try {
			pro = new Productos();
			stock = pro.getProductos();
			tablaStock.setItems(stock);
		} catch (Exception e) {
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
		}
	}
	
	@FXML public void limpiar(){
		txtClave.clear();
		txtProducto.clear();
		txtActual.clear();
		txtNueva.clear();
		txaCausa.clear();
	}
	
	
	@FXML public void seleccionar(){
		
		if(tablaStock.getSelectionModel().getSelectedItem() != null){
			pro = tablaStock.getSelectionModel().getSelectedItem();
			id = pro.getId_codigo();
			txtClave.setText(pro.getClave());
			txtProducto.setText(pro.getDescripcion());
			txtActual.setText(pro.getActual().toString());
			
		}
	}

	@FXML public void actualizar(){
		try {
			if(txtNueva.getText().trim().isEmpty() == false){
				if(txaCausa.getText().trim().isEmpty() == false){
					pro = new Productos();
					System.out.println("seleccionar "+id);
					pro.setId_codigo(new SimpleIntegerProperty(id));
					pro.setActual(new SimpleFloatProperty(Float.valueOf(txtNueva.getText())));
					pro.setCausa(new SimpleStringProperty(txaCausa.getText()));
					if(pro.actualizarExistencia()){
						limpiar();
						actualizarTabla();
					}else{
						System.out.println();
					}
				}else{
					txaCausa.requestFocus();
					txaCausa.setEditable(true);
					JOptionPane.showMessageDialog(null, " FAVOR DE LLENAR EL CAMPO CAUSA","ALERTA",JOptionPane.INFORMATION_MESSAGE);
				}
			}else{
				txtNueva.requestFocus();
				JOptionPane.showMessageDialog(null, " FAVOR DE LLENAR EL CAMPO EXISTENCIA NUEVA","ALERTA",JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
		}
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			tcDescripcion.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcion"));
			tcUnidad.setCellValueFactory(new PropertyValueFactory<Productos, String>("unidad"));
			tcCategoria.setCellValueFactory(new PropertyValueFactory<Productos, String>("cp"));
			tcMaxima.setCellValueFactory(new PropertyValueFactory<Productos, Float>("maxima"));
			tcMinima.setCellValueFactory(new PropertyValueFactory<Productos, Float>("minima"));
			tcActual.setCellValueFactory(new PropertyValueFactory<Productos, Float>("actual"));
			tcCausa.setCellValueFactory(new PropertyValueFactory<Productos, String>("causa"));
			txtClave.setDisable(true);
			txtProducto.setDisable(true);
			txtActual.setDisable(true);
			stock = pro.getProductos();
			tablaStock.setItems(stock);
		} catch (Exception e) {
			// TODO: handle exception
			error.printLong(e.getMessage(), this.getClass().toString());
		}
		
	}
	

}