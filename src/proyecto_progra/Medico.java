/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_progra;
/**
 *
 * @author maoao
 */
import java.util.List;

public class Medico {
    private String dni;
    private String nombre;
    @Override
    public String toString() {
        return nombre + " " + apellidos;
    }
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

    // Getters y setters
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEspecialidad() {
        return especialidad;
    }
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
