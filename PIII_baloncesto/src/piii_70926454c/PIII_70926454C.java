/*
 *  Proyecto en Java creado por D. Ángel Picado Cuadrado
 *         (C) 2020    Universidad de Salamanca
 *         - 70926454C  angel.piccua@usal.es -
 */
package piii_70926454c;

import vista.Vista;

/**
 *
 * @author angel
 */
public class PIII_70926454C {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Vista v = new Vista();

        v.runmenu("\n\n"
                + "---------------MENÚ----------------\n"
                + "|1. Inicio de temporada           |\n"
                + "|2. Gestión de jugadoras          |\n"
                + "|3. Gestión de jornadas           |\n"
                + "|4. Visualización de resultados   |\n"
                + "|5. Almacenamiento de resultados  |\n"
                + "|q. Salir                         |\n"
                + "-----------------------------------\n"
                + "Introduzca una opción: ");
    }

}
