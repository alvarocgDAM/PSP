package Servidor;

import java.io.*;
import java.math.RoundingMode;
import java.net.*;

import DAO.CompraDAO;
import DAO.CompraEmpleadoDAO;
import DAO.CompraProductoDAO;
import DAO.EmpleadoDAO;
import DAO.ProductoDAO;
import Modelos.Empleado;
import Modelos.Producto;
import Util.EmailUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class HiloServidor extends Thread {

	//DECLARACION DE ATRIBUTOS
	private Socket cliente;
	private LocalDateTime hoy;
	private String emailEmergencia;

	//DECLARACION DE DAOS
	private EmpleadoDAO empleadoDAO;
	private ProductoDAO productoDAO;
	private CompraDAO compraDAO;
	private CompraProductoDAO compraProductoDAO;
	private CompraEmpleadoDAO compraEmpleadoDAO;

	//Constructor
	public HiloServidor(Socket cliente) {
		this.cliente = cliente;

		hoy = LocalDateTime.now();

		emailEmergencia = obtenerEmailEmergencia("App.config");

		empleadoDAO = new EmpleadoDAO();
		productoDAO = new ProductoDAO();
		compraDAO = new CompraDAO(this.hoy);
		compraProductoDAO = new CompraProductoDAO();
		compraEmpleadoDAO = new CompraEmpleadoDAO();
	}

	@Override
	public void run() {
		try {
			// Voy a recibir la ID del empleado y validarla
			InputStream entrada = cliente.getInputStream();
			DataInputStream flujoEntrada = new DataInputStream(entrada);
			
			//Lo usaremos en la caja
			DataOutputStream flujoSalida = new DataOutputStream(cliente.getOutputStream());			

			// Recibo un mensaje del tipo "Login;ID"
			String loginString = flujoEntrada.readUTF();
			String idCaja = loginString.split(";")[1];

			Empleado empleadoCaja = validarEmpleado(idCaja);
			
			//Si el empleado es correcto, se actualiza su ultima sesion
			if (empleadoCaja != null) {
				
				empleadoDAO.actualizarUltimaSesion(idCaja, hoy);
				
			}

			// Ahora debo mandarle el objeto empleado al servidor
			ObjectOutputStream outObjeto = new ObjectOutputStream(cliente.getOutputStream());
			outObjeto.writeObject(empleadoCaja);

			// Ahora nos quedamos escuchando a que nos digan si se va a comprar, si se va
			// pedir la caja o si se va a salir
			while (true) {

				String accion = flujoEntrada.readUTF();

				if (accion.contains("Cobro")) {

					String[] splitCobro = accion.split(";");

					String productoId = splitCobro[1];
					String cantidad = splitCobro[2];

					crearCobroOrMail(productoId, cantidad, idCaja);

				} else if (accion.contains("Caja")) {
					
					flujoSalida.writeUTF(calcularBeneficios(idCaja));

				} else {

					entrada.close();
					flujoEntrada.close();
					cliente.close();
					//Si cierro el hilo de esta manera, el servidor Main sigue lanzado y puedo meter más cajas, 
					//pero el servidor Main lanza una excepción de que el socket se ha cerrado.
					//Si lo hago con System.exit(0) no da excepción, pero el servidor Main también se cierra y habria que
					//reabrirlo para que otras cajas se puedan conectar a el, asi que.... no se que es mejor, sinceramente
					this.interrupt();

				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Comprueba que haya un empleado con la id introducida en BD y si existe, lo devuelve
	 * 
	 * @param id
	 * @return
	 */
	private Empleado validarEmpleado(String id) {

		ResultSet rs = empleadoDAO.getEmpleado(id);

		try {
			if (rs.next()) { // id,ultima_sesion,fecha_contratacion

				int rsId = rs.getInt(1);
				Date rsUltimaSesion = rs.getDate(2);
				Date rsFechaContratacion = rs.getDate(3);

				return new Empleado(rsId, rsUltimaSesion, rsFechaContratacion);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Else...
		return null;

	}

	/**
	 * Crea los registros de BD necesarios para registrar un cobro o manda un mail al supervisor en caso de no haber stock suficiente del producto seleccionado
	 * 
	 * @param productoId
	 * @param cantidad
	 * @param empleadoId
	 */
	private void crearCobroOrMail(String productoId, String cantidad, String empleadoId) {

		//Reformateo la cantidad para que no lleve finales de linea y para que sea un int
		cantidad = cantidad.replace("\r", "");
		int cantidadInt = Integer.parseInt(cantidad);
		
		int cantidadBD = productoDAO.getCantidadProducto(productoId);
		
		// En el caso de que haya suficiente
		if (cantidadInt <= cantidadBD) {

			// creo una entidad compra, y luego una entidad compra_producto y
			// compra_empleado
			String compraId = compraDAO.insertarCompra();

			compraProductoDAO.insertarCompraProducto(productoId, compraId, cantidad);
			compraEmpleadoDAO.insertarCompraEmpleado(compraId, empleadoId);

		} else {

			Producto producto = productoDAO.productoFromId(productoId);
			
			// Mando un mail
			EmailUtil.enviarEmail(producto,hoy,emailEmergencia);

		}

	}
	
	/**
	 * Devuelve los beneficios obtenidos en el día de hoy y para el empleado de la caja activa
	 * 
	 * @param empleadoId
	 * @return
	 */
	private String calcularBeneficios(String empleadoId) {
		
		DecimalFormat df = new DecimalFormat("##.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		
		String resultado = df.format(compraDAO.calcularBeneficios(empleadoId));
		
		return resultado;
		
	}
	
	/**
	 * Devuelve el email de emergencia leyendo un fichero de configuracion
	 * 
	 * @param rutaFichero
	 * @return
	 */
	private String obtenerEmailEmergencia(String rutaFichero) {
		
        String linea= null;
		BufferedReader lector;
		
		try {
			
			lector = new BufferedReader(new FileReader(rutaFichero));
			while ((linea = lector.readLine()) != null) {
				if (linea.contains("EmailEmergencia")) {
					
					//limpiamos
					String email = linea.replace("<EmailEmergencia>","");
					email = email.replace("</EmailEmergencia>","");
					email = email.replace("\t","");
					
					lector.close();
					
					return email;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		return linea;
	
	}

}