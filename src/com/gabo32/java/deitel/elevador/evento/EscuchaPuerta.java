/*
 * Metodos invocador cuando se abre o cirra la puerta
 */
package com.gabo32.java.deitel.elevador.evento;

/**
 *
 * @author juangb
 */
public interface EscuchaPuerta {
    
    public void puertaAbierta(EventoPuerta eventoPuerta);
    
    public void puertaCerrada(EventoPuerta eventoPuerta);
}
