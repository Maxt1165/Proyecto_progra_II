package proyecto_progra;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
public  class MedicoRegistroPanel extends JPanel {
    String[] especialidades = {
    "Pediatría",
    "Cardiología",
    "Dermatología",
    "Neurología",
    "Ginecología",
    "Oftalmología",
    "Medicina General",
    "Psiquiatría"
};

    JTextField txtNombre = new JTextField();
    JTextField txtApellidos = new JTextField();       
    JTextField txtDNI = new JTextField();
    @SuppressWarnings("rawtypes")
    JComboBox cbxEspecialidad = new JComboBox<>(especialidades);
    JButton btnRegistrar = new JButton("Registrar");
    @SuppressWarnings("unused")
    public MedicoRegistroPanel() {

        setLayout(new GridLayout(5, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        add(new JLabel("Nombre *"));
        add(txtNombre);

        add(new JLabel("Apellido Paterno *"));
        add(txtApellidos);

        add(new JLabel("DNI *"));
        add(txtDNI);

        add(new JLabel("Especialidad *"));
        add(cbxEspecialidad);

        add(new JLabel("")); // Espacio vacío
        add(btnRegistrar);

        btnRegistrar.addActionListener(e -> registrarMedico());

    }
    public void registrarMedico() {
            String dni = txtDNI.getText().trim();
            String nombre = txtNombre.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String especialidad = cbxEspecialidad.getSelectedItem().toString();

            if (dni.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || especialidad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Medico medico;
            boolean e = false;

            if (verificarDNI(dni)) {
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
         // Validación básica de formato
        if (dni == null || !dni.matches("\\d{8}")) {
            return false;
        }
        
        // Verificar en base de datos
        try (Connection conn = ConexionMySQL.getConnection()) {
            String sql = "SELECT COUNT(*) FROM Medicos WHERE dni = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, dni);  
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) == 0; // Si no hay registros, el DNI es válido
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar DNI: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }    
}