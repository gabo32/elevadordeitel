/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabo32.java.deitel.elevador;

import com.gabo32.java.deitel.elevador.controlador.ControladorElevador;
import com.gabo32.java.deitel.elevador.modelo.SimulacionElevador;
import com.gabo32.java.deitel.elevador.vista.VistaElevador;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import javax.swing.JFrame;

/**
 *
 * @author juangb
 */
public class Elevador extends  JFrame{

    private SimulacionElevador modelo;
    private VistaElevador vista;
    private ControladorElevador controlador;
    
    public Elevador() throws HeadlessException {
        super("Simulacion de elevador deitel");
        
        modelo = new SimulacionElevador();
        vista = new VistaElevador();
        controlador = new ControladorElevador(modelo);
        
        modelo.setEscuchaSimulacionElevador(vista);
        
        getContentPane().add( vista, BorderLayout.CENTER);
        getContentPane().add(controlador, BorderLayout.SOUTH);
    }
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Elevador app = new Elevador();
        app.setDefaultCloseOperation(EXIT_ON_CLOSE);
        app.pack();
        app.setVisible(true);
    }
    
}
