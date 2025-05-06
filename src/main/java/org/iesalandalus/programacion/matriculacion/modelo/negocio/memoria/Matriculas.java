package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class Matriculas implements IMatriculas {
    private ArrayList <Matricula> coleccionMatriculas;


    //CONSTRUCTORES
    public Matriculas (){
        this.coleccionMatriculas=new ArrayList<>();

    }

    //GETTER
    public ArrayList <Matricula> get() throws OperationNotSupportedException{
        return copiaProfundamatricula();
    }

    private ArrayList <Matricula> copiaProfundamatricula() throws OperationNotSupportedException{
        ArrayList<Matricula> copiaProfunda=new ArrayList<>();
        for (Matricula matricula : coleccionMatriculas) {
            copiaProfunda.add(new Matricula(matricula));
        }
        return copiaProfunda;
    }


    public int getTamano() {
        return coleccionMatriculas.size();
    }





    public void insertar (Matricula matricula) throws OperationNotSupportedException {
        if (matricula==null){
            throw new NullPointerException("ERROR: No se puede insertar una matrícula nula.");
        }
        if (coleccionMatriculas.contains(matricula)){
            throw new OperationNotSupportedException("Error, la matricula ya esta insertada.");
        }
        coleccionMatriculas.add(new Matricula(matricula));

    }


    public Matricula buscar (Matricula matricula) throws OperationNotSupportedException{
        if (matricula==null){
            throw new NullPointerException("Error, no se puede buscar una matricula nula");
        }
        for (Matricula m:coleccionMatriculas){
            if (m.equals(matricula)){
                return new Matricula(matricula);
            }
        }
        return null;
    }




    public void borrar (Matricula matricula){
        if (matricula==null){
            throw new NullPointerException("Error, no se puede borrar un matricula nula");
        }
        if (!coleccionMatriculas.remove(matricula)){
            throw new IllegalArgumentException("Error, no se puede borrar la matricula");
        }
    }


    public ArrayList<Matricula> get (Alumno alumno) throws OperationNotSupportedException{
        if (alumno==null){
            throw new NullPointerException("Error, el alumno no puede ser nulo");
        }
        ArrayList<Matricula> matriculasAlumno = new ArrayList<>();
        for (Matricula matricula : coleccionMatriculas) {
            if (matricula.getAlumno().equals(alumno)) {
                matriculasAlumno.add(new Matricula(matricula));
            }
        }
        return matriculasAlumno;

    }

    public ArrayList<Matricula> get (String cursoAcademico) throws OperationNotSupportedException{
        if (cursoAcademico==null){
            throw new NullPointerException("Error, el curso académico no puede ser nulo.");
        }
        if (cursoAcademico.isEmpty()){
            throw new IllegalArgumentException("Error, el curso no puede estar vacío.");
        }
        ArrayList<Matricula> matriculasCurso = new ArrayList<>();
        for (Matricula matricula : coleccionMatriculas) {
            if (matricula.getCursoAcademico().equals(cursoAcademico)) {
                matriculasCurso.add(new Matricula(matricula));
            }
        }
        return matriculasCurso;
    }

    public ArrayList<Matricula> get (CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: El ciclo formativo no puede ser nulo.");
        }

        ArrayList<Matricula> matriculasCicloFormativo = new ArrayList<>();
        for (Matricula matricula : coleccionMatriculas) {
            for (Asignatura asignatura : matricula.getColeccionAsignaturas()) {
                if (asignatura.getCicloFormativo().equals(cicloFormativo)) {
                    matriculasCicloFormativo.add(new Matricula(matricula));
                    break;
                }
            }
        }
        return matriculasCicloFormativo;
    }

    @Override
    public void comenzar() {

    }

    @Override
    public void terminar() {

    }
}
