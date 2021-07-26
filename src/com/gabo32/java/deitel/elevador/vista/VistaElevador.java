/*
 * Vista elevador para simulacionElevador
 */
package com.gabo32.java.deitel.elevador.vista;

import com.gabo32.java.deitel.elevador.ConstantesElevador;
import com.gabo32.java.deitel.elevador.evento.EscuchaSimulacionElevador;
import com.gabo32.java.deitel.elevador.evento.EventoBoton;
import com.gabo32.java.deitel.elevador.evento.EventoLuz;
import com.gabo32.java.deitel.elevador.evento.EventoMoverElevador;
import com.gabo32.java.deitel.elevador.evento.EventoMoverPersona;
import com.gabo32.java.deitel.elevador.evento.EventoPuerta;
import com.gabo32.java.deitel.elevador.evento.EventoTimbre;
import java.applet.AudioClip;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author juangb
 */
public class VistaElevador extends JPanel implements ActionListener, EscuchaSimulacionElevador, ConstantesElevador {

    private static final int ANCHURA_VISTA = 800;
    private static final int ALTURA_VISTA = 435;

    private static final int DESPLAZAMIENTO = 10;

    private static final int RETRASO_ANIMACION = 50;

    private static final int DISTANCIA_PERSONA_A_BOTON = 400;
    private static final int DISTANCIA_BOTON_A_ELEVADOR = 50;
    private static final int DISTANCIA_PERSONA_A_ELEVADOR = DISTANCIA_PERSONA_A_BOTON + DISTANCIA_BOTON_A_ELEVADOR;

    private static final int TIEMPO_A_BOTON = 3000;
    private static final int TIEMPO_A_ELEVADOR = 1000;

    private static final int TIEMPO_VIAJE_ELEVADOR = 5000;

    private static final String cuadrosPuerta[] = {
        "imagenes/puerta1.png", "imagenes/puerta2.png", "imagenes/puerta3.png",
        "imagenes/puerta4.png", "imagenes/puerta5.png"};

    private static final String cuadrosPersona[] = {
        "imagenes/insecto1.png", "imagenes/insecto2.png", "imagenes/insecto3.png",
        "imagenes/insecto4.png", "imagenes/insecto5.png", "imagenes/insecto6.png",
        "imagenes/insecto7.png", "imagenes/insecto8.png"};

    private static final String cuadrosLuz[] = {
        "imagenes/luzApagada.png", "imagenes/luzEncendida.png"};

    private static final String cuadrosLuzPrimerPiso[] = {
        "imagenes/luzPrimerPisoApagada.png",
        "imagenes/luzPrimerPisoEncendida.png"};

    private static final String cuadrosLuzSegundoPiso[] = {
        "imagenes/luzSegundoPisoApagada.png",
        "imagenes/luzSegundoPisoEncendida.png",};

    private static final String cuadrosBotonPiso[] = {
        "imagenes/botonPisoSinOprimir.png",
        "imagenes/botonPisoOprimido.png",
        "imagenes/botonPisoEncendido.png"};

    private static final String cuadrosBotonElevador[] = {
        "imagenes/botonElevadorSinOprimir.png",
        "imagenes/botonElevadorOprimido.png",
        "imagenes/botonElevadorEncendido.png"};

    private static final String cuadrosTimbre[] = {
        "imagenes/timbre1.png", "imagenes/timbre2.png",
        "imagenes/timbre3.png"};

    private static final String imagenPiso = "imagenes/piso.png";
    private static final String imagenTecho = "imagenes/techo.png";
    private static final String imagenElevador = "imagenes/elevador.png";
    private static final String imagenPared = "imagenes/pared.jpg";
    private static final String imagenConductoElevador = "imagenes/conductoElevador.png";

    // archivos de audio
    private static final String sonidoTimbre = "timbre.wav";
    private static final String sonidoPuertaAbrir = "puertaAbrir.wav";
    private static final String sonidoPuertaCerrar = "puertaCerrar.wav";
    private static final String sonidoElevador = "elevador.au";
    private static final String sonidoBoton = "boton.wav";
    private static final String sonidoCaminando = "caminar.wav";
    private static final String sonidoMusicaElevador = "liszt.mid";

