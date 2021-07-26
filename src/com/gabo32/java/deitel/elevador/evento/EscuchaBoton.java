/*
 * Metodo invocados cuando se ha oprimido o reestablecido el boton
 */
package com.gabo32.java.deitel.elevador.evento;

/**
 *
 * @author juangb
 */
public interface EscuchaBoton {
    
    //se invoca cuando se ha oprimido el boton
    public void botonOprimido(EventoBoton eventoBoton);
    
    //se invoca cuando se ha reestablecido el boton
    public void botonReestablecido(EventoBoton eventoBoton);
}
