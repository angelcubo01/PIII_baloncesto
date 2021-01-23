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
public class Datos_equipo implements Serializable {

    String nombreEq;
    int partidosJug;
    int partidosGanad;
    int partidosPerd;
    int puntosFavor;
    int puntosContra;
    int puntosClasificacion;

    Datos_equipo(String nombreEq, int puntosFavor, int puntosContra) {  //PRIMERA JORNADA
        this.nombreEq = nombreEq;
        this.puntosFavor = puntosFavor;
        this.puntosContra = puntosContra;
        this.partidosJug = 1;
        if (puntosFavor > puntosContra) {
            this.partidosGanad = 1;
            this.partidosPerd = 0;
        } else {
            this.partidosGanad = 0;
            this.partidosPerd = 1;
        }
        this.puntosClasificacion = 2 * this.partidosGanad + this.partidosPerd;
    }

    Datos_equipo(String nombreEq, int puntosFavor, int puntosContra, Datos_equipo jornadaAnterior) {  //RESTO DE JORNADAS
        this.nombreEq = nombreEq;
        this.puntosFavor = puntosFavor + jornadaAnterior.puntosFavor;
        this.puntosContra = puntosContra + jornadaAnterior.puntosContra;
        this.partidosJug = jornadaAnterior.partidosJug + 1;
        if (puntosFavor > puntosContra) {
            this.partidosGanad = jornadaAnterior.partidosGanad + 1;
            this.partidosPerd = jornadaAnterior.partidosPerd;
        } else {
            this.partidosGanad = jornadaAnterior.partidosGanad;
            this.partidosPerd = jornadaAnterior.partidosPerd + 1;
        }
        this.puntosClasificacion = 2 * this.partidosGanad + this.partidosPerd;
    }
}
