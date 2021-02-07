import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) throws IOException {

		String host = "localhost";// Host                            YO HABLO CONMIGO MISMO, PERO PODEMOS CAMBIAR AQUI PARA HABLAR CON OTRA IP
		int numeroPuerto = 6000;  // Puerto
		
		//Incialización de sockets y streams
		//ServerSocket servidor = new ServerSocket(numeroPuerto);
		Socket clienteConectado = null;
		InputStream entrada = null;
		OutputStream salida = null;
		
		/////////////////////////////////////////////////////////
		//      Este lado del Walkie se inicia el segundo,     //
		//  por lo que es el primero que pide la comunicación  //
		/////////////////////////////////////////////////////////
		
		//Pedimos la conexión al otro lado
		clienteConectado = new Socket(host,numeroPuerto); 
		System.out.println("_-WALKIE TOLKIE INICIADO-_");

		while (true) { // Bucle de walkie

			while (true) { // Estoy mandando yo los mensajes (bucle de habla)
				
				salida = clienteConectado.getOutputStream();
				DataOutputStream flujoSalida = new DataOutputStream(salida);
				String mensaje = null;
				
				// Preparamos la entrada por teclado
				Scanner keyboard = new Scanner(System.in);
				keyboard.useDelimiter("\n");
				mensaje = keyboard.next();
				
				// Mando el mensaje al otro
				flujoSalida.writeUTF(mensaje);
				
				if (mensaje.equals("cambio\r")) { // Si he dicho cambio, paso al bucle de habla
					
					break;
					
				} else if (mensaje.equals("corto\r")) { // Si he dicho corto, corto la ejecución del programa
					
					clienteConectado.close();
					keyboard.close();
					System.exit(0);
					
				}
				
				
			} // Acaba el bucle de habla
			
			while (true) { // Estoy recibiendo yo (bucle de escucha)

				entrada = clienteConectado.getInputStream();
				DataInputStream flujoEntrada = new DataInputStream(entrada);
				
				// Recibo el mensaje que me mandan
				String mensaje = flujoEntrada.readUTF();
				System.out.println(mensaje);

				if (mensaje.equals("cambio\r")) { // Si me dicen cambio, paso al bucle de habla

					break;

				} else if (mensaje.equals("corto\r")) { // Si me dicen corto, corto la ejecución del programa
					
					clienteConectado.close();
					System.exit(0);
					
				}

			} //Acaba el bucle de escucha
			
			
		}
	}
}
