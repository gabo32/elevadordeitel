/*
 * Model de simulacion del elevador con conducto elevador y dos objetos piso
 */
package com.gabo32.java.deitel.elevador.modelo;

import com.gabo32.java.deitel.elevador.ConstantesElevador;
import com.gabo32.java.deitel.elevador.evento.EscuchaBoton;
import com.gabo32.java.deitel.elevador.evento.EscuchaLuz;
import com.gabo32.java.deitel.elevador.evento.EscuchaMoverElevador;
import com.gabo32.java.deitel.elevador.evento.EscuchaMoverPersona;
import com.gabo32.java.deitel.elevador.evento.EscuchaPuerta;
import com.gabo32.java.deitel.elevador.evento.EscuchaSimulacionElevador;
import com.gabo32.java.deitel.elevador.evento.EscuchaTimbre;
import com.gabo32.java.deitel.elevador.evento.EventoBoton;
import com.gabo32.java.deitel.elevador.evento.EventoLuz;
import com.gabo32.java.deitel.elevador.evento.EventoMoverElevador;
import com.gabo32.java.deitel.elevador.evento.EventoMoverPersona;
import com.gabo32.java.deitel.elevador.evento.EventoPuerta;
import com.gabo32.java.deitel.elevador.evento.EventoTimbre;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author juangb
 */
public class SimulacionElevador implements EscuchaSimulacionElevador, ConstantesElevador {

    private Piso primerPiso;
    private Piso segundoPiso;

    private ConductoElevador conductoElevador;

    private Set escuchasMoverPersona;
    private EscuchaPuerta escuchaPuerta;
    private EscuchaBoton escuchaBoton;
    private EscuchaLuz escuchaLuz;
    private EscuchaTimbre escuchaTimbre;
    private EscuchaMoverElevador escuchaMoverElevador;

    private int numeroDePersonas = 0;

    public SimulacionElevador() {
        primerPiso = new Piso(NOMBRE_PRIMER_PISO);
        segundoPiso = new Piso(NOMBRE_SEGUNDO_PISO);

        conductoElevador = new ConductoElevador(primerPiso, segundoPiso);

        primerPiso.setConductoElevador(conductoElevador);
        segundoPiso.setConductoElevador(conductoElevador);

        conductoElevador.setEscuchaPuerta(this);
        conductoElevador.setEscuchaBoton(this);
        conductoElevador.agregarEscuchaMoverElevador(this);
        conductoElevador.setEscuchaLuz(this);
        conductoElevador.setEscuchaTimbre(this);

        escuchasMoverPersona = new HashSet(1);
    }

    private Piso getPiso(String nombre) {
        if (nombre.equals(NOMBRE_PRIMER_PISO)) {
            return primerPiso;
        }
        if (nombre.equals(NOMBRE_SEGUNDO_PISO)) {
            return segundoPiso;
        }
        return null;
    }

    public void agregarPersona(String nombrePiso) {
        Persona persona = new Persona(numeroDePersonas, getPiso(nombrePiso));
        persona.setName(Integer.toString(numeroDePersonas));

        persona.setEscuchaMoverPersona(this);

        persona.start();

        numeroDePersonas++;
    }

    @Override
    public void elevadorPartio(EventoMoverElevador eventoMover) {
        escuchaMoverElevador.elevadorPartio(eventoMover);
    }

    @Override
    public void elevadorLlego(EventoMoverElevador eventoMover) {
        escuchaMoverElevador.elevadorLlego(eventoMover);
    }

    public void enviarEventoMoverPersona(int tipoEvento, EventoMoverPersona evento) {
        Iterator iterador = escuchasMoverPersona.iterator();

        while (iterador.hasNext()) {
            EscuchaMoverPersona escucha = (EscuchaMoverPersona) iterador.next();

            switch (tipoEvento) {
                case Persona.PERSONA_CREADA:
                    escucha.personaCreada(evento);
                    break;
                case Persona.PERSONA_LLEGO:
                    escucha.personaLlego(evento);
                    break;
                case Persona.PERSONA_ENTRANDO_ELEVADOR:
                    escucha.personaEntro( evento );
                    break;
                case Persona.PERSONA_OPRIMIENDO_BOTON:
                    escucha.personaOprimioBoton(evento);
                    break;
                case Persona.PERSONA_SALIENDO_ELEVADOR:
                    escucha.personaPartio(evento);
                    break;
                case Persona.PERSONA_SALIO:
                    escucha.personaSalio(evento);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void personaCreada(EventoMoverPersona eventoMover) {
        enviarEventoMoverPersona(Persona.PERSONA_CREADA, eventoMover);
    }

    @Override
    public void personaLlego(EventoMoverPersona eventoMover) {
        enviarEventoMoverPersona(Persona.PERSONA_LLEGO, eventoMover);
    }

    @Override
    public void personaOprimioBoton(EventoMoverPersona eventoMover) {
        enviarEventoMoverPersona(Persona.PERSONA_OPRIMIENDO_BOTON, eventoMover);
    }

    @Override
    public void personaEntro(EventoMoverPersona eventoMover) {
        enviarEventoMoverPersona(Persona.PERSONA_ENTRANDO_ELEVADOR, eventoMover);
    }

    @Override
    public void personaPartio(EventoMoverPersona eventoMover) {
        enviarEventoMoverPersona(Persona.PERSONA_SALIENDO_ELEVADOR, eventoMover);
    }

    @Override
    public void personaSalio(EventoMoverPersona eventoMover) {
        enviarEventoMoverPersona(Persona.PERSONA_SALIO, eventoMover);
    }

    @Override
    public void puertaAbierta(EventoPuerta eventoPuerta) {
        escuchaPuerta.puertaAbierta(eventoPuerta);
    }

    @Override
    public void puertaCerrada(EventoPuerta eventoPuerta) {
        escuchaPuerta.puertaCerrada(eventoPuerta);
    }

    @Override
    public void botonOprimido(EventoBoton eventoBoton) {
        escuchaBoton.botonOprimido(eventoBoton);
    }

    @Override
    public void botonReestablecido(EventoBoton eventoBoton) {
        escuchaBoton.botonReestablecido(eventoBoton);
    }

    @Override
    public void timbreSono(EventoTimbre eventoTimbre) {
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
    
    public void setEscuchaSimulacionElevador( EscuchaSimulacionElevador escucha){
        agregarEscuchaMoverPersona( escucha );
        setEscuchaMoverElevador( escucha );
        setEscuchaPuerta( escucha );
        setEscuchaBoton( escucha );
        setEscuchaLuz( escucha );
        setEscuchaTimbre( escucha );
    }
    
    public void agregarEscuchaMoverPersona(EscuchaMoverPersona escucha){
        escuchasMoverPersona.add(escucha);
    }
    
    public void setEscuchaPuerta(EscuchaPuerta escucha){
        escuchaPuerta = escucha;
    }
    
    public void setEscuchaBoton(EscuchaBoton escucha){
        this.escuchaBoton = escucha;
    }
    
    public void setEscuchaMoverElevador(EscuchaMoverElevador escucha){
        this.escuchaMoverElevador = escucha;
    }
    
    public void setEscuchaLuz(EscuchaLuz escucha){
        this.escuchaLuz = escucha;
    }
    
    public void setEscuchaTimbre(EscuchaTimbre escucha){
        this.escuchaTimbre = escucha;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
