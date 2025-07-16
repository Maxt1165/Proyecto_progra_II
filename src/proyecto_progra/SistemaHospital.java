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
import javax.swing.table.DefaultTableModel;

public class SistemaHospital extends JFrame {
    Connection con;
    JPanel panelLateral, panelCentral;
    CardLayout cardLayout;

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
        panelCentral.add(new CitaRegistroPanel(), "citas");
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
            System.out.println("Error de conexión: " + e.getMessage());
        }  
    }

private JPanel crearPanelConsultaCitasDNI(String sql) {
        JPanel jpan = new JPanel();
        DefaultTableModel model = new DefaultTableModel();
        JTable tablaPersonas = new JTable(model);
        model.addColumn("Region");
        model.addColumn("Direccion");
        model.addColumn("Vivienda");
        
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Object[] fila = {rs.getString("DniPac"), rs.getString("DniMed"), rs.getString("FechaHora"), rs.getString("Estado"), rs.getString("Motivo")};
                model.addRow(fila);
            }
        } catch (SQLException ex) {
            jpan.add(new JLabel("No se pudo conectar"));
            System.out.println(ex);
        }
        jpan.add(new JScrollPane(tablaPersonas), BorderLayout.CENTER);
        return jpan;
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
