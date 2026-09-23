package vista;

import modelo.*;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class FrmSistema extends JFrame {

    private JPanel contentPane;
    private JTextArea txtConsola;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FrmSistema frame = new FrmSistema();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public FrmSistema() {
        setTitle("Módulo de Gestión - San Juan de Lurigancho");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null); // Absolute layout

        // Título de la ventana
        JLabel lblTitulo = new JLabel("Centro de Salud Rural - Gestión de Pacientes");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTitulo.setBounds(20, 20, 400, 20);
        contentPane.add(lblTitulo);

        // Consola de salida de datos (JTextArea dentro de JScrollPane)
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 100, 490, 280);
        contentPane.add(scrollPane);

        txtConsola = new JTextArea();
        txtConsola.setEditable(false);
        txtConsola.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtConsola.setText("Bienvenido. Presione el botón para iniciar la simulación de atención...\n");
        scrollPane.setViewportView(txtConsola);

        // Botón que ejecuta la lógica orientada a eventos
        JButton btnEjecutar = new JButton("Ejecutar Simulación Médica");
        btnEjecutar.setBounds(20, 60, 220, 30);
        contentPane.add(btnEjecutar);

        // EVENTO DEL BOTÓN (Aquí integramos los datos de Ejecucion.java)
        btnEjecutar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtConsola.setText(""); // Limpiar consola
                txtConsola.append(">>> INICIANDO PROCESO DE ATENCIÓN <<<\n\n");

                try {
                    // 1. Registrar paciente (Datos en duro tomados de Ejecucion.java)
                    Paciente paciente = new Paciente();
                    paciente.setIdPersona("P001");
                    paciente.setDni("45678912");
                    paciente.setNombres("Rosa");
                    paciente.setApellidos("Quispe Mamani");
                    paciente.setNumeroHistoriaClinica("HC-001");
                    
                    // 2. Registrar médico
                    Medico medico = new Medico();
                    medico.setIdPersona("M001");
                    medico.setNombres("Carlos");
                    medico.setApellidos("Torres Vega");
                    medico.setCmp("CMP-55210");
                    medico.setEspecialidad("Medicina General");

                    // 3. Paciente solicita una cita
                    CitaMedica cita = new CitaMedica();
                    cita.setIdCita("C001");
                    cita.setFechaHora(LocalDateTime.now());
                    cita.setMotivoConsulta("Dolor abdominal");
                    cita.programarCita();

                    paciente.solicitarCita(cita);
                    medico.getCitasAsignadas().add(cita);

                    // 4. Médico atiende la cita y registra la atención
                    medico.atenderCita(cita);
                    AtencionMedica atencion = cita.getAtencionMedica();
                    atencion.setDiagnostico("Gastritis leve");
                    atencion.setTratamiento("Dieta blanda y medicación");

                    // 5. Registrar medicamento disponible en almacén
                    Medicamento medicamento = new Medicamento();
                    medicamento.setIdMedicamento("MED001");
                    medicamento.setNombre("Omeprazol 20mg");
                    medicamento.setStockDisponible(50);
                    medicamento.setFechaVencimiento(LocalDate.now().plusYears(1));

                    // 6. Médico emite receta
                    DetalleReceta detalle = new DetalleReceta();
                    detalle.setMedicamento(medicamento);
                    detalle.setCantidad(10);
                    detalle.setIndicaciones("Tomar 1 tableta cada 24 horas, en ayunas");
                    medico.emitirReceta(atencion, detalle);

                    // 7. Mostrar resultados en el JTextArea (Reemplazo de System.out.println)
                    txtConsola.append("Paciente: " + paciente.getNombreCompleto() + "\n");
                    txtConsola.append("DNI protegido por Ley N.º 29733: " + paciente.getDniEnmascarado() + "\n");
                    txtConsola.append("Médico tratante: " + medico.getNombreCompleto() + " (" + medico.getEspecialidad() + ")\n");
                    txtConsola.append("Estado de la cita: " + cita.getEstado() + "\n");
                    txtConsola.append("--- INFORME MÉDICO ---\n");
                    txtConsola.append(atencion.generarInforme() + "\n");
                    txtConsola.append("Stock restante de " + medicamento.getNombre() + ": " + medicamento.getStockDisponible() + "\n");
                    
                    // 8. Consultar historial
                    txtConsola.append("\nHistorial de citas atendidas: " + paciente.consultarHistorial().size() + "\n");
                    txtConsola.append("\n>>> SIMULACIÓN FINALIZADA CON ÉXITO <<<");

                } catch (Exception ex) {
                    // Manejo de errores solicitado en la rúbrica
                    txtConsola.append("Error en la ejecución: " + ex.getMessage() + "\n");
                }
            }
        });
    }
}