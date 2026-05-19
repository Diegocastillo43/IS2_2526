package es.unican.is2;

public class TransporteMercancias extends Transporte {

	private int toneladas;
	
	// CC = 2 = 1 base + 1 por if
	// CCog = 1 if
	public TransporteMercancias(double horas, int toneladas) throws IllegalArgumentException {
		super(horas);
		if (toneladas <= 0) {
            throw new IllegalArgumentException("El tonelaje debe ser positivo");
        }
        this.toneladas = toneladas;
		
	}

	// CC = 1
	// CCog = 0
	public int getToneladas() {
        return toneladas;
    }
	
	@Override
	// CC = 1
	// CCog = 0
	public double extraSueldo() {
		return toneladas * 2;
	}
	
	// WMC = 2 + 1 + 1 = 4
	// CCog = 1 + 0 + 0 = 1

}
