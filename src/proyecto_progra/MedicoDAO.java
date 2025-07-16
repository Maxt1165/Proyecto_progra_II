package proyecto_progra;

import java.sql.*;

public class MedicoDAO {

    public static boolean insertarMedico(Medico medico) {
        String sql = "INSERT INTO Medicos (DNI, Nombre, Apellido, Especialidad) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, medico.getDni());
            stmt.setString(2, medico.getNombre());
            stmt.setString(3, medico.getApellidos());
            stmt.setString(4, medico.getEspecialidad());

            return stmt.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.err.println("Error al insertar médico: " + ex.getMessage());
            return false;
        }
    }
}
