/*
 * Persona que viaja en el elevador
 */
package com.gabo32.java.deitel.elevador.modelo;

import com.gabo32.java.deitel.elevador.evento.EscuchaMoverPersona;
import com.gabo32.java.deitel.elevador.evento.EventoMoverPersona;

/**
 *
 * @author juangb
 */
public class Persona extends Thread{
    
    private int ID = -1;
    
    private boolean movimiento;
    
    private Ubicacion ubicacion;
    
    private EscuchaMoverPersona escuchaMoverPersona;
    
    private static final int TIEMPO_PARA_CAMINAR = 3000;
    
    public static final int PERSONA_CREADA = 1;
    public static final int PERSONA_LLEGO = 2;
    public static final int PERSONA_ENTRANDO_ELEVADOR = 3;
    public static final int PERSONA_OPRIMIENDO_BOTON = 4;
    public static final int PERSONA_SALIENDO_ELEVADOR = 5;
    public static final int PERSONA_SALIO = 6;
    
    public Persona(int id, Ubicacion ubicacionInicial){
        super();
        
        this.ID = id;
        this.ubicacion = ubicacionInicial;
        movimiento = true;
    }
    
    public void run(){
        enviarEventoMoverPersona(PERSONA_CREADA);
        
        interrumpirSubProceso(TIEMPO_PARA_CAMINAR);
        
        setEstaMoviendo(false);
        
        enviarEventoMoverPersona( PERSONA_LLEGO );
        
        Puerta puertaPisoActual = ubicacion.getPuerta();
        
        Elevador elevador = ( (Piso) getUbicacion() ).getConductoElevador().getElevador();
        
        synchronized( puertaPisoActual ){
            if( !puertaPisoActual.isPuertaAbierta() ){
                enviarEventoMoverPersona( PERSONA_OPRIMIENDO_BOTON );
                interrumpirSubProceso(1000);
                
                Boton botonPiso = getUbicacion().getBoton();
                botonPiso.oprimirBoton(getUbicacion());
            }
            
            try{
                while(!puertaPisoActual.isPuertaAbierta()){
                    puertaPisoActual.wait();
                }
            }catch(InterruptedException ex){
                ex.printStackTrace();
            }
            
            interrumpirSubProceso(1000);
            
            synchronized(elevador){
                enviarEventoMoverPersona( PERSONA_ENTRANDO_ELEVADOR );
                
                setUbicacion(elevador);
                
                interrumpirSubProceso( 1000 );
                
                enviarEventoMoverPersona( PERSONA_OPRIMIENDO_BOTON );
                interrumpirSubProceso(1000);
                
                Boton botonElevador = getUbicacion().getBoton();
                
                botonElevador.oprimirBoton(ubicacion);
                
                interrumpirSubProceso(1000);
            }
        }
        
        synchronized( elevador ){
            Puerta puertaElevador = getUbicacion().getPuerta();
            
            synchronized( puertaElevador ){
                try{
                    while( !puertaElevador.isPuertaAbierta() ){
                        puertaElevador.wait();
                    }
                }catch( InterruptedException ex){
                    ex.printStackTrace();
                }
                
                interrumpirSubProceso(1000);
                
                setUbicacion(elevador.getPisoActual());
                
                setEstaMoviendo(true);
                
                enviarEventoMoverPersona( PERSONA_SALIENDO_ELEVADOR );
            }
        }
        
        interrumpirSubProceso( 2 * TIEMPO_PARA_CAMINAR );
        
        enviarEventoMoverPersona( PERSONA_SALIO );
    }
    
    private void interrumpirSubProceso(int milisegundos){
        try{
            sleep(milisegundos);
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }
    }
    
    private void enviarEventoMoverPersona( int tipoEvento ){
        EventoMoverPersona evento = new EventoMoverPersona(this, getUbicacion(), getID());
        
        switch(tipoEvento){
            case PERSONA_CREADA:
                escuchaMoverPersona.personaCreada(evento);
                break;
            case PERSONA_LLEGO:
                escuchaMoverPersona.personaLlego(evento);
                break;
            case PERSONA_ENTRANDO_ELEVADOR:
                escuchaMoverPersona.personaEntro(evento);
                break;
            case PERSONA_OPRIMIENDO_BOTON:
                escuchaMoverPersona.personaOprimioBoton(evento);
                break;
            case PERSONA_SALIENDO_ELEVADOR:
                escuchaMoverPersona.personaPartio(evento);
                break;
            case PERSONA_SALIO:
                escuchaMoverPersona.personaSalio(evento);
                break;
            default:
                break;
        }
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isMovimiento() {
        return movimiento;
    }

    public void setEstaMoviendo(boolean movimiento) {
        this.movimiento = movimiento;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public EscuchaMoverPersona getEscuchaMoverPersona() {
        return escuchaMoverPersona;
    }

    public void setEscuchaMoverPersona(EscuchaMoverPersona escuchaMoverPersona) {
        this.escuchaMoverPersona = escuchaMoverPersona;
    }
    
    
}
