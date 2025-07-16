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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
    JTextField txtNombrePac = new JTextField();
    JTextField txtApellidoPac = new JTextField();
    JTextField txtDNI = new JTextField();
    JTextField txtFechaNac = new JTextField();
    JTextField txtDomicilio= new JTextField();
    JButton btnRegistrar = new JButton("Registrar");
    JComboBox cbxSexo = new JComboBox<>(new String[]{"Masculino", "Femenino"});
    public RegistroPacientePanel() {
        setLayout(new GridLayout(7, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new JLabel("Nombre *"));
        add(txtNombrePac);

        add(new JLabel("Apellidos *"));
        add(txtApellidoPac);

        add(new JLabel("DNI *"));
        add(txtDNI);

        add(new JLabel("Sexo *"));
        add(cbxSexo);

        add(new JLabel("Fecha de Nacimiento *"));
        add(txtFechaNac);

        add(new JLabel("Domicilio *"));
        add(txtDomicilio);

        add(new JLabel(""));
        add(btnRegistrar);
        btnRegistrar.addActionListener(e -> registrarPaciente());
        
    }

    public void registrarPaciente(){
        if(txtDNI.getText().trim().isEmpty() || txtNombrePac.getText().trim().isEmpty() || txtApellidoPac.getText().trim().isEmpty() || txtFechaNac.getText().trim().isEmpty() || txtDomicilio.getText().trim().isEmpty() || cbxSexo.getSelectedItem().toString().isEmpty()){
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        LocalDate fechaNac;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            fechaNac = LocalDate.parse(txtFechaNac.getText().trim(), formatter);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "La fecha de nacimiento debe tener el formato AAAA-MM-DD.", "Formato de fecha inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Date sqlFechaNac = Date.valueOf(fechaNac);
        Date sqlFechaReg = Date.valueOf(LocalDate.now());
        boolean e = false;
        if(!verificarDNI(txtDNI.getText())){
            Paciente p = new Paciente(
            txtDNI.getText().trim(), 
            txtNombrePac.getText().trim(), 
            txtApellidoPac.getText().trim(), 
            cbxSexo.getSelectedItem().toString(),
            sqlFechaNac, 
            txtDomicilio.getText().trim(),
            sqlFechaReg
            );
            e = PacienteDAO.insertarPaciente(p);
        }
        if (e) {
            JOptionPane.showMessageDialog(this, "Paciente registrado");
        } else {
            JOptionPane.showMessageDialog(this,
            "No se pudo registrar al paciente",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean verificarDNI(String dni){
        //Logica para evitar DNIs repetidos
        return false;
    }
}

// Panel de Registro de Médico
class RegistroMedicoPanel extends JPanel {
    JTextField txtNombre = new JTextField();
    JTextField txtApellidos = new JTextField();       
    JTextField txtDNI = new JTextField();
    JTextField txtEspecialidad = new JTextField();
    JButton btnRegistrar = new JButton("Registrar");
    public RegistroMedicoPanel() {

        setLayout(new GridLayout(5, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        add(new JLabel("Nombre *"));
        add(txtNombre);

        add(new JLabel("Apellido Paterno *"));
        add(txtApellidos);

        add(new JLabel("DNI *"));
        add(txtDNI);

        add(new JLabel("Especialidad *"));
        add(txtEspecialidad);

        add(new JLabel("")); // Espacio vacío
        add(btnRegistrar);

        btnRegistrar.addActionListener(e -> registrarMedico());

    }
    public void registrarMedico() {
            String dni = txtDNI.getText().trim();
            String nombre = txtNombre.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String especialidad = txtEspecialidad.getText().trim();

            if (dni.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || especialidad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Medico medico;
            boolean e = false;

            if (!verificarDNI(dni)) {
                medico = new Medico(dni, nombre, apellidos, especialidad);
                e = MedicoDAO.insertarMedico(medico);
            }

            if (e) {
                JOptionPane.showMessageDialog(this, "Médico registrado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar al médico.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    public boolean verificarDNI(String dni){
        return false;
    }
    }
