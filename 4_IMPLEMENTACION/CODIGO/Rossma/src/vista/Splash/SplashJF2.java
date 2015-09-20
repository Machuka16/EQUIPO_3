package vista.Splash;

import java.awt.Color;
import java.awt.SplashScreen;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.stage.Stage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.UIManager;

public class SplashJF2 extends JFrame {

	private JPanel contentPane;
	private Stage dialogStage;
	private int iiii;
	private boolean bandera;
	private int retorno;

	public SplashJF2() {
		
				//**********************************************//
				//		AQUI SE LE DAN LOS ATRIBUTOS AL JFrame  //							
				//**********************************************//
				

				setUndecorated(true);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setBounds(100, 100, 717, 498);
				setLocationRelativeTo(null);
				setBackground(new Color(0, 255, 0, 0));
				
				//**********************************************************//
				//		AQUI SE HACE INSTANCIA DE LA CLASE ContentPane      //
				//      Y SE LE A GREGA AL ContentPane DEL JFrame           //							
				//**********************************************************//
				
				
				ContentPane contentPane_1 = new ContentPane();
				setContentPane(contentPane_1);
				getContentPane().setBackground(Color.BLACK);
				contentPane_1.setLayout(null);
				
				//**********************************************************//
				//		AQUI SE HACE INSTANCIA DE LA BARRA DE PROGRESO      //
				//				Y SE LE AGREGA AL ContectPane				//
				//**********************************************************//
				
				JProgressBar progressBar = new JProgressBar();
				progressBar.setStringPainted(true);
				progressBar.setBorder(UIManager.getBorder("ComboBox.editorBorder"));
				progressBar.setValue(20);
				progressBar.setForeground(Color.BLUE);
				progressBar.setBounds(95, 426, 529, 21);
				contentPane_1.add(progressBar);
						//**************************************************************************//
						//ESTE METODO ES PARA CUANDO LA BARRA LLEGUE A 100 SE CIERRE LA APLICACION //
						//*************************************************************************//
				
						progressBar.addChangeListener(new ChangeListener() {
							
							public void stateChanged(ChangeEvent arg0) {
								if(progressBar.getValue() == 100){
									
									
									//new SplashJF2(progressBar.getValue());
									setVisible(false);
									
									//System.exit(0);
								}
							}
						});
						
				//******************************************************************//
				//		AQUI SE HACE INSTANCIA DE LA CLASE QUE MUESTRA LA IMAGEN    //
				//					Y SE LE AGREGA AL ContectPane					//
				//******************************************************************//

				
				CargarImagen panel = new CargarImagen();
				panel.setOpaque(true);
				panel.setBounds(0, 0, 717, 498);
				getContentPane().add(panel);		
				setVisible(true);
				
				//******************************************************************//
				//		ESTE METODO ES EL QUE HACE CORRER LA BARRA DE PROGRESO      //
				//******************************************************************//
				
					new Thread(){			
					public void run(){
						int i = 0;
						for( i = 0; i < 101; i++){				
						try{
							sleep(40);
							progressBar.setValue(i);
							retorno = i;
										
						}catch(InterruptedException ex){
							Logger.getLogger(SplashScreen.class.getName()).log(Level.SEVERE,null,ex);
							
							
						}
						
						}
						
					}
			
			}.start();
				
				
				
			}
	
	public boolean retornar(){
		

		while(retorno < 100){
			bandera = false;
			
		}bandera = true;
		return bandera;
	}

	
}
