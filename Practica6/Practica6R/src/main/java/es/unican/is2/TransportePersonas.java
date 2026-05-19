package es.unican.is2;

public class TransportePersonas extends Transporte {
	
	private int personas;

	// CC = 2 = 1 + if
	// CCog = 1
    public TransportePersonas(double horas, int personas) throws IllegalArgumentException {
        super(horas);
        if (personas <= 0) {
            throw new IllegalArgumentException("Número de personas no válido");
        }
        this.personas = personas;
    }

    // CC = 1
 	// CCog = 0
    public int getPersonas() {
        return personas;
    }

    @Override
    // CC = 2 = 1 + if
 	// CCog = 2 if else
    public double extraSueldo() {
        if (personas < 10) {
            return horas() * 0.5;
        } else {
            return horas();
        }
    }

    // WMC = 2 + 1 + 2 = 5
    // CCog = 1 + 0 + 2 = 3
    
}
