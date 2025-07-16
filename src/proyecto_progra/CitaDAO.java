package proyecto_progra;

import java.sql.*;

public class CitaDAO {

    public static boolean insertarCita(Cita cita) {
        String sql = "INSERT INTO Citas (DniPac, DniMed, FechaHora, Estado, Motivo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cita.getDniPaciente());
            stmt.setString(2, cita.getDniMedico());
            stmt.setTimestamp(3, cita.getFechaHora()); // DATETIME compatible
            stmt.setInt(4, cita.getEstado());
            stmt.setString(5, cita.getMotivo());

            return stmt.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.err.println("Error al insertar cita: " + ex.getMessage());
            return false;
        }
    }

    public static boolean existePaciente(String dni) {
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT DNI FROM Pacientes WHERE DNI = ?")) {
            stmt.setString(1, dni);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean existeMedico(String dni) {
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT DNI FROM Medicos WHERE DNI = ?")) {
            stmt.setString(1, dni);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }
}
