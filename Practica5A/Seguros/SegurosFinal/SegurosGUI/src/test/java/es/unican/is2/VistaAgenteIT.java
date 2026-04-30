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
    public void setUp() throws DataAccessException{
        daoClientes = new ClientesDAO();
        daoSeguros = new SegurosDAO();
        
        try {
            String createTable = "CREATE TABLE IF NOT EXISTS Clientes " +
                                 "(dni VARCHAR(10) PRIMARY KEY, nombre VARCHAR(50), minusvalia BOOLEAN)";
            H2ServerConnectionManager.executeSqlStatement(createTable);
        } catch (Exception e) {
            // Si ya existe o hay otro error, lo ignoramos o gestionamos
        }
        
        Cliente juan = new Cliente("Juan", "11111111A", false);
        daoClientes.creaCliente(juan);
        
        GestionSeguros negocio = new GestionSeguros(daoClientes, daoSeguros);
        clientes = negocio;
        seguros = negocio;
        info = negocio;

        VistaAgente frame = GuiActionRunner.execute(() -> 
            new VistaAgente(clientes, seguros, info)
        );
        
        window = new FrameFixture(frame);
        window.show();
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
        try { Thread.sleep(500); } catch (Exception e) {} // Pausa de seguridad para Swing
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
        if (window != null) { // Solo limpia si se llegó a crear
            window.cleanUp();
        }
    }
}