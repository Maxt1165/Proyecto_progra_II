/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_progra;
/**
 *
 * @author LyM
 */
import javax.swing.*;
import java.awt.*;

public class SistemaHospital extends JFrame {

    JPanel panelLateral, panelCentral;
    CardLayout cardLayout;

    @SuppressWarnings("unused")
    public SistemaHospital() {
        setTitle("Sistema de Gestión de Hospitales");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel lateral
        panelLateral = new JPanel();
        panelLateral.setBackground(new Color(33, 150, 243));
        panelLateral.setLayout(new GridLayout(3, 1, 10, 10));
        panelLateral.setPreferredSize(new Dimension(200, 600));

        JButton btnRegistro = new JButton("Registro");
        JButton btnCitas = new JButton("Gestión Citas");
        JButton btnHistorial = new JButton("Historial Paciente");

        panelLateral.add(btnRegistro);
        panelLateral.add(btnCitas);
        panelLateral.add(btnHistorial);

        add(panelLateral, BorderLayout.WEST);

        // Panel central con CardLayout
        cardLayout = new CardLayout();
        panelCentral = new JPanel(cardLayout);

        panelCentral.add(panelRegistro(), "registro");
        panelCentral.add(panelCitas(), "citas");
        panelCentral.add(panelHistorial(), "historial");

        add(panelCentral, BorderLayout.CENTER);

        btnRegistro.addActionListener(e -> cardLayout.show(panelCentral, "registro"));
        btnCitas.addActionListener(e -> cardLayout.show(panelCentral, "citas"));
        btnHistorial.addActionListener(e -> cardLayout.show(panelCentral, "historial"));

        setVisible(true);
    }

    private JPanel panelRegistro() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.add("Registrar Paciente", new RegistroPacientePanel());
        pestañas.add("Registrar Médico", new RegistroMedicoPanel());

        panel.add(pestañas, BorderLayout.CENTER);
        return panel;
    }

    private JPanel panelCitas() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField txtDNI = new JTextField();
        JTextField txtMedico = new JTextField();
        JTextField txtMotivo = new JTextField();

        JButton btnHorarioAuto = new JButton("Horario Automático");
        JButton btnElegirHorario = new JButton("Elegir Horario");
        JButton btnAgendar = new JButton("Agendar Cita");

        panel.add(new JLabel("DNI Paciente *"));
        panel.add(txtDNI);

        panel.add(new JLabel("Médico o Especialidad *"));
        panel.add(txtMedico);

        panel.add(new JLabel("Motivo de consulta *"));
        panel.add(txtMotivo);

        panel.add(btnHorarioAuto);
        panel.add(btnElegirHorario);

        panel.add(new JLabel(""));
        panel.add(btnAgendar);

        return panel;
    }

    private JPanel panelHistorial() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextField txtBuscarDNI = new JTextField();
        JButton btnBuscar = new JButton("Buscar Historial");

        JTabbedPane tabsHistorial = new JTabbedPane();
        JTextArea historialCitas = new JTextArea("Aquí se muestran las citas...");
        JTextArea historialClinico = new JTextArea("Aquí se muestran los registros clínicos...");
        JButton btnAñadir = new JButton("Añadir Registro Clínico");

        tabsHistorial.add("Historial de Citas", new JScrollPane(historialCitas));
        tabsHistorial.add("Historial Clínico", new JScrollPane(historialClinico));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("DNI del paciente:"), BorderLayout.WEST);
        topPanel.add(txtBuscarDNI, BorderLayout.CENTER);
        topPanel.add(btnBuscar, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(tabsHistorial, BorderLayout.CENTER);
        panel.add(btnAñadir, BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SistemaHospital::new);
    }
}

// Panel de Registro de Paciente
class RegistroPacientePanel extends JPanel {
    public RegistroPacientePanel() {
        setLayout(new GridLayout(8, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new JLabel("Nombre *"));
        add(new JTextField());

        add(new JLabel("Apellido Paterno *"));
        add(new JTextField());

        add(new JLabel("Apellido Materno *"));
        add(new JTextField());

        add(new JLabel("DNI *"));
        add(new JTextField());

        add(new JLabel("Sexo *"));
        add(new JComboBox<>(new String[]{"Masculino", "Femenino"}));

        add(new JLabel("Fecha de Nacimiento *"));
        add(new JTextField());

        add(new JLabel("Domicilio *"));
        add(new JTextField());

        add(new JLabel(""));
        add(new JButton("Registrar"));
    }
}

// Panel de Registro de Médico
class RegistroMedicoPanel extends JPanel {
    public RegistroMedicoPanel() {
        setLayout(new GridLayout(6, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new JLabel("Nombre *"));
        add(new JTextField());

        add(new JLabel("Apellido Paterno *"));
        add(new JTextField());

        add(new JLabel("Apellido Materno *"));
        add(new JTextField());

        add(new JLabel("DNI *"));
        add(new JTextField());

        add(new JLabel("Especialidad *"));
        add(new JTextField());

        add(new JLabel(""));
        add(new JButton("Registrar"));
    }
    
    
}