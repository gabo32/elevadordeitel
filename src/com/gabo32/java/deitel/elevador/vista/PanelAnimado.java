/*
 * Subclase de panemovil con herramientas para animacion
 */
package com.gabo32.java.deitel.elevador.vista;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author juangb
 */
public class PanelAnimado extends PanelMovil{
    
    private boolean animacion;
    
    private int velocidadAnimacion;
    private int contadorVelocidadAnimacion;
    private boolean recorrerHaciaDelante = true;
    
    private ImageIcon iconosImagenes[];
    
    private List secuenciasCuadros;
    private int animacionActual;
    
    private boolean continuar;
    
    private boolean mostrarUltimoCuadro;
    
    private int contadorCuadroActual;

    public PanelAnimado(int id, String nombreImagen[]) {
        super(id, nombreImagen[0]);
        
        iconosImagenes = new ImageIcon[nombreImagen.length];
        
        for (int i = 0; i < iconosImagenes.length; i++) {
            iconosImagenes[i] = new ImageIcon(getClass().getClassLoader().getResource("resources/"+nombreImagen[i]));
        }
        
        secuenciasCuadros = new ArrayList();
    }
    
    public void animar(){
        super.animar();
        
        if( secuenciasCuadros != null && isAnimacion()){
            if( contadorVelocidadAnimacion > velocidadAnimacion){
                contadorVelocidadAnimacion = 0;
                determinarSiguienteCuadro();
            }else{
                contadorVelocidadAnimacion ++;
            }
        }
    }
    
    private void determinarSiguienteCuadro(){
        int secuenciCuadros[] = (int[]) secuenciasCuadros.get(animacionActual);
        
        if( contadorCuadroActual >= secuenciCuadros.length){
            contadorCuadroActual = 0;
            
            if( !debeContinuar()){
                setAnimacion(false);
                
                if( isMostrarUltimoCuadro()){
                    contadorCuadroActual = secuenciCuadros.length-1;
                }
            }
        }
        
        setCuadroActual(secuenciCuadros[contadorCuadroActual]);
        contadorCuadroActual++;
    }
    
    public void agregarSecuenciaCuadro(int secuenciaCuadros[]){
        secuenciasCuadros.add(secuenciaCuadros);
    }

    public boolean isAnimacion() {
        return animacion;
    }
    
    public void setAnimacion(boolean animar){
        this.animacion = animar;
    }
    
    public void setCuadroActual(int cuadro){
        setIcono(iconosImagenes[cuadro]);
    }

    public void setVelocidadAnimacion(int velocidadAnimacion) {
        this.velocidadAnimacion = velocidadAnimacion;
    }

    public int getVelocidadAnimacion() {
        return velocidadAnimacion;
    }
    
    public void setContinua(boolean continuarAnimacion){
        this.continuar = continuarAnimacion;
    }
    
    public boolean debeContinuar(){
        return continuar;
    }
    
    public boolean isMostrarUltimoCuadro(){
        return mostrarUltimoCuadro;
    }
    
    public void setMostrarUltimoCuadro(boolean mostrarCuadro){
        this.mostrarUltimoCuadro = mostrarCuadro;
    }
    
    public void reproducirAnimacion(int secuenciaCuadros){
        animacionActual = secuenciaCuadros;
        contadorCuadroActual = 0;
        setAnimacion(true);
    }
    
    
    
    
}
