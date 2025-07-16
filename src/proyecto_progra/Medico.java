/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_progra;

/**
 *
 * @author maoao
 */

public class Medico {
    private String dni;
    private String nombre;
    private String apellidos;
    private String especialidad;

    public Medico(String dni, String nombre, String apellidos, String especialidad) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.especialidad = especialidad;
    }

    public boolean validarDatos() {
        return dni != null && dni.matches("\\d{8}") && !nombre.isBlank() && !apellidos.isBlank();
    }

    public List<Cita> obtenerAgenda() {
        // Lógica para recuperar citas del médico
        return null;
    }

    // Getters y setters...
}
