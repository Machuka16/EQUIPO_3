package controlador;

import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import modelo.Clientes;
import modelo.ClientesFisicos;
import modelo.ClientesMorales;
import modelo.Productos;
import modelo.Proveedores;

public class ControladorClientes implements Initializable {
	
	/******************************Controladores de los TextFields********************************/
	@FXML private TextField txtPais,txtMunicipio,txtLocalidad,txtColonia,
							txtCodigoPostal,txtCalle,txtNumeroExterior,
							txtNumeroInterior,txtTelefono,txtCelular,txtOtro,txtEmail,
							txtRfcMoral,txtNombreContacto,txtPaternoContacto,txtMaternoContacto,
	 						txtEmpresa,txtTelefonoMoral,txtEmailMoral,
	 						txtRfcFisico,txtNombreFisico,txtPaternoFisico,txtMaternoFisico,
	 						txtBuscador;
	/******************************Controladores de los botones********************************/
	@FXML private Button btnEliminar,btnGuardar,btnLimpiar,btnPapelera,btnCerrar;
	
	@FXML RadioButton rbFisico, rbMorales;

	/******************************Controladores de los botones********************************/
	@FXML private Label lblPais,lblEstado,lblMunicipio,lblLocalidad,lblColonia,lblCodigoPostal,lblCalle,
						lblNumeroExterior,lblNumeroInterior,lblTelefono,lblCelular,lblOtro,lblEmail,
						lblRfcMoral,lblNombreMoral,lblPaternoMoral,lblMaternoMoral,
						lblEmpresa,lblTelefonoMoral,lblEmailMoral,
						lblRfcFisico,lblNombreFisico,lblPaternoFisico,lblMaternoFisico,
						lblMensaje, lblRegistro;
	
	/******************************Controladores de otros tipos********************************/
	@FXML private ComboBox <String> cbEstado;
	@FXML private ComboBox <String> cbTipo;
	@FXML private TitledPane tpFisica, tpMoral, tpContacto;
	
	
	
	
	/******************************Controladores de las columnas********************************/
	
	@FXML private TableColumn<Clientes, String> tcRfc,tcCliente,tcDireccion,tcTelefono,tcEmail,tcTipo;
	
	private ControladorVentanas ventanas;
	private Clientes C;
	private ClientesMorales M;
	private ClientesFisicos F;
	private Validar validar;
	
	private int filasXPagina;
	private ObservableList<Clientes> datos;	
	private FilteredList<Clientes> datosBusqueda;
	@FXML private TableView<Clientes>  tbClientes;
	@FXML Pagination paginador;
	
	public  ControladorClientes() {
		C = new Clientes();
		M = new ClientesMorales();
		F = new ClientesFisicos();
		filasXPagina = 10;
		ventanas = ControladorVentanas.getInstancia();
		datos = FXCollections.observableArrayList();
		validar = new Validar();
	}
		
	
	ObservableList<String> listComboboxPersona = FXCollections.observableArrayList("Fisico","Moral");	
	ObservableList<String> listComboboxEstados = FXCollections.observableArrayList("Aguascalientes", "Baja california norte", "Baja california sur",
																				   "Campeche", "Coahuila", "Chiapas", "Chihuahua", "Durango", "Mexico df",
																				   "Guanajuato", "Guerrero", "Hidalgo", "Jalisco", "Michoacan", "Morelos",
																				   "Mexico,df", "Nayarit", "Nuevo leon","Oaxaca", "Puebla", "Queretaro", 
																				   "Quintana roo", "San luis potosi", "Sinaloa", "Sonora", "Tabasco", 
																				   "Tamaulipas", "Tlaxcala", "Veracruz", "Yucatan", "Zacatecas");
	
		
	private Node createPage(int pageIndex) {
		if(filasXPagina>0){
		   int fromIndex = pageIndex * filasXPagina;
		   int toIndex = Math.min(fromIndex + filasXPagina, datosBusqueda.size());
		   tbClientes.setItems(FXCollections.observableArrayList(
				   datosBusqueda.subList(fromIndex, toIndex)));
		}
		else{
			tbClientes.setItems(null);
			paginador.setPageCount(0);
		}
	   return new BorderPane(tbClientes);
	}	
	
 	@FXML public void buscarTexto(){
 		if(txtBuscador.getText().trim().isEmpty()){
 			//Llenar TableView
 			datosBusqueda= new FilteredList<>(datos);
 			filasXPagina=10;
			paginador.setPageCount(datosBusqueda.size()/filasXPagina);
			paginador.setPageFactory((Integer pagina) -> createPage(pagina));
			lblRegistro.setText(datosBusqueda.size() + " registros encontrados en la Base de Datos.");
 		}
 		else{
 		
 		
 			try{
	 			datosBusqueda.setPredicate(Clientes->Clientes.toString().toLowerCase().
	 					contains(txtBuscador.getText().toLowerCase()));
	 			if(datosBusqueda.size()<10)
	 				filasXPagina= datosBusqueda.size();
	 			else
	 				filasXPagina=10;
	 			paginador.setPageCount(datosBusqueda.size()/filasXPagina);
				paginador.setPageFactory((Integer pagina) -> createPage(pagina));
				lblRegistro.setText("Se encontraron " + datosBusqueda.size() + " coincidencias.");
 			}
 			catch(Exception ex){
 				//Enviar mensaje
 				
 				lblRegistro.setText("No se encontraron resultados");
 				filasXPagina=0;
 				paginador.setPageCount(filasXPagina); 				
				paginador.setPageFactory((Integer pagina) -> createPage(pagina));
 			}
 		}
 		
 	}
	
	public void limpiar(){
		txtPais.clear();			txtRfcMoral.clear();
		txtMunicipio.clear();		txtEmpresa.clear();
		txtLocalidad.clear();		txtNombreContacto.clear();
		txtColonia.clear();			txtPaternoContacto.clear();
		txtCodigoPostal.clear();	txtMaternoContacto.clear();
		txtCalle.clear();			txtTelefonoMoral.clear();
		txtNumeroExterior.clear();	txtEmailMoral.clear();
		txtNumeroInterior.clear();	txtRfcFisico.clear();
		txtTelefono.clear();		txtNombreFisico.clear();
		txtCelular.clear();			txtPaternoFisico.clear();
		txtOtro.clear();			txtMaternoFisico.clear();
		txtEmail.clear();			lblRegistro.setText(" ");
//		cbTipo.getSelectionModel().select("Tipo de persona");
		cbEstado.getSelectionModel().select("Selecciona Estado");
	}
	
