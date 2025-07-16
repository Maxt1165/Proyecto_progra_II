/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_progra;

/**
 *
 * @author maoao
 */
import java.sql.*;

public class Cita {
    private String dniPaciente;
    private String dniMedico;
    private Date fechaHora;
    private int estado;
    private String motivo;

    public Cita(String dniPaciente, String dniMedico, Date fechaHora, int estado, String motivo) {
        this.dniPaciente = dniPaciente;
        this.dniMedico = dniMedico;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.motivo = motivo;
    }

    public void marcarComoCompletada() {
        this.estado = 1;
    }

    public void cancelar() {
        this.estado = 0;
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

    public Date getFechaHora() {
        return fechaHora;
    }
    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getEstado() {
        return estado;
    }
    public void setEstado(int estado) {
        this.estado = estado;
    }
}
