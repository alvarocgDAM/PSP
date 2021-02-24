package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorMain {

	//////////////
	//CONSTANTES//
	//////////////
	static final int NUMERO_PUERTO = 6000; 
	
	//Desde aquí arranca el servidor
	public static void main(String[] args) throws IOException {

		
		//Incialización de sockets y streams
		ServerSocket servidor = new ServerSocket(NUMERO_PUERTO);
		
		//Nos quedamos escuchando al otro lado
		System.out.println("Servidor iniciado... A VENDER");
		
		while (true) {
			
            Socket caja  = new Socket();
            
            //PUNTO DE ESPERA
            caja = servidor.accept();
            
            HiloServidor hilo = new HiloServidor(caja );
            hilo.start(); 
           
		}
		
	}
	
	
}
