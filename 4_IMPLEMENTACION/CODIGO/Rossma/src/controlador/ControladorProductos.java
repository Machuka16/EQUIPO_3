 package controlador;


import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.sun.prism.paint.Color;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Blend;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import modelo.CategoriasProductos;
import modelo.Productos;
import modelo.Proveedores;

public class ControladorProductos implements Initializable {
	private ControladorVentanas ventanas;
	private CategoriasProductos cp;
	private Productos P;
	private float porciento;
	private Float pi = (float) 0.16;
		

	@FXML	TextField	txtMaxima, txtMinima, txtActual, txtPrecio1, txtPrecio2, txtId_Producto, txtClave,txtFiltrar;
	@FXML	TextArea	txaDescripcion; 
	@FXML	Label		lblMensaje, lblBuscar, lblRegistro;
	@FXML	ComboBox<CategoriasProductos> cbCategoria;
	@FXML	ComboBox<String> cbUnidad;
	
	@FXML TitledPane tpGeneral;
	
	
				
	@FXML	TableColumn<Productos, String> tcDescripcion, tcUnidad, tcCategoria, tcClave;
	@FXML 	TableColumn<Productos, Float> tcMaxima, tcMinima, tcActual, tcPrecio1, tcPrecio2;
	
	
	private int filasXPagina;
	private ObservableList<Productos> datos;
	private FilteredList<Productos> datosBusqueda;
	@FXML	TableView<Productos> tablaProductos;
	@FXML	Pagination paginador;
	
	
	
	public ControladorProductos() {	
		error = new ControladorErrores();
		cp = new CategoriasProductos();
		P = new Productos();
		datos = FXCollections.observableArrayList();
		filasXPagina = 10;
		ventanas = ControladorVentanas.getInstancia();
		
	}
	
	ObservableList<String> listCombobox = FXCollections.observableArrayList("Metro","Pieza","Conjunto");
	private ControladorErrores error;
	
	
	
	
	
	/*
	 * Metodo para el paginador
	 */
	private Node createPage(int pageIndex) {
		if(filasXPagina>0){
		   int fromIndex = pageIndex * filasXPagina;
		   int toIndex = Math.min(fromIndex + filasXPagina, datosBusqueda.size());
		   tablaProductos.setItems(FXCollections.observableArrayList(
				   datosBusqueda.subList(fromIndex, toIndex)));
		}
		else{
			tablaProductos.setItems(null);
			paginador.setPageCount(0);
		}
	   return new BorderPane(tablaProductos);
	}	
	
 	@FXML public void buscarTexto(){
 		if(txtFiltrar.getText().trim().isEmpty()){
 			//Llenar TableView
 			datosBusqueda= new FilteredList<>(datos);
 			filasXPagina=10;
			paginador.setPageCount(datosBusqueda.size()/filasXPagina);
			paginador.setPageFactory((Integer pagina) -> createPage(pagina));
			lblMensaje.setText(datosBusqueda.size() + " registros encontrados en la Base de Datos.");
 		}
 		else{
 			try{
	 			datosBusqueda.setPredicate(Productos->Productos.getDescripcion().toLowerCase().
	 					contains(txtFiltrar.getText().toLowerCase()));
	 			if(datosBusqueda.size()<10)
	 				filasXPagina= datosBusqueda.size();
	 			else
	 				filasXPagina=10;
	 			paginador.setPageCount(datosBusqueda.size()/filasXPagina);
				paginador.setPageFactory((Integer pagina) -> createPage(pagina));
				lblMensaje.setText("Se encontraron " + datosBusqueda.size() + " coincidencias.");
 			}
 			catch(Exception ex){
 				//Enviar mensaje
 				lblMensaje.setText("No se encontraron resultados");
 				filasXPagina=0;
 				paginador.setPageCount(filasXPagina); 				
				paginador.setPageFactory((Integer pagina) -> createPage(pagina));
 			}
 		}
 		
 	}
	
