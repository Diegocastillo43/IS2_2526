
public class GestionSeguros implements IGestionSeguros, IGestionClientes, IInfoSeguros{
	
	public GestionSeguros(IClientesDAO daoClientes, ISegurosDAO daoSeguros) {
		
	}

	/**
     * Añade un nuevo cliente al sistema
     */
    public Cliente nuevoCliente(Cliente c) {
        // TODO: 
    	return null;
    }

    /**
     * Elimina un cliente si no tiene seguros asociados
     */
    public Cliente bajaCliente(String dni) {
        // TODO:
        return null;
    }

    /**
     * Crea un nuevo seguro para un cliente
     */
    public Seguro nuevoSeguro(Seguro s, String dni) {
        // TODO
    	return null;
    }

    /**
     * Da de baja un seguro
     */
    public Seguro bajaSeguro(String matricula, String dni) {
        // TODO:
        return null;
    }
    
    /**
     * Consulta los datos de un cliente y sus seguros
     */
    public Cliente consultaCliente(String dni) {
        // TODO:
        return null; 
    }

    /**
     * Consulta los datos de un seguro
     */
    public Cliente consultaSeguro(String matricula) {
        // TODO:
        return null; 
    }
    
    public Seguro anhadeConductorAdicional(String matricula, String nombreConductor) {
    	// TODO:
    	return null;
    }

	@Override
	public Cliente cliente(String dni) throws DataAccessException {
		// TODO 
		return null;
	}

	@Override
	public Seguro seguro(String matricula) throws DataAccessException {
		// TODO
		return null;
	}
    
}
