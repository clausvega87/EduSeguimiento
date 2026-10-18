
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Clase que representa a cada alumno
class Alumno {
    private String nombre;
    private int grado;
    private String grupo;
    private ArrayList<Reporte> reportes;

    // Constructor del alumno
    public Alumno(String nombre, int grado, String grupo) {
        this.nombre = nombre;
        this.grado = grado;
        this.grupo = grupo;
        this.reportes = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String toString() {
        return nombre + " - " + grado + "° " + grupo;
    }

    // Agrega un reporte al historial del alumno
    public void agregarReporte(Reporte reporte) {
        reportes.add(reporte);
    }

    // Devuelve el historial de reportes
    public ArrayList<Reporte> getReportes() {
        return reportes;
    }
}

// Clase que representa un reporte individual
class Reporte {
    private String descripcion;
    private String estrategia;

    // Constructor del reporte
    public Reporte(String descripcion, String estrategia) {
        this.descripcion = descripcion;
        this.estrategia = estrategia;
    }

    public String toString() {
        return "Situacion: " + descripcion
                + "\nEstrategia: " + estrategia;
    }
}

// Clase principal de la aplicacion
public class Main {

    private static ArrayList<Alumno> alumnos = new ArrayList<>();
    private static DefaultListModel<Alumno> modelo =
            new DefaultListModel<>();
    private static JList<Alumno> listaAlumnos =
            new JList<>(modelo);
    private static JTextArea historial = new JTextArea();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> crearVentana());
    }

    // Construye la ventana principal
    private static void crearVentana() {
        JFrame ventana = new JFrame("EduSeguimiento");
        ventana.setSize(650, 450);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

        JLabel titulo = new JLabel(
                "EduSeguimiento - Control docente",
                SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        JButton btnAlumno = new JButton("Registrar alumno");
        JButton btnReporte = new JButton("Nuevo reporte");
        JButton btnHistorial = new JButton("Ver seguimiento");

        JPanel botones = new JPanel(new GridLayout(1, 3, 8, 8));
        botones.add(btnAlumno);
        botones.add(btnReporte);
        botones.add(btnHistorial);

        historial.setEditable(false);
        historial.setLineWrap(true);
        historial.setWrapStyleWord(true);

        JPanel panelIzquierdo = new JPanel(new BorderLayout(5, 5));
        panelIzquierdo.add(new JLabel("Lista de alumnos"),
                BorderLayout.NORTH);
        panelIzquierdo.add(new JScrollPane(listaAlumnos),
                BorderLayout.CENTER);

        JPanel panelCentral = new JPanel(new BorderLayout(8, 8));
        panelCentral.add(titulo, BorderLayout.NORTH);
        panelCentral.add(new JScrollPane(historial),
                BorderLayout.CENTER);
        panelCentral.add(botones, BorderLayout.SOUTH);

        ventana.setLayout(new BorderLayout(10, 10));
        ventana.add(panelIzquierdo, BorderLayout.WEST);
        ventana.add(panelCentral, BorderLayout.CENTER);

        btnAlumno.addActionListener(e -> registrarAlumno());
        btnReporte.addActionListener(e -> crearReporte());
        btnHistorial.addActionListener(e -> verHistorial());

        ventana.setVisible(true);
    }

    // Solicita los datos y crea un alumno
    private static void registrarAlumno() {
        String nombre = JOptionPane.showInputDialog(
                "Nombre del alumno:");

        if (nombre == null || nombre.trim().isEmpty()) {
            return;
        }

        String gradoTexto = JOptionPane.showInputDialog(
                "Grado escolar:");

        if (gradoTexto == null) {
            return;
        }

        try {
            int grado = Integer.parseInt(gradoTexto);

            if (grado < 1 || grado > 12) {
                JOptionPane.showMessageDialog(
                        null, "Ingresa un grado entre 1 y 12.");
                return;
            }

            String grupo = JOptionPane.showInputDialog(
                    "Grupo del alumno:");

            if (grupo == null || grupo.trim().isEmpty()) {
                return;
            }

            Alumno alumno = new Alumno(
                    nombre.trim(), grado, grupo.trim());

            alumnos.add(alumno);
            modelo.addElement(alumno);

            JOptionPane.showMessageDialog(
                    null, "Alumno registrado correctamente.");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    null, "El grado debe ser un numero.");
        }
    }

    // Crea un reporte para el alumno seleccionado
    private static void crearReporte() {
        Alumno alumno = listaAlumnos.getSelectedValue();

        if (alumno == null) {
            JOptionPane.showMessageDialog(
                    null, "Primero selecciona un alumno.");
            return;
        }

        String descripcion = JOptionPane.showInputDialog(
                "Describe la situacion observada:");

        if (descripcion == null || descripcion.trim().isEmpty()) {
            return;
        }

        String estrategia = JOptionPane.showInputDialog(
                "Estrategia de seguimiento:");

        if (estrategia == null || estrategia.trim().isEmpty()) {
            return;
        }

        Reporte reporte = new Reporte(
                descripcion.trim(), estrategia.trim());

        alumno.agregarReporte(reporte);

        JOptionPane.showMessageDialog(
                null, "Reporte guardado correctamente.");
    }

    // Muestra los reportes del alumno seleccionado
    private static void verHistorial() {
        Alumno alumno = listaAlumnos.getSelectedValue();

        if (alumno == null) {
            JOptionPane.showMessageDialog(
                    null, "Selecciona un alumno de la lista.");
            return;
        }

        StringBuilder texto = new StringBuilder();
        texto.append("ALUMNO: ").append(alumno)
                .append("\n\n");

        if (alumno.getReportes().isEmpty()) {
            texto.append("Todavia no tiene reportes.");
        } else {
            int numero = 1;

            for (Reporte reporte : alumno.getReportes()) {
                texto.append("Reporte ").append(numero++)
                        .append("\n");
                texto.append(reporte).append("\n\n");
            }
        }

        historial.setText(texto.toString());
    }
}
