package controlador;

	import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import modelo.Clientes;
import modelo.Proveedores;
import modelo.Ventas;

public class ControladorProveedores implements Initializable {
	
	@FXML private TextField txtNombreProveedor,txtPais,txtMunicipio,txtCalle,txtNumeroExterior,
							txtNumeroInterior,txtColonia,txtLocalidad,txtCP,txtEmail,txtTelefono,txtCelular,txtOtro,
							txtFiltrar,txtCNombre,txtCApellidoP,txtCApellidoM,txtCDireccion,txtCTelefono,txtCCorreo;
	
	@FXML private Button btnEliminar,btnAgregar,btnNuevo;
		
	@FXML private Label lblRFC,lblNombreProveedor,lblPais,lblEstado,lblMunicipio,lblCalle,lblNumeroExterior
						,lblNumeroInterior,lblColonia,lblLocalidad,lblCP,lblEmail,lblTelefono,lblMensaje
						,lblMensajes;
	@FXML private RadioButton rbInactivos;
	
	@FXML private TitledPane tpproveedor,tpcontacto;
	
	@FXML private TableColumn<Proveedores, Integer> idColumn,cpColumn;
	
	@FXML private TableColumn<Proveedores, String> nombreColumn,paisColumn,estadoColumn,municipioColumn,calleColumn,numeroExteriorColumn,
												numeroInteriorColumn,coloniaColumn,localidadColumn,emailColumn,telefonoColumn,celularColumn,otroColumn;
	
	@FXML private ComboBox <String> cbEstado;
	private int id = 0;
	private Proveedores P;
	private Validar validar;
	private ControladorVentanas ventanas;
	
	ObservableList<String> listComboboxEstados = FXCollections.observableArrayList("Aguascalientes", "Baja california norte", "Baja california sur",
			   "Campeche", "Coahuila", "Chiapas", "Chihuahua", "Durango", "Mexico df",
			   "Guanajuato", "Guerrero", "Hidalgo", "Jalisco", "Michoacan", "Morelos",
			   "Mexico,df", "Nayarit", "Nuevo leon","Oaxaca", "Puebla", "Queretaro", 
			   "Quintana roo", "San luis potosi", "Sinaloa", "Sonora", "Tabasco", 
			   "Tamaulipas", "Tlaxcala", "Veracruz", "Yucatan", "Zacatecas");
	
	private int filasXPagina;
	private ObservableList<Proveedores> datos;
	private FilteredList<Proveedores> datosBusqueda;
	@FXML private TableView<Proveedores> tablaProveedores;
	@FXML Pagination paginador;
	
	
	private ControladorErrores error;
		
	
	public  ControladorProveedores() {
		// TODO Auto-generated constructor stub
		error = new ControladorErrores();
		P = new Proveedores();
		filasXPagina = 10;
		datos = FXCollections.observableArrayList();
		validar = new Validar();
		ventanas = ControladorVentanas.getInstancia();
	}
	
	private Node createPage(int pageIndex) {
		if(filasXPagina>0){
		   int fromIndex = pageIndex * filasXPagina;
		   int toIndex = Math.min(fromIndex + filasXPagina, datosBusqueda.size());
		   tablaProveedores.setItems(FXCollections.observableArrayList(
				   datosBusqueda.subList(fromIndex, toIndex)));
		}
		else{
			tablaProveedores.setItems(null);
			paginador.setPageCount(0);
		}
	   return new BorderPane(tablaProveedores);
	}	
	
 	@FXML public void buscarTexto(){
 		if(txtFiltrar.getText().trim().isEmpty()){
 			//Llenar TableView
 			datosBusqueda= new FilteredList<>(datos);
 			filasXPagina=10;
			paginador.setPageCount(datosBusqueda.size()/filasXPagina);
			paginador.setPageFactory((Integer pagina) -> createPage(pagina));
			lblMensajes.setText(datosBusqueda.size() + " registros encontrados en la Base de Datos.");
 		}
 		else{
 			try{
	 			datosBusqueda.setPredicate(Proveedores->Proveedores.getNombreProveedor().toLowerCase().
	 					contains(txtFiltrar.getText().toLowerCase()));
	 			if(datosBusqueda.size()<10)
	 				filasXPagina= datosBusqueda.size();
	 			else
	 				filasXPagina=10;
	 			paginador.setPageCount(datosBusqueda.size()/filasXPagina);
				paginador.setPageFactory((Integer pagina) -> createPage(pagina));
				lblMensajes.setText("Se encontraron " + datosBusqueda.size() + " coincidencias.");
 			}
 			catch(Exception ex){
 				//Enviar mensaje
 				lblMensajes.setText("No se encontraron resultados");
 				filasXPagina=0;
 				paginador.setPageCount(filasXPagina); 				
				paginador.setPageFactory((Integer pagina) -> createPage(pagina));
 			}
 		}
 		
 	}
	
