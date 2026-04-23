

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
        // Regla: Si se consulta antes de su inicio, retorna 0
        if (fechaInicio.isAfter(LocalDate.now())) {
            return 0;
        }

        double total = 0;
        
        // 1. Nivel de cobertura (Precio Base)
        switch (cobertura) {
            case TODO_RIESGO: total = 1000; break;
            case TERCEROS_LUNAS: total = 600; break;
            case TERCEROS: total = 400; break;
        }

        // 2. Potencia del coche (Subida porcentual)
        if (potencia > 110) {
            total += total * 0.20; // +20%
        } else if (potencia >= 90) {
            total += total * 0.05; // +5% (90 a 110 incluidos)
        }

        // 3. Oferta (Descuento 20% durante el primer año)
        long mesesAntiguedad = Period.between(fechaInicio, LocalDate.now()).toTotalMonths();
        if (mesesAntiguedad < 12) {
            total -= total * 0.20;
        }

        return total;
    }
	
}
