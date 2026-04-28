package es.unican.is2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;


public class SegurosDAO implements ISegurosDAO {

	@Override
	public Seguro creaSeguro(Seguro s) throws DataAccessException {
		String insertStatement = String.format(
				"insert into Seguros(matricula, fechaInicio, cobertura, potencia, conductorAdicional) values ('%s', '%s', '%s', %d, '%s')",
				s.getMatricula(),
				s.getFechaInicio().toString(),
				s.getCobertura().toString(),
				s.getPotencia(),
				s.getConductorAdicional());
		H2ServerConnectionManager.executeSqlStatement(insertStatement);
		return s;
	}

	@Override
	public Seguro eliminaSeguro(long id) throws DataAccessException {
		Seguro seguro = seguro(id);
		Connection con = H2ServerConnectionManager.getConnection();
		String statementText = "delete from Seguros where id = " + id;
		H2ServerConnectionManager.executeSqlStatement(statementText);
		return seguro;
	}

	@Override
	public Seguro actualizaSeguro(Seguro nuevo) throws DataAccessException {
		Seguro seguro = null;
		Seguro old = seguro(nuevo.getId());
		String statementText;
		Connection con = H2ServerConnectionManager.getConnection();

		statementText = String.format(
				"update Seguros set matricula = '%s', fechaInicio = '%s', cobertura = '%s', potencia = '%d', conductorAdicional = '%s' where id = '%d'", 
				nuevo.getMatricula(), nuevo.getFechaInicio().toString(), nuevo.getCobertura().toString(), nuevo.getPotencia(), nuevo.getConductorAdicional(), nuevo.getId());
			H2ServerConnectionManager.executeSqlStatement(statementText);
			seguro = seguro(nuevo.getId());
		return seguro;
	}

	public Seguro seguro(long id) throws DataAccessException {
    Seguro result = null;
    String sql = "select id, matricula, fechaInicio, cobertura, potencia, conductorAdicional, cliente_FK from Seguros where id = " + id;

    // Aquí solo declaramos 'con' UNA vez dentro del try
    try (Connection con = H2ServerConnectionManager.getConnection();
         Statement stm = con.createStatement()) {
        
        ResultSet rs = stm.executeQuery(sql);
        if (rs.next()) {
            result = SeguroMapper.toSeguro(rs);
        }
    } catch (SQLException e) {
        throw new DataAccessException();
    }
    return result;
}

	@Override
public List<Seguro> seguros() throws DataAccessException {
    List<Seguro> seguros = new LinkedList<Seguro>();
    String statementText = "select * from Seguros";

    // Al declarar 'con' y 'statement' aquí, Java los cierra SÍ O SÍ al terminar el try
    try (Connection con = H2ServerConnectionManager.getConnection();
         Statement statement = con.createStatement()) {
        
        ResultSet results = statement.executeQuery(statementText);
        
        // Procesamos cada fila como vehículo independiente según el Anexo 2
        while (results.next()) {
            seguros.add(SeguroMapper.toSeguro(results));
        }
        
    } catch (SQLException e) {
        // Se lanza la excepción personalizada de la práctica
        throw new DataAccessException();
    }

    return seguros;
}

	@Override
public Seguro seguroPorMatricula(String matricula) throws DataAccessException {
    Seguro result = null;
    String statementText = "select * from Seguros where matricula = '" + matricula + "'";
    
    // Al incluir Connection y Statement en el try, garantizamos su cierre automático
    try (Connection con = H2ServerConnectionManager.getConnection();
         Statement statement = con.createStatement()) {
        
        ResultSet results = statement.executeQuery(statementText);
        
        if (results.next()) {
            result = SeguroMapper.toSeguro(results);
        }
        
        // El statement.close() manual ya no es necesario aquí
        
    } catch (SQLException e) {
        // Capturamos el error de la base de datos H2 y lanzamos la excepción de la práctica
        throw new DataAccessException();
    }
    
    return result;
}
	
	

	

}
