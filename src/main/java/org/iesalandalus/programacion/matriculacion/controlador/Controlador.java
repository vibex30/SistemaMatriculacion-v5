package org.iesalandalus.programacion.matriculacion.controlador;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.Modelo;
import org.iesalandalus.programacion.matriculacion.vista.Vista;

import javax.naming.OperationNotSupportedException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Controlador {
    private Modelo modelo;
    private Vista vista;


    public Controlador (Modelo modelo, Vista vista){
        if (modelo==null||vista==null){
            throw new NullPointerException("Error, vista o modelo no puede ser nulo.");
        }
        this.modelo=modelo;
        this.vista=vista;
        vista.setControlador(this);
    }

    public void comenzar(){
        modelo.comenzar();
        vista.comenzar();


    }
    public void terminar(){
        modelo.terminar();
        vista.terminar();


    }
    public void insertar(Alumno alumno) throws OperationNotSupportedException {
        modelo.insertar(alumno);

    }

    public Alumno buscar(Alumno alumno) throws SQLException{

        return modelo.buscar(alumno);

    }

    public void borrar (Alumno alumno) throws OperationNotSupportedException{
        modelo.borrar(alumno);
    }

    public ArrayList<Alumno> getAlumnos() throws OperationNotSupportedException{
            return modelo.getAlumnos();
    }

    public void insertar (Asignatura asignatura)throws OperationNotSupportedException{
        modelo.insertar(asignatura);

    }

    public Asignatura buscar (Asignatura asignatura) throws SQLException{
         return modelo.buscar(asignatura);

    }

    public void borrar (Asignatura asignatura) throws OperationNotSupportedException{
        modelo.borrar(asignatura);

    }

    public ArrayList<Asignatura> getAsignaturas() throws OperationNotSupportedException{
        return modelo.getAsignaturas();

    }

    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException{
        modelo.insertar(cicloFormativo);

    }

    public CicloFormativo buscar (CicloFormativo cicloFormativo)throws SQLException{

        return modelo.buscar(cicloFormativo);

    }

    public void borrar (CicloFormativo cicloFormativo)throws OperationNotSupportedException{

        modelo.borrar(cicloFormativo);


    }

    public ArrayList<CicloFormativo>getCiclosFormativos()throws OperationNotSupportedException{

        return modelo.getCiclosFormativos();

    }

    public void insertar (Matricula matricula) throws OperationNotSupportedException{
        modelo.insertar(matricula);

    }

    public Matricula buscar(Matricula matricula) throws SQLException, OperationNotSupportedException{

        return modelo.buscar(matricula);


    }

    public void borrar (Matricula matricula) throws OperationNotSupportedException{

        modelo.borrar(matricula);



    }

    public ArrayList<Matricula> getMatriculas()throws OperationNotSupportedException{
        return modelo.getMatriculas();
    }

    public ArrayList<Matricula> getMatriculas(Alumno alumno)throws OperationNotSupportedException{
        return modelo.getMatriculas(alumno);
    }

    public ArrayList<Matricula> getMatriculas(CicloFormativo cicloFormativo) throws OperationNotSupportedException{
        return modelo.getMatriculas(cicloFormativo);
    }

    public ArrayList<Matricula> getMatriculas (String cursoAcademico)throws OperationNotSupportedException{
        return modelo.getMatriculas(cursoAcademico);
    }


}

