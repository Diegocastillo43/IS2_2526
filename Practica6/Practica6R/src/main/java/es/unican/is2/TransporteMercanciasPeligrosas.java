package es.unican.is2;

public class TransporteMercanciasPeligrosas extends TransporteMercancias {
	
	// CC = 1
	// CCog = 0
	public TransporteMercanciasPeligrosas(double horas, int toneladas) throws IllegalArgumentException {
        super(horas, toneladas);
    }
	
	@Override
	// CC = 1
	// CCog = 0
    public double extraSueldo() {
        return super.extraSueldo() + 50;
    }
	
	// WMC = 1 + 1 = 2
	// CCog = 0 + 0 = 0

}
