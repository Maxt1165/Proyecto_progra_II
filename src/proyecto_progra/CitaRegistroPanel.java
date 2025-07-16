package proyecto_progra;

import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.swing.*;

class CitaRegistroPanel extends JPanel {
        
    JTextField txtDNI = new JTextField();
    JTextField txtMedico = new JTextField();
    JTextField txtMotivo = new JTextField();
        
    public CitaRegistroPanel() {
        setLayout(new GridLayout(6, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnHorarioAuto = new JButton("Horario Automático");
        JButton btnElegirHorario = new JButton("Elegir Horario");
        JButton btnAgendar = new JButton("Agendar Cita");

        add(new JLabel("DNI Paciente *"));
        add(txtDNI);

        add(new JLabel("Médico*"));
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