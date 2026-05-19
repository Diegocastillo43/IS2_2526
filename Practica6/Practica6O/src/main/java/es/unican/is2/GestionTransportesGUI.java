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
	 * CC: 1 base + 1 while + 4 cases del switch principal + 1 if + 1 if 
	 * + 3 cases switch anidado + 1 if + 1 for + 1 if + 1 else if + 1 if + 1 for = 17
	 * Desglose CCog por niveles de anidamiento (N):
	 * +1 por el while (N1)
	 * +2 por el switch de opcion (N2)
	 * +3 por el if de anhadeConductor (N3)
	 * +3 por el if de anhadeTransporte (c!=null) (N3)
	 * +4 por el switch de tipo anidado en el if (N4)
	 * +1 por el else
	 * +3 por el if de sueldoConductor (N3)
	 * +1 por el else
	 * +3 por el for de mejorConductor (N3)
	 * +4 por el if (sueldo > max) anidado en el for (N4)
	 * +4 por el else if (sueldo == max) anidado en el for (N4)
	 * +3 por el if (resultado.size == 0) (N3)
	 * +1 por el else
	 * +4 por el for anidado en el else (N4)
	 * Total = 1+2+3+3+4+1+3+1+3+4+4+3+1+4 = 37
	 */
	public static void main(String[] args) {
		// opciones del menu
		final int ANHADE_CONDUCTOR = 0, ANHADE_TRANSPORTE = 1, 
		SUELDO_CONDUCTOR = 2, MEJOR_CONDUCTOR = 3;

		// variables auxiliares
		String dni;
		Lectura lect;
		Conductor c;

		// crea la empresa de transportes
		gestionTransportes gt = new gestionTransportes();
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
				break;

			case ANHADE_TRANSPORTE:
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
							t = new Transporte(horas,CategoriaTransporte.Personas, personas);
							c.anhadeTransporte(t);
							break;
						case "M":
							t = new Transporte(horas, CategoriaTransporte.Mercancias, toneladas);
							c.anhadeTransporte(t);
							break;
						case "MP":
							t = new Transporte(horas, CategoriaTransporte.MercanciasPeligrosas, toneladas);
							c.anhadeTransporte(t);
							break;		
					}
				} else {
					mensaje("ERROR", "No existe un conductor con DNI "+dni);
				}
				break;
				
			case SUELDO_CONDUCTOR:
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
 				break;

			case MEJOR_CONDUCTOR:
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
				break;
			}
		}
	}

	/**
	 * Metodo auxiliar que muestra un ventana de mensaje
	 * @param titulo titulo de la ventana
	 * @param txt texto contenido en la ventana
	 */
	// CC = 1
	// CCog = 0
	private static void mensaje(String titulo, String txt) {
		Mensaje msj = new Mensaje(titulo);
		msj.escribe(txt);

	}
	
	// WMC = 17 + 1 = 18
	// CCog = 37 + 0 = 37

}
