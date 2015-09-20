package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import vista.Principal;

public class CerrarSesion implements Initializable{
	private Principal principal;
	private ControladorVentanas conVentanas;
	
	public CerrarSesion(){
		principal = new Principal();
		conVentanas.getInstancia();
	}
	
	@FXML public void cerrarSi() throws Exception{
		principal.start(principal.getPrimaryStage());
		principal.getPrimaryStage().centerOnScreen();
		conVentanas.dialogStage.close();

	}
	
	@FXML public void cerrarNo(){
		conVentanas.dialogStage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
