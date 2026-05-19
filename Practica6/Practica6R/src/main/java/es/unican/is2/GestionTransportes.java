package es.unican.is2;
import java.util.ArrayList;
import java.util.List;

public class GestionTransportes {

	private ArrayList<Conductor> cs = new ArrayList<Conductor>();
	
	// CC = 3 = base + if + for
	// CCog = 3 = 1 for + 2 if por estar dentro 
	public Conductor buscaConductor(String DNI) {		
		for(Conductor c: cs) 
			if (c.dni().equals(DNI))
				return c;
		
		return null;
	}
	
	// CC = 2 = base + if
	// CCog = 1 = if 
	public boolean anhadeConductor(String dni, String nombre, String apellido1, String apellido2, String direccion) {
		if (buscaConductor(dni) != null)
			return false;
		cs.add(new Conductor(dni, nombre, apellido1, apellido2,direccion));
		return true;
	}

	// CC = 1
	// CCog = 0 
	public List<Conductor> conductores() {
		return cs;
	}
	
	// WMC = 3 + 2 + 1 = 6
	// CCog = 3 + 1 + 0 = 4
	
}
