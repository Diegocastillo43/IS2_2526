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
	private String dire;

	// CC = 2 if con clausulas or
	// CCog = 1 = if 
	public Conductor(String dni, String nombre, String apellido1,
			String apellido2, String direccion) {
		if (dni == null || nombre == null || apellido1 == null || direccion == null) {
			throw new IllegalArgumentException();
		}
		this.dni = dni;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.dire = direccion;
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
	public String apellido2() {
		return apellido2;
	}

	// CC = 1
	// CCog = 0
	public String getDire() {
		return dire;
	}

	// CC = 6 = 1 + 1 for + 2 switch con 3 casos + 2 if-else 
	// CCog = 6 = 1 for + 2 switch + 3 if-else
	public double sueldo() {
		double sueldoTransportes = 0;
		for (Transporte t : transportes) {
			double sueldoExtraTransporte = 0.0;
			switch (t.categoria()) {
				case Mercancias:
					sueldoExtraTransporte = t.ton() * 2;
					break;
				case MercanciasPeligrosas:
					sueldoExtraTransporte = t.ton() * 2 + 50;
					break;
				case Personas:
					if (t.getPersonas() < 10)
						sueldoExtraTransporte = t.horas() * 0.5;
					else
						sueldoExtraTransporte = t.horas();
					break;
			}
			sueldoTransportes += t.horas() * 5 + sueldoExtraTransporte;
		}
		return 700 + sueldoTransportes;
	}

	// CC = 1
	// CCog = 0
	public void anhadeTransporte(Transporte t) {
		transportes.add(t);
	}
	
	// WMC = 2 + 6 + 1 + 6 = 15
	// CCog = 1 + 0 + 0 + 6 = 7

}
