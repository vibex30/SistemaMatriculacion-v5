package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAsignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.util.ArrayList;

public class Asignaturas implements IAsignaturas {
    private ArrayList<Asignatura> coleccionAsignaturas;
    private Connection conexion;
    private static Asignaturas instancia;



    //CONSTRUCTORES
    public Asignaturas (){
        this.coleccionAsignaturas=new ArrayList<>();
        comenzar();
    }

    private static Asignaturas getInstancia(){
        if (instancia==null){
            instancia=new Asignaturas();
        }
        return instancia;
    }


    @Override
    public void comenzar() {
        conexion= MySQL.establecerConexion();

    }

    @Override
    public void terminar() {
        instancia=null;
        conexion=null;

    }



    private Curso getCurso(String curso){
        return Curso.valueOf(curso.toUpperCase());
    }

    private EspecialidadProfesorado getEspecialidadProfesorado(String especialidad){
        return EspecialidadProfesorado.valueOf(especialidad);
    }


    //GETTER
    public ArrayList <Asignatura>get(){
        ArrayList<Asignatura> lista = new ArrayList<>();

        Statement statement = null;
        try {

            statement = conexion.createStatement();
            ResultSet resultado = statement.executeQuery("Select * from asignatura order by nombre asc;");
            while (resultado.next()) {
                lista.add(new Asignatura(
                        resultado.getString("codigo"),
                        resultado.getString("nombre"),
                        resultado.getInt("horasAnuales"),
                        getCurso(resultado.getString("curso")),
                        resultado.getInt("horasDesdoble"),
                        getEspecialidadProfesorado(resultado.getString("especialidadProfesorado")),
                        (CicloFormativo) resultado.getObject("cicloFormativo")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lista;

    }





    public int getTamano() {
        int numAsignaturas=0;
        Statement statement = null;
        try {

            statement = conexion.createStatement();
            ResultSet result = statement.executeQuery("Select count(*) from asignatura;");
            numAsignaturas=result.getInt("count(*)");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return numAsignaturas;
    }




    public void insertar (Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura==null){
            throw new NullPointerException("ERROR: No se puede insertar una asignatura nula.");
        }

        if (coleccionAsignaturas.contains(asignatura)){
            throw new OperationNotSupportedException("Error, el la asignatura ya está insertada.");
        }


        PreparedStatement statement = null;
        try {

            statement = conexion.prepareStatement("insert into asignatura values(?,?,?,?,?,?,?);");
            statement.setString(1, asignatura.getCodigo());
            statement.setString(2, asignatura.getNombre());
            statement.setInt(3, asignatura.getHorasAnuales());
            statement.setString(4, asignatura.getCurso().toString());
            statement.setInt(5, asignatura.getHorasDesdoble());
            statement.setString(6, asignatura.getEspecialidadProfesorado().toString());
            statement.setInt(7, asignatura.getCicloFormativo().getCodigo());
            statement.executeUpdate("insert into asignatura values(?,?,?,?,?,?,?);");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        coleccionAsignaturas.add(new Asignatura(asignatura));

    }


    public Asignatura buscar (Asignatura asignatura){
        if (asignatura==null){
            throw new NullPointerException("Error, no se puede buscar una Asignatura nula");
        }
        Asignatura asignaturaDevuelta=null;
        PreparedStatement statement = null;
        try {

            statement = conexion.prepareStatement("Select * from asignatura where codigo=? ;");
            statement.setString(1, asignatura.getCodigo());

            ResultSet resultado= statement.executeQuery();
            if (resultado.next()){
                asignaturaDevuelta=new Asignatura(
                        resultado.getString("codigo"),
                        resultado.getString("nombre"),
                        resultado.getInt("horasAnuales"),
                        getCurso(resultado.getString("curso")),
                        resultado.getInt("horasDesdoble"),
                        getEspecialidadProfesorado(resultado.getString("especialidadProfesorado")),
                        (CicloFormativo) resultado.getObject("cicloFormativo"));
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return asignaturaDevuelta;
    }


    public void borrar (Asignatura asignatura){
        if (asignatura==null){
            throw new NullPointerException("Error, no se puede borrar una Asignatura nula");
        }

        if (!coleccionAsignaturas.remove(asignatura)){
            throw new IllegalArgumentException("Error, no se puede borrar la asignatura.");
        }


        PreparedStatement statement;
        try {

            statement=conexion.prepareStatement("DELETE FROM asignatura WHERE codigo = ?;");
            statement.setString(1, asignatura.getCodigo());
            statement.executeQuery();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }


}
