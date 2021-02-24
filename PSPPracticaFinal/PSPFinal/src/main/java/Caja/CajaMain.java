package Caja;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;

import Modelos.Empleado;

public class CajaMain {

	static final int NUMEROPUERTO = 6000;
	static final String HOST = "localhost";
	static LocalDateTime hoy = LocalDateTime.now();
	

	public static void main(String[] args) throws Exception {
				
		System.out.println("------ABRIENDO CAJA------");

		//Declaración de socket, flujos de entrada y salida y scanner 
		Socket caja = new Socket(HOST, NUMEROPUERTO);
		DataOutputStream flujoSalida = new DataOutputStream(caja.getOutputStream());
		DataInputStream flujoEntrada = new DataInputStream(caja.getInputStream());
		Scanner keyboard = new Scanner(System.in);

		System.out.println("Bienvenido! Introduzca su complicadísima ID de empleado para iniciar la caja: ");
		keyboard.useDelimiter("\n");
		String id = keyboard.next();

		//Se manda la ID del empleado al servidor para su validación
		flujoSalida.writeUTF("Login;"+ id);

		//Tras enviar la string, quedarme escuchando para recibir el objeto empleado
		ObjectInputStream inObjeto = new ObjectInputStream(caja.getInputStream());
		Empleado empleadoCaja = (Empleado)inObjeto.readObject();
		
		// "El cliente solo continuará el proceso si ha iniciado sesión." Jose, 2021.
		if (empleadoCaja == null) {
			System.out.println("¡¡¡¡¡¡¡¡¡¡¡ERROR CRÍTICO DEL SISTEMA !!!!!!");
			System.out.println("Es broma jeje te has equivocado de ID :( ");
			System.out.println("Cerrando la caja, por torpe");
			
			System.exit(0);
		}
		
		System.out.println("Bienvenido, Empleado " + empleadoCaja.getId() + "!");
		System.out.println("Recursos humanos dice que deberiamos meter vuestros nombres en la base de datos, pero es que no nos importan :( A VENDER");
		System.out.println();
		System.out.println();
		
		//Menú de caja
		while (true) {
			
			System.out.println("Bueno, qué quieres hacer?");
			System.out.println();
			System.out.println("1.  Cobrar compra");
			System.out.println("2.  Obtener la caja del día");
			System.out.println("3.  Salir");
			
			String respuesta = keyboard.next();
			
			//Si la respuesta es 1              COBRAR COMPRA
			//                                  -------------  
			if (respuesta.contains("1")) {
				
				System.out.println("ARTICULOS EN OFERTA!! NO TE LOS PIERDAS! (oferta solo valida hasta el dia " + hoy.getDayOfMonth() + " del mes " + hoy.getMonthValue() + ")" );
				System.out.println();
				System.out.println("1.  Arroz");
				System.out.println("2.  Salchichon");
				System.out.println("3.  Yogures");
				System.out.println("4.  Queso fresco");
				System.out.println("5.  Detergente");
				System.out.println();
				
				System.out.print("Selecciona el producto que desees: ");
				
				String productoId = keyboard.next();
				System.out.println();
				
				System.out.print("Selecciona la cantidad de producto deseada: ");
				String cantidad = keyboard.next();
				
				//Mandamos el mensaje de cobro al servidor
				flujoSalida.writeUTF("Cobro;" + productoId + ";" + cantidad);
				
				//el server recibe el mensaje y manda el javamail... 
				
				System.out.println("--------------0--------------");
				
			}
			//Si la respuesta es 2              OBTENER LA CAJA DEL DÍA     
			//                                  -----------------------
			else if (respuesta.contains("2")) {
				
				//Mandamos el mensaje de caja al servidor
				flujoSalida.writeUTF("Caja");
				
				String total = flujoEntrada.readUTF();
				
				System.out.println("El total de beneficios obtenidos hoy ha sido de " + total + " €.");
				System.out.println("Bastante poco, pero bueno... Igualmente vas fuera el mes que viene, así que... :P");
				
			} 
			//Si la respuesta es 3              SALIR  
			//                                  -----   
			else {
				flujoSalida.writeUTF("Salir");
				
				keyboard.close();
				flujoSalida.close();
				inObjeto.close();
				caja.close();
				System.exit(0);

			}
			
		}

	}

}
