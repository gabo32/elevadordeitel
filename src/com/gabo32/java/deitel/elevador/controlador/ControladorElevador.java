/*
 * Controlador para la simulacion del elevador
 */
package com.gabo32.java.deitel.elevador.controlador;

import com.gabo32.java.deitel.elevador.ConstantesElevador;
import com.gabo32.java.deitel.elevador.evento.EscuchaMoverPersona;
import com.gabo32.java.deitel.elevador.evento.EventoMoverPersona;
import com.gabo32.java.deitel.elevador.modelo.SimulacionElevador;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author juangb
 */
public class ControladorElevador extends JPanel implements ConstantesElevador {

    private JButton primerBotonControlador;
    private JButton segundoBotonControlador;

    private SimulacionElevador simulacionElevador;

    public ControladorElevador(SimulacionElevador simulacion) {
        this.simulacionElevador = simulacion;
        setBackground(Color.white);

        primerBotonControlador = new JButton("Primer piso");
        add(primerBotonControlador);

        segundoBotonControlador = new JButton("Segundo piso");
        add(segundoBotonControlador);

        primerBotonControlador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulacionElevador.agregarPersona(NOMBRE_PRIMER_PISO);
                primerBotonControlador.setEnabled(false);
            }
        });

        segundoBotonControlador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulacionElevador.agregarPersona(NOMBRE_SEGUNDO_PISO);

                segundoBotonControlador.setEnabled(false);
            }
        });

        simulacionElevador.agregarEscuchaMoverPersona(new EscuchaMoverPersona() {
            @Override
            public void personaCreada(EventoMoverPersona eventoMover) {
            }

            @Override
            public void personaLlego(EventoMoverPersona eventoMover) {
            }

            @Override
            public void personaPartio(EventoMoverPersona eventoMover) {
            }

            @Override
            public void personaOprimioBoton(EventoMoverPersona eventoMover) {
            }

            @Override
            public void personaEntro(EventoMoverPersona eventoMover) {
                System.out.println("Persona salio");
                String ubicacion = eventoMover.getUbicacion().getNombreUbicacion();

                if (ubicacion.equals(NOMBRE_PRIMER_PISO)) {
                    primerBotonControlador.setEnabled(true);
                } else {
                    segundoBotonControlador.setEnabled(true);
                }
            }

            @Override
            public void personaSalio(EventoMoverPersona eventoMover) {
            }
        });
    }

}
