package vista;

import modelo.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class FrmAtencion extends JFrame {

    private JPanel contentPane;
    private JTextField txtDni;
    private JTextField txtNombrePaciente;
    private JTextField txtFechaHora;
    private JTextArea txtDiagnostico;
    private JTextArea txtTratamiento;
    private JComboBox<String> cbxMedicos;
    private JComboBox<String> cbxMedicamentos;
    private JSpinner spnCantidad;

    public static List<Medico> dbMedicosMock = new ArrayList<>();
    public static List<Medicamento> dbMedicamentosMock = new ArrayList<>();
    private static final DateTimeFormatter FMT_HORA = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    static {
        // CORRECCIÓN POO: Se separan los nombres y apellidos explícitamente para evitar el "null"
        String[] nombres = {"Carlos", "Ana Luisa", "Roberto", "Lucía", "Jorge"};
        String[] apellidos = {"Torres", "Pineda", "Fernández", "Ramírez", "Castillo"};
        String[] esp = {"Medicina General", "Pediatría", "Ginecología", "Cardiología", "Traumatología"};
        
        for (int i = 0; i < 5; i++) { 
            Medico m = new Medico(); 
            m.setNombres(nombres[i]); 
            m.setApellidos(apellidos[i]); 
            m.setEspecialidad(esp[i]); 
            m.setCmp("CMP-" + (5000+i)); 
            dbMedicosMock.add(m); 
        }
        
        String[] nMeds = {"Amoxicilina 500mg", "Paracetamol 500mg", "Ibuprofeno 400mg", "Omeprazol 20mg", "Azitromicina 250mg"};
        int[] stks = {100, 200, 150, 80, 50};
        
        for (int i = 0; i < 5; i++) { 
            Medicamento med = new Medicamento(); 
            med.setNombre(nMeds[i]); 
            med.setStockDisponible(stks[i]); 
            med.setFechaVencimiento(LocalDate.now().plusYears(2)); 
            dbMedicamentosMock.add(med); 
        }
    }

    public FrmAtencion() {
        setTitle("Consultorio Médico - Atención");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 950, 580);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(245, 247, 250));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // HEADER
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(0, 102, 204));
        panelHeader.setBounds(0, 0, 950, 70);
        contentPane.add(panelHeader);
        panelHeader.setLayout(null);
        JLabel lblTitulo = new JLabel("CONSULTORIO MÉDICO - DIAGNÓSTICO Y RECETAS");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setBounds(0, 18, 934, 30);
        panelHeader.add(lblTitulo);

        // COLUMNA IZQUIERDA
        JPanel panelId = new JPanel();
        panelId.setBackground(Color.WHITE);
        panelId.setBorder(new TitledBorder(new LineBorder(new Color(180,180,180), 1, true), "1. Asignación y Paciente", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 102, 204)));
        panelId.setBounds(20, 85, 430, 160);
        contentPane.add(panelId);
        panelId.setLayout(null);

        JLabel lblMedico = new JLabel("Médico Turno:"); lblMedico.setFont(new Font("Segoe UI", Font.PLAIN, 13)); lblMedico.setBounds(20, 30, 100, 20); panelId.add(lblMedico);
        cbxMedicos = new JComboBox<>(); cbxMedicos.setBounds(115, 28, 295, 25);
        for(Medico m : dbMedicosMock) cbxMedicos.addItem("Dr/a. " + m.getNombreCompleto() + " (" + m.getEspecialidad() + ")");
        panelId.add(cbxMedicos);

        JLabel lblDni = new JLabel("DNI Paciente:"); lblDni.setFont(new Font("Segoe UI", Font.PLAIN, 13)); lblDni.setBounds(20, 70, 90, 20); panelId.add(lblDni);
        txtDni = new JTextField(); txtDni.setFont(new Font("Segoe UI", Font.BOLD, 14)); txtDni.setBounds(115, 68, 120, 25); panelId.add(txtDni);
        JButton btnBuscar = new JButton("Buscar"); btnBuscar.setBackground(new Color(108, 117, 125)); btnBuscar.setForeground(Color.WHITE); btnBuscar.setBounds(245, 68, 80, 25); panelId.add(btnBuscar);

        JLabel lblPac = new JLabel("Paciente:"); lblPac.setFont(new Font("Segoe UI", Font.PLAIN, 13)); lblPac.setBounds(20, 110, 90, 20); panelId.add(lblPac);
        txtNombrePaciente = new JTextField(); txtNombrePaciente.setEditable(false); txtNombrePaciente.setBackground(new Color(240, 245, 250)); txtNombrePaciente.setBounds(115, 108, 295, 25); panelId.add(txtNombrePaciente);

        JPanel panelEval = new JPanel();
        panelEval.setBackground(Color.WHITE);
        panelEval.setBorder(new TitledBorder(new LineBorder(new Color(180,180,180), 1, true), "2. Evaluación Clínica", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 102, 204)));
        panelEval.setBounds(20, 260, 430, 250);
        contentPane.add(panelEval);
        panelEval.setLayout(null);

        JLabel lblDiag = new JLabel("Diagnóstico Médico:"); lblDiag.setFont(new Font("Segoe UI", Font.PLAIN, 13)); lblDiag.setBounds(20, 25, 200, 20); panelEval.add(lblDiag);
        JScrollPane scrDiag = new JScrollPane(); scrDiag.setBounds(20, 50, 390, 70); panelEval.add(scrDiag);
        txtDiagnostico = new JTextArea(); txtDiagnostico.setLineWrap(true); scrDiag.setViewportView(txtDiagnostico);

        JLabel lblTrat = new JLabel("Tratamiento / Recomendaciones:"); lblTrat.setFont(new Font("Segoe UI", Font.PLAIN, 13)); lblTrat.setBounds(20, 130, 250, 20); panelEval.add(lblTrat);
        JScrollPane scrTrat = new JScrollPane(); scrTrat.setBounds(20, 155, 390, 75); panelEval.add(scrTrat);
        txtTratamiento = new JTextArea(); txtTratamiento.setLineWrap(true); scrTrat.setViewportView(txtTratamiento);

        // COLUMNA DERECHA
        JPanel panelReceta = new JPanel();
        panelReceta.setBackground(Color.WHITE);
        panelReceta.setBorder(new TitledBorder(new LineBorder(new Color(180,180,180), 1, true), "3. Farmacia y Prescripción", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 102, 204)));
        panelReceta.setBounds(470, 85, 440, 160);
        contentPane.add(panelReceta);
        panelReceta.setLayout(null);

        JLabel lblFec = new JLabel("Fecha y Hora:"); lblFec.setFont(new Font("Segoe UI", Font.PLAIN, 13)); lblFec.setBounds(20, 30, 100, 20); panelReceta.add(lblFec);
        txtFechaHora = new JTextField(LocalDateTime.now().format(FMT_HORA)); txtFechaHora.setEditable(false); txtFechaHora.setBackground(new Color(240, 245, 250)); txtFechaHora.setBounds(120, 28, 140, 25); panelReceta.add(txtFechaHora);

        JLabel lblMed = new JLabel("Medicamento:"); lblMed.setFont(new Font("Segoe UI", Font.PLAIN, 13)); lblMed.setBounds(20, 70, 100, 20); panelReceta.add(lblMed);
        cbxMedicamentos = new JComboBox<>(); cbxMedicamentos.setBounds(120, 68, 290, 25); actualizarCombo(); panelReceta.add(cbxMedicamentos);

        JLabel lblCant = new JLabel("Cantidad:"); lblCant.setFont(new Font("Segoe UI", Font.PLAIN, 13)); lblCant.setBounds(20, 110, 80, 20); panelReceta.add(lblCant);
        spnCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1)); spnCantidad.setBounds(120, 108, 60, 25); panelReceta.add(spnCantidad);

        // BOTONES GRANDES (Derecha Abajo)
        JButton btnGuardar = new JButton("Guardar Atención Médica");
        btnGuardar.setBackground(new Color(40, 167, 69)); btnGuardar.setForeground(Color.WHITE); btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 15)); btnGuardar.setBounds(470, 270, 440, 50); btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR)); contentPane.add(btnGuardar);

        JButton btnImprimir = new JButton("Imprimir Receta / Ticket");
        btnImprimir.setBackground(new Color(23, 162, 184)); btnImprimir.setForeground(Color.WHITE); btnImprimir.setFont(new Font("Segoe UI", Font.BOLD, 15)); btnImprimir.setBounds(470, 340, 440, 50); btnImprimir.setCursor(new Cursor(Cursor.HAND_CURSOR)); contentPane.add(btnImprimir);

        JButton btnVolver = new JButton("Volver al Menú Principal");
        btnVolver.setBackground(new Color(108, 117, 125)); btnVolver.setForeground(Color.WHITE); btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 15)); btnVolver.setBounds(470, 460, 440, 50); btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR)); contentPane.add(btnVolver);

        // EVENTOS
        btnBuscar.addActionListener(e -> {
            Optional<Paciente> p = FrmRegistro.dbPacientesMock.stream().filter(pac -> pac.getDni() != null && pac.getDni().equals(txtDni.getText().trim())).findFirst();
            if (p.isPresent()) { txtNombrePaciente.setText(p.get().getNombreCompleto()); txtFechaHora.setText(LocalDateTime.now().format(FMT_HORA)); } 
            else JOptionPane.showMessageDialog(this, "Paciente no encontrado en Triaje.");
        });

        btnGuardar.addActionListener(e -> {
            if(txtNombrePaciente.getText().isEmpty() || txtDiagnostico.getText().isEmpty()) { JOptionPane.showMessageDialog(this, "Llene los campos clínicos."); return; }
            Optional<Paciente> pOpt = FrmRegistro.dbPacientesMock.stream().filter(pac -> pac.getDni() != null && pac.getDni().equals(txtDni.getText().trim())).findFirst();
            if (pOpt.isPresent()) {
                Paciente p = pOpt.get();
                Optional<CitaMedica> citaPendiente = p.getCitasMedicas().stream().filter(c -> c.getEstado() == CitaMedica.EstadoCita.PENDIENTE).findFirst(); 
                if (citaPendiente.isPresent()) {
                    CitaMedica cita = citaPendiente.get();
                    Medico ms = dbMedicosMock.get(cbxMedicos.getSelectedIndex());
                    Medicamento medS = dbMedicamentosMock.get(cbxMedicamentos.getSelectedIndex());
                    int cant = (Integer) spnCantidad.getValue();
                    if (cant > medS.getStockDisponible()) { JOptionPane.showMessageDialog(this, "Stock insuficiente."); return; }
                    ms.atenderCita(cita); 
                    AtencionMedica atencion = cita.getAtencionMedica(); atencion.setDiagnostico(txtDiagnostico.getText()); atencion.setTratamiento(txtTratamiento.getText());
                    DetalleReceta receta = new DetalleReceta(); receta.setMedicamento(medS); receta.setCantidad(cant); receta.setIndicaciones(txtTratamiento.getText());
                    ms.emitirReceta(atencion, receta); actualizarCombo();
                    JOptionPane.showMessageDialog(this, "Atención registrada. Stock descontado.");
                } else { JOptionPane.showMessageDialog(this, "El paciente no tiene citas pendientes."); }
            }
        });
        
        btnImprimir.addActionListener(e -> {
            if (txtNombrePaciente.getText().isEmpty() || txtDiagnostico.getText().isEmpty()) return;
            Medico ms = dbMedicosMock.get(cbxMedicos.getSelectedIndex()); Medicamento medS = dbMedicamentosMock.get(cbxMedicamentos.getSelectedIndex());
            String t = "==========================================\nRECETA MÉDICA - MINSA RURAL\n==========================================\n" +
            "Paciente : " + txtNombrePaciente.getText() + "\nFecha    : " + txtFechaHora.getText() + "\n" +
            "DIAGNÓSTICO:\n" + txtDiagnostico.getText() + "\n\nFARMACIA:\n➤ " + spnCantidad.getValue() + "x " + medS.getNombre() + "\n==========================================\n" +
            "Firma : " + ms.getNombreCompleto() + " (" + ms.getCmp() + ")\n==========================================";
            JOptionPane.showMessageDialog(this, t, "Impresora Virtual", JOptionPane.INFORMATION_MESSAGE);
            txtDni.setText(""); txtNombrePaciente.setText(""); txtDiagnostico.setText(""); txtTratamiento.setText("");
        });
        btnVolver.addActionListener(e -> { new FrmPrincipal().setVisible(true); dispose(); });
    }

    private void actualizarCombo() {
        int idx = cbxMedicamentos.getSelectedIndex(); cbxMedicamentos.removeAllItems();
        for (Medicamento m : dbMedicamentosMock) cbxMedicamentos.addItem(m.getNombre() + " (Stock: " + m.getStockDisponible() + ")");
        if(idx >= 0 && idx < cbxMedicamentos.getItemCount()) cbxMedicamentos.setSelectedIndex(idx);
    }
}