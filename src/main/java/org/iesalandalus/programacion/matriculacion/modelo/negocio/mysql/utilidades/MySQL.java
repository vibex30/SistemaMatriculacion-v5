package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {
    private static final String HOST="dbsistemamatriculacion2.cfxcn31yaqke.us-east-1.rds.amazonaws.com";
    private static final String ESQUEMA="sistemamatriculacion";
    private static final String USUARIO="admin";
    private static final String CONTRASENA="sistemamatriculacion-2025";
    public static Connection conexion;


    private MySQL(){

    }

    public static Connection establecerConexion(){
        String conexionUrl="jdbc:mysql://"+ HOST+ "/"+ ESQUEMA;
        try {
            conexion = DriverManager.getConnection(conexionUrl, USUARIO, CONTRASENA);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conexion;
    }

    public static void cerrarConexion(){
        conexion=null;
    }

}
