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

public class FrmAtencion extends JFrame {

    private JPanel contentPane;
    private JTextField txtDni;
    private JTextArea txtDiagnostico;
    private JTextArea txtTratamiento;

    // Datos simulados (Mock) para el médico y farmacia
    private Medico medicoTurno;
    private Medicamento amoxicilina;

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
        setBounds(100, 100, 450, 480);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("Registrar Atención Médica");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTitulo.setBounds(20, 10, 250, 20);
        contentPane.add(lblTitulo);

        JLabel lblDni = new JLabel("DNI del Paciente en sala:");
        lblDni.setBounds(20, 50, 150, 20);
        contentPane.add(lblDni);

        txtDni = new JTextField();
        txtDni.setBounds(180, 50, 150, 20);
        contentPane.add(txtDni);

        JLabel lblDiag = new JLabel("Diagnóstico:");
        lblDiag.setBounds(20, 90, 100, 20);
        contentPane.add(lblDiag);

        JScrollPane scrollDiag = new JScrollPane();
        scrollDiag.setBounds(20, 110, 390, 60);
        contentPane.add(scrollDiag);
        txtDiagnostico = new JTextArea();
        scrollDiag.setViewportView(txtDiagnostico);

        JLabel lblTrat = new JLabel("Tratamiento / Receta:");
        lblTrat.setBounds(20, 190, 150, 20);
        contentPane.add(lblTrat);

        JScrollPane scrollTrat = new JScrollPane();
        scrollTrat.setBounds(20, 210, 390, 60);
        contentPane.add(scrollTrat);
        txtTratamiento = new JTextArea();
        scrollTrat.setViewportView(txtTratamiento);

        JButton btnGuardar = new JButton("Guardar Atención y Generar Receta");
        btnGuardar.setBounds(20, 290, 390, 35);
        contentPane.add(btnGuardar);

        JButton btnVolver = new JButton("Volver al Menú Principal");
        btnVolver.setBounds(20, 380, 390, 30);
        contentPane.add(btnVolver);

        // EVENTO: GUARDAR ATENCIÓN MÉDICA
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String dni = txtDni.getText();
                
                try {
                    // Programación funcional: Buscar paciente y verificar si tiene citas pendientes
                    Optional<Paciente> pacienteOpt = FrmRegistro.dbPacientesMock.stream()
                        .filter(p -> p.getDniEnmascarado().contains(dni.substring(Math.max(0, dni.length() - 4))))
                        .findFirst();

                    if (pacienteOpt.isPresent()) {
                        Paciente paciente = pacienteOpt.get();
                        
                        // Buscar la primera cita pendiente usando Streams
                        Optional<CitaMedica> citaPendiente = paciente.consultarHistorial().stream()
                            // Asumiendo que agregas un getter getEstado() en CitaMedica que retorne un Enum o String
                            .findFirst(); // Simplificado: tomamos la cita para atenderla

                        if (citaPendiente.isPresent()) {
                            CitaMedica cita = citaPendiente.get();
                            
                            // Aplicar lógica UML
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

                            JOptionPane.showMessageDialog(null, "Atención registrada con éxito.\nStock restante de " + amoxicilina.getNombre() + ": " + amoxicilina.getStockDisponible());
                            
                            // Limpiar
                            txtDni.setText(""); txtDiagnostico.setText(""); txtTratamiento.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "El paciente no tiene citas pendientes.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Paciente no encontrado en el sistema.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al procesar la atención. Verifique los datos.");
                }
            }
        });

        // EVENTO: VOLVER
        btnVolver.addActionListener(e -> {
            new FrmPrincipal().setVisible(true);
            dispose();
        });
    }
}