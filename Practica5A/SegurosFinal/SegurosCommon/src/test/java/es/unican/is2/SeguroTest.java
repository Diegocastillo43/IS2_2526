package es.unican.is2;

import static org.junit.Assert.*;
import org.junit.Test;
import java.time.LocalDate;

public class SeguroTest {

	private Seguro sut;

	@Test
	public void testPrecioSeguroNoEnVigor() {
		// CP: Fecha de inicio mañana -> El valor retornado será 0
		sut = new Seguro(1, "1234ABC", 100, Cobertura.TODO_RIESGO, LocalDate.now().plusDays(1));
		assertEquals(0.0, sut.precio(), 0.001);
	}

	@Test
	public void testPrecioTercerosPotenciaLimiteInferior() {
		// CP: Hoy, 90 CV (límite subida 5%), Terceros (400€ base)
		// Cálculo: (400 + 5%) = 420. Oferta 1er año (-20%) = 336€
		sut = new Seguro(2, "1111AAA", 90, Cobertura.TERCEROS, LocalDate.now());
		assertEquals(336.0, sut.precio(), 0.001);
	}

	@Test
	public void testPrecioTercerosLunasPotenciaMedia() {
	    // CP: Hoy, 100 CV (subida 5%), Terceros + Lunas (600€ base)
	    // Cálculo: (600 + 5%) = 630. Oferta 1er año (-20%) = 504€
	    sut = new Seguro(5, "5555EEE", 100, Cobertura.TERCEROS_LUNAS, LocalDate.now());
	    assertEquals(504.0, sut.precio(), 0.001);
	}
	
	@Test
	public void testPrecioTodoRiesgoPotenciaAlta() {
		// CP: Hace 6 meses (oferta activa), 120 CV (subida 20%), Todo Riesgo (1000€ base)
		// Cálculo: (1000 + 20%) = 1200. Oferta 1er año (-20%) = 960€
		sut = new Seguro(3, "2222BBB", 120, Cobertura.TODO_RIESGO, LocalDate.now().minusMonths(6));
		assertEquals(960.0, sut.precio(), 0.001);
	}

	@Test
	public void testPrecioSinOfertaPotenciaBaja() {
		// CP: Hace 2 años (sin oferta), 80 CV (0% subida), Todo Riesgo (1000€ base)
		// Cálculo: 1000€
		sut = new Seguro(4, "3333CCC", 80, Cobertura.TODO_RIESGO, LocalDate.now().minusYears(2));
		assertEquals(1000.0, sut.precio(), 0.001);
	}

}
