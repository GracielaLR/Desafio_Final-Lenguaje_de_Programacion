package vista;

import modelo.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
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
import javax.swing.JComboBox;
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
    private JTextField txtDni, txtNombres, txtApellidos, txtTelefono, txtFechaNac;
    private JTextArea txtMotivo; 
    private JComboBox<String> cbxEspecialidad, cbxMedico;
    
    // BASES DE DATOS SIMULADAS GLOBALES
    public static List<Paciente> dbPacientesMock = new ArrayList<>();
    public static List<Medico> dbMedicosMock = new ArrayList<>();
    private static Map<String, Paciente> apiReniecMock = new HashMap<>();
    
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    static {
        // 1. Poblamos 10 Médicos (2 por especialidad)
        dbMedicosMock.add(crearMedico("Carlos", "Torres", "Medicina General", "CMP-55210"));
        dbMedicosMock.add(crearMedico("María", "Paz", "Medicina General", "CMP-55211"));
        dbMedicosMock.add(crearMedico("Ana Luisa", "Pineda", "Pediatría", "CMP-61023"));
        dbMedicosMock.add(crearMedico("Luis", "Gómez", "Pediatría", "CMP-61024"));
        dbMedicosMock.add(crearMedico("Roberto", "Fernández", "Ginecología", "CMP-48991"));
        dbMedicosMock.add(crearMedico("Carmen", "Vega", "Ginecología", "CMP-48992"));
        dbMedicosMock.add(crearMedico("Lucía", "Ramírez", "Cardiología", "CMP-70112"));
        dbMedicosMock.add(crearMedico("Marcos", "Torres", "Cardiología", "CMP-70113"));
        dbMedicosMock.add(crearMedico("Jorge", "Castillo", "Traumatología", "CMP-33104"));
        dbMedicosMock.add(crearMedico("Elena", "Rojas", "Traumatología", "CMP-33105"));

        // 2. Poblamos API Reniec
        apiReniecMock.put("45721839", crearPac("45721839", "Luis Miguel", "Rojas Cárdenas", 1985, 4, 12));
        apiReniecMock.put("71239485", crearPac("71239485", "Carmen Sofía", "Chávez Ramírez", 1992, 8, 25));
        apiReniecMock.put("09458123", crearPac("09458123", "Julio César", "Flores Huamán", 1978, 11, 5));
        apiReniecMock.put("60192837", crearPac("60192837", "Daniela Andrea", "Pérez Castillo", 2001, 2, 18));
        apiReniecMock.put("42857193", crearPac("42857193", "Roberto Carlos", "Gutiérrez Quispe", 1989, 9, 30));

    }

    private static Medico crearMedico(String n, String a, String e, String c) {
        Medico m = new Medico(); m.setNombres(n); m.setApellidos(a); m.setEspecialidad(e); m.setCmp(c); return m;
    }
    private static Paciente crearPac(String d, String n, String a, int y, int m, int d2) {
        Paciente p = new Paciente(); p.setDni(d); p.setNombres(n); p.setApellidos(a); p.setFechaNacimiento(LocalDate.of(y,m,d2)); return p;
    }

    private static void cargarPacientesDemo() {
        LocalDateTime hoy = LocalDateTime.now();
        CitaMedica.EstadoCita ATEN = CitaMedica.EstadoCita.ATENDIDA;
        CitaMedica.EstadoCita PEND = CitaMedica.EstadoCita.PENDIENTE;
        CitaMedica.EstadoCita CANC = CitaMedica.EstadoCita.CANCELADA;

        // 1. Luis Miguel: historial (con receta) + cita pendiente
        Paciente p1 = registrarDemo("45721839", "Luis Miguel", "Rojas Cárdenas", 1985, 4, 12, "987654321");
        agregarCitaDemo(p1, "Medicina General", "CMP-55210", "Dolor abdominal y acidez", hoy.minusMonths(3), ATEN,
                "Gastritis leve", "Dieta blanda, evitar irritantes. Omeprazol 20mg cada 24 h en ayunas", "Omeprazol", 10);
        agregarCitaDemo(p1, "Cardiología", "CMP-70112", "Palpitaciones ocasionales", hoy.minusDays(1), PEND,
                null, null, null, 0);

        // 2. Carmen Sofía: historial + control pendiente
        Paciente p2 = registrarDemo("71239485", "Carmen Sofía", "Chávez Ramírez", 1992, 8, 25, "976543210");
        agregarCitaDemo(p2, "Ginecología", "CMP-48992", "Control ginecológico anual", hoy.minusMonths(2), ATEN,
                "Control normal, sin hallazgos", "Continuar controles anuales. Suplemento de ácido fólico", null, 0);
        agregarCitaDemo(p2, "Medicina General", "CMP-55211", "Cefalea frecuente", hoy.minusHours(5), PEND,
                null, null, null, 0);

        // 3. Julio César: una atendida (con receta) y una cancelada
        Paciente p3 = registrarDemo("09458123", "Julio César", "Flores Huamán", 1978, 11, 5, "965432109");
        agregarCitaDemo(p3, "Traumatología", "CMP-33104", "Dolor en rodilla derecha tras caída", hoy.minusMonths(1), ATEN,
                "Esguince de rodilla grado I", "Reposo relativo, hielo local, Ibuprofeno 400mg cada 8 h por 5 días", "Ibuprofeno", 15);
        agregarCitaDemo(p3, "Traumatología", "CMP-33104", "Control de rodilla", hoy.minusDays(10), CANC,
                null, null, null, 0);

        // 4. Daniela Andrea: solo cita pendiente
        Paciente p4 = registrarDemo("60192837", "Daniela Andrea", "Pérez Castillo", 2001, 2, 18, "954321098");
        agregarCitaDemo(p4, "Medicina General", "CMP-55210", "Fiebre y malestar general desde hace 2 días", hoy.minusMinutes(30), PEND,
                null, null, null, 0);

        // 5. Roberto Carlos: historial largo (2 atenciones, la segunda con receta)
        Paciente p5 = registrarDemo("42857193", "Roberto Carlos", "Gutiérrez Quispe", 1989, 9, 30, "943210987");
        agregarCitaDemo(p5, "Cardiología", "CMP-70113", "Presión arterial elevada", hoy.minusMonths(6), ATEN,
                "Hipertensión arterial leve", "Dieta baja en sodio, ejercicio moderado, control mensual de presión", null, 0);
        agregarCitaDemo(p5, "Medicina General", "CMP-55211", "Tos y dolor de garganta", hoy.minusDays(20), ATEN,
                "Faringitis aguda", "Azitromicina 250mg cada 24 h por 3 días, abundantes líquidos", "Azitromicina", 3);
    }

    private static Paciente registrarDemo(String dni, String nom, String ape, int y, int m, int d, String tel) {
        Paciente p = crearPac(dni, nom, ape, y, m, d);
        p.setTelefono(tel);
        p.setNumeroHistoriaClinica("HC-" + (dbPacientesMock.size() + 1));
        dbPacientesMock.add(p);
        return p;
    }

    private static void agregarCitaDemo(Paciente p, String esp, String cmpMedico, String motivo, LocalDateTime fecha,
                                        CitaMedica.EstadoCita estado, String diag, String trat,
                                        String nomMedicamento, int cantidad) {
        Medico med = dbMedicosMock.stream().filter(m -> m.getCmp().equals(cmpMedico)).findFirst().orElse(null);

        CitaMedica c = new CitaMedica();
        c.setIdCita("C" + (p.getCitasMedicas().size() + 1));
        c.setFechaHora(fecha);
        c.setMotivoConsulta(motivo);
        c.setEspecialidad(esp);
        c.setMedicoAsignado(med);
        c.programarCita();
        p.solicitarCita(c);

        if (estado == CitaMedica.EstadoCita.ATENDIDA) {
            med.getCitasAsignadas().add(c);
            med.atenderCita(c);
            AtencionMedica a = c.getAtencionMedica();
            a.setFechaAtencion(fecha.plusMinutes(25));
            a.setDiagnostico(diag);
            a.setTratamiento(trat);

            if (nomMedicamento != null) {
                Medicamento mc = FrmAtencion.dbMedicamentosMock.stream()
                        .filter(x -> x.getNombre().startsWith(nomMedicamento)).findFirst().orElse(null);
                if (mc != null) {
                    DetalleReceta d = new DetalleReceta();
                    d.setMedicamento(mc);
                    d.setCantidad(cantidad);
                    d.setIndicaciones(trat);
                    med.emitirReceta(a, d);
                }
            }
        } else if (estado == CitaMedica.EstadoCita.CANCELADA) {
            c.cancelarCita();
        }
    }
    
    
    public FrmRegistro() {
        setTitle("Módulo de Recepción - Centro de Salud");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 950, 500); 
        setLocationRelativeTo(null);
        contentPane = new JPanel(); contentPane.setBackground(new Color(245, 247, 250)); contentPane.setLayout(null); setContentPane(contentPane);

        JPanel panelHeader = new JPanel(); panelHeader.setBackground(new Color(0, 102, 204)); panelHeader.setBounds(0, 0, 950, 70); contentPane.add(panelHeader); panelHeader.setLayout(null);
        JLabel lblTitulo = new JLabel("REGISTRO DE NUEVA CITA Y TRIAJE"); lblTitulo.setHorizontalAlignment(SwingConstants.CENTER); lblTitulo.setForeground(Color.WHITE); lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22)); lblTitulo.setBounds(0, 18, 934, 30); panelHeader.add(lblTitulo);

        // COLUMNA IZQUIERDA
        JPanel panelBusq = new JPanel(); panelBusq.setBackground(Color.WHITE); panelBusq.setBorder(new TitledBorder(new LineBorder(new Color(180, 180, 180), 1, true), "1. Conexión RENIEC", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 102, 204))); panelBusq.setBounds(20, 85, 430, 80); contentPane.add(panelBusq); panelBusq.setLayout(null);
        JLabel lblDni = new JLabel("DNI:"); lblDni.setFont(new Font("Segoe UI", Font.BOLD, 14)); lblDni.setBounds(20, 32, 40, 20); panelBusq.add(lblDni);
        txtDni = new JTextField(); txtDni.setFont(new Font("Segoe UI", Font.BOLD, 14)); txtDni.setBounds(70, 30, 180, 28); panelBusq.add(txtDni);
        JLabel lblHint = new JLabel("<html><i>Autocompletado</i></html>"); lblHint.setForeground(Color.GRAY); lblHint.setBounds(260, 35, 120, 20); panelBusq.add(lblHint);

        JPanel panelDatos = new JPanel(); panelDatos.setBackground(Color.WHITE); panelDatos.setBorder(new TitledBorder(new LineBorder(new Color(180, 180, 180), 1, true), "2. Datos Personales", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 102, 204))); panelDatos.setBounds(20, 180, 430, 160); contentPane.add(panelDatos); panelDatos.setLayout(null);
        JLabel lblNom = new JLabel("Nombres:"); lblNom.setBounds(20,30,80,20); panelDatos.add(lblNom); txtNombres = new JTextField(); txtNombres.setBounds(110,28,300,26); panelDatos.add(txtNombres);
        JLabel lblApe = new JLabel("Apellidos:"); lblApe.setBounds(20,65,80,20); panelDatos.add(lblApe); txtApellidos = new JTextField(); txtApellidos.setBounds(110,63,300,26); panelDatos.add(txtApellidos);
        JLabel lblFec = new JLabel("F. Nacimiento:"); lblFec.setBounds(20,100,90,20); panelDatos.add(lblFec); txtFechaNac = new JTextField(); txtFechaNac.setBounds(110,98,100,26); panelDatos.add(txtFechaNac);
        JLabel lblTel = new JLabel("Celular:"); lblTel.setBounds(230,100,50,20); panelDatos.add(lblTel); txtTelefono = new JTextField(); txtTelefono.setBounds(280,98,130,26); panelDatos.add(txtTelefono);

        // COLUMNA DERECHA (TRIAJE MEJORADO)
        JPanel panelCita = new JPanel(); panelCita.setBackground(Color.WHITE); panelCita.setBorder(new TitledBorder(new LineBorder(new Color(180, 180, 180), 1, true), "3. Información Clínica (Triaje)", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 102, 204))); panelCita.setBounds(470, 85, 440, 255); contentPane.add(panelCita); panelCita.setLayout(null);
        
        JLabel lblEsp = new JLabel("Especialidad requerida:"); lblEsp.setBounds(20, 30, 150, 20); panelCita.add(lblEsp);
        cbxEspecialidad = new JComboBox<>(new String[]{"Medicina General", "Pediatría", "Ginecología", "Cardiología", "Traumatología"}); cbxEspecialidad.setBounds(170, 28, 250, 25); panelCita.add(cbxEspecialidad);
        
        JLabel lblMed = new JLabel("Médico de preferencia:"); lblMed.setBounds(20, 65, 150, 20); panelCita.add(lblMed);
        cbxMedico = new JComboBox<>(); cbxMedico.setBounds(170, 63, 250, 25); panelCita.add(cbxMedico);
        
        JLabel lblMotivo = new JLabel("Describa el motivo principal de la consulta:"); lblMotivo.setBounds(20, 100, 300, 20); panelCita.add(lblMotivo);
        JScrollPane scrollMotivo = new JScrollPane(); scrollMotivo.setBounds(20, 125, 400, 110); panelCita.add(scrollMotivo);
        txtMotivo = new JTextArea(); txtMotivo.setFont(new Font("Segoe UI", Font.PLAIN, 14)); txtMotivo.setLineWrap(true); txtMotivo.setWrapStyleWord(true); scrollMotivo.setViewportView(txtMotivo);

        // BOTONES
        JButton btnRegistrar = new JButton("Guardar Cita Médica"); btnRegistrar.setBackground(new Color(40, 167, 69)); btnRegistrar.setForeground(Color.WHITE); btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 15)); btnRegistrar.setBounds(20, 360, 430, 45); btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR)); contentPane.add(btnRegistrar);
        JButton btnIrConsulta = new JButton("Volver al Menú Principal"); btnIrConsulta.setBackground(new Color(108, 117, 125)); btnIrConsulta.setForeground(Color.WHITE); btnIrConsulta.setFont(new Font("Segoe UI", Font.BOLD, 15)); btnIrConsulta.setBounds(470, 360, 440, 45); btnIrConsulta.setCursor(new Cursor(Cursor.HAND_CURSOR)); contentPane.add(btnIrConsulta);

        // EVENTOS
        cbxEspecialidad.addActionListener(e -> actualizarMedicosRegistro());
        actualizarMedicosRegistro(); // Carga inicial

        txtDni.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String dniIngresado = txtDni.getText().trim();
                if (dniIngresado.length() == 8) {
                    Optional<Paciente> pacienteExistente = dbPacientesMock.stream().filter(p -> p.getDni() != null && p.getDni().equals(dniIngresado)).findFirst();
                    if (pacienteExistente.isPresent()) {
                        Paciente p = pacienteExistente.get();
                        txtNombres.setText(p.getNombres()); txtApellidos.setText(p.getApellidos()); txtTelefono.setText(p.getTelefono() != null ? p.getTelefono() : ""); txtFechaNac.setText(p.getFechaNacimiento() != null ? p.getFechaNacimiento().format(FORMATO_FECHA) : ""); bloquear(true);
                    } else if (apiReniecMock.containsKey(dniIngresado)) {
                        Paciente pReniec = apiReniecMock.get(dniIngresado);
                        txtNombres.setText(pReniec.getNombres()); txtApellidos.setText(pReniec.getApellidos()); txtFechaNac.setText(pReniec.getFechaNacimiento().format(FORMATO_FECHA)); txtTelefono.setText(""); bloquear(true);
                        JOptionPane.showMessageDialog(null, "Datos recuperados de RENIEC.");
                    } else { limpiar(); bloquear(false); }
                } else { limpiar(); bloquear(false); }
            }
        });

        btnRegistrar.addActionListener(e -> {
            try {
                String dniIn = txtDni.getText().trim();
                if (dniIn.length() != 8 || !dniIn.matches("[0-9]+")) { JOptionPane.showMessageDialog(null, "DNI inválido."); return; }
                if (!txtNombres.getText().trim().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$") || !txtApellidos.getText().trim().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) { JOptionPane.showMessageDialog(null, "Nombres inválidos."); return; }
                LocalDate fNac; try { fNac = LocalDate.parse(txtFechaNac.getText().trim(), FORMATO_FECHA); } catch (DateTimeParseException ex) { JOptionPane.showMessageDialog(null, "Formato fecha: DD-MM-YYYY"); return; }
                
                Optional<Paciente> pExist = dbPacientesMock.stream().filter(p -> p.getDni() != null && p.getDni().equals(dniIn)).findFirst();
                Paciente p;
                if (pExist.isPresent()) { p = pExist.get(); p.setTelefono(txtTelefono.getText().trim()); } 
                else {
                    p = new Paciente(); p.setDni(dniIn); p.setNombres(txtNombres.getText().trim()); p.setApellidos(txtApellidos.getText().trim()); p.setFechaNacimiento(fNac); p.setTelefono(txtTelefono.getText().trim()); p.setNumeroHistoriaClinica("HC-" + (dbPacientesMock.size() + 1)); dbPacientesMock.add(p);
                }

                // Identificar al médico seleccionado eliminando el prefijo
                String nomMedSel = cbxMedico.getSelectedItem().toString().replace("Dra. ", "").replace("Dr. ", "");
                Medico medAsignado = dbMedicosMock.stream().filter(m -> m.getNombreCompleto().equals(nomMedSel)).findFirst().orElse(null);

                CitaMedica cita = new CitaMedica(); cita.setIdCita("C" + (p.getCitasMedicas().size() + 1));
                cita.setFechaHora(LocalDateTime.now()); cita.setMotivoConsulta(txtMotivo.getText().trim());
                cita.setEspecialidad(cbxEspecialidad.getSelectedItem().toString());
                cita.setMedicoAsignado(medAsignado);
                cita.programarCita();
                
                p.solicitarCita(cita); 
                JOptionPane.showMessageDialog(null, "Cita registrada exitosamente y derivada a " + cbxEspecialidad.getSelectedItem().toString());
                
                txtDni.setText(""); txtMotivo.setText(""); limpiar(); bloquear(false);
            } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage()); }
        });

        btnIrConsulta.addActionListener(e -> { new FrmPrincipal().setVisible(true); dispose(); });
    }

    private void actualizarMedicosRegistro() {
        cbxMedico.removeAllItems();
        String espSel = cbxEspecialidad.getSelectedItem().toString();
        for(Medico m : dbMedicosMock) {
            if(m.getEspecialidad().equals(espSel)) {
                String pref = m.getNombres().trim().endsWith("a") ? "Dra. " : "Dr. ";
                cbxMedico.addItem(pref + m.getNombreCompleto());
            }
        }
    }

    private void limpiar() { txtNombres.setText(""); txtApellidos.setText(""); txtTelefono.setText(""); txtFechaNac.setText(""); }
    private void bloquear(boolean b) { txtNombres.setEditable(!b); txtApellidos.setEditable(!b); txtFechaNac.setEditable(!b); Color cb = new Color(240, 245, 250); txtNombres.setBackground(b ? cb : Color.WHITE); txtApellidos.setBackground(b ? cb : Color.WHITE); txtFechaNac.setBackground(b ? cb : Color.WHITE); }
}