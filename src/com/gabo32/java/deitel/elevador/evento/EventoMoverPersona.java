/*
 * Indica si una Persona se ha movido
 */
package com.gabo32.java.deitel.elevador.evento;

import com.gabo32.java.deitel.elevador.modelo.Ubicacion;

/**
 *
 * @author juangb
 */
public class EventoMoverPersona extends EventoSimulacionElevador{
    
    private int ID;

    public EventoMoverPersona(Object origen, Ubicacion ubicacion, int ID) {
        super(origen, ubicacion);
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
