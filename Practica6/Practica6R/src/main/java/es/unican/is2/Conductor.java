package es.unican.is2;
import java.util.ArrayList;

/**
 * Clase que representa a un conductor, con sus datos personales
 * y los transportes que ha realizado. 
 */
public class Conductor {

	private ArrayList<Transporte> transportes = new ArrayList<Transporte>();
	private String dni;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String direccion;

	// CC = 5 = 1 + 1 if + 3 ors
	// CCog = 2 = 1 if + 1 ors
	public Conductor(String dni, String nombre, String apellido1,
			String apellido2, String direccion) {
		if (dni == null || nombre == null || apellido1 == null || direccion == null) {
			throw new IllegalArgumentException();
		}
		this.dni = dni;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.direccion = direccion;
	}

	// CC = 1
	// CCog = 0
	public String dni() {
		return dni;
	}

	// CC = 1
	// CCog = 0
	public String getDni() {
		return dni;
	}

	// CC = 1
	// CCog = 0
	public String getNombre() {
		return nombre;
	}

	// CC = 1
	// CCog = 0
	public String getApellido1() {
		return apellido1;
	}

	// CC = 1
	// CCog = 0
	public String getapellido2() {
		return apellido2;
	}

	// CC = 1
	// CCog = 0
	public String getDireccion() {
		return direccion;
	}

	// CC = 2 = 1 + 1 for
	// CCog = 1
	public double sueldo() {
		double sueldoTransportes = 0;
		for (Transporte t : transportes) {
			sueldoTransportes += t.horas() * 5 + t.extraSueldo();
		}
		return 700 + sueldoTransportes;
	}

	// CC = 1
	// CCog = 0
	public void anhadeTransporte(Transporte t) {
		transportes.add(t);
	}
	
	// WMC = 5 + 2 + 1 + 1... = 14
	// CCog = 2 + 1 + 0 + 0 ... = 3

}
