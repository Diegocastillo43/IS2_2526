package es.unican.is2;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class VistaAgenteIT {

    private FrameFixture window;
    
    // Necesitamos las capas reales para la integración
    private IGestionClientes clientes;
    private IGestionSeguros seguros;
    private IInfoSeguros info;
    private IClientesDAO daoClientes;
    private ISegurosDAO daoSeguros;

    @BeforeClass
    public static void setUpOnce() {
        // Obligatorio para evitar problemas de hilos en Swing
        FailOnThreadViolationRepaintManager.install();
    }

    @Before
    public void setUp() {
        /* * CONFIGURACIÓN DE INTEGRACIÓN:
         * Aquí inicializamos los DAOs reales (conectados a la BD H2) 
         * y la capa de Negocio.
         */
        daoClientes = new ClientesDAO(); // Asegúrate de que el nombre coincide con tu clase
        daoSeguros = new SegurosDAO();   // Asegúrate de que el nombre coincide con tu clase
        
        GestionSeguros negocio = new GestionSeguros(daoClientes, daoSeguros);
        
        // La vista usa las interfaces
        clientes = negocio;
        seguros = negocio;
        info = negocio;

        // Lanzamos la interfaz en el hilo de eventos de GUI
        VistaAgente frame = GuiActionRunner.execute(() -> 
            new VistaAgente(clientes, seguros, info)
        );
        
        window = new FrameFixture(frame);
        window.show(); // Muestra la ventana para el test
    }

    @Test
    public void testConsultaClienteJuan() {
        /* * Basado en la Tabla 1 del Anexo:
         * Juan (11111111A) tiene 3 seguros y no es minusválido.
         */
        
        // 1. Introducimos el DNI en el campo que llamaste "txtDNICliente"
        window.textBox("txtDNICliente").enterText("11111111A");
        
        // 2. Pulsamos el botón "btnBuscar"
        window.button("btnBuscar").click();
        
        // 3. Verificamos los resultados en la interfaz
        window.textBox("txtNombreCliente").requireText("Juan");
        
        /*
         * Nota sobre el Total: Debes calcular el esperado según 
         * las reglas del Paso 1 para los seguros de Juan en la BD.
         */
        // window.textBox("txtTotalCliente").requireText("VALOR_CALCULADO");
    }

    @Test
    public void testConsultaClienteInexistente() {
        window.textBox("txtDNICliente").enterText("00000000Z");
        window.button("btnBuscar").click();
        
        // Según tu código, si no existe pone "Error en BBDD"
        window.textBox("txtNombreCliente").requireText("Error en BBDD");
        window.textBox("txtTotalCliente").requireEmpty();
    }

    @After
    public void tearDown() {
        // Limpieza de la ventana después de cada test
        window.cleanUp();
    }
}