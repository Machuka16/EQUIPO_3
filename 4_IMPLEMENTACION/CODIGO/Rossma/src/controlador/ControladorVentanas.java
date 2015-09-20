package controlador;


//import controller.ControllerMenu;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import vista.Principal;

public class ControladorVentanas {
	/*
	 * Atributos
	 */
	
	private static ControladorVentanas instancia;
	private Stage primaryStage;
	public static Stage dialogStage;
	private BorderPane contenedor;
	private BorderPane contenedorDialog;
	private AnchorPane subContenedorDialog;
	private Scene escena;
	private ControladorErrores error;
	/*
	 * Contructor privado
	 */
	private ControladorVentanas(){
		error = new ControladorErrores();
	}
	
	/*
	 * Recuperar la Instancia de la clase
	 */
	
	public static ControladorVentanas getInstancia(){
		if(instancia == null){
			instancia = new ControladorVentanas();
		}
		return instancia;
	}
	/*
	 * Establecer Escenario Principal
	 */
	public void setPrimaryStage(Stage primaryStage){
		this.primaryStage = primaryStage;
	}
	
		
	public void asignarMenu(String ruta, String titulo){
		try{
			FXMLLoader interfaz = new FXMLLoader(getClass().getResource(ruta));
			contenedor = (BorderPane)interfaz.load();
			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			Scene escena = new Scene(contenedor, screenBounds.getWidth(), screenBounds.getHeight());
			primaryStage.setScene(escena);
			primaryStage.setTitle(titulo);
			primaryStage.show();
			
		}catch(Exception e){
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
		}
	}
	
	public void asignarEscena(String ruta, String titulo, String nivel,String nombre){
		try{
			ControladorPrincipal.nombre = nombre;
			ControladorPrincipal.nivel= nivel;
			FXMLLoader interfaz = new FXMLLoader(getClass().getResource(ruta));
			contenedorDialog = (BorderPane)interfaz.load();			
			primaryStage = Principal.getPrimaryStage();
			primaryStage.setTitle(titulo);
			escena = new Scene(contenedorDialog);			
			primaryStage.setScene(escena);	
			primaryStage.centerOnScreen();
			//primaryStage.setResizable(false);
			primaryStage.show();
			primaryStage.setTitle("ROSSMA--- Usuario: "+nombre+"  Tipo: " +nivel);
			
		}catch(Exception e){
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
		}
	}
	
	public void asignarCentro(String ruta){
		try{
			FXMLLoader interfaz = new FXMLLoader(getClass().getResource(ruta));
			subContenedorDialog = (AnchorPane)interfaz.load();
			contenedorDialog.setCenter(subContenedorDialog);
			//Scene escena = new Scene(contenedorDialog);
			
			primaryStage.setScene(escena);
			primaryStage.centerOnScreen();
			primaryStage.show();
		}catch(Exception e){
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
		}
	}
	
	public void asignarEmergente(String ruta, String titulo){
		try {
			FXMLLoader emergente = new FXMLLoader(getClass().getResource(ruta));
			AnchorPane page = (AnchorPane)emergente.load();
			dialogStage = new Stage();
			dialogStage.setTitle(titulo);
			//dialogStage.setTitle("CATEGORIA");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);			
			dialogStage.show();
		} catch (Exception e) {
			e.printStackTrace();
			error.printLong(e.getMessage(), this.getClass().toString());
		}
	}
}
