/*
 * Viaja entre los objetos piso en el objeto ConductoElevador
 */
package com.gabo32.java.deitel.elevador.modelo;

import com.gabo32.java.deitel.elevador.ConstantesElevador;
import com.gabo32.java.deitel.elevador.evento.EscuchaBoton;
import com.gabo32.java.deitel.elevador.evento.EscuchaMoverElevador;
import com.gabo32.java.deitel.elevador.evento.EscuchaPuerta;
import com.gabo32.java.deitel.elevador.evento.EscuchaTimbre;
import com.gabo32.java.deitel.elevador.evento.EventoBoton;
import com.gabo32.java.deitel.elevador.evento.EventoMoverElevador;
import com.gabo32.java.deitel.elevador.evento.EventoPuerta;
import com.gabo32.java.deitel.elevador.evento.EventoTimbre;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author juangb
 */
public class Elevador extends Ubicacion implements Runnable, EscuchaTimbre, ConstantesElevador{
    
    private boolean elevadorFuncionando = false;
    
    private boolean movimieto = false;
    
    private Ubicacion ubicacionPisoActual;
    
    private Ubicacion ubicacionPisoDestino;
    
    private boolean llamado;
    
    private Set escuchasMoverElevador;
    private EscuchaTimbre escuchaTimbre;
    private EscuchaBoton escuchaBotonElevador;
    private EscuchaPuerta escuchaPuertaElevador;
    
    private PuertaElevador puertaElevador;
    
    private Boton botonElevador;
    
    private Timbre timbre;
    
    public static final int UN_SEGUNDO = 1000;
    
    private static final int TIEMPO_RECORRIDO = 5 * UN_SEGUNDO;
    
    private Thread subProceso;

    public Elevador(Piso primerPiso, Piso segundoPiso) {
        setNombreUbicacion(NOMBRE_ELEVADOR);
        
        puertaElevador = new PuertaElevador();
        botonElevador = new Boton();
        timbre = new Timbre();
        
        timbre.setEscuchaTimbre(this);
        
        escuchasMoverElevador = new HashSet(1);
        
        ubicacionPisoActual = primerPiso;
        ubicacionPisoDestino = segundoPiso;
        
        agregarEscuchaMoverElevador(botonElevador);
        agregarEscuchaMoverElevador(puertaElevador);
        agregarEscuchaMoverElevador(timbre);
        
        botonElevador.setEscuchaBoton(new EscuchaBoton() {
            @Override
            public void botonOprimido(EventoBoton eventoBoton) {
                escuchaBotonElevador.botonOprimido(eventoBoton);
                
                setMovimiento(true);
            }

            @Override
            public void botonReestablecido(EventoBoton eventoBoton) {
                escuchaBotonElevador.botonReestablecido(eventoBoton);
            }
        });
        
        puertaElevador.agregarEscuchaPuerta(new EscuchaPuerta() {
            @Override
            public void puertaAbierta(EventoPuerta eventoPuerta) {
                escuchaPuertaElevador.puertaAbierta(new EventoPuerta(eventoPuerta.getOrigen(), Elevador.this));
            }

            @Override
            public void puertaCerrada(EventoPuerta eventoPuerta) {
                escuchaPuertaElevador.puertaCerrada(new EventoPuerta(eventoPuerta.getOrigen(), Elevador.this));
            }
        });
    }
    
    private void cambiarPisos(){
        Ubicacion ubicacion = ubicacionPisoActual;
        ubicacionPisoActual = ubicacionPisoDestino;
        ubicacionPisoDestino = ubicacion;
    }
    
    public void start(){
        if( subProceso == null){
            subProceso = new Thread(this);
        }
        
        elevadorFuncionando = true;
        subProceso.start();
    }
    
    public void detenerElevador(){
        elevadorFuncionando = false;
    }
    
    public void run(){
        while( estaFuncionandoElevador() ){
            while( !seEstaMoviendo()){
                interrumpirSubProceso(10);
            }
            
            interrumpirSubProceso(UN_SEGUNDO);
            
            getPuerta().cerrarPuerta(ubicacionPisoActual);
            
            interrumpirSubProceso(UN_SEGUNDO);
            
            enviarEventoPartida(ubicacionPisoActual);
            
            interrumpirSubProceso(TIEMPO_RECORRIDO);
            
            setMovimiento(false);
            
            cambiarPisos();
            
            enviarEventoLlegada(ubicacionPisoActual);
        }
    }
    
    private void interrumpirSubProceso(int milisegundos){
        try{
            Thread.sleep(milisegundos);
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }
    }
    
    public Boton getBoton(){
        return botonElevador;
    }
    
    public Puerta getPuerta(){
        return puertaElevador;
    }
    
    private void setMovimiento(boolean elevadorMoviendose){
        movimieto = elevadorMoviendose;
    }
    
    private boolean seEstaMoviendo(){
        return movimieto;
    }
    
    private boolean estaFuncionandoElevador(){
        return elevadorFuncionando;
    }
    
    public void agregarEscuchaMoverElevador(EscuchaMoverElevador escucha){
        escuchasMoverElevador.add(escucha);
    }
    
    public void setEsuchaTimbre(EscuchaTimbre escucha){
        this.escuchaTimbre = escucha;
    }
    
    public void setEscuchaBoton(EscuchaBoton escucha){
        this.escuchaBotonElevador = escucha;
    }
    
    public void setEscuchaPuerta(EscuchaPuerta escucha){
        this.escuchaPuertaElevador = escucha;
    }
    
    private void enviarEventoLlegada(Ubicacion ubicacion){
        Iterator iterador = escuchasMoverElevador.iterator();
        
        while( iterador.hasNext()){
            EscuchaMoverElevador escucha = (EscuchaMoverElevador) iterador.next();
            
            escucha.elevadorLlego(new EventoMoverElevador(this, ubicacion));
        }
        
        if( llamado ){
            setMovimiento(true);
        }
        
        llamado = false;
    }
    
    private void enviarEventoPartida(Ubicacion ubicacion){
        Iterator iterador = escuchasMoverElevador.iterator();
        
        while( iterador.hasNext() ){
            EscuchaMoverElevador escucha = (EscuchaMoverElevador) iterador.next();
            
            escucha.elevadorPartio(new EventoMoverElevador(this, ubicacionPisoActual));
        }
    }
    
    public void solicitarElevador(Ubicacion ubicacion){
        if( !seEstaMoviendo() ){
            if( ubicacion == ubicacionPisoActual ){
                enviarEventoLlegada(ubicacionPisoActual);
            }else{
                setMovimiento(true);
            }
        }else{
            if( ubicacion == ubicacionPisoActual){
                llamado = true;
            }
        }
    }
    
    public void timbreSono(EventoTimbre eventoTimbre){
        if( escuchaTimbre != null){
            escuchaTimbre.timbreSono(eventoTimbre);
        }
    }
    
    public Ubicacion getPisoActual(){
        return ubicacionPisoActual;
    }
    
    
    
    
}
