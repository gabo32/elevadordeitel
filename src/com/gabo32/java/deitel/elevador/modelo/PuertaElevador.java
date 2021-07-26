/*
 * Abre y cierra la puerta del piso cuando el elevador llega y sale
 */
package com.gabo32.java.deitel.elevador.modelo;

import com.gabo32.java.deitel.elevador.evento.EscuchaMoverElevador;
import com.gabo32.java.deitel.elevador.evento.EventoMoverElevador;

/**
 *
 * @author juangb
 */
public class PuertaElevador extends Puerta implements EscuchaMoverElevador{
    
    public synchronized void abrirPuerta(Ubicacion ubicacion){
        ubicacion.getPuerta().abrirPuerta(ubicacion);
        
        super.abrirPuerta(ubicacion);
    }

    @Override
    public synchronized void cerrarPuerta(Ubicacion ubicacion) {
        ubicacion.getPuerta().cerrarPuerta(ubicacion);
        super.cerrarPuerta(ubicacion); 
    }

    @Override
    public void elevadorPartio(EventoMoverElevador eventoMover) {}

    @Override
    public void elevadorLlego(EventoMoverElevador eventoMover) {
        abrirPuerta(eventoMover.getUbicacion());
    }
    
    
    
    
    
    
}