	public int contar(){
		int i = 0;
		try {
			P = new Productos();
			if (P.contador() < 10) {
				i = P.contador();
			}else {
				i = 10;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	/*
	 * Metodo para devolver los valores de la tableView a los texfield
	 */
	@FXML public void click_tabla(){
		if(tablaProductos.getSelectionModel().getSelectedItem() != null){
			P = tablaProductos.getSelectionModel().getSelectedItem();
			P.getId_codigo();
			txtClave.setText(P.getClave());
			txtMaxima.setText(P.getMaxima().toString());
			txtMinima.setText(P.getMinima().toString());
			cbCategoria.getSelectionModel().select(P.getCp());
			cbUnidad.getSelectionModel().select(P.getUnidad());
			txaDescripcion.setText(P.getDescripcion());
			txtActual.setText(P.getActual().toString());
			txtPrecio1.setText(P.getPrecio1().toString());
			txtPrecio2.setText(P.getPrecio2().toString());
			
			
			//tpGeneral.setDisable(true);
		}
	}
	
	public int recuperarId(){
		Productos pr = new Productos();
		int c = P.getId_codigo();		
		return c;
	}
	
	/*
	 * Metodo para limpiar los texfield y combobox
	 */
	public void limpiar(){
		lblRegistro.setText("");
		txtClave.clear();
		txaDescripcion.clear();
		txtMaxima.clear();
		txtMinima.clear();
		txtActual.clear();
		txtPrecio1.clear();
		txtPrecio2.clear();		
		cbUnidad.getSelectionModel().select("UNIDAD");		
		cbCategoria.getSelectionModel().selectFirst();;
	}
	
	
	/*
	 * Metodo para boton NUEVO
	 */
	@FXML public void click_nuevo(){
		limpiar();
	}
	
	
	
	
	/*
	 * Metodo para insertar
	 */
	@FXML public void click_insertar(){
		porciento = (float) 1.00 ;
		try {
			if(txtClave.getText().trim().isEmpty() == false){
				if(cbUnidad.getSelectionModel().getSelectedItem() != null){
					if(cbCategoria.getSelectionModel().getSelectedItem() != null){
						if(txaDescripcion.getText().trim().isEmpty() == false){
							if(txtPrecio1.getText().trim().isEmpty() == false){
								if(txtPrecio2.getText().trim().isEmpty() == false){
									if(txtMaxima.getText().trim().isEmpty() == false){
										if(txtMinima.getText().trim().isEmpty() == false){
											if(txtActual.getText().trim().isEmpty() == false){
												int b = recuperarId();
												P = new Productos();
												P.setId_codigo(new SimpleIntegerProperty(b));
												P.setCp(cbCategoria.getSelectionModel().getSelectedItem());
												P.setUnidad(cbUnidad.getSelectionModel().getSelectedItem());
												P.setClave(new SimpleStringProperty(txtClave.getText()));
												P.setDescripcion(new SimpleStringProperty(txaDescripcion.getText()));
												P.setPrecio1(new SimpleFloatProperty(Float.valueOf(txtPrecio1.getText())));
												P.setPrecio2(new SimpleFloatProperty(Float.valueOf(txtPrecio2.getText())));
													Float pr1 = Float.parseFloat(txtPrecio1.getText());
													porciento = pr1 * pi;
													System.out.println(porciento);
												P.setIva(new SimpleFloatProperty(porciento));
												P.setActual(new SimpleFloatProperty(Float.valueOf(txtActual.getText())));
												P.setMaxima(new SimpleFloatProperty(Float.valueOf(txtMaxima.getText())));
												P.setMinima(new SimpleFloatProperty(Float.valueOf(txtMinima.getText())));
												
												boolean res = P.insertarProducto();
													if (res){
														llenarTabla();
														limpiar();
														lblRegistro.setText("Datos Guardados Exitosamente ");
													}else{
														lblRegistro.setText("Se presento un error");
													}			
											}else{
												txtActual.requestFocus();
												lblRegistro.setText("El campo STOCK ACTUAL se encuentra vacio");
												
											}
										}else{
											txtMinima.requestFocus();
											lblRegistro.setText("El campo STOCK MINIMO se encuentra vacio");
											
										}
									}else{
										txtMaxima.requestFocus();
										lblRegistro.setText("El campo STOCK MAXIMO se encuentra vacio");										
																				
									}
								}else{
									txtPrecio2.requestFocus();
									lblRegistro.setText("El campo PRECIO MAYOREO se encuentra vacio");									
									
								}
							}else{								
								txtPrecio1.requestFocus();
								lblRegistro.setText("El campo PRECIO PUBLICO se encuentra vacio");
								
							}
						}else{
							txaDescripcion.requestFocus();
							txaDescripcion.setEditable(true);
							lblRegistro.setText("El campo DESCRIPCION se encuentra vacio");
						}
					}else{
						lblRegistro.setText("El campo CATEGORIA se encuentra vacio");
						
					}
				}else{
					lblRegistro.setText("El campo UNIDAD se encuentra vacio");
					
				}
			}else{
				txtClave.requestFocus();
				lblRegistro.setText("El campo CLAVE se encuentra vacio");
				
			}
		} catch (Exception e) {			
			e.printStackTrace();
			lblRegistro.setText("Revisar el servidor");	
			error.printLong(e.getMessage(), this.getClass().toString());
		}
	}
	
	/*
	 * Metodo para eliminar
	 */
	@FXML public void click_eliminar(){
		try {
			if(txtMaxima.getText().trim().isEmpty()	||	txtMinima.getText().trim().isEmpty()	|| txtClave.getText().trim().isEmpty() ||
					txtActual.getText().trim().isEmpty()	||	txtPrecio1.getText().trim().isEmpty()	||
					txtPrecio2.getText().trim().isEmpty()	||	txaDescripcion.getText().trim().isEmpty()	||
					cbCategoria.getSelectionModel().getSelectedItem() == null	||
					cbUnidad.getSelectionModel().getSelectedItem() == null){
				lblRegistro.setText("Seleccionar un Producto");
			}else{
				int g = recuperarId();
				P = new Productos();				
//				System.out.println(g);
				P.setId_codigo(new SimpleIntegerProperty(g));
				if(P.eliminarProducto() == true){					
					llenarTabla();
					limpiar();
					lblRegistro.setText("Producto eliminado exitosamente");
				}else{
					lblRegistro.setText("Se presento un error al eliminar");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			lblRegistro.setText("Error en el servidor");
			error.printLong(e.getMessage(), this.getClass().toString());
		}
	}
	
	
	@FXML public void click_emergente(){
		ventanas.asignarEmergente("../vista/fxml/vistaCategoria.fxml", "CATEGORIA");
	}
	
	@FXML public void click_reciclaje(){
		ventanas.asignarEmergente("../vista/fxml/vistaReciclajeProductos.fxml", "RECICLAJE");
	}
	
	public void llenarTabla(){
		try {

		datos=P.getProductos();
		filasXPagina = contar();
		datosBusqueda = new FilteredList<>(datos);
		paginador.setPageCount(datosBusqueda.size()/filasXPagina);
		paginador.setPageFactory((Integer pagina) -> createPage(pagina));
		lblMensaje.setText(datos.size() + " Registros encontrados.");
	} catch (Exception e) {
		error.printLong(e.getMessage(), this.getClass().toString());
		e.printStackTrace();
		lblMensaje.setText("Se ha producido un error al recuperar los datos.");
	}
		
}
	@FXML public void actualizar(){
		llenarTabla();
	}
	
	//**********************************************//
	//				INICIALIZAR CONTROLADOR			//							
	//**********************************************//		
	 @Override
		public void initialize(URL arg0, ResourceBundle arg1) {
		 try {
			 
			 cbCategoria.setItems(cp.getCategoriasProductos());
			 cbUnidad.setItems(listCombobox);	
			 cbUnidad.setValue("Selecciona Unidad");
			 tcClave.setCellValueFactory(new PropertyValueFactory<Productos, String>("clave"));
			 tcDescripcion.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcion"));
			 tcUnidad.setCellValueFactory(new PropertyValueFactory<Productos, String>("unidad"));
			 tcCategoria.setCellValueFactory(new PropertyValueFactory<Productos, String>("cp"));
			 tcMaxima.setCellValueFactory(new PropertyValueFactory<Productos, Float>("maxima"));
			 tcMinima.setCellValueFactory(new PropertyValueFactory<Productos, Float>("minima"));
			 tcActual.setCellValueFactory(new PropertyValueFactory<Productos, Float>("actual"));
			 tcPrecio1.setCellValueFactory(new PropertyValueFactory<Productos, Float>("precio1"));
			 tcPrecio2.setCellValueFactory(new PropertyValueFactory<Productos, Float>("precio2"));
			
			
			//tablaProductos.setItems(P.getProductos());
			llenarTabla();
			
			 //datosBusqueda = new FilteredList<>(produc);
			 //paginador.setPageCount(datosBusqueda.size()/filasPagina);
			 //paginador.setPageFactory((Integer pagina) -> createPage(pagina));
		} catch (Exception e) {
			e.printStackTrace();	
			error.printLong(e.getMessage(), this.getClass().toString());
		}		
	}
	 
	 

}
