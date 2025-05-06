package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class Matriculas implements IMatriculas {
    private ArrayList<Matricula> coleccionMatriculas;
    private static Connection conexion;
    private static Matriculas instancia;

    // Constructor
    public Matriculas() {
        this.coleccionMatriculas = new ArrayList<>();
        comenzar();
    }

    // Patrón Singleton
    public static Matriculas getInstancia() {
        if (instancia == null) {
            instancia = new Matriculas();
        }
        return instancia;
    }

    @Override
    public void comenzar() {
        conexion = MySQL.establecerConexion();
    }

    @Override
    public void terminar() {
        instancia = null;
        conexion = null;
    }

    private ArrayList<Asignatura> getAsignaturasMatricula(int idMatricula) {
        ArrayList<Asignatura> lista = new ArrayList<>();
        String consulta = """
                SELECT a.codigo, a.nombre, a.horasAnuales, a.curso, 
                       a.horasDesdoble, a.especialidadProfesorado, a.codigoCicloFormativo
                FROM asignaturasMatricula am
                LEFT JOIN asignatura a ON am.codigo = a.codigo
                WHERE am.idMatricula = ?
                """;

        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, idMatricula);
            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                CicloFormativo cicloFormativo = CiclosFormativos.getInstancia().buscar(new CicloFormativo(
                        resultado.getInt("codigoCicloFormativo"),
                        "familiaFicticia",
                        new GradoE("gradoe", 1, 1),
                        "ficticio",
                        1
                ));

                Asignatura asignatura = new Asignatura(
                        resultado.getString("codigo"),
                        resultado.getString("nombre"),
                        resultado.getInt("horasAnuales"),
                        Curso.valueOf(resultado.getString("curso").toUpperCase()),
                        resultado.getInt("horasDesdoble"),
                        EspecialidadProfesorado.valueOf(resultado.getString("especialidadProfesorado").toUpperCase()),
                        cicloFormativo
                );

                lista.add(asignatura);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener asignaturas: " + e.getMessage());
        }

        return lista;
    }


    public ArrayList<Matricula> get() throws OperationNotSupportedException {
        ArrayList<Matricula> lista = new ArrayList<>();
        String sql = """
                SELECT m.idMatricula, m.fechaMatriculacion, m.cursoAcademico, 
                       a.dni, a.nombre
                FROM matricula m
                LEFT JOIN alumno a ON m.dni = a.dni
                ORDER BY m.fechaMatriculacion ASC, a.nombre ASC
                """;

        try (Statement sentencia = conexion.createStatement();
             ResultSet resultado = sentencia.executeQuery(sql)) {

            while (resultado.next()) {
                Alumno alumno = Alumnos.getInstancia().buscar(
                        new Alumno("ficticio", resultado.getString("dni"), "658696365", "ficticio@fic.com", LocalDate.of(2000, 1, 1))
                );

                Matricula matricula = new Matricula(
                        resultado.getInt("idMatricula"),
                        resultado.getString("cursoAcademico"),
                        resultado.getDate("fechaMatriculacion").toLocalDate(),
                        alumno,
                        getAsignaturasMatricula(resultado.getInt("idMatricula"))
                );

                lista.add(matricula);
            }

        } catch (SQLException e) {
            System.out.println("ERROR al obtener las matrículas: " + e.getMessage());
        }

        return lista;
    }

    public int getTamano() {
        int numMatriculas = 0;
        try (Statement statement = conexion.createStatement();
             ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM matricula;")) {

            if (result.next()) {
                numMatriculas = result.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return numMatriculas;
    }

    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede insertar una matrícula nula.");
        }
        if (coleccionMatriculas.contains(matricula)) {
            throw new OperationNotSupportedException("Error, la matricula ya está insertada.");
        }

        String consulta = "INSERT INTO matricula VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            statement.setInt(1, matricula.getIdMatricula());
            statement.setString(2, matricula.getCursoAcademico());
            statement.setDate(3, Date.valueOf(matricula.getFechaMatriculacion()));
            if (matricula.getFechaAnulacion() != null) {
                statement.setDate(4, Date.valueOf(matricula.getFechaAnulacion()));
            } else {
                statement.setNull(4, Types.DATE);
            }
            statement.setString(5, matricula.getAlumno().getDni());
            statement.executeUpdate();

            insertarAsignaturasMatricula(matricula.getIdMatricula(), matricula.getColeccionAsignaturas());
            coleccionMatriculas.add(new Matricula(matricula));

        } catch (SQLException e) {
            System.out.println("Error al insertar matrícula: " + e.getMessage());
        }
    }

    private void insertarAsignaturasMatricula(int idMatricula, ArrayList<Asignatura> coleccionAsignaturas) {
        String consulta = "INSERT INTO asignaturasMatricula (idMatricula, codigo) VALUES (?, ?)";

        for (Asignatura asignatura : coleccionAsignaturas) {
            try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
                statement.setInt(1, idMatricula);
                statement.setString(2, asignatura.getCodigo());
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error al insertar asignaturas de la matrícula: " + e.getMessage());
            }
        }
    }

    public Matricula buscar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("Error, no se puede buscar una matrícula nula.");
        }

        Matricula matriculaDevuelta = null;
        String consulta = """
                SELECT m.idMatricula, m.cursoAcademico, m.fechaMatriculacion, m.fechaAnulacion,
                       a.nombre, a.dni, a.correo, a.telefono, a.fechaNacimiento
                FROM matricula m
                JOIN alumno a ON m.dni = a.dni
                WHERE m.idMatricula = ?
                """;

        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            statement.setInt(1, matricula.getIdMatricula());

            ResultSet resultado = statement.executeQuery();
            if (resultado.next()) {
                Alumno alumno = new Alumno(
                        resultado.getString("nombre"),
                        resultado.getString("dni"),
                        resultado.getString("correo"),
                        resultado.getString("telefono"),
                        resultado.getDate("fechaNacimiento").toLocalDate()
                );

                matriculaDevuelta = new Matricula(
                        resultado.getInt("idMatricula"),
                        resultado.getString("cursoAcademico"),
                        resultado.getDate("fechaMatriculacion").toLocalDate(),
                        alumno,
                        getAsignaturasMatricula(resultado.getInt("idMatricula"))
                );

                if (resultado.getDate("fechaAnulacion") != null) {
                    matriculaDevuelta.setFechaAnulacion(resultado.getDate("fechaAnulacion").toLocalDate());
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar matrícula: " + e.getMessage());
        }

        return matriculaDevuelta;
    }

    public void borrar(Matricula matricula) {
        if (matricula == null) {
            throw new NullPointerException("Error, no se puede borrar una matrícula nula.");
        }

        if (!coleccionMatriculas.remove(matricula)) {
            throw new IllegalArgumentException("Error, no se puede borrar la matrícula.");
        }

        try (PreparedStatement statement = conexion.prepareStatement("DELETE FROM matricula WHERE idMatricula = ?")) {
            statement.setInt(1, matricula.getIdMatricula());
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al borrar matrícula: " + e.getMessage());
        }
    }

    public ArrayList<Matricula> get(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null) {
            throw new NullPointerException("Error, el alumno no puede ser nulo.");
        }

        ArrayList<Matricula> lista = new ArrayList<>();
        String consulta = """
                SELECT m.idMatricula, m.cursoAcademico, m.fechaMatriculacion, m.fechaAnulacion,
                       a.nombre, a.dni, a.correo, a.telefono, a.fechaNacimiento
                FROM matricula m
                JOIN alumno a ON m.dni = a.dni
                WHERE a.dni = ?
                ORDER BY m.fechaMatriculacion DESC, a.nombre
                """;

        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            statement.setString(1, alumno.getDni());
            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                Alumno a = new Alumno(
                        resultado.getString("nombre"),
                        resultado.getString("dni"),
                        resultado.getString("correo"),
                        resultado.getString("telefono"),
                        resultado.getDate("fechaNacimiento").toLocalDate()
                );

                Matricula matricula = new Matricula(
                        resultado.getInt("idMatricula"),
                        resultado.getString("cursoAcademico"),
                        resultado.getDate("fechaMatriculacion").toLocalDate(),
                        a,
                        getAsignaturasMatricula(resultado.getInt("idMatricula"))
                );

                if (resultado.getDate("fechaAnulacion") != null) {
                    matricula.setFechaAnulacion(resultado.getDate("fechaAnulacion").toLocalDate());
                }

                lista.add(matricula);
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar matrículas del alumno: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public ArrayList<Matricula> get(String cursoAcademico) throws OperationNotSupportedException {
        return null;
    }

    public ArrayList<Matricula> get(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: El ciclo formativo no puede ser nulo.");
        }

        ArrayList<Matricula> lista = new ArrayList<>();
        String consulta = """
                SELECT m.idMatricula, m.cursoAcademico, m.fechaMatriculacion,
                       al.dni, al.nombre, al.correo, al.telefono, al.fechaNacimiento
                FROM matricula m
                JOIN alumno al ON m.dni = al.dni
                JOIN asignaturasMatricula am ON am.idMatricula = m.idMatricula
                JOIN asignatura a ON am.codigo = a.codigo
                WHERE a.codigoCicloFormativo = ?
                GROUP BY m.idMatricula
                """;

        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            statement.setInt(1, cicloFormativo.getCodigo());
            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                Alumno alumno = new Alumno(
                        resultado.getString("nombre"),
                        resultado.getString("dni"),
                        resultado.getString("correo"),
                        resultado.getString("telefono"),
                        resultado.getDate("fechaNacimiento").toLocalDate()
                );

                Matricula matricula = new Matricula(
                        resultado.getInt("idMatricula"),
                        resultado.getString("cursoAcademico"),
                        resultado.getDate("fechaMatriculacion").toLocalDate(),
                        alumno,
                        getAsignaturasMatricula(resultado.getInt("idMatricula"))
                );

                lista.add(matricula);
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar matrículas por ciclo formativo: " + e.getMessage());
        }

        return lista;
    }
}



