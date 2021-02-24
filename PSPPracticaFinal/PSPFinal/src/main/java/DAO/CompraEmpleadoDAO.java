package DAO;

import java.sql.SQLException;
import java.sql.Statement;

public class CompraEmpleadoDAO extends AbstractDAO{

	public CompraEmpleadoDAO() {
		super();
	}
	
	
	/**
	 * Inserta una compra_empleado en la base de datos
	 * 
	 */
	public void insertarCompraEmpleado(String compraId, String empleadoId) {
			
			try {
				Statement stm = this.conexion.createStatement();
				
				stm.executeUpdate("INSERT INTO compra_empleado (id_compra,id_empleado) VALUES ("+ compraId +"," + empleadoId + ");");				
				
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
			
	}
	
}
