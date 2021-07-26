/*
 * Envia eventos EventoBoton a los objetos EscuchaBoton cuando se utiliza
 */
package com.gabo32.java.deitel.elevador.modelo;

import com.gabo32.java.deitel.elevador.evento.EscuchaBoton;
import com.gabo32.java.deitel.elevador.evento.EscuchaMoverElevador;
import com.gabo32.java.deitel.elevador.evento.EventoBoton;
import com.gabo32.java.deitel.elevador.evento.EventoMoverElevador;

/**
 *
 * @author juangb
 */
public class Boton implements EscuchaMoverElevador{
    
    private EscuchaBoton escuchaBoton = null;
    
    private boolean oprimido = false;

    public void oprimirBoton(Ubicacion ubicacion){
        oprimido = true;
        escuchaBoton.botonOprimido(new EventoBoton(this, ubicacion));
    }
    
    public void reestablecerBoton(Ubicacion ubicacion){
        oprimido = false;
        escuchaBoton.botonReestablecido(new EventoBoton(this, ubicacion));
    }
    
    @Override
    public void elevadorPartio(EventoMoverElevador eventoMover) {
    }

    @Override
    public void elevadorLlego(EventoMoverElevador eventoMover) {
        reestablecerBoton(eventoMover.getUbicacion());
    }
    
    public EscuchaBoton getEscuchaBoton() {
        return escuchaBoton;
    }

    public void setEscuchaBoton(EscuchaBoton escuchaBoton) {
        this.escuchaBoton = escuchaBoton;
    }   

    public boolean isOprimido() {
        return oprimido;
    }

    
    
    
}
