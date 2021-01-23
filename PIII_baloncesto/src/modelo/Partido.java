/*
 *  Proyecto en Java creado por D. Ángel Picado Cuadrado
 *         (C) 2020    Universidad de Salamanca
 *         - 70926454C  angel.piccua@usal.es -
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author angel
 */
public class Partido implements Serializable {

    String nombreLoc;
    String nombreVis;
    int puntosLoc;
    int puntosVis;
    String fechaPar;
    String horaPar;

    public Partido(String nombreLoc, String nombreVis, String fechaPar, String horaPar) {
        this.nombreLoc = nombreLoc;
        this.nombreVis = nombreVis;
        this.fechaPar = fechaPar;
        this.horaPar = horaPar;
    }
}
