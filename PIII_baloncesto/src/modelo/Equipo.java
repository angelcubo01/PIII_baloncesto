/*
 *  Proyecto en Java creado por D. Ángel Picado Cuadrado
 *         (C) 2020    Universidad de Salamanca
 *         - 70926454C  angel.piccua@usal.es -
 */
package modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author angel
 */
public class Equipo implements Serializable {

    public String nombreEq;
    public String direccionEq;
    public long telefonoEq;
    public String webEq;
    public String emailEq;
    public ArrayList<Jugadora> jugadorasEq = new ArrayList();

    public Equipo(String nombreEq, String direccionEq, long telefonoEq, String webEq, String emailEq) {
        this.nombreEq = nombreEq;
        this.direccionEq = direccionEq;
        this.telefonoEq = telefonoEq;
        this.webEq = webEq;
        this.emailEq = emailEq;
    }
}
