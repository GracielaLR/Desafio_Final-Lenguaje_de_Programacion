package vista;

import modelo.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class FrmConsulta extends JFrame {

    private JPanel contentPane;
    private JTextField txtBuscarDni;
    private JTextField txtHC;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtDniSeguro;
    private JTextField txtFechaNac;
    private JTextField txtTelefono;
    private JTextArea txtConsola;
    
    // Componentes para la gestión de estados
    private JComboBox<String> cbxCitas;
    private JComboBox<String> cbxEstado;
    private JButton btnActualizarEstado;

    // Variables de estado temporal de la ventana
    private Paciente pacienteActual = null;
    private List<CitaMedica> listaCitasActual = new ArrayList<>();
    
    // Formateadores
    private static final DateTimeFormatter FMT_FECHA = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter FMT_FECHAHORA = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                FrmConsulta frame = new FrmConsulta();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public FrmConsulta() {
        setTitle("Archivo Clínico y Gestión de Citas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 720); // Ventana más grande y profesional
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setBackground(new Color(245, 247, 250));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // --- BANNER SUPERIOR ---
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(0, 102, 204));
        panelHeader.setBounds(0, 0, 600, 60);
        contentPane.add(panelHeader);
        panelHeader.setLayout(null);

        JLabel lblTitulo = new JLabel("ARCHIVO CLÍNICO Y GESTIÓN DE CITAS");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setBounds(0, 15, 584, 30);
        panelHeader.add(lblTitulo);

        // --- PANEL 1: BÚSQUEDA ---
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setBackground(Color.WHITE);
        panelBusqueda.setBorder(new TitledBorder(new LineBorder(new Color(180, 180, 180), 1, true), "1. Buscar Paciente", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 102, 204)));
        panelBusqueda.setBounds(20, 75, 545, 70);
        contentPane.add(panelBusqueda);
        panelBusqueda.setLayout(null);

        JLabel lblDniBusc = new JLabel("DNI a buscar:");
        lblDniBusc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDniBusc.setBounds(20, 27, 90, 20);
        panelBusqueda.add(lblDniBusc);

        txtBuscarDni = new JTextField();
        txtBuscarDni.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtBuscarDni.setBounds(110, 25, 180, 26);
        panelBusqueda.add(txtBuscarDni);

        JButton btnBuscar = new JButton("Buscar Historial");
        btnBuscar.setBackground(new Color(23, 162, 184)); // Cyan Informativo
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBounds(310, 24, 150, 28);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelBusqueda.add(btnBuscar);

        // --- PANEL 2: FICHA TÉCNICA DEL PACIENTE ---
        JPanel panelFicha = new JPanel();
        panelFicha.setBackground(Color.WHITE);
        panelFicha.setBorder(new TitledBorder(new LineBorder(new Color(180, 180, 180), 1, true), "2. Ficha del Paciente (Ley N.º 29733)", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 102, 204)));
        panelFicha.setBounds(20, 155, 545, 130);
        contentPane.add(panelFicha);
        panelFicha.setLayout(null);

        JLabel lblHC = new JLabel("N° Historia:");
        lblHC.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblHC.setBounds(20, 25, 80, 20);
        panelFicha.add(lblHC);
        
        txtHC = crearCampoLectura(100, 25, 150);
        panelFicha.add(txtHC);

        JLabel lblDniSeguro = new JLabel("DNI Seguro:");
        lblDniSeguro.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblDniSeguro.setBounds(270, 25, 80, 20);
        panelFicha.add(lblDniSeguro);

        txtDniSeguro = crearCampoLectura(350, 25, 175);
        panelFicha.add(txtDniSeguro);

        JLabel lblNombres = new JLabel("Nombres:");
        lblNombres.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblNombres.setBounds(20, 60, 80, 20);
        panelFicha.add(lblNombres);

        txtNombres = crearCampoLectura(100, 60, 150);
        panelFicha.add(txtNombres);

        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblApellidos.setBounds(270, 60, 80, 20);
        panelFicha.add(lblApellidos);

        txtApellidos = crearCampoLectura(350, 60, 175);
        panelFicha.add(txtApellidos);

        JLabel lblFechaNac = new JLabel("Fecha Nac.:");
        lblFechaNac.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblFechaNac.setBounds(20, 95, 80, 20);
        panelFicha.add(lblFechaNac);

        txtFechaNac = crearCampoLectura(100, 95, 150);
        panelFicha.add(txtFechaNac);

        JLabel lblTel = new JLabel("Teléfono:");
        lblTel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTel.setBounds(270, 95, 80, 20);
        panelFicha.add(lblTel);

        txtTelefono = crearCampoLectura(350, 95, 175);
        panelFicha.add(txtTelefono);

        // --- PANEL 3: CAMBIO DE ESTADO DE CITAS (Para el Médico) ---
        JPanel panelEstado = new JPanel();
        panelEstado.setBackground(Color.WHITE);
        panelEstado.setBorder(new TitledBorder(new LineBorder(new Color(180, 180, 180), 1, true), "3. Gestión y Estado de Citas", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0, 102, 204)));
        panelEstado.setBounds(20, 295, 545, 80);
        contentPane.add(panelEstado);
        panelEstado.setLayout(null);

        JLabel lblSelCita = new JLabel("Seleccionar Cita:");
        lblSelCita.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSelCita.setBounds(20, 30, 100, 20);
        panelEstado.add(lblSelCita);

        cbxCitas = new JComboBox<>();
        cbxCitas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cbxCitas.setBounds(120, 28, 160, 25);
        panelEstado.add(cbxCitas);

        cbxEstado = new JComboBox<>(new String[]{"En Proceso / Pendiente", "Consulta Finalizada", "Cancelada"});
        cbxEstado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cbxEstado.setBounds(290, 28, 150, 25);
        panelEstado.add(cbxEstado);

        btnActualizarEstado = new JButton("Actualizar");
        btnActualizarEstado.setBackground(new Color(40, 167, 69)); // Verde
        btnActualizarEstado.setForeground(Color.WHITE);
        btnActualizarEstado.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnActualizarEstado.setFocusPainted(false);
        btnActualizarEstado.setBounds(450, 28, 80, 25);
        panelEstado.add(btnActualizarEstado);

        // --- PANEL 4: CONSOLA DE HISTORIAL ---
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 390, 545, 230);
        contentPane.add(scrollPane);

        txtConsola = new JTextArea();
        txtConsola.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtConsola.setEditable(false);
        txtConsola.setLineWrap(true);
        txtConsola.setWrapStyleWord(true);
        scrollPane.setViewportView(txtConsola);

        // --- BOTÓN FOOTER ---
        JButton btnVolver = new JButton("Volver al Menú Principal");
        btnVolver.setBackground(new Color(108, 117, 125));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVolver.setFocusPainted(false);
        btnVolver.setBounds(20, 630, 545, 35);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        contentPane.add(btnVolver);

        // ==========================================
        // EVENTOS Y LÓGICA FUNCIONAL
        // ==========================================

        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String dniBuscado = txtBuscarDni.getText().trim();
                limpiarFicha();

                try {
                    // Programación funcional para buscar al paciente
                    Optional<Paciente> pacienteEncontrado = FrmRegistro.dbPacientesMock.stream()
                        .filter(p -> p.getDni() != null && p.getDni().equals(dniBuscado))
                        .findFirst();

                    if (pacienteEncontrado.isPresent()) {
                        pacienteActual = pacienteEncontrado.get();
                        
                        // Rellenar Ficha Técnica
                        txtHC.setText(pacienteActual.getNumeroHistoriaClinica());
                        txtDniSeguro.setText(pacienteActual.getDniEnmascarado()); // Aplicando Ley de Datos
                        txtNombres.setText(pacienteActual.getNombres());
                        txtApellidos.setText(pacienteActual.getApellidos());
                        txtTelefono.setText(pacienteActual.getTelefono() != null ? pacienteActual.getTelefono() : "No registrado");
                        txtFechaNac.setText(pacienteActual.getFechaNacimiento() != null ? pacienteActual.getFechaNacimiento().format(FMT_FECHA) : "No registrada");

                        cargarComboCitas();
                        imprimirHistorialCompleto();
                        
                    } else {
                        pacienteActual = null;
                        JOptionPane.showMessageDialog(null, "No se encontró ningún paciente con el DNI: " + dniBuscado, "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error en la búsqueda.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Evento para actualizar el estado de la cita seleccionada
        btnActualizarEstado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(pacienteActual == null || listaCitasActual.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Primero debe buscar un paciente con citas.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int index = cbxCitas.getSelectedIndex();
                if(index >= 0) {
                    CitaMedica citaSeleccionada = listaCitasActual.get(index);
                    String estadoTexto = (String) cbxEstado.getSelectedItem();
                    
                    // Mapeamos el texto amigable de la UI a los Enum reales del Backend
                    if(estadoTexto.equals("En Proceso / Pendiente")) {
                        citaSeleccionada.setEstado(CitaMedica.EstadoCita.PENDIENTE);
                    } else if(estadoTexto.equals("Consulta Finalizada")) {
                        citaSeleccionada.setEstado(CitaMedica.EstadoCita.ATENDIDA);
                    } else {
                        citaSeleccionada.setEstado(CitaMedica.EstadoCita.CANCELADA);
                    }

                    JOptionPane.showMessageDialog(null, "Estado de la cita actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    imprimirHistorialCompleto(); // Refrescar la consola
                }
            }
        });

        // Evento que escucha cuando el usuario selecciona otra cita en el combo
        cbxCitas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = cbxCitas.getSelectedIndex();
                if(index >= 0 && index < listaCitasActual.size()) {
                    CitaMedica cita = listaCitasActual.get(index);
                    // Actualizar el combo de estados para que coincida con la cita seleccionada
                    switch(cita.getEstado()) {
                        case PENDIENTE: cbxEstado.setSelectedIndex(0); break;
                        case ATENDIDA: cbxEstado.setSelectedIndex(1); break;
                        case CANCELADA: cbxEstado.setSelectedIndex(2); break;
                    }
                }
            }
        });

        btnVolver.addActionListener(e -> {
            new FrmPrincipal().setVisible(true);
            dispose();
        });
    }

    // ==========================================
    // MÉTODOS AUXILIARES (CLEAN CODE)
    // ==========================================

    private JTextField crearCampoLectura(int x, int y, int width) {
        JTextField txt = new JTextField();
        txt.setBounds(x, y, width, 25);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txt.setEditable(false);
        txt.setBackground(new Color(240, 245, 250));
        return txt;
    }

    private void limpiarFicha() {
        txtHC.setText(""); txtDniSeguro.setText("");
        txtNombres.setText(""); txtApellidos.setText("");
        txtFechaNac.setText(""); txtTelefono.setText("");
        txtConsola.setText("");
        cbxCitas.removeAllItems();
        listaCitasActual.clear();
    }

    private void cargarComboCitas() {
        cbxCitas.removeAllItems();
        listaCitasActual = pacienteActual.getCitasMedicas(); // Traemos todas las citas
        
        for (CitaMedica cita : listaCitasActual) {
            cbxCitas.addItem(cita.getIdCita() + " - " + cita.getFechaHora().toLocalDate().format(FMT_FECHA));
        }
    }

    private void imprimirHistorialCompleto() {
        txtConsola.setText("==============================================================\n");
        txtConsola.append("            HISTORIAL CLÍNICO Y REGISTRO DE CITAS            \n");
        txtConsola.append("==============================================================\n\n");
        
        if (listaCitasActual.isEmpty()) {
            txtConsola.append(" El paciente no tiene ninguna cita registrada en el sistema.\n");
            return;
        }

        // Aplicamos un ForEach funcional para recorrer las citas
        listaCitasActual.forEach(cita -> {
            txtConsola.append("» CITA ID: " + cita.getIdCita() + "\n");
            txtConsola.append("  Fecha y Hora : " + cita.getFechaHora().format(FMT_FECHAHORA) + "\n");
            txtConsola.append("  Motivo       : " + cita.getMotivoConsulta() + "\n");
            
            // Transformar el Enum backend a un texto amigable para el reporte
            String estadoVisual = cita.getEstado() == CitaMedica.EstadoCita.PENDIENTE ? "EN PROCESO / PENDIENTE" :
                                 (cita.getEstado() == CitaMedica.EstadoCita.ATENDIDA ? "CONSULTA FINALIZADA" : "CANCELADA");
            txtConsola.append("  Estado       : [" + estadoVisual + "]\n");

            // --- LÓGICA AVANZADA: Buscar qué médico atendió esta cita específica ---
            // Revisamos en todos los médicos quién tiene esta cita en su lista de "citasAsignadas"
            Optional<Medico> medicoTratante = FrmAtencion.dbMedicosMock.stream()
                .filter(m -> m.getCitasAsignadas().contains(cita))
                .findFirst();

            if (medicoTratante.isPresent()) {
                txtConsola.append("  Atendido por : " + medicoTratante.get().getNombreCompleto() + " (" + medicoTratante.get().getEspecialidad() + ")\n");
            } else {
                txtConsola.append("  Atendido por : [Pendiente de asignación médica]\n");
            }

            // Si la cita ya tiene diagnóstico y tratamiento
            if(cita.getAtencionMedica() != null) {
                txtConsola.append("  -- Informe Médico:\n");
                txtConsola.append("     Diagnóstico : " + cita.getAtencionMedica().getDiagnostico() + "\n");
                txtConsola.append("     Tratamiento : " + cita.getAtencionMedica().getTratamiento() + "\n");
            }
            txtConsola.append("--------------------------------------------------------------\n");
        });
    }
}