package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Modelos.Producto;

public class ProductoDAO extends AbstractDAO {

	public ProductoDAO() {
		super();
	}
	

	/**
	 * Obtiene la cantidad en stock de un producto dado
	 * 
	 * @return int cantidad_stock
	 */
	public int getCantidadProducto(String id) {
			
			
			try {
				Statement stm = this.conexion.createStatement();
				
				ResultSet rs = stm.executeQuery("SELECT cantidad_stock FROM producto WHERE id=" + id + ";");
				
				rs.next();
				
				int cantidad = rs.getInt(1);
				
				return cantidad;
				
			} catch (SQLException e) {
				e.printStackTrace();
				
				return 0;
			} 
			
		}
	
	/**
	 * Devuelve un objeto producto a partir de una ID de producto
	 * 
	 * @param id
	 * @return Producto
	 */
	public Producto productoFromId(String id) {
		
		try {
			Statement stm = this.conexion.createStatement();
			
			ResultSet rs = stm.executeQuery("SELECT id,nombre_producto,precio_venta,precio_proveedor,cantidad_stock FROM producto WHERE id=" + id + ";");
			
			rs.next();
			
			int idProducto = rs.getInt(1);
			String nombreProducto = rs.getString(2);
			double precioVenta = rs.getDouble(3);
			double precioProveedor = rs.getDouble(4);
			int cantidadStock = rs.getInt(5);
			
			Producto producto = new Producto(idProducto, nombreProducto, precioVenta, precioProveedor, cantidadStock);
			
			return producto;
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			return null;
		} 
		
	}
}
