package presentacion;

import DAO.EstudianteDAO;
import dominio.Estudiante;

import java.util.Scanner;

public class SistemaEstudiantesApp {
    public static void main(String[] args) {
       var salir = false;
       var consola = new Scanner(System.in);
       //Se crea una instancia clase servicio
       var estudianteDao = new EstudianteDAO();
       while (!salir){
           try {
               mostrarMenu();
               salir = ejecutarOpciones(consola, estudianteDao);
           } catch (Exception e){
               System.out.println("Ocurri\u00F3 un error al ejecutar operaci\u00F3n: " + e.getMessage());
           }
           System.out.println();
       } //fin ciclo while
    }

    private static void mostrarMenu(){
        System.out.print("""
               *** Sistema de Estudiantes ***
               1. Listar estudiantes
               2. Buscar estudiante
               3. Agregar estudiante
               4. Modificar estudiante
               5. Eliminar estudiante
               6. Salir
               Elige una opcion:
                """);
    }

    private static boolean ejecutarOpciones(Scanner consola, EstudianteDAO estudianteDAO){
        var opcion = Integer.parseInt(consola.nextLine());
        var salir = false;
        switch (opcion){
            case 1 -> {
                System.out.println("Listado de estudiantes..");
                var estudiantes = estudianteDAO.listarEstudiantes();
                estudiantes.forEach(System.out::println);
            }
            case 2 -> {
                System.out.println("Introduce el ID estudiante a buscar:");
                var idEstudiante = Integer.parseInt(consola.next());
                var estudiante = new Estudiante(idEstudiante);
                var encontrado = estudianteDAO.buscarEstudiantePorId(estudiante);
                if (encontrado){
                    System.out.println("Estudiante encontrado: " + estudiante);
                } else {
                    System.out.println("Estudiante NO encontrado: " + estudiante);
                }
            }
            case 3 -> {
                System.out.println("Agregar estudiante: ");
                System.out.print("Nombre: ");
                var nombre = consola.nextLine();
                System.out.print("Apellido: ");
                var apellido = consola.nextLine();
                System.out.print("Celular: ");
                var celular = consola.nextLine();
                System.out.print("Email: ");
                var email = consola.nextLine();
                //Crear el objeto estudiante (sin el id)
                var estudiante = new Estudiante(nombre, apellido, celular, email);
                var agregado = estudianteDAO.agregarEstudiante(estudiante);
                if (agregado){
                    System.out.println("Estudiante agregado: " + estudiante);
                } else {
                    System.out.println("Estudiante NO agregado: " + estudiante);
                }
            }
            case 4 -> {
                System.out.println("Modificar estudiante: ");
                System.out.println("Id estudiante: ");
                var idEstudiante = Integer.parseInt(consola.nextLine());
                System.out.print("Nombre: ");
                var nombre = consola.nextLine();
                System.out.print("Apellido: ");
                var apellido = consola.nextLine();
                System.out.print("Celular: ");
                var celular = consola.nextLine();
                System.out.print("Email: ");
                var email = consola.nextLine();
                //Crear el objeto estudiante a modificar
                var estudiante = new Estudiante(idEstudiante, nombre, apellido, celular, email);
                var modificado = estudianteDAO.modificarEstudiante(estudiante);
                if (modificado){
                    System.out.println("Estudiante modificado: " + estudiante);
                } else {
                    System.out.println("Estudiante NO modificado: " + estudiante);
                }
            }

            case 5 -> {
                System.out.println("Eliminar estudiante: ");
                System.out.println("Id estudiante: ");
                var idEstudiante = Integer.parseInt(consola.nextLine());
                var estudiante = new Estudiante(idEstudiante);
                var eliminado = estudianteDAO.eliminarEstudiante(estudiante);
                if (eliminado){
                    System.out.println("Estudiante eliminado: " + estudiante);
                } else {
                    System.out.println("Estudiante NO eliminado: " + estudiante);
                }
            }
            case 6 -> {
                System.out.println("Hasta pronto...");
                salir = true;
            }
            default -> System.out.println("Opción no reconocida");
        }
        return salir;
    }
}
