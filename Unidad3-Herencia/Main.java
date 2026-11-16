import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
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

    // Agrega un reporte al historial (acepta cualquier subclase de Reporte)
    public void agregarReporte(Reporte reporte) {
        reportes.add(reporte);
    }

    // Devuelve el historial de reportes
    public ArrayList<Reporte> getReportes() {
        return reportes;
    }
}

// ============================================================
// CLASE BASE: contiene lo comun a todo reporte
// ============================================================
class Reporte {
    // protected para que las subclases puedan acceder a ellos
    protected String descripcion;
    protected String estrategia;
    protected LocalDate fecha;

    // Constructor del reporte
    public Reporte(String descripcion, String estrategia) {
        this.descripcion = descripcion;
        this.estrategia = estrategia;
        this.fecha = LocalDate.now();
    }

    // Valores por defecto que las subclases sobrescriben
    public String getTipo() {
        return "General";
    }

    public String getPrioridad() {
        return "Normal";
    }

    @Override
    public String toString() {
        return "Tipo: " + getTipo()
                + " | Prioridad: " + getPrioridad()
                + " | Fecha: " + fecha
                + "\nSituacion: " + descripcion
                + "\nEstrategia: " + estrategia;
    }
}

// ============================================================
// CLASE DERIVADA 1: reportes de conducta
// ============================================================
class ReporteConducta extends Reporte {
    private String gravedad;      // Baja, Moderada o Alta
    private boolean citaPadres;

    public ReporteConducta(String descripcion, String estrategia,
                           String gravedad, boolean citaPadres) {
        super(descripcion, estrategia);   // reutiliza la clase base
        this.gravedad = gravedad;
        this.citaPadres = citaPadres;
    }

    // Metodo exclusivo de esta subclase
    public boolean requiereAtencionInmediata() {
        return gravedad.equalsIgnoreCase("Alta") || citaPadres;
    }

    @Override
    public String getTipo() {
        return "Conducta";
    }

    @Override
    public String getPrioridad() {
        if (requiereAtencionInmediata()) {
            return "Alta";
        }
        return gravedad.equalsIgnoreCase("Moderada") ? "Media" : "Baja";
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nGravedad: " + gravedad
                + "\nCita a padres: " + (citaPadres ? "Si" : "No");
    }
}

// ============================================================
// CLASE DERIVADA 2: reportes academicos
// ============================================================
class ReporteAcademico extends Reporte {
    private String materia;
    private double calificacion;

    public ReporteAcademico(String descripcion, String estrategia,
                            String materia, double calificacion) {
        super(descripcion, estrategia);   // reutiliza la clase base
        this.materia = materia;
        this.calificacion = calificacion;
    }

    // Metodo exclusivo de esta subclase
    public boolean estaEnRiesgo() {
        return calificacion < 6.0;
    }

    @Override
    public String getTipo() {
        return "Academico";
    }

    @Override
    public String getPrioridad() {
        return estaEnRiesgo() ? "Alta" : "Normal";
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nMateria: " + materia
                + "\nCalificacion: " + calificacion
                + (estaEnRiesgo() ? " (en riesgo)" : "");
    }
}

// ============================================================
// CLASE DERIVADA 3: reportes de asistencia
// ============================================================
class ReporteAsistencia extends Reporte {
    private int faltas;

    public ReporteAsistencia(String descripcion, String estrategia,
                             int faltas) {
        super(descripcion, estrategia);   // reutiliza la clase base
        this.faltas = faltas;
    }

    // Metodo exclusivo de esta subclase
    public boolean excedeLimite() {
        return faltas >= 3;
    }

    @Override
    public String getTipo() {
        return "Asistencia";
    }

    @Override
    public String getPrioridad() {
        return excedeLimite() ? "Alta" : "Normal";
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nFaltas: " + faltas
                + (excedeLimite() ? " (excede el limite)" : "");
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
        probarHerencia();   // prueba en consola
        SwingUtilities.invokeLater(() -> crearVentana());
    }

