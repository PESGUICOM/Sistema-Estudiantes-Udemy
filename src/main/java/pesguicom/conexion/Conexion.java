package pesguicom.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    public static Connection getConexion(){
        Connection conexion = null;
        var baseDatos = "estudiantes_db";
        var url = " " + baseDatos; //TU URL
        var usuario = " "; //TU USER
        var password = " "; //TU PASS
        //Cargamos la clase del driver de mysql en memoria
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, password);
        } catch (ClassNotFoundException | SQLException e){
            System.out.println("Ocurrió un error en la conexión: " + e.getMessage());
        }
        return conexion;
    }

    public static void main(String[] args) {
        Connection conexion = Conexion.getConexion();
        if (conexion != null)
            System.out.println("Conexi\u00F3n exitosa: " + conexion);
        else
            System.out.println("Error al conectarse");
    }
}
