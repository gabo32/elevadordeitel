/*
 * Metodos invocador al moverse la Persona
 */
package com.gabo32.java.deitel.elevador.evento;

/**
 *
 * @author juangb
 */
public interface EscuchaMoverPersona {
    
    public void personaCreada(EventoMoverPersona eventoMover);
    
    public void personaLlego(EventoMoverPersona eventoMover);
    
    public void personaPartio(EventoMoverPersona eventoMover);
    
    public void personaOprimioBoton(EventoMoverPersona eventoMover);
    
    public void personaEntro(EventoMoverPersona eventoMover);
    
    public void personaSalio(EventoMoverPersona eventoMover);
    
}
