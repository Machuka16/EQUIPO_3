package controlador;

import java.awt.color.CMMException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.crypto.spec.PSource;
import javax.swing.JOptionPane;

import modelo.Clientes;
import modelo.ClientesMorales;
import modelo.DetalleVenta;
import modelo.NuevoUsuario;
import modelo.Productos;
import modelo.Usuario;
import modelo.Ventas;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.embed.swt.FXCanvas;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class ControladorVentas implements Initializable {
	@FXML TextField txtCantidad, txtPrecio, txtProducto, txtExistencia, txtBuscador, txtUnidad, txtTotal, txtVendedor, txtFolio,txtId;
	@FXML Button btnAgregar;
	@FXML Label lblMensaje, lblRegistro;
	@FXML TableView<DetalleVenta> tablaVenta;
	@FXML ListView<Productos> listaProducto;
	@FXML TableColumn<DetalleVenta, String> tcDescripcion;
	@FXML TableColumn<DetalleVenta, Integer> tcCantidad;
	@FXML TableColumn<DetalleVenta, Float> tcPrecio, tcSubtotal;
	@FXML TableColumn tcEliminar;
	
	@FXML RadioButton rbFisico, rbMoral;
	@FXML ComboBox<Clientes> cbCliente, cbMorales;
	@FXML CheckBox ckMayoreo;
	
	private String usuario;
	private int cliente;
	private Productos pro;
	private NuevoUsuario nu;
	private Usuario u;
	private ControladorPrincipal cp;
	private Ventas v;
	private Clientes cl;
	private ClientesMorales cm;
	private DetalleVenta dVenta;
	private FilteredList<Productos> busqueda;	
	private ObservableList<Productos> lisPro;
	private ObservableList<DetalleVenta> getPrueba;
	private int posicion;
	
	private int filasXPagina;
	private ObservableList<Productos> datos;
	private FilteredList<Productos> datosBusqueda;
	
	@FXML Pagination paginador;



	public ControladorVentas(){
		usuario = cp.nombre;
		
		getPrueba = FXCollections.observableArrayList();
		pro = new Productos();
		lisPro = FXCollections.observableArrayList();
		v = new Ventas();
		dVenta = new DetalleVenta();
		cl = new Clientes();
		cm = new ClientesMorales();
		nu = new NuevoUsuario();
		u = new Usuario();
		
	}
	
	
	
	private Node createPage(int pageIndex) {
		if(filasXPagina>0){
		   int fromIndex = pageIndex * filasXPagina;
		   int toIndex = Math.min(fromIndex + filasXPagina, datosBusqueda.size());
		   listaProducto.setItems(FXCollections.observableArrayList(
				   datosBusqueda.subList(fromIndex, toIndex)));
		}
		else{
			listaProducto.setItems(null);
			paginador.setPageCount(0);
		}
	   return new BorderPane(listaProducto);
	}	
	
 	@FXML public void buscarTexto(){
 		if(txtBuscador.getText().trim().isEmpty()){
 			//Llenar TableView
 			datosBusqueda= new FilteredList<>(datos);
 			filasXPagina=17;
			paginador.setPageCount(datosBusqueda.size()/filasXPagina);
			paginador.setPageFactory((Integer pagina) -> createPage(pagina));
			lblMensaje.setText(datosBusqueda.size() + " registros encontrados en la Base de Datos.");
 		}
 		else{
 			try{
	 			datosBusqueda.setPredicate(Productos->Productos.getDescripcion().toLowerCase().
	 					contains(txtBuscador.getText().toLowerCase()));
	 			if(datosBusqueda.size()<10)
	 				filasXPagina= datosBusqueda.size();
	 			else
	 				filasXPagina=17;
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
			pro = new Productos();
			if (pro.contador() < 17) {
				i = pro.contador();
			}else {
				i = 17;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public void limpiar(){
		lblRegistro.setText("");
		cbCliente.getSelectionModel().clearSelection();
		txtCantidad.setText("1");
		txtPrecio.clear();
		txtProducto.clear();
		txtExistencia.clear();
		txtTotal.clear();
		txtUnidad.clear();
		//rbFisico.setSelected(false);
		//rbMoral.setSelected(false);
	}
	
	@FXML public void click_limpiar(){
		limpiar();
		tablaVenta.getItems().clear();
		
	}
	
	@FXML public int recuperarId(){
		if(cbCliente.getSelectionModel().getSelectedItem()!=null){
			cl = cbCliente.getSelectionModel().getSelectedItem();
			cliente = cl.getCodigo_cliente();
			System.out.println(cliente);
		}else{
			System.out.println("no se encontro el id");
		}
		return cliente;
	}
	
	@FXML public void guardarVenta(){
		
		try {
			if(cbCliente.getSelectionModel().getSelectedItem() != null){
				if(! tablaVenta.getSelectionModel().isEmpty()){
					if (v.guardarVenta(u.codigo, recuperarId())){
						recuperarFolio();
						tablaVenta.getItems().clear();;
						llenarTabla();
						limpiar();
						lblRegistro.setText("Venta exitosa");					
					}
				}else{
					lblRegistro.setText("No hay registros para realizar una venta");
				}				
			}else{
				lblRegistro.setText("El campo CLIENTE se encuentra vacio");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("no se hizo la venta");
		}
	}
		
	/*@FXML public void rbMoral(){
		try {			
			rbMoral.setSelected(true);
			rbFisico.setSelected(false);
			cbCliente.getItems().clear();			
			cbCliente.setItems(cl.getClientesMorales());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	
	@FXML public void rbFisico(){
		try {
			
			rbMoral.setSelected(false);
			rbFisico.setSelected(true);
			cbCliente.getItems().clear();
			cbCliente.setItems(cl.getClientesFisicos());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	
	
	
	public void actualizarVenta(){
		try {
			insertarBoton();
			tablaVenta.setItems(v.obtenerDetalle());
			
			txtTotal.setText(String.valueOf(v.getTotales()));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	@FXML public void checkPrecios(){
		Productos pr = listaProducto.getSelectionModel().getSelectedItem();
			if(ckMayoreo.isSelected()){
				txtPrecio.setText(String.valueOf(pr.getPrecio2()));
				
			}else{
				txtPrecio.setText(String.valueOf(pr.getPrecio1()));
				
			}
		}	
			
	
	
	@FXML public void buscar(){
		try {
			if(txtBuscador.getText().trim().isEmpty()){
				busqueda = new FilteredList<Productos>(lisPro);
				busqueda.setPredicate(Productos -> Productos.getDescripcion().toLowerCase().contains(txtBuscador.getText().toLowerCase()));
				listaProducto.setItems(busqueda);
			}else{
				listaProducto.setItems(lisPro);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
		
	@FXML public void seleccionar(){
		try {
			Productos pr = listaProducto.getSelectionModel().getSelectedItem();
			if( pr != null){
				txtCantidad.setText("1");
				txtProducto.setText(pr.getDescripcion());
				txtExistencia.setText(pr.getActual().toString());
				txtPrecio.setText(pr.getPrecio1().toString());
				txtUnidad.setText(pr.getUnidad());
				pr.getPrecio2();				
				ckMayoreo.setSelected(false);
				pr.getId_codigo();
				v.setExitencias(pr.getActual());				
				dVenta.setProducto_id(pr.getId_codigo());
				lblRegistro.setText("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void agregarVenta(){		
		try {
			float cantidad = Float.parseFloat(txtCantidad.getText());
			float existencia = Float.parseFloat(txtExistencia.getText());			
			if (cantidad > 0  && cantidad < existencia){				
				v.setPr(listaProducto.getSelectionModel().getSelectedItem());
				v.setCantidad(new Float(Float.parseFloat(txtCantidad.getText())));
				if(ckMayoreo.isSelected()){
					if(v.agregarDetallePublico()){						
						actualizarVenta();
						txtExistencia.setText(v.getActual());
						lblRegistro.setText("Se agregego el producto");
					}else{
						System.out.println("no publico");
					}
				}else{
						if(v.agregarDetalle() == true){
							pro.getActual();
							System.out.println("imprimiendo actual "+ pro.getActual());
							actualizarVenta();
							txtExistencia.setText(v.getActual());
							lblRegistro.setText("Se agregego el producto");
						}else{
							lblRegistro.setText("No se agrego el producto");
						}
			}
			}else{
				lblRegistro.setText("Solo queda en existencia 1 producto");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
}
	
	@FXML public void seleccionTabla(){
		try {
			if(tablaVenta.getSelectionModel().getSelectedItem() != null){
				dVenta = tablaVenta.getSelectionModel().getSelectedItem();
				//dVenta = tablaVenta.getSelectionModel().getSelectedItem();
				int id = dVenta.getProducto_id();
				
				System.out.println("probando 1 "+ dVenta.getProducto_id());
				System.out.println("probando 2 "+ v.getExitencias());
				txtCantidad.setText(dVenta.getCantidad().toString());
				txtPrecio.setText(dVenta.getPrecio().toString());
				txtProducto.setText(dVenta.getProducto());
				DetalleVenta dv = new DetalleVenta();
				dv.setProducto_id(id);
				//txtExistencia.setText(dVenta.getExistencia().toString());
				//txtExistencia.setText(String.valueOf(v.getExitencias()));
				if(v.existencia()){
					txtExistencia.setText(String.valueOf(v.getExitencias()));
					System.out.println("si lo hace"+ v.getExitencias());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public void insertarBoton(){
		try {
			tcEliminar.setSortable(false);
			tcEliminar.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DetalleVenta, Boolean>,
					ObservableValue<Boolean>>() {
				public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<DetalleVenta, Boolean> p){
					return new SimpleBooleanProperty(p.getValue() != null);
				}
			});
			
			tcEliminar.setCellFactory(
					new Callback<TableColumn<DetalleVenta, Boolean>, TableCell<DetalleVenta, Boolean>>() {
						
						public TableCell<DetalleVenta, Boolean> call(TableColumn<DetalleVenta, Boolean> p){
							return new ButtonCell ();
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class ButtonCell extends TableCell<DetalleVenta, Boolean> {
	       
        Image eliminarImagen;
        ImageView iv;
        final Button cellButton;
        
        ButtonCell(){
        	eliminarImagen = new Image(getClass().getResourceAsStream("/vista/imagenes/Delete-File-256.png"),10,10,false,false);
        	iv = new ImageView(eliminarImagen);
        	cellButton = new Button("", new ImageView(eliminarImagen));
      	
            cellButton.setOnAction(new EventHandler<ActionEvent>(){
 
				@Override
				public void handle(ActionEvent arg0) {
					getPrueba = v.obtenerDetalle();
					int selec = getTableRow().getIndex();					
					getPrueba.remove(selec);
					actualizarVenta();
					txtCantidad.setText("1");
					txtUnidad.clear();
					txtProducto.clear();
					txtExistencia.clear();
					txtPrecio.clear();
					//cellButton.isDisable();
				}
            });
        
        }    

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }
        }
        
	}
	
	public void recuperarFolio(){
		try {
			String folio = Integer.toString(v.id() + 1);
			txtFolio.setText(folio);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void llenarTabla(){
		try {
		//Refrescar y volver a cargar los datos en el TableView
		datos=pro.getProductos();
		filasXPagina = contar();
		datosBusqueda = new FilteredList<>(datos);
		paginador.setPageCount(datosBusqueda.size()/filasXPagina);
		paginador.setPageFactory((Integer pagina) -> createPage(pagina));
		lblMensaje.setText(datos.size() + " Registros encontrados.");
	} catch (Exception e) {
		e.printStackTrace();
		lblMensaje.setText("Se ha producido un error al recuperar los datos.");
	}
		
}
	
	@SuppressWarnings("unused")
	public void initialize(URL arg0, ResourceBundle arg1) {
			try {
				
				recuperarFolio();
				txtFolio.setDisable(true);
				tcCantidad.setCellValueFactory(new PropertyValueFactory<DetalleVenta, Integer>("cantidad"));
				tcDescripcion.setCellValueFactory(new PropertyValueFactory<DetalleVenta, String>("producto"));				
				tcPrecio.setCellValueFactory(new PropertyValueFactory<DetalleVenta, Float>("precio"));				
				tcSubtotal.setCellValueFactory(new PropertyValueFactory<DetalleVenta, Float>("subtotal"));
				insertarBoton();
				
				txtExistencia.setDisable(true);
				txtPrecio.setDisable(true);
				txtProducto.setDisable(true);
				txtUnidad.setDisable(true);	
				txtVendedor.setDisable(true);
				txtVendedor.setText(usuario);
				llenarTabla();
				
				cbCliente.setItems(cl.getNombreCombo());
				cbCliente.promptTextProperty().setValue("Seleccionar cliente");
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}   
	
	}

