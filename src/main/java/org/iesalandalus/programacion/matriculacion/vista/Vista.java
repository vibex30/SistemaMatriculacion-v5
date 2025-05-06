package org.iesalandalus.programacion.matriculacion.vista;

import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

public class Vista {
    private Controlador controlador;

    public Vista(){
        Opcion.setVista(this);
    }

    public void setControlador(Controlador controlador){
        if(controlador==null){
            throw new NullPointerException("Error, el controlador no puede ser null.");
        }
        this.controlador=controlador;

    }


    public void comenzar(){

        Opcion opcion=null;

        while (opcion!=Opcion.SALIR){
            try{
                Consola.mostrarMenu();
                opcion=Consola.elegirOpcion();
                opcion.ejecutar();


            }catch (IllegalArgumentException|NullPointerException e){
                System.out.println(e.getMessage());
            }
        }

    }

    public void terminar(){
        System.out.println("¡Hasta luego!");
    }


public void insertarAlumno(){

    try{
        controlador.insertar(Consola.leerAlumno());
        System.out.println("*****************");
        System.out.println("Alumno insertado.");
        System.out.println("*****************");


    }catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e){
        System.out.println(e.getMessage());
    }

}

public void buscarAlumno() {
    try{
        Alumno alumno=Consola.getAlumnoPorDni();
        Alumno buscaAlumno= controlador.buscar(alumno);
        if (buscaAlumno!=null){
            System.out.println(buscaAlumno);
        }else{
            System.out.println("El alumno buscado no existe.");
        }

    }catch (IllegalArgumentException|NullPointerException | SQLException e){
        System.out.println(e.getMessage());
    }


}

public void borrarAlumno() {
    try{
        Alumno alumno=Consola.getAlumnoPorDni();
        controlador.borrar(alumno);
        System.out.println("***************");
        System.out.println("Alumno Borrado.");
        System.out.println("***************");

    }catch (OperationNotSupportedException|IllegalArgumentException|NullPointerException e){
        System.out.println(e.getMessage());
    }

}

public void mostrarAlumnos() {
    try{
        ArrayList<Alumno> coleccionAlumnos=controlador.getAlumnos();
        coleccionAlumnos.sort(Comparator.comparing(Alumno::getNombre, String.CASE_INSENSITIVE_ORDER));
        if (coleccionAlumnos.isEmpty()){
            System.out.println("No hay alumnos para mostrar.");
        }else {
            for (Alumno alumno:coleccionAlumnos){
                if (alumno!=null) {
                    System.out.println(alumno);
                }
            }
        }

    }catch (IllegalArgumentException|NullPointerException | OperationNotSupportedException e){
        System.out.println(e.getMessage());
    }

}
public void insertarAsignatura(){
    try{
        if (controlador.getCiclosFormativos().isEmpty()){
            System.out.println("No se puede insertar una asignatura si no hay un ciclo asociado a ella.");
        }else{
            controlador.insertar(Consola.leerAsignatura(Consola.getCicloFormativoPorCodigo()));
            System.out.println("*******************");
            System.out.println("Asignatura añadida.");
            System.out.println("*******************");        }

    }catch (OperationNotSupportedException|IllegalArgumentException|NullPointerException e){
        System.out.println(e.getMessage());
    }

}
public void buscarAsignatura(){
    try{
        Asignatura asignatura=Consola.getAsignaturaPorCodigo();
        Asignatura buscaAsignatura= controlador.buscar(asignatura);
        if (buscaAsignatura!=null){
            System.out.println(asignatura);
        }else {
            System.out.println("No se ha encontrado la asignatura.");
        }
    }catch (NullPointerException|IllegalArgumentException |SQLException e){
        System.out.println(e.getMessage());
    }


}
public void borrarAsignatura(){
    try{
        Asignatura asignatura=Consola.getAsignaturaPorCodigo();
        controlador.borrar(asignatura);
        System.out.println("******************************");
        System.out.println("La asignatura ha sido borrada.");
        System.out.println("******************************");

    }catch (IllegalArgumentException|NullPointerException | OperationNotSupportedException e){
        System.out.println(e.getMessage());
    }

}
public void mostrarAsignaturas(){

    try{
        ArrayList<Asignatura> coleccionAsignaturas=controlador.getAsignaturas();
        coleccionAsignaturas.sort(Comparator.comparing(Asignatura::getNombre, String.CASE_INSENSITIVE_ORDER));
        if (coleccionAsignaturas.isEmpty()){
            System.out.println("No hay asignaturas para mostrar.");
        }else {
            for (Asignatura asignatura:coleccionAsignaturas){
                if (asignatura!=null) {
                    System.out.println(asignatura);
                }
            }
        }

    }catch (IllegalArgumentException|NullPointerException | OperationNotSupportedException e){
        System.out.println(e.getMessage());
    }

}
public void insertarCicloFormativo(){
    try{
        CicloFormativo cicloFormativo=Consola.leerCicloFormativo();
        controlador.insertar(cicloFormativo);
        System.out.println("**************************");
        System.out.println("Ciclo formativo insertado.");
        System.out.println("**************************");

    }catch (OperationNotSupportedException|IllegalArgumentException|NullPointerException e){
        System.out.println(e.getMessage());
    }


}
public void buscarCicloFormativo(){
    CicloFormativo cicloFormativo=Consola.getCicloFormativoPorCodigo();
    try{

        if (controlador.buscar(cicloFormativo)==null)
            System.out.println("Ciclo no encontrado.");
        else
            System.out.println(controlador.buscar(cicloFormativo));
    }catch (NullPointerException|IllegalArgumentException | SQLException e){
        System.out.println(e.getMessage());
    }

}
public void borrarCicloFormativo(){
    try{
        CicloFormativo cicloFormativo=Consola.getCicloFormativoPorCodigo();
        controlador.borrar(cicloFormativo);
        System.out.println("Se ha borrado el ciclo formativo.");

    }catch (IllegalArgumentException|NullPointerException | OperationNotSupportedException e){
        System.out.println(e.getMessage());
    }

}
public void mostrarCicloFormativo() {
    try{
        ArrayList <CicloFormativo> coleccionCiclos=controlador.getCiclosFormativos();
        coleccionCiclos.sort(Comparator.comparing(CicloFormativo::getNombre, String.CASE_INSENSITIVE_ORDER));
        if (coleccionCiclos.isEmpty()){
            System.out.println("No hay alumnos para mostrar.");
        }else {
            for (CicloFormativo cicloFormativo:coleccionCiclos){
                if (cicloFormativo!=null) {
                    System.out.println(cicloFormativo);
                }
            }
        }

    }catch (IllegalArgumentException|NullPointerException | OperationNotSupportedException e){
        System.out.println(e.getMessage());
    }

}
public void insertarMatricula(){
    try{
        Alumno alumno = Consola.getAlumnoPorDni();
        Alumno a = controlador.buscar(alumno);
        if (a == null) {
            System.out.println("No se encuentra el alumno.");
            return;
        }
        System.out.println("Asignaturas de la matricula:");
        ArrayList<Asignatura> matriculaAsignaturas = Consola.elegirAsignaturasMatricula(controlador.getAsignaturas());
        System.out.println("Datos de la matricula:");
        Matricula matricula = Consola.leerMatricula(a, matriculaAsignaturas);
        controlador.insertar(matricula);
        System.out.println("**********************************");
        System.out.println("Matricula insertada correctamente.");
        System.out.println("**********************************");

    } catch (OperationNotSupportedException|IllegalArgumentException|NullPointerException | SQLException e){
        System.out.println(e.getMessage());
    }

}
public void buscarMatricula(){
    try{
        Matricula matricula=Consola.getMatriculaPorIdentificador();
        Matricula buscaMatricula= controlador.buscar(matricula);
        if (buscaMatricula!=null){
            System.out.println(matricula);
        }else {
            System.out.println("No se ha encontrado la matrícula.");
        }
    }catch (OperationNotSupportedException|NullPointerException|IllegalArgumentException | SQLException e){
        System.out.println(e.getMessage());
    }

}
public void anularMatricula(){
    try{
        mostrarMatriculas();
        Matricula matriculaF=Consola.getMatriculaPorIdentificador();
        Matricula matricula=controlador.buscar(matriculaF);

        if (matricula==null){
            System.out.println("No se ha encontrado la matrícula.");
        }else{
            matricula.setFechaAnulacion(Consola.leerFecha("Introduzca la fecha de anulación: "));
            System.out.println("Matrícula anulada con fecha de: "+matricula.getFechaAnulacion());
        }

    }catch (OperationNotSupportedException|IllegalArgumentException|NullPointerException | SQLException e){
        System.out.println(e.getMessage());
    }

}
public void mostrarMatriculas(){
    try{
        ArrayList<Matricula> coleccionMatriculas=controlador.getMatriculas();

        if (coleccionMatriculas.isEmpty()){
            System.out.println("No hay matrículas para mostrar.");
        }else {
            coleccionMatriculas.sort(Comparator.comparing(Matricula::getFechaAnulacion).thenComparing(m->m.getAlumno().getNombre(), String.CASE_INSENSITIVE_ORDER));
            for (Matricula matricula:coleccionMatriculas){
                if (matricula!=null) {
                    System.out.println(matricula);
                }
            }
        }

    }catch (OperationNotSupportedException|IllegalArgumentException|NullPointerException e){
        System.out.println(e.getMessage());
    }

}
public void mostrarMatriculasPorAlumno(){
    try{
        Alumno alumno=Consola.getAlumnoPorDni();
        ArrayList<Matricula> matriculasDelAlumno=controlador.getMatriculas(alumno);

        if (matriculasDelAlumno.isEmpty()){
            System.out.println("No hay matrículas para este alumno.");
        }else {
            matriculasDelAlumno.sort(Comparator.comparing(Matricula::getFechaAnulacion).thenComparing(m->m.getAlumno().getNombre(), String.CASE_INSENSITIVE_ORDER));
            for (Matricula matricula: matriculasDelAlumno){
                if (matricula!=null){
                    System.out.println(matricula);
                }
            }

        }
    }catch (OperationNotSupportedException|IllegalArgumentException|NullPointerException e){
        System.out.println(e.getMessage());
    }


}
public void mostrarMatriculasPorCicloFormativo(){
    try{
        CicloFormativo ciclo=Consola.getCicloFormativoPorCodigo();
        ArrayList<Matricula> matriculasPorCiclo=controlador.getMatriculas(ciclo);


        if (matriculasPorCiclo.isEmpty()){
            System.out.println("No hay matrículas para este ciclo formativo.");
        }else {
            matriculasPorCiclo.sort(Comparator.comparing(Matricula::getFechaAnulacion).thenComparing(m->m.getAlumno().getNombre(), String.CASE_INSENSITIVE_ORDER));
            for (Matricula matricula: matriculasPorCiclo){
                if (matricula!=null){
                    System.out.println(matricula);
                }
            }

        }
    }catch (OperationNotSupportedException|IllegalArgumentException|NullPointerException e) {
        System.out.println(e.getMessage());
    }


}
public void mostrarMatriculasPorCursoAcademico(){
    try{

        System.out.println("Introduce el curso académico (YY-YY): ");
        String cursoIntroducido = Entrada.cadena();

        ArrayList<Matricula> matriculasPorCurso = controlador.getMatriculas(cursoIntroducido);


        if (matriculasPorCurso.isEmpty()){
            System.out.println("No hay matrículas para este curso académico.");
        }else {
            matriculasPorCurso.sort(Comparator.comparing(Matricula::getFechaAnulacion).thenComparing(m->m.getAlumno().getNombre(), String.CASE_INSENSITIVE_ORDER));
            for (Matricula matricula: matriculasPorCurso){
                if (matricula!=null){
                    System.out.println(matricula);
                }
            }

        }
    }catch (OperationNotSupportedException|IllegalArgumentException|NullPointerException e) {
        System.out.println(e.getMessage());
    }
}


}

