package DAO;

import java.sql.SQLException;
import java.sql.Statement;

public class CompraProductoDAO extends AbstractDAO{

	public CompraProductoDAO() {
		super();
	}
	
	
	/**
	 * Inserta una compra_producto en la base de datos
	 * 
	 */
	public void insertarCompraProducto(String productoId, String compraId, String cantidadProducto) {
			
			try {
				Statement stm = this.conexion.createStatement();
				
				stm.executeUpdate("INSERT INTO producto_compra (id_producto,id_compra,cantidad_producto) VALUES ("+ productoId +"," + compraId + "," + cantidadProducto + ");");				
				
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
			
	}
	
}
