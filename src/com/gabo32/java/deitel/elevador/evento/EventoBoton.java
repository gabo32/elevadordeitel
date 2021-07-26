/*
 * Indica que un objeto boton ha cambiado su estado
 */
package com.gabo32.java.deitel.elevador.evento;

import com.gabo32.java.deitel.elevador.modelo.Ubicacion;

/**
 *
 * @author juangb
 */
public class EventoBoton extends EventoSimulacionElevador{

    //Constructor
    public EventoBoton(Object origen, Ubicacion ubicacion) {
        super(origen, ubicacion);
    }

    
}
