package proyecto_progra;

import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.*;
   

public class PacienteRegistroPanel extends JPanel {
    JTextField txtNombrePac = new JTextField();
    JTextField txtApellidoPac = new JTextField();
    JTextField txtDNI = new JTextField();
    JTextField txtFechaNac = new JTextField();
    JTextField txtDomicilio= new JTextField();
    JButton btnRegistrar = new JButton("Registrar");
    
    @SuppressWarnings("rawtypes")
    JComboBox cbxSexo = new JComboBox<>(new String[]{"M", "F"});

    @SuppressWarnings("unused")
    public PacienteRegistroPanel() {
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
        try {
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
    
            if(verificarDNI(txtDNI.getText())){
                Paciente p = new Paciente(
                txtDNI.getText().trim(), 
                txtNombrePac.getText().trim(), 
                txtApellidoPac.getText().trim(), 
                cbxSexo.getSelectedItem().toString(),
                sqlFechaNac, 
                txtDomicilio.getText().trim(),
                sqlFechaReg);
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
        } catch (Exception ex) {
            ex.printStackTrace(); // imprime error en consola
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public boolean verificarDNI(String dni){
           // Validación básica de formato
        if (dni == null || !dni.matches("\\d{8}")) {
            return false;
        }
        
        // Verificar en base de datos
        try (Connection conn = ConexionMySQL.getConnection()) {
            String sql = "SELECT COUNT(*) FROM Pacientes WHERE dni = ?";           
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