/*
 *  Proyecto en Java creado por D. Ángel Picado Cuadrado
 *         (C) 2020    Universidad de Salamanca
 *         - 70926454C  angel.piccua@usal.es -
 */
package vista;

import static com.coti.tools.DiaUtil.clear;
import com.coti.tools.Esdia;
import controlador.Controlador;
import java.nio.file.Path;

/**
 *
 * @author angel
 */
public class Vista {

    Controlador c = new Controlador();

    public void runmenu(String menu) {
        boolean fin = false;
        boolean cargadosDatos = false;
        Path archivoGuardado = c.pathEnSubcarpetaEnEscritorio(c.getCarpetaGeneral(), c.getCarpetaBinarios(), c.getArchivoBinarios());
        if (archivoGuardado.toFile().exists()) {
            if (Esdia.yesOrNo("Quiere cargar los datos guardados antes: ")) {
                c.leerBinariosModelo();
                cargadosDatos = true;
            }
        }
        do {

            String op = Esdia.readString(menu);
            switch (op) {
                case "1" -> {
                    clear();
                    if (cargadosDatos == true) {
                        if (Esdia.yesOrNo("Quiere volver a cargar los datos de los fichero?? *Se sobrescribira los anteriores")) {
                            inicioTemporada();
                        }
                    } else {
                        inicioTemporada();
                        cargadosDatos = true;
                    }
                }
                case "2" -> {
                    clear();
                    if (cargadosDatos == false) {
                        System.out.println("Tienes que cargar los datos primero : Pulsa opción '1'");
                    } else {
                        gestionJugadoras();
                    }
                }
                case "3" -> {
                    clear();
                    if (cargadosDatos == false) {
                        System.out.println("Tienes que cargar los datos primero : Pulsa opción '1'");
                    } else {
                        gestionJornada();
                    }
                }
                case "4" -> {
                    clear();
                    if (cargadosDatos == false) {
                        System.out.println("Tienes que cargar los datos primero : Pulsa opción '1'");
                    } else {
                        visualizarResultados();
                    }
                }
                case "5" -> {
                    clear();
                    if (cargadosDatos == false) {
                        System.out.println("Tienes que cargar los datos primero : Pulsa opción '1'");
                    } else {
                        extraerDatos();
                    }
                }
                case "q" -> {
                    if (Esdia.yesOrNo("Quiere guardar la sesion para retomarla más tarde: ")) {
                        c.guardarBinariosModelo();
                    }
                    fin = Esdia.yesOrNo("¿Desea salir del programa?");
                }
                default ->
                    System.out.println("Opción Incorrecta, inténtalo de nuevo\n");
            }
        } while (!fin);

    }

    private void inicioTemporada() {
        c.setTemporada(Esdia.readString("Introduce la temporada: "));
        c.cargarJornadas();
        System.out.println("Jornadas Cargadas Correctamente");
        c.cargarEquipos();
        System.out.println("Equipos Cargados Correctamente");
        c.cargarJugadoras();
        System.out.println("Jugadoras Cargadas Correctamente");
    }

    private void gestionJugadoras() {
        String op1 = Esdia.readString("-------MENÚ GESTIÓN JUGADORAS-----------\n"
                + "|1. Modificar datos Jugadora           |\n"
                + "|2. Eliminar Jugadora de un Equipo     |\n"
                + "|3. Añadir Jugadora a un Equipo        |\n"
                + "----------------------------------------\n"
                + "Introduzca una opción: ");
        int eqSelec, jugSelec;
        switch (op1) {
            case "1" -> {
                int campoNuevoInt = -1;
                float campoNuevoFloat = -1;
                String campoNuevo = "null";
                eqSelec = seleccionarEquipo();
                jugSelec = seleccionarJugadora(eqSelec);
                String[] opcionesDisponibles1 = {"1", "2", "3", "4", "5"};
                String op11 = Esdia.readString("Que quieres modificar de la jugadora: \n"
                        + "1. Posición   2. Dorsal   3. Nacimiento   4. Nacionalidad   5.Altura\n"
                        + "Introduzca una opción: ", opcionesDisponibles1);
                if ("2".equals(op11)) {
                    campoNuevoInt = Esdia.readInt("Nuevo campo: ");
                } else if ("5".equals(op11)) {
                    campoNuevoFloat = Esdia.readFloat("Nuevo campo: ");
                } else {
                    campoNuevo = Esdia.readString("Nuevo campo: ");
                }
                c.modificarJugadora(eqSelec, jugSelec, op11, campoNuevo, campoNuevoInt, campoNuevoFloat);
            }
            case "2" -> {
                eqSelec = seleccionarEquipo();
                jugSelec = seleccionarJugadora(eqSelec);
                System.out.println("Se va a eliminar la jugadora selecionadas");
                c.eliminarJugadora(eqSelec, jugSelec);
            }
            case "3" -> {
                eqSelec = seleccionarEquipo();
                System.out.println("Se piden todos los datos: ");
                String nombreJugNueva = Esdia.readString("Nombre Jugadora: ").toUpperCase();
                String posicionJugNueva = Esdia.readString("Posicion Jugadora: ");
                int dorsalJugNueva = Esdia.readInt("Dorsal Jugadora: ");
                String nacimientoJugNueva = Esdia.readString("Nacimiento Jugadora: ");
                String nacionalidadJugNueva = Esdia.readString("Nacionalidad Jugadora: ").toUpperCase();
                float alturaJugNueva = Esdia.readFloat("Altura Jugadora: ");
                c.agregarJugadora(eqSelec, nombreJugNueva, posicionJugNueva, dorsalJugNueva, nacimientoJugNueva, nacionalidadJugNueva, alturaJugNueva);
            }
            default ->
                System.out.println("Opción Incorrecta, inténtalo de nuevo\n");
        }
    }

    private void gestionJornada() {
        String op1 = Esdia.readString("----------MENÚ GESTIÓN JORNADA------------\n"
                + "|1. Leer los resultados de la jornada    |\n"
                + "|2. Leer resultados de varias jornadas   |\n" //La opción 2. no se pedía pero se ha implementado para ayudar en la depuración
                + "|  (hay que indicar hasta que jornada se |\n" // sirve para leer resultados de multiples jornadas a la vez
                + "|   quiere leer)                         |\n"
                + "|3. Modificar fecha de la jornada        |\n"
                + "|4. Modificar fecha u hora de un partido |\n"
                + "|5. Mostrar resultados de jornada        |\n"
                + "|6. Mostrar clasificación de una jornada |\n"
                + "------------------------------------------\n"
                + "Introduzca una opción: ");
        int jornada = Esdia.readInt("Introduce la jornada a gestionar: ", 1, c.getN_JORNADAS());
        switch (op1) {
            case "1" -> {
                c.cargarResulJornadas(jornada);
                System.out.printf("Se carga la jornada %d\n", jornada);
            }
            case "2" -> {
                c.cargarResulHastaJornadas(jornada);
                System.out.printf("Se carga la jornada %d y las anteriores\n", jornada);
            }
            case "3" -> {
                String fechaJornada = Esdia.readString("Introduce la nueva fecha para la jornada: ");
                c.modificarFechaJor(jornada, fechaJornada);
            }
            case "4" -> {
                int equipoSelec = seleccionarEquipo();
                String op11 = Esdia.readString("Elije que quieres cambiar: 1- Fecha  2- Hora  : ", "1", "2");
                String nuevoCampo = Esdia.readString("Introduce el nuevo dato: ");
                c.modificarFechaHoraPartido(op11, equipoSelec, nuevoCampo, jornada);
            }
            case "5" ->
                c.resultadosJornada(jornada);
            case "6" ->
                c.clasificacionJornada(jornada);
            default ->
                System.out.println("Opción Incorrecta, inténtalo de nuevo\n");
        }
    }

    private void visualizarResultados() {
        String op1 = Esdia.readString("--------MENÚ VISUALIZAR RESULTADOS--------\n"
                + "|1. Jugadoras de un equipo               |\n"
                + "|2. Equipos y sus datos                  |\n"
                + "|3. Jugadoras (Letra inicial nombre)     |\n"
                + "------------------------------------------\n"
                + "Introduzca una opción: ");
        switch (op1) {
            case "1" -> {
                int eqSelec = seleccionarEquipo();
                System.out.println("Jugadoras ordenadas por posición y altura ** :");
                System.out.println("**Las jugadoras que no tienen asignados datos numericos aparecen con un '-1'**");
                c.mostrarJugadorasEquipo(eqSelec);
            }
            case "2" -> {
                System.out.println("Datos de todos los equipos:");
                c.mostrarDatosEquipo();
            }
            case "3" -> {
                String lInicial = Esdia.readString("Introduce la inicial de la jugadora: ");
                System.out.printf("Jugadoras que empiezan por '%s' : \n", lInicial);
                System.out.println("**Las jugadoras que no tienen asignados datos numericos aparecen con un '-1'**");
                c.mostrarJugadorasInicial(lInicial);
            }
            default ->
                System.out.println("Opción Incorrecta, inténtalo de nuevo\n");
        }
    }

    private void extraerDatos() {
        String op1 = Esdia.readString("------------MENÚ EXTRAER DATOS------------\n"
                + "|1. Jugadoras de un equipo               |\n"
                + "|2. Equipos y sus datos                  |\n"
                + "|3. Clasificación de una jornada         |\n"
                + "------------------------------------------\n"
                + "Introduzca una opción: ");
        switch (op1) {
            case "1" -> {
                int eqSelec = seleccionarEquipo();
                c.extraerJugadorasEquipo(eqSelec);
                System.out.println("Se crea el archivo en la carpeta indicada");
            }
            case "2" -> {
                c.extraerDatosEquipo();
                System.out.println("Se crea el archivo en la carpeta indicada");
            }
            case "3" -> {
                int jornada = Esdia.readInt("Introduce la jornada de la que desea crear el archivo: ", 1, c.getN_JORNADAS());
                c.extraerClasificacionHtml(jornada);
                System.out.println("Se crea el archivo en la carpeta indicada");
            }
            default ->
                System.out.println("Opción Incorrecta, inténtalo de nuevo\n");
        }

    }
// ---------------------------------------------------------------MENUS AUXILIARES-------------------------------------

    private int seleccionarEquipo() {
        System.out.println("Equipos disponibles:");
        for (int i = 0; i < c.getN_EQUIPOS(); i++) {
            System.out.printf("%d.\t%s\n", i + 1, c.getEquipos().get(i).nombreEq);
        }
        int eqSelec = Esdia.readInt("Selecciona el equipo: ");
        return eqSelec - 1;
    }

    private int seleccionarJugadora(int eqSelec) {
        System.out.printf("Jugadoras del equipo %s: \n", c.getEquipos().get(eqSelec).nombreEq);
        for (int i = 0; i < c.getEquipos().get(eqSelec).jugadorasEq.size(); i++) {
            System.out.printf("%d.\t%s\n", i + 1, c.getEquipos().get(eqSelec).jugadorasEq.get(i).nombreJug);
        }
        int jugSelec = Esdia.readInt("Selecciona la jugadora: ");
        return jugSelec - 1;
    }

}
