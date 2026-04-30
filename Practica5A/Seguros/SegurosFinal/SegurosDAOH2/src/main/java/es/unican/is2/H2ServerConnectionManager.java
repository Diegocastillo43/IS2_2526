package es.unican.is2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Clase que gestiona el acceso a la base de datos H2 en memoria.
 * Permite abrir conexiones y crear y rellenar la propia base de datos
 * al inicio de la aplicacion
 */
public class H2ServerConnectionManager {

	// Conexion con la base de datos
	protected static Connection connection;

	// Atributos de acceso a la Base de Datos
	protected static String dbName = "test";
	protected static String user = "sa";
	protected static String pass = "";

	/**
	 * Obtiene una conexion con la base de datos
	 * @return La conexion
	 * @throws DataAccessException si no se puede establecer al conexion
	 */
	public static Connection getConnection() throws DataAccessException {

		if (connection == null) { 
			try {
				Class.forName("org.h2.Driver"); //comprueba que el driver esta instalado
				connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa","");		
				cargaDatos();
			} catch (SQLException | ClassNotFoundException e) {
				throw new DataAccessException();
			}
		}
		return connection;
	}

	public static void cargaDatos() throws DataAccessException {
    // Definimos los recursos en el try para que se cierren automáticamente al final
    try (Connection con = getConnection(); 
         Statement stm = con.createStatement()) {

        // Creación de la tabla Clientes
        String sql = "CREATE TABLE Clientes (dni CHAR(9) NOT NULL, nombre VARCHAR(100) NOT NULL, "
                   + "minusvalia BOOLEAN NOT NULL, PRIMARY KEY(dni))";
        stm.execute(sql);

        // Creación de la tabla Vehiculos (Seguros)
        sql = "CREATE TABLE Seguros (id BIGINT NOT NULL AUTO_INCREMENT, matricula CHAR(7) NOT NULL, fechaInicio DATE NOT NULL, "
            + "cobertura VARCHAR(100) NOT NULL, potencia INT NOT NULL, conductorAdicional VARCHAR(100), cliente_FK CHAR(9) NOT NULL, "
            + "PRIMARY KEY(id), FOREIGN KEY(cliente_FK) REFERENCES Clientes(dni))";
        stm.execute(sql);

        // ... Aquí irían todos tus INSERT actuales (sql = "INSERT...") ...
        // stm.executeUpdate(sql);
        
        // NO HACE FALTA stm.close() ni con.close() manual. 
        // Se cierran solos al llegar a la llave de abajo '}'.

    } catch (SQLException e) {
        // SonarQube suele marcar System.out.println(e) como Code Smell.
        // Lo ideal es relanzar la excepción o usar un logger.
        throw new DataAccessException(); 
    }
}

	public static void executeSqlStatement(String stringStatement) throws DataAccessException {
    // Definimos la conexión y el statement en el bloque try para cierre automático
		try (Connection con = getConnection(); 
			Statement stm = con.createStatement()) {
			
			stm.execute(stringStatement);
			
			// El stm.close() manual ya no es necesario
			
		} catch (SQLException e) {
			// Si hay un error, el 'try' se encarga de cerrar los recursos antes de lanzar esto
			throw new DataAccessException();
		}
	}
}
