package proyectoConexionPrueba;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * Servidor Multihilo TCP, hace eco de lo que recibe
 */

public class ServerMTCP{
	
	public static HashMap<String, String> theliza = new HashMap<String, String>();
	

    public static void main(String args[]){
    	
    	theliza.put("HOLA","Hola, ¿qué tal?");
    	theliza.put("ENCANTADO","Encantado de conocerte yo también");
    	theliza.put("ADIOS","Adiós, espero volverte a ver pronto");
    	theliza.put("HORA", "Lo siento no llevo reloj");
    	theliza.put("NOMBRE","Mi nombre es Eliza");
    	theliza.put("CACA","Creo que tu lenguaje no es adecuado");
        // Establecemos el número de puerto a utilizar.
        int serverPort = 4444;
        
        // Creamos una instancia para esperar las peticiones de los clientes.
        ServerSocket listenSocket;
        
        // Socket para conexión.
        Socket clientSocket;

        // Usamos la clase conection.
        Connection c;

        try{
            
            // Creamos el objeto para esperar peticiones de los clientes.
            listenSocket = new ServerSocket(serverPort);
            // CICLO DEL SERVIDOR 
            while (true){
                
                // Dejamos invocado el servidor esperando haste que un cliente
                //se conecte. clientSocket = Socket nuevo para comunicaciones.
                clientSocket = listenSocket.accept();
                System.out.println(" NUEVO CLIENTE: " + clientSocket.getInetAddress()+
                		" PUERTO:"+clientSocket.getPort());                  
                // Se establece la conexión, y se vuelve a esperar un nuevo cliente.
                c = new Connection(clientSocket);
               
            }
            
        // Control de excepciones.
        }catch(IOException e){
            System.out.println("Error en socket: " + e.getMessage());
        }
    }
}

/**
*
* @author Iván Durango
* Hilo de ejecución que procesa cada una de la peticiones
*/
class Connection extends Thread{

   // Instanciamos los flujos de datos de entrada y salida,y el socket para
   // conexión.
   Socket clientSocket;
   BufferedReader entrada = null;
   PrintWriter salida = null;

   // No hace nada
   public static String procesarMensaje ( String cadena) {
   	
	   return cadena;
   }
   
   // Constructor.
   public Connection(Socket aSocket){
       
       // Asocia el Socket(this) con el del cliente que conecta.
       clientSocket = aSocket;
       
       try {
           
           // Creamos los flujos de entrada y salida de datos, y lo se los 
           // asociamos al socket cliente.
	   // Establece canal de entrada
           entrada = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	   // Establece canal de salida
           salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())),true);
       
       } catch (IOException ex) {
           System.out.println("Error en conexion: " + ex.getMessage());
       }
       
       // Lanzamos el método run.
       this.start();
   }
   
   //Método que devuelve el String que has pasado como parámetro pero invertido
   public String RevertMessage(String message) 
   {
	   return new StringBuilder(message).reverse().toString().trim();
   }
   
   public String AnswerBotEliza(String message) 
   {
		String answer = "";
		String notFound = "Lo siento, no te comprendo";
		
			boolean encontrado = false;
			for ( Map.Entry<String, String> entrada: ServerMTCP.theliza.entrySet()){
			// Si la linea contiene la entrada en al clave
				if ( message.toUpperCase().contains(entrada.getKey())){
					answer = entrada.getValue();
					encontrado = true;
					break;
				}
			}
			if ( !encontrado)
				answer = notFound;
			
		return answer;
   }
   
   
   

   public void run(){
       
       try {
    	   
    	   
                       
           while (true){
           String msg = entrada.readLine();
           
           //TODO INTRODUCIR MÉTODO AnswerBotEliza
           salida.println(AnswerBotEliza(msg));
           
		   if (msg.toUpperCase().equals("ADIOS"))
			   break;
           }
           
       // Control de excepciones.
       } catch (Exception ex) {
           System.err.println(" Fin / Error de conexión." + clientSocket.getInetAddress() +"\n" );
       }
   }
}
