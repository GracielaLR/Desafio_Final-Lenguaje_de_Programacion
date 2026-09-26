package vista;

import modelo.*;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class FrmRegistro extends JFrame {

    private JPanel contentPane;
    private JTextField txtDni;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtTelefono;
    private JTextField txtFechaNac;
    private JTextField txtMotivo;
    
    // "Base de datos en duro" del centro de salud
    public static List<Paciente> dbPacientesMock = new ArrayList<>();

    // Simulación de Base de Datos de la API RENIEC
    private static Map<String, Paciente> apiReniecMock = new HashMap<>();
    
    static {
        // Precargamos datos de prueba realistas para simular la API de RENIEC
        Paciente c1 = new Paciente();
        c1.setDni("45721839");
        c1.setNombres("Luis Miguel");
        c1.setApellidos("Rojas Cárdenas");
        c1.setFechaNacimiento(LocalDate.of(1985, 4, 12));
        apiReniecMock.put(c1.getDni(), c1);

        Paciente c2 = new Paciente();
        c2.setDni("71239485");
        c2.setNombres("Carmen Sofía");
        c2.setApellidos("Chávez Ramírez");
        c2.setFechaNacimiento(LocalDate.of(1992, 8, 25));
        apiReniecMock.put(c2.getDni(), c2);

        Paciente c3 = new Paciente();
        c3.setDni("09458123");
        c3.setNombres("Julio César");
        c3.setApellidos("Flores Huamán");
        c3.setFechaNacimiento(LocalDate.of(1978, 11, 5));
        apiReniecMock.put(c3.getDni(), c3);

        Paciente c4 = new Paciente();
        c4.setDni("60192837");
        c4.setNombres("Daniela Andrea");
        c4.setApellidos("Pérez Castillo");
        c4.setFechaNacimiento(LocalDate.of(2001, 2, 18));
        apiReniecMock.put(c4.getDni(), c4);

        Paciente c5 = new Paciente();
        c5.setDni("42857193");
        c5.setNombres("Roberto Carlos");
        c5.setApellidos("Gutiérrez Quispe");
        c5.setFechaNacimiento(LocalDate.of(1989, 9, 30));
        apiReniecMock.put(c5.getDni(), c5);
        
        Paciente c6 = new Paciente();
        c6.setDni("74456153");
        c6.setNombres("Aarón Keneth");
        c6.setApellidos("Gonzales Cortez");
        c6.setFechaNacimiento(LocalDate.of(2006, 2, 25));
        apiReniecMock.put(c6.getDni(), c6);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                FrmRegistro frame = new FrmRegistro();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public FrmRegistro() {
        // --- CONFIGURACIÓN DE LA VENTANA PRINCIPAL ---
        setTitle("Módulo de Recepción - Centro de Salud");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 580); 
        contentPane = new JPanel();
        contentPane.setBackground(new Color(245, 247, 250)); // Color de fondo general claro
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // --- BANNER SUPERIOR CORPORATIVO ---
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(0, 102, 204)); // Azul Médico
        panelHeader.setBounds(0, 0, 500, 60);
        contentPane.add(panelHeader);
        panelHeader.setLayout(null);

        JLabel lblTitulo = new JLabel("REGISTRO DE NUEVA CITA");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setBounds(0, 15, 484, 30);
        panelHeader.add(lblTitulo);

        // --- PANEL 1: BÚSQUEDA ---
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setBackground(Color.WHITE);
        panelBusqueda.setBorder(new TitledBorder(new LineBorder(new Color(180, 180, 180), 1, true), "1. Identidad del Paciente (Conexión RENIEC)", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 102, 204)));
        panelBusqueda.setBounds(20, 75, 445, 75);
        contentPane.add(panelBusqueda);
        panelBusqueda.setLayout(null);

        JLabel lblDni = new JLabel("Ingrese DNI:");
        lblDni.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDni.setBounds(20, 30, 100, 20);
        panelBusqueda.add(lblDni);

        txtDni = new JTextField();
        txtDni.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtDni.setBounds(110, 27, 200, 26);
        panelBusqueda.add(txtDni);
        
        JLabel lblHint = new JLabel("<html><i>(Autocompletado)</i></html>");
        lblHint.setForeground(Color.GRAY);
        lblHint.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblHint.setBounds(320, 30, 110, 20);
        panelBusqueda.add(lblHint);

        // --- PANEL 2: DATOS PERSONALES ---
        JPanel panelDatos = new JPanel();
        panelDatos.setBackground(Color.WHITE);
        panelDatos.setBorder(new TitledBorder(new LineBorder(new Color(180, 180, 180), 1, true), "2. Datos Personales", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 102, 204)));
        panelDatos.setBounds(20, 165, 445, 145);
        contentPane.add(panelDatos);
        panelDatos.setLayout(null);

        JLabel lblNombres = new JLabel("Nombres:");
        lblNombres.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblNombres.setBounds(20, 30, 80, 20);
        panelDatos.add(lblNombres);

        txtNombres = new JTextField();
        txtNombres.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtNombres.setBounds(110, 27, 315, 26);
        panelDatos.add(txtNombres);

        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblApellidos.setBounds(20, 65, 80, 20);
        panelDatos.add(lblApellidos);

        txtApellidos = new JTextField();
        txtApellidos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtApellidos.setBounds(110, 62, 315, 26);
        panelDatos.add(txtApellidos);

        JLabel lblFechaNac = new JLabel("F. Nacimiento:");
        lblFechaNac.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblFechaNac.setBounds(20, 100, 90, 20);
        panelDatos.add(lblFechaNac);

        txtFechaNac = new JTextField();
        txtFechaNac.setToolTipText("YYYY-MM-DD");
        txtFechaNac.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtFechaNac.setBounds(110, 97, 100, 26);
        panelDatos.add(txtFechaNac);

        JLabel lblTelefono = new JLabel("Celular:");
        lblTelefono.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTelefono.setBounds(230, 100, 60, 20);
        panelDatos.add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtTelefono.setBounds(285, 97, 140, 26);
        panelDatos.add(txtTelefono);

        // --- PANEL 3: MOTIVO DE CITA ---
        JPanel panelCita = new JPanel();
        panelCita.setBackground(Color.WHITE);
        panelCita.setBorder(new TitledBorder(new LineBorder(new Color(180, 180, 180), 1, true), "3. Información Clínica (Triaje)", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 102, 204)));
        panelCita.setBounds(20, 325, 445, 115);
        contentPane.add(panelCita);
        panelCita.setLayout(null);

        JLabel lblMotivo = new JLabel("Motivo de consulta principal:");
        lblMotivo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMotivo.setBounds(20, 25, 200, 20);
        panelCita.add(lblMotivo);

        txtMotivo = new JTextField();
        txtMotivo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtMotivo.setBounds(20, 50, 405, 45);
        panelCita.add(txtMotivo);

        // --- BOTONES CON FLAT DESIGN ---
        JButton btnRegistrar = new JButton("✔ Guardar Cita Médica");
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setBackground(new Color(40, 167, 69)); // Verde Éxito
        btnRegistrar.setFocusPainted(false); // Quitar borde punteado
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistrar.setBounds(20, 465, 210, 40);
        contentPane.add(btnRegistrar);

        JButton btnIrConsulta = new JButton("Volver al Menú");
        btnIrConsulta.setForeground(Color.WHITE);
        btnIrConsulta.setBackground(new Color(108, 117, 125)); // Gris Corporativo
        btnIrConsulta.setFocusPainted(false);
        btnIrConsulta.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIrConsulta.setBounds(255, 465, 210, 40);
        contentPane.add(btnIrConsulta);

        // ==========================================
        // EVENTOS (Misma lógica funcional y de seguridad)
        // ==========================================

        txtDni.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String dniIngresado = txtDni.getText().trim();
                
                if (dniIngresado.length() == 8) {
                    Optional<Paciente> pacienteExistente = dbPacientesMock.stream()
                        .filter(p -> p.getDni() != null && p.getDni().equals(dniIngresado))
                        .findFirst();

                    if (pacienteExistente.isPresent()) {
                        Paciente p = pacienteExistente.get();
                        txtNombres.setText(p.getNombres());
                        txtApellidos.setText(p.getApellidos());
                        txtTelefono.setText(p.getTelefono() != null ? p.getTelefono() : "");
                        txtFechaNac.setText(p.getFechaNacimiento() != null ? p.getFechaNacimiento().toString() : "");
                        bloquearCamposPersonales(true);
                        
                    } else if (apiReniecMock.containsKey(dniIngresado)) {
                        Paciente pReniec = apiReniecMock.get(dniIngresado);
                        txtNombres.setText(pReniec.getNombres());
                        txtApellidos.setText(pReniec.getApellidos());
                        txtFechaNac.setText(pReniec.getFechaNacimiento().toString());
                        txtTelefono.setText(""); 
                        
                        bloquearCamposPersonales(true);
                        JOptionPane.showMessageDialog(null, "Consulta a API RENIEC exitosa.\nDatos del ciudadano recuperados.", "Conexión RENIEC", JOptionPane.INFORMATION_MESSAGE);
                        
                    } else {
                        limpiarCamposPersonales();
                        bloquearCamposPersonales(false);
                    }
                } else {
                    limpiarCamposPersonales();
                    bloquearCamposPersonales(false);
                }
            }
        });

        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String dniIngresado = txtDni.getText().trim();
                    
                    if (dniIngresado.length() != 8 || !dniIngresado.matches("[0-9]+")) {
                        JOptionPane.showMessageDialog(null, "Error: Debe ingresar un DNI válido de exactamente 8 dígitos numéricos.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return; 
                    }

                    String nombres = txtNombres.getText().trim();
                    String apellidos = txtApellidos.getText().trim();

                    if (!nombres.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$") || !apellidos.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
                        JOptionPane.showMessageDialog(null, "Error: Los nombres y apellidos solo deben contener letras.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return; 
                    }

                    LocalDate fechaNacimiento;
                    try {
                        fechaNacimiento = LocalDate.parse(txtFechaNac.getText().trim());
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(null, "Error: La fecha debe usar el formato YYYY-MM-DD (Ej. 1995-08-25).", "Formato de Fecha", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    Optional<Paciente> pacienteExistente = dbPacientesMock.stream()
                        .filter(p -> p.getDni() != null && p.getDni().equals(dniIngresado))
                        .findFirst();

                    Paciente p;
                    if (pacienteExistente.isPresent()) {
                        p = pacienteExistente.get();
                        p.setTelefono(txtTelefono.getText().trim());
                    } else {
                        p = new Paciente();
                        p.setDni(dniIngresado);
                        p.setNombres(txtNombres.getText().trim());
                        p.setApellidos(txtApellidos.getText().trim());
                        p.setFechaNacimiento(fechaNacimiento);
                        p.setTelefono(txtTelefono.getText().trim());
                        p.setNumeroHistoriaClinica("HC-" + (dbPacientesMock.size() + 1));
                        dbPacientesMock.add(p);
                    }

                    CitaMedica cita = new CitaMedica();
                    cita.setIdCita("C" + (p.getCitasMedicas().size() + 1));
                    cita.setFechaHora(LocalDateTime.now());
                    cita.setMotivoConsulta(txtMotivo.getText().trim());
                    cita.programarCita();

                    p.solicitarCita(cita); 

                    JOptionPane.showMessageDialog(null, "Cita registrada exitosamente para:\n" + p.getNombreCompleto(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    
                    txtDni.setText(""); 
                    txtMotivo.setText("");
                    limpiarCamposPersonales();
                    bloquearCamposPersonales(false);
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error crítico: " + ex.getMessage(), "Error del Sistema", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnIrConsulta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FrmPrincipal principal = new FrmPrincipal();
                principal.setVisible(true);
                dispose();
            }
        });
    }

    // Métodos auxiliares
    private void limpiarCamposPersonales() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        txtFechaNac.setText("");
    }

    private void bloquearCamposPersonales(boolean bloquear) {
        txtNombres.setEditable(!bloquear);
        txtApellidos.setEditable(!bloquear);
        txtFechaNac.setEditable(!bloquear);
        
        Color colorBloqueado = new Color(240, 245, 250); // Un celeste muy tenue para indicar bloqueo
        txtNombres.setBackground(bloquear ? colorBloqueado : Color.WHITE);
        txtApellidos.setBackground(bloquear ? colorBloqueado : Color.WHITE);
        txtFechaNac.setBackground(bloquear ? colorBloqueado : Color.WHITE);
    }
}