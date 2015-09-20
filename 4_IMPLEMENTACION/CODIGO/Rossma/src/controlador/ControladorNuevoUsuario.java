package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import modelo.NuevoUsuario;
import modelo.Proveedores;

public class ControladorNuevoUsuario implements Initializable{
	private NuevoUsuario u;
	private ControladorVentanas ventana;
	
	@ FXML TextField 	txtId, txtNombre, txtPaterno, txtMaterno, txtEmail,  txtUsuario,	txtTelefono, txtCelular,txtFiltrar;	
	@ FXML PasswordField	txtContrasenia, txtConfirmacion;	
	@ FXML TextArea 	txaDireccion;			
	@ FXML Label		lblMensaje, lblBuscar, lblRegistro; 			
	@ FXML ComboBox		<String> categoria;	
	@ FXML Button 		btnInsertar, btnEliminar, btnModificar, btnNuevo, btnReciclar, btnCerrar_sesion;
		
	@FXML private TableView<NuevoUsuario> tablaUsuario;
	@FXML private TableColumn<NuevoUsuario, Integer> idColumn;
	@FXML private TableColumn<NuevoUsuario, String> nombreColumn, paternoColumn, maternoColumn, direccionColumn
									, emailColumn, telefonoColumn, celularColumn, usuarioColumn, categoriaColumn;
	
//	
//	Image nuevo = new Image(getClass().getResourceAsStream("../vista/imagenes/nuevo.png"));
//	Image guardar = new Image(getClass().getResourceAsStream("../vista/imagenes/guardar.png"));
//	Image borrar = new Image(getClass().getResourceAsStream("../vista/imagenes/borrar.png"));
//	Image reciclar = new Image(getClass().getResourceAsStream("../vista/imagenes/reciclaje.png"));
//	Image cr_sesion = new Image(getClass().getResourceAsStream("../vista/imagenes/cerrar_sesion.jpg"));
//	Image buscar = new Image(getClass().getResourceAsStream("../vista/imagenes/buscar.png"));
	private int filasXPagina;
	private FilteredList<NuevoUsuario> datosBusqueda;
	
	@FXML Pagination paginador;
	
	private ObservableList<NuevoUsuario> datos;
	private ControladorErrores error;
	
	
	
	
		private Node createPage(int pageIndex) {
		if(filasXPagina>0){
		   int fromIndex = pageIndex * filasXPagina;
		   int toIndex = Math.min(fromIndex + filasXPagina, datosBusqueda.size());
		   tablaUsuario.setItems(FXCollections.observableArrayList(
				   datosBusqueda.subList(fromIndex, toIndex)));
		}
		else{
			tablaUsuario.setItems(null);
			paginador.setPageCount(0);
		}
	   return new BorderPane(tablaUsuario);
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
	 			datosBusqueda.setPredicate(NuevoUsuaio->NuevoUsuaio.getNombre().toLowerCase().
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
	
	public ControladorNuevoUsuario() {		
		error = new ControladorErrores();
		u = new NuevoUsuario();
		datos = FXCollections.observableArrayList();
		ventana = ControladorVentanas.getInstancia();
		filasXPagina = contar(); 
	}
	
	public int contar(){
		int i = 0;
		try {
			u = new NuevoUsuario();
			if (u.contarUsuarios() < 10) {
				i = u.contarUsuarios();
			}else {
				i = 10;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
		}
			

	
	ObservableList<String> listCombobox = FXCollections.observableArrayList("Administrador","Empleado");
	
	public void actualizarTabla(){
		try {
			u = new NuevoUsuario();
			datos = u.getNewUsser();
			tablaUsuario.setItems(datos);
			//lblMensaje.setText(lista.size() + " registros");
		} catch (Exception e) {
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
		}
	}
	
	@FXML public void actualizar(){
		actualizarTabla();
		limpiar();
	}
	
	/*
	 * Metodo para regresar los valores de la TableView a los Texfield
	 */
	@FXML public void click_table(){
		if(tablaUsuario.getSelectionModel().getSelectedItem() != null){
			u = tablaUsuario.getSelectionModel().getSelectedItem();
			u.getCodigo_usuario();
			//txtId.setText(u.getCodigo_usuario().toString());
			txtNombre.setText(u.getNombre());
			txtPaterno.setText(u.getApellido_paterno());
			txtMaterno.setText(u.getApellido_materno());
			txtEmail.setText(u.getCorreo());
			txaDireccion.setText(u.getDireccion());
			txtUsuario.setText(u.getUsuario());
			txtContrasenia.setText(u.getContrasenia());
			txtTelefono.setText(u.getTelefono());
			txtCelular.setText(u.getCelular());
			categoria.getSelectionModel().select(u.getCategoria());
		}
	}
	
	public int recuperarId(){		
		int a = u.getCodigo_usuario();
		return a;
	}
	
	
	
	
	/*
	 *Metodo para limpiar textfield, combobox
	 */
 	public void limpiar(){
 		lblRegistro.setText("");
		txtConfirmacion.clear();
		txtNombre.clear();
		txtPaterno.clear();
		txtMaterno.clear();
		txtUsuario.clear();
		txtContrasenia.clear();
		txtTelefono.clear();
		txtCelular.clear();
		txaDireccion.clear();
		txtEmail.clear();
		categoria.getSelectionModel().select(-1);		
	}
 	
 		
	/*
	 * #Region Boton NUEVO
	 */
	@FXML public void click_nuevo(){
		limpiar();
		lblMensaje.setText("");
	}
	
	
	/*
	 * #Region Boton INSERTAR
	 */
	@FXML public void click_insertar(){
		try {
			if(!txtNombre.getText().trim().isEmpty()){
				if(!txtPaterno.getText().trim().isEmpty()){
					if(!txtMaterno.getText().trim().isEmpty()){
						if(!txaDireccion.getText().trim().isEmpty()){
							if(!txtEmail.getText().trim().isEmpty()){
								if(!txtUsuario.getText().trim().isEmpty()){
									if(!txtContrasenia.getText().trim().isEmpty()){
										if(categoria.getSelectionModel().getSelectedItem() != null){
											int c = recuperarId();
											u = new NuevoUsuario();	
											u.setCodigo_usuario(new SimpleIntegerProperty(c));
											u.setUsuario(new SimpleStringProperty(txtUsuario.getText()));
											u.setContrasenia(new SimpleStringProperty(txtContrasenia.getText()));
											u.setTipo(categoria.getSelectionModel().getSelectedItem());
											u.setNombre(new SimpleStringProperty(txtNombre.getText()));
											u.setApellido_paterno(new SimpleStringProperty(txtPaterno.getText()));
											u.setApellido_materno(new SimpleStringProperty(txtMaterno.getText()));
											u.setDireccion(new SimpleStringProperty(txaDireccion.getText()));
											u.setTelefono(new SimpleStringProperty(txtTelefono.getText()));
											u.setCelular(new SimpleStringProperty(txtCelular.getText()));						
											u.setCorreo(new SimpleStringProperty(txtEmail.getText()));					
												if(txtContrasenia.getText().equals(txtConfirmacion.getText())){
													boolean resultado = u.insertar();
													if (resultado){
														lblRegistro.setText("Datos Insertados");
														actualizarTabla();
														limpiar();
													}else{
														lblRegistro.setText("Ocurrio un ERROOR");
													}		
												}else{
													lblRegistro.setText("LA CONTRASEÑA ES DIFERENTE");
												
												}
										//Comienza validacion		
										}else{
											lblRegistro.setText("El campo CATEGORIA se encuentra vacio");
										}
									}else{
										txtContrasenia.requestFocus();
										lblRegistro.setText("El campo CONTRASEÑA se encuentra vacio");
									}
								}else{
									txtUsuario.requestFocus();
									lblRegistro.setText("El campo NOMBRE USUARIO se encuentra vacio");
								}
							}else{
								txtEmail.requestFocus();
								lblRegistro.setText("El campo CORREO ELECTRONICO se encuentra vacio");
							}
						}else{
							txaDireccion.requestFocus();
							txaDireccion.setVisible(true);
							lblRegistro.setText("El campo DIRECCION se encuentra vacio");
						}
					}else{
						txtMaterno.requestFocus();
						lblRegistro.setText("El campo APELLIDO MATERNO se encuentra vacio");
					}
				}else{
					txtPaterno.requestFocus();
					lblRegistro.setText("El campo APELLIDO PATERNO se encuentra vacio");
				}
			}else{
				txtNombre.requestFocus();
				lblRegistro.setText("El campo NOMBRE se encuentra vacio");
			}		
			
		} catch (Exception e) {
			e.printStackTrace();
			lblRegistro.setText("Error en el Servidor");
			error.printLong(e.getMessage(), this.getClass().toString());
		}
	}
	
	
	/*
	 * Region boton ELIMINAR
	 */
	@FXML public void click_eliminar(){
		try {
			if(txtNombre.getText().isEmpty()){
				lblRegistro.setText("Seleccionar un producto");
			}else{
				
				int b = recuperarId();
				u = new NuevoUsuario();
				System.out.println(b);
				u.setCodigo_usuario(new SimpleIntegerProperty(b));
				if(u.getCodigo_usuario().equals(1)){
					lblRegistro.setText("No se puede eliminar un Administrador");
				}else{
					if(u.eliminar()==true){
						actualizarTabla();						
						limpiar();
						lblRegistro.setText("Usuario eliminado exitosamente");
					}else{
						lblRegistro.setText("Error al eliminar el usuario");
					}
				}
					
			}
		} catch (Exception e) {
			e.printStackTrace();
			lblRegistro.setText("Error en el servidor");
			error.printLong(e.getMessage(), this.getClass().toString());
		}
	}
	
	@FXML public void click_reciclaje(){
		ventana.asignarEmergente("../vista/fxml/vistaReciclajeUsuario.fxml", "RECICLAJE");
	}

	
	public void llenarTabla(){
		try {
		//Refrescar y volver a cargar los datos en el TableView
		datos=u.getNewUsser();
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
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			nombreColumn.setCellValueFactory(	new PropertyValueFactory<NuevoUsuario, String>("completo"));
			//paternoColumn.setCellValueFactory(	new PropertyValueFactory<NuevoUsuario, String>("apellido_paterno"));
			//maternoColumn.setCellValueFactory(	new PropertyValueFactory<NuevoUsuario, String>("apellido_materno"));
			direccionColumn.setCellValueFactory(new PropertyValueFactory<NuevoUsuario, String>("direccion"));
			emailColumn.setCellValueFactory(	new PropertyValueFactory<NuevoUsuario, String>("correo"));
			telefonoColumn.setCellValueFactory( new PropertyValueFactory<NuevoUsuario, String>("telefono"));
			celularColumn.setCellValueFactory(	new PropertyValueFactory<NuevoUsuario, String>("celular"));
			usuarioColumn.setCellValueFactory(	new PropertyValueFactory<NuevoUsuario, String>("usuario"));
			categoriaColumn.setCellValueFactory(new PropertyValueFactory<NuevoUsuario, String>("categoria"));
		
			categoria.setItems(listCombobox);	
			categoria.setValue("Selecciona Tipo");	
			
			llenarTabla();	

		} catch (Exception e) {
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
		}	
	}

}
