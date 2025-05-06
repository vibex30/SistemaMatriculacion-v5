package org.iesalandalus.programacion.matriculacion.modelo.dominio;

import java.util.Objects;

public class Asignatura {
    public static final int MAX_NUM_HORAS_ANUALES=300;
    public static final int MAX_NUM_HORAS_DESDOBLES=6;
    private static final String ER_CODIGO="\\d{4}";
    private CicloFormativo cicloFormativo;
    private String codigo;
    private String nombre;
    private int horasAnuales;
    private Curso curso;
    private int horasDesdoble;
    private EspecialidadProfesorado especialidadProfesorado;



    //CONSTRUCTORES


    public Asignatura(String codigo, String nombre, int horasAnuales, Curso curso, int horasDesdoble, EspecialidadProfesorado especialidadProfesorado, CicloFormativo cicloFormativo) {

        setCodigo(codigo);
        setNombre(nombre);
        setHorasAnuales(horasAnuales);
        setCurso(curso);
        setHorasDesdoble(horasDesdoble);
        setEspecialidadProfesorado(especialidadProfesorado);
        setCicloFormativo(cicloFormativo);


    }

    public Asignatura(Asignatura asignatura){
        if (asignatura==null){
            throw new NullPointerException("ERROR: No es posible copiar una asignatura nula.");
        }
        setCodigo(asignatura.getCodigo());
        setNombre(asignatura.getNombre());
        setHorasAnuales(asignatura.getHorasAnuales());
        setCurso(asignatura.getCurso());
        setHorasDesdoble(asignatura.getHorasDesdoble());
        setEspecialidadProfesorado(asignatura.getEspecialidadProfesorado());
        setCicloFormativo(asignatura.getCicloFormativo());
    }

    //GETTER Y SETTER
    public CicloFormativo getCicloFormativo() {
        return cicloFormativo;
    }

    public void setCicloFormativo(CicloFormativo cicloFormativo) {
        if(cicloFormativo==null){
            throw new NullPointerException("ERROR: El ciclo formativo de una asignatura no puede ser nulo.");
        }
        this.cicloFormativo = cicloFormativo;
    }

    public String getCodigo() {
        return codigo;
    }

    private void setCodigo(String codigo) {
        if(codigo==null){
            throw new NullPointerException("ERROR: El código de una asignatura no puede ser nulo.");
        }
        if(codigo.isBlank()||codigo.isEmpty()){
            throw new IllegalArgumentException("ERROR: El código de una asignatura no puede estar vacío.");
        }
        if (!codigo.matches(ER_CODIGO)){
            throw new IllegalArgumentException("ERROR: El código de la asignatura no tiene un formato válido.");
        }

        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if(nombre==null){
            throw new NullPointerException("ERROR: El nombre de una asignatura no puede ser nulo.");
        }
        if(nombre.isBlank()||nombre.isEmpty()){
            throw new IllegalArgumentException("ERROR: El nombre de una asignatura no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public int getHorasAnuales() {
        return horasAnuales;
    }

    public void setHorasAnuales(int horasAnuales) {
        if(horasAnuales<=0||horasAnuales>MAX_NUM_HORAS_ANUALES){
            throw new IllegalArgumentException("ERROR: El número de horas de una asignatura no puede ser menor o igual a 0 ni mayor a 300.");
        }
        this.horasAnuales = horasAnuales;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        if (curso==null){
            throw new NullPointerException("ERROR: El curso de una asignatura no puede ser nulo.");
        }
        this.curso = curso;
    }

    public int getHorasDesdoble() {
        return horasDesdoble;
    }

    public void setHorasDesdoble(int horasDesdoble) {
        if (horasDesdoble<0||horasDesdoble>MAX_NUM_HORAS_DESDOBLES){
            throw new IllegalArgumentException("ERROR: El número de horas de desdoble de una asignatura no puede ser menor a 0 ni mayor a 6.");
        }
        this.horasDesdoble = horasDesdoble;
    }

    public EspecialidadProfesorado getEspecialidadProfesorado() {
        return especialidadProfesorado;
    }

    public void setEspecialidadProfesorado(EspecialidadProfesorado especialidadProfesorado) {
        if(especialidadProfesorado==null){
            throw new NullPointerException("ERROR: La especialidad del profesorado de una asignatura no puede ser nula.");
        }
        this.especialidadProfesorado = especialidadProfesorado;
    }




    //EQUALS Y HASHCODE
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Asignatura that)) return false;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }




    //IMPRIMIR
    public String imprimir(){
        return String.format("Código asignatura=%s, nombre asignatura=%s, ciclo formativo=%s",
                codigo,
                nombre,
                cicloFormativo.imprimir());

    }

    //TO STRING
    @Override
    public String toString() {
        return
                "Código=" + codigo +", nombre=" + nombre + ", horas anuales=" + horasAnuales +", curso=" + curso +
                ", horas desdoble=" + horasDesdoble + ", ciclo formativo=Código ciclo formativo=" +cicloFormativo.getCodigo()+ ", nombre ciclo formativo="+ cicloFormativo.getNombre()+
                ", especialidad profesorado=" + especialidadProfesorado;
    }
}
