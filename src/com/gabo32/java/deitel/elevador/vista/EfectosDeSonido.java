/*
 * Devuelve objetos audioclip
 */
package com.gabo32.java.deitel.elevador.vista;

import java.applet.Applet;
import java.applet.AudioClip;

/**
 *
 * @author juangb
 */
public class EfectosDeSonido {
    
    private String prefijo = "";

    public EfectosDeSonido() {
    }
    
    public AudioClip getClipAudio(String archivoSonido){
        try{
            return Applet.newAudioClip(getClass().getClassLoader().getResource("resources/"+prefijo+ archivoSonido));
        }catch(NullPointerException ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    public void setPrefijoRuta(String prefijo){
        this.prefijo = prefijo;
    }
    
    
}
