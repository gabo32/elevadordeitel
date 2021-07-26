/*
 * Representa el conducto del elevador que contiene al elevador
 */
package com.gabo32.java.deitel.elevador.modelo;

import com.gabo32.java.deitel.elevador.evento.EscuchaBoton;
import com.gabo32.java.deitel.elevador.evento.EscuchaLuz;
import com.gabo32.java.deitel.elevador.evento.EscuchaMoverElevador;
import com.gabo32.java.deitel.elevador.evento.EscuchaPuerta;
import com.gabo32.java.deitel.elevador.evento.EscuchaTimbre;
import com.gabo32.java.deitel.elevador.evento.EventoBoton;
import com.gabo32.java.deitel.elevador.evento.EventoLuz;
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
public class ConductoElevador implements EscuchaMoverElevador, EscuchaLuz, EscuchaTimbre{
    
    private Elevador elevador;
    
    private Boton botonPrimerPiso;
    private Boton botonSegundoPiso;
    
    private Puerta puertaPrimerPiso;
    private Puerta puertaSegundoPiso;
    
    private Luz luzPrimerPiso;
    private Luz luzSegundoPiso;
    
    private EscuchaPuerta escuchaPuerta;
    private EscuchaBoton escuchaBoton;
    private EscuchaLuz escuchaLuz;
    private EscuchaTimbre escuchaTimbre;
    private Set escuchasMoverElevador;
    
    public ConductoElevador(Piso primerPiso, Piso segundoPiso){
        escuchasMoverElevador = new HashSet(1);
        
        EscuchaBoton escuchaBotonPiso = new EscuchaBoton() {
            @Override
            public void botonOprimido(EventoBoton eventoBoton) {
                Ubicacion ubicacion = eventoBoton.getUbicacion();
                escuchaBoton.botonOprimido(eventoBoton);
                elevador.solicitarElevador(ubicacion);
            }

            @Override
            public void botonReestablecido(EventoBoton eventoBoton) {
                escuchaBoton.botonReestablecido(eventoBoton);
            }
        };
        
        botonPrimerPiso = new Boton();
        botonSegundoPiso = new Boton();
        
        botonPrimerPiso.setEscuchaBoton(escuchaBotonPiso);
        botonSegundoPiso.setEscuchaBoton(escuchaBotonPiso);
        
        agregarEscuchaMoverElevador( botonPrimerPiso );
        agregarEscuchaMoverElevador( botonSegundoPiso );
        
        EscuchaPuerta escuchaPuertaPiso = new EscuchaPuerta() {
            @Override
            public void puertaAbierta(EventoPuerta eventoPuerta) {
                escuchaPuerta.puertaAbierta(eventoPuerta);
            }

            @Override
            public void puertaCerrada(EventoPuerta eventoPuerta) {
                escuchaPuerta.puertaCerrada(eventoPuerta);
            }
        };
        
        puertaPrimerPiso = new Puerta();
        puertaSegundoPiso = new Puerta();
        
        puertaPrimerPiso.agregarEscuchaPuerta(escuchaPuertaPiso);
        puertaSegundoPiso.agregarEscuchaPuerta(escuchaPuertaPiso);
        
        luzPrimerPiso = new Luz();
        agregarEscuchaMoverElevador(luzPrimerPiso);
        luzPrimerPiso.setEscuchaLuz(this);
        
        luzSegundoPiso = new Luz();
        agregarEscuchaMoverElevador(luzSegundoPiso);
        luzSegundoPiso.setEscuchaLuz(this);
        
        elevador = new Elevador(primerPiso, segundoPiso);
        
        elevador.agregarEscuchaMoverElevador(this);
        
        elevador.setEsuchaTimbre(this);
        
        elevador.setEscuchaBoton(new EscuchaBoton() {
            @Override
            public void botonOprimido(EventoBoton eventoBoton) {
                escuchaBoton.botonOprimido(eventoBoton);
            }

            @Override
            public void botonReestablecido(EventoBoton eventoBoton) {
                escuchaBoton.botonReestablecido(new EventoBoton(this, elevador));
            }
        });
        
        elevador.setEscuchaPuerta(new EscuchaPuerta() {
            @Override
            public void puertaAbierta(EventoPuerta eventoPuerta) {
                escuchaPuerta.puertaAbierta(eventoPuerta);
            }

            @Override
            public void puertaCerrada(EventoPuerta eventoPuerta) {
                escuchaPuerta.puertaCerrada(eventoPuerta);
            }
        });
        
        elevador.start();
    }
    
    public void timbreSono(EventoTimbre eventoTimbre){
        escuchaTimbre.timbreSono(eventoTimbre);
    }

    @Override
    public void luzSeEncendio(EventoLuz eventoLuz) {
        escuchaLuz.luzSeEncendio(eventoLuz);
    }

    @Override
    public void luzSeApago(EventoLuz eventoLuz) {
        escuchaLuz.luzSeApago(eventoLuz);
    }

    @Override
    public void elevadorPartio(EventoMoverElevador eventoMover) {
        Iterator iterador = escuchasMoverElevador.iterator();
        
        while( iterador.hasNext()){
            EscuchaMoverElevador escucha = (EscuchaMoverElevador) iterador.next();
            
            escucha.elevadorPartio(eventoMover);
        }
    }

    @Override
    public void elevadorLlego(EventoMoverElevador eventoMover) {
        Iterator iterador = escuchasMoverElevador.iterator();
        
        while( iterador.hasNext()){
            EscuchaMoverElevador escucha = (EscuchaMoverElevador) iterador.next();
            
            escucha.elevadorLlego(eventoMover);
        }
    }
    
    public void agregarEscuchaMoverElevador(EscuchaMoverElevador escucha){
        escuchasMoverElevador.add(escucha);
    }
    
    
    
    
    
    
    
    
    

    public Elevador getElevador() {
        return elevador;
    }

    public void setElevador(Elevador elevador) {
        this.elevador = elevador;
    }

    public Boton getBotonPrimerPiso() {
        return botonPrimerPiso;
    }

    public void setBotonPrimerPiso(Boton botonPrimerPiso) {
        this.botonPrimerPiso = botonPrimerPiso;
    }

    public Boton getBotonSegundoPiso() {
        return botonSegundoPiso;
    }

    public void setBotonSegundoPiso(Boton botonSegundoPiso) {
        this.botonSegundoPiso = botonSegundoPiso;
    }

    public Puerta getPuertaPrimerPiso() {
        return puertaPrimerPiso;
    }

    public void setPuertaPrimerPiso(Puerta puertaPrimerPiso) {
        this.puertaPrimerPiso = puertaPrimerPiso;
    }

    public Puerta getPuertaSegundoPiso() {
        return puertaSegundoPiso;
    }

    public void setPuertaSegundoPiso(Puerta puertaSegundoPiso) {
        this.puertaSegundoPiso = puertaSegundoPiso;
    }

    public Luz getLuzPrimerPiso() {
        return luzPrimerPiso;
    }

    public void setLuzPrimerPiso(Luz luzPrimerPiso) {
        this.luzPrimerPiso = luzPrimerPiso;
    }

    public Luz getLuzSegundoPiso() {
        return luzSegundoPiso;
    }

    public void setLuzSegundoPiso(Luz luzSegundoPiso) {
        this.luzSegundoPiso = luzSegundoPiso;
    }

    public EscuchaPuerta getEscuchaPuerta() {
        return escuchaPuerta;
    }

    public void setEscuchaPuerta(EscuchaPuerta escuchaPuerta) {
        this.escuchaPuerta = escuchaPuerta;
    }

    public EscuchaBoton getEscuchaBoton() {
        return escuchaBoton;
    }

    public void setEscuchaBoton(EscuchaBoton escuchaBoton) {
        this.escuchaBoton = escuchaBoton;
    }

    public EscuchaLuz getEscuchaLuz() {
        return escuchaLuz;
    }

    public void setEscuchaLuz(EscuchaLuz escuchaLuz) {
        this.escuchaLuz = escuchaLuz;
    }

    public EscuchaTimbre getEscuchaTimbre() {
        return escuchaTimbre;
    }

    public void setEscuchaTimbre(EscuchaTimbre escuchaTimbre) {
        this.escuchaTimbre = escuchaTimbre;
    }

    public Set getEscuchasMoverElevador() {
        return escuchasMoverElevador;
    }

    public void setEscuchasMoverElevador(Set escuchasMoverElevador) {
        this.escuchasMoverElevador = escuchasMoverElevador;
    }
    
    
}
