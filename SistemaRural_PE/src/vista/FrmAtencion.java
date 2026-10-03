package vista;

import modelo.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
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

// Importaciones de iText para generación de PDF
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class FrmAtencion extends JFrame {

    private JPanel contentPane;
    private JTextField txtDni, txtNombrePaciente, txtFechaHora;
    private JTextArea txtDiagnostico, txtTratamiento;
    private JComboBox<String> cbxEspecialidadTurno, cbxMedicos, cbxMedicamentos;
    private JSpinner spnCantidad;

    public static List<Medicamento> dbMedicamentosMock = new ArrayList<>();
    private static final DateTimeFormatter FMT_HORA = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    static {
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
        setBounds(100, 100, 950, 580); setLocationRelativeTo(null);
        contentPane = new JPanel(); contentPane.setBackground(new Color(245, 247, 250)); contentPane.setLayout(null); setContentPane(contentPane);

        JPanel panelHeader = new JPanel(); panelHeader.setBackground(new Color(0, 102, 204)); panelHeader.setBounds(0, 0, 950, 70); contentPane.add(panelHeader); panelHeader.setLayout(null);
        JLabel lblTitulo = new JLabel("CONSULTORIO MÉDICO - DIAGNÓSTICO Y RECETAS"); lblTitulo.setHorizontalAlignment(SwingConstants.CENTER); lblTitulo.setForeground(Color.WHITE); lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22)); lblTitulo.setBounds(0, 18, 934, 30); panelHeader.add(lblTitulo);

        // COLUMNA IZQUIERDA
        JPanel panelId = new JPanel(); panelId.setBackground(Color.WHITE); panelId.setBorder(new TitledBorder(new LineBorder(new Color(180,180,180), 1, true), "1. Asignación y Paciente", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 102, 204))); panelId.setBounds(20, 85, 430, 180); contentPane.add(panelId); panelId.setLayout(null);

        JLabel lblDni = new JLabel("DNI Paciente:"); lblDni.setBounds(20, 30, 90, 20); panelId.add(lblDni);
        txtDni = new JTextField(); txtDni.setFont(new Font("Segoe UI", Font.BOLD, 14)); txtDni.setBounds(115, 28, 120, 25); panelId.add(txtDni);
        JButton btnBuscar = new JButton("Buscar"); btnBuscar.setBackground(new Color(108, 117, 125)); btnBuscar.setForeground(Color.WHITE); btnBuscar.setBounds(245, 28, 80, 25); panelId.add(btnBuscar);

        JLabel lblPac = new JLabel("Paciente:"); lblPac.setBounds(20, 65, 90, 20); panelId.add(lblPac);
        txtNombrePaciente = new JTextField(); txtNombrePaciente.setEditable(false); txtNombrePaciente.setBackground(new Color(240, 245, 250)); txtNombrePaciente.setBounds(115, 63, 295, 25); panelId.add(txtNombrePaciente);

        JLabel lblEsp = new JLabel("Especialidad:"); lblEsp.setBounds(20, 100, 90, 20); panelId.add(lblEsp);
        cbxEspecialidadTurno = new JComboBox<>(new String[]{"Medicina General", "Pediatría", "Ginecología", "Cardiología", "Traumatología"}); cbxEspecialidadTurno.setBounds(115, 98, 295, 25); panelId.add(cbxEspecialidadTurno);

        JLabel lblMedico = new JLabel("Médico Turno:"); lblMedico.setBounds(20, 135, 90, 20); panelId.add(lblMedico);
        cbxMedicos = new JComboBox<>(); cbxMedicos.setBounds(115, 133, 295, 25); panelId.add(cbxMedicos);

        JPanel panelEval = new JPanel(); panelEval.setBackground(Color.WHITE); panelEval.setBorder(new TitledBorder(new LineBorder(new Color(180,180,180), 1, true), "2. Evaluación Clínica", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 102, 204))); panelEval.setBounds(20, 280, 430, 230); contentPane.add(panelEval); panelEval.setLayout(null);
        JLabel lblDiag = new JLabel("Diagnóstico Médico:"); lblDiag.setBounds(20, 25, 200, 20); panelEval.add(lblDiag);
        JScrollPane scrDiag = new JScrollPane(); scrDiag.setBounds(20, 50, 390, 60); panelEval.add(scrDiag); txtDiagnostico = new JTextArea(); txtDiagnostico.setLineWrap(true); scrDiag.setViewportView(txtDiagnostico);
        JLabel lblTrat = new JLabel("Tratamiento / Recomendaciones:"); lblTrat.setBounds(20, 120, 250, 20); panelEval.add(lblTrat);
        JScrollPane scrTrat = new JScrollPane(); scrTrat.setBounds(20, 145, 390, 65); panelEval.add(scrTrat); txtTratamiento = new JTextArea(); txtTratamiento.setLineWrap(true); scrTrat.setViewportView(txtTratamiento);

        // COLUMNA DERECHA
        JPanel panelReceta = new JPanel(); panelReceta.setBackground(Color.WHITE); panelReceta.setBorder(new TitledBorder(new LineBorder(new Color(180,180,180), 1, true), "3. Farmacia y Prescripción", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 102, 204))); panelReceta.setBounds(470, 85, 440, 160); contentPane.add(panelReceta); panelReceta.setLayout(null);
        JLabel lblFec = new JLabel("Fecha y Hora:"); lblFec.setBounds(20, 30, 100, 20); panelReceta.add(lblFec); txtFechaHora = new JTextField(LocalDateTime.now().format(FMT_HORA)); txtFechaHora.setEditable(false); txtFechaHora.setBackground(new Color(240, 245, 250)); txtFechaHora.setBounds(120, 28, 140, 25); panelReceta.add(txtFechaHora);
        JLabel lblMed = new JLabel("Medicamento:"); lblMed.setBounds(20, 70, 100, 20); panelReceta.add(lblMed); cbxMedicamentos = new JComboBox<>(); cbxMedicamentos.setBounds(120, 68, 290, 25); actualizarComboMeds(); panelReceta.add(cbxMedicamentos);
        JLabel lblCant = new JLabel("Cantidad:"); lblCant.setBounds(20, 110, 80, 20); panelReceta.add(lblCant); spnCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1)); spnCantidad.setBounds(120, 108, 60, 25); panelReceta.add(spnCantidad);

        // BOTONES
        JButton btnGuardar = new JButton("Guardar Atención Médica"); btnGuardar.setBackground(new Color(40, 167, 69)); btnGuardar.setForeground(Color.WHITE); btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 15)); btnGuardar.setBounds(470, 270, 440, 50); contentPane.add(btnGuardar);
        JButton btnImprimir = new JButton("Descargar Receta PDF"); btnImprimir.setBackground(new Color(23, 162, 184)); btnImprimir.setForeground(Color.WHITE); btnImprimir.setFont(new Font("Segoe UI", Font.BOLD, 15)); btnImprimir.setBounds(470, 340, 440, 50); contentPane.add(btnImprimir);
        JButton btnVolver = new JButton("Volver al Menú Principal"); btnVolver.setBackground(new Color(108, 117, 125)); btnVolver.setForeground(Color.WHITE); btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 15)); btnVolver.setBounds(470, 460, 440, 50); contentPane.add(btnVolver);

        // EVENTOS
        cbxEspecialidadTurno.addActionListener(e -> actualizarMedicosAtencion());
        actualizarMedicosAtencion();

        btnBuscar.addActionListener(e -> {
            Optional<Paciente> p = FrmRegistro.dbPacientesMock.stream().filter(pac -> pac.getDni() != null && pac.getDni().equals(txtDni.getText().trim())).findFirst();
            if (p.isPresent()) { 
                txtNombrePaciente.setText(p.get().getNombreCompleto()); txtFechaHora.setText(LocalDateTime.now().format(FMT_HORA)); 
                
                Optional<CitaMedica> cp = p.get().getCitasMedicas().stream().filter(c -> c.getEstado() == CitaMedica.EstadoCita.PENDIENTE).findFirst();
                if(cp.isPresent() && cp.get().getEspecialidad() != null) {
                    cbxEspecialidadTurno.setSelectedItem(cp.get().getEspecialidad()); 
                    if(cp.get().getMedicoAsignado() != null) {
                        String pref = cp.get().getMedicoAsignado().getNombres().trim().endsWith("a") ? "Dra. " : "Dr. ";
                        cbxMedicos.setSelectedItem(pref + cp.get().getMedicoAsignado().getNombreCompleto()); 
                    }
                    cbxEspecialidadTurno.setEnabled(false);
                    cbxMedicos.setEnabled(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Paciente no encontrado en Triaje.");
                cbxEspecialidadTurno.setEnabled(true);
            }
        });

        btnGuardar.addActionListener(e -> {
            if(txtNombrePaciente.getText().isEmpty() || txtDiagnostico.getText().isEmpty()) { JOptionPane.showMessageDialog(this, "Llene los campos clínicos."); return; }
            Optional<Paciente> pOpt = FrmRegistro.dbPacientesMock.stream().filter(pac -> pac.getDni() != null && pac.getDni().equals(txtDni.getText().trim())).findFirst();
            if (pOpt.isPresent()) {
                Paciente p = pOpt.get();
                Optional<CitaMedica> citaPendiente = p.getCitasMedicas().stream().filter(c -> c.getEstado() == CitaMedica.EstadoCita.PENDIENTE).findFirst(); 
                if (citaPendiente.isPresent()) {
                    CitaMedica cita = citaPendiente.get();
                    
                    String nombreSel = cbxMedicos.getSelectedItem().toString().replace("Dra. ", "").replace("Dr. ", "");
                    Medico ms = FrmRegistro.dbMedicosMock.stream().filter(m -> m.getNombreCompleto().equals(nombreSel)).findFirst().orElse(null);
                    
                    Medicamento medS = dbMedicamentosMock.get(cbxMedicamentos.getSelectedIndex());
                    int cant = (Integer) spnCantidad.getValue();
                    if (cant > medS.getStockDisponible()) { JOptionPane.showMessageDialog(this, "Stock insuficiente."); return; }
                    
                    if (!ms.getCitasAsignadas().contains(cita)) ms.getCitasAsignadas().add(cita);

                    ms.atenderCita(cita); 
                    AtencionMedica atencion = cita.getAtencionMedica(); atencion.setDiagnostico(txtDiagnostico.getText()); atencion.setTratamiento(txtTratamiento.getText());
                    DetalleReceta receta = new DetalleReceta(); receta.setMedicamento(medS); receta.setCantidad(cant); receta.setIndicaciones(txtTratamiento.getText());
                    ms.emitirReceta(atencion, receta); actualizarComboMeds();
                    JOptionPane.showMessageDialog(this, "Atención registrada. Stock descontado.");
                } else JOptionPane.showMessageDialog(this, "El paciente no tiene citas pendientes.");
            }
        });
        
        btnImprimir.addActionListener(e -> {
            if (txtNombrePaciente.getText().isEmpty() || txtDiagnostico.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Registre la atención médica primero para generar la receta.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Recopilar datos
            String nombreSel = cbxMedicos.getSelectedItem().toString().replace("Dra. ", "").replace("Dr. ", "");
            Medico ms = FrmRegistro.dbMedicosMock.stream().filter(m -> m.getNombreCompleto().equals(nombreSel)).findFirst().orElse(null);
            Medicamento medS = dbMedicamentosMock.get(cbxMedicamentos.getSelectedIndex());
            String prefijo = ms.getNombres().trim().endsWith("a") ? "Dra. " : "Dr. ";
            int cant = (Integer) spnCantidad.getValue();

            // Nombre del PDF
            String nombreArchivo = "Receta_" + txtDni.getText().trim() + "_" + System.currentTimeMillis() + ".pdf";

            try {
                // Generación de PDF con iText
                Document documento = new Document();
                PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
                documento.open();

                // Definir fuentes
                com.itextpdf.text.Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.DARK_GRAY);
                com.itextpdf.text.Font fontSubtitulo = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.GRAY);
                com.itextpdf.text.Font fontBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
                com.itextpdf.text.Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

                // Encabezado
                Paragraph header = new Paragraph("MINISTERIO DE SALUD - RECETA MÉDICA", fontTitulo);
                header.setAlignment(Element.ALIGN_CENTER);
                documento.add(header);
                
                Paragraph subheader = new Paragraph("Centro de Salud Rural - San Juan de Lurigancho", fontSubtitulo);
                subheader.setAlignment(Element.ALIGN_CENTER);
                documento.add(subheader);
                
                documento.add(new Chunk("\n"));
                LineSeparator separador = new LineSeparator();
                separador.setLineColor(BaseColor.GRAY);
                documento.add(new Chunk(separador));
                documento.add(new Chunk("\n\n"));

                // Datos del Paciente
                documento.add(new Paragraph("DATOS DEL PACIENTE", fontBold));
                documento.add(new Paragraph("Paciente: " + txtNombrePaciente.getText(), fontNormal));
                documento.add(new Paragraph("DNI: " + txtDni.getText(), fontNormal));
                documento.add(new Paragraph("Fecha de Atención: " + txtFechaHora.getText(), fontNormal));
                documento.add(new Chunk("\n"));

                // Diagnóstico y Tratamiento
                documento.add(new Paragraph("DIAGNÓSTICO CLÍNICO", fontBold));
                documento.add(new Paragraph(txtDiagnostico.getText(), fontNormal));
                documento.add(new Chunk("\n"));
                
                documento.add(new Paragraph("PRESCRIPCIÓN FARMACÉUTICA", fontBold));
                documento.add(new Paragraph("➤ " + cant + "x " + medS.getNombre(), fontNormal));
                documento.add(new Paragraph("Indicaciones: " + txtTratamiento.getText(), fontNormal));
                documento.add(new Chunk("\n\n\n\n\n")); // Espaciado para la firma

                // Firma del Doctor
                documento.add(new Chunk(separador));
                Paragraph firma = new Paragraph("Firma y Sello del Médico Tratante\n" + prefijo + ms.getNombreCompleto() + "\nEspecialidad: " + ms.getEspecialidad() + "\nColegiatura: " + ms.getCmp() + " [ACTIVO]", fontNormal);
                firma.setAlignment(Element.ALIGN_CENTER);
                documento.add(firma);

                documento.close();

                // Abrir PDF automáticamente usando java.awt.Desktop
                File archivoPDF = new File(nombreArchivo);
                if (archivoPDF.exists()) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(archivoPDF);
                    } else {
                        JOptionPane.showMessageDialog(this, "PDF guardado como: " + nombreArchivo);
                    }
                }
                
                // Limpieza de formulario post-impresión
                txtDni.setText(""); txtNombrePaciente.setText(""); txtDiagnostico.setText(""); txtTratamiento.setText("");
                cbxEspecialidadTurno.setEnabled(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error crítico al generar el documento PDF: " + ex.getMessage(), "Error I/O", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnVolver.addActionListener(e -> { new FrmPrincipal().setVisible(true); dispose(); });
    }

    private void actualizarMedicosAtencion() {
        cbxMedicos.removeAllItems();
        String espSel = cbxEspecialidadTurno.getSelectedItem().toString();
        for(Medico m : FrmRegistro.dbMedicosMock) {
            if(m.getEspecialidad().equals(espSel)) {
                String pref = m.getNombres().trim().endsWith("a") ? "Dra. " : "Dr. ";
                cbxMedicos.addItem(pref + m.getNombreCompleto());
            }
        }
    }

    private void actualizarComboMeds() {
        int idx = cbxMedicamentos.getSelectedIndex(); cbxMedicamentos.removeAllItems();
        for (Medicamento m : dbMedicamentosMock) cbxMedicamentos.addItem(m.getNombre() + " (Stock: " + m.getStockDisponible() + ")");
        if(idx >= 0 && idx < cbxMedicamentos.getItemCount()) cbxMedicamentos.setSelectedIndex(idx);
    }
}