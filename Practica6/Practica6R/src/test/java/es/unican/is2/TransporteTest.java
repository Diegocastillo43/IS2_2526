package es.unican.is2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TransporteTest {

    @Test
    public void testConstructor() {

        // Casos validos
        TransporteMercancias tm = new TransporteMercancias(1, 1);
        assertEquals(1, tm.horas());
        assertEquals(1, tm.getToneladas());
        
        TransporteMercanciasPeligrosas tmp = new TransporteMercanciasPeligrosas(10, 1000);
        assertEquals(10, tmp.horas());
        assertEquals(1000, tmp.getToneladas());

        TransportePersonas tp = new TransportePersonas(10, 10);
        assertEquals(10, tp.horas());
        assertEquals(10, tp.getPersonas());
 

        // Casos no validos
        assertThrows(IllegalArgumentException.class, () -> new TransporteMercancias(0, 1));
        assertThrows(IllegalArgumentException.class, () -> new TransportePersonas(-5, 10));
        
        assertThrows(IllegalArgumentException.class, () -> new TransporteMercancias(10, 0));
        assertThrows(IllegalArgumentException.class, () -> new TransporteMercancias(10, -5));
        assertThrows(IllegalArgumentException.class, () -> new TransportePersonas(10, 0));
    }

}
