package es.unican.is2;
/* Clase que representa un transporte realizado por un conductor */
public abstract class Transporte {
	
	private double horas;
	
	/**
	 * Constructor de la clase Transporte
	 * @param horas Horas que ha durado el transporte
	 * @param cat Categoria del transporte
	 * @param valor En caso de ser un transporte de tipo Personas, 
	 * representa el numero de personas, en caso de ser de tipo Mercancias 
	 * representa las toneladas
	 */  
	// CC = 2 = 1 (base) + 1 if
	// CCog = 1 if 
	public Transporte(double horas) throws IllegalArgumentException {
		if (horas <= 0) {
			throw new IllegalArgumentException();
		}
		this.horas = horas;
	}
	
	// CC = 1
	// CCog = 0
	public double horas() {
		return horas;
	}
	
	// CC = 1
	// CCog = 0
	public abstract double extraSueldo();
	
	// WMC = 2 + 1 + 1 = 4
	// CCog = 1 + 0 + 0 = 1
	
}
