package vista;

import modelo.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
    private JTextArea txtMotivo; // Cambiado a JTextArea para más espacio
    
    public static List<Paciente> dbPacientesMock = new ArrayList<>();
    private static Map<String, Paciente> apiReniecMock = new HashMap<>();
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    static {
        Paciente c1 = new Paciente(); c1.setDni("45721839"); c1.setNombres("Luis Miguel"); c1.setApellidos("Rojas Cárdenas"); c1.setFechaNacimiento(LocalDate.of(1985, 4, 12)); apiReniecMock.put(c1.getDni(), c1);
        Paciente c2 = new Paciente(); c2.setDni("71239485"); c2.setNombres("Carmen Sofía"); c2.setApellidos("Chávez Ramírez"); c2.setFechaNacimiento(LocalDate.of(1992, 8, 25)); apiReniecMock.put(c2.getDni(), c2);
        Paciente c3 = new Paciente(); c3.setDni("09458123"); c3.setNombres("Julio César"); c3.setApellidos("Flores Huamán"); c3.setFechaNacimiento(LocalDate.of(1978, 11, 5)); apiReniecMock.put(c3.getDni(), c3);
        Paciente c4 = new Paciente(); c4.setDni("60192837"); c4.setNombres("Daniela Andrea"); c4.setApellidos("Pérez Castillo"); c4.setFechaNacimiento(LocalDate.of(2001, 2, 18)); apiReniecMock.put(c4.getDni(), c4);
        Paciente c5 = new Paciente(); c5.setDni("42857193"); c5.setNombres("Roberto Carlos"); c5.setApellidos("Gutiérrez Quispe"); c5.setFechaNacimiento(LocalDate.of(1989, 9, 30)); apiReniecMock.put(c5.getDni(), c5);
        Paciente c6 = new Paciente(); c6.setDni("74456153"); c6.setNombres("Aarón Keneth"); c6.setApellidos("Gonzales Cortez"); c6.setFechaNacimiento(LocalDate.of(2006, 2, 25)); apiReniecMock.put(c6.getDni(), c6);
    }

    public FrmRegistro() {
        setTitle("Módulo de Recepción - Centro de Salud");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 950, 500); // Horizontal y grande
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(245, 247, 250));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // --- HEADER ---
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(0, 102, 204));
        panelHeader.setBounds(0, 0, 950, 70);
        contentPane.add(panelHeader);
        panelHeader.setLayout(null);

        JLabel lblTitulo = new JLabel("REGISTRO DE NUEVA CITA Y TRIAJE");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setBounds(0, 18, 934, 30);
        panelHeader.add(lblTitulo);

        // --- COLUMNA IZQUIERDA ---
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setBackground(Color.WHITE);
        panelBusqueda.setBorder(new TitledBorder(new LineBorder(new Color(180, 180, 180), 1, true), "1. Conexión RENIEC", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 102, 204)));
        panelBusqueda.setBounds(20, 85, 430, 80);
        contentPane.add(panelBusqueda);
        panelBusqueda.setLayout(null);

        JLabel lblDni = new JLabel("DNI:");
        lblDni.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblDni.setBounds(20, 32, 40, 20);
        panelBusqueda.add(lblDni);

        txtDni = new JTextField();
        txtDni.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtDni.setBounds(70, 30, 180, 28);
        panelBusqueda.add(txtDni);

        JLabel lblHint = new JLabel("<html><i>Autocompletado</i></html>");
        lblHint.setForeground(Color.GRAY);
        lblHint.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblHint.setBounds(260, 35, 120, 20);
        panelBusqueda.add(lblHint);

        JPanel panelDatos = new JPanel();
        panelDatos.setBackground(Color.WHITE);
        panelDatos.setBorder(new TitledBorder(new LineBorder(new Color(180, 180, 180), 1, true), "2. Datos Personales", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 102, 204)));
        panelDatos.setBounds(20, 180, 430, 160);
        contentPane.add(panelDatos);
        panelDatos.setLayout(null);

        JLabel lblNombres = new JLabel("Nombres:");
        lblNombres.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblNombres.setBounds(20, 30, 80, 20);
        panelDatos.add(lblNombres);
        txtNombres = new JTextField();
        txtNombres.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtNombres.setBounds(110, 28, 300, 26);
        panelDatos.add(txtNombres);

        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblApellidos.setBounds(20, 65, 80, 20);
        panelDatos.add(lblApellidos);
        txtApellidos = new JTextField();
        txtApellidos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtApellidos.setBounds(110, 63, 300, 26);
        panelDatos.add(txtApellidos);

        JLabel lblFechaNac = new JLabel("F. Nacimiento:");
        lblFechaNac.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblFechaNac.setBounds(20, 100, 90, 20);
        panelDatos.add(lblFechaNac);
        txtFechaNac = new JTextField();
        txtFechaNac.setToolTipText("DD-MM-YYYY");
        txtFechaNac.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtFechaNac.setBounds(110, 98, 100, 26);
        panelDatos.add(txtFechaNac);

        JLabel lblTelefono = new JLabel("Celular:");
        lblTelefono.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTelefono.setBounds(230, 100, 50, 20);
        panelDatos.add(lblTelefono);
        txtTelefono = new JTextField();
        txtTelefono.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtTelefono.setBounds(280, 98, 130, 26);
        panelDatos.add(txtTelefono);

        // --- COLUMNA DERECHA ---
        JPanel panelCita = new JPanel();
        panelCita.setBackground(Color.WHITE);
        panelCita.setBorder(new TitledBorder(new LineBorder(new Color(180, 180, 180), 1, true), "3. Información Clínica (Triaje)", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 102, 204)));
        panelCita.setBounds(470, 85, 440, 255);
        contentPane.add(panelCita);
        panelCita.setLayout(null);

        JLabel lblMotivo = new JLabel("Describa el motivo principal de la consulta:");
        lblMotivo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMotivo.setBounds(20, 25, 300, 20);
        panelCita.add(lblMotivo);

        JScrollPane scrollMotivo = new JScrollPane();
        scrollMotivo.setBounds(20, 50, 400, 185);
        panelCita.add(scrollMotivo);
        txtMotivo = new JTextArea();
        txtMotivo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMotivo.setLineWrap(true);
        txtMotivo.setWrapStyleWord(true);
        scrollMotivo.setViewportView(txtMotivo);

        // --- BOTONES ---
        JButton btnRegistrar = new JButton("Guardar Cita Médica");
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setBackground(new Color(40, 167, 69)); 
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnRegistrar.setBounds(20, 360, 430, 45);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        contentPane.add(btnRegistrar);

        JButton btnIrConsulta = new JButton("Volver al Menú Principal");
        btnIrConsulta.setForeground(Color.WHITE);
        btnIrConsulta.setBackground(new Color(108, 117, 125)); 
        btnIrConsulta.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnIrConsulta.setBounds(470, 360, 440, 45);
        btnIrConsulta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        contentPane.add(btnIrConsulta);

        // --- EVENTOS ---
        txtDni.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String dniIngresado = txtDni.getText().trim();
                if (dniIngresado.length() == 8) {
                    Optional<Paciente> pacienteExistente = dbPacientesMock.stream().filter(p -> p.getDni() != null && p.getDni().equals(dniIngresado)).findFirst();
                    if (pacienteExistente.isPresent()) {
                        Paciente p = pacienteExistente.get();
                        txtNombres.setText(p.getNombres()); txtApellidos.setText(p.getApellidos());
                        txtTelefono.setText(p.getTelefono() != null ? p.getTelefono() : "");
                        txtFechaNac.setText(p.getFechaNacimiento() != null ? p.getFechaNacimiento().format(FORMATO_FECHA) : "");
                        bloquearCamposPersonales(true);
                    } else if (apiReniecMock.containsKey(dniIngresado)) {
                        Paciente pReniec = apiReniecMock.get(dniIngresado);
                        txtNombres.setText(pReniec.getNombres()); txtApellidos.setText(pReniec.getApellidos());
                        txtFechaNac.setText(pReniec.getFechaNacimiento().format(FORMATO_FECHA)); txtTelefono.setText(""); 
                        bloquearCamposPersonales(true);
                        JOptionPane.showMessageDialog(null, "Datos recuperados de RENIEC.", "Conexión", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        limpiarCamposPersonales(); bloquearCamposPersonales(false);
                    }
                } else {
                    limpiarCamposPersonales(); bloquearCamposPersonales(false);
                }
            }
        });

        btnRegistrar.addActionListener(e -> {
            try {
                String dniIngresado = txtDni.getText().trim();
                if (dniIngresado.length() != 8 || !dniIngresado.matches("[0-9]+")) { JOptionPane.showMessageDialog(null, "DNI inválido."); return; }
                if (!txtNombres.getText().trim().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$") || !txtApellidos.getText().trim().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) { JOptionPane.showMessageDialog(null, "Nombres/Apellidos inválidos."); return; }
                
                LocalDate fechaNacimiento;
                try { fechaNacimiento = LocalDate.parse(txtFechaNac.getText().trim(), FORMATO_FECHA); } catch (DateTimeParseException ex) { JOptionPane.showMessageDialog(null, "Formato fecha: DD-MM-YYYY"); return; }
                
                Optional<Paciente> pacienteExistente = dbPacientesMock.stream().filter(p -> p.getDni() != null && p.getDni().equals(dniIngresado)).findFirst();
                Paciente p;
                if (pacienteExistente.isPresent()) { p = pacienteExistente.get(); p.setTelefono(txtTelefono.getText().trim()); } 
                else {
                    p = new Paciente(); p.setDni(dniIngresado); p.setNombres(txtNombres.getText().trim()); p.setApellidos(txtApellidos.getText().trim());
                    p.setFechaNacimiento(fechaNacimiento); p.setTelefono(txtTelefono.getText().trim()); p.setNumeroHistoriaClinica("HC-" + (dbPacientesMock.size() + 1));
                    dbPacientesMock.add(p);
                }

                CitaMedica cita = new CitaMedica(); cita.setIdCita("C" + (p.getCitasMedicas().size() + 1));
                cita.setFechaHora(LocalDateTime.now()); cita.setMotivoConsulta(txtMotivo.getText().trim()); cita.programarCita();
                p.solicitarCita(cita); 
                JOptionPane.showMessageDialog(null, "Cita registrada exitosamente para:\n" + p.getNombreCompleto(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
                txtDni.setText(""); txtMotivo.setText(""); limpiarCamposPersonales(); bloquearCamposPersonales(false);
            } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage()); }
        });

        btnIrConsulta.addActionListener(e -> { new FrmPrincipal().setVisible(true); dispose(); });
    }

    private void limpiarCamposPersonales() { txtNombres.setText(""); txtApellidos.setText(""); txtTelefono.setText(""); txtFechaNac.setText(""); }
    private void bloquearCamposPersonales(boolean bloquear) {
        txtNombres.setEditable(!bloquear); txtApellidos.setEditable(!bloquear); txtFechaNac.setEditable(!bloquear);
        Color cb = new Color(240, 245, 250);
        txtNombres.setBackground(bloquear ? cb : Color.WHITE); txtApellidos.setBackground(bloquear ? cb : Color.WHITE); txtFechaNac.setBackground(bloquear ? cb : Color.WHITE);
    }
}