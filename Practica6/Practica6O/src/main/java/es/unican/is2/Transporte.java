package es.unican.is2;
/* Clase que representa un transporte realizado por un conductor */
public class Transporte {
	
	private double horas;
	private int ton;
	private int personas;
	private CategoriaTransporte cat;
	
	/**
	 * Constructor de la clase Transporte
	 * @param horas Horas que ha durado el transporte
	 * @param cat Categoria del transporte
	 * @param valor En caso de ser un transporte de tipo Personas, 
	 * representa el numero de personas, en caso de ser de tipo Mercancias 
	 * representa las toneladas
	 */ 
	// CC = 1 (base) + 2 por los dos or en el primer if + 1 por el if-else
	// CCog = 2 = 1 primer if + 1 segundo if
	public Transporte(double horas, CategoriaTransporte cat, int valor) throws IllegalArgumentException {
		if (horas <= 0 || valor <= 0 || cat == null) {
			throw new IllegalArgumentException();
		}
		this.horas = horas;
		this.cat = cat;
		if (cat.equals(CategoriaTransporte.Personas)) {
			this.personas = valor;
		} else  {
			this.ton = valor;
		}
	}
	
	// CC = 1
	// CCog = 0
	public double horas() {
		return horas;
	}

	// CC = 1
	// CCog = 0
	public CategoriaTransporte categoria() {
		return cat;
	}

	// CC = 1
	// CCog = 0
	public int ton() {
		return ton;
	}

	// CC = 1
	// CCog = 0
	public int getPersonas() {
		return personas;
	}
	
	// WMC = 4 + 1 + 1 + 1 + 1 = 8
	// CCog = 2 + 0 + 0 + 0 + 0 = 2
	
}
