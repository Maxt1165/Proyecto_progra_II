package proyecto_progra;

import java.sql.*;
import javax.swing.JOptionPane;

public class PacienteDAO {

    public static boolean insertarPaciente(Paciente paciente) {
        String sql = "INSERT INTO Pacientes (DNI, Nombre, Apellido, Sexo, FechaNac, FechaReg, Domicilio) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionMySQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, paciente.getDni());
            ps.setString(2, paciente.getNombre());
            ps.setString(3, paciente.getApellidos());
            ps.setString(4, paciente.getSexo());
            ps.setDate(5, paciente.getFechaNacimiento());
            ps.setDate(6, paciente.getFechaRegistro());
            ps.setString(7, paciente.getDomicilio());
           
                    int respuesta = JOptionPane.showConfirmDialog(
                        null,
                        "¿Está seguro que desea ingresar al paciente: " + paciente.toString(),
                        "Confirmar cambio",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                    );
                    
                    if (respuesta != JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(null, "Revise sus datos");
                        return false;
                    }else{
                        int filasInsertadas = ps.executeUpdate();
                            return filasInsertadas > 0;                 
                   }

        } catch (SQLException e) {
            System.err.println("Error al insertar paciente: " + e.getMessage());
            return false;
        }
    }
}
