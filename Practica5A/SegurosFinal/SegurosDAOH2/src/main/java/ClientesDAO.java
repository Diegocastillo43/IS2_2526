


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;


public class ClientesDAO implements IClientesDAO {

	@Override
	public Cliente creaCliente(Cliente c) throws DataAccessException {
		String insertStatement = String.format(
				"insert into Clientes(dni, nombre, minusvalia) values ('%s', '%s', '%b')",
				c.getDni(),
				c.getNombre(),
				c.getMinusvalia());
		H2ServerConnectionManager.executeSqlStatement(insertStatement);
		return c;
	}

	@Override
public Cliente cliente(String dni) throws DataAccessException {
    Cliente result = null;
    // La conexión se queda fuera si se gestiona externamente, 
    // pero el Statement DEBE ir en el try para cerrarse siempre.
    Connection con = H2ServerConnectionManager.getConnection();
    
    String statementText = "select * from Clientes where dni = '" + dni + "'";
    
    try (Statement statement = con.createStatement()) { // <--- Se abre aquí
        ResultSet results = statement.executeQuery(statementText);
        if (results.next()) {
            result = procesaCliente(con, results);
        }
        // YA NO HACE FALTA el statement.close() manual, se hace solo al cerrar la llave.
    } catch (SQLException e) {
        e.printStackTrace();
        throw new DataAccessException();
    }
    return result;
}

	@Override
	public Cliente actualizaCliente(Cliente nuevo) throws DataAccessException {
		Cliente cliente = null;
		Cliente old = cliente(nuevo.getDni());
		String statementText;

		Connection con = H2ServerConnectionManager.getConnection();

		statementText = String.format(
				"update Clientes set nombre = '%s', minusvalia = '%b' where dni = '%s'", 
				nuevo.getNombre(), nuevo.getMinusvalia(), nuevo.getDni());
			H2ServerConnectionManager.executeSqlStatement(statementText);
			for(Seguro s: old.getSeguros()) {
				if (!nuevo.getSeguros().contains(s)) {
					statementText = String.format(
						"update Seguros set cliente_FK = null where id = '%d'",
						s.getId());
					H2ServerConnectionManager.executeSqlStatement(statementText);
				}
			}
			cliente = cliente(nuevo.getDni());
		
		return cliente;
	}

	@Override
	public Cliente eliminaCliente(String dni) throws DataAccessException {
		Cliente cliente = cliente(dni);
		Connection con = H2ServerConnectionManager.getConnection();
		String statementText = "delete from Clientes where dni = " + dni;
		H2ServerConnectionManager.executeSqlStatement(statementText);
		return cliente;
	}

	@Override
public List<Cliente> clientes() throws DataAccessException {
    List<Cliente> clientes = new LinkedList<Cliente>();
    Connection con = H2ServerConnectionManager.getConnection();
    
    String statementText = "select dni, nombre, minusvalia from Clientes";

    // El Statement se abre aquí y Java lo cerrará SÍ O SÍ al llegar a la llave final }
    try (Statement statement = con.createStatement()) { 
        ResultSet results = statement.executeQuery(statementText);
        
        while (results.next()) {
            clientes.add(procesaCliente(con, results));
        }
        // Ya no necesitas statement.close() aquí
    } catch (SQLException e) {
        throw new DataAccessException();
    }

    return clientes;
}

	private Cliente procesaCliente(Connection con, ResultSet results) throws SQLException, DataAccessException {
    Cliente result = ClienteMapper.toCliente(results);
    // Cargamos los seguros del cliente
    String statementText = String.format("select * from Seguros where cliente_FK = '%s'", result.getDni());
    
    // Usamos un nombre distinto para el nuevo ResultSet para evitar colisiones
    try (Statement statement = con.createStatement();
         ResultSet rsSeguros = statement.executeQuery(statementText)) {
        
        while (rsSeguros.next()) {
            result.getSeguros().add(SeguroMapper.toSeguro(rsSeguros));
        }
        // Tanto el statement como rsSeguros se cerrarán automáticamente aquí
    }
    
    return result;
}
	
}
