/*
 * Subclase de jpanel con herramientas para mover objetos en pantalla
 */
package com.gabo32.java.deitel.elevador.vista;

import java.util.Iterator;

/**
 *
 * @author juangb
 */
public class PanelMovil extends PanelImagen{
    
    private boolean movimiento;
    
    private double velocidadX;
    private double velocidadY;

    public PanelMovil(int id, String nombreImagen) {
        super(id, nombreImagen);
        
        velocidadX = 0;
        velocidadY = 0;
    }
    
    public void animar(){
        if( isMovimiento()){
            double posicionXAntigua = getPosicion().getX();
            double posicionYAntigue = getPosicion().getY();
            
            setPosicion(posicionXAntigua + velocidadX, posicionYAntigue+velocidadY);
        }
        
        Iterator iterador = getHijos().iterator();
        
        while( iterador.hasNext()){
            PanelMovil panel = (PanelMovil) iterador.next();
            
            panel.animar();
        }
    }

    public boolean isMovimiento() {
        return movimiento;
    }

    public void setMovimiento(boolean movimiento) {
        this.movimiento = movimiento;
    }

    public double getVelocidadX() {
        return velocidadX;
    }

    public void setVelocidad(double velocidadX, double velocidadY) {
        this.velocidadX = velocidadX;
        this.velocidadY = velocidadY;
    }

    public double getVelocidadY() {
        return velocidadY;
    }

   
    
    
    
    
}
