package org.iesalandalus.programacion.matriculacion.modelo.dominio;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class Matricula {
    public static final int MAXIMO_MESES_ANTERIOR_ANULACION=6;
    public static final int MAXIMO_DIAS_ANTERIOR_MATRICULA=15;
    public static final int MAXIMO_NUMERO_HORAS_MATRICULA=1000;
    public static final int MAXIMO_NUMERO_ASIGNATURAS_POR_MATRICULA=10;
    private static final String ER_CURSO_ACADEMICO="\\d{2}-\\d{2}";
    public static final String FORMATO_FECHA="dd/MM/yyyy";
    private int idMatricula;
    private String cursoAcademico;
    private LocalDate fechaMatriculacion;
    private LocalDate fechaAnulacion;
    private Alumno alumno;
    private ArrayList<Asignatura> coleccionAsignaturas;



    //CONSTRUCTORES

    public Matricula(int idMatricula, String cursoAcademico, LocalDate fechaMatriculacion, Alumno alumno, List<Asignatura> coleccionAsignaturas)throws OperationNotSupportedException {
        setIdMatricula(idMatricula);
        setCursoAcademico(cursoAcademico);
        setFechaMatriculacion(fechaMatriculacion);
        setAlumno(alumno);
        setColeccionAsignaturas(coleccionAsignaturas);
        //this.fechaAnulacion=fechaMatriculacion.plusMonths(MAXIMO_MESES_ANTERIOR_ANULACION);
        this.fechaAnulacion= null;


    }

    public Matricula (Matricula matricula) throws OperationNotSupportedException{
        if (matricula==null){
            throw new NullPointerException("ERROR: No es posible copiar una matrícula nula.");
        }
        setIdMatricula(matricula.getIdMatricula());
        setCursoAcademico(matricula.getCursoAcademico());
        setFechaMatriculacion(matricula.getFechaMatriculacion());
        if (matricula.getFechaAnulacion()!=null){
            this.fechaAnulacion=matricula.getFechaAnulacion();
        }
        setAlumno(matricula.getAlumno());
        setColeccionAsignaturas(matricula.getColeccionAsignaturas());
    }


    //GETTER Y SETTER

    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {
        if (idMatricula<=0){
            throw new IllegalArgumentException("ERROR: El identificador de una matrícula no puede ser menor o igual a 0.");
        }
        this.idMatricula = idMatricula;
    }

    public ArrayList<Asignatura> getColeccionAsignaturas() {
        return new ArrayList<>(coleccionAsignaturas);
    }

    public void setColeccionAsignaturas(List<Asignatura> coleccionAsignaturas) throws OperationNotSupportedException{
        if (coleccionAsignaturas==null){
            throw new NullPointerException("ERROR: La lista de asignaturas de una matrícula no puede ser nula.");
        }
        if (coleccionAsignaturas.isEmpty()){
            throw new IllegalArgumentException("Error, no puede haber 0 asignaturas");
        }
        if (coleccionAsignaturas.size()>MAXIMO_NUMERO_ASIGNATURAS_POR_MATRICULA){
            throw new IllegalArgumentException("Error, no puede haber más de 10 asignaturas por matrícula");
        }

        if (superaMaximoNumeroHorasMatricula(coleccionAsignaturas)){
            throw new OperationNotSupportedException("ERROR: No se puede realizar la matrícula ya que supera el máximo de horas permitidas (1000 horas).");
        }

        this.coleccionAsignaturas=new ArrayList<>(coleccionAsignaturas);
    }


    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        if (alumno==null){
            throw new NullPointerException("ERROR: El alumno de una matrícula no puede ser nulo.");
        }
        this.alumno = alumno;
    }


    public LocalDate getFechaAnulacion() {
        return fechaAnulacion;
    }

    public void setFechaAnulacion(LocalDate fechaAnulacion) {
        if (fechaAnulacion==null){
            throw new NullPointerException("ERROR: El alumno de una matrícula no puede ser nulo.");
        }

        if (fechaAnulacion.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("ERROR: La fecha de anulación de una matrícula no puede ser posterior a hoy.");
        }
        if (fechaAnulacion.isAfter(fechaMatriculacion.plusMonths(MAXIMO_MESES_ANTERIOR_ANULACION))){
            throw new IllegalArgumentException("Error, la fecha de la anulacion no puede pasar los 6 meses");
        }
        if (fechaAnulacion.isBefore(fechaMatriculacion)){
            throw new IllegalArgumentException("ERROR: La fecha de anulación no puede ser anterior a la fecha de matriculación.");
        }

        this.fechaAnulacion = fechaAnulacion;
    }

    public LocalDate getFechaMatriculacion() {
        return fechaMatriculacion;
    }

    public void setFechaMatriculacion(LocalDate fechaMatriculacion)throws OperationNotSupportedException {
        if (fechaMatriculacion==null){
            throw new NullPointerException("ERROR: La fecha de matriculación de una mátricula no puede ser nula.");
        }
        if (fechaMatriculacion.isAfter(LocalDate.now().plusDays(MAXIMO_DIAS_ANTERIOR_MATRICULA))) {
            throw new OperationNotSupportedException("ERROR: La fecha de matriculación no puede ser superior a 15 días en el futuro.");
        }
        // Verificar que no sea posterior a la fecha de anulación (si existe)
        if (fechaAnulacion != null && fechaMatriculacion.isAfter(fechaAnulacion)) {
            throw new IllegalArgumentException("ERROR: La fecha de matriculación no puede ser posterior a la de anulación.");
        }
        if (fechaMatriculacion.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("ERROR: La fecha de matriculación no puede ser posterior a hoy.");
        }
        if (fechaMatriculacion.isBefore(LocalDate.now().minusDays(MAXIMO_DIAS_ANTERIOR_MATRICULA))){
            throw new IllegalArgumentException("ERROR: La fecha de matriculación no puede ser anterior a 15 días.");
        }
        this.fechaMatriculacion = fechaMatriculacion;
    }

    public String getCursoAcademico() {
        return cursoAcademico;
    }

    public void setCursoAcademico(String cursoAcademico) {
        if (cursoAcademico==null){
            throw new NullPointerException("ERROR: El curso académico de una matrícula no puede ser nulo.");
        }
        if (cursoAcademico.isEmpty()||cursoAcademico.isBlank()){
            throw new IllegalArgumentException("ERROR: El curso académico de una matrícula no puede estar vacío.");
        }
        if (!cursoAcademico.matches(ER_CURSO_ACADEMICO)){
            throw new IllegalArgumentException("ERROR: El formato del curso académico no es correcto.");
        }

        this.cursoAcademico = cursoAcademico;
    }



    //OTROS MÉTODOS
    /*Crea superaMaximoNumeroHorasMatricula que deberá chequear si el total de
      horas de las asignaturas de la matrícula supera o no el máximo permitido.*/

    private boolean superaMaximoNumeroHorasMatricula (List<Asignatura> asignaturasMatricula){
        if (asignaturasMatricula==null){
            throw new NullPointerException("Error, la lista de asignaturas no puede estar nula");
        }
        int totalHoras=0;
        for (Asignatura asignatura : asignaturasMatricula) {
            if (asignatura != null){
                totalHoras += asignatura.getHorasAnuales();
            }
        }
        return totalHoras > MAXIMO_NUMERO_HORAS_MATRICULA;
    }

    private String asignaturasMatricula(){
        String resultado="Estas son las asignaturas que tiene la matrícula: ";
        for (Asignatura asignatura: coleccionAsignaturas){
            if (asignatura!=null){
                resultado+= asignatura+ "\n";
            }

        }
        return resultado;
    }

    //EQUALS Y HASHCODE

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Matricula matricula)) return false;
        return idMatricula == matricula.idMatricula;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idMatricula);
    }


    //IMPRIMIR
    public String imprimir(){
        return "idMatricula="+idMatricula+", curso académico="+cursoAcademico+", fecha matriculación="+fechaMatriculacion.format(DateTimeFormatter.ofPattern(FORMATO_FECHA)) +
                ", alumno="+"{"+alumno.imprimir()+"}";
    }



    //TO STRING
    @Override
    public String toString() {
        String asignaturasTexto;
        boolean contieneElementosNulos=false;

        for (Asignatura asignatura: coleccionAsignaturas){
            if (asignatura==null){
                contieneElementosNulos=true;
                break;
            }
        }
        if (contieneElementosNulos){
            asignaturasTexto="{ }";
        }else{
            asignaturasTexto=coleccionAsignaturas.toString();
        }

        if (fechaAnulacion != null) {
            return "idMatricula="+idMatricula+", curso académico="+cursoAcademico+", fecha matriculación="+fechaMatriculacion.format(DateTimeFormatter.ofPattern(FORMATO_FECHA)) +
                    ", fecha anulación=" + fechaAnulacion.format(DateTimeFormatter.ofPattern(FORMATO_FECHA))+", alumno="+alumno.imprimir()+", Asignaturas="+ asignaturasTexto;

        }else {
            return "idMatricula="+idMatricula+", curso académico="+cursoAcademico+", fecha matriculación="+fechaMatriculacion.format(DateTimeFormatter.ofPattern(FORMATO_FECHA)) +
                    ", alumno="+alumno.imprimir()+", Asignaturas="+ asignaturasTexto;
        }



    }
}
