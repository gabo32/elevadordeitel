/*
 * Subclase de jpanel para posicionar y mostrar un objeto imagenicon
 */
package com.gabo32.java.deitel.elevador.vista;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author juangb
 */
public class PanelImagen extends  JPanel{
    
    private int ID;
    
    private Point2D.Double posicion;
    
    private ImageIcon iconoImagen;
    
    private Set hijosPanel;
    
    public PanelImagen(int id, String nombreImagen){
        super(null);
        setOpaque(false);
        
        this.ID = id;
        
        posicion = new Point2D.Double(0, 0);
        setPosicion(0,0);
        System.out.println(getClass().getClassLoader().getResource("resources"));
        iconoImagen = new ImageIcon(getClass().getClassLoader().getResource("resources/"+nombreImagen));
        
        Image imagen = iconoImagen.getImage();
        
        setSize(imagen.getWidth(this), imagen.getHeight(this));
        
        hijosPanel = new HashSet();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        
        iconoImagen.paintIcon(this, g, 0, 0);
    }

    public void add(PanelImagen panel) {
        hijosPanel.add(panel);
        super.add(panel);
    }
    
    public void add(PanelImagen panel, int indice){
        hijosPanel.add(panel);
        
        super.add(panel, indice);
    }
    
    public void remove(PanelImagen panel){
        hijosPanel.remove(panel);
        super.remove(panel);
    }
    
    public void setIcono(ImageIcon icono){
        this.iconoImagen = icono;
    }
    
    public void setPosicion(double x, double y){
        posicion.setLocation(x, y);
        setLocation( (int)x, (int)y);
    }
    
    public int getId(){
        return this.ID;
    }
    
    public Point2D.Double getPosicion(){
        return this.posicion;
    }
    
    public ImageIcon getIconoImagen(){
        return this.iconoImagen;
    }
    
    public Set getHijos(){
        return this.hijosPanel;
    }
}