	public int contar(boolean status){
		int i = 0;
		try {
			P = new Proveedores();
			if (P.contador(status) < 10) {
				i = P.contador(status);
			}else {
				i = 10;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	@FXML public void inactivos() throws SQLException{
		if (rbInactivos.isSelected()) {
			filasXPagina = P.contador(false);
			llenarTabla(false);
			btnAgregar.setDisable(true);
			btnEliminar.setDisable(true);
			btnNuevo.setDisable(true);
		}else {
			filasXPagina = P.contador(true);
			llenarTabla(true);
			btnAgregar.setDisable(false);
			btnEliminar.setDisable(false);
			btnNuevo.setDisable(false);
		}
	}
	
	@FXML 
	public void click_Insertar() throws SQLException {
		
		lblMensaje.setText(" ");
		lblMensajes.setText(" ");
		
		if (!txtNombreProveedor.getText().trim().isEmpty()) {
			if (!txtPais.getText().trim().isEmpty()) {
				if (!txtMunicipio.getText().trim().isEmpty()) {
					if (!txtLocalidad.getText().trim().isEmpty()) {
						if (!txtColonia.getText().trim().isEmpty()) {
							if (!txtCP.getText().trim().isEmpty()) {
								if (!txtNumeroExterior.getText().trim().isEmpty()) {
									if (!txtNumeroInterior.getText().trim().isEmpty()) {
										if (!txtTelefono.getText().trim().isEmpty()) {
											if (!txtCelular.getText().trim().isEmpty()) {
												if (!txtOtro.getText().trim().isEmpty()) {
													if (!txtEmail.getText().trim().isEmpty()) {
														if (!txtCNombre.getText().trim().isEmpty()) {
															if (!txtCApellidoP.getText().trim().isEmpty()) {
																if (!txtCApellidoM.getText().trim().isEmpty()) {
																	if (!txtCDireccion.getText().trim().isEmpty()) {
																		if (!txtCTelefono.getText().trim().isEmpty()) {
																			if (!txtCCorreo.getText().trim().isEmpty()) {
																				if (validar.validarCPostal(txtCP.getText().trim())) {
																					
																				if (validar.validarEmail(txtEmail.getText().trim())) {
																					
																				if (validar.validarEmail(txtCCorreo.getText().trim())) {
																					
																					if (!(txtLocalidad.getText().trim().length() <= 3)) {
																						if (! (txtNombreProveedor.getText().trim().length() <= 3)) {
																							if (!(txtMunicipio.getText().trim().length() <= 3)) {
																								if (!(txtCalle.getText().trim().length() <= 1)) {
																									if (!(txtEmail.getText().trim().length() <= 3)) {
																										if (!(txtCCorreo.getText().trim().length() <= 3)) {
																											if (!(txtCNombre.getText().trim().length() <= 2)) {
																												if (!(txtCApellidoP.getText().trim().length() <= 3)) {
																													if (!(txtCApellidoM.getText().trim().length() <= 3)) {
																														if (!(txtCDireccion.getText().trim().length() <= 3)) {
																															if (cbEstado.getSelectionModel().getSelectedItem() != null) {
																																if (!(txtColonia.getText().trim().length() <= 3)) {
																																	
																																
																																																					
																										
																									
																																
																				
																				P = new Proveedores();
																				
																				P.setId_proveedor(new SimpleIntegerProperty(id));
																				P.setNombreProveedor(new SimpleStringProperty(txtNombreProveedor.getText()));
																				P.setPais(new SimpleStringProperty(txtPais.getText()));
																				P.setEstado(cbEstado.getSelectionModel().getSelectedItem());
																				P.setMunicipio(new SimpleStringProperty(txtMunicipio.getText()));
																				P.setCalle(new SimpleStringProperty(txtCalle.getText()));
																				P.setNumeroExterior(new SimpleStringProperty(txtNumeroExterior.getText()));
																				P.setNumeroInterior(new SimpleStringProperty(txtNumeroInterior.getText()));
																				P.setColonia(new SimpleStringProperty(txtColonia.getText()));
																				P.setCp(new SimpleIntegerProperty(Integer.valueOf(txtCP.getText())));
																				P.setEmail(new SimpleStringProperty(txtEmail.getText()));
																				P.setTelefono(new SimpleStringProperty(txtTelefono.getText()));
																				P.setCelular(new SimpleStringProperty(txtCelular.getText()));
																				P.setOtro(new SimpleStringProperty(txtOtro.getText()));
																				P.setLocalidad(new SimpleStringProperty(txtLocalidad.getText()));
																				
																				P.setC_nombre(new SimpleStringProperty(txtCNombre.getText()));
																				P.setC_paterno(new SimpleStringProperty(txtCApellidoP.getText()));
																				P.setC_direccion(new SimpleStringProperty(txtCDireccion.getText()));
																				P.setC_correo(new SimpleStringProperty(txtCCorreo.getText()));
																				P.setC_telefono(new SimpleStringProperty(txtCTelefono.getText()));
																				P.setC_materno(new SimpleStringProperty(txtCApellidoM.getText()));
																				
																				int resultado = P.insertar();
																				if (resultado == 0) {
																					lblMensaje.setText("A ocurrido un problema en el servidor");
																				}else if (resultado == 1) {
																					lblMensajes.setText("Proveedor actualizado con exito");
																					filasXPagina = P.contador(true);
																					llenarTabla(true);
																					limpiar();
																				}else if (resultado == 2) {
																					lblMensaje.setText("El correo de proveedor ya es utilizado");
																					txtEmail.requestFocus();
																					
																				}else if (resultado == 3) {
																					lblMensaje.setText("El correo de contacto proveedor ya es utilizado");
																					txtCCorreo.requestFocus();
																				}else if (resultado == 4) {
																					
																					lblMensaje.setText("Nuevo proveedor insertado con exito");
																					filasXPagina = P.contador(true);
																					llenarTabla(true);
																					limpiar();
																					
																				}
																				
																															}else {
																																txtColonia.requestFocus();
																																lblMensaje.setText("El campo colonia debe ser mayor a 3");
																															}
																															}else {
																																cbEstado.requestFocus();
																																lblMensaje.setText("Seleccione un estado");
																					
																															}
																				
																				
																														}else {
																															txtCDireccion.requestFocus();
																															lblMensaje.setText("El campo direccion de contacto debe ser mayor a 3 caracteres");
																														}
																				
																													}else {
																														txtCApellidoM.requestFocus();
																														lblMensaje.setText("El campo apellido materno de contacto debe ser mayor a 3 caracteres");
																													}
																				
																												}else {
																													txtCApellidoP.requestFocus();
																													lblMensaje.setText("El campo apellido paterno de contacto  debe ser mayor a 3 caracteres");
																												}
																				
																											}else {
																												txtCNombre.requestFocus();
																												lblMensaje.setText("El campo nombre de contacto debe ser mayor a 2 caracteres");
																											}
																				
																										}else {
																											txtCCorreo.requestFocus();
																											lblMensaje.setText("El campo correo electronico de contacto debe ser mayor a 3 caracteres");
																										}
																					
																									}else {
																										txtEmail.requestFocus();
																										lblMensaje.setText("El campo correo electronico de proveedor debe ser mayor a 3 caracteres");
																										System.out
																												.println(txtEmail.getText().trim().length());
																									}
																								
																								
																								}else {
																									txtCalle.requestFocus();
																									lblMensaje.setText("El campo calle debe ser mayor a 1 caracter");
																								}
																							}else {
																								txtMunicipio.requestFocus();
																								lblMensaje.setText("El campo municipio debe ser mayor a 3 caracteres");
																							}
																				
																				
																						}else {
																							txtNombreProveedor.requestFocus();
																							lblMensaje.setText("El campo nombre proveedor debe ser mayor a 3 caracteres");
																						}
																				}else {
																					txtLocalidad.requestFocus();
																					lblMensaje.setText("El campo localidad debe contener mas de 3 caracteres");
																				}
																				
																				
																				}else {
																					txtCCorreo.requestFocus();
																					lblMensaje.setText("El campo correo de contacto debe ser: ejemplo@mail.com");
																				}
																				
																				}else {
																					txtEmail.requestFocus();
																					lblMensaje.setText("El campo correo debe ser: ejemplo@mail.com");
																				}
																				}else {
																					txtCP.requestFocus();
																					lblMensaje.setText("El campo cp debe ser de 5 caracteres numericos");
																				}
																			}else {
																				txtCCorreo.requestFocus();
																				lblMensaje.setText("Falta llenar el campo correo de contacto");
																			}
																		}else {
																			txtCTelefono.requestFocus();
																			lblMensaje.setText("Falta llenar el campo telefono de contacto");
																		}
																	}else {
																		txtCDireccion.requestFocus();
																		lblMensaje.setText("Falta llenar el campo direccion de contacto");
																	}
																}else {
																	txtCApellidoM.requestFocus();
																	lblMensaje.setText("Falta llenar el campo apellido materno de contacto");
																}
															}else {
																txtCApellidoP.requestFocus();
																lblMensaje.setText("Falta llenar el campo apellido paterno de contacto");
															}
														}else {
															txtCNombre.requestFocus();
															lblMensaje.setText("Falta llenar el campo nombre de contaccto");
														}
													}else {
														txtEmail.requestFocus();
														lblMensaje.setText("Falta llenar el campo correo de proveedor ");
													}
												}else {
													txtOtro.requestFocus();
													lblMensaje.setText("Falta llenar el campo otro");
												}
											}else {
												txtCelular.requestFocus();
												lblMensaje.setText("Falta llenar el campo celular");
											}
										}else {
											txtTelefono.requestFocus();
											lblMensaje.setText("Falta llenar el campo telefono");
										}
									}else {
										txtNumeroInterior.requestFocus();
										lblMensaje.setText("Falta llenar el campo numero interior");
									}
								}else {
									txtNumeroExterior.requestFocus();
									lblMensaje.setText("Falta llenar el campo numero exterior");
								}
							}else {
								txtCP.requestFocus();
								lblMensaje.setText("Falta llenar el campo codigo postal");
							}
						}else {
							txtColonia.requestFocus();
							lblMensaje.setText("Falta llenar el campo colonia");
						}
					}else {
						txtLocalidad.requestFocus();
						lblMensaje.setText("Falta llenar el campo localidad");
					}
				}else {
					txtMunicipio.requestFocus();
					lblMensaje.setText("Falta llenar el campo municipio");
				}
			}else {
				txtPais.requestFocus();
				lblMensaje.setText("Falta llenar el campo país");
			}
		}else {
			txtNombreProveedor.requestFocus();
			lblMensaje.setText("Falta llenar el campo nombre del proveedor");
		}
	}
	
	
	@FXML public void click_emergente(){
		ventanas.asignarEmergente("../vista/fxml/vistaReciclajeClientes.fxml", "RECICLAJE");
	}
	@FXML
	public void click_Eliminar() {
		lblMensaje.setText(" ");
		lblMensajes.setText(" ");
		if (id != 0) {			
			P = new Proveedores();
			P.setId_proveedor(new SimpleIntegerProperty(id));
			boolean rs = P.eliminar();
			if (rs) {
				lblMensaje.setText("Cliente eliminado exitosamente");
				limpiar();
				id = 0;
			}else {
				lblMensaje.setText("Ocurrio un problema en e lservidor");
			}
	
		
		}else {
			lblMensaje.setText("No hay ningun dato seleccionado");
		}
		
	}
	@ FXML public void limpiar() {
		
		txtNombreProveedor.clear();
		txtPais.clear();
		txtMunicipio.clear();
		txtCalle.clear();
		txtNumeroExterior.clear();
		txtNumeroInterior.clear();
		txtColonia.clear();
		txtLocalidad.clear();
		txtCP.clear();
		txtEmail.clear();
		txtTelefono.clear();
		txtCelular.clear();
		txtOtro.clear();
		txtFiltrar.clear();
		txtCNombre.clear();
		txtCApellidoP.clear();
		txtCApellidoM.clear();
		txtCDireccion.clear();
		txtCTelefono.clear();
		txtCCorreo.clear();
		cbEstado.getSelectionModel().select("Selecciona Estado");
		id = 0;
	}
	

	@FXML public void click_TableView(){
		if (!rbInactivos.isSelected()) {
			
		
		if(tablaProveedores.getSelectionModel().getSelectedItem() != null){
			P = tablaProveedores.getSelectionModel().getSelectedItem();
			id = P.getId_proveedor();
			txtNombreProveedor.setText(P.getNombreProveedor());
			txtPais.setText(P.getPais());
			cbEstado.getSelectionModel().select(P.getEstado());
			txtMunicipio.setText(P.getMunicipio());
			txtCalle.setText(P.getCalle());
			txtNumeroExterior.setText(P.getNumeroExterior());
			txtNumeroInterior.setText(P.getNumeroExterior());
			txtColonia.setText(P.getColonia());
			txtLocalidad.setText(P.getLocalidad());
			txtCP.setText(P.getCp().toString());
			txtEmail.setText(P.getEmail());
			txtTelefono.setText(P.getTelefono());
			txtCelular.setText(P.getCelular());
			txtOtro.setText(P.getOtro());
			
			txtCNombre.setText(P.getC_nombre());
			txtCApellidoP.setText(P.getC_paterno());
			txtCApellidoM.setText(P.getC_materno());
			txtCCorreo.setText(P.getC_correo());
			txtCDireccion.setText(P.getC_direccion());
			txtCTelefono.setText(P.getC_telefono());
			}
		}else {
			
		}

		
	}
	public void llenarTabla(boolean estatus){
				try {
				//Refrescar y volver a cargar los datos en el TableView
				datos=P.getProveedores(estatus);
				datosBusqueda = new FilteredList<>(datos);
				paginador.setPageCount(datosBusqueda.size()/filasXPagina);
				paginador.setPageFactory((Integer pagina) -> createPage(pagina));
				lblMensajes.setText(datos.size() + " Registros encontrados.");
			} catch (Exception e) {
//				e.printStackTrace();
				lblMensajes.setText("Se ha producido un error al recuperar los datos.");
			}
				
		}
		
	
	

			
		
	
		//**********************************************//
		//				INICIALIZAR CONTROLADOR			//							
		//**********************************************//	
		@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			try {
				
//				idColumn.setCellValueFactory(			 new PropertyValueFactory<Proveedores, Integer>("id_proveedor"));
				nombreColumn.setCellValueFactory(		 new PropertyValueFactory<Proveedores, String>("nombreProveedor"));
				paisColumn.setCellValueFactory(			 new PropertyValueFactory<Proveedores, String>("pais"));
				estadoColumn.setCellValueFactory( 		 new PropertyValueFactory<Proveedores, String>("estado"));
				municipioColumn.setCellValueFactory(	 new PropertyValueFactory<Proveedores, String>("municipio"));
				calleColumn.setCellValueFactory(		 new PropertyValueFactory<Proveedores, String>("calle"));
				numeroExteriorColumn.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("numeroExterior"));
				numeroInteriorColumn.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("numeroInterior"));
				coloniaColumn.setCellValueFactory(		 new PropertyValueFactory<Proveedores, String>("colonia"));
				localidadColumn.setCellValueFactory(	 new PropertyValueFactory<Proveedores, String>("localidad"));
				cpColumn.setCellValueFactory(			 new PropertyValueFactory<Proveedores, Integer>("cp"));
				emailColumn.setCellValueFactory(		 new PropertyValueFactory<Proveedores, String>("email"));
				telefonoColumn.setCellValueFactory(		 new PropertyValueFactory<Proveedores, String>("telefono"));
				celularColumn.setCellValueFactory(		 new PropertyValueFactory<Proveedores, String>("celular"));
				otroColumn.setCellValueFactory(		 	 new PropertyValueFactory<Proveedores, String>("otro"));

				
				cbEstado.setItems(listComboboxEstados);
				llenarTabla(true);

				
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				error.printLong(e.getMessage(), this.getClass().toString());
			}
			
			
			

		}    

		
}
