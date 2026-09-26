package vista;

import modelo.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    // Colecciones estáticas para mantener el estado (Stock y Médicos) durante toda la sesión
    public static List<Medico> dbMedicosMock = new ArrayList<>();
    public static List<Medicamento> dbMedicamentosMock = new ArrayList<>();

    // Formateador de Fecha y Hora
    private static final DateTimeFormatter FORMATO_FECHAHORA = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    static {
        // 1. Inicializar 5 Médicos de prueba
        String[] nombresMedicos = {"Dr. Carlos Torres", "Dra. Ana Luisa Pineda", "Dr. Roberto Fernández", "Dra. Lucía Ramírez", "Dr. Jorge Castillo"};
        String[] especialidades = {"Medicina General", "Pediatría", "Ginecología", "Cardiología", "Traumatología"};
        String[] cmps = {"CMP-55210", "CMP-61023", "CMP-48991", "CMP-70112", "CMP-33104"};

        for (int i = 0; i < 5; i++) {
            Medico m = new Medico();
            m.setNombres(nombresMedicos[i].split(" ")[1] + " " + nombresMedicos[i].split(" ")[2]); // Extrae nombres
            m.setApellidos("Vargas"); // Apellido genérico de prueba
            m.setEspecialidad(especialidades[i]);
            m.setCmp(cmps[i]);
            dbMedicosMock.add(m);
        }

        // 2. Inicializar 5 Medicamentos de prueba con stock variado
        String[] nomMedicamentos = {"Amoxicilina 500mg", "Paracetamol 500mg", "Ibuprofeno 400mg", "Omeprazol 20mg", "Azitromicina 250mg"};
        int[] stocks = {100, 200, 150, 80, 50};

        for (int i = 0; i < 5; i++) {
            Medicamento med = new Medicamento();
            med.setNombre(nomMedicamentos[i]);
            med.setStockDisponible(stocks[i]);
            med.setFechaVencimiento(LocalDate.now().plusYears(2));
            dbMedicamentosMock.add(med);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                FrmAtencion frame = new FrmAtencion();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public FrmAtencion() {
        setTitle("Consultorio Médico - Atención de Citas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 680); // Ajuste vertical para los nuevos paneles
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(245, 247, 250));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // --- BANNER SUPERIOR ---
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(0, 102, 204));
        panelHeader.setBounds(0, 0, 550, 60);
        contentPane.add(panelHeader);
        panelHeader.setLayout(null);

        JLabel lblTitulo = new JLabel("CONSULTORIO MÉDICO - ATENCIÓN");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setBounds(0, 15, 534, 30);
        panelHeader.add(lblTitulo);

        // --- PANEL 1: IDENTIDAD Y ASIGNACIÓN ---
        JPanel panelIdentidad = new JPanel();
        panelIdentidad.setBackground(Color.WHITE);
        panelIdentidad.setBorder(new TitledBorder(new LineBorder(new Color(180, 180, 180), 1, true), "1. Asignación y Búsqueda", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 102, 204)));
        panelIdentidad.setBounds(20, 75, 495, 140);
        contentPane.add(panelIdentidad);
        panelIdentidad.setLayout(null);

        JLabel lblMedico = new JLabel("Médico de Turno:");
        lblMedico.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMedico.setBounds(20, 25, 110, 20);
        panelIdentidad.add(lblMedico);

        // Desplegable de Médicos
        cbxMedicos = new JComboBox<>();
        cbxMedicos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbxMedicos.setBounds(135, 23, 230, 25);
        for (Medico m : dbMedicosMock) {
            cbxMedicos.addItem(m.getNombreCompleto() + " (" + m.getEspecialidad() + ")");
        }
        panelIdentidad.add(cbxMedicos);

        JLabel lblDni = new JLabel("DNI Paciente:");
        lblDni.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDni.setBounds(20, 60, 100, 20);
        panelIdentidad.add(lblDni);

        txtDni = new JTextField();
        txtDni.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtDni.setBounds(135, 58, 130, 25);
        panelIdentidad.add(txtDni);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(108, 117, 125));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBounds(275, 58, 90, 25);
        panelIdentidad.add(btnBuscar);

        JLabel lblPaciente = new JLabel("Paciente:");
        lblPaciente.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblPaciente.setBounds(20, 95, 80, 20);
        panelIdentidad.add(lblPaciente);

        txtNombrePaciente = new JTextField();
        txtNombrePaciente.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtNombrePaciente.setBounds(135, 93, 230, 25);
        txtNombrePaciente.setEditable(false);
        txtNombrePaciente.setBackground(new Color(240, 245, 250));
        panelIdentidad.add(txtNombrePaciente);

        JLabel lblFecha = new JLabel("Fecha y Hora:");
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblFecha.setBounds(380, 25, 100, 20);
        panelIdentidad.add(lblFecha);

        txtFechaHora = new JTextField(LocalDateTime.now().format(FORMATO_FECHAHORA));
        txtFechaHora.setFont(new Font("Segoe UI", Font.BOLD, 12));
        txtFechaHora.setBounds(380, 50, 100, 25);
        txtFechaHora.setEditable(false);
        txtFechaHora.setBackground(new Color(240, 245, 250));
        txtFechaHora.setHorizontalAlignment(JTextField.CENTER);
        panelIdentidad.add(txtFechaHora);

        // --- PANEL 2: EVALUACIÓN CLÍNICA ---
        JPanel panelEvaluacion = new JPanel();
        panelEvaluacion.setBackground(Color.WHITE);
        panelEvaluacion.setBorder(new TitledBorder(new LineBorder(new Color(180, 180, 180), 1, true), "2. Evaluación y Diagnóstico", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 102, 204)));
        panelEvaluacion.setBounds(20, 230, 495, 160);
        contentPane.add(panelEvaluacion);
        panelEvaluacion.setLayout(null);

        JLabel lblDiag = new JLabel("Diagnóstico Clínico:");
        lblDiag.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDiag.setBounds(20, 25, 130, 20);
        panelEvaluacion.add(lblDiag);

        JScrollPane scrollDiag = new JScrollPane();
        scrollDiag.setBounds(20, 50, 455, 40);
        panelEvaluacion.add(scrollDiag);
        txtDiagnostico = new JTextArea();
        txtDiagnostico.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        scrollDiag.setViewportView(txtDiagnostico);

        JLabel lblTrat = new JLabel("Tratamiento Recomendado:");
        lblTrat.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTrat.setBounds(20, 95, 180, 20);
        panelEvaluacion.add(lblTrat);

        JScrollPane scrollTrat = new JScrollPane();
        scrollTrat.setBounds(20, 115, 455, 30);
        panelEvaluacion.add(scrollTrat);
        txtTratamiento = new JTextArea();
        txtTratamiento.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        scrollTrat.setViewportView(txtTratamiento);

        // --- PANEL 3: RECETA Y FARMACIA ---
        JPanel panelReceta = new JPanel();
        panelReceta.setBackground(Color.WHITE);
        panelReceta.setBorder(new TitledBorder(new LineBorder(new Color(180, 180, 180), 1, true), "3. Prescripción de Medicamentos", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 102, 204)));
        panelReceta.setBounds(20, 400, 495, 90);
        contentPane.add(panelReceta);
        panelReceta.setLayout(null);

        JLabel lblMed = new JLabel("Medicamento:");
        lblMed.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMed.setBounds(20, 35, 100, 20);
        panelReceta.add(lblMed);

        cbxMedicamentos = new JComboBox<>();
        cbxMedicamentos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbxMedicamentos.setBounds(110, 32, 230, 25);
        actualizarComboMedicamentos(); // Método propio para cargar el combo
        panelReceta.add(cbxMedicamentos);

        JLabel lblCant = new JLabel("Cantidad:");
        lblCant.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblCant.setBounds(360, 35, 60, 20);
        panelReceta.add(lblCant);

        // Spinner para elegir cantidad de medicamentos (empieza en 1, min 1, max 50)
        spnCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        spnCantidad.setFont(new Font("Segoe UI", Font.BOLD, 13));
        spnCantidad.setBounds(425, 32, 50, 25);
        panelReceta.add(spnCantidad);

        // --- BOTONES INFERIORES ---
        JButton btnGuardar = new JButton("Guardar Atención");
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setBackground(new Color(40, 167, 69));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnGuardar.setBounds(20, 510, 240, 40);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        contentPane.add(btnGuardar);

        JButton btnImprimirReceta = new JButton("Imprimir Receta");
        btnImprimirReceta.setForeground(Color.WHITE);
        btnImprimirReceta.setBackground(new Color(23, 162, 184)); // Cyan Informativo
        btnImprimirReceta.setFocusPainted(false);
        btnImprimirReceta.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnImprimirReceta.setBounds(275, 510, 240, 40);
        btnImprimirReceta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        contentPane.add(btnImprimirReceta);

        JButton btnVolver = new JButton("Volver al Menú Principal");
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setBackground(new Color(108, 117, 125));
        btnVolver.setFocusPainted(false);
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVolver.setBounds(20, 565, 495, 35);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        contentPane.add(btnVolver);


        // ==========================================
        // EVENTOS DE BOTONES
        // ==========================================

        btnBuscar.addActionListener(e -> {
            String dniBuscado = txtDni.getText().trim();
            if(dniBuscado.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el DNI del paciente.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Optional<Paciente> pacienteOpt = FrmRegistro.dbPacientesMock.stream()
                .filter(p -> p.getDni() != null && p.getDni().equals(dniBuscado))
                .findFirst();

            if (pacienteOpt.isPresent()) {
                txtNombrePaciente.setText(pacienteOpt.get().getNombreCompleto());
                txtDiagnostico.requestFocus();
                // Actualiza la fecha a la hora exacta de la consulta
                txtFechaHora.setText(LocalDateTime.now().format(FORMATO_FECHAHORA));
            } else {
                txtNombrePaciente.setText("");
                JOptionPane.showMessageDialog(this, "Paciente no encontrado. Verifique en Recepción.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnGuardar.addActionListener(e -> {
            String dni = txtDni.getText().trim();
            
            if(txtNombrePaciente.getText().isEmpty() || txtDiagnostico.getText().isEmpty() || txtTratamiento.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete la búsqueda del paciente y los campos de diagnóstico/tratamiento.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                Optional<Paciente> pacienteOpt = FrmRegistro.dbPacientesMock.stream()
                    .filter(p -> p.getDni() != null && p.getDni().equals(dni))
                    .findFirst();

                if (pacienteOpt.isPresent()) {
                    Paciente paciente = pacienteOpt.get();
                    
                    Optional<CitaMedica> citaPendiente = paciente.getCitasMedicas().stream()
                            .filter(c -> c.getEstado() == CitaMedica.EstadoCita.PENDIENTE)
                            .findFirst(); 

                    if (citaPendiente.isPresent()) {
                        CitaMedica cita = citaPendiente.get();
                        
                        // Capturar selecciones de ComboBox
                        Medico medicoSeleccionado = dbMedicosMock.get(cbxMedicos.getSelectedIndex());
                        Medicamento medSeleccionado = dbMedicamentosMock.get(cbxMedicamentos.getSelectedIndex());
                        int cantidadSolicitada = (Integer) spnCantidad.getValue();

                        // Validar Inventario
                        if (cantidadSolicitada > medSeleccionado.getStockDisponible()) {
                            JOptionPane.showMessageDialog(this, "Stock insuficiente para " + medSeleccionado.getNombre() + ". Solo quedan " + medSeleccionado.getStockDisponible() + " unidades.", "Falta de Stock", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Lógica de Negocio (POO)
                        medicoSeleccionado.atenderCita(cita); 
                        AtencionMedica atencion = cita.getAtencionMedica();
                        atencion.setDiagnostico(txtDiagnostico.getText());
                        atencion.setTratamiento(txtTratamiento.getText());

                        DetalleReceta receta = new DetalleReceta();
                        receta.setMedicamento(medSeleccionado);
                        receta.setCantidad(cantidadSolicitada);
                        receta.setIndicaciones(txtTratamiento.getText());
                        
                        // emitirReceta ejecuta internamente el descuento de stock
                        medicoSeleccionado.emitirReceta(atencion, receta);

                        // Actualizar UI del ComboBox de medicamentos para reflejar el nuevo stock
                        actualizarComboMedicamentos();

                        JOptionPane.showMessageDialog(this, "Atención registrada con éxito.\nEl stock se ha descontado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        
                        // No limpiamos los campos aún para que el doctor pueda darle clic a "Imprimir Receta"
                    } else {
                        JOptionPane.showMessageDialog(this, "El paciente no tiene citas pendientes en Recepción.", "Sin Citas", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al procesar la atención.", "Error del Sistema", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnImprimirReceta.addActionListener(e -> generarImpresionReceta());

        btnVolver.addActionListener(e -> {
            new FrmPrincipal().setVisible(true);
            dispose();
        });
    }

    // ==========================================
    // MÉTODOS AUXILIARES (CLEAN CODE)
    // ==========================================

    private void actualizarComboMedicamentos() {
        // Guarda el índice seleccionado actual para no perderlo al recargar
        int indexActual = cbxMedicamentos.getSelectedIndex();
        cbxMedicamentos.removeAllItems();
        for (Medicamento m : dbMedicamentosMock) {
            cbxMedicamentos.addItem(m.getNombre() + " (Stock: " + m.getStockDisponible() + ")");
        }
        // Restaura el índice si era válido
        if(indexActual >= 0 && indexActual < cbxMedicamentos.getItemCount()) {
            cbxMedicamentos.setSelectedIndex(indexActual);
        }
    }

    private void generarImpresionReceta() {
        String paciente = txtNombrePaciente.getText().trim();
        String diagnostico = txtDiagnostico.getText().trim();
        String tratamiento = txtTratamiento.getText().trim();

        if (paciente.isEmpty() || tratamiento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe registrar la atención médica antes de imprimir el ticket.", "Receta vacía", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener datos exactos seleccionados
        Medico medicoSel = dbMedicosMock.get(cbxMedicos.getSelectedIndex());
        Medicamento medicamentoSel = dbMedicamentosMock.get(cbxMedicamentos.getSelectedIndex());
        int cantidad = (Integer) spnCantidad.getValue();

        String formatoReceta = 
            "==========================================\n" +
            "       RECETA MÉDICA - MINSA RURAL        \n" +
            "==========================================\n" +
            "Paciente : " + paciente + "\n" +
            "Fecha    : " + txtFechaHora.getText() + "\n" +
            "------------------------------------------\n" +
            "DIAGNÓSTICO:\n" + diagnostico + "\n\n" +
            "PRESCRIPCIÓN / TRATAMIENTO:\n" + tratamiento + "\n\n" +
            "FARMACIA:\n" +
            "➤ " + cantidad + "x " + medicamentoSel.getNombre() + "\n" +
            "==========================================\n" +
            "Firma Médico : " + medicoSel.getNombreCompleto() + "\n" +
            "Especialidad : " + medicoSel.getEspecialidad() + "\n" +
            "Colegiatura  : " + medicoSel.getCmp() + "\n" +
            "==========================================";

        JOptionPane.showMessageDialog(this, formatoReceta, "Impresora Virtual", JOptionPane.INFORMATION_MESSAGE);
        
        // Limpiar para el siguiente paciente una vez impreso
        txtDni.setText(""); 
        txtNombrePaciente.setText(""); 
        txtDiagnostico.setText(""); 
        txtTratamiento.setText("");
        spnCantidad.setValue(1);
    }
}