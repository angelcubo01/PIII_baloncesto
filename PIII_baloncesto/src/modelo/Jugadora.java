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
public class Jugadora implements Serializable {

    public String nombreJug;
    public String posicionJug;
    public int dorsalJug;
    public String nacimientoJug;
    public String nacionalidadJug;
    public float alturaJug;

    public Jugadora(String nombreJug, String posicionJug, int dorsalJug, String nacimientoJug, String nacionalidadJug, float alturaJug) {
        this.nombreJug = nombreJug;
        this.posicionJug = posicionJug;
        this.dorsalJug = dorsalJug;
        this.nacimientoJug = nacimientoJug;
        this.nacionalidadJug = nacionalidadJug;
        this.alturaJug = alturaJug;
    }
}