    // Instancia un objeto de cada subclase y demuestra polimorfismo
    private static void probarHerencia() {
        Alumno alumno = new Alumno("Carlos", 2, "A");

        ReporteConducta conducta = new ReporteConducta(
                "Falta de atender indicaciones.",
                "Dar indicaciones claras y cortas.",
                "Moderada", false);

        ReporteAcademico academico = new ReporteAcademico(
                "Dificultad con la suma de fracciones.",
                "Practica guiada con material concreto.",
                "Matematicas", 5.5);

        ReporteAsistencia asistencia = new ReporteAsistencia(
                "Llegadas tarde y ausencias consecutivas.",
                "Dialogo con la familia sobre horarios.",
                4);

        alumno.agregarReporte(conducta);
        alumno.agregarReporte(academico);
        alumno.agregarReporte(asistencia);

        // Alumno de ejemplo visible en la interfaz
        alumnos.add(alumno);
        modelo.addElement(alumno);

        System.out.println("=== PRUEBA DE HERENCIA ===");

        // Polimorfismo: variable de tipo base, comportamiento de la subclase
        for (Reporte r : alumno.getReportes()) {
            System.out.println(r);
            System.out.println("--> getTipo(): " + r.getTipo()
                    + " | getPrioridad(): " + r.getPrioridad());
            System.out.println();
        }

        // Metodos exclusivos de cada subclase
        System.out.println("requiereAtencionInmediata(): "
                + conducta.requiereAtencionInmediata());
        System.out.println("estaEnRiesgo(): "
                + academico.estaEnRiesgo());
        System.out.println("excedeLimite(): "
                + asistencia.excedeLimite());
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
            int grado = Integer.parseInt(gradoTexto.trim());

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

    // Crea un reporte (de la subclase elegida) para el alumno seleccionado
    private static void crearReporte() {
        Alumno alumno = listaAlumnos.getSelectedValue();

        if (alumno == null) {
            JOptionPane.showMessageDialog(
                    null, "Primero selecciona un alumno.");
            return;
        }

        // Pregunta el tipo de reporte
        String[] tipos = {"Conducta", "Academico", "Asistencia"};
        String tipo = (String) JOptionPane.showInputDialog(
                null, "Tipo de reporte:", "Nuevo reporte",
                JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);

        if (tipo == null) {
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

        descripcion = descripcion.trim();
        estrategia = estrategia.trim();

        // La variable es del tipo base, pero guarda la subclase elegida
        Reporte reporte;

        try {
            if (tipo.equals("Conducta")) {
                String[] gravedades = {"Baja", "Moderada", "Alta"};
                String gravedad = (String) JOptionPane.showInputDialog(
                        null, "Gravedad:", "Conducta",
                        JOptionPane.QUESTION_MESSAGE, null,
                        gravedades, gravedades[0]);

                if (gravedad == null) {
                    return;
                }

                int cita = JOptionPane.showConfirmDialog(
                        null, "¿Se cita a los padres?",
                        "Conducta", JOptionPane.YES_NO_OPTION);

                reporte = new ReporteConducta(descripcion, estrategia,
                        gravedad, cita == JOptionPane.YES_OPTION);

            } else if (tipo.equals("Academico")) {
                String materia = JOptionPane.showInputDialog(
                        "Materia:");

                if (materia == null || materia.trim().isEmpty()) {
                    return;
                }

                String califTexto = JOptionPane.showInputDialog(
                        "Calificacion (0 a 10):");

                if (califTexto == null) {
                    return;
                }

                double calif = Double.parseDouble(califTexto.trim());

                if (calif < 0 || calif > 10) {
                    JOptionPane.showMessageDialog(null,
                            "Ingresa una calificacion entre 0 y 10.");
                    return;
                }

                reporte = new ReporteAcademico(descripcion, estrategia,
                        materia.trim(), calif);

            } else {
                String faltasTexto = JOptionPane.showInputDialog(
                        "Numero de faltas:");

                if (faltasTexto == null) {
                    return;
                }

                int faltas = Integer.parseInt(faltasTexto.trim());

                if (faltas < 0) {
                    JOptionPane.showMessageDialog(null,
                            "Las faltas no pueden ser negativas.");
                    return;
                }

                reporte = new ReporteAsistencia(descripcion,
                        estrategia, faltas);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    null, "Ingresa un valor numerico valido.");
            return;
        }

        alumno.agregarReporte(reporte);

        JOptionPane.showMessageDialog(
                null, "Reporte guardado correctamente.");
    }

    // Muestra los reportes del alumno seleccionado
    // (no cambia: cada reporte ejecuta el toString() de su subclase)
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
