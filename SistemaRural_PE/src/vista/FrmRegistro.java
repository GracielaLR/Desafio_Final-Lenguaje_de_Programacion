package vista;

import modelo.*;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class FrmRegistro extends JFrame {

    private JPanel contentPane;
    private JTextField txtDni;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtMotivo;
    
    // "Base de datos en duro" para compartir entre ventanas
    public static List<Paciente> dbPacientesMock = new ArrayList<>();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                FrmRegistro frame = new FrmRegistro();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public FrmRegistro() {
        setTitle("Registro de Citas - Centro de Salud");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 429, 385);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("Registrar Nueva Cita Médica");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 21));
        lblTitulo.setBounds(40, 11, 335, 46);
        contentPane.add(lblTitulo);
    															

        txtDni = new JTextField();
        txtDni.setBounds(154, 68, 235, 20);
        contentPane.add(txtDni);
        
     // EVENTO: AUTOCOMPLETAR DATOS AL INGRESAR DNI
        txtDni.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String dniIngresado = txtDni.getText().trim();
                
                // Si el usuario digitó un DNI (ej. 8 dígitos), buscamos en la lista
                if (dniIngresado.length() >= 8) {
                    Optional<Paciente> pacienteExistente = dbPacientesMock.stream()
                        .filter(p -> p.getDni() != null && p.getDni().equals(dniIngresado))
                        .findFirst();

                    if (pacienteExistente.isPresent()) {
                        // Si existe, autocompleta y bloquea los campos para evitar sobreescritura accidental
                        txtNombres.setText(pacienteExistente.get().getNombres());
                        txtApellidos.setText(pacienteExistente.get().getApellidos());
                        txtNombres.setEditable(false);
                        txtApellidos.setEditable(false);
                    } else {
                        // Si no existe (es un paciente nuevo), deja los campos libres
                        txtNombres.setEditable(true);
                        txtApellidos.setEditable(true);
                    }
                } else {
                    // Limpiar si el usuario borra el DNI
                    txtNombres.setText("");
                    txtApellidos.setText("");
                    txtNombres.setEditable(true);
                    txtApellidos.setEditable(true);
                }
            }
        });

        txtNombres = new JTextField();
        txtNombres.setBounds(154, 99, 235, 20);
        contentPane.add(txtNombres);

        txtApellidos = new JTextField();
        txtApellidos.setBounds(154, 130, 235, 20);
        contentPane.add(txtApellidos);

        txtMotivo = new JTextField();
        txtMotivo.setBounds(154, 170, 235, 108);
        contentPane.add(txtMotivo);

        // Botón Registrar
        JButton btnRegistrar = new JButton("Guardar Cita");
        btnRegistrar.setBounds(20, 289, 150, 30);
        contentPane.add(btnRegistrar);

        // Botón Ir a Consulta
        JButton btnIrConsulta = new JButton("Ir a Consultas");
        btnIrConsulta.setBounds(239, 289, 150, 30);
        contentPane.add(btnIrConsulta);
        
        JLabel lblDni = new JLabel("Ingrese el DNI:");
        lblDni.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblDni.setBounds(20, 62, 110, 29);
        contentPane.add(lblDni);
        
        JLabel lblNombres = new JLabel("Nombres:");
        lblNombres.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNombres.setBounds(20, 100, 110, 14);
        contentPane.add(lblNombres);
        
        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblApellidos.setBounds(20, 124, 110, 29);
        contentPane.add(lblApellidos);
        
        JLabel lblMotivo = new JLabel("Motivo de la consulta:");
        lblMotivo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblMotivo.setBounds(10, 186, 169, 73);
        contentPane.add(lblMotivo);

     // EVENTO: GUARDAR
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String dniIngresado = txtDni.getText().trim();
                    
                    // 1. Verificar si el paciente ya existe en la base de datos simulada
                    Optional<Paciente> pacienteExistente = dbPacientesMock.stream()
                        .filter(p -> p.getDni() != null && p.getDni().equals(dniIngresado))
                        .findFirst();

                    Paciente p;
                    if (pacienteExistente.isPresent()) {
                        // Si existe, usamos el mismo objeto (mantiene su historial intacto)
                        p = pacienteExistente.get();
                    } else {
                        // Si es nuevo, lo creamos y lo añadimos a la lista
                        p = new Paciente();
                        p.setDni(dniIngresado);
                        p.setNombres(txtNombres.getText());
                        p.setApellidos(txtApellidos.getText());
                        p.setNumeroHistoriaClinica("HC-" + (dbPacientesMock.size() + 1));
                        dbPacientesMock.add(p);
                    }

                    // 2. Programar la nueva cita
                    CitaMedica cita = new CitaMedica();
                    cita.setIdCita("C" + (p.getCitasMedicas().size() + 1));
                    cita.setFechaHora(LocalDateTime.now());
                    cita.setMotivoConsulta(txtMotivo.getText());
                    cita.programarCita();

                    p.solicitarCita(cita); // Asignar la cita al paciente

                    JOptionPane.showMessageDialog(null, "Cita registrada exitosamente.");
                    
                    // Limpiar y restaurar campos para el próximo registro
                    txtDni.setText(""); 
                    txtNombres.setText(""); 
                    txtApellidos.setText(""); 
                    txtMotivo.setText("");
                    txtNombres.setEditable(true);
                    txtApellidos.setEditable(true);
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        // EVENTO: NAVEGAR (Volver al menú)
        btnIrConsulta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Instancia el menú principal y cierra la ventana actual
                FrmPrincipal principal = new FrmPrincipal();
                principal.setVisible(true);
                dispose();
            }
        });
    }
}