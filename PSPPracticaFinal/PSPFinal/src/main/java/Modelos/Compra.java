package Modelos;

import java.util.Date;

public class Compra {

	private int id;
	private Date fecha_compra;
	
	public Compra() {
		
	}
	
	public Compra(int id, Date fecha_compra) {
		this.id = id;
		this.fecha_compra = fecha_compra;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha_compra() {
		return fecha_compra;
	}

	public void setFecha_compra(Date fecha_compra) {
		this.fecha_compra = fecha_compra;
	}
	
	
}
