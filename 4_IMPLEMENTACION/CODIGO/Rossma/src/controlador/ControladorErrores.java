package controlador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControladorErrores {
	
	private DateFormat dateFormat;
	private Date date;

	
	public ControladorErrores(){

		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = new Date();
	}
	public void printLong(String mensaje,String clase) {
		FileWriter pw = null;
		BufferedWriter bw = null;
		try {
			
			File archivo = new File("D:\\log.txt");
			pw = new FileWriter(archivo,true);
			bw = new BufferedWriter(pw);
			bw.write(clase);
			bw.newLine();
			bw.write(mensaje + " " + dateFormat.format(date) + " ");
			bw.newLine();
			bw.write("**********************************************************");
			bw.close();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		
		}
	}

}
