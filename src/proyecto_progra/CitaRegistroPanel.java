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

    private void registrarCita() {
        String dniPaciente = txtDNI.getText().trim();
        String dniMedico = cmbMedicos.getSelectedItem().toString().split(" - ")[0];
        String motivo = txtMotivo.getText().trim();

        // Validaciones (igual que antes)
        // ...

        Timestamp fechaHoraSQL;
        if (chkHorarioManual.isSelected()) {
            try {
                LocalDateTime fechaManual = LocalDateTime.parse(txtHorarioManual.getText().trim().replace(" ", "T"));
                fechaHoraSQL = Timestamp.valueOf(fechaManual);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use: AAAA-MM-DD HH:MM", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            fechaHoraSQL = Timestamp.valueOf(LocalDateTime.now().plusHours(1));
        }

        // Resto del código para insertar la cita...
    }
}