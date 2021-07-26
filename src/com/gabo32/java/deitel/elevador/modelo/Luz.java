/*
 * Luz apaga o enciende una luz
 */
package com.gabo32.java.deitel.elevador.modelo;

import com.gabo32.java.deitel.elevador.evento.EscuchaLuz;
import com.gabo32.java.deitel.elevador.evento.EscuchaMoverElevador;
import com.gabo32.java.deitel.elevador.evento.EventoLuz;
import com.gabo32.java.deitel.elevador.evento.EventoMoverElevador;

/**
 *
 * @author juangb
 */
public class Luz implements EscuchaMoverElevador{
    
    private boolean luzEncendida;
    
    public static final int RETRASO_APAGADO_AUTOMATICO = 3000;
    
    private EscuchaLuz escuchaLuz;
    
    private Ubicacion ubicacionLuz;

    public void encenderLuz(Ubicacion ubicacion){
        if( !luzEncendida ){
            luzEncendida = true;
            
            escuchaLuz.luzSeEncendio(new EventoLuz(this, ubicacion));
            
            ubicacionLuz = ubicacion;
            
            Thread subProceso = new Thread( new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(RETRASO_APAGADO_AUTOMATICO);
                        apagarLuz(ubicacionLuz);
                    }catch(InterruptedException ex){
                        ex.printStackTrace();
                    }
                }
            });
            
            subProceso.start();
        }
    }
    
    public void apagarLuz(Ubicacion ubicacion){
        if( luzEncendida ){
            luzEncendida = false;
            
            escuchaLuz.luzSeApago(new EventoLuz(this, ubicacion));
        }
    }

    @Override
    public void elevadorPartio(EventoMoverElevador eventoMover) {
        apagarLuz(eventoMover.getUbicacion());
    }

    @Override
    public void elevadorLlego(EventoMoverElevador eventoMover) {
        encenderLuz(eventoMover.getUbicacion());
    }
    
    public boolean isLuzEncendida() {
        return luzEncendida;
    }

    public void setLuzEncendida(boolean luzEncendida) {
        this.luzEncendida = luzEncendida;
    }

    public EscuchaLuz getEscuchaLuz() {
        return escuchaLuz;
    }

    public void setEscuchaLuz(EscuchaLuz escuchaLuz) {
        this.escuchaLuz = escuchaLuz;
    }

    public Ubicacion getUbicacionLuz() {
        return ubicacionLuz;
    }

    public void setUbicacionLuz(Ubicacion ubicacionLuz) {
        this.ubicacionLuz = ubicacionLuz;
    }
    
    
}
