/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_progra;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CitasHistorial {
    private int idCita;
    private String dniPaciente;
    private LocalDate fecha;
    private String diagnostico;
    private String tratamiento;
    private String observaciones;

    public CitasHistorial(int idCita, String dniPaciente, LocalDate fecha, String diagnostico, String tratamiento, String observaciones) {
        this.idCita = idCita;
        this.dniPaciente = dniPaciente;
        this.fecha = fecha;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.observaciones = observaciones;
    }

    // Métodos útiles
    public boolean validar() {
        return diagnostico != null && !diagnostico.isBlank();
    }
        
    public static JTable Obtenerhistorial(String dniPaciente) {
    final String SQL = "{call sp_HistorialPorPaciente(?)}";  // Sintaxis estándar
    
    try (Connection conn = ConexionMySQL.getConnection();
         CallableStatement stmt = conn.prepareCall(SQL)) {
        stmt.setString(1, dniPaciente);
        stmt.execute();
            
        DefaultTableModel modelo = new DefaultTableModel(new String[]{"Nombre Paciente", "Apellido Paciente", "Diagnóstico Actual", "Tratamiento", "Observaciones"}, 0);
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 
         //Si devuelve un ResultSet
        try (ResultSet rs = stmt.getResultSet()) {
            while (rs != null && rs.next()) {
            Object[] fila = {
                    rs.getString("Nombre Paciente"),
                    rs.getString("Apellido Paciente"),
                    rs.getString("Diagnostico Actual"),
                    rs.getString("Tratamiento"),
                    rs.getString("Observaciones")
                };
                modelo.addRow(fila);
            }          }
            //JTable tabla = new JTable(modelo);
                return new JTable(modelo);
        } catch (SQLException e) {
            System.err.println("Error al insertar paciente: " + e.getMessage());
            throw new RuntimeException("Error en procedimiento almacenado", e);
        }
    }
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 

    // Getters y setters...
    public int getIdCita() {
        return idCita;
    }
    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public String getDniPaciente() {
        return dniPaciente;
    }
    public void setDniPaciente(String dniPaciente) {
        this.dniPaciente = dniPaciente;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDiagnostico() {
        return diagnostico;
    }
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }
    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
