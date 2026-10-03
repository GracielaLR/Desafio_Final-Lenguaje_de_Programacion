package vista;

import modelo.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
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
    private JTextField txtBuscarDni, txtHC, txtNombres, txtApellidos, txtDniSeguro, txtFechaNac, txtTelefono;
    private JTextArea txtConsola;
    private JComboBox<String> cbxCitas, cbxEstado;
    private Paciente pacienteActual = null;
    private List<CitaMedica> listaCitasActual = new ArrayList<>();
    private static final DateTimeFormatter FMT_F = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter FMT_FH = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public static void main(String[] args) { EventQueue.invokeLater(() -> { try { new FrmConsulta().setVisible(true); } catch (Exception e) { e.printStackTrace(); } }); }

    public FrmConsulta() {
        setTitle("Archivo Clínico - Historiales");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1050, 620); 
        setLocationRelativeTo(null);
        contentPane = new JPanel(); contentPane.setBackground(new Color(245, 247, 250)); contentPane.setLayout(null); setContentPane(contentPane);

        JPanel panelHeader = new JPanel(); panelHeader.setBackground(new Color(0, 102, 204)); panelHeader.setBounds(0, 0, 1050, 70); contentPane.add(panelHeader); panelHeader.setLayout(null);
        JLabel lblTitulo = new JLabel("ARCHIVO CLÍNICO Y GESTIÓN DE ESTADOS"); lblTitulo.setHorizontalAlignment(SwingConstants.CENTER); lblTitulo.setForeground(Color.WHITE); lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22)); lblTitulo.setBounds(0, 18, 1034, 30); panelHeader.add(lblTitulo);

        JPanel panelBusq = new JPanel(); panelBusq.setBackground(Color.WHITE); panelBusq.setBorder(new TitledBorder(new LineBorder(new Color(180,180,180), 1, true), "1. Buscar Paciente", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0,102,204))); panelBusq.setBounds(20, 85, 460, 80); contentPane.add(panelBusq); panelBusq.setLayout(null);
        JLabel lblDniBusc = new JLabel("DNI:"); lblDniBusc.setBounds(20, 32, 40, 20); panelBusq.add(lblDniBusc); txtBuscarDni = new JTextField(); txtBuscarDni.setFont(new Font("Segoe UI", Font.BOLD, 14)); txtBuscarDni.setBounds(60, 30, 200, 28); panelBusq.add(txtBuscarDni);
        JButton btnBuscar = new JButton("Buscar Historial"); btnBuscar.setBackground(new Color(23, 162, 184)); btnBuscar.setForeground(Color.WHITE); btnBuscar.setBounds(275, 30, 160, 28); panelBusq.add(btnBuscar);

        JPanel panelFicha = new JPanel(); panelFicha.setBackground(Color.WHITE); panelFicha.setBorder(new TitledBorder(new LineBorder(new Color(180,180,180), 1, true), "2. Ficha Técnica (Ley N.º 29733)", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0,102,204))); panelFicha.setBounds(20, 175, 460, 180); contentPane.add(panelFicha); panelFicha.setLayout(null);
        JLabel lHC = new JLabel("N° HC:"); lHC.setBounds(20,30,60,20); panelFicha.add(lHC); txtHC = crTxt(80,28,120); panelFicha.add(txtHC);
        JLabel lDni = new JLabel("DNI Seg:"); lDni.setBounds(220,30,70,20); panelFicha.add(lDni); txtDniSeguro = crTxt(290,28,150); panelFicha.add(txtDniSeguro);
        JLabel lNom = new JLabel("Nombres:"); lNom.setBounds(20,65,70,20); panelFicha.add(lNom); txtNombres = crTxt(90,63,350); panelFicha.add(txtNombres);
        JLabel lApe = new JLabel("Apellidos:"); lApe.setBounds(20,100,70,20); panelFicha.add(lApe); txtApellidos = crTxt(90,98,350); panelFicha.add(txtApellidos);
        JLabel lFec = new JLabel("F. Nac:"); lFec.setBounds(20,135,60,20); panelFicha.add(lFec); txtFechaNac = crTxt(80,133,120); panelFicha.add(txtFechaNac);
        JLabel lTel = new JLabel("Celular:"); lTel.setBounds(220,135,70,20); panelFicha.add(lTel); txtTelefono = crTxt(290,133,150); panelFicha.add(txtTelefono);

        JPanel panelEst = new JPanel(); panelEst.setBackground(Color.WHITE); panelEst.setBorder(new TitledBorder(new LineBorder(new Color(180,180,180), 1, true), "3. Gestión de Estados", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), new Color(0,102,204))); panelEst.setBounds(20, 365, 460, 110); contentPane.add(panelEst); panelEst.setLayout(null);
        JLabel lCita = new JLabel("Cita:"); lCita.setBounds(20,30,40,20); panelEst.add(lCita); cbxCitas = new JComboBox<>(); cbxCitas.setBounds(60,28,380,25); panelEst.add(cbxCitas);
        cbxEstado = new JComboBox<>(new String[]{"En Proceso / Pendiente", "Consulta Finalizada", "Cancelada"}); cbxEstado.setBounds(20,65,250,25); panelEst.add(cbxEstado);
        JButton btnAct = new JButton("Actualizar Estado"); btnAct.setBackground(new Color(40,167,69)); btnAct.setForeground(Color.WHITE); btnAct.setBounds(290,65,150,25); panelEst.add(btnAct);

        JButton btnVolver = new JButton("Volver al Menú Principal"); btnVolver.setBackground(new Color(108,117,125)); btnVolver.setForeground(Color.WHITE); btnVolver.setBounds(20, 490, 460, 45); contentPane.add(btnVolver);

        JScrollPane scr = new JScrollPane(); scr.setBounds(500, 85, 510, 450); contentPane.add(scr);
        txtConsola = new JTextArea(); txtConsola.setFont(new Font("Monospaced", Font.PLAIN, 13)); txtConsola.setEditable(false); scr.setViewportView(txtConsola);

        btnBuscar.addActionListener(e -> {
            txtHC.setText(""); txtDniSeguro.setText(""); txtNombres.setText(""); txtApellidos.setText(""); txtFechaNac.setText(""); txtTelefono.setText(""); txtConsola.setText(""); cbxCitas.removeAllItems(); listaCitasActual.clear();
            Optional<Paciente> pOpt = FrmRegistro.dbPacientesMock.stream().filter(p -> p.getDni() != null && p.getDni().equals(txtBuscarDni.getText().trim())).findFirst();
            if (pOpt.isPresent()) {
                pacienteActual = pOpt.get(); txtHC.setText(pacienteActual.getNumeroHistoriaClinica()); txtDniSeguro.setText(pacienteActual.getDniEnmascarado()); txtNombres.setText(pacienteActual.getNombres()); txtApellidos.setText(pacienteActual.getApellidos()); txtTelefono.setText(pacienteActual.getTelefono() != null ? pacienteActual.getTelefono() : "N/A"); txtFechaNac.setText(pacienteActual.getFechaNacimiento() != null ? pacienteActual.getFechaNacimiento().format(FMT_F) : "N/A");
                listaCitasActual = pacienteActual.getCitasMedicas(); for (CitaMedica c : listaCitasActual) cbxCitas.addItem(c.getIdCita() + " - " + c.getFechaHora().toLocalDate().format(FMT_F));
                imprimirHistorial();
            } else { pacienteActual = null; JOptionPane.showMessageDialog(this, "No se encontró paciente."); }
        });

        btnAct.addActionListener(e -> {
            if(pacienteActual != null && cbxCitas.getSelectedIndex() >= 0) {
                CitaMedica c = listaCitasActual.get(cbxCitas.getSelectedIndex()); String est = (String) cbxEstado.getSelectedItem();
                if(est.contains("Pendiente")) c.setEstado(CitaMedica.EstadoCita.PENDIENTE); else if(est.contains("Finalizada")) c.setEstado(CitaMedica.EstadoCita.ATENDIDA); else c.setEstado(CitaMedica.EstadoCita.CANCELADA);
                JOptionPane.showMessageDialog(this, "Estado actualizado."); imprimirHistorial();
            }
        });

        cbxCitas.addActionListener(e -> {
            if(cbxCitas.getSelectedIndex() >= 0) {
                switch(listaCitasActual.get(cbxCitas.getSelectedIndex()).getEstado()) { case PENDIENTE: cbxEstado.setSelectedIndex(0); break; case ATENDIDA: cbxEstado.setSelectedIndex(1); break; case CANCELADA: cbxEstado.setSelectedIndex(2); break; }
            }
        });
        btnVolver.addActionListener(e -> { new FrmPrincipal().setVisible(true); dispose(); });
    }

    private JTextField crTxt(int x, int y, int w) { JTextField t = new JTextField(); t.setBounds(x,y,w,25); t.setEditable(false); t.setBackground(new Color(240,245,250)); return t; }

    private void imprimirHistorial() {
        txtConsola.setText("==============================================================\n            HISTORIAL CLÍNICO Y REGISTRO DE CITAS\n==============================================================\n\n");
        listaCitasActual.forEach(cita -> {
            txtConsola.append("» CITA ID: " + cita.getIdCita() + "\n  Fecha    : " + cita.getFechaHora().format(FMT_FH) + "\n  Motivo   : " + cita.getMotivoConsulta() + "\n  Estado   : [" + (cita.getEstado() == CitaMedica.EstadoCita.PENDIENTE ? "EN PROCESO" : (cita.getEstado() == CitaMedica.EstadoCita.ATENDIDA ? "FINALIZADA" : "CANCELADA")) + "]\n");
            txtConsola.append("  Especialidad : " + (cita.getEspecialidad() != null ? cita.getEspecialidad() : "No definida") + "\n");
            
            // Buscar médico
            Optional<Medico> med = FrmRegistro.dbMedicosMock.stream().filter(m -> m.getCitasAsignadas().contains(cita)).findFirst();
            if(med.isPresent()) {
                String pref = med.get().getNombres().trim().endsWith("a") ? "Dra. " : "Dr. ";
                txtConsola.append("  Atendido por : " + pref + med.get().getNombreCompleto() + "\n");
            } else if (cita.getMedicoAsignado() != null) {
                String pref = cita.getMedicoAsignado().getNombres().trim().endsWith("a") ? "Dra. " : "Dr. ";
                txtConsola.append("  Derivado a   : " + pref + cita.getMedicoAsignado().getNombreCompleto() + " (En Espera)\n");
            } else {
                txtConsola.append("  Atendido por : [Pendiente]\n");
            }

            if(cita.getAtencionMedica() != null) txtConsola.append("  -- Informe Médico:\n     Diag: " + cita.getAtencionMedica().getDiagnostico() + "\n     Trat: " + cita.getAtencionMedica().getTratamiento() + "\n");
            txtConsola.append("--------------------------------------------------------------\n");
        });
    }
}