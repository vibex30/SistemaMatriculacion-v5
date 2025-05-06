package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;

import javax.naming.OperationNotSupportedException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ICiclosFormativos {
    void comenzar();

    void terminar();

    ArrayList<CicloFormativo> get() throws OperationNotSupportedException;

    int getTamano() throws SQLException;

    void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException;

    CicloFormativo buscar(CicloFormativo cicloFormativo) throws SQLException;

    void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException;
}
