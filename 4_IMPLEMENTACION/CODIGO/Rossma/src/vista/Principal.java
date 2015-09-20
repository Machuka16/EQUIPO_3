package vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Principal extends Application {

	private static Stage primaryStage;
	
	
	public static void main(String[] args){
//				SplashJF2 frame = new SplashJF2();
//		frame.setVisible(true);
//		
//		if(frame.retornar()){
//		}		
		launch(args);

}

	

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Principal.primaryStage=primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));
		Scene escena = new Scene(root);
		primaryStage.setTitle("Conexion");		
		primaryStage.setScene(escena);
		primaryStage.show();		
	}
	
	/**
     * Returns the main stage.
     * @return
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
	
	
}
