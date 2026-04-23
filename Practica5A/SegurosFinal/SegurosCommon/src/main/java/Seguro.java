

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * Clase que representa un seguro de coche.
 */
public class Seguro {
	
	private long id;

    private String matricula;

	private int potencia;

    private Cobertura cobertura;
    
    private LocalDate fechaInicio;

	private String conductorAdicional;
	
	public Seguro(long id, String matricula, int potencia, Cobertura cobertura, LocalDate fechaInicio) {
        this.id = id;
        this.matricula = matricula;
        this.potencia = potencia;
        this.cobertura = cobertura;
        this.fechaInicio = fechaInicio;
    }
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seguro seguro = (Seguro) o;
        return id == seguro.id && Objects.equals(matricula, seguro.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, matricula);
    }

	/**
	 * Retorna el identificador del seguro
	 */
	public long getId() {
		return id;
	}

	/**
	 *  Asigna el valor del identificador del seguro
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Retorna la matricula del coche 
	 * asociado al seguro
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 *  Asigna el valor de la matrícula del seguro
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	/**
	 * Retorna la fecha de contratacion del seguro
	 */
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Asigna la fecha de inicio del seguro
	 * @param fechaInicio La fecha de inicio del seguro
	 */
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * Retorna el tipo de cobertura del seguro
	 */
	public Cobertura getCobertura() {
		return cobertura;
	}

	/**
	 * Asigna el tipo de cobertura del seguro
	 * @param cobertura El tipo de cobertura del seguro
	 */	
	public void setCobertura(Cobertura cobertura) {
		this.cobertura = cobertura;		
}

	/**
     * Retorna la potencia del coche asociado 
     * al seguro. 
     */
    public int getPotencia() {
        return potencia;
    }

	/**
	 *  Asigna el valor del identificador del seguro
	 */
	public void setPotencia(int potencia) {
		this.potencia = potencia;
	}

	/**
	 * Retorna el conductor adicional del seguro, si lo hay
	 * @return El conductor adicional si lo hay
	 * 		null en caso contrario
	 */
	public String getConductorAdicional() {
		return conductorAdicional;
	}

	/**
	 * Asigna el conductor adicional del seguro
	 * @param conductorAdicional
	 */
	public void setConductorAdicional(String conductorAdicional) {
		this.conductorAdicional = conductorAdicional;
	}
    
    /**
     * Retorna el precio del seguro. 
	 * El precio se calcula a partir de la cobertura, la potencia del coche y el tiempo que lleva contratado el seguro
	 * @return El precio del seguro
	 *         0 si el seguro todavía no está en vigor (no se ha alcanzado su fecha de inicio)
     */
	public double precio() {
        // 1. Comprobar si está en vigor
        if (fechaInicio.isAfter(LocalDate.now())) {
            return 0;
        }

        double precioBase = 0;
        
        // 2. Precio según Cobertura
        switch (cobertura) {
            case TERCEROS: precioBase = 400; break;
            case TERCEROS_LUNAS: precioBase = 450; break;
            case TODO_RIESGO: precioBase = 600; break;
        }

        // 3. Recargo por potencia
        if (potencia > 110) {
            precioBase += 50;
        } else if (potencia > 90) {
            precioBase += 20;
        }

        // 4. Descuento por antigüedad (Si lleva más de 20 años)
        int antiguedad = Period.between(fechaInicio, LocalDate.now()).getYears();
        if (antiguedad > 20) {
            precioBase = precioBase * 0.8;
        }

        return precioBase;
    }
	
}
