package Modelos;

import java.io.Serializable;
import java.util.Date;

public class Empleado implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private Date ultima_sesion;
	private Date fecha_contratacion;
	
	
	public Empleado(int id, Date ultima_sesion, Date fecha_contratacion) {
		this.id = id;
		this.ultima_sesion = ultima_sesion;
		this.fecha_contratacion = fecha_contratacion;
	}


	public Empleado() {
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Date getUltima_sesion() {
		return ultima_sesion;
	}


	public void setUltima_sesion(Date ultima_sesion) {
		this.ultima_sesion = ultima_sesion;
	}


	public Date getFecha_contratacion() {
		return fecha_contratacion;
	}


	public void setFecha_contratacion(Date fecha_contratacion) {
		this.fecha_contratacion = fecha_contratacion;
	}

}
