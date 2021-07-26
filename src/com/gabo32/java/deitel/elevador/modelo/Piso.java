/*
 * Representa un piso ubicado enseguda de un conducto elevador
 */
package com.gabo32.java.deitel.elevador.modelo;

import com.gabo32.java.deitel.elevador.ConstantesElevador;

/**
 *
 * @author juangb
 */
public class Piso extends Ubicacion implements ConstantesElevador{
    
    private ConductoElevador condutoElevador;

    public Piso(String nombre) {
        setNombreUbicacion(nombre);
    }
    
    @Override
    public Boton getBoton(){
        if( getNombreUbicacion().equals(NOMBRE_PRIMER_PISO)){
            return getConductoElevador().getBotonPrimerPiso();
        }else if( getNombreUbicacion().equals(NOMBRE_SEGUNDO_PISO)){
            return getConductoElevador().getBotonSegundoPiso();
            
        }else{
            return null;
        }
    }
    
    @Override
    public Puerta getPuerta(){
        if( getNombreUbicacion().equals(NOMBRE_PRIMER_PISO)){
            return getConductoElevador().getPuertaPrimerPiso();
        }else if( getNombreUbicacion().equals(NOMBRE_SEGUNDO_PISO)){
            return getConductoElevador().getPuertaSegundoPiso();
        }else {
            return null;
        }
    }
    
    public ConductoElevador getConductoElevador(){
        return condutoElevador;
    }
    
    public void setConductoElevador(ConductoElevador conducto){
        this.condutoElevador = conducto;
    }
    
    
}
