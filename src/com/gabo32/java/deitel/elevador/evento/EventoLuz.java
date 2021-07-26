/*
 * Indica en que Piso la Luz ha cambiado de estado
 */
package com.gabo32.java.deitel.elevador.evento;

import com.gabo32.java.deitel.elevador.modelo.Ubicacion;

/**
 *
 * @author juangb
 */
public class EventoLuz extends EventoSimulacionElevador{

    public EventoLuz(Object origen, Ubicacion ubicacion) {
        super(origen, ubicacion);
    }
    
    
}
