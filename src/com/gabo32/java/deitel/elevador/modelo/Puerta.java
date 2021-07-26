/*
 * Envia eventos EventoPuerta a componentes EscuchaPuerta cuando se cierra o abre
 */
package com.gabo32.java.deitel.elevador.modelo;

import com.gabo32.java.deitel.elevador.evento.EscuchaPuerta;
import com.gabo32.java.deitel.elevador.evento.EventoPuerta;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author juangb
 */
public class Puerta {
    
    private boolean abierta = false;
    
    public static final int RETRASO_CIERRE_AUTOMATICO = 3000;
    
    private Set escuchasPuerta;
    
    private Ubicacion ubicacionPuerta;

    public Puerta() {
        escuchasPuerta = new HashSet(1);
    }
    
    public void agregarEscuchaPuerta(EscuchaPuerta escucha){
        synchronized(escuchasPuerta){
            escuchasPuerta.add(escucha);
        }
    }
    
    public void eliminarEscuchaPuerta(EscuchaPuerta escucha){
        synchronized(escuchasPuerta){
            escuchasPuerta.remove(escucha);
        }
    }
    
    public synchronized void abrirPuerta(Ubicacion ubicacion){
        if( !abierta ){
            abierta = true;
            
            Iterator iterador;
            synchronized( escuchasPuerta ){
                iterador = new HashSet(escuchasPuerta).iterator();
            }
            
            while( iterador.hasNext()){
                EscuchaPuerta escuchaPuerta = (EscuchaPuerta) iterador.next();
                
                escuchaPuerta.puertaAbierta(new EventoPuerta(this, ubicacion));
            }
            
            ubicacionPuerta = ubicacion;
            
            Thread subProcesoCierre = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(RETRASO_CIERRE_AUTOMATICO);
                        cerrarPuerta(ubicacionPuerta);
                    }catch(InterruptedException ex){
                        ex.printStackTrace();
                    }
                }
            });
            
            subProcesoCierre.start();
        }
        
        notifyAll();
    }
    
    public synchronized void cerrarPuerta(Ubicacion ubicacion){
        if( abierta ){
            abierta = false;
            
            Iterator iterador;
            synchronized(escuchasPuerta){
                iterador = new HashSet(escuchasPuerta).iterator();
            }
            
            while( iterador.hasNext()){
                EscuchaPuerta escuchaPuerta = (EscuchaPuerta) iterador.next();
                
                escuchaPuerta.puertaCerrada(new EventoPuerta(this, ubicacion));
            }
        }
    }

    public boolean isPuertaAbierta() {
        return abierta;
    }
    
    
    
    
}
