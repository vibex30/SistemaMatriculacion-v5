package org.iesalandalus.programacion.matriculacion.modelo.dominio;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Alumno {
    private static final String ER_TELEFONO="[0-9]{9}";
    private static final String ER_CORREO="[\\w.-]+@[\\w.-]+\\.[a-zA-Z]+";
    private static final String ER_DNI="([\\d]{8})([\\a-zA-Z])";
    public static final String FORMATO_FECHA="dd/MM/yyyy";
    private static final String ER_NIA="[a-zA-Z]{4}[\\d]{3}";
    private static final int MIN_EDAD_ALUMNADO=16;
    private String nombre;
    private String telefono;
    private String correo;
    private String dni;
    private LocalDate fechaNacimiento;
    private String nia;


    //CONSTRUCTORES
    public Alumno(String nombre, String dni, String correo, String telefono, LocalDate fechaNacimiento) {
        setNombre(nombre);
        setDni(dni);
        setCorreo(correo);
        setTelefono(telefono);
        setFechaNacimiento(fechaNacimiento);
        setNia();

    }

    public Alumno(Alumno alumno){
        if (alumno==null){
            throw new NullPointerException("ERROR: No es posible copiar un alumno nulo.");
        }
        setNombre(alumno.getNombre());
        setDni(alumno.getDni());
        setCorreo(alumno.getCorreo());
        setTelefono(alumno.getTelefono());
        setFechaNacimiento(alumno.getFechaNacimiento());
        setNia(alumno.getNia());
    }



    //GETTER Y SETTER
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        if (nombre==null){
            throw new NullPointerException("ERROR: El nombre de un alumno no puede ser nulo.");
        }
        if (nombre.isEmpty() || nombre.isBlank()){
            throw new IllegalArgumentException("ERROR: El nombre de un alumno no puede estar vacío.");
        }

        this.nombre = formateaNombre(nombre);
    }




    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        if (telefono==null){
            throw new NullPointerException("ERROR: El teléfono de un alumno no puede ser nulo.");
        }
        if (telefono.isEmpty() || telefono.isBlank()){
            throw new IllegalArgumentException("ERROR: El teléfono del alumno no tiene un formato válido.");
        }
        if (!telefono.matches(ER_TELEFONO)){
            throw new IllegalArgumentException("ERROR: El teléfono del alumno no tiene un formato válido.");
        }
        this.telefono = telefono;
    }




    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        if (correo==null){
            throw new NullPointerException("ERROR: El correo de un alumno no puede ser nulo.");
        }
        if (correo.isEmpty() || correo.isBlank()){
            throw new IllegalArgumentException("ERROR: El correo del alumno no tiene un formato válido.");
        }
        if (!correo.matches(ER_CORREO)){
            throw new IllegalArgumentException("ERROR: El correo del alumno no tiene un formato válido.");
        }
        this.correo = correo;
    }




    public String getDni() {
        return dni;
    }
    private void setDni(String dni) {
        if (dni==null){
            throw new NullPointerException("ERROR: El dni de un alumno no puede ser nulo.");
        }
        if (dni.isEmpty() || dni.isBlank()){
            throw new IllegalArgumentException("ERROR: El dni del alumno no tiene un formato válido.");
        }
        if (!dni.matches(ER_DNI)){
            throw new IllegalArgumentException("ERROR: El dni del alumno no tiene un formato válido.");
        }
        if (!comprobarLetraDni(dni)){
            throw new IllegalArgumentException("ERROR: La letra del dni del alumno no es correcta.");
        }
        this.dni = dni;
    }




    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    private void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento==null){
            throw new NullPointerException("ERROR: La fecha de nacimiento de un alumno no puede ser nula.");
        }

        int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        if (edad < MIN_EDAD_ALUMNADO) {
            throw new IllegalArgumentException("ERROR: La edad del alumno debe ser mayor o igual a 16 años.");
        }

        this.fechaNacimiento = fechaNacimiento;
    }




    public String getNia() {
        return nia;
    }


    private void setNia(){
        if (nombre == null || dni == null) {
            throw new NullPointerException("error: No se puede generar el NIA porque el nombre o el DNI son nulos.");
        }
        String caracteresNombre = nombre.toLowerCase().substring(0, 4);
        String ultimosDigitosDni = dni.substring(5, 8);

        this.nia = caracteresNombre + ultimosDigitosDni;

    }

    private void setNia(String nia) {
        if (nia==null){
            throw new NullPointerException("Error, el nia no puede ser nulo.");
        }
        if (nia.isEmpty() || nia.isBlank()){
            throw new IllegalArgumentException("Error: el campo del NIA no puede estar vacío");
        }
        if(!nia.matches(ER_NIA)){
            throw new IllegalArgumentException("Error, el patrón del NIA no es válido");
        }
        this.nia = nia;

    }




    //EQUALS Y HASHCODE
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alumno alumno)) return false;
        return Objects.equals(dni, alumno.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }



    //FORMATEANOMBRE, COMPROBARLETRADNI,GETINICIALES, IMPRIMIR

    /*Debe normalizar un nombre eliminando caracteres en
    blanco de sobra y poniendo en mayúsculas la primera letra de cada palabra y en minúsculas las demás.*/
    private String formateaNombre(String formateaNombre){
        if(formateaNombre==null){
            throw new NullPointerException("Error: el nombre formateado no puede ser null.");
        }
        if(formateaNombre.isBlank()|| formateaNombre.isEmpty()){
            throw new IllegalArgumentException("Error: el nombre no puede estar en blanco.");
        }

        String nombreEnMinusSinEspacios = formateaNombre.toLowerCase().trim();
//todo

        String[] miArray = nombreEnMinusSinEspacios.split("\\s+");
        StringBuilder nombreFormateado=new StringBuilder();

        //poner en mayuscula la primera letra
        for (String letra : miArray)
            if (!letra.isEmpty()) {
                //Primera letra en mayus, resto en minus
                char inicialMayuscula = Character.toUpperCase(letra.charAt(0));
                nombreFormateado.append(inicialMayuscula).append(letra.substring(1)).append(" ");
            }

        return nombreFormateado.toString().trim();
    }



    private boolean comprobarLetraDni (String dni){

        Pattern patronDni= Pattern.compile(ER_DNI);
        Matcher m =patronDni.matcher(dni);
        if (!m.matches())
            throw new IllegalArgumentException("ERROR: La letra del dni del alumno no es correcta.");
        int numeroDni=Integer.parseInt(m.group(1));
        int resultadoDivision=numeroDni%23;
        String[] tablaLetras= {"T","R","W","A","G","M","Y","F","P","D","X","B","N","J","Z","S","Q","V","H","L","C","K","E"};
        if (m.group(2).equalsIgnoreCase(tablaLetras[resultadoDivision]))
            return true;
        return false;
    }



    private String getIniciales(){
        String iniciales = "";
        for (int i = 0; i < nombre.length(); i++) {
            if (i == 0 || nombre.charAt(i - 1) == ' ') {
                iniciales += nombre.charAt(i);
            }
        }

        return iniciales.toUpperCase();
    }




    public String imprimir() {
        return String.format("{Alumno: " + nombre + ", DNI: " + dni + ", Teléfono: " + telefono + ", Correo: " + correo +
                ", Fecha de Nacimiento: " + fechaNacimiento.format(DateTimeFormatter.ofPattern(FORMATO_FECHA)) + ", NIA: " + nia +"}");


    }


    //TO STRING
    @Override
    public String toString() {
        return "Número de Identificación del Alumnado (NIA)="+ nia + " nombre="+ nombre + " ("+getIniciales()+ ")" + ", DNI=" + dni + ", correo="
                + correo + ", teléfono=" + telefono + ", fecha nacimiento=" + fechaNacimiento.format(DateTimeFormatter.ofPattern(FORMATO_FECHA));

    }
}
