package controlador;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Dialogs.DialogOptions;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import modelo.Conexion;
import modelo.Usuario;
import vista.Principal;


public class ControladorLogin implements Initializable {
	private Conexion con;
	private Usuario modeloUsuario;
	private ControladorVentanas ventanas;


	private GridPane grid = new GridPane();
	private String usernameResult,passwordResult;
	private TextField username = new TextField();
	private PasswordField password = new PasswordField(); 
	private Callback myCallback;
	private Stage stage;
	public static String usuario = " ";
	// #endregion
	
	// #region Controles
	
	 @FXML TextField txtBaseDatos, txtIP, txtPuerto;
//	 @FXML Label lblSalida;
	 @FXML TitledPane tpServidor,tpLogin;
	 @FXML Accordion accLogin;
//	 @FXML Accordion accPane;	 
	 //controles del inicio de sesión
	 @FXML TextField txtUser;
	 @FXML PasswordField txtCon;
		@FXML
		private Label lblSalida;
	
		@FXML
		private Label lblUsuario,lblMensaje;
		
		@FXML 
		private TextField txtUsuario,txtUsuarioo;
		
		@FXML 
		private Label lblContraseña;
		
		@FXML 
		private PasswordField txtContrasenia,txtContraseniaa;
		
		@FXML 
		private Button btnConectar;


	 
	 /*
	  * Método asociado al botón conectar para establecer conexión
	  */
	@FXML public void click_btnConectar(){
		con =con.getInstancia();
		
		if(txtBaseDatos.getText().isEmpty()==false &
				txtUsuarioo.getText().isEmpty()==false &
				txtContraseniaa.getText().isEmpty()==false &
				txtIP.getText().isEmpty()==false &
				txtPuerto.getText().isEmpty()==false){
			//Se asigna datos a los atributos
			con.setUsuario(txtUsuario.getText().trim());
			con.setBd(txtBaseDatos.getText().trim());
			con.setContrasenia(txtContrasenia.getText().trim());;
			con.setIp(txtIP.getText().trim());
			con.setPuerto(txtPuerto.getText().trim());
		}
		String mensaje=con.conectar();
		lblMensaje.setText(mensaje);
	}
	

	@FXML
	public void click_titledServidor(){
//		stage=Principal.getPrimaryStage();	
//		dialogAcceso();
//		DialogResponse resp = Dialogs.showCustomDialog(stage, grid, "ï¿½Eres Administrador?", "Confirmar identidad", DialogOptions.OK_CANCEL, myCallback);
//		
//		if(resp==DialogResponse.OK){
//			if(usernameResult.equals("Reina") & passwordResult.equals("12345")){
//				tpServidor.setExpanded(true);
//			}
//			else{
//				tpServidor.setExpanded(false);
//				lblMensaje.setText("Acceso denegado");
//			}
//		}
		
		
		
		if (!usuario.equals("administrador")) {
			tpServidor.setExpanded(false);
		
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Autentificación");
		dialog.setHeaderText("Favor de iniciar cesión como administrador");

		// Set the icon (must be included in the project).
		//dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField username = new TextField();
		username.setPromptText("Usuario");
		PasswordField password = new PasswordField();
		password.setPromptText("Contraseña");

		grid.add(new Label("Usuario:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Contraseña:"), 0, 1);
		grid.add(password, 1, 1);

		// Habilitar botón de acceso / desactivar en función de si se ha introducido un nombre de usuario .
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		//Haga un poco de validación (utilizando la sintaxis lambda Java 8 ) . .
		username.textProperty().addListener((observable, oldValue, newValue) -> {
		    loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);
		dialog.initModality(Modality.WINDOW_MODAL);
		//dialog.showAndWait();
		

		// Solicitud enfoque en el campo nombre de usuario por defecto.
		Platform.runLater(() -> username.requestFocus());

		// Convertir el resultado a un par nombre de usuario - contraseña cuando se hace clic en el botón de inicio de sesión.
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return new Pair<>(username.getText(), password.getText());
		    }
		    return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();
		modeloUsuario = new Usuario();
		modeloUsuario.setNombre(username.getText());
		modeloUsuario.setContrasenia(password.getText());
	
		if (modeloUsuario.Existe()) {
			System.out.println(modeloUsuario.getPrivilegio());
				if (modeloUsuario.getPrivilegio().equals("administrador")) {
					tpServidor.setExpanded(true);
					result.ifPresent(usernamePassword -> {
					    System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
					    usuario = modeloUsuario.getPrivilegio();
					});
				}else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Alerta");
					alert.setHeaderText("Los campos no coinciden con un adminstrador");
					//alert.setContentText("Favor de verificar los campos");
//					alert.initModality(Modality.WINDOW_MODAL);
//					dialog.showAndWait();
					alert.showAndWait();
				}
			
				
		}else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Alerta");
			alert.setHeaderText("Ninguna coincidencia encontrada");
			alert.setContentText("Favor de verificar los campos");
//			dialog.showAndWait();
			alert.showAndWait();
		
		}
		}
		
		
	}

	public void dialogAcceso(){
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));		
		username.setPromptText("Username");		
		password.setPromptText("Password");

		grid.add(new Label("Username:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Password:"), 0, 1);
		grid.add(password, 1, 1);

	
		myCallback = new Callback() {
		@Override
		public Object call(Object arg0) {
			 usernameResult = username.getText();
			 passwordResult = password.getText();
			 return null;
		}
		};

	
	}

	@FXML
	public void validarUsuario(){
		//Accesar 
		if(txtUsuario.getText().isEmpty() | txtContrasenia.getText().isEmpty()){
			lblSalida.setText("Faltan datos por ingresar.");
		}
		else{
			//Validar si existe el usuario
			if(modeloUsuario==null){
				modeloUsuario= new Usuario();
			}
			//Asignamos los datos
			modeloUsuario.setNombre(txtUsuario.getText());
			modeloUsuario.setContrasenia(txtContrasenia.getText());
			//Verificamos si existe en la BD
			boolean resultado= modeloUsuario.Existe();
			if(resultado){
				ventanas = ControladorVentanas.getInstancia();
				ventanas.setPrimaryStage(Principal.getPrimaryStage());
				ventanas.asignarEscena("../vista/fxml/Principal.fxml", "Menu",modeloUsuario.getPrivilegio(),modeloUsuario.getNombre());
//				ventanas.asignarMenu("../vista/fxml/Principal.fxml", "Menu",modeloUsuario.getPrivilegio(),modeloUsuario.getNombre());
			}
			else
				System.out.println("Usuario no válido");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		//accPane.setExpandedPane(tpInicio);
		//accLogin.setExpandedPane(tpLogin);
		
	}
	
	// #endregion
}
