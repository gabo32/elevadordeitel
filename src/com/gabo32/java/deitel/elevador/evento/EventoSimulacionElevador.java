/*
 * Paquete de eventos basicos en la simulacion del Elevador
 */
package com.gabo32.java.deitel.elevador.evento;

import com.gabo32.java.deitel.elevador.modelo.Ubicacion;

/**
 *
 * @author juangb
 */
public class EventoSimulacionElevador {
    
    //Ubicacion en la que se genera EventoSimulacionElevador
    private Ubicacion ubicacion;
    
    //objeto de origen que genera el EventoSimulacionElevador
    private Object origen;

    /**
     * Constructor que establece la ubicacion
     * @param ubicacion
     * @param origen 
     */
    public EventoSimulacionElevador( Object origen,Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
        this.origen = origen;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Object getOrigen() {
        return origen;
    }

    public void setOrigen(Object origen) {
        this.origen = origen;
    }
    
    
}