	public int contar(boolean cliente){
		int i = 0;
		try {
			Clientes c = new Clientes();
			if (c.contador(cliente) < 10) {
				i = c.contador(cliente);
			}else {
				i = 10;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	
	public int recuperarId(int c){
		Clientes C = new Clientes();
		int a;
		if (tbClientes.getSelectionModel().getSelectionMode().equals("Fisico")){
			a = C.getCodigo_cliente();
			System.out.println(a);			
		}else{
			if (tbClientes.getSelectionModel().getSelectionMode().equals("Moral")){
				a = C.getCodigo_cliente();
				System.out.println(a);
			}else{
				System.out.println("no funciona el metodo recuperarId");
			}
		}
			
		return c;
	}
	
	/*
	 * Metodo para actualizar la tabla
	 */
	public void actualizarTabla(){
		try {
			if(C.getTipo().equals("Fisico")){
				C = new Clientes();
				datos = C.getClientesFisicos();
				tbClientes.setItems(datos);
				txtPais.setText(C.getPais());
			}else{
				if(C.getTipo().equals("Moral")){
				C = new Clientes();
				datos = C.getClientesMorales();
				tbClientes.setItems(datos);
				txtPais.setText(C.getPais());
				}else{
					System.out.println("no sirve");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
	}
		
	
	/*
	 * Metodo para escoger en el ComboBox Fisico o Moral y deshabilitar el TitlePanel contrario
	 */
	@FXML public void click_comboBox(){
			if (cbTipo.getSelectionModel().getSelectedItem().equals("Fisico")) {
				tpMoral.setDisable(true);
				tpContacto.setDisable(true);
				tpFisica.setDisable(false);
				//limpiar();
				
			}
			else if(cbTipo.getSelectionModel().getSelectedItem().equals("Moral")){
				tpFisica.setDisable(true);
				tpMoral.setDisable(false);
				tpContacto.setDisable(false);
				//limpiar();
				
				
				
			}
		}
		
	
	/*
	 * Metodo para validar que tipo de dato se recupero en el comboBox (Fisico o Moral)
	 */
	public boolean combo(){
			if (cbTipo.getSelectionModel().getSelectedItem().equals("Fisico")) {
				tpMoral.setDisable(true);
				tpContacto.setDisable(true);
				return true;
			}
			else {
				tpFisica.setDisable(true);
				return false;
			}
		}
		

	
	/*
	 * Metodo para insertar 
	 */
	@FXML public void click_guardar(){
		lblMensaje.setText(" ");
			try {
			
					if(!(cbEstado.getSelectionModel().isEmpty())){
						if(!txtMunicipio.getText().trim().isEmpty()){
							if(!txtCalle.getText().trim().isEmpty()){
								if(!txtColonia.getText().trim().isEmpty()){
									if(!txtLocalidad.getText().trim().isEmpty()){
										if(!txtCodigoPostal.getText().trim().isEmpty()){
											
											if (!txtNumeroExterior.getText().trim().isEmpty()) {
												
												if (!txtNumeroInterior.getText().trim().isEmpty()) {
											if(!txtEmail.getText().trim().isEmpty()){
												if(!txtTelefono.getText().trim().isEmpty()){
													if(!txtCelular.getText().trim().isEmpty()){
														if(!txtOtro.getText().trim().isEmpty()){													
															
															if(!(txtMunicipio.getText().trim().length() <= 3)){																
																	if(!(txtColonia.getText().trim().length() <= 3)){
																		if(!(txtLocalidad.getText().trim().length() <= 3)){
																			if(validar.validarCPostal(txtCodigoPostal.getText().trim())){
																				if(validar.validarEmail(txtEmail.getText().trim())){
																					if(validar.validar_soloTelefono(txtTelefono.getText().trim())){
																						if(validar.validarCelular(txtCelular.getText().trim())){
																							if(validar.validarTelefono(txtOtro.getText().trim())){
																																		
																								int f = C.getCodigo_cliente();
																								C = new Clientes();
																								C.setCodigo_cliente(new SimpleIntegerProperty(f));
																								C.setPais(new String(C.getPais()));
																								C.setEstado(new SimpleStringProperty( cbEstado.getSelectionModel().getSelectedItem()));
																								C.setMunicipio(new SimpleStringProperty(txtMunicipio.getText()));
																								C.setLocalidad(new SimpleStringProperty(txtLocalidad.getText()));
																								C.setColonia(new SimpleStringProperty(txtColonia.getText()));
																								C.setCodigo_postal(new SimpleIntegerProperty(Integer.valueOf(txtCodigoPostal.getText())));
																								C.setCalle(new SimpleStringProperty(txtCalle.getText()));
																								C.setNumeroExterior(new SimpleStringProperty(txtNumeroExterior.getText()));
																								C.setNumeroInterior(new SimpleStringProperty(txtNumeroInterior.getText()));
																								C.setTelefono(new SimpleStringProperty(txtTelefono.getText()));
																								C.setCelular(new SimpleStringProperty(txtCelular.getText()));
																								C.setOtro(new SimpleStringProperty(txtOtro.getText()));
																								C.setEmail(new SimpleStringProperty(txtEmail.getText()));
																								C.setTipo(new SimpleStringProperty( cbTipo.getSelectionModel().getSelectedItem()));
																								
																								if(cbTipo.getSelectionModel().getSelectedItem().equals("Fisico")){																									
																									if(!txtRfcFisico.getText().trim().isEmpty()){
																										if(!txtNombreFisico.getText().trim().isEmpty()){
																											if(!txtPaternoFisico.getText().trim().isEmpty()){
																												if(!txtMaternoFisico.getText().trim().isEmpty()){																													
																													if (validar.validarRFC_fisico(txtRfcFisico.getText().trim())) {																														
																														if (txtNombreFisico.getText().trim().length() <= 2) {																															
																														if (txtPaternoFisico.getText().trim().length() <= 3) {																															
																														if (txtMaternoFisico.getText().trim().length() <= 3) {											
																											
																													F = new ClientesFisicos();
																													C.getF().setRfc(new SimpleStringProperty(txtRfcFisico.getText()));
																													C.getF().setNombre(new SimpleStringProperty(txtNombreFisico.getText()));					
																													C.getF().setApellido_paterno(new SimpleStringProperty(txtPaternoFisico.getText()));
																													C.getF().setApellido_materno(new SimpleStringProperty(txtMaternoFisico.getText()));
																														if(C.insertarClientesFisicos()==true){	
																															limpiar();
																															actualizarTabla();
																															lblMensaje.setText("Datos de persona fisica insertados");
																															
																																																							
																														}else{
																															lblMensaje.setText("Datos de persona fisica no insertados");
																														}
																														
																														}else {
																															txtMaternoFisico.requestFocus();
																															lblMensaje.setText("El campo apellido materno de persona fisica debe ser mayor 3 caracteres");
																														}
																														}else {
																															txtPaternoFisico.requestFocus();
																															lblMensaje.setText("El campo apellido materno de persona fisica debe ser mayor a 3 caracteres");
																														}
																														
																													}else {
																														txtNombreFisico.requestFocus();
																														lblMensaje.setText("El campo nombre de persona fisica debe ser mayor a 2 caracteres");
																													}
																													}else {
																														txtRfcFisico.requestFocus();
																														lblMensaje.setText("El campo rfc fisico debe ser ejemplo: FLMC640609P48");
																													}
																												}else{
																													txtMaternoFisico.requestFocus();
																													lblMaternoFisico.setText("El campo APELLIDO MATERNO de PERSONA FISICA se encuentra vacio");																				
																												}
																											}else{
																												txtPaternoFisico.requestFocus();
																												lblMensaje.setText("El campo APELLIDO PATERNO de PERSONA FISICA se encuentra vacio");																			
																											}
																										}else{
																											txtNombreFisico.requestFocus();
																											lblMensaje.setText("El campo NOMBRE de PERSONA FISICA se encuentra vacio");																		
																										}
																									}else{
																										txtRfcFisico.requestFocus();
																										lblMensaje.setText("El campo RFC de PERSONA FISICA se encuentra vacio");																	
																									}
																									
																									
																									
																								}else{		
																									if(cbTipo.getSelectionModel().getSelectedItem().equals("Moral")){
																										if(!txtRfcMoral.getText().trim().isEmpty()){
																											if(!txtEmpresa.getText().trim().isEmpty()){
																												if(!txtNombreContacto.getText().trim().isEmpty()){
																													if(!txtPaternoContacto.getText().trim().isEmpty()){
																														if(!txtMaternoContacto.getText().trim().isEmpty()){
																															if(!txtTelefonoMoral.getText().trim().isEmpty()){
																																if(!txtEmailMoral.getText().trim().isEmpty()){
																																	
																																	if (validar.validarRFC_moral(txtRfcMoral.getText())) {
																																		
																																		if (!(txtEmpresa.getText().trim().length() <=3)) {
																																			if (!(txtNombreContacto.getText().trim().length() <= 2)) {
																																			
																																				if (!(txtPaternoContacto.getText().trim().length() <= 3)) {
																																					
																																				if (!(txtMaternoContacto.getText().trim().length() <= 3)) {
																																					if (validar.validar_soloTelefono(txtTelefonoMoral.getText().trim())) {
																																						
																																						if (validar.validarEmail(txtEmailMoral.getText().trim())) {
																																							
																																						
																																				
																																	
																																	
																																							M = new ClientesMorales();
																																							C.getM().setRfc(new SimpleStringProperty(txtRfcMoral.getText()));
																																							C.getM().setEmpresa(new SimpleStringProperty(txtEmpresa.getText()));
																																							C.getM().setNombre(new SimpleStringProperty(txtNombreContacto.getText()));
																																							C.getM().setApellido_paterno(new SimpleStringProperty(txtPaternoContacto.getText()));
																																							C.getM().setApellido_materno(new SimpleStringProperty(txtMaternoContacto.getText()));
																																							C.getM().setTelefono(new SimpleStringProperty(txtTelefonoMoral.getText()));
																																							C.getM().setCorreo(new SimpleStringProperty(txtEmailMoral.getText()));
																																								if(C.insertarClientesMorales()==true){
																																									limpiar();
																																									actualizarTabla();
																																									lblMensaje.setText("Datos de persona moral insertados");
																																								}else{
																																									lblMensaje.setText("Datos de persona moral no insertados");
																																								}
																																		
																																		
																																		
																																						}else {
																																							txtEmailMoral.requestFocus();
																																							lblMensaje.setText("El campo correo electronico de contacto debe ser ejemplo: mail@mail.com");
																																							
																																						}
																																		
																																		
																																					}else {
																																						txtTelefonoMoral.requestFocus();
																																						lblMensaje.setText("El campo telefono de contacto debe ser de 7 caracteres numericos");
																																					}
																																		
																																				}else {
																																					txtMaternoContacto.requestFocus();
																																					lblMensaje.setText("El campo apellido materno de contacto debe ser mayor a 3 caracteres");
																																				}
																																		
																																				}else {
																																					txtPaternoContacto.requestFocus();
																																					lblMensaje.setText("El campo apellido paterno de contacto debe ser mayor a 3 caracteres");
																																				}
																																		
																																			}else {
																																				txtNombreContacto.requestFocus();
																																				lblMensaje.setText("El campo nombre de contacto debe ser mayor a 2 caracteres");
																																			}
																																		
																																		
																																	}else {
																																		txtEmpresa.requestFocus();
																																		lblMensaje.setText("El campo Empresa de contacto debe ser mayor a 3 caracteres");
																																	}
																																		
																																	}else {
																																		txtRfcMoral.requestFocus();
																																		lblMensaje.setText("El campo rfc de cliente moral debe iniciar con un espacio en blanco  ejemplo: ' LMC640609P48'");
																																	}
																																		
																																}else{
																																	txtEmailMoral.requestFocus();
																																	lblMensaje.setText("El campo CORREO ELECTRONICO de CONTACTO DE LA EMPRESA se encuentra vacio");																								
																																}
																															}else{
																																txtTelefonoMoral.requestFocus();
																																lblMensaje.setText("El campo TELEFONO de CONTACTO DE LA EMPRESA se encuentra vacio");																							
																															}
																														}else{	
																															txtMaternoContacto.requestFocus();
																															lblMensaje.setText("El campo APELLIDO MATERNO de CONTACTO DE LA EMPRESA se encuentra vacio");																						
																														}
																													}else{
																														txtPaternoContacto.requestFocus();
																														lblMensaje.setText("El campo APELLIDO PATERNO de CONTACTO DE LA EMPRESA se encuentra vacio");																					
																													}
																												}else{
																													txtNombreContacto.requestFocus();
																													lblMensaje.setText("El campo NOMBRE de CONTACTO DE LA EMPRESA se encuentra vacio");																				
																												}
																											}else{
																												txtEmpresa.requestFocus();
																												lblMensaje.setText("El campo EMPRESA de PERSONA MORAL se encuentra vacio");																			
																											}
																										}else{
																											txtRfcMoral.requestFocus();
																											lblMensaje.setText("El campo RFC de PERSONA MORAL se encuentra vacio");																		
																										}
																									
																									}// se cierra seleccion de clientes morales
																								
																								}// se cierra insertar morales
																								
																								
																								
																								
																								
																								
																								
																								
																								
																								
																								
																								
																								
																								
																								
																								
																								
																								
																								
																								
																							}else {
																								txtOtro.requestFocus();
																								lblMensaje.setText("El campo otro debe ser de 7,10 o 12 caracteres numericos");
																							}
																							}else {
																								txtCelular.requestFocus();
																								lblMensaje.setText("El campo celular debe contener 10 caracteres numericos");
																							}
																							}else {
																								txtTelefono.requestFocus();
																								lblMensaje.setText("El campo telefono debe contener 7 caracteres numericos");
																							}
																							}else {
																								txtEmail.requestFocus();
																								lblMensaje.setText("El campo correo electronico debe ser ejemplo: mail@mail.com ");
																							}
															
																							}else {
																							txtCodigoPostal.requestFocus();
																							lblMensaje.setText("El campo codigo postal deber de 5 caracteres numericos");
																							}
															
																							}else {
																								txtLocalidad.requestFocus();
																								lblMensaje.setText("El campo localidad debe ser mayor a 3 caracteres");
																							}
																							}else {
																								txtColonia.requestFocus();
																								lblMensaje.setText("El campo colonia debe ser mayor a 3 caracteres");
																							}
																							}else {
																								txtMunicipio.requestFocus();
																								lblMensaje.setText("El campo municipio debe ser mayor a 3 caracteres");
																							}
															
															
															
															
															

														}else{
															txtOtro.requestFocus();
															lblMensaje.setText("El campo OTRO TELEFONO de DATOS GENERALES se encuentra vacio");															
														}
													}else{
														txtCelular.requestFocus();
														lblMensaje.setText("El campo CELULAR de DATOS GENERALES se encuentra vacio");														
													}
												}else{
													txtTelefono.requestFocus();
													lblMensaje.setText("El campo TELEFONO de DATOS GENERALES se encuentra vacio");													
												}
											}else{
												txtEmail.requestFocus();
												lblMensaje.setText("El campo CORREO ELECTRONICO de DATOS GENERALES se encuentra vacio");												
											}
											
											
												}else {
													txtNumeroInterior.requestFocus();
													lblMensaje.setText("El campo numero interior se encuentra vacio");
												}
											
												}else {
													txtNumeroExterior.requestFocus();
													lblMensaje.setText("El campo numero exterior se encuantra vacio");												
												}										
												
										}else{
											txtCodigoPostal.requestFocus();
											lblMensaje.setText("El campo CODIGO POSTAL de DATOS GENERALES se encuentra vacio");											
										}
									}else{
										txtLocalidad.requestFocus();
										lblMensaje.setText("El campo LOCALIDAD de DATOS GENERALES se encuentra vacio");										
									}
								}else{
									txtColonia.requestFocus();
									lblMensaje.setText("El campo COLONIA de DATOS GENERALES se encuentra vacio");									
								}
							}else{
								txtCalle.requestFocus();
								lblMensaje.setText("El campo CALLE de DATOS GENERALES se encuentra vacio");								
							}
						}else{
							txtMunicipio.requestFocus();
							lblMensaje.setText("El campo MUNICIPIO de DATOS GENERALES se encuentra vacio");							
						}
					}else{
						cbEstado.requestFocus();
						lblMensaje.setText("El campo ESTADO de DATOS GENERALES se encuentra vacio");						
					}
				
				
				
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
			
		}
		
	
//	public void rfcfisico(String rfc){
//		try {
//			
//		
//		if (validar.validarTelefono(rfc)) {
//			System.out.println("es valido");
//		}else {
//			System.out.println("no es valido");
//		}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
//	
//	public static void main(String[] args) {
//		ControladorClientes c = new ControladorClientes();
//		c.rfcfisico("12345671");
//	}
	@FXML public void click_editar(){
		
	}		
		
	@FXML public void click_eliminar(){
			try {
				if(txtPais.getText().trim().isEmpty() ||
						  // cbEstado.getSelectionModel().getSelectedItem()==null ||
						  //txtMunicipio.getText().trim().isEmpty() ||
						   //txtCalle.getText().trim().isEmpty() ||
						   //txtColonia.getText().trim().isEmpty() ||
						   //txtLocalidad.getText().trim().isEmpty() ||
						   //txtCodigoPostal.getText().trim().isEmpty() ||
						   //txtEmail.getText().trim().isEmpty() ||
						   //txtTelefono.getText().trim().isEmpty() ||
						   //txtCelular.getText().trim().isEmpty() ||
						   txtOtro.getText().trim().isEmpty() ){
					lblMensaje.setText("Favor de llenar los campo");
				}else{
					if(C.getTipo().equals("Fisico")){
						int g = C.getCodigo_cliente();
								recuperarId(g);
						C = new Clientes();
						C.setCodigo_cliente(new SimpleIntegerProperty(g));
							if(C.eliminarClienteFisico() == true){
								lblMensaje.setText("cliente eliminado");
								actualizarTabla();
								limpiar();
							}else{
								lblMensaje.setText("cliente no eliminado");
							}						
					}else{
						if (C.getTipo().contains("Moral")){
							int f = C.getCodigo_cliente();
									recuperarId(f);
							System.out.println(f);
							//C = new Clientes();
							C.setCodigo_cliente(new SimpleIntegerProperty(f));
							if(C.eliminarClienteMorales() == true){
								lblMensaje.setText("cliente moral eliminado");
								actualizarTabla();
								limpiar();
							}else{
								lblMensaje.setText("no se elimino el cliente");
							}
						}
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
	}
	
	
	/*
	 * Metodo para seleccionar el RadioBoton de Fisicos llena la tabla
	 * con los clientes de personas fisicas
	 */
	@FXML public void click_rbFisico(){
			if (rbFisico.isSelected());  {
				inicializarTabla(true);
				
			}
			
		}
	
	
	/*
	 * Metodo para seleccionar el RadioBoton de Morales llena la tabla
	 * con los clientes de personas morales
	 */
	@FXML public void click_rbMoral(){
			if (rbMorales.isSelected()) {
				inicializarTabla(false);
			}
		}
		
	
	/*
	 * Metodo para inicializar la tabla con los clientes personas fisicas
	 */
	public void inicializarTabla(boolean tipo){
		if (tipo) {
			rbFisico.setSelected(true);
			rbMorales.setSelected(false);
			tpMoral.setDisable(true);
			tpContacto.setDisable(true);
			tpFisica.setDisable(false);
			limpiar();			
			cbTipo.getSelectionModel().selectFirst();
			try {
				tcRfc.setCellValueFactory(new PropertyValueFactory<Clientes, String>("F"));					
				tcCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombreCompleto"));
				tcDireccion.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccionCompleta"));
				tcTelefono.setCellValueFactory(new PropertyValueFactory<Clientes, String>("Telefono"));
				tcEmail.setCellValueFactory(new PropertyValueFactory<Clientes, String>("Email"));
				tcTipo.setCellValueFactory(new PropertyValueFactory<Clientes, String>("Tipo"));
				
				filasXPagina = contar(true);
				datos=C.getClientesFisicos();
				datosBusqueda = new FilteredList<>(datos);
				paginador.setPageCount(datosBusqueda.size()/filasXPagina);
				paginador.setPageFactory((Integer pagina) -> createPage(pagina));
				lblRegistro.setText(datos.size() + " Registros encontrados.");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else {
			rbMorales.setSelected(true);		
			rbFisico.setSelected(false);
			tpFisica.setDisable(true);
			tpMoral.setDisable(false);
			tpContacto.setDisable(false);
			limpiar();
			cbTipo.getSelectionModel().selectLast();
			try {
				tcRfc.setCellValueFactory(new PropertyValueFactory<Clientes, String>("M"));
				tcCliente.setCellValueFactory(c->c.getValue().getM().Empresa);
				tcDireccion.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccionCompleta"));
				tcTelefono.setCellValueFactory(new PropertyValueFactory<Clientes, String>("Telefono"));
				tcEmail.setCellValueFactory(new PropertyValueFactory<Clientes, String>("Email"));
				tcTipo.setCellValueFactory(new PropertyValueFactory<Clientes, String>("Tipo"));
				
				filasXPagina = contar(false);
				datos=C.getClientesMorales();
				datosBusqueda = new FilteredList<>(datos);
				System.out.println(datosBusqueda.size());
				System.out.println(filasXPagina);
				System.out.println(Math.round(datosBusqueda.size()/filasXPagina));
				paginador.setPageCount(datosBusqueda.size()/filasXPagina);
				paginador.setPageFactory((Integer pagina) -> createPage(pagina));
				lblRegistro.setText(datos.size() + " Registros encontrados.");
			} catch (Exception e) {
				e.printStackTrace(); 
			}
			
		}
			
		}
		
	
	/*
	 * Metodo para retornar alas cajas de textos los valores seleccionados en la tabla
	 */
	@FXML public void clickTabla(){
			
			if(tbClientes.getSelectionModel().getSelectedItem() != null){
				C = new Clientes();
				C = tbClientes.getSelectionModel().getSelectedItem();
				txtPais.setText(C.getPais());
				cbEstado.getSelectionModel().select(C.getEstado());
				txtMunicipio.setText(C.getMunicipio());
				txtLocalidad.setText(C.getLocalidad());
				txtColonia.setText(C.getColonia());
				txtCodigoPostal.setText(C.getCodigo_postal().toString());
				txtCalle.setText(C.getCalle());
				txtNumeroExterior.setText(C.getNumeroExterior());
				txtNumeroInterior.setText(C.getNumeroInterior());
				txtTelefono.setText(C.getTelefono());
				txtCelular.setText(C.getCelular());
				txtOtro.setText(C.getOtro());
				txtEmail.setText(C.getEmail());
				cbTipo.getSelectionModel().select(C.getTipo());
					if(C.getTipo().contains("Fisico")){
						tpMoral.setDisable(true);						
						txtNombreFisico.setText(C.getF().getNombre());
						txtRfcFisico.setText(C.getF().getRfc());
						txtPaternoFisico.setText(C.getF().getApellido_paterno());
						txtMaternoFisico.setText(C.getF().getApellido_materno());
						
						txtRfcMoral.clear();
						txtNombreContacto.clear();
						txtPaternoContacto.clear();
						txtMaternoContacto.clear();
						txtEmpresa.clear();
						txtTelefonoMoral.clear();
						txtEmailMoral.clear();
						
					}else{
						if(C.getTipo().contains("Moral")){
							txtRfcMoral.setText(C.getM().getRfc());
							txtNombreContacto.setText(C.getM().getNombre());
							txtPaternoContacto.setText(C.getM().getApellido_paterno());
							txtMaternoContacto.setText(C.getM().getApellido_materno());
							txtEmpresa.setText(C.getM().getEmpresa());
							txtTelefonoMoral.setText(C.getM().getTelefono());
							txtEmailMoral.setText(C.getM().getCorreo());
																				
							txtNombreFisico.clear();
							txtRfcFisico.clear();
							txtPaternoFisico.clear();
							txtMaternoFisico.clear();
						}else{
							System.out.println("no es moral");
						}
						System.out.println("no es fisico");
					}
			}else{
				System.out.println("no");
			}
						
		}
		
		
	@FXML public void click_emergente(){
		ventanas.asignarEmergente("../vista/fxml/vistaReciclajeClientes.fxml", "RECICLAJE");
	}
		
		//**********************************************//
		//	    EVENTO INICIALIZAR CONTROLADOR			//							
		//**********************************************//
		@Override
	public void initialize(URL arg0, ResourceBundle arg1) {			
				try {					
					txtPais.setText("Mexico");
					txtPais.setVisible(true);
					cbEstado.setItems(listComboboxEstados);
					cbTipo.setItems(listComboboxPersona);
					txtPais.setDisable(true);
	
					inicializarTabla(false);
//				
				} catch (Exception e) {
					e.printStackTrace();
				};
		}
		
		@SuppressWarnings("unused")
		private vista.Principal principal;

		public void setPrincipal(vista.Principal principal){
			this.principal=principal;
		}
}