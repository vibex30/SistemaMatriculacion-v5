package org.iesalandalus.programacion.matriculacion.vista;


import java.sql.SQLException;

public enum Opcion {
    SALIR ("SALIR"){
        @Override
        public void ejecutar() {



        }
    },
    INSERTAR_ALUMNO("INSERTAR ALUMNO"){

        @Override
        public void ejecutar() {
            vista.insertarAlumno();

        }
    },
    BUSCAR_ALUMNO("BUSCAR ALUMNO"){
        @Override
        public void ejecutar()  {
            vista.buscarAlumno();

        }
    },
    BORRAR_ALUMNO("BORRAR ALUMNO"){
        @Override
        public void ejecutar() {
            vista.borrarAlumno();

        }
    },
    MOSTRAR_ALUMNOS("MOSTRAR ALUMNOS"){
        @Override
        public void ejecutar() {
            vista.mostrarAlumnos();

        }
    },
    INSERTAR_CICLO_FORMATIVO("INSERTAR CICLO FORMATIVO"){
        @Override
        public void ejecutar() {
            vista.insertarCicloFormativo();

        }
    },
    BUSCAR_CICLO_FORMATIVO("BUSCAR CICLO FORMATIVO"){
        @Override
        public void ejecutar() {
            vista.buscarCicloFormativo();
        }
    },
    BORRAR_CICLO_FORMATIVO("BORRAR CICLO FORMATIVO"){
        @Override
        public void ejecutar() {
            vista.borrarCicloFormativo();
        }
    },
    MOSTRAR_CICLOS_FORMATIVOS("MOSTRAR CICLOS FORMATIVOS"){
        @Override
        public void ejecutar() {
            vista.mostrarCicloFormativo();

        }
    },
    INSERTAR_ASIGNATURA("INSERTAR ASIGNATURA"){
        @Override
        public void ejecutar() {
            vista.insertarAsignatura();

        }
    },
    BUSCAR_ASIGNATURA("BUSCAR ASIGNATURA"){
        @Override
        public void ejecutar() {
            vista.buscarAsignatura();

        }
    },
    BORRAR_ASIGNATURA("BORRAR ASIGNATURA"){
        @Override
        public void ejecutar() {
            vista.borrarAsignatura();

        }
    },
    MOSTRAR_ASIGNATURAS("MOSTRAR ASIGNATURAS"){
        @Override
        public void ejecutar() {
            vista.mostrarAsignaturas();

        }
    },
    INSERTAR_MATRICULA("INSERTAR MATRÍCULA"){
        @Override
        public void ejecutar() {
            vista.insertarMatricula();
        }
    },
    BUSCAR_MATRICULA("BUSCAR MATRÍCULA"){
        @Override
        public void ejecutar() {
            vista.buscarMatricula();

        }
    },
    ANULAR_MATRICULA("ANULAR MATRÍCULA"){
        @Override
        public void ejecutar() {
            vista.anularMatricula();

        }
    },
    MOSTRAR_MATRICULAS("MOSTRAR MATRÍCULA"){
        @Override
        public void ejecutar() {
            vista.mostrarMatriculas();
        }
    },
    MOSTRAR_MATRICULAS_ALUMNO("MOSTRAR MATRÍCULAS ALUMNO"){
        @Override
        public void ejecutar() {
            vista.mostrarMatriculasPorAlumno();

        }
    },
    MOSTRAR_MATRICULAS_CICLO_FORMATIVO("MOSTRAR MATRÍCULAS CICLO FORMATIVO"){
        @Override
        public void ejecutar() {
            vista.mostrarMatriculasPorCicloFormativo();

        }
    },
    MOSTRAR_MATRICULAS_CURSO_ACADEMICO("MOSTRAR MATRÍCULAS CURSO ACADÉMICO"){
        @Override
        public void ejecutar() {
            vista.mostrarMatriculasPorCursoAcademico();

        }
    };



    private String cadenaAMostrar;
    private static Vista vista;

    Opcion(String cadenaAMostrar){
        this.cadenaAMostrar=cadenaAMostrar;
    }

    public abstract void ejecutar();

    public static void setVista(Vista vista) {
        if (vista==null){
            throw new NullPointerException("Error, la vista no puede ser nula");
        }
        Opcion.vista = vista;
    }

    @Override
    public String toString() {
        return cadenaAMostrar;
    }
}
