
class Alumno {

    // Atributos del alumno
    String nombre;
    int grado;
    String grupo;

    // Constructor: inicializa los datos del alumno
    public Alumno(String nombre, int grado, String grupo) {
        this.nombre = nombre;
        this.grado = grado;
        this.grupo = grupo;
    }

    // Metodo para mostrar los datos del alumno
    public void mostrarDatos() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Grado: " + grado);
        System.out.println("Grupo: " + grupo);
    }
}

class Reporte {

    // Atributos del reporte
    String descripcion;
    String estrategia;

    // Constructor: inicializa los datos del reporte
    public Reporte(String descripcion, String estrategia) {
        this.descripcion = descripcion;
        this.estrategia = estrategia;
    }

    // Metodo para mostrar el reporte
    public void mostrarReporte() {
        System.out.println("Descripcion: " + descripcion);
        System.out.println("Estrategia: " + estrategia);
    }
}

public class Main {

    public static void main(String[] args) {

        // Crear un objeto de la clase Alumno
        Alumno alumno1 = new Alumno("Estudiante 01", 2, "A");

        // Crear un objeto de la clase Reporte
        Reporte reporte1 = new Reporte(
            "Necesita apoyo para seguir instrucciones",
            "Dar indicaciones claras y revisar sus avances"
        );

        // Utilizar los metodos
        System.out.println("=== EDUSEGUIMIENTO ===");

        System.out.println("\n--- Datos del alumno ---");
        alumno1.mostrarDatos();

        System.out.println("\n--- Reporte individual ---");
        reporte1.mostrarReporte();
    }
}
