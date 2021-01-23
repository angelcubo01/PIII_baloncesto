/*
 *  Proyecto en Java creado por D. Ángel Picado Cuadrado
 *         (C) 2020    Universidad de Salamanca
 *         - 70926454C  angel.piccua@usal.es -
 */
package controlador;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.System.err;
import static java.lang.System.out;
import java.nio.file.Path;
import java.util.ArrayList;
import modelo.Equipo;
import modelo.LigaFem;

/**
 *
 * @author angel
 */
public class Controlador {

    LigaFem m = new LigaFem();
    final String carpetaBinarios = "binarios";
    final String archivoBinarios = "datosGuardados.bin";

    public String getCarpetaBinarios() {
        return carpetaBinarios;
    }

    public String getArchivoBinarios() {
        return archivoBinarios;
    }

    public String getCarpetaGeneral() {
        return m.getCarpetaGeneral();
    }

    public void setTemporada(String temporada) {
        m.setTemporada(temporada);
    }

    public int getN_EQUIPOS() {
        return m.getN_EQUIPOS();
    }

    public int getN_JORNADAS() {
        return m.getN_JORNADAS();
    }

    public ArrayList<Equipo> getEquipos() {
        return m.getEquipos();
    }

    //---------------------------------------------Menú 1----------------------------------------------------------------------------------------
    public void cargarJornadas() {
        m.cargarJornadas();
    }

    public void cargarEquipos() {
        m.cargarEquipos();
    }

    public void cargarJugadoras() {
        m.cargarJugadoras();
    }
    //---------------------------------------------Menú 2----------------------------------------------------------------------------------------

    public void modificarJugadora(int eqSelec, int jugSelec, String opcionSelec, String campoNuevo, int campoNuevoInt, float campoNuevoFloat) {
        m.modificarJugadora(eqSelec, jugSelec, opcionSelec, campoNuevo, campoNuevoInt, campoNuevoFloat);
    }

    public void eliminarJugadora(int eqSelec, int jugSelec) {
        m.eliminarJugadora(eqSelec, jugSelec);
    }

    public void agregarJugadora(int eqSelec, String nombreJugNueva, String posicionJugNueva, int dorsalJugNueva, String nacimientoJugNueva, String nacionalidadJugNueva, float alturaJugNueva) {
        m.agregarJugadora(eqSelec, nombreJugNueva, posicionJugNueva, dorsalJugNueva, nacimientoJugNueva, nacionalidadJugNueva, alturaJugNueva);
    }
    //---------------------------------------------Menú 3----------------------------------------------------------------------------------------

    public void modificarFechaJor(int jornada, String fechaJornada) {
        m.modificarFechaJor(jornada - 1, fechaJornada);
    }

    public void modificarFechaHoraPartido(String opcion, int equipoSelec, String nuevoCampo, int jornada) {
        m.modificarFechaHoraPartido(opcion, equipoSelec, nuevoCampo, jornada - 1);
    }

    public void cargarResulJornadas(int jornada) {
        String jornadaLetra = numerosALetras(jornada);
        if ("null".equals(jornadaLetra)) {
            err.println("Se ha producido un error: El metodo de paso de numeros a letras se ha sobrepasado, modifique el método");
            System.exit(-1);
        }
        m.cargarResulJornadas(jornadaLetra.toLowerCase(), jornada - 1);
    }

    public void cargarResulHastaJornadas(int jornada) {
        for (int i = 0; i < jornada; i++) {
            String jornadaLetra = numerosALetras(i + 1);
            if ("null".equals(jornadaLetra)) {
                err.println("Se ha producido un error: El metodo de paso de numeros a letras se ha sobrepasado, modifique el método");
                System.exit(-1);
            }
            m.cargarResulJornadas(jornadaLetra.toLowerCase(), i);
        }
    }

    public void resultadosJornada(int jornada) {
        m.resultadosJornada(jornada - 1);
    }

    public void clasificacionJornada(int jornada) {
        m.clasificacionJornada(jornada - 1);
    }

    //---------------------------------------------Menú 4----------------------------------------------------------------------------------------
    public void mostrarJugadorasEquipo(int eqSelec) {
        m.mostrarJugadorasEquipo(eqSelec);
    }

    public void mostrarDatosEquipo() {
        m.mostrarDatosEquipo();
    }

    public void mostrarJugadorasInicial(String lInicial) {
        m.mostrarJugadorasInicial(lInicial.toUpperCase());
    }

    //---------------------------------------------Menú 5----------------------------------------------------------------------------------------
    public void extraerJugadorasEquipo(int eqSelec) {
        m.extraerJugadorasEquipo(eqSelec);
    }

    public void extraerDatosEquipo() {
        m.extraerDatosEquipo();
    }

    public void extraerClasificacionHtml(int jornada) {
        m.extraerClasificionHtml(jornada - 1);
    }

    //----------------------------------------------------Guardar y leer binarios modelo.--------------------------------------------------------
    public void guardarBinariosModelo() {
        Path pathGuardarBinarios = pathEnSubcarpetaEnEscritorio(m.getCarpetaGeneral(), carpetaBinarios, archivoBinarios);
        FileOutputStream fos;
        BufferedOutputStream bos;
        ObjectOutputStream oos;
        try {
            fos = new FileOutputStream(pathGuardarBinarios.toFile());
            bos = new BufferedOutputStream(fos);
            oos = new ObjectOutputStream(bos);
            oos.writeObject(m);
            oos.close();
        } catch (FileNotFoundException ex) {
            out.printf("No fue posible guardar el archivo : %s \n", ex.toString());
            System.exit(-1);
        } catch (IOException ex) {
            out.printf("Error en IO : %s\n", ex.toString());
            System.exit(-1);
        }
    }

    public void leerBinariosModelo() {
        Path pathLeerBinarios = pathEnSubcarpetaEnEscritorio(m.getCarpetaGeneral(), carpetaBinarios, archivoBinarios);
        FileInputStream fis;
        BufferedInputStream bis;
        ObjectInputStream ois;
        try {
            fis = new FileInputStream(pathLeerBinarios.toFile());
            bis = new BufferedInputStream(fis);
            ois = new ObjectInputStream(bis);
            m = (LigaFem) ois.readObject();
            ois.close();
        } catch (FileNotFoundException ex) {
            out.printf("No fue posible guardar el archivo : s \n", ex.toString());
            System.exit(-1);
        } catch (IOException | ClassNotFoundException ex) {
            out.printf("Error en IO : %s\n", ex.toString());
            System.exit(-1);
        }
    }

    //---------------------------------------------Metodos Auxiliares----------------------------------------------------------------------------
    //Se hace un metodo muy arcaico para salir del paso y probar con los archivos dados, pero 
    //podria ser modificado para abarcar más numeros.     VALIDO DEL 1 AL 20
    private String numerosALetras(int jornada) {
        return switch (jornada) {
            case 1 ->
                "uno";
            case 2 ->
                "dos";
            case 3 ->
                "tres";
            case 4 ->
                "cuatro";
            case 5 ->
                "cinco";
            case 6 ->
                "seis";
            case 7 ->
                "siete";
            case 8 ->
                "ocho";
            case 9 ->
                "nueve";
            case 10 ->
                "diez";
            case 11 ->
                "once";
            case 12 ->
                "doce";
            case 13 ->
                "trece";
            case 14 ->
                "catorce";
            case 15 ->
                "quince";
            case 16 ->
                "dieciseis";
            case 17 ->
                "diecisiete";
            case 18 ->
                "dieciocho";
            case 19 ->
                "diecinueve";
            case 20 ->
                "veinte";
            default ->
                "null";
        };
    }

    public Path pathEnSubcarpetaEnEscritorio(String carpetaPadre, String carpetaHija, String nombreFichero) {
        return m.pathEnSubcarpetaEnEscritorio(carpetaPadre, carpetaHija, nombreFichero);
    }

}
