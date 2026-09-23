package vista;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

public class FrmPrincipal extends JFrame {

    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                FrmPrincipal frame = new FrmPrincipal();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public FrmPrincipal() {
        setTitle("Sistema Rural-PE - San Juan de Lurigancho");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 350);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("SISTEMA INTEGRAL DE SALUD RURAL");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblTitulo.setBounds(10, 20, 360, 30);
        contentPane.add(lblTitulo);

        // Botón Módulo 1: Recepción
        JButton btnRegistro = new JButton("1. Módulo de Recepción (Registrar Cita)");
        btnRegistro.setBounds(50, 80, 280, 40);
        contentPane.add(btnRegistro);

        // Botón Módulo 2: Consultorio Médico
        JButton btnAtencion = new JButton("2. Consultorio Médico (Atender Paciente)");
        btnAtencion.setBounds(50, 140, 280, 40);
        contentPane.add(btnAtencion);

        // Botón Módulo 3: Archivo e Historiales
        JButton btnConsulta = new JButton("3. Archivo Clínico (Consultar Historial)");
        btnConsulta.setBounds(50, 200, 280, 40);
        contentPane.add(btnConsulta);

        // EVENTOS DE NAVEGACIÓN
        btnRegistro.addActionListener(e -> {
            new FrmRegistro().setVisible(true);
            dispose();
        });

        btnAtencion.addActionListener(e -> {
            new FrmAtencion().setVisible(true);
            dispose();
        });

        btnConsulta.addActionListener(e -> {
            new FrmConsulta().setVisible(true);
            dispose();
        });
    }
}