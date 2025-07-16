package proyecto_progra;

import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import javax.swing.*;

class CitaRegistroPanel extends JPanel {
    private JTextField txtDNI = new JTextField();
    private JComboBox<String> cmbMedicos = new JComboBox<>();
    private JTextField txtMotivo = new JTextField();
    private JCheckBox chkHorarioManual = new JCheckBox("Horario Manual");
    private JTextField txtHorarioManual = new JTextField("AAAA-MM-DD HH:MM");
    private JButton btnAgendar = new JButton("Agendar Cita");

    @SuppressWarnings("unused")
    public CitaRegistroPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Cargar médicos en el ComboBox
        cargarMedicos();

        // Componentes
        txtHorarioManual.setEnabled(false);
        chkHorarioManual.addActionListener(e -> txtHorarioManual.setEnabled(chkHorarioManual.isSelected()));

        // Diseño
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("DNI Paciente *"), gbc);
        gbc.gridx = 1;
        add(txtDNI, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Médico *"), gbc);
        gbc.gridx = 1;
        add(cmbMedicos, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Motivo *"), gbc);
        gbc.gridx = 1;
        add(txtMotivo, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(chkHorarioManual, gbc);
        gbc.gridx = 1;
        add(txtHorarioManual, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnAgendar, gbc);

        btnAgendar.addActionListener(e -> registrarCita());
    }

    private void cargarMedicos() {
        try (Connection conn = ConexionMySQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT DNI, Nombre, Apellido FROM Medicos")) {
            while (rs.next()) {
                cmbMedicos.addItem(rs.getString("DNI") + " - " + rs.getString("Nombre") + " " + rs.getString("Apellido"));
            }
        } catch (SQLException ex) {
            System.err.println("Error al cargar médicos: " + ex.getMessage());
        }
    }

    public void registrarCita() {
        String dniPaciente = txtDNI.getText().trim();
        String dniMedico = cmbMedicos.getSelectedItem().toString();
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