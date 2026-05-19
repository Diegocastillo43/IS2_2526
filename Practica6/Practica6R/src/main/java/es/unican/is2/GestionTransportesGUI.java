package es.unican.is2;
import java.util.LinkedList;
import java.util.List;
import fundamentos.*;

/**
 * Gestion de una empresa de transportes
 */
public class GestionTransportesGUI {

	/**
	 * Programa principal basado en menu 
	 */
	// CC = 6 = 1 + 1 while + 4 casos  
	// CCog = 3 = 1 por while + 1 por switch anidado nv 1 + 1 por penalización switch
	public static void main(String[] args) {
		// opciones del menu
		final int ANHADE_CONDUCTOR = 0, ANHADE_TRANSPORTE = 1, 
		SUELDO_CONDUCTOR = 2, MEJOR_CONDUCTOR = 3;

		// crea la empresa de transportes
		GestionTransportes gt = new GestionTransportes();
		// crea la ventana de menu
		Menu menu = new Menu("Transportes");
		menu.insertaOpcion("Anhade conductor", ANHADE_CONDUCTOR);
		menu.insertaOpcion("Anhade transporte", ANHADE_TRANSPORTE);
		menu.insertaOpcion("Sueldo conductor", SUELDO_CONDUCTOR);
		menu.insertaOpcion("Mejor conductor", MEJOR_CONDUCTOR);
		
		int opcion;

		// lazo de espera de comandos del usuario
		while(true) {
			opcion = menu.leeOpcion();

			// realiza las acciones dependiendo de la opcion elegida
			switch (opcion) {
			case  ANHADE_CONDUCTOR:
				anhadeConductor(gt);
				break;

			case ANHADE_TRANSPORTE:
				anhadeTransporte(gt);
				break;
				
			case SUELDO_CONDUCTOR:
				sueldoConductor(gt);
 				break;

			case MEJOR_CONDUCTOR:
				mejorConductor(gt);
				break;
			}
		}
	}

	// CC = 6 = 1 base + 1 for + 1 if + 1 else if + 1 if + 1 for
	// CCog = 8 = 1 for + 1 if anidado + 1 anidamiento + 1 else if + 1 if + 1 else + 1 for anidado + 1 anidamiento
	private static void mejorConductor(GestionTransportes gt) {
		List<Conductor> resultado = new LinkedList<Conductor>();
		double maxSueldo = 0.0;
		for (Conductor conductor : gt.conductores()) {
			if (conductor.sueldo() > maxSueldo) {
				maxSueldo = conductor.sueldo();
				resultado.clear();
				resultado.add(conductor);
			} else if (conductor.sueldo() == maxSueldo) {
				resultado.add(conductor);
			}
		}		
		String msj = "";
		if (resultado.size() == 0) {
			msj = "No hay conductores";
		} else {
			for (Conductor conductor : resultado) {
				msj += conductor.getNombre() + " "+conductor.getNombre()+"\n";
			}
		}
		mensaje("MEJOR CONDUCTOR", msj);
	}

	// CC = 2 = 1 + 1 if
	// CCog = 2 = 1 if + 1 else
	private static void sueldoConductor(GestionTransportes gt) {
		String dni;
		Lectura lect;
		Conductor c;
		lect = new Lectura("Transportes Peligrosos");
		lect.creaEntrada("DNI", "");
		lect.esperaYCierra();
		dni = lect.leeString("DNI");
		c = gt.buscaConductor(dni);
		if (c!=null){
			mensaje("Sueldo", "El sueldo del conductor es: "+c.sueldo());
		} else {
			mensaje("ERROR", "No existe un conductor con DNI "+dni);
		}
	}

	// CC = 5 = 1 + 1 if + 3 cases del switch
	// CCog = 4 = 1 if + 1 switch anidado + 1 anidamiento + 1 else
	private static void anhadeTransporte(GestionTransportes gt) {
		String dni;
		Lectura lect;
		Conductor c;
		lect = new Lectura("Nuevo transporte");
		lect.creaEntrada("DNI", "");
		lect.creaEntrada("Tipo Transporte: P | M | MP", "");
		lect.creaEntrada("Horas", 0);
		lect.creaEntrada("Personas", 0);
		lect.creaEntrada("Toneladas", 0);
		lect.esperaYCierra();
		dni = lect.leeString("DNI");
		String tipo = lect.leeString("Tipo Transporte: P | M | MP");
		int horas = lect.leeInt("Horas");
		int personas = lect.leeInt("Personas");
		int toneladas = lect.leeInt("Toneladas");

		Transporte t = null;
		c = gt.buscaConductor(dni);
		if (c!=null) {
			switch (tipo) {
				case "P":
					t = new TransportePersonas(horas, personas);
					c.anhadeTransporte(t);
					break;
				case "M":
					t = new TransporteMercancias(horas, toneladas);
					c.anhadeTransporte(t);
					break;
				case "MP":
					t = new TransporteMercanciasPeligrosas(horas, toneladas);
					c.anhadeTransporte(t);
					break;		
			}
		} else {
			mensaje("ERROR", "No existe un conductor con DNI "+dni);
		}
	}

	// CC = 2 = 1 + 1 if
	// CCog = 1 if
	private static void anhadeConductor(GestionTransportes gt) {
		String dni;
		Lectura lect;
		lect = new Lectura("Datos Conductor");
		lect.creaEntrada("DNI", "");
		lect.creaEntrada("Nombre","");
		lect.creaEntrada("Apellido1", "");
		lect.creaEntrada("Apellido2", "");
		lect.creaEntrada("Direccion", "");
		lect.esperaYCierra();
		dni = lect.leeString("DNI");
		String nombre = lect.leeString("Nombre");
		String apellido1 = lect.leeString("Apellido1");
		String apellido2 = lect.leeString("Apellido2");
		String direccion = lect.leeString("Direccion");
		// Anhade el conductor
		if (!gt.anhadeConductor(dni, nombre, apellido1, apellido2, direccion)) 
			mensaje("ERROR", "Ya existe un conductor con DNI "+dni);
	}

	/**
	 * Metodo auxiliar que muestra un ventana de mensaje
	 * @param titulo titulo de la ventana
	 * @param txt texto contenido en la ventana
	 */
	// CC = 1
	// CCOG = 0
	private static void mensaje(String titulo, String txt) {
		Mensaje msj = new Mensaje(titulo);
		msj.escribe(txt);

	}
	
	// WMC = 6 + 6 + 2 + 5 + 2 + 1 = 22
	// CCog = 3 + 8 + 2 + 4 + 1 + 0 = 18

}
