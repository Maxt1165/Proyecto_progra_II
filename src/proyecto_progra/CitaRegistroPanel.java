package proyecto_progra;

import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

class CitaRegistroPanel extends JPanel {
    String[] especialidadesLista = {
    "Pediatría",
    "Cardiología",
    "Dermatología",
    "Neurología",
    "Ginecología",
    "Oftalmología",
    "Medicina General",
    "Psiquiatría"
};
    private JTextField txtDNI = new JTextField();
    private JComboBox<Medico> cmbMedicos = new JComboBox<>();
    private JComboBox<String> cmbEspecialidades = new JComboBox<>(especialidadesLista);
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
        // add(cmbEspecialidades);

        // Componentes
        txtHorarioManual.setEnabled(false);
        chkHorarioManual.addActionListener(e -> txtHorarioManual.setEnabled(chkHorarioManual.isSelected()));

        // Diseño
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("DNI Paciente *"), gbc);
        gbc.gridx = 1;
        add(txtDNI, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Especialidad *"), gbc);
        gbc.gridx = 1;
        add(cmbEspecialidades, gbc);

        cmbEspecialidades.addActionListener(e->cargarMedicos());

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Médico *"), gbc);
        gbc.gridx = 1;
        add(cmbMedicos, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Motivo *"), gbc);
        gbc.gridx = 1;
        add(txtMotivo, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        add(chkHorarioManual, gbc);
        gbc.gridx = 1;
        add(txtHorarioManual, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnAgendar, gbc);

        btnAgendar.addActionListener(e -> registrarCita());
    }

    private void cargarMedicos() {
            if(cmbMedicos.getItemCount()>=1){
                cmbMedicos.removeAllItems();
            } else {
                
            }
        try (Connection conn = ConexionMySQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Medicos WHERE Especialidad = "+"'"+cmbEspecialidades.getSelectedItem().toString()+"'")) {
            while (rs.next()) {
                cmbMedicos.addItem( new Medico(rs.getString("DNI"),rs.getString("Nombre"),rs.getString("Apellido"),rs.getString("Especialidad")));
            }
        } catch (SQLException ex) {
            System.err.println("Error al cargar médicos: " + ex.getMessage());
        }
    }

 
    public void registrarCita() {
        String dniPaciente = txtDNI.getText().trim();
        String dniMedico = ((Medico) cmbMedicos.getSelectedItem()).getDni();
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

        Timestamp fechaHoraSQL;
    if (chkHorarioManual.isSelected()) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime fechaManual = LocalDateTime.parse(txtHorarioManual.getText().trim(), formatter);
            fechaHoraSQL = Timestamp.valueOf(fechaManual);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use: AAAA-MM-DD HH:MM", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    } else {
        fechaHoraSQL = Timestamp.valueOf(LocalDateTime.now().plusHours(1));
    }


    LocalDateTime ahora = LocalDateTime.now();
    int horaActual = ahora.getHour();
    // Validación y ajuste de horario automático
    if (horaActual >= 20 || horaActual < 8) {
        // Si es de noche, programar para mañana a las 8am
        LocalDateTime primerHorarioValido = ahora.plusDays(1)
                                          .withHour(8)
                                          .withMinute(0)
                                          .withSecond(0);
        fechaHoraSQL = Timestamp.valueOf(primerHorarioValido);
        
        // Mostrar confirmación al usuario
        String mensaje = "El horario actual no es válido. ¿Desea agendar para " 
                       + primerHorarioValido.toLocalDate() + " a las 8:00 am?";
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, 
                                   "Ajuste de Horario", JOptionPane.YES_NO_OPTION);
        
        if (respuesta != JOptionPane.YES_OPTION) {
            return; // El usuario canceló
        }
    }

    Cita cita = new Cita(dniPaciente, dniMedico, fechaHoraSQL, 0, motivo);
    boolean exito = CitaDAO.insertarCita(cita);

    if (exito) {
        JOptionPane.showMessageDialog(this, "Cita registrada con éxito.");
    } else {
        JOptionPane.showMessageDialog(this, "No se pudo registrar la cita.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

}