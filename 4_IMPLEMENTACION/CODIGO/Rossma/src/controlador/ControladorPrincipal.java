package controlador;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import modelo.ClientesFisicos;
import vista.Principal;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class ControladorPrincipal implements Initializable {
	//public static String nivel;
			
	@FXML
	Button btnClientes, btnPrueba;
	
			
	@FXML 
	Button btnProductos;
	
	@FXML	
	Button btnVentas;
	
	@FXML
	Button btnProveedor;
	
	@FXML
	Button btnStock;
		
	@FXML
	Button btnSalir;
	
	@FXML
	Button btnBase;
	
	@FXML
	Button btnNuevoUsuario;
	
	@FXML private Label lblModulo;
		
	//Image client = new Image(getClass().getResourceAsStream("../vista/imagenes/cliente2.png"));
	
	

		
		public static String nivel;

		public static String nombre;
		private ControladorVentanas ventanas;
		private Principal principal;
		
		public ControladorPrincipal() {
			// TODO Auto-generated constructor stub
			principal = new Principal();
			ventanas = ControladorVentanas.getInstancia();
		}
		
		
		
		@FXML public void clientes(){		
			ventanas.asignarCentro("../vista/fxml/vistaClientes.fxml");	
			lblModulo.setText("CLIENTES");
		}
		
		public void proveedores(ActionEvent event){
			ventanas.asignarCentro("../vista/fxml/vistaProveedores.fxml");
			lblModulo.setText("PROVEEDORES");
		}
		
		public void productos(ActionEvent event){
			ventanas.asignarCentro("../vista/fxml/vistaProductos.fxml");
			lblModulo.setText("PRODUCTOS");
		}
		public void ventas(ActionEvent event){
			ventanas.asignarCentro("../vista/fxml/vistaVentas.fxml");
			lblModulo.setText("VENTAS");
		}
		
		public void stock(ActionEvent event){
			ventanas.asignarCentro("../vista/fxml/vistaStock.fxml");
			lblModulo.setText("STOCK");
		}
		
		public void usuarios(ActionEvent event){
			ventanas.asignarCentro("../vista/fxml/vistaNuevoUsuario.fxml");
			lblModulo.setText("USUARIOS");	
		}
		
		@FXML public void desarrolladores(){
			ventanas.asignarEmergente("../vista/fxml/vistaDesarrolladores.fxml", "DESARROLLADORES");
		}
		
		@FXML public void respaldo(){
			Backup respaldo = new Backup();
			respaldo.setVisible(true);
			respaldo.setSize(500, 500);
			respaldo.setLocationRelativeTo(null);
		   // respaldo.setDefaultCloseOperation(respaldo.EXIT_ON_CLOSE);
			//respaldo.setBounds(450, 100, 500, 500);
		}
		
		@FXML public void ayuda(){
			String ruta="../vista/pdf/lectura.pdf"; 
			try {
			     File path = new File (ruta);
			     Desktop.getDesktop().open(path);
			}catch (IOException ex) {
			     ex.printStackTrace();
			     System.out.println("error");
			}
			//JOptionPane.showConfirmDialog("No existe\n"+ruta, "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		@FXML public void cerrarSesion() throws Exception{
			ventanas.asignarEmergente("../vista/fxml/cerrarSesion.fxml", "Confirmación");
			
		}
		
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			ventanas = ControladorVentanas.getInstancia();
			//btnPrueba.setGraphic(new ImageView(client));
			lblModulo.setText("PRINCIPAL");
			
			if(ControladorPrincipal.nivel.equals("administrador")){
				//btnClientes.setDisable(false);
				
				}else if(ControladorPrincipal.nivel.equals("empleado")){
				btnBase.setDisable(true);
				btnClientes.setDisable(true);
				btnNuevoUsuario.setDisable(true);
				btnProveedor.setDisable(true);
			
				}
			
		}
		
		
	

}
