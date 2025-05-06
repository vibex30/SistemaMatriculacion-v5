package org.iesalandalus.programacion.matriculacion;
import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.matriculacion.modelo.Modelo;
import org.iesalandalus.programacion.matriculacion.vista.Vista;



public class MainApp {
    public static void main(String[] args) {
        Vista vista = new Vista();
        Modelo modelo = procesarArgumentosFuenteDatos(args);

        Controlador controlador = new Controlador(modelo, vista);
        vista.setControlador(controlador);
        controlador.comenzar();
        controlador.terminar();

    }


    private static Modelo procesarArgumentosFuenteDatos (String[] args){
        Modelo modelo =new Modelo (FactoriaFuenteDatos.MEMORIA);
        for (String argumento:args){
            if (argumento.equalsIgnoreCase("-fdmemoria")){
                modelo=new Modelo(FactoriaFuenteDatos.MEMORIA);
            } else if (argumento.equalsIgnoreCase("-fdmysql")) {
                modelo=new Modelo(FactoriaFuenteDatos.MYSQL);
            }
        }
        return modelo;
//Hay que recorrer el Arrayyyyy
        /*if (args[0].equals("-fdmemoria")){
            return new Modelo(FactoriaFuenteDatos.MEMORIA);

        }else if(args[0].equals("-fdmysql")){
            return new Modelo(FactoriaFuenteDatos.MYSQL);

        }else {
            exit(0);
        }
        return null;*/

    }

}

