package proyecto_progra;

import javax.swing.*;
import java.awt.*;
public  class RegistroMedicoPanel extends JPanel {
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