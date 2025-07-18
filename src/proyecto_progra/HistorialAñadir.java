package proyecto_progra;
import java.awt.*;
import javax.swing.*; 

public class HistorialAñadir extends JDialog {
    private JTextArea taDiagnostico;
    private JTextArea taTratamiento;
    private JTextArea taObservaciones;
    private int idCita;
    private String DNIpaciente;
    
    public HistorialAñadir(JFrame padre, String titulo, boolean modal,int idcita,String DNIPaciente) {
        super(padre, titulo, modal);
        this.idCita = idcita;
        this.DNIpaciente = DNIPaciente;

        setSize(500, 400);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 10, 15));
        
        JLabel jlDiagnostico = new JLabel("Diagnóstico Actual:");
        taDiagnostico = crearTextAreaConScroll();
        
        JLabel jlTratamiento = new JLabel("Tratamiento:");
        taTratamiento = crearTextAreaConScroll();
        
        JLabel jlObservaciones = new JLabel("Observaciones:");
        taObservaciones = crearTextAreaConScroll();

        panelCampos.add(jlDiagnostico);
        panelCampos.add(new JScrollPane(taDiagnostico));
        panelCampos.add(jlTratamiento);
        panelCampos.add(new JScrollPane(taTratamiento));
        panelCampos.add(jlObservaciones);
        panelCampos.add(new JScrollPane(taObservaciones));

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarDatos());

        panelBoton.add(btnGuardar);
        
        panelPrincipal.add(panelCampos, BorderLayout.CENTER);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);
        add(panelPrincipal);

        pack();
       
    }

    private JTextArea crearTextAreaConScroll() {
        JTextArea textArea = new JTextArea(5, 20);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        return textArea;
    }

    private void guardarDatos() {
        String diagnostico = taDiagnostico.getText();
        String tratamiento = taTratamiento.getText();
        String observaciones = taObservaciones.getText();
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de guardar este historial médico?",
            "Confirmar guardado",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        if (CitaDAO.insertarHistorial(idCita,DNIpaciente, diagnostico, tratamiento, observaciones)) {
            JOptionPane.showMessageDialog(this, "Historial guardado exitosamente");
            this.dispose();  // Cerrar el diálogo
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error al guardar el historial. Consulte con el administrador",
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    } 
}