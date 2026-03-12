

public class GestionSeguros implements IGestionSeguros, IGestionClientes, IInfoSeguros {

    private IClientesDAO daoClientes;
    private ISegurosDAO daoSeguros;

    public GestionSeguros(IClientesDAO daoClientes, ISegurosDAO daoSeguros) {
        this.daoClientes = daoClientes;
        this.daoSeguros = daoSeguros;
    }

    /**
     * Añade un nuevo cliente al sistema
     */
    @Override
    public Cliente nuevoCliente(Cliente c) throws DataAccessException {
        // Primero comprobamos si ya existe para cumplir con el contrato de la interfaz
        if (daoClientes.cliente(c.getDni()) != null) {
            return null;
        }
        return daoClientes.creaCliente(c);
    }

    /**
     * Elimina un cliente si no tiene seguros asociados
     */
    @Override
    public Cliente bajaCliente(String dni) throws OperacionNoValida, DataAccessException {
        Cliente c = daoClientes.cliente(dni);
        if (c == null) {
            return null;
        }
        // Regla de negocio: No se puede eliminar si tiene seguros
        if (c.getSeguros() != null && !c.getSeguros().isEmpty()) {
            throw new OperacionNoValida("El cliente tiene seguros asociados y no puede ser eliminado.");
        }
        return daoClientes.eliminaCliente(dni);
    }

    /**
     * Crea un nuevo seguro para un cliente
     */
    @Override
    public Seguro nuevoSeguro(Seguro s, String dni) throws OperacionNoValida, DataAccessException {
        Cliente c = daoClientes.cliente(dni);
        if (c == null) {
            return null; // El cliente no existe
        }
        
        // Comprobar si el seguro ya existe por matrícula
        if (daoSeguros.seguroPorMatricula(s.getMatricula()) != null) {
            throw new OperacionNoValida("Ya existe un seguro con esa matrícula.");
        }

        // En H2, según el ClientesDAO, los seguros se asocian por la FK cliente_FK
        // Aquí llamaríamos al DAO para persistir el seguro
        return daoSeguros.creaSeguro(s); 
    }

    /**
     * Da de baja un seguro
     */
    @Override
    public Seguro bajaSeguro(String matricula, String dni) throws OperacionNoValida, DataAccessException {
        Seguro s = daoSeguros.seguroPorMatricula(matricula);
        if (s == null) return null;

        // Comprobamos que el seguro pertenece al cliente indicado (puedes necesitar lógica extra según tu BD)
        // Por ahora, si existe, lo borramos usando su ID
        return daoSeguros.eliminaSeguro(s.getId());
    }
    
    /**
     * Consulta los datos de un cliente y sus seguros
     * (Equivale al método cliente(dni) de IInfoSeguros)
     */
    public Cliente consultaCliente(String dni) throws DataAccessException {
        return daoClientes.cliente(dni);
    }

    /**
     * Consulta los datos de un seguro
     */
    @Override
    public Seguro seguro(String matricula) throws DataAccessException {
        return daoSeguros.seguroPorMatricula(matricula);
    }
    
    @Override
    public Seguro anhadeConductorAdicional(String matricula, String nombreConductor) throws DataAccessException {
        Seguro s = daoSeguros.seguroPorMatricula(matricula);
        if (s == null) return null;
        
        s.setConductorAdicional(nombreConductor);
        return daoSeguros.actualizaSeguro(s);
    }

    // Métodos obligatorios por las interfaces que implementas:

    @Override
    public Cliente cliente(String dni) throws DataAccessException {
        return daoClientes.cliente(dni);
    }

    // Nota: El método consultaSeguro de tu código pedía retornar un Cliente, 
    // pero por lógica debería retornar un Seguro. Lo corrijo según la interfaz:
    public Seguro consultaSeguro(String matricula) throws DataAccessException {
        return daoSeguros.seguroPorMatricula(matricula);
    }
}