package controlador;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Validar {
	static final String patt_cadena = "[A-Za-z]*";
	static final String patt_numero = "[0-9]*";
	static final String patt_telefono = "[0-9]{7,12}";
	static final String patt_CPostal = "[0-9]{5}";
	static final String patt_email = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	static final String patt_rfc = "[A-Z0-9]{13}";
	static final String patt_rfc_fisico = "^(([A-Z]|[a-z]){4})"+"([0-9]{6})"+"((([A-Z]|[a-z]|[0-9]){3}))";
	static final String patt_rfc_moral = "^((\\s){1})"+"(([A-Z]|[a-z]){3})"+"([0-9]{6})"+"((([A-Z]|[a-z]|[0-9]){3}))";
	
	static final String patt_soloTelefono = "[0-9]{7}";
	static final String patt_celular = "[0-9]{10}";
	
	public boolean validarCelular(String celular){
		Pattern pattern = Pattern.compile(patt_celular);
		Matcher matcher = pattern.matcher(celular);
		return matcher.matches();
	}
	public boolean validar_soloTelefono(String sTelefono){
		Pattern pattern = Pattern.compile(patt_soloTelefono);
		Matcher matcher = pattern.matcher(sTelefono);
		return matcher.matches();
	}
	
		public boolean validarRFC_moral(String rfcMoral){
			Pattern pattern = Pattern.compile(patt_rfc_moral);
			Matcher matcher = pattern.matcher(rfcMoral);
			return matcher.matches();
		}
		
		public boolean validarRFC_fisico(String rfcFisico){
			Pattern pattern = Pattern.compile(patt_rfc_fisico);
			Matcher matcher = pattern.matcher(rfcFisico);
			return matcher.matches();
		}
	
	
		public boolean validarCadena(String cadena){
			Pattern pattern = Pattern.compile(patt_cadena);
			Matcher matcher = pattern.matcher(cadena);
			return matcher.matches();
		}
		
		public boolean validarCPostal(String postal){
			Pattern pattern = Pattern.compile(patt_CPostal);
			Matcher matcher = pattern.matcher(postal);
			return matcher.matches();
		}
		
		public boolean validarEntero(String entero){
			Pattern pattern = Pattern.compile(patt_numero);
			Matcher matcher = pattern.matcher(entero);
			return matcher.matches();
		}
		
		public boolean validarTelefono(String telefono){
			Pattern pattern = Pattern.compile(patt_telefono);
			Matcher matcher = pattern.matcher(telefono);
			return matcher.matches();
		}
		
		public boolean validarEmail(String email){
			Pattern pattern = Pattern.compile(patt_email);
			Matcher matcher = pattern.matcher(email);
			return matcher.matches();
		}
		
		public boolean validarRfc(String rfc){
			Pattern pattern = Pattern.compile(patt_rfc);
			Matcher matcher = pattern.matcher(rfc);
			return matcher.matches();
		}
	
	
	/*public static void main(String [] args){
		Validar validar = new Validar();
			if(validar.validarCadena("hola")){
				System.out.println("correcto");
			}else{
				System.out.println("incorrecto");
			}
			
			if(validar.validarEntero("12234566")){
				System.out.println("es un numero");
			}else{
				System.out.println("no es un numero");
			}
			
			if(validar.validarRfc("q1w2e3r4t5y6u")){
				System.out.println("son 13 digitos");
			}else{
				System.out.println("no son 13 digitos");
			}
			
			if(validar.validarTelefono("12345678912345")){
				System.out.println("telefono");
			}else{
				System.out.println("no es telefono");
			}
	}*/
	
}