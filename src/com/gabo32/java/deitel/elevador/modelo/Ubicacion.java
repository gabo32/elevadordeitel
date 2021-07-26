/*
 * Superclase abstracta que representa la ubicacion en la simulacion
 */
package com.gabo32.java.deitel.elevador.modelo;

/**
 *
 * @author juangb
 */
public abstract class Ubicacion {
    
    private String nombreUbicacion;
    
    /**
     * Devolver objeto boton en objeto ubicacion
     * @return 
     */
    public abstract Boton getBoton();
    
    /**
     * Devolver objeto puerta en el objeto ubicacion
     * @return 
     */
    public abstract Puerta getPuerta();
    
    protected void setNombreUbicacion(String nombre){
        this.nombreUbicacion = nombre;
    }
    
    public String getNombreUbicacion(){
        return this.nombreUbicacion;
    }
    
    
}
