package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class CompraDAO extends AbstractDAO {
	
	LocalDateTime hoy;

	public CompraDAO(LocalDateTime hoy) {
		super();
		this.hoy = hoy;
	}
	
	
	/**
	 * Inserta una compra y devuelve su ID autogenerada
	 * 
	 * @return String
	 */
	public String insertarCompra() {
			
			try {
				Statement stm = this.conexion.createStatement();
				
				//Formateo la fecha YYYY-MM-DD
				String fechaHoy = "'" + hoy.getYear() + "-" + hoy.getMonthValue() + "-" + hoy.getDayOfMonth() + "'";
				
				stm.executeUpdate("INSERT INTO compra (fecha_compra) VALUES (" + fechaHoy  + ");",
                        Statement.RETURN_GENERATED_KEYS);
				
				ResultSet generatedKeys = stm.getGeneratedKeys();
				
				generatedKeys.next();
				
				String compraId = generatedKeys.getString(1);
				
				return compraId;
				
			} catch (SQLException e) {
				e.printStackTrace();
				
				return null;
			}
			
		
	}
	
	/**
	 * Devuelve el cálculo de los beneficios de la caja de hoy
	 * 
	 * @return double
	 */
	public float calcularBeneficios(String empleadoId) {
		
		try {
			Statement stm = this.conexion.createStatement();
			
			//Formateo la fecha YYYY-MM-DD
			String fechaHoy = "'" + hoy.getYear() + "-" + hoy.getMonthValue() + "-" + hoy.getDayOfMonth() + "'";
			
			ResultSet rs = stm.executeQuery("SELECT PC.cantidad_producto,P.precio_venta,P.precio_proveedor FROM compra C " + 
											"LEFT JOIN producto_compra PC ON PC.id_compra=C.id " + 
											"LEFT JOIN producto P ON P.id=PC.id_producto " + 
											"LEFT JOIN compra_empleado CE ON CE.id_compra=C.id " + 
											"WHERE C.fecha_compra=" + fechaHoy + " AND CE.id_empleado=" + empleadoId + ";");
			
			float beneficioTotal = 0;		
			
			 while (rs.next()) {
				 
				 int cantidad = rs.getInt(1);
				 float precioVenta = rs.getFloat(2);
				 float precioProveedor = rs.getFloat(3);
				 
				 beneficioTotal += (precioVenta-precioProveedor) * cantidad;
				 
			 }
			 
			return beneficioTotal;
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			return 0;
		} 
		
	}
}
