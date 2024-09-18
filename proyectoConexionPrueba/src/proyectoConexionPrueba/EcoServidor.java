package proyectoConexionPrueba;

import java.io.*;
import java.net.*;
import java.util.Date;
/**
 *  EJEMPLO BÁSICO DE UN SERVIDOR TCP/IP
 *  
 *  Abre el puerto y espera que alguien se conecte.
 *  Cuando alguien se ha conectado hace eco de los datos que recibe.
 *  
 * @author alberto
 *
 */
public class EcoServidor {
	public static final int PUERTO = 4444;

	public static void main(String[] args) throws IOException {
		
		
		
		
		// Establece el puerto en el que escucha peticiones
		ServerSocket socketEscucha = null;

		try {
			socketEscucha = new ServerSocket(PUERTO);
		} catch (IOException e) {
			System.err.println("No puede escuchar en el puerto: " + PUERTO);
			System.exit(1);
		}

		Socket socketConexion = null;
		BufferedReader conexionEntrada = null;
		PrintWriter    conexionSalida = null;

		System.out.println("Escuchando: " + socketEscucha);
		try {
			// Se bloquea hasta que recibe alguna petición de un cliente
			// abriendo un socket para el cliente
			socketConexion = socketEscucha.accept();
			System.out.println("Connexión acceptada: " + socketConexion);
			// Establece canal de entrada
			conexionEntrada = new BufferedReader(new InputStreamReader(socketConexion.getInputStream()));
			// Establece canal de salida
			conexionSalida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketConexion.getOutputStream())), true);

			// Hace eco de lo que le proporciona el cliente, hasta que recibe "Adios"
			while (true) {
				String str = conexionEntrada.readLine();
				/*System.out.println("Cliente: " + str);
				conexionSalida.println(">>" + str);
				if (str.equals("Adios"))
					break;*/
				conexionSalida.println(responderComoUnServidorWeb());
				break;
			}

		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
		
		conexionSalida.close();
		conexionEntrada.close();
		socketConexion.close();
		socketEscucha.close();
	}
	
	/*
	 *  Funcion de prueba para simular un servidor Web
	 */
	private static String responderComoUnServidorWeb ( ) {
		
		String cabecera =   "HTTP/1.0 200 ok"+"\n"
    	                   +"Server: Mini Server Server/1.0"+"\n"
    	                   + "Date: " + new Date() + "\n"
    	                   + "Content-Type: text/html" + "\n";
		
    	String salida  = "<html><head></head><body><h1> HOLA MUNDO </H1><BR></body></html>";
    	
    	return cabecera+"\n"+ salida;
	
	}
}
