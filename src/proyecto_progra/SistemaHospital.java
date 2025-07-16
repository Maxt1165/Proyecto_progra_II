/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_progra;
/**
 *
 * @author LyM
 */
import java.sql.*;
import java.time.LocalDateTime;
import javax.swing.*;
import java.awt.*;

public class SistemaHospital extends JFrame {
    Connection con;
    JPanel panelLateral, panelCentral;
    CardLayout cardLayout;

    @SuppressWarnings("unused")
    public SistemaHospital() {
        setTitle("Sistema de Gestión de Hospitales");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        establecerConexion();
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

        cardLayout = new CardLayout();
        panelCentral = new JPanel(cardLayout);

        panelCentral.add(panelRegistro(), "registro");
        panelCentral.add(new panelCitas(), "citas");
        panelCentral.add(panelHistorial(), "historial");

        add(panelCentral, BorderLayout.CENTER);

        btnRegistro.addActionListener(e -> cardLayout.show(panelCentral, "registro"));
        btnCitas.addActionListener(e -> cardLayout.show(panelCentral, "citas"));
        btnHistorial.addActionListener(e -> cardLayout.show(panelCentral, "historial"));

        setVisible(true);
    }

    void establecerConexion(){
        try {
            con = ConexionMySQL.getConnection();
        } catch (Exception e) {
            // TODO: handle exception
        }  
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

    class panelCitas extends JPanel {
        
        JTextField txtDNI = new JTextField();
        JTextField txtMedico = new JTextField();
        JTextField txtMotivo = new JTextField();
        public panelCitas(){
        setLayout(new GridLayout(6, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        JButton btnHorarioAuto = new JButton("Horario Automático");
        JButton btnElegirHorario = new JButton("Elegir Horario");
        JButton btnAgendar = new JButton("Agendar Cita");

        add(new JLabel("DNI Paciente *"));
        add(txtDNI);

        add(new JLabel("Médico o Especialidad *"));
        add(txtMedico);

        add(new JLabel("Motivo de consulta *"));
        add(txtMotivo);

        add(btnHorarioAuto);
        add(btnElegirHorario);

        add(new JLabel(""));
        add(btnAgendar);

        btnAgendar.addActionListener(e->registrarCita());
        }

        public void registrarCita() {
        String dniPaciente = txtDNI.getText().trim();
        String dniMedico = txtMedico.getText().trim();
        String motivo = txtMotivo.getText().trim();

        if (dniPaciente.isEmpty() || dniMedico.isEmpty() || motivo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!CitaDAO.existePaciente(dniPaciente)) {
            JOptionPane.showMessageDialog(this, "El paciente no está registrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!CitaDAO.existeMedico(dniMedico)) {
            JOptionPane.showMessageDialog(this, "El médico no está registrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDateTime ahora = LocalDateTime.now().plusHours(1); 
        Timestamp fechaHoraSQL = Timestamp.valueOf(ahora); 

        Cita cita = new Cita(
            dniPaciente,
            dniMedico,
            fechaHoraSQL,
            0, 
            motivo
        );

        boolean exito = CitaDAO.insertarCita(cita);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Cita registrada con éxito.");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar la cita.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

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
