package DAO;

import dominio.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static pesguicom.conexion.Conexion.getConexion;

//Interactúa con los datos de la aplicación
//DAO-Data Access Object

public class EstudianteDAO {

    public List<Estudiante> listarEstudiantes(){
      List<Estudiante> estudiantes = new ArrayList<>();
        PreparedStatement ps; //Prepara la sentencia para comunicarse con sql base de datos
        ResultSet rs; //Permite almacenar los resultados obtenidos de la base de datos
        Connection con = getConexion(); //Conectamos con nuestra base de datos
        String sql = "SELECT * FROM estudiante ORDER BY id_estudiante";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            //para iterar todos los registros
            while (rs.next()){
                //Creando objeto en memoria
                var estudiante = new Estudiante();
                //Recuperando los valores de cada columna
                estudiante.setIdEstudiante(rs.getInt("id_estudiante"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setCelular(rs.getString("celular"));
                estudiante.setEmail(rs.getString("email"));
                estudiantes.add(estudiante);
            }
        } catch (SQLException e) {
            System.out.println("Ocurri\u00F3 un error al seleccionar datos: " + e.getMessage());;
        }
        finally {
            try{
              con.close();
            } catch (Exception e){
                System.out.println("Hay un error al cerrar conexi\u00F3n: " + e.getMessage());
            }
        }
        return estudiantes;
    }

    //findById en inglés buscarPorId
    public boolean buscarEstudiantePorId(Estudiante estudiante){
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        String sql = "SELECT * FROM estudiante WHERE id_estudiante = ?";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, estudiante.getIdEstudiante());
            rs = ps.executeQuery();
            if (rs.next()){ //llamando el método next para saber si existe un registro
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setCelular(rs.getString("celular"));
                estudiante.setEmail(rs.getString("email"));
                return true; //devuelve el registro que hay
            }
        } catch (Exception e){
            System.out.println(MessageFormat.format("Ocurrió un error al buscar estudiante: {0}", e.getMessage()));
        }
        finally {
            try{
                con.close();
            } catch (Exception e){
                System.out.println("Ocurrió un error al cerrar conexión: " + e.getMessage());
            }
        }
        return false; //si no hay registro no retorna nada
    }

    //Agregar estudiante
    public boolean agregarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "INSERT INTO estudiante (nombre, apellido, celular, email) " + " VALUES(?, ?, ?, ?)";
        //Parámetros significa el signo de interrogación de cierre que corresponden a los índices anteriores
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, estudiante.getNombre());//recuperamos los parámetros
            ps.setString(2, estudiante.getApellido());
            ps.setString(3, estudiante.getCelular());
            ps.setString(4, estudiante.getEmail());
            ps.execute();
            return true;
        } catch (Exception e){
            System.out.println("Error al agregar estudiante: " + e.getMessage());
        }
        finally {
            try{
                con.close();
            } catch (Exception e){
                System.out.println("Error al cerrar conexi\u00F3n: " + e.getMessage());
            }
        }
        return false;
    }

    public boolean modificarEstudiante(Estudiante estudiante){
       PreparedStatement ps;
       Connection con = getConexion();
       String sql = "UPDATE estudiante SET nombre=?, apellido=?, celular=?, email=? WHERE id_estudiante = ?";
       try {
           ps = con.prepareStatement(sql);
           ps.setString(1, estudiante.getNombre());
           ps.setString(2, estudiante.getApellido());
           ps.setString(3, estudiante.getCelular());
           ps.setString(4, estudiante.getEmail());
           ps.setInt(5, estudiante.getIdEstudiante());
           ps.execute();
           return true;
       } catch (Exception e){
           System.out.println("Error al modificar estudiante: " + e.getMessage());
       }
       finally {
           try {
               con.close();
           } catch (Exception e){
               System.out.println("Error al cerrar conexión: " + e.getMessage());
           }
       }
       return false;
    }

    public  boolean eliminarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "DELETE FROM estudiante WHERE id_estudiante = ?";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, estudiante.getIdEstudiante());
            ps.execute();
            return true;
        } catch (Exception e){
            System.out.println("Error al eliminar estudiante: " + e.getMessage());
        }
        finally {
            try {
                con.close();
            } catch (Exception e){
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        return false;
    }

    //Verificando que funciona el código
    public static void main(String[] args) {
        var estudianteDao = new EstudianteDAO();

        //Agregar estudiante
        //var nuevoEstudiante = new Estudiante("Leonel", "Messi", "521543", "leomessi@gmail.com");
        //var agregado = estudianteDao.agregarEstudiante(nuevoEstudiante);
        //if (agregado){
        //    System.out.println("Estudiante agregado: " + nuevoEstudiante);
        //} else {
        //    System.out.println("No se agregó el estudiante: " + nuevoEstudiante);
        //}

        //Modificamos un estudiante existente (4)
        var estudianteModificar = new Estudiante(4, "Camila", "Cabral", "684212", "camicabral@gmail.com");
        var modificado = estudianteDao.modificarEstudiante(estudianteModificar);
        if (modificado){
            System.out.println("Estudiante modificado: " + estudianteModificar);
        } else {
            System.out.println("No se modificó el estudiante: " + estudianteModificar);
        }

        //Eliminar estudiante (11)
        //var estudianteEliminar = new Estudiante(11);
        //var eliminado = estudianteDao.eliminarEstudiante(estudianteEliminar);
        //if (eliminado){
        //    System.out.println("Estudiante eliminado: " + estudianteEliminar);
        //} else {
        //    System.out.println("No se eliminó estudiante: " + estudianteEliminar);
        //}

        //Listar los estudiantes
        System.out.println("Listado de Estudiantes: ");
        List<Estudiante> estudiantes = estudianteDao.listarEstudiantes();
        estudiantes.forEach(System.out::println);

        //Buscar por Id
        //var estudiante1 = new Estudiante(1);
        //System.out.println("Estudiante antes de la b\u00FAsqueda: " + estudiante1);
        //var encontrado = estudianteDao.buscarEstudiantePorId(estudiante1);
        //if (encontrado){
        //    System.out.println("Estudiante encontrado: " + estudiante1);
        //} else {
        //    System.out.println("No se encontr\u00F3 el estudiante: " + estudiante1.getIdEstudiante());
        //}

    }
}
