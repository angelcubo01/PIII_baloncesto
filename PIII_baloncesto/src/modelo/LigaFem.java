/*
 *  Proyecto en Java creado por D. Ángel Picado Cuadrado
 *         (C) 2020    Universidad de Salamanca
 *         - 70926454C  angel.piccua@usal.es -
 */
package modelo;

import com.coti.tools.OpMat;
import com.coti.tools.Rutas;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import static java.lang.System.err;
import static java.lang.System.out;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

/**
 *
 * @author angel
 */
public class LigaFem implements Serializable {

    //NOMBRES DE LOS FICHEROS y CARPETAS:
    final String carpetaGeneral = "LigFemBal";
    final String jugadorasDatosCarpeta = "jugadoras";
    final String jornadasResultadosDatosCarpeta = "resul_jornadas";
    final String carpetaExtracciones = "fichsalida";
    final String jornadasDatosArchivo = "datosjornadas.txt";
    final String equiposDatosArchivo = "datosequipos.txt";
    final String equiposExtracciones = "equipos.enc";
    private String temporada;
    private final int N_PARTIDOS = 8;
    private final int N_JORNADAS = 15;
    private final int N_EQUIPOS = 16;
    private ArrayList<Jornada> jornadas = new ArrayList(N_JORNADAS);
    private ArrayList<Equipo> equipos = new ArrayList(N_EQUIPOS);

    public String getCarpetaGeneral() {
        return carpetaGeneral;
    }

    public int getN_PARTIDOS() {
        return N_PARTIDOS;
    }

    public int getN_JORNADAS() {
        return N_JORNADAS;
    }

    public int getN_EQUIPOS() {
        return N_EQUIPOS;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    public ArrayList<Jornada> getJornadas() {
        return jornadas;
    }

    public ArrayList<Equipo> getEquipos() {
        return equipos;
    }

    //---------------------------------------------Menú 1----------------------------------------------------------------------------------------
    public void cargarJornadas() {
        Path pathJornadas = Rutas.pathToFileInFolderOnDesktop(carpetaGeneral, jornadasDatosArchivo);
        try {
            String datosJornadasTemp[][] = OpMat.importFromDisk(pathJornadas.toFile(), "\\+");
            for (int i = 0; i < N_JORNADAS; i++) {
                String datosPartidosJornada[] = datosJornadasTemp[i][2].split("#");
                Jornada jornadaTemp = new Jornada(Integer.parseInt(datosJornadasTemp[i][0]), datosJornadasTemp[i][1], datosPartidosJornada);
                jornadas.add(jornadaTemp);
            }
        } catch (Exception ex) {
            out.printf("Error al cargar jornadas : %s\n", ex.toString());
            System.exit(-1);
        }

    }

    public void cargarEquipos() {
        Path pathEquipos = Rutas.pathToFileInFolderOnDesktop(carpetaGeneral, equiposDatosArchivo);
        try {
            String datosEquiposTemp[][] = OpMat.importFromDisk(pathEquipos.toFile(), "#");
            for (int i = 0; i < N_EQUIPOS; i++) {
                equipos.add(new Equipo(datosEquiposTemp[i][0], datosEquiposTemp[i][1], Long.parseLong(datosEquiposTemp[i][2]), datosEquiposTemp[i][3], datosEquiposTemp[i][4]));
            }
        } catch (Exception ex) {
            out.printf("Error al cargar equipos : %s\n", ex.toString());
            System.exit(-1);
        }

    }

    public void cargarJugadoras() {
        for (int i = 0; i < N_EQUIPOS; i++) {
            String nombreArchivoDatos = equipos.get(i).nombreEq.toUpperCase(new Locale("es_ES"));
            Path pathJugadoras = pathEnSubcarpetaEnEscritorio(carpetaGeneral, jugadorasDatosCarpeta, nombreArchivoDatos.concat(".txt"));
            try {
                String[][] datosJugadorasTemp = OpMat.importFromDisk(pathJugadoras.toFile(), "\t");
                for (int j = 0; j < datosJugadorasTemp.length; j++) {
                    if (("-".equals(datosJugadorasTemp[j][1])) || ("".equals(datosJugadorasTemp[j][1]))) { //NO POSICIÓN -> no_pos;
                        datosJugadorasTemp[j][1] = "no_pos";
                    }
                    if (("-".equals(datosJugadorasTemp[j][2])) || ("".equals(datosJugadorasTemp[j][2]))) {//NO DORSAL -> -1;
                        datosJugadorasTemp[j][2] = "-1";
                    }
                    if (("-".equals(datosJugadorasTemp[j][3])) || ("".equals(datosJugadorasTemp[j][3]))) { //NO DATOS NACIMIENTO-> no_fnac;
                        datosJugadorasTemp[j][3] = "no_fnac";
                    }
                    if (("-".equals(datosJugadorasTemp[j][4])) || ("".equals(datosJugadorasTemp[j][4]))) { //NO NACIONALIDAD-> no_nac;
                        datosJugadorasTemp[j][4] = "no_nac";
                    }
                    if (("-".equals(datosJugadorasTemp[j][5])) || ("".equals(datosJugadorasTemp[j][5]))) { //NO ALTURA -> -1;
                        datosJugadorasTemp[j][5] = "-1";
                    }

                    Jugadora jugadoraTemp = new Jugadora(datosJugadorasTemp[j][0], datosJugadorasTemp[j][1], Integer.parseInt(datosJugadorasTemp[j][2]), datosJugadorasTemp[j][3], datosJugadorasTemp[j][4], Float.parseFloat(datosJugadorasTemp[j][5]));
                    equipos.get(i).jugadorasEq.add(jugadoraTemp);
                }
            } catch (Exception ex) {
                err.printf("Error al cargar jugadoras: %s\n", ex.toString());
                System.exit(-1);
            }
        }
    }

    //---------------------------------------------Menú 2----------------------------------------------------------------------------------------
    //                                                                           //----------------CONTROL DE TIPO DE DATOS-----------------//
    public void modificarJugadora(int eqSelec, int jugSelec, String opcionSelec, String campoNuevo, int campoNuevoInt, float campoNuevoFloat) {
        switch (opcionSelec) {
            case "1" -> {
                //Posición
                String posicionJug = equipos.get(eqSelec).jugadorasEq.get(jugSelec).posicionJug;
                if ("null".equals(posicionJug)) {
                    System.out.println("La jugadora no tenía posición asignada");
                } else {
                    System.out.printf("Posicion Antigua: %s\n", posicionJug);
                }
                equipos.get(eqSelec).jugadorasEq.get(jugSelec).posicionJug = campoNuevo;
                System.out.println("Jugadora Actualizada!!\n");
            }
            case "2" -> {
                //Dorsal
                int dorsalJug = equipos.get(eqSelec).jugadorasEq.get(jugSelec).dorsalJug;
                if (dorsalJug == -1) {
                    System.out.println("La jugadora no tenía dorsal asignado");
                } else {
                    System.out.printf("Dorsal Antiguo: %d\n", dorsalJug);
                }
                equipos.get(eqSelec).jugadorasEq.get(jugSelec).dorsalJug = campoNuevoInt;
                System.out.println("Jugadora Actualizada!!\n");
            }
            case "3" -> {
                //Nacimiento
                String nacimientoJug = equipos.get(eqSelec).jugadorasEq.get(jugSelec).nacimientoJug;
                if ("null".equals(nacimientoJug)) {
                    System.out.println("La jugadora no tenía datos de nacimiento asignados");
                } else {
                    System.out.printf("Nacimiento Antigua: %s\n", nacimientoJug);
                }
                equipos.get(eqSelec).jugadorasEq.get(jugSelec).nacimientoJug = campoNuevo;
                System.out.println("Jugadora Actualizada!!\n");
            }
            case "4" -> {
                //Nacionalidad
                String nacionalidadJug = equipos.get(eqSelec).jugadorasEq.get(jugSelec).nacionalidadJug;
                if ("null".equals(nacionalidadJug)) {
                    System.out.println("La jugadora no tenía nacionalidad asignada");
                } else {
                    System.out.printf("Nacionalidad Antigua: %s\n", nacionalidadJug);
                }
                equipos.get(eqSelec).jugadorasEq.get(jugSelec).nacionalidadJug = campoNuevo.toUpperCase();
                System.out.println("Jugadora Actualizada!!\n");
            }
            case "5" -> {
                //Altura
                float alturaJug = equipos.get(eqSelec).jugadorasEq.get(jugSelec).alturaJug;
                if (alturaJug == -1) {
                    System.out.println("La jugadora no tenía altura asignada");
                } else {
                    System.out.printf("Altura Antigua: %g\n", alturaJug);
                }
                equipos.get(eqSelec).jugadorasEq.get(jugSelec).alturaJug = campoNuevoFloat;
                System.out.println("Jugadora Actualizada!!\n");
            }
        }
    }

    public void eliminarJugadora(int eqSelec, int jugSelec) {
        String jugadoraSeleccionada = equipos.get(eqSelec).jugadorasEq.get(jugSelec).toString();
        System.out.printf("Jugadora Seleccionada: \n%s\n", jugadoraSeleccionada);
        equipos.get(eqSelec).jugadorasEq.remove(jugSelec);
        System.out.println("Jugadora Elimimanda!!!");
    }

    public void agregarJugadora(int eqSelec, String nombreJugNueva, String posicionJugNueva, int dorsalJugNueva, String nacimientoJugNueva, String nacionalidadJugNueva, float alturaJugNueva) {
        Jugadora jugadoraTemp = new Jugadora(nombreJugNueva, posicionJugNueva, dorsalJugNueva, nacimientoJugNueva, nacionalidadJugNueva, alturaJugNueva);
        equipos.get(eqSelec).jugadorasEq.add(jugadoraTemp);
        System.out.println("Jugadora Añadida!!!");
    }

    //---------------------------------------------Menú 3----------------------------------------------------------------------------------------
    public void cargarResulJornadas(String jornada, int nJornada) {
        Path pathJornadas = pathEnSubcarpetaEnEscritorio(carpetaGeneral, jornadasResultadosDatosCarpeta, jornada.concat(".txt"));
        try {
            String[][] resulJornadas = OpMat.importFromDisk(pathJornadas.toFile(), "=");
            ArrayList<Partido> partidoAct = jornadas.get(nJornada).partidosJor;
            for (int i = 0; i < resulJornadas.length; i++) {
                partidoAct.get(i).puntosLoc = Integer.parseInt(resulJornadas[i][2]);
                partidoAct.get(i).puntosVis = Integer.parseInt(resulJornadas[i][3]);
                if (nJornada == 0) {
                    Datos_equipo datosEquipoLoc = new Datos_equipo(resulJornadas[i][0], Integer.parseInt(resulJornadas[i][2]), Integer.parseInt(resulJornadas[i][3]));
                    jornadas.get(nJornada).clasificacionJor.add(datosEquipoLoc);
                    Datos_equipo datosEquipoVis = new Datos_equipo(resulJornadas[i][1], Integer.parseInt(resulJornadas[i][3]), Integer.parseInt(resulJornadas[i][2]));
                    jornadas.get(nJornada).clasificacionJor.add(datosEquipoVis);
                } else {
                    for (int j = 0; j < jornadas.get(nJornada - 1).clasificacionJor.size(); j++) {
                        if (jornadas.get(nJornada - 1).clasificacionJor.get(j).nombreEq.equals(resulJornadas[i][0])) {
                            Datos_equipo datosEquipoLoc = new Datos_equipo(resulJornadas[i][0], Integer.parseInt(resulJornadas[i][2]), Integer.parseInt(resulJornadas[i][3]), jornadas.get(nJornada - 1).clasificacionJor.get(j));
                            jornadas.get(nJornada).clasificacionJor.add(datosEquipoLoc);
                        }
                        if (jornadas.get(nJornada - 1).clasificacionJor.get(j).nombreEq.equals(resulJornadas[i][1])) {
                            Datos_equipo datosEquipoVis = new Datos_equipo(resulJornadas[i][1], Integer.parseInt(resulJornadas[i][3]), Integer.parseInt(resulJornadas[i][2]), jornadas.get(nJornada - 1).clasificacionJor.get(j));
                            jornadas.get(nJornada).clasificacionJor.add(datosEquipoVis);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            err.printf("Error al cargar Resultados jornadas: %s\n", ex.toString());
            System.exit(-1);
        }

    }

    public void modificarFechaJor(int jornada, String fechaJornada) {
        jornadas.get(jornada).fechaJor = fechaJornada;
        System.out.println("Fecha de la Jornada actualizada!!");
    }

    public void modificarFechaHoraPartido(String opcion, int equipoSelec, String nuevoCampo, int jornada) {
        String nombEquipo = equipos.get(equipoSelec).nombreEq;
        ArrayList<Partido> partidoSelec = jornadas.get(jornada).partidosJor;

        if ("1".equals(opcion)) {        //FECHA

            for (int i = 0; i < jornadas.get(jornada).partidosJor.size(); i++) {
                if ((partidoSelec.get(i).nombreLoc.equals(nombEquipo)) || (partidoSelec.get(i).nombreVis.equals(nombEquipo))) {
                    System.out.printf("La fecha antigua del partido era: %s\n", partidoSelec.get(i).fechaPar);
                    partidoSelec.get(i).fechaPar = nuevoCampo;
                    System.out.printf("La fecha nueva del partido es: %s\n", partidoSelec.get(i).fechaPar);
                    break;
                }
            }
        } else if ("2".equals(opcion)) { //HORA

            for (int i = 0; i < jornadas.get(jornada).partidosJor.size(); i++) {
                if ((partidoSelec.get(i).nombreLoc.equals(nombEquipo)) || (partidoSelec.get(i).nombreVis.equals(nombEquipo))) {
                    partidoSelec.get(i).horaPar = nuevoCampo;
                    break;
                }
            }
        }
    }

    public void resultadosJornada(int jornada) {
        ArrayList<Partido> partidos = jornadas.get(jornada).partidosJor;
        System.out.printf("\n\n%40s%40s%11s\n", "EQUIPO LOCAL", "EQUIPO VISITANTE", "RESULTADO");
        for (int i = 0; i < jornadas.get(jornada).partidosJor.size(); i++) {
            System.out.printf("%40s%40s%5d %5d\n", partidos.get(i).nombreLoc, partidos.get(i).nombreVis, partidos.get(i).puntosLoc, partidos.get(i).puntosVis);
        }
    }

    public void clasificacionJornada(int jornada) {
        Comparator<Datos_equipo> comparaClasificaciones = new ComparadorClasificaciones();
        ArrayList<Datos_equipo> jornadaAct = jornadas.get(jornada).clasificacionJor;
        jornadaAct.sort(comparaClasificaciones);

        System.out.printf("\n%5s\t%40s\t%5s\t%5s\t%5s\t%5s\t%5s\t%5s\n", "POS", "EQUIPO", "PJ", "PG", "PP", "PF", "PC", "PTS");
        for (int i = 0; i < N_EQUIPOS; i++) {
            System.out.printf("%5d\t%40s\t%5d\t%5d\t%5d\t%5d\t%5d\t%5d\n", i + 1, jornadaAct.get(i).nombreEq, jornadaAct.get(i).partidosJug, jornadaAct.get(i).partidosGanad, jornadaAct.get(i).partidosPerd, jornadaAct.get(i).puntosFavor, jornadaAct.get(i).puntosContra, jornadaAct.get(i).puntosClasificacion);
        }

    }

    //---------------------------------------------Menú 4---------------------------------------------------------------------------------------- 
    public void mostrarJugadorasEquipo(int equipo) {
        Comparator<Jugadora> comparaJugadoras = new ComparadorJugadorasEq();
        ArrayList<Jugadora> jugadoras = equipos.get(equipo).jugadorasEq;
        jugadoras.sort(comparaJugadoras);
        System.out.printf("\n\n%35s\t%10s\t%8s\t%55s\t%25s\t%8s\n", "NOMBRE", "POSICION", "DORSAL", "NACIMIENTO", "NACIONALIDAD", "ALTURA");
        for (int i = 0; i < jugadoras.size(); i++) {
            System.out.printf("%35s\t%10s\t%8d\t%55s\t%25s\t%4gcm\n", jugadoras.get(i).nombreJug, jugadoras.get(i).posicionJug, jugadoras.get(i).dorsalJug, jugadoras.get(i).nacimientoJug, jugadoras.get(i).nacionalidadJug, jugadoras.get(i).alturaJug);
        }
    }

    public void mostrarDatosEquipo() {
        Comparator<Equipo> comparaEquipos = new ComparadorDatosEq();
        equipos.sort(comparaEquipos);
        System.out.printf("\n\n%35s\t%85s\t%12s\t%50s\t%45s\n", "NOMBRE", "DIRECCIÓN", "TELEFONO", "WEBSITE", "EMAIL");
        for (int i = 0; i < equipos.size(); i++) {
            System.out.printf("%35s\t%85s\t%12d\t%50s\t%45s\n", equipos.get(i).nombreEq, equipos.get(i).direccionEq, equipos.get(i).telefonoEq, equipos.get(i).webEq, equipos.get(i).emailEq);
        }

    }

    public void mostrarJugadorasInicial(String lInicial) {
        //Recopila las jugadoras de todos los equipos
        Comparator<Jugadora> comparaJugadora = new ComparadorJugadoraInicial();
        ArrayList<Jugadora> jugadoras = new ArrayList();
        for (int i = 0; i < equipos.size(); i++) {
            for (int j = 0; j < equipos.get(i).jugadorasEq.size(); j++) {
                if (equipos.get(i).jugadorasEq.get(j).nombreJug.startsWith(lInicial)) {
                    jugadoras.add(equipos.get(i).jugadorasEq.get(j));
                }
            }
        }
        jugadoras.sort(comparaJugadora);
        System.out.printf("\n\n%30s\t%10s\t%8s\t%55s\t%15s\t%8s\n", "NOMBRE", "POSICION", "DORSAL", "NACIMIENTO", "NACIONALIDAD", "ALTURA");
        for (int i = 0; i < jugadoras.size(); i++) {
            System.out.printf("%30s\t%10s\t%8d\t%55s\t%15s\t%4gcm\n", jugadoras.get(i).nombreJug, jugadoras.get(i).posicionJug, jugadoras.get(i).dorsalJug, jugadoras.get(i).nacimientoJug, jugadoras.get(i).nacionalidadJug, jugadoras.get(i).alturaJug);
        }
    }

    //---------------------------------------------Menú 5---------------------------------------------------------------------------------------- 
    public void extraerJugadorasEquipo(int eqSelec) {
        String equipoSelec = equipos.get(eqSelec).nombreEq.toUpperCase();
        Path pathJugadorasEquipo = pathEnSubcarpetaEnEscritorio(carpetaGeneral, carpetaExtracciones, equipoSelec.concat(".enc"));
        try {
            FileWriter fw = new FileWriter(pathJugadorasEquipo.toString());
            BufferedWriter bw = new BufferedWriter(fw);
            String encabezado;
            String textoTemp;
            ArrayList<Jugadora> jugadora = equipos.get(eqSelec).jugadorasEq;
            bw.write("**Las jugadoras que no tienen asignados datos numericos aparecen con un '-1'**");
            bw.newLine();
            encabezado = String.format("%30s\t%10s\t%8s\t%55s\t%25s\t%8s", "NOMBRE", "POSICION", "DORSAL", "NACIMIENTO", "NACIONALIDAD", "ALTURA");
            bw.write(encabezado);
            bw.newLine();
            for (int i = 0; i < jugadora.size(); i++) {
                textoTemp = String.format("%30s\t%10s\t%8d\t%55s\t%25s\t%5gcm", jugadora.get(i).nombreJug, jugadora.get(i).posicionJug, jugadora.get(i).dorsalJug, jugadora.get(i).nacimientoJug, jugadora.get(i).nacionalidadJug, jugadora.get(i).alturaJug);
                bw.write(textoTemp);
                bw.newLine();
            }
            bw.close();
        } catch (IOException ex) {
            err.printf("Error en IO:  %s\n", ex.toString());
            System.exit(-1);
        }
    }

    //El archivo final puede no verse correctamente en tabla debido a que el tamaño de las columnas es muy grande para que este alineado
    public void extraerDatosEquipo() {
        Path pathEquipo = pathEnSubcarpetaEnEscritorio(carpetaGeneral, carpetaExtracciones, equiposExtracciones);
        try {
            FileWriter fw = new FileWriter(pathEquipo.toString());
            BufferedWriter bw = new BufferedWriter(fw);
            String encabezado;
            String textoTemp;
            encabezado = String.format("%35s\t%85s\t%12s\t%50s\t%45s", "NOMBRE", "DIRECCIÓN", "TELEFONO", "WEBSITE", "EMAIL");
            bw.write(encabezado);
            bw.newLine();
            for (int i = 0; i < equipos.size(); i++) {
                textoTemp = String.format("%35s\t%85s\t%12d\t%50s\t%45s", equipos.get(i).nombreEq, equipos.get(i).direccionEq, equipos.get(i).telefonoEq, equipos.get(i).webEq, equipos.get(i).emailEq);
                bw.write(textoTemp);
                bw.newLine();
            }
            bw.close();
        } catch (IOException ex) {
            err.printf("Error en IO:  %s\n", ex.toString());
            System.exit(-1);
        }
    }

    public void extraerClasificionHtml(int jornada) {
        Path pathHtml = pathEnSubcarpetaEnEscritorio(carpetaGeneral, carpetaExtracciones, ("fich_html_".concat(String.valueOf(jornada + 1))).concat(".html"));
        try {
            FileWriter fw = new FileWriter(pathHtml.toString());
            BufferedWriter bw = new BufferedWriter(fw);
            String textoTemp;
            bw.write("<HTML><HEAD><H1>CLASIFICACIÓN</H1></HEAD><BODY><TABLE BORDER=1>");
            bw.newLine();
            bw.write("<TR><TD>Puesto</TD><TD>EQUIPO</TD><TD>PJ</TD><TD>PG</TD><TD>PP</TD><TD>PF</TD><TD>PC</TD><TD>PTS</TD><TR>");
            bw.newLine();
            Comparator<Datos_equipo> comparaClasificaciones = new ComparadorClasificaciones();
            ArrayList<Datos_equipo> jornadaAct = jornadas.get(jornada).clasificacionJor;
            jornadaAct.sort(comparaClasificaciones);
            for (int i = 0; i < jornadaAct.size(); i++) {
                textoTemp = String.format("<TR><TD>%d</TD><TD>%s</TD><TD>%d</TD><TD>%d</TD><TD>%d</TD><TD>%d</TD><TD>%d</TD><TD>%d</TD><TR>", i + 1, jornadaAct.get(i).nombreEq, jornadaAct.get(i).partidosJug, jornadaAct.get(i).partidosGanad, jornadaAct.get(i).partidosPerd, jornadaAct.get(i).puntosFavor, jornadaAct.get(i).puntosContra, jornadaAct.get(i).puntosClasificacion);
                bw.write(textoTemp);
                bw.newLine();
            }
            bw.write("</TABLE></BODY></HTML>");
            bw.close();
        } catch (IOException ex) {
            err.printf("Error en IO:  %s\n", ex.toString());
            System.exit(-1);
        }
    }

    //---------------------------------------------------------TODOS LOS COMPARADORES------------------------------------------------------------
    public class ComparadorClasificaciones implements Comparator<Datos_equipo> {

        @Override
        public int compare(Datos_equipo o1, Datos_equipo o2) {
            if (o2.puntosClasificacion - o1.puntosClasificacion == 0) {
                return o2.puntosFavor - o1.puntosFavor;
            }
            return o2.puntosClasificacion - o1.puntosClasificacion;

        }
    }

    public class ComparadorJugadorasEq implements Comparator<Jugadora> {

        @Override
        public int compare(Jugadora j1, Jugadora j2) {
            if (j1.posicionJug.compareTo(j2.posicionJug) == 0) {
                return (int) (j2.alturaJug - j1.alturaJug);
            }
            return j1.posicionJug.compareTo(j2.posicionJug);

        }
    }

    public class ComparadorDatosEq implements Comparator<Equipo> {

        @Override
        public int compare(Equipo e1, Equipo e2) {
            return (int) (e1.telefonoEq - e2.telefonoEq);
        }
    }

    public class ComparadorJugadoraInicial implements Comparator<Jugadora> {

        @Override
        public int compare(Jugadora j1, Jugadora j2) {

            String[] nacimientoJ1 = j1.nacimientoJug.split("/");
            String[] añoJ1 = nacimientoJ1[2].split(" ");
            String[] nacimientoJ2 = j2.nacimientoJug.split("/");
            String[] añoJ2 = nacimientoJ2[2].split(" ");

            //YA TENGO EL DIA EL MES Y EL AÑO SEPARADOS -> DIA: nacimiento[0]  MES: nacimiento[1]  AÑO: año[0]
            if (añoJ1[0].compareTo(añoJ2[0]) == 0) {
                if (nacimientoJ1[1].compareTo(nacimientoJ2[1]) == 0) {
                    if (nacimientoJ1[0].compareTo(nacimientoJ2[0]) == 0) {
                        return 0;
                    }
                    return nacimientoJ1[0].compareTo(nacimientoJ2[0]);
                }
                return nacimientoJ1[1].compareTo(nacimientoJ2[1]);
            }
            return añoJ1[0].compareTo(añoJ2[0]);

        }
    }

//-------------------------------------------Metodos Auxiliares---------------------------------------------------
    public Path pathEnSubcarpetaEnEscritorio(String carpetaPadre, String carpetaHija, String nombreFichero) { //SE AYUDA DE com.coti.tools.Rutas;
        String sistemaOperativo = System.getProperty("os.name");
        Path path;
        if ("Windows".equals(sistemaOperativo)) {
            path = Rutas.pathToFileInFolderOnDesktop(carpetaPadre.concat("\\").concat(carpetaHija), nombreFichero);
        } else {
            path = Rutas.pathToFileInFolderOnDesktop(carpetaPadre.concat("/").concat(carpetaHija), nombreFichero);
        }
        return path;
    }
}