    private PanelImagen panelPrimerPiso;
    private PanelImagen panelSegundoPiso;
    private PanelImagen panelConductoElevador;
    private PanelImagen panelPared;
    private PanelImagen panelTecho;

    private PanelMovil panelElevador;

    private PanelAnimado panelBotonPrimerPiso;
    private PanelAnimado panelBotonSegundoPiso;
    private PanelAnimado panelBotonElevador;
    private PanelAnimado panelTimbre;
    private PanelAnimado panelLuzElevador;
    private PanelAnimado panelLuzPrimerPiso;
    private PanelAnimado panelLuzSegundoPiso;
    private PanelAnimado panelPuerta;

    private List panelesAnimadosPersona;

    private AudioClip clipTimbre;
    private AudioClip clipPuertaAbrir;
    private AudioClip clipPuertaCerrar;
    private AudioClip clipElevador;
    private AudioClip clipBoton;
    private AudioClip clipCaminar;

    private AudioClip clipMusicaElevador;

    private Timer timerAnimacion;

    private int posicionPrimerPiso;
    private int posicionSegundoPiso;

    private double velocidadElevador;

    public VistaElevador() {
        super(null);

        instanciarPaneles();
        colocarPanelesEnVista();
        inicializarAudio();

        double distanciaPiso = posicionPrimerPiso - posicionSegundoPiso;

        double tiempo = TIEMPO_VIAJE_ELEVADOR / RETRASO_ANIMACION;

        velocidadElevador = (distanciaPiso + DESPLAZAMIENTO) / tiempo;

        iniciarAnimacion();
    }

    private void instanciarPaneles() {
        panelPrimerPiso = new PanelImagen(0, imagenPiso);
        panelSegundoPiso = new PanelImagen(0, imagenPiso);

        posicionPrimerPiso = ALTURA_VISTA - panelPrimerPiso.getHeight();
        posicionSegundoPiso = (int) (posicionPrimerPiso / 2) - DESPLAZAMIENTO;

        panelPrimerPiso.setPosicion(0, posicionPrimerPiso);
        panelSegundoPiso.setPosicion(0, posicionSegundoPiso);

        panelPared = new PanelImagen(0, imagenPared);

        panelConductoElevador = new PanelImagen(0, imagenConductoElevador);

        double posicionX = DISTANCIA_PERSONA_A_ELEVADOR + DESPLAZAMIENTO;
        double posicionY = posicionPrimerPiso - panelConductoElevador.getHeight();

        panelConductoElevador.setPosicion(posicionX, posicionY);

        panelTecho = new PanelImagen(0, imagenTecho);

        posicionY = panelConductoElevador.getPosicion().getY() - panelTecho.getHeight();

        panelTecho.setPosicion(posicionX, posicionY);

        panelElevador = new PanelMovil(0, imagenElevador);

        posicionY = posicionPrimerPiso - panelElevador.getHeight();

        panelElevador.setPosicion(posicionX, posicionY);

        panelBotonPrimerPiso = new PanelAnimado(0, cuadrosBotonPiso);

        posicionX = DISTANCIA_PERSONA_A_BOTON + 2 * DESPLAZAMIENTO;
        posicionY = posicionPrimerPiso - 5 * DESPLAZAMIENTO;
        panelBotonPrimerPiso.setPosicion(posicionX, posicionY);

        int ordenCuadrosBotonPisoOprimido[] = {0, 1, 2};
        panelBotonPrimerPiso.agregarSecuenciaCuadro(ordenCuadrosBotonPisoOprimido);

        panelBotonSegundoPiso = new PanelAnimado(1, cuadrosBotonPiso);

        posicionX = DISTANCIA_PERSONA_A_BOTON + 2 * DESPLAZAMIENTO;
        posicionY = posicionSegundoPiso - 5 * DESPLAZAMIENTO;
        panelBotonSegundoPiso.setPosicion(posicionX, posicionY);

        panelBotonSegundoPiso.agregarSecuenciaCuadro(ordenCuadrosBotonPisoOprimido);

        panelLuzPrimerPiso = new PanelAnimado(0, cuadrosLuzPrimerPiso);

        posicionX = panelElevador.getLocation().x - 4 * DESPLAZAMIENTO;
        posicionY = panelBotonPrimerPiso.getLocation().y - 10 * DESPLAZAMIENTO;
        panelLuzPrimerPiso.setPosicion(posicionX, posicionY);

        panelLuzSegundoPiso = new PanelAnimado(1, cuadrosLuzSegundoPiso);

        posicionY = panelBotonSegundoPiso.getLocation().y - 10 * DESPLAZAMIENTO;
        panelLuzSegundoPiso.setPosicion(posicionX, posicionY);

        panelPuerta = new PanelAnimado(0, cuadrosPuerta);
        int ordenCuadroPuertaAbierta[] = {0, 1, 2, 3, 4};
        int ordenCuadroPuertaCerrada[] = {4, 3, 2, 1, 0};
        panelPuerta.agregarSecuenciaCuadro(ordenCuadroPuertaAbierta);
        panelPuerta.agregarSecuenciaCuadro(ordenCuadroPuertaCerrada);

        posicionY = panelElevador.getHeight() - panelPuerta.getHeight();

        panelPuerta.setPosicion(0, posicionY);

        panelLuzElevador = new PanelAnimado(0, cuadrosLuz);
        panelLuzElevador.setPosicion(DESPLAZAMIENTO, 5 * DESPLAZAMIENTO);

        panelTimbre = new PanelAnimado(0, cuadrosTimbre);

        posicionY = panelLuzElevador.getPosicion().getY() + panelLuzElevador.getHeight() + DESPLAZAMIENTO;

        panelTimbre.setPosicion(DESPLAZAMIENTO, posicionY);
        int animacionSonarTimbre[] = {0, 1, 0, 2};
        panelTimbre.agregarSecuenciaCuadro(animacionSonarTimbre);

        panelBotonElevador = new PanelAnimado(0, cuadrosBotonElevador);

        posicionY = panelElevador.getHeight() - 6 * DESPLAZAMIENTO;
        panelBotonElevador.setPosicion(10 * DESPLAZAMIENTO, posicionY);

        int ordenCuadroBotonOprimido[] = {0, 1, 2};
        panelBotonElevador.agregarSecuenciaCuadro(ordenCuadroBotonOprimido);

        panelesAnimadosPersona = new ArrayList();
    }

    private void colocarPanelesEnVista() {
        add(panelPrimerPiso);
        add(panelSegundoPiso);
        add(panelTecho);
        add(panelElevador);
        add(panelBotonPrimerPiso);
        add(panelBotonSegundoPiso);
        add(panelLuzPrimerPiso);
        add(panelLuzSegundoPiso);
        add(panelConductoElevador);
        add(panelPared);

        panelElevador.add(panelPuerta);
        panelElevador.add(panelLuzElevador);
        panelElevador.add(panelTimbre);
        panelElevador.add(panelBotonElevador);
    }

    private void inicializarAudio() {
        EfectosDeSonido sonidos = new EfectosDeSonido();
        sonidos.setPrefijoRuta("/sonidos/");

        clipTimbre = sonidos.getClipAudio(sonidoTimbre);
        clipPuertaAbrir = sonidos.getClipAudio(sonidoPuertaAbrir);
        clipPuertaCerrar = sonidos.getClipAudio(sonidoPuertaCerrar);
        clipElevador = sonidos.getClipAudio(sonidoElevador);
        clipBoton = sonidos.getClipAudio(sonidoBoton);
        clipCaminar = sonidos.getClipAudio(sonidoCaminando);
        clipMusicaElevador = sonidos.getClipAudio(sonidoMusicaElevador);

    }

    public void iniciarAnimacion() {
        if (timerAnimacion == null) {
            timerAnimacion = new Timer(RETRASO_ANIMACION, this);
            timerAnimacion.start();
        } else if (!timerAnimacion.isRunning()) {
            timerAnimacion.restart();
        }
    }

    public void detenerAnimacion() {
        timerAnimacion.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panelElevador.animar();

        panelBotonPrimerPiso.animar();
        panelBotonSegundoPiso.animar();

        Iterator iterador = getIteradorPanelesAnimadorPersona();

        while (iterador.hasNext()) {
            PanelAnimado panelPersona = (PanelAnimado) iterador.next();

            panelPersona.animar();
        }

        repaint();
    }

    private Iterator getIteradorPanelesAnimadorPersona() {
        synchronized (panelesAnimadosPersona) {
            return new ArrayList(panelesAnimadosPersona).iterator();
        }
    }

    private void detenerSonidoCaminando() {
        clipCaminar.stop();

        Iterator iterador = getIteradorPanelesAnimadorPersona();

        while (iterador.hasNext()) {
            PanelAnimado panel = (PanelAnimado) iterador.next();

            if (panel.getVelocidadX() != 0) {
                clipCaminar.loop();
            }
        }
    }

    private PanelAnimado getPanelPersona(EventoMoverPersona evento) {
        Iterator iterador = getIteradorPanelesAnimadorPersona();

        while (iterador.hasNext()) {
            PanelAnimado panelPersona = (PanelAnimado) iterador.next();

            if (panelPersona.getId() == evento.getID()) {
                return panelPersona;
            }
        }

        return null;
    }

    public void elevadorPartio(EventoMoverElevador eventoMover) {
        String ubicacion = eventoMover.getUbicacion().getNombreUbicacion();

        Iterator iterador = getIteradorPanelesAnimadorPersona();

        while (iterador.hasNext()) {
            PanelAnimado panelPersona = (PanelAnimado) iterador.next();

            double posicionY = panelPersona.getPosicion().getY();
            String ubicacionPanel;

            if (posicionY > posicionSegundoPiso) {
                ubicacionPanel = NOMBRE_PRIMER_PISO;
            } else {
                ubicacionPanel = NOMBRE_SEGUNDO_PISO;
            }

            int posicionX = (int) panelPersona.getPosicion().getX();
            if (ubicacionPanel.equals(ubicacion) && posicionX > DISTANCIA_PERSONA_A_BOTON + DESPLAZAMIENTO) {
                remove(panelPersona);

                panelElevador.add(panelPersona, 1);
                panelPersona.setLocation(2 * DESPLAZAMIENTO, 9 * DESPLAZAMIENTO);
                panelPersona.setMovimiento(false);
                panelPersona.setAnimacion(false);
                panelPersona.setVelocidad(0, 0);
                panelPersona.setCuadroActual(1);
            }
        }

        if (ubicacion.equals(NOMBRE_PRIMER_PISO)) {
            panelElevador.setVelocidad(0, -velocidadElevador);
        } else if (ubicacion.equals(NOMBRE_SEGUNDO_PISO)) {
            panelElevador.setVelocidad(0, velocidadElevador);
        }

        panelElevador.setMovimiento(true);

        if (clipElevador != null) {
            clipElevador.play();
        }

        clipMusicaElevador.play();
    }

    public void elevadorLlego(EventoMoverElevador eventoMover) {
        panelElevador.setMovimiento(false);
        clipMusicaElevador.stop();

        double posicionX = panelElevador.getPosicion().getX();
        double posicionY;

        if (panelElevador.getVelocidadY() < 0) {
            posicionY = posicionSegundoPiso - panelElevador.getHeight();
        } else {
            posicionY = posicionPrimerPiso - panelElevador.getHeight();
        }

        panelElevador.setPosicion(posicionX, posicionY);
    }

    public void personaCreada(EventoMoverPersona eventoPersona) {
        int idPersona = eventoPersona.getID();

        String ubicacionPiso = eventoPersona.getUbicacion().getNombreUbicacion();

        PanelAnimado panelPersona = new PanelAnimado(idPersona, cuadrosPersona);

        double posicionX = -panelPersona.getWidth();
        double posicionY = 0;

        if (ubicacionPiso.equals(NOMBRE_PRIMER_PISO)) {
            posicionY = posicionPrimerPiso + (panelPrimerPiso.getHeight() / 2);
        } else if (ubicacionPiso.equals(NOMBRE_SEGUNDO_PISO)) {
            posicionY = posicionSegundoPiso + (panelSegundoPiso.getHeight() / 2);
        }

        posicionY -= panelPersona.getHeight();

        panelPersona.setPosicion(posicionX, posicionY);

        int ordenCuadrosCaminar[] = {1, 0, 1, 2};
        int ordenCuadrosOprimirBoton[] = {1, 3, 3, 4, 4, 1};
        int ordenCuadrosAlejarse[] = {6, 5, 6, 7};
        panelPersona.agregarSecuenciaCuadro(ordenCuadrosCaminar);
        panelPersona.agregarSecuenciaCuadro(ordenCuadrosOprimirBoton);
        panelPersona.agregarSecuenciaCuadro(ordenCuadrosAlejarse);

        panelPersona.reproducirAnimacion(0);
        panelPersona.setContinua(true);
        panelPersona.setAnimacion(true);
        panelPersona.setMovimiento(true);

        double tiempo = (double) (TIEMPO_A_BOTON / RETRASO_ANIMACION);
        double distanciaX = DISTANCIA_PERSONA_A_BOTON - 2 * DESPLAZAMIENTO + panelPersona.getSize().width;
        double velocidadX = distanciaX / tiempo;

        panelPersona.setVelocidad(velocidadX, 0);
        panelPersona.setVelocidadAnimacion(1);

        clipCaminar.loop();

        synchronized (panelesAnimadosPersona) {
            panelesAnimadosPersona.add(panelPersona);
        }

        add(panelPersona, 0);
    }

    @Override
    public void personaLlego(EventoMoverPersona eventoPersona) {
        PanelAnimado panel = getPanelPersona(eventoPersona);

        if (panel != null) {
            panel.setMovimiento(false);
            panel.setAnimacion(false);
            panel.setCuadroActual(1);
            detenerSonidoCaminando();

            double posicionX = DISTANCIA_PERSONA_A_BOTON - (panel.getSize().width / 2);
            double posicionY = panel.getPosicion().getY();
            
             panel.setPosicion(posicionX, posicionY);
        }
        
        
    }

    @Override
    public void personaOprimioBoton(EventoMoverPersona eventoPersona) {
        PanelAnimado panel = getPanelPersona(eventoPersona);

        if (panel != null) {
            panel.setContinua(false);
            panel.reproducirAnimacion(1);

            panel.setVelocidad(0, 0);
            panel.setMovimiento(false);
            panel.setAnimacion(true);
            detenerSonidoCaminando();
        }
    }

    @Override
    public void personaEntro(EventoMoverPersona eventoPersona) {
        PanelAnimado panel = getPanelPersona(eventoPersona);

        if (panel != null) {
            double tiempo = TIEMPO_A_ELEVADOR / RETRASO_ANIMACION;

            double distancia = panelElevador.getPosicion().getX() - panel.getPosicion().getX() + 2 * DESPLAZAMIENTO;

            panel.setVelocidad(distancia / tiempo, -1.5);

            panel.setMovimiento(true);
            panel.reproducirAnimacion(0);
            panel.setContinua(true);
        }
    }

    @Override
    public void personaPartio(EventoMoverPersona eventoPersona) {
        PanelAnimado panel = getPanelPersona(eventoPersona);

        if (panel != null) {
            double tiempo = TIEMPO_A_BOTON / RETRASO_ANIMACION;
            double velocidadX = -DISTANCIA_PERSONA_A_BOTON / tiempo;

            panel.setVelocidad(velocidadX, 0);

            panelElevador.remove(panel);

            double posicionX = DISTANCIA_PERSONA_A_ELEVADOR + 3 * DESPLAZAMIENTO;
            double posicionY = 0;

            String ubicacionPiso = eventoPersona.getUbicacion().getNombreUbicacion();

            if (ubicacionPiso.equals(NOMBRE_PRIMER_PISO)) {
                posicionY = posicionPrimerPiso + (panelPrimerPiso.getHeight() / 2);
            } else if (ubicacionPiso.equals(NOMBRE_SEGUNDO_PISO)) {
                posicionY = posicionSegundoPiso + (panelSegundoPiso.getHeight() / 2);
            }

            posicionY -= panel.getHeight();

            panel.setPosicion(posicionX, posicionY);

            add(panel, 0);

            panel.setMovimiento(true);
            panel.setAnimacion(true);
            panel.reproducirAnimacion(2);
            panel.setContinua(true);
            clipCaminar.loop();
        }
    }

    @Override
    public void personaSalio(EventoMoverPersona eventoPersona) {
        PanelAnimado panel = getPanelPersona(eventoPersona);

        if (panel != null) {
            panel.setMovimiento(false);
            panel.setAnimacion(false);

            synchronized (panelesAnimadosPersona) {
                panelesAnimadosPersona.remove(panel);
            }
            remove(panel);
            detenerSonidoCaminando();
        }
    }

    @Override
    public void puertaAbierta(EventoPuerta eventoPuerta) {
        String ubicacion = eventoPuerta.getUbicacion().getNombreUbicacion();

        panelPuerta.reproducirAnimacion(0);
        panelPuerta.setVelocidadAnimacion(2);
        panelPuerta.setMostrarUltimoCuadro(true);

        if (clipPuertaAbrir != null) {
            clipPuertaAbrir.play();
        }
    }

    @Override
    public void puertaCerrada(EventoPuerta eventoPuerta) {
        String ubicacion = eventoPuerta.getUbicacion().getNombreUbicacion();

        panelPuerta.reproducirAnimacion(1);
        panelPuerta.setVelocidadAnimacion(2);
        panelPuerta.setMostrarUltimoCuadro(true);

        if (clipPuertaCerrar != null) {
            clipPuertaCerrar.play();
        }
    }

    @Override
    public void botonOprimido(EventoBoton eventoBoton) {
        String ubicacion = eventoBoton.getUbicacion().getNombreUbicacion();

        if (ubicacion.equals(NOMBRE_ELEVADOR)) {
            panelBotonElevador.reproducirAnimacion(0);
            panelBotonElevador.setMostrarUltimoCuadro(true);
        } else if (ubicacion.equals(NOMBRE_PRIMER_PISO)) {
            panelBotonPrimerPiso.reproducirAnimacion(0);
            panelBotonPrimerPiso.setMostrarUltimoCuadro(true);
        } else if (ubicacion.equals(NOMBRE_SEGUNDO_PISO)) {
            panelBotonSegundoPiso.reproducirAnimacion(0);
            panelBotonSegundoPiso.setMostrarUltimoCuadro(true);
        }

        if (clipBoton != null) {
            clipBoton.play();
        }
    }

    @Override
    public void botonReestablecido(EventoBoton eventoBoton) {
        String ubicacion = eventoBoton.getUbicacion().getNombreUbicacion();

        if (ubicacion.equals(NOMBRE_ELEVADOR)) {
            if (panelBotonElevador.isAnimacion()) {
                panelBotonElevador.setMostrarUltimoCuadro(false);
            } else {
                panelBotonElevador.setCuadroActual(0);
            }
        } else if (ubicacion.equals(NOMBRE_PRIMER_PISO)) {
            if (panelBotonPrimerPiso.isAnimacion()) {
                panelBotonPrimerPiso.setMostrarUltimoCuadro(false);
            } else {
                panelBotonPrimerPiso.setCuadroActual(0);
            }
        } else if (ubicacion.equals(NOMBRE_SEGUNDO_PISO)) {
            if (panelBotonSegundoPiso.isAnimacion()) {
                panelBotonSegundoPiso.setMostrarUltimoCuadro(false);
            } else {
                panelBotonSegundoPiso.setCuadroActual(0);
            }
        }
    }

    @Override
    public void timbreSono(EventoTimbre eventoTimbre) {
        panelTimbre.reproducirAnimacion(0);

        if (clipTimbre != null) {
            clipTimbre.play();
        }
    }

    @Override
    public void luzSeEncendio(EventoLuz eventoLuz) {
        panelLuzElevador.setCuadroActual(1);

        String ubicacion = eventoLuz.getUbicacion().getNombreUbicacion();

        if (ubicacion.equals(NOMBRE_PRIMER_PISO)) {
            panelLuzPrimerPiso.setCuadroActual(1);
        } else if (ubicacion.equals(NOMBRE_SEGUNDO_PISO)) {
            panelLuzSegundoPiso.setCuadroActual(1);
        }
    }

    @Override
    public void luzSeApago(EventoLuz eventoLuz) {
        panelLuzElevador.setCuadroActual(0);

        String ubicacion = eventoLuz.getUbicacion().getNombreUbicacion();

        if (ubicacion.equals(NOMBRE_PRIMER_PISO)) {
            panelLuzPrimerPiso.setCuadroActual(0);
        } else if (ubicacion.equals(NOMBRE_SEGUNDO_PISO)) {
            panelLuzSegundoPiso.setCuadroActual(0);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(ANCHURA_VISTA, ALTURA_VISTA);
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

}
