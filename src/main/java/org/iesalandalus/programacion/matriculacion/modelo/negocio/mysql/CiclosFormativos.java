package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;



import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.ICiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.util.ArrayList;

public class CiclosFormativos implements ICiclosFormativos {
    private ArrayList<CicloFormativo> coleccionCiclosFormativos;
    private Connection conexion;
    private static CiclosFormativos instacia;



    public static CiclosFormativos getInstancia(){
        if (instacia==null){
            instacia=new CiclosFormativos();
        }
        return new CiclosFormativos();
    }


    @Override
    public void comenzar() {
        conexion= MySQL.establecerConexion();
    }

    @Override
    public void terminar() {
        instacia=null;
        conexion=null;

    }





    //CONSTRUCTORES
    public CiclosFormativos (){
        this.coleccionCiclosFormativos=new ArrayList<>();
        comenzar();

    }



    public Grado getGrado(String tipoGrado, String nombreGrado, int numAniosGrado, String modalidad, int numEdiciones){

        if (tipoGrado=="GradoE"){
            return new GradoE(nombreGrado, numAniosGrado, numEdiciones);

        }else {
            return new GradoD(nombreGrado, numAniosGrado, Modalidad.valueOf(modalidad));

        }
    }


    public ArrayList<CicloFormativo> get(){
        ArrayList<CicloFormativo> lista = new ArrayList<>();

        Statement statement = null;
        try {

            statement = conexion.createStatement();
            ResultSet result = statement.executeQuery("Select * from cicloFormativo order by nombre asc;");
            while (result.next()) {
                CicloFormativo ciclo= (new CicloFormativo(result.getInt("codigo"), "Informatica", getGrado("GradoE", "DAW", 1, "PRESENCIAL",3), "daw", 2000));

                lista.add(buscar(ciclo));
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lista;
    }




    public int getTamano() {
        int numCiclos=0;
        Statement statement = null;
        try {

            statement = conexion.createStatement();
            ResultSet result = statement.executeQuery("Select count(*) from cicloFormativo;");
            numCiclos=result.getInt("count(*)");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return numCiclos;
    }




    public void insertar (CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo==null){
            throw new NullPointerException("ERROR: No se puede insertar un ciclo nulo.");
        }
        if (coleccionCiclosFormativos.contains(cicloFormativo)){
            throw new OperationNotSupportedException("Error, el ciclo formativo está ya insertado.");
        }

        PreparedStatement statement = null;
        try {

            statement = conexion.prepareStatement("insert into cicloFormativo values(?,?,?,?,?,?,?,?,?);");
            statement.setInt(1, cicloFormativo.getCodigo());
            statement.setString(2, cicloFormativo.getFamiliaProfesional());
            statement.setString(4, cicloFormativo.getNombre());
            statement.setString(3, cicloFormativo.getGrado().getClass().getSimpleName().toUpperCase());
            statement.setInt(5, cicloFormativo.getHoras());
            statement.setInt(7, cicloFormativo.getGrado().getNumAnios());
            if (cicloFormativo.getGrado() instanceof GradoE){
                statement.setInt(9, ((GradoE) cicloFormativo.getGrado()).getNumEdiciones()) ;

            } else if (cicloFormativo.getGrado() instanceof GradoD){
                statement.setString(8, ((GradoD) cicloFormativo.getGrado()).getModalidad().toString()); ;
            }

            statement.executeUpdate("insert into cicloFormativo values(?,?,?,?,?,?,?,?,?);");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        coleccionCiclosFormativos.add(new CicloFormativo(cicloFormativo));
    }



    public CicloFormativo buscar (CicloFormativo cicloFormativo){
        if (cicloFormativo==null){
            throw new NullPointerException("Error, no se puede buscar un Ciclo Formativo nulo");
        }

        CicloFormativo cicloDevuelto=null;
        PreparedStatement statement = null;
        try {

            statement = conexion.prepareStatement("Select * from cicloFormativo where codigo=? ;");
            statement.setInt(1, cicloFormativo.getCodigo());

            ResultSet resultado= statement.executeQuery();
            if (resultado.next()){
                Grado grado = null;
                String tipoGrado = resultado.getString(3).toUpperCase();

                if ("GradoD".equals(tipoGrado)) {
                    grado = new GradoD(
                            resultado.getString("nombre"),
                            resultado.getInt("numAnios"),
                            Modalidad.valueOf(resultado.getString("modalidad").toUpperCase())
                    );
                } else if ("GradoE".equals(tipoGrado)) {
                    grado = new GradoE(
                            resultado.getString("nombre"),
                            resultado.getInt("numAnios"),
                            resultado.getInt("numEdiciones")
                    );
                } else {
                    throw new SQLException("Error, no existe el tipo de grado");
                }
                cicloDevuelto=new CicloFormativo((resultado.getInt("codigo")),
                        resultado.getString("familiaProfesional"),
                        grado,
                        resultado.getString("nombre"),
                        resultado.getInt("horas")
                );
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cicloDevuelto;
    }


    public void borrar (CicloFormativo cicloFormativo){
        if (cicloFormativo==null){
            throw new NullPointerException("Error, no puede haber un ciclo formativo nulo");
        }
        if (!coleccionCiclosFormativos.remove(cicloFormativo)){
            throw new IllegalArgumentException("Error, no se puede borrar el ciclo formativo.");
        }

        PreparedStatement statement;
        try {

            statement=conexion.prepareStatement("DELETE FROM cicloFormativo WHERE codigo = ?;");
            statement.setInt(1, cicloFormativo.getCodigo());
            statement.executeQuery();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
}
