package es.unican.is2;

import static org.junit.Assert.*;
import org.junit.Test;
import java.time.LocalDate;

public class ClienteTest {

	@Test
	public void testTotalSegurosConMinusvalia() {
		// Cliente con minusvalía (descuento 25%)
		Cliente c = new Cliente("11111111A", "Juan", true);

		// Añadimos un seguro que sabemos que cuesta 1000€ (Sin oferta, potencia baja)
		Seguro s = new Seguro(1, "1111AAA", 80, Cobertura.TODO_RIESGO, LocalDate.now().minusYears(2));
		c.getSeguros().add(s);

		// Esperado: 1000 * 0.75 = 750€
		assertEquals(750.0, c.totalSeguros(), 0.001);
	}

	@Test
	public void testTotalSegurosSinMinusvalia() {
		Cliente c = new Cliente("22222222A", "Ana", false);
		Seguro s = new Seguro(2, "2222AAA", 80, Cobertura.TODO_RIESGO, LocalDate.now().minusYears(2));
		c.getSeguros().add(s);

		// Esperado: 1000€ (sin descuento)
		assertEquals(1000.0, c.totalSeguros(), 0.001);
	}
}