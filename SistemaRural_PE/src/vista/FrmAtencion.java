package vista;

import modelo.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class FrmAtencion extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JTextField txtDni;
    private JTextField txtNombrePaciente;
    private JTextArea txtDiagnostico;
    private JTextArea txtTratamiento;

    // Datos simulados (Mock) para el médico y farmacia
    private Medico medicoTurno;
    private Medicamento amoxicilina;
    private JButton btnImprimirReceta;

    public FrmAtencion() {
        // Inicializar datos en duro para la atención
        medicoTurno = new Medico();
        medicoTurno.setNombres("Dra. María");
        medicoTurno.setApellidos("Gómez");
        medicoTurno.setEspecialidad("Medicina General");

        amoxicilina = new Medicamento();
        amoxicilina.setNombre("Amoxicilina 500mg");
        amoxicilina.setStockDisponible(100);
        amoxicilina.setFechaVencimiento(LocalDate.now().plusYears(1));

        setTitle("Consultorio Médico - Atención de Citas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 520); // Se aumentó el alto de la ventana
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("Registrar Atención Médica");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTitulo.setBounds(20, 10, 250, 20);
        contentPane.add(lblTitulo);

        // --- FILA 1: DNI y Botón Buscar ---
        JLabel lblDni = new JLabel("DNI en sala:");
        lblDni.setBounds(20, 50, 100, 20);
        contentPane.add(lblDni);

        txtDni = new JTextField();
        txtDni.setBounds(110, 50, 120, 20);
        contentPane.add(txtDni);
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(240, 48, 100, 25);
        contentPane.add(btnBuscar);

        // --- FILA 2: Mostrar Nombres Automáticamente ---
        JLabel lblPaciente = new JLabel("Paciente:");
        lblPaciente.setBounds(20, 90, 80, 20);
        contentPane.add(lblPaciente);
        
        txtNombrePaciente = new JTextField();
        txtNombrePaciente.setBounds(110, 90, 290, 20);
        txtNombrePaciente.setEditable(false); // Bloqueado para que el usuario no lo edite
        txtNombrePaciente.setBackground(new Color(240, 240, 240));
        contentPane.add(txtNombrePaciente);

        // --- FILA 3: Diagnóstico ---
        JLabel lblDiag = new JLabel("Diagnóstico:");
        lblDiag.setBounds(20, 130, 100, 20);
        contentPane.add(lblDiag);

        JScrollPane scrollDiag = new JScrollPane();
        scrollDiag.setBounds(20, 150, 380, 60);
        contentPane.add(scrollDiag);
        txtDiagnostico = new JTextArea();
        scrollDiag.setViewportView(txtDiagnostico);

        // --- FILA 4: Tratamiento ---
        JLabel lblTrat = new JLabel("Tratamiento / Receta:");
        lblTrat.setBounds(20, 230, 150, 20);
        contentPane.add(lblTrat);

        JScrollPane scrollTrat = new JScrollPane();
        scrollTrat.setBounds(20, 250, 380, 60);
        contentPane.add(scrollTrat);
        txtTratamiento = new JTextArea();
        scrollTrat.setViewportView(txtTratamiento);

        // --- BOTONES DE ACCIÓN ---
        JButton btnGuardar = new JButton("Guardar Atención");
        btnGuardar.setBounds(20, 344, 183, 30);
        contentPane.add(btnGuardar);

        JButton btnVolver = new JButton("Volver al Menú Principal");
        btnVolver.setBounds(20, 420, 380, 30);
        contentPane.add(btnVolver);
        
        btnImprimirReceta = new JButton("Imprimir Receta");
        btnImprimirReceta.addActionListener(this);
        btnImprimirReceta.setBounds(213, 344, 183, 30);
        contentPane.add(btnImprimirReceta);


        // ==========================================
        // EVENTO 1: BUSCAR PACIENTE Y RELLENAR DATOS
        // ==========================================
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String dniBuscado = txtDni.getText().trim();
                
                if(dniBuscado.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese un DNI.");
                    return;
                }

                // Uso de Programación Funcional para encontrar al paciente exacto
                Optional<Paciente> pacienteEncontrado = FrmRegistro.dbPacientesMock.stream()
                    .filter(p -> p.getDni() != null && p.getDni().equals(dniBuscado))
                    .findFirst();

                if (pacienteEncontrado.isPresent()) {
                    // Si lo encuentra, extrae los datos y los coloca en la caja de texto bloqueada
                    Paciente p = pacienteEncontrado.get();
                    txtNombrePaciente.setText(p.getNombreCompleto());
                    txtDiagnostico.requestFocus(); // Mueve el cursor al área de diagnóstico automáticamente
                } else {
                    txtNombrePaciente.setText("");
                    JOptionPane.showMessageDialog(null, "No se encontró ningún paciente con el DNI: " + dniBuscado);
                }
            }
        });

        // ==========================================
        // EVENTO 2: GUARDAR ATENCIÓN MÉDICA
        // ==========================================
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String dni = txtDni.getText().trim();
                String pacienteConfirmado = txtNombrePaciente.getText();
                
                // Validación rápida: Asegurarse de que el médico haya buscado al paciente primero
                if(pacienteConfirmado.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe buscar y validar un paciente antes de guardar la atención.");
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
                            
                            // Lógica de atención médica según UML
                            medicoTurno.atenderCita(cita); 
                            AtencionMedica atencion = cita.getAtencionMedica();
                            atencion.setDiagnostico(txtDiagnostico.getText());
                            atencion.setTratamiento(txtTratamiento.getText());

                            // Generar Receta y descontar stock
                            DetalleReceta receta = new DetalleReceta();
                            receta.setMedicamento(amoxicilina);
                            receta.setCantidad(20);
                            receta.setIndicaciones(txtTratamiento.getText());
                            
                            medicoTurno.emitirReceta(atencion, receta);

                            JOptionPane.showMessageDialog(null, "Atención registrada con éxito para " + paciente.getNombreCompleto() + ".\nStock restante de " + amoxicilina.getNombre() + ": " + amoxicilina.getStockDisponible());
                            
                            // Limpiar los campos para el siguiente paciente
                            txtDni.setText(""); txtNombrePaciente.setText(""); 
                            txtDiagnostico.setText(""); txtTratamiento.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "El paciente no tiene citas pendientes.");
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al procesar la atención. Verifique los datos.");
                }
            }
        });

        // ==========================================
        // EVENTO 3: VOLVER AL MENÚ
        // ==========================================
        btnVolver.addActionListener(e -> {
            new FrmPrincipal().setVisible(true);
            dispose();
        });
    }
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnImprimirReceta) {
			actionPerformedBtnGuardar_1JButton(e);
		}
	}
	protected void actionPerformedBtnGuardar_1JButton(ActionEvent e) {
	}
}