
public class GestionSeguros {
	
	public GestionSeguros(IClientesDAO daoClientes, ISegurosDAO daoSeguros) {
		
	}

	/**
     * Añade un nuevo cliente al sistema
     */
    public void nuevoCliente(Cliente c) {
        // TODO: 
    }

    /**
     * Elimina un cliente si no tiene seguros asociados
     */
    public boolean bajaCliente(String dni) {
        // TODO:
        return false;
    }

    /**
     * Crea un nuevo seguro para un cliente
     */
    public void nuevoSeguro(Seguro s, String dni) {
        // TODO
    }

    /**
     * Da de baja un seguro
     */
    public boolean bajaSeguro(String matricula, String dni) {
        // TODO:
        return false;
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
    
    public void anhadirConductorAdicional(String matricula, String nombreConductor) {
    	// TODO:
    }
    
}
