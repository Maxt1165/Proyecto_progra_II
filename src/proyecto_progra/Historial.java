/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_progra;

/**
 *
 * @author maoao
 */
import java.time.LocalDate;

public class Historial {
    private int idCita;
    private String dniPaciente;
    private LocalDate fecha;
    private String diagnostico;
    private String tratamiento;
    private String observaciones;

    public Historial(int idCita, String dniPaciente, LocalDate fecha, String diagnostico, String tratamiento, String observaciones) {
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

    // Getters y setters...
}
