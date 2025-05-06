package org.iesalandalus.programacion.matriculacion.vista;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria.Asignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria.CiclosFormativos;
import org.iesalandalus.programacion.utilidades.Entrada;
import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Consola {
    private Consola(){
    }

    public static void mostrarMenu(){
        System.out.println("Menú de opciones:");
        for (Opcion opcion: Opcion.values()){
            System.out.println(opcion.ordinal()+". "+ opcion);
        }
    }

    public static Opcion elegirOpcion() {
        int opcionElegida = -1;

        do {
            System.out.println("Elige una opción: ");
            opcionElegida = Entrada.entero();
        } while (opcionElegida < 0 || opcionElegida >= Opcion.values().length);

        switch (opcionElegida) {
            case 0:
                return Opcion.SALIR;
            case 1:
                return Opcion.INSERTAR_ALUMNO;
            case 2:
                return Opcion.BUSCAR_ALUMNO;
            case 3:
                return Opcion.BORRAR_ALUMNO;
            case 4:
                return Opcion.MOSTRAR_ALUMNOS;
            case 5:
                return Opcion.INSERTAR_CICLO_FORMATIVO;
            case 6:
                return Opcion.BUSCAR_CICLO_FORMATIVO;
            case 7:
                return Opcion.BORRAR_CICLO_FORMATIVO;
            case 8:
                return Opcion.MOSTRAR_CICLOS_FORMATIVOS;
            case 9:
                return Opcion.INSERTAR_ASIGNATURA;
            case 10:
                return Opcion.BUSCAR_ASIGNATURA;
            case 11:
                return Opcion.BORRAR_ASIGNATURA;
            case 12:
                return Opcion.MOSTRAR_ASIGNATURAS;
            case 13:
                return Opcion.INSERTAR_MATRICULA;
            case 14:
                return Opcion.BUSCAR_MATRICULA;
            case 15:
                return Opcion.ANULAR_MATRICULA;
            case 16:
                return Opcion.MOSTRAR_MATRICULAS;
            case 17:
                return Opcion.MOSTRAR_MATRICULAS_ALUMNO;
            case 18:
                return Opcion.MOSTRAR_MATRICULAS_CICLO_FORMATIVO;
            case 19:
                return Opcion.MOSTRAR_MATRICULAS_CURSO_ACADEMICO;


        }

        return Opcion.values()[opcionElegida];
    }


    public static Alumno leerAlumno(){
        String nombre, dni, correo, telefono;
        try{
            do{
                System.out.println("Introduce el nombre del alumno: ");
                nombre=Entrada.cadena();
            }while (nombre.isEmpty());
            do{
                System.out.println("Introduce el dni del alumno: ");
                dni=Entrada.cadena().toUpperCase();
            }while (!dni.matches("\\d{8}[A-z]"));
            do{
                System.out.println("Introduce el correo del alumno: ");
                correo=Entrada.cadena();
            }while (!correo.matches("[\\w.-]+@[\\w.-]+\\.[a-zA-Z]+"));
            do{
                System.out.println("Introduce el teléfono del alumno: ");
                telefono=Entrada.cadena();
            }while(!telefono.matches("[0-9]{9}"));

            LocalDate fechaNac=leerFecha("Introduce la fecha de nacimiento (dd/MM/yyyy):");

            return new Alumno(nombre,
                    dni.toUpperCase(),
                    correo,
                    telefono,
                    fechaNac );
        }catch (IllegalArgumentException|NullPointerException e){
            System.out.println(e.getMessage());
        }
        return null;

    }

    public static Alumno getAlumnoPorDni(){
        System.out.println("Introduce el DNI del alumno: ");
        String dni=Entrada.cadena().toUpperCase();
        if (!dni.matches("\\d{8}[A-z]")){
            throw new IllegalArgumentException("Error, el formato del dni es incorrecto.");
        }

        return new Alumno("Nombre ficticio",
                dni, "correo@ficticio.com",
                "695868497",
                LocalDate.of(1992, 11, 17));

    }

    public static LocalDate leerFecha (String mensaje){

        LocalDate fecha = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        do {
            System.out.println(mensaje);
            String entrada = Entrada.cadena().trim();
            try {
                fecha = LocalDate.parse(entrada, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha no válido.");
            }
        } while (fecha == null);

        return fecha;
    }


    public static TiposGrado leerTiposGrado() {
        int opcion;
        do {
            System.out.println("Selecciona el tipo de Grado:");
            System.out.println("1. Grado D");
            System.out.println("2. Grado E");
            opcion = Entrada.entero();
        } while (opcion < 1 || opcion > 2);
        if (opcion==1){
            return TiposGrado.GRADOD;
        }else
            return TiposGrado.GRADOE;
    }



    public static Modalidad leerModalidad() {
        int opcion;
        do {
            System.out.println("Selecciona la modalidad del grado:");
            System.out.println("1. Presencial");
            System.out.println("2. Semipresencial");
            opcion = Entrada.entero();
        } while (opcion < 1 || opcion > 2);
        if (opcion==1){
            return Modalidad.PRESENCIAL;
        }else
            return Modalidad.SEMIPRESENCIAL;
    }

    public static Grado leerGrado() {
        TiposGrado tipoGrado = leerTiposGrado();
        String nombre;
        int numAnios;
        do {
            System.out.println("Introduce el nombre del grado: ");
            nombre = Entrada.cadena();
        } while (nombre.isEmpty() || nombre.isBlank());

        if (tipoGrado == TiposGrado.GRADOD) {
            Modalidad modalidad = leerModalidad();
            do {
                System.out.println("Introduce la duración en años del grado(2 a 3): ");
                numAnios = Entrada.entero();
            } while (numAnios < 2 || numAnios > 3);
            return new GradoD(nombre,
                    numAnios,
                    modalidad);

        } else{
            System.out.println("Introduce el número de ediciones del grado:");
            int numEdiciones = Entrada.entero();
            return new GradoE(nombre,
                    1,
                    numEdiciones);
        }

    }


    public static CicloFormativo leerCicloFormativo(){

        int codigo, horas;
        String familiaProfesional,nombre;


        try{
            do {
                System.out.println("Introduce el código del Ciclo Formativo, debe de tener 4 dígitos: ");
                codigo=Entrada.entero();
            }while (codigo< 1000||codigo>9999);

            do {
                System.out.println("Introduce la familia profesional del ciclo formativo: ");
                familiaProfesional=Entrada.cadena();
            }while (familiaProfesional==null||familiaProfesional.isEmpty());

            Grado grado=leerGrado();

            do {
                System.out.println("Introduce el nombre del ciclo formativo: ");
                nombre=Entrada.cadena();
            }while (nombre==null||nombre.isEmpty());

            do {
                System.out.println("Introduce las horas del ciclo formativo (Max 2000): ");
                horas=Entrada.entero();
            }while (horas<=0||horas>CicloFormativo.MAXIMO_NUMERO_HORAS);

            return new CicloFormativo(codigo,
                    familiaProfesional,
                    grado,
                    nombre,
                    horas);


        }catch (NullPointerException|IllegalArgumentException e){
            System.out.println(e.getMessage());;
        }
        return null;

    }

    public static void mostrarCiclosFormativos(ArrayList<CiclosFormativos> cicloFormativo){
        if (cicloFormativo==null)
            throw new NullPointerException("Error, El ciclo formativo no puede ser null.");
        if (cicloFormativo.isEmpty()){
            System.out.println("No hay ciclos para mostrar.");
            return;

        }
        System.out.println("Estos son los ciclos existentes: ");
        for (CiclosFormativos ciclos:cicloFormativo){
            if (ciclos!=null){
                System.out.println("- "+ciclos);
            }
        }
    }

    public static CicloFormativo getCicloFormativoPorCodigo(){

        int codigo;
        try{
            String familiaProfesional="Familia ficticia";
            Grado grado=new GradoD("Grado ficticio", 2, Modalidad.PRESENCIAL);
            String nombre="ficticio";
            int horas=1;
            do {
                System.out.println("Introduce el código del ciclo formativo: ");
                codigo = Entrada.entero();

            } while (codigo<1000 || codigo>9999);
            return new CicloFormativo(codigo,
                    familiaProfesional,
                    grado,
                    nombre,
                    horas);


        }catch (NullPointerException|IllegalArgumentException e) {
            System.out.println(e.getMessage());;

        }
        return null;
    }

    public static Curso leerCurso(){
        int opcion;
        do {
            System.out.println("Selecciona un curso de la lista: ");
            for (Curso curso: Curso.values()){
                System.out.println(curso.imprimir());
            }
            opcion=Entrada.entero();
        }while (opcion<=0 || opcion> Curso.values().length);
        return Curso.values()[opcion-1];
    }

    public static EspecialidadProfesorado leerEspecialidadProfesorado(){
        int opcion;
        do {
            System.out.println("Selecciona una especialidad del profesorado de la lista: ");
            for (EspecialidadProfesorado especialidad: EspecialidadProfesorado.values()){
                System.out.println(especialidad);
            }
            opcion=Entrada.entero();
        }while (opcion<=0 || opcion> EspecialidadProfesorado.values().length);
        return EspecialidadProfesorado.values()[opcion-1];
    }

    public static Asignatura leerAsignatura (CicloFormativo cicloFormativo){
        if (cicloFormativo==null){
            throw new NullPointerException("Los ciclos formativos no pueden ser nulos.");
        }

        int horasAnuales, horasDesdoble;
        String nombre, codigo;

        System.out.println("Introduce los datos siguientes datos de la asignatura...");
        try{
            do{
                System.out.println("Introduce el código de la asignatura. Mínimo 4 dígitos: ");
                codigo=Entrada.cadena();

            }while (codigo==null||!codigo.matches("\\d{4}")||codigo.isEmpty());

            do{
                System.out.println("Introduce el nombre de la asignatura: ");
                nombre=Entrada.cadena();
            }while (nombre==null||nombre.isEmpty());

            do{
                System.out.println("Introduce las horas anuales, siendo el máximo 300: ");
                horasAnuales=Entrada.entero();
            }while (horasAnuales<=0||horasAnuales>Asignatura.MAX_NUM_HORAS_ANUALES);

            Curso curso=leerCurso();

            do {
                System.out.println("Introduce las horas de desdoble, siendo el máximo 6: ");
                horasDesdoble=Entrada.entero();
            }while (horasDesdoble<0||horasDesdoble>Asignatura.MAX_NUM_HORAS_DESDOBLES);

            EspecialidadProfesorado especialidad= leerEspecialidadProfesorado();


            return new Asignatura(codigo,
                    nombre,
                    horasAnuales,
                    curso,
                    horasDesdoble,
                    especialidad,
                    cicloFormativo);
        }catch (NullPointerException|IllegalArgumentException e){
            System.out.println(e.getMessage());;
        }
        return null;
        
    }

    public static Asignatura getAsignaturaPorCodigo(){
        String codigo;
        try{
            do{
                System.out.println("Introduce el código de la asignatura: ");
                codigo=Entrada.cadena();
            }while (codigo==null||!codigo.matches("\\d{4}"));
            return new Asignatura(codigo,
                    "nombreFicticioAsig",
                    1000, Curso.PRIMERO,
                    6,
                    EspecialidadProfesorado.FOL,
                    getCicloFormativoPorCodigo());

        }catch (IllegalArgumentException |NullPointerException e){
            System.out.println(e.getMessage());;
        }
        return null;

    }

    private static void mostrarAsignaturas(ArrayList <Asignaturas> asignaturas) {
        if (asignaturas == null) {
            throw new NullPointerException("Error, las asignaturas no pueden ser nulas.");
        }

        if (asignaturas.isEmpty()) {
            System.out.println("No hay asignaturas para mostrar.");
            return;
        }

        for (Asignaturas asignatura : asignaturas) {
            if (asignatura != null) {
                System.out.println("- " + asignatura);
            }
        }
    }

    private static boolean asignaturaYaMatriculada(ArrayList<Asignatura> asignaturasMatricula, Asignatura asignatura){
        if (asignaturasMatricula==null){
            throw new NullPointerException("Error, las asignaturas no pueden ser nulas");
        }
        if (asignatura==null){
            throw new NullPointerException("Error, las asignaturas1 no pueden ser nulas");
        }

        boolean encontrada=false;

        for (Asignatura asignatura1: asignaturasMatricula){
            if (asignatura1.equals(asignatura)){
                encontrada=true;
                break;
            }
        }
        if (encontrada){
            System.out.println("La asignatura se ha encontrado en la lista.");
        }else {
            System.out.println("La asignatura no ha sido encontrada.");
        }
        return encontrada;

    }

    /*Crea el méto do leerMatricula que nos pedirá los datos correspondientes a una matrícula y devolverá un objeto
    instancia de dicha clase en el caso que los datos introducidos sean correctos o propague la excepción
    correspondiente en caso contrario. Hay que tener en cuenta, que en la misma matrícula no puede aparecer más de una
    vez la misma asignatura.*/

    public static Matricula leerMatricula (Alumno alumno, ArrayList<Asignatura> asignaturas) throws OperationNotSupportedException {
        if (alumno==null|| asignaturas==null){
            throw new NullPointerException("Error, los alumnos o las asignaturas no pueden ser nulos.");
        }
        int idMatricula;
        String cursoAcademico;
        LocalDate fechaMatriculacion;
        Matricula matricula;

        try{
            do{
                System.out.println("Introduce el ID de la matrícula.");
                idMatricula = Entrada.entero();
            }while(idMatricula<=0);

            do{
                System.out.println("Introduce el curso académico (YY-YY).");
                cursoAcademico = Entrada.cadena();
            }while (cursoAcademico.isEmpty()||!cursoAcademico.matches("\\d{2}-\\d{2}"));

            String fecha= "Introduce la fecha de matriculación (dd/MM/yyyy): ";
            fechaMatriculacion = leerFecha(fecha);
            /*if (fechaMatriculacion.isAfter(LocalDate.now())){
                throw new IllegalArgumentException("Error, la fehca de matriculación no puede ser posterior a hoy.");
            }*/

            matricula = new Matricula (idMatricula,
                    cursoAcademico,
                    fechaMatriculacion,
                    alumno,
                    asignaturas);

            return matricula;

        }catch (NullPointerException|IllegalArgumentException |OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static Matricula getMatriculaPorIdentificador() throws OperationNotSupportedException{
        int idMatricula;
        String cursoAcademico= "24-25";
        LocalDate fechaMatriculacion = LocalDate.now();
        Alumno alumno = new Alumno("Nombre ficticio", "11111111H", "correo@ficticio.es", "654565656",
                LocalDate.of(1993, 10, 05));
        try{
            do{
                System.out.println("Introduce el identificador de la matrícula: ");
                idMatricula=Entrada.entero();
            }while (idMatricula<=0);

            /*LocalDate fecha=leerFecha("Introduce la fecha de matriculación (dd/MM/yyyy): ");
            Alumno alumno= getAlumnoPorDni();*/
            return new Matricula(idMatricula,
                    cursoAcademico,
                    fechaMatriculacion,
                    alumno,
                    new Asignaturas().get());

        }catch (OperationNotSupportedException|NullPointerException|IllegalArgumentException e){
            System.out.println(e.getMessage());;
        }
        return null;

    }

    public static ArrayList<Asignatura> elegirAsignaturasMatricula(ArrayList<Asignatura> asignaturas) {
        if (asignaturas == null || asignaturas.isEmpty()) {
            throw new NullPointerException("Error, no hay asignaturas disponibles para elegir.");
        }
        System.out.println("Seleccione las asignaturas para la matrícula: ");

        // Mostrar las asignaturas numeradas
        for (int i = 0; i < asignaturas.size(); i++) {
            System.out.println((i + 1) + ". " + asignaturas.get(i));
        }

        ArrayList<Asignatura> asignaturasElegidas = new ArrayList<>();
        boolean continuar = true;

        while (continuar) {
            System.out.print("Selecciona una asignatura de la lista o pulsa 0 para terminar: ");
            int seleccion = Entrada.entero();

            if (seleccion == 0) {
                continuar = false;
            } else if (seleccion > 0 && seleccion <= asignaturas.size()) {
                Asignatura asignaturaElegida = asignaturas.get(seleccion - 1);

                if (!asignaturasElegidas.contains(asignaturaElegida)) {
                    asignaturasElegidas.add(asignaturaElegida);
                    System.out.println("Asignatura seleccionada: " + asignaturaElegida);
                } else {
                    System.out.println("Esta asignatura ya fue elegida previamente.");
                }
            } else {
                System.out.println("Selección inválida. Intenta de nuevo.");
            }
        }

        return asignaturasElegidas;
    }



}
