package es.unican.is2;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import es.unican.is2.adt.IConjuntoOrdenado;

public class ConjuntoOrdenadoTest {

    private IConjuntoOrdenado<String> conjunto;

    @Before
    public void setUp() {
        // Inicializamos el conjunto antes de cada prueba
        conjunto = new ConjuntoOrdenado<String>();
    }

   
    // PRUEBAS DEL MÉTODO get(int index)

    @Test
    public void testGetValido() {
        // Caso: get(1) en [e1,e2,e3]: e2
        conjunto.add("e1");
        conjunto.add("e2");
        conjunto.add("e3");
        
        assertEquals("e2", conjunto.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetIndexSuperior() {
        // Caso: get(5) en [e1,e2,e3]: IndexOutOfBoundsException
        conjunto.add("e1");
        conjunto.add("e2");
        conjunto.add("e3");
        
        conjunto.get(5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetIndexNegativo() {
        // Caso: get(-1) en [e1,e2,e3]: IndexOutOfBoundsException
        conjunto.add("e1");
        conjunto.add("e2");
        conjunto.add("e3");
        
        conjunto.get(-1);
    }

    
    // PRUEBAS DEL MÉTODO add(E elemento)

    @Test
    public void testAddValido() {
        // Caso: add(e3) en [e1,e2]: true [e1,e2,e3]
        conjunto.add("e1");
        conjunto.add("e2");
        
        assertTrue(conjunto.add("e3"));
        assertEquals(3, conjunto.size());
        assertEquals("e3", conjunto.get(2));
    }

    @Test
    public void testAddConjuntoVacio() {
        // Caso: add(e3) en []: true [e3]
        assertTrue(conjunto.add("e1"));
        assertEquals(1, conjunto.size());
        assertEquals("e1", conjunto.get(0));
    }

    @Test(expected = NullPointerException.class)
    public void testAddNulo() {
        // Caso: add(null) en [e1,e2]: NullPointerException
        conjunto.add("e1");
        conjunto.add("e2");
        
        conjunto.add(null);
    }

    @Test
    public void testAddRepetido() {
        // Caso add(e2) en [e1,e2]: false
        conjunto.add("e1");
        conjunto.add("e2");
        
        // Intentamos añadir e2 de nuevo, debería devolver false y no cambiar el tamaño
        assertFalse(conjunto.add("e2"));
        assertEquals(2, conjunto.size());
    }
    
    @Test
    public void testAddElementoMenor() {
        // Añadimos elementos mayores primero
        conjunto.add("e2");
        conjunto.add("e3");
        
        // Añadimos un elemento menor, forzando a que se inserte al principio
        assertTrue(conjunto.add("e1"));
        
        // Comprobamos que se ha ordenado bien: [e1, e2, e3]
        assertEquals(3, conjunto.size());
        assertEquals("e1", conjunto.get(0));
    }


    // PRUEBAS DEL MÉTODO remove(int index)

    @Test
    public void testRemoveValido() {
        // Caso: remove(1) en [e1,e2,e3] -> elimina e2, quedan e1 y e3
        conjunto.add("e1");
        conjunto.add("e2");
        conjunto.add("e3");
        
        String eliminado = conjunto.remove(1);
        assertEquals("e2", eliminado);
        assertEquals(2, conjunto.size());
        assertEquals("e3", conjunto.get(1)); // e3 debe haber bajado a la posición 1
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveIndexSuperior() {
        conjunto.add("e1");
        conjunto.remove(2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveIndexNegativo() {
        conjunto.add("e1");
        conjunto.remove(-1);
    }

   
    // PRUEBAS DE size() Y clear()

    @Test
    public void testSize() {
        // Lista vacía
        assertEquals(0, conjunto.size());
        
        // Lista con elementos
        conjunto.add("e1");
        assertEquals(1, conjunto.size());
    }

    @Test
    public void testClear() {
        // Con lista vacía no debe dar error
        conjunto.clear();
        assertEquals(0, conjunto.size());
        
        // Con lista llena debe vaciarla
        conjunto.add("e1");
        conjunto.add("e2");
        conjunto.clear();
        assertEquals(0, conjunto.size());
    }
}
