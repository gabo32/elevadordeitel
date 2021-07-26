/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabo32.java.deitel.elevador.evento;

/**
 *
 * @author juangb
 */
public interface EscuchaSimulacionElevador extends EscuchaTimbre, EscuchaBoton, 
                                           EscuchaPuerta, EscuchaMoverElevador, 
                                           EscuchaLuz, EscuchaMoverPersona {

}
