package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;

import javax.naming.OperationNotSupportedException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IAlumnos {
    void comenzar();

    void terminar();

    ArrayList<Alumno> get() throws OperationNotSupportedException;

    int getTamano() throws SQLException;

    void insertar(Alumno alumno) throws OperationNotSupportedException;

    Alumno buscar(Alumno alumno) throws SQLException;

    void borrar(Alumno alumno) throws OperationNotSupportedException;
}
