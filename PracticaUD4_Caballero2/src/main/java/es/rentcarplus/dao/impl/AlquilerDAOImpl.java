package es.rentcarplus.dao.impl;

import es.rentcarplus.model.Alquiler;
import es.rentcarplus.util.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlquilerDAOImpl {

    public void guardarTransaccional(Alquiler a, Connection conn) throws SQLException {
        String sql = "INSERT INTO alquileres (dni, matricula, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getDniCliente());
            ps.setString(2, a.getMatricula());
            // Convertimos LocalDate de Java a Date de SQL
            ps.setDate(3, Date.valueOf(a.getFechaInicio()));
            ps.setDate(4, Date.valueOf(a.getFechaFin()));

            ps.executeUpdate();
        }
    }

    public List<Alquiler> historialPorCliente(String dni) throws SQLException {
        List<Alquiler> historial = new ArrayList<>();
        String sql = "SELECT * FROM alquileres WHERE dni = ? ORDER BY fecha_inicio DESC";

        try (PreparedStatement ps = ConexionDB.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                historial.add(new Alquiler(
                        rs.getLong("id"),
                        rs.getString("dni"),
                        rs.getString("matricula"),
                        // Convertimos Date de SQL a LocalDate de Java
                        rs.getDate("fecha_inicio").toLocalDate(),
                        rs.getDate("fecha_fin").toLocalDate()));
            }
        }
        return historial;
    }
}