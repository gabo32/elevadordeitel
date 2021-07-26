/*
 * Representa el objeto timbre en la simulacion
 */
package com.gabo32.java.deitel.elevador.modelo;

import com.gabo32.java.deitel.elevador.evento.EscuchaMoverElevador;
import com.gabo32.java.deitel.elevador.evento.EscuchaTimbre;
import com.gabo32.java.deitel.elevador.evento.EventoMoverElevador;
import com.gabo32.java.deitel.elevador.evento.EventoTimbre;

/**
 *
 * @author juangb
 */
public class Timbre implements EscuchaMoverElevador{
    
    private EscuchaTimbre escuchaTimbre;
    
    private void sonarTimbre(Ubicacion ubicacion){
        if( escuchaTimbre != null){
            escuchaTimbre.timbreSono(new EventoTimbre(this, ubicacion));
        }
    }

    public void setEscuchaTimbre(EscuchaTimbre escuchaTimbre) {
        this.escuchaTimbre = escuchaTimbre;
    }

    @Override
    public void elevadorPartio(EventoMoverElevador eventoMover) {}

    @Override
    public void elevadorLlego(EventoMoverElevador eventoMover) {
        sonarTimbre(eventoMover.getUbicacion());
    }
    
    
    
    
}
