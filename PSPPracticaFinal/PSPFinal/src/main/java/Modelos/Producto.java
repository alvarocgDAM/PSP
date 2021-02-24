package Modelos;

public class Producto {

	private int id;
	private String nombreProducto;
	private double precioVenta;
	private double precioProveedor;
	private int cantidadStock;
	
	public Producto(int id, String nombreProducto, double precioVenta, double precioProveedor, int cantidadStock) {
		this.id = id;
		this.nombreProducto = nombreProducto;
		this.precioVenta = precioVenta;
		this.precioProveedor = precioProveedor;
		this.cantidadStock = cantidadStock;
	}
	
	public Producto() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(double precioVenta) {
		this.precioVenta = precioVenta;
	}

	public double getPrecioProveedor() {
		return precioProveedor;
	}

	public void setPrecioProveedor(double precioProveedor) {
		this.precioProveedor = precioProveedor;
	}

	public int getCantidadStock() {
		return cantidadStock;
	}

	public void setCantidadStock(int cantidadStock) {
		this.cantidadStock = cantidadStock;
	}
	
	
	
	
	
}
