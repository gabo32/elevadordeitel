/*
 * Indica si un objeto puerta ha cambiado de estado
 */
package com.gabo32.java.deitel.elevador.evento;

import com.gabo32.java.deitel.elevador.modelo.Ubicacion;

/**
 *
 * @author juangb
 */
public class EventoPuerta extends EventoSimulacionElevador{

    public EventoPuerta(Object orige, Ubicacion ubicacion) {
        super(orige, ubicacion);
    }
    
    
}
