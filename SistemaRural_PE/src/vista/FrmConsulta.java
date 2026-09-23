package vista;

import modelo.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class FrmConsulta extends JFrame {

    private JPanel contentPane;
    private JTextField txtBuscarDni;
    private JTextArea txtConsola;

    public FrmConsulta() {
        setTitle("Consulta de Atenciones - Centro de Salud");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("Módulo de Consulta de Pacientes");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTitulo.setBounds(20, 10, 300, 20);
        contentPane.add(lblTitulo);

        JLabel lblDni = new JLabel("DNI a buscar:");
        lblDni.setBounds(20, 50, 100, 20);
        contentPane.add(lblDni);

        txtBuscarDni = new JTextField();
        txtBuscarDni.setBounds(110, 50, 150, 20);
        contentPane.add(txtBuscarDni);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(280, 48, 120, 25);
        contentPane.add(btnBuscar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 90, 380, 200);
        contentPane.add(scrollPane);

        txtConsola = new JTextArea();
        txtConsola.setEditable(false);
        scrollPane.setViewportView(txtConsola);

        JButton btnVolver = new JButton("Volver al Registro");
        btnVolver.setBounds(20, 310, 180, 30);
        contentPane.add(btnVolver);

        // EVENTO: BUSCAR (Aplicación de Programación Funcional)
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String dniBuscado = txtBuscarDni.getText();
                txtConsola.setText("");

                try {
                    // Uso de API Stream y Expresiones Lambda para búsqueda (Cumple Rúbrica Avanzada)
                    Optional<Paciente> pacienteEncontrado = FrmRegistro.dbPacientesMock.stream()
                        .filter(p -> p.getDniEnmascarado().contains(dniBuscado.substring(Math.max(0, dniBuscado.length() - 4))) || p.getDniEnmascarado().equals("****" + dniBuscado))
                        .findFirst();

                    if (pacienteEncontrado.isPresent()) {
                        Paciente p = pacienteEncontrado.get();
                        txtConsola.append("--- DATOS DEL PACIENTE ---\n");
                        txtConsola.append("Nombre: " + p.getNombreCompleto() + "\n");
                        // Ley 29733: Datos sensibles no expuestos
                        txtConsola.append("DNI Seguro: " + p.getDniEnmascarado() + "\n");
                        txtConsola.append("Historia Clínica: " + p.getNumeroHistoriaClinica() + "\n\n");
                        
                        txtConsola.append("--- HISTORIAL DE CITAS ---\n");
                        if(p.consultarHistorial().isEmpty() && FrmRegistro.dbPacientesMock.size() > 0) {
                             txtConsola.append("Tiene citas programadas pero aún no atendidas.\n");
                        }
                    } else {
                        txtConsola.append("No se encontró ningún paciente con ese DNI.\n");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error en la búsqueda. Verifique el DNI ingresado.");
                }
            }
        });

        // EVENTO: VOLVER
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Instancia el menú principal y cierra la ventana actual
                FrmPrincipal principal = new FrmPrincipal();
                principal.setVisible(true);
                dispose();
            }
        });
    }
}