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
public class Jornada implements Serializable {

    int numJor;
    String fechaJor;
    ArrayList<Partido> partidosJor = new ArrayList();
    ArrayList<Datos_equipo> clasificacionJor = new ArrayList();

    public Jornada(int numJor, String fechaJor, String[] partidosTemp) {
        this.numJor = numJor;
        this.fechaJor = fechaJor;
        for (int i = 0; i < partidosTemp.length; i++) {     //AÑADO TODOS LOS PARTIDOS DE LA JORNADA
            String datosEncuentro[] = partidosTemp[i].split("\\$");
            partidosJor.add(new Partido(datosEncuentro[0], datosEncuentro[1], datosEncuentro[2], datosEncuentro[3]));

        }
    }
}
