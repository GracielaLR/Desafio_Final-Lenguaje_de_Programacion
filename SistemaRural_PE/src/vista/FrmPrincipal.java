package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

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
        // --- CONFIGURACIÓN DE LA VENTANA PRINCIPAL ---
        setTitle("Sistema Rural-PE - Dashboard Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 530);
        setLocationRelativeTo(null); // Esto centra la ventana automáticamente en la pantalla
        
        contentPane = new JPanel();
        contentPane.setBackground(new Color(245, 247, 250)); // Fondo gris corporativo claro
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // --- BANNER SUPERIOR CORPORATIVO ---
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(0, 102, 204)); // Azul Médico
        panelHeader.setBounds(0, 0, 500, 80);
        contentPane.add(panelHeader);
        panelHeader.setLayout(null);

        JLabel lblTitulo = new JLabel("SISTEMA INTEGRAL DE SALUD RURAL");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setBounds(0, 15, 484, 25);
        panelHeader.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Centro Médico - San Juan de Lurigancho");
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitulo.setForeground(new Color(200, 225, 255));
        lblSubtitulo.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblSubtitulo.setBounds(0, 40, 484, 20);
        panelHeader.add(lblSubtitulo);

        // --- CONTENEDOR CENTRAL DE MÓDULOS (Dashboard) ---
        JPanel panelMenu = new JPanel();
        panelMenu.setBackground(Color.WHITE);
        panelMenu.setBorder(new LineBorder(new Color(200, 200, 200), 1, true)); // Borde suave
        panelMenu.setBounds(40, 110, 400, 330);
        contentPane.add(panelMenu);
        panelMenu.setLayout(null);

        JLabel lblInstruccion = new JLabel("Seleccione un módulo de trabajo:");
        lblInstruccion.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblInstruccion.setForeground(new Color(50, 50, 50));
        lblInstruccion.setBounds(30, 20, 300, 20);
        panelMenu.add(lblInstruccion);

        // Módulo 1: Recepción
        JButton btnRegistro = crearBotonMenu("1. Módulo de Recepción (Triaje)", 30, 60);
        panelMenu.add(btnRegistro);
        
        JLabel lblDesc1 = new JLabel("<html>Registro de pacientes, programación de citas y validación directa con API RENIEC.</html>");
        lblDesc1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDesc1.setForeground(Color.GRAY);
        lblDesc1.setBounds(35, 100, 340, 35);
        panelMenu.add(lblDesc1);

        // Módulo 2: Consultorio
        JButton btnAtencion = crearBotonMenu("2. Consultorio Médico", 30, 145);
        panelMenu.add(btnAtencion);
        
        JLabel lblDesc2 = new JLabel("<html>Atención de pacientes, generación de diagnósticos y emisión de recetas con descuento de stock.</html>");
        lblDesc2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDesc2.setForeground(Color.GRAY);
        lblDesc2.setBounds(35, 185, 340, 35);
        panelMenu.add(lblDesc2);

        // Módulo 3: Archivo Clínico
        JButton btnConsulta = crearBotonMenu("3. Archivo Clínico", 30, 230);
        panelMenu.add(btnConsulta);
        
        JLabel lblDesc3 = new JLabel("<html>Consulta funcional de historiales médicos aplicando Ley de Protección de Datos (N.º 29733).</html>");
        lblDesc3.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDesc3.setForeground(Color.GRAY);
        lblDesc3.setBounds(35, 270, 340, 35);
        panelMenu.add(lblDesc3);

        // --- FOOTER ---
        JLabel lblFooter = new JLabel("Sistema Rural-PE © 2026 | Arquitectura Empresarial");
        lblFooter.setHorizontalAlignment(SwingConstants.CENTER);
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFooter.setForeground(new Color(150, 150, 150));
        lblFooter.setBounds(0, 460, 484, 20);
        contentPane.add(lblFooter);

        // --- EVENTOS DE NAVEGACIÓN ---
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

    // =========================================================
    // MÉTODO AUXILIAR PARA APLICAR "CLEAN CODE" (Principio DRY)
    // =========================================================
    private JButton crearBotonMenu(String texto, int x, int y) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, 340, 40);
        btn.setBackground(new Color(0, 102, 204));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false); // Elimina el contorno punteado al hacer clic
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia el cursor a "Manito" al pasar el mouse
        return btn;
    }
}