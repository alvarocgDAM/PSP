package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class EmpleadoDAO extends AbstractDAO {
	
	public EmpleadoDAO() {
		super();
	}
	

	/**
	 * Obtiene todos los datos de un empleado dada una ID de empleado
	 * 
	 * @return java.sql.ResultSet
	 */
	public ResultSet getEmpleado(String id) {
			
			ResultSet rs = null;
			
			try {
				Statement stm = this.conexion.createStatement();
				
				rs = stm.executeQuery("SELECT id,ultima_sesion,fecha_contratacion FROM empleado WHERE id=" + id + ";");
				
			} catch (SQLException e) {
				e.printStackTrace();
				
			} 
			
			return rs;
		}
	
	/**
	 * Actualiza la ultima sesión de un empleado a la fecha de hoy
	 * 
	 */
	public void actualizarUltimaSesion(String id, LocalDateTime hoy) {
		
		try {
			Statement stm = this.conexion.createStatement();
			
			//Formateo la fecha YYYY-MM-DD
			String fechaHoy = "'" + hoy.getYear() + "-" + hoy.getMonthValue() + "-" + hoy.getDayOfMonth() + "'";
			
			stm.executeUpdate("UPDATE empleado SET ultima_sesion=" + fechaHoy  + " WHERE id=" + id + ";");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
