/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_progra;

import java.sql.Date;
import java.util.List;

public class Paciente {
    private String dni;
    private String nombre;
    private String apellidos;
    private String sexo;
    private Date fechaNacimiento;
    private String domicilio;
    private Date fechaRegistro;

    public Paciente(String dni, String nombre, String apellidos, String sexo, Date fechaNacimiento, String domicilio, Date fechaRegistro) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.domicilio = domicilio;
        this.fechaRegistro = fechaRegistro;
    }

    public boolean validarDatos() {
        return dni != null && dni.matches("\\d{8}") && !nombre.isBlank() && !apellidos.isBlank();
    }

    public List<Cita> obtenerCitas() {
        // Lógica para recuperar citas desde la base de datos
        return null;
    }

    public List<Historial> obtenerHistorialClinico() {
        // Lógica para recuperar historial desde la base de datos
        return null;
    }
    
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

    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDomicilio() {
        return domicilio;
    }
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(nombre).append(" ").append(apellidos);
        sb.append("\n");
        sb.append("DNI: ").append(dni);
        sb.append("\n");
        sb.append("Sexo: ").append(sexo);
        sb.append("\n");
        sb.append("Fecha de Nacimiento: ").append(fechaNacimiento);
        sb.append("\n");
        sb.append("Domicilio: ").append(domicilio);
        return sb.toString();
    }
}


