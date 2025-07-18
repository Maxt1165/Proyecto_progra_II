package proyecto_progra;

import java.awt.*;
import javax.swing.*;

class PanelHistorial extends JPanel {
    JTextField txtBuscarDNI = new JTextField();
    JButton btnBuscar = new JButton("Buscar Historial");
    JTabbedPane tabsHistorial = new JTabbedPane();
    JScrollPane scrollCitas = new JScrollPane();
    JScrollPane scrollClinico = new JScrollPane();

    public PanelHistorial() {
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("DNI del paciente:"), BorderLayout.WEST);
        topPanel.add(txtBuscarDNI, BorderLayout.CENTER);
        topPanel.add(btnBuscar, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        tabsHistorial.addTab("Historial de Citas", scrollCitas);
        tabsHistorial.addTab("Historial Clínico", scrollClinico);
        add(tabsHistorial, BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> {
            String dni = txtBuscarDNI.getText().trim();
            JTable tablaCitas = CitasHistorial.Obtenerhistorial(dni);
            JTable tablaClinico = Historial.Obtenerhistorial(dni);

            // Actualizar scroll panes
            scrollCitas.setViewportView(tablaCitas);
            scrollClinico.setViewportView(tablaClinico);
        });
    }
}