package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.CategoriasProductos;

public class ControladorCategorias implements Initializable {
	
	private CategoriasProductos catP;
	private ObservableList<CategoriasProductos> datos;
	@FXML TextField txtId_categoria, txtCategoria;
	@FXML Label lblMensaje;
	@FXML Button btnNuevo, btnModificar, btnInsertar, btnEliminar;
	
	@FXML TableView<CategoriasProductos> tablaCategorias;
	@FXML TableColumn<CategoriasProductos, Integer>tcId_categoria;
	@FXML TableColumn<CategoriasProductos, String>tcCategoria;
	private ControladorErrores error;
	
	
	/*
	 * Constructor de la clase
	 */
	public ControladorCategorias() {
		error = new ControladorErrores();
		catP = new CategoriasProductos();
		datos = FXCollections.observableArrayList();
	}
	
	public void actualizarTabla(){
		try {
			catP = new CategoriasProductos();
			datos = catP.getCategoriasProductos();
			tablaCategorias.setItems(datos);
		} catch (Exception e) {
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
		}
	}
	
	/*
	 * Metodo para limpiar los textfield
	 */
	public void limpiar(){
		//txtId_categoria.clear();
		txtCategoria.clear();
		
	}
	
	/*
	 * Metodo para regresar los valores de la TableView a los Texfield
	 */
	@FXML public void click_tabla(){
		if(tablaCategorias.getSelectionModel().getSelectedItem() != null){
			catP = tablaCategorias.getSelectionModel().getSelectedItem();
			catP.getId_categoria();
			//txtId_categoria.setText(catP.getId_categoria().toString());
			txtCategoria.setText(catP.getCategoria());
		}
	}
	
	public int recuperarId(){
		CategoriasProductos ca = new CategoriasProductos();
		int a = catP.getId_categoria();
		System.out.println(a);
		return a;
	}
	
	/*
	 * #Region boton NUEVO
	 */
	@FXML public void click_nuevo(){
		limpiar();
		actualizarTabla();
	}
	
	/*
	 * #Region boton INSERTAR
	 */
	@FXML public void click_insertar(){
		try {
			if(txtCategoria.getText().trim().isEmpty()){
				lblMensaje.setText("Favor de acompletar los datos");
			}else{
				int b = recuperarId();
				catP = new CategoriasProductos();
				catP.setId_categoria(new SimpleIntegerProperty(b));
				catP.setCategoria(new SimpleStringProperty(txtCategoria.getText()));
					boolean res = catP.insertar();
					if(res){
						lblMensaje.setText("Datos Guardados Exitosamente");
						limpiar();
						actualizarTabla();
					}else{
						lblMensaje.setText("Error al insertar");
					}
				
				}			
		} catch (Exception e) {
			error.printLong(e.getMessage(), this.getClass().toString());
			// TODO: handle exception
		}		
	}
	
	@FXML public void click_eliminar(){
		try {
			if(txtCategoria.getText().isEmpty()){
				lblMensaje.setText("seleccionar categoria");
			}else{
				int c = recuperarId();
				catP = new CategoriasProductos();
				catP.setId_categoria(new SimpleIntegerProperty(c));
				System.out.println(c);
					if(catP.eliminar() == true){
						lblMensaje.setText("Categoria Eliminada");
						limpiar();
						actualizarTabla();
					}else{
						lblMensaje.setText("Error al eliminar");
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
			lblMensaje.setText("Error en el servidor");
			error.printLong(e.getMessage(), this.getClass().toString());
		}
	}
	
	@FXML public void click_actualizar(){
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			tcCategoria.setCellValueFactory(	 	new PropertyValueFactory<CategoriasProductos, String>("categoria"));
			//tcId_categoria.setCellValueFactory(	new PropertyValueFactory<CategoriasProductos, Integer>("id_categoria"));
			tablaCategorias.setItems(catP.getCategoriasProductos());
		} catch (Exception e) {
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
			
		}
		
	}

}
