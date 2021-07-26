/*
 * Metodos invocador cuando se apaga o enciende la luz
 */
package com.gabo32.java.deitel.elevador.evento;

/**
 *
 * @author juangb
 */
public interface EscuchaLuz {
    
    public void luzSeEncendio(EventoLuz eventoLuz);
    
    public void luzSeApago(EventoLuz eventoLuz);
}
