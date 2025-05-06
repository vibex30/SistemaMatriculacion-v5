package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;
import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Alumnos implements IAlumnos {
    private ArrayList<Alumno> coleccionAlumnos;
    private Connection conexion;
    private static Alumnos instancia;

    //Se hace públic para que funcione
    public static Alumnos getInstancia(){
        if (instancia==null){
            instancia=new Alumnos();
        }
        return new Alumnos();
    }



    @Override
    public void comenzar(){
        conexion= MySQL.establecerConexion();

    }
    @Override
    public void terminar(){
        instancia=null;
        conexion=null;

    }





    //CONSTRUCTORES
    public Alumnos (){
        this.coleccionAlumnos=new ArrayList<>();
        comenzar();
    }

    //GETTER
    public ArrayList<Alumno>get(){
        ArrayList<Alumno> lista = new ArrayList<>();

        Statement statement = null;
        try {

            statement = conexion.createStatement();
            ResultSet result = statement.executeQuery("Select * from alumno order by dni asc;");
            while (result.next()) {
                lista.add(new Alumno(result.getString("nombre"), result.getString("dni"), result.getString("correo"), result.getString("telefono"), result.getDate("fechaNacimiento").toLocalDate()));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lista;


    }




    public int getTamano() {
        int numAlumnos=0;
        Statement statement = null;
        try {

            statement = conexion.createStatement();
            ResultSet result = statement.executeQuery("Select count(*) from alumno;");
            numAlumnos=result.getInt("count(*)");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return numAlumnos;
    }



    /*
    Se permitirán insertar alumnos no nulos al final de la colección
    sin admitir repetidos.*/

    public void insertar (Alumno alumno) throws OperationNotSupportedException{
        if (alumno==null){
            throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
        }
        if (coleccionAlumnos.contains(alumno)){
            throw new OperationNotSupportedException("Error, el alumno está ya insertado.");
        }


        PreparedStatement statement = null;
        try {

            statement = conexion.prepareStatement("insert into alumno values(?,?,?,?,?);");
            statement.setString(1, alumno.getNombre());
            statement.setString(2, alumno.getTelefono());
            statement.setString(3, alumno.getCorreo());
            statement.setString(4, alumno.getDni().toUpperCase());
            statement.setDate(5, Date.valueOf(alumno.getFechaNacimiento()));
            statement.executeUpdate("insert into alumno values(?,?,?,?,?);");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        coleccionAlumnos.add(new Alumno(alumno));

    }


    /* buscar devolverá un alumno si éste se encuentra en la
    colección y null en caso contrario.*/

    public Alumno buscar (Alumno alumno) {
        if (alumno==null){
            throw new NullPointerException("Error, no se puede buscar un alumno nulo");
        }

        Alumno alumnoDevuelto=null;
        PreparedStatement statement = null;
        try {

            statement = conexion.prepareStatement("Select * from alumno where dni=? ;");
            statement.setString(1, alumno.getDni().toUpperCase());

            ResultSet resultado= statement.executeQuery();
            if (resultado.next()){
                alumnoDevuelto=new Alumno(
                        resultado.getString("nombre"),
                        resultado.getString("dni"),
                        resultado.getString("correo"),
                        resultado.getString("telefono"),
                        resultado.getDate("fechaNacimiento").toLocalDate());
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return alumnoDevuelto;


    }


    public void borrar (Alumno alumno){
        if (alumno==null){
            throw new NullPointerException("Error, no se puede borrar un alumno nulo");
        }
        if (!coleccionAlumnos.remove(alumno)){
            throw new IllegalArgumentException("Error, no se puede borrar el alumno.");
        }

        PreparedStatement statement;
        try {

            statement=conexion.prepareStatement("DELETE FROM alumno WHERE dni = ?;");
            statement.setString(1, alumno.getDni().toUpperCase());
            statement.executeQuery();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
