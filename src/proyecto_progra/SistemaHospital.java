/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_progra;
/**
 *
 * @author LyM
 */
import java.awt.*;
import java.sql.*;
import javax.swing.*;

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
        panelCentral.add(panelRegistroCitas(), "citas");
        panelCentral.add(new PanelHistorial(), "historial");

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
            System.out.println("Error de conexión: " + e.getMessage());
        }  
    }

    private JPanel panelRegistro() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.add("Registrar Paciente", new PacienteRegistroPanel());
        pestañas.add("Registrar Médico", new MedicoRegistroPanel());

        panel.add(pestañas, BorderLayout.CENTER);
        return panel;
    }

    public JPanel panelRegistroCitas() {
        JPanel panel = new JPanel(new BorderLayout());
        JTabbedPane pestañas = new JTabbedPane();
        pestañas.add("Agendar Cita", new CitaRegistroPanel());
        pestañas.add("Modificar Cita", new CitaModificacionPanel());
        panel.add(pestañas, BorderLayout.CENTER);
        return panel;
    }


    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SistemaHospital::new);
    }
}
