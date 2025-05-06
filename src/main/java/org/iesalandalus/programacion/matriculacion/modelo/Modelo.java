package org.iesalandalus.programacion.matriculacion.modelo;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.*;

import javax.naming.OperationNotSupportedException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Modelo {
    private IAlumnos alumnos;
    private IAsignaturas asignaturas;
    private ICiclosFormativos ciclosFormativos;
    private IMatriculas matriculas;
    private IFuenteDatos fuenteDatos;



    public Modelo (FactoriaFuenteDatos factoriaFuenteDatos){
        setFuenteDatos(factoriaFuenteDatos.crear());
        comenzar();
    }

    private void setFuenteDatos(IFuenteDatos fuenteDatos){
        if (fuenteDatos==null){
            throw new NullPointerException("Error, la fuente de datos no puede ser nula");
        }
        this.fuenteDatos=fuenteDatos;
    }


    public void comenzar(){
        alumnos=fuenteDatos.crearAlumnos();
        asignaturas=fuenteDatos.crearAsignaturas();
        ciclosFormativos=fuenteDatos.crearCiclosFormativos();
        matriculas=fuenteDatos.crearMatriculas();
    }


    public void terminar(){
        alumnos.terminar();
        asignaturas.terminar();
        ciclosFormativos.terminar();
        matriculas.terminar();
        System.out.println("Hasta pronto!");

    }
    public void insertar(Alumno alumno) throws OperationNotSupportedException {
        alumnos.insertar(alumno);

    }

    public Alumno buscar(Alumno alumno) throws SQLException{
        if (alumno==null){
            throw new NullPointerException("Error, el alumno no puede ser nulo");
        }
        return alumnos.buscar(alumno);
    }

    public void borrar (Alumno alumno) throws OperationNotSupportedException{
        if (alumno==null){
            throw new NullPointerException("Error, el alumno no puede ser nulo");
        }
        alumnos.borrar(alumno);
    }

    public ArrayList<Alumno> getAlumnos() throws OperationNotSupportedException{
        return alumnos.get();
    }

    public void insertar (Asignatura asignatura)throws OperationNotSupportedException{
        if (asignatura==null){
            throw new NullPointerException("Error, la asig no puede ser nulo");
        }
        asignaturas.insertar(asignatura);

    }

    public Asignatura buscar (Asignatura asignatura)throws SQLException{
        if (asignatura==null){
            throw new NullPointerException("Error, la asigno puede ser nulo");
        }
        return new Asignatura(asignaturas.buscar(asignatura));
    }

    public void borrar (Asignatura asignatura)throws OperationNotSupportedException{
        if (asignatura==null){
            throw new NullPointerException("Error, la asig no puede ser nulo");
        }
        asignaturas.borrar(asignatura);

    }

    public ArrayList<Asignatura> getAsignaturas() throws OperationNotSupportedException{
        return asignaturas.get();
    }

    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException{
        if (cicloFormativo==null){
            throw new NullPointerException("Error, el ciclo no puede ser nulo");
        }
        ciclosFormativos.insertar(cicloFormativo);

    }

    public CicloFormativo buscar (CicloFormativo cicloFormativo)throws SQLException{
        if (cicloFormativo==null){
            throw new NullPointerException("Error, el ciclo no puede ser nulo");
        }
        return new CicloFormativo(ciclosFormativos.buscar(cicloFormativo));
    }

    public void borrar (CicloFormativo cicloFormativo) throws OperationNotSupportedException{
        if (cicloFormativo==null){
            throw new NullPointerException("Error, el ciclo no puede ser nulo");
        }
        ciclosFormativos.borrar(cicloFormativo);

    }

    public ArrayList <CicloFormativo> getCiclosFormativos()throws OperationNotSupportedException{
        return ciclosFormativos.get();
    }

    public void insertar (Matricula matricula) throws OperationNotSupportedException{
        if (matricula==null){
            throw new NullPointerException("Error, la matricula no puede ser nulo");
        }
        matriculas.insertar(matricula);

    }

    public Matricula buscar(Matricula matricula) throws SQLException, OperationNotSupportedException{
        if (matricula==null){
            throw new NullPointerException("Error, la matricula no puede ser nulo");
        }
        return new Matricula(matriculas.buscar(matricula));
    }

    public void borrar (Matricula matricula) throws OperationNotSupportedException{
        if (matricula==null){
            throw new NullPointerException("Error, la matricula no puede ser nulo");
        }
        matriculas.borrar(matricula);

    }

    public ArrayList<Matricula> getMatriculas() throws OperationNotSupportedException{
        return matriculas.get();
    }

    public ArrayList<Matricula> getMatriculas(Alumno alumno)throws OperationNotSupportedException{
        if (alumno==null){
            throw new NullPointerException("Error, el alumno no puede ser nulo");
        }
        return matriculas.get(alumno);
    }

    public ArrayList <Matricula> getMatriculas(CicloFormativo cicloFormativo) throws OperationNotSupportedException{
        if (cicloFormativo==null){
            throw new NullPointerException("Error, el ciclo no puede ser nulo");
        }
        return matriculas.get(cicloFormativo);
    }

    public ArrayList <Matricula> getMatriculas (String cursoAcademico)throws OperationNotSupportedException{
        if (cursoAcademico==null){
            throw new NullPointerException("Error, el curso academico no puede ser nulo");
        }
        return matriculas.get(cursoAcademico);
    }



}
