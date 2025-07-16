package proyecto_progra;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CitaRegistroPanel extends JPanel {
    private JTextField txtDniPaciente = new JTextField(15);
    private JButton btnBuscar = new JButton("Buscar Citas");
    private JTable tablaCitas = new JTable();
    private JButton btnModificar = new JButton("Modificar Cita");
    private JComboBox<String> cmbMedicos = new JComboBox<>();
    private JTextField txtFechaHora = new JTextField();
    private JTextField txtMotivo = new JTextField();
    private JComboBox<String> cmbEstado = new JComboBox<>(new String[]{"Pendiente", "Completada", "Cancelada"});

    public CitaRegistroPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.add(new JLabel("DNI Paciente:"));
        panelBusqueda.add(txtDniPaciente);
        panelBusqueda.add(btnBuscar);

        // Configurar tabla
        tablaCitas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaCitas);

        // Panel de edición
        JPanel panelEdicion = new JPanel(new GridLayout(5, 2, 10, 10));
        panelEdicion.add(new JLabel("Médico:"));
        panelEdicion.add(cmbMedicos);
        panelEdicion.add(new JLabel("Fecha/Hora (AAAA-MM-DD HH:MM):"));
        panelEdicion.add(txtFechaHora);
        panelEdicion.add(new JLabel("Motivo:"));
        panelEdicion.add(txtMotivo);
        panelEdicion.add(new JLabel("Estado:"));
        panelEdicion.add(cmbEstado);
        panelEdicion.add(new JLabel(""));
        panelEdicion.add(btnModificar);

        // Cargar médicos al iniciar
        cargarMedicos();

        // Agregar componentes al panel principal
        add(panelBusqueda, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelEdicion, BorderLayout.SOUTH);

        // Eventos
        btnBuscar.addActionListener(e -> buscarCitasPorDni());
        btnModificar.addActionListener(e -> modificarCita());
        tablaCitas.getSelectionModel().addListSelectionListener(e -> cargarDatosSeleccionados());
    }

    private void cargarMedicos() {
        cmbMedicos.removeAllItems();
        try (Connection conn = ConexionMySQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT DNI, Nombre, Apellido FROM Medicos")) {
            while (rs.next()) {
                cmbMedicos.addItem(rs.getString("DNI") + " - " + rs.getString("Nombre") + " " + rs.getString("Apellido"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar médicos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarCitasPorDni() {
        String dni = txtDniPaciente.getText().trim();
        if (!dni.matches("\\d{8}")) {
            JOptionPane.showMessageDialog(this, "DNI debe tener 8 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Médico", "Fecha/Hora", "Motivo", "Estado"});

        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT c.idCita, m.Nombre, m.Apellido, c.FechaHora, c.Motivo, c.Estado " +
                     "FROM Citas c JOIN Medicos m ON c.DniMed = m.DNI " +
                     "WHERE c.DniPac = ?")) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("idCita"),
                    rs.getString("Nombre") + " " + rs.getString("Apellido"),
                    rs.getTimestamp("FechaHora"),
                    rs.getString("Motivo"),
                    rs.getInt("Estado") == 0 ? "Pendiente" : "Completada"
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar citas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        tablaCitas.setModel(model);
    }

    private void cargarDatosSeleccionados() {
        int filaSeleccionada = tablaCitas.getSelectedRow();
        if (filaSeleccionada >= 0) {
            DefaultTableModel model = (DefaultTableModel) tablaCitas.getModel();
            txtFechaHora.setText(model.getValueAt(filaSeleccionada, 2).toString());
            txtMotivo.setText(model.getValueAt(filaSeleccionada, 3).toString());
            cmbEstado.setSelectedItem(model.getValueAt(filaSeleccionada, 4));
        }
    }

    private void modificarCita() {
        int filaSeleccionada = tablaCitas.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una cita primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idCita = (int) tablaCitas.getValueAt(filaSeleccionada, 0);
        String dniMedico = cmbMedicos.getSelectedItem().toString().split(" - ")[0];
        String fechaHora = txtFechaHora.getText();
        String motivo = txtMotivo.getText();
        int estado = cmbEstado.getSelectedIndex(); // 0=Pendiente, 1=Completada, 2=Cancelada

        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE Citas SET DniMed = ?, FechaHora = ?, Motivo = ?, Estado = ? WHERE idCita = ?")) {
            stmt.setString(1, dniMedico);
            stmt.setTimestamp(2, Timestamp.valueOf(fechaHora.replace(" ", "T") + ":00"));
            stmt.setString(3, motivo);
            stmt.setInt(4, estado);
            stmt.setInt(5, idCita);

            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Cita actualizada correctamente.");
                buscarCitasPorDni(); // Refrescar tabla
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar cita: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}