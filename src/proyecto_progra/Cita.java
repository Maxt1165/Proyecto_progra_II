/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_progra;

/**
 *
 * @author maoao
 */
import java.time.LocalDateTime;

public class Cita {
    private int id;
    private String dniPaciente;
    private String dniMedico;
    private LocalDateTime fechaHora;
    private String motivo;
    private String estado; // pendiente, completada, cancelada

    public Cita(int id, String dniPaciente, String dniMedico, LocalDateTime fechaHora, String motivo, String estado) {
        this.id = id;
        this.dniPaciente = dniPaciente;
        this.dniMedico = dniMedico;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.estado = estado;
    }

    public void marcarComoCompletada() {
        this.estado = "completada";
    }

    public void cancelar() {
        this.estado = "cancelada";
    }

    // Getters y setters...

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDniPaciente() {
        return dniPaciente;
    }
    public void setDniPaciente(String dniPaciente) {
        this.dniPaciente = dniPaciente;
    }

    public String getDniMedico() {
        return dniMedico;
    }
    public void setDniMedico(String dniMedico) {
        this.dniMedico = dniMedico;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
