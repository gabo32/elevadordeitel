/*
 * Indica que el Timbre ha sonado
 */
package com.gabo32.java.deitel.elevador.evento;

import com.gabo32.java.deitel.elevador.modelo.Ubicacion;

/**
 *
 * @author juangb
 */
public class EventoTimbre extends  EventoSimulacionElevador{

    public EventoTimbre(Object origen, Ubicacion ubicacion) {
        super(origen, ubicacion);
    }
    
    
}
