import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Clase que representa un cliente de la empresa de seguros
 * Un cliente se identifica por su dni
 */
public class Cliente {

    private String dni;

    private String nombre;  
    
    private boolean minusvalia;

    private List<Seguro> seguros = new LinkedList<Seguro>();
    
    public Cliente(String dni, String nombre, boolean minusvalia) {
        this.dni = dni;
        this.nombre = nombre;
        this.minusvalia = minusvalia;
    }
    
	/**
     * Retorna los seguros del cliente 
     */
    public List<Seguro> getSeguros() {
        return seguros;
    }
    
    /**
     * Asigna la lista de seguros
     */
    public void setSeguros(List<Seguro> seguros) {
        this.seguros = seguros;
    }

    /**
     * Retorna el nombre del cliente.   
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre del cliente
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Retorna el dni del cliente.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Asigna el dni del cliente
     * @param dni
     */
    public void setDni(String dni) {
        this.dni = dni;
    }
    
    /**
     * Indica si el cliente es minusvalido
     */
    public boolean getMinusvalia() {
    	return minusvalia;
    }

    /**
     * Asigna la minusvalia del cliente
     * @param minusvalia
     */
     public void setMinusvalia(boolean minusvalia) {
        this.minusvalia = minusvalia;
    }
    
    /**
     * Calcula el total a pagar por el cliente por 
     * todos los seguros a su nombre
     */
     public double totalSeguros() {
         double total = 0;
         for (Seguro s : seguros) {
             total += s.precio();
         }
         // Aplicar descuento por minusvalía si procede
         if (minusvalia) {
             total = total * 0.75;
         }
         return total;
     }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(dni, cliente.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

}
