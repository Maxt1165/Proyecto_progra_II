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

    public static boolean medicoDisponible(String dniMedico, Timestamp fechaHora) {
    // Validar horario (8am-8pm)
        int hora = fechaHora.toLocalDateTime().getHour();
        if (hora < 8 || hora >= 20) {
            return false;
        }

        // Validar si el médico ya tiene cita a esa hora
        String sql = "SELECT COUNT(*) FROM Citas WHERE DniMed = ? AND FechaHora = ?";
        try (Connection conn = ConexionMySQL.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dniMedico);
            stmt.setTimestamp(2, fechaHora);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) == 0;
        } catch (SQLException ex) {
            System.err.println("Error al verificar disponibilidad: " + ex.getMessage());
            return false;
        }
    }

    public static boolean insertarHistorial(int idCita, String dniPaciente, String diagnostico, 
                                          String tratamiento, String observaciones) {
        String sql = "INSERT INTO Historial (idCita, dniPaciente, fecha, diagnostico, tratamiento, observaciones) " +
                     "VALUES (?, ?, CURRENT_DATE(), ?, ?, ?)";
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idCita);
            stmt.setString(2, dniPaciente);
            stmt.setString(3, diagnostico);
            stmt.setString(4, tratamiento);
            stmt.setString(5, observaciones);

            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al insertar historial: " + ex.getMessage());
            return false;
        }
    }
}
