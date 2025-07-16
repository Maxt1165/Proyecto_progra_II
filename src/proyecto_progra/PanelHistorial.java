package proyecto_progra;

import javax.swing.*;
import java.awt.*;

class PanelHistorial extends JPanel {
    JTextField txtBuscarDNI = new JTextField();
    JButton btnBuscar = new JButton("Buscar Historial");
    JTabbedPane tabsHistorial = new JTabbedPane();
    JButton btnAñadir = new JButton("Añadir Registro Clínico");

    // Paneles para las dos pestañas
    JScrollPane scrollCitas = new JScrollPane();
    JScrollPane scrollClinico = new JScrollPane();

    public PanelHistorial() {
        setLayout(new BorderLayout());
        
        // Top panel: búsqueda
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("DNI del paciente:"), BorderLayout.WEST);
        topPanel.add(txtBuscarDNI, BorderLayout.CENTER);
        topPanel.add(btnBuscar, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Tabs de historial
        tabsHistorial.addTab("Historial de Citas", scrollCitas);
        tabsHistorial.addTab("Historial Clínico", scrollClinico);
        add(tabsHistorial, BorderLayout.CENTER);

        // Botón inferior
        add(btnAñadir, BorderLayout.SOUTH);

        // Acción del botón buscar
        btnBuscar.addActionListener(e -> {
            String dni = txtBuscarDNI.getText().trim();
            JTable tablaCitas = Historial.Obtenerhistorial(dni);
            JTable tablaClinico = Historial.Obtenerhistorial(dni);

            // Actualizar scroll panes
            scrollCitas.setViewportView(tablaCitas);
            scrollClinico.setViewportView(tablaClinico);
        });
    }
}