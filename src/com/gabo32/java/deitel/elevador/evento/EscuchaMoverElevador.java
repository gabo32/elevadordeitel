/*
 * Metodos invocador cuando Elevador ha partido o llegado
 */
package com.gabo32.java.deitel.elevador.evento;

/**
 *
 * @author juangb
 */
public interface EscuchaMoverElevador {
    
    public void elevadorPartio(EventoMoverElevador eventoMover);
    
    public void elevadorLlego(EventoMoverElevador eventoMover);
}
