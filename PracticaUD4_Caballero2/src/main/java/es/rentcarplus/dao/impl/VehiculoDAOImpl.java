package es.rentcarplus.dao.impl;

import es.rentcarplus.model.Vehiculo;
import es.rentcarplus.util.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAOImpl {

    // Requisito: Alta de un vehículo
    public void guardar(Vehiculo v) throws SQLException {
        String sql = "INSERT INTO vehiculos (matricula, marca, modelo, disponible) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = ConexionDB.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, v.getMatricula());
            ps.setString(2, v.getMarca());
            ps.setString(3, v.getModelo());
            ps.setBoolean(4, v.isDisponible());
            ps.executeUpdate();
        }
    }

    // Requisito: Consulta de vehículos disponibles
    public List<Vehiculo> listarDisponibles() throws SQLException {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM vehiculos WHERE disponible = TRUE";

        try (Statement st = ConexionDB.getInstance().getConnection().createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Vehiculo(
                        rs.getString("matricula"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getBoolean("disponible")));
            }
        }
        return lista;
    }

    public void cambiarDisponibilidad(String matricula, boolean disponible, Connection conn) throws SQLException {
        String sql = "UPDATE vehiculos SET disponible = ? WHERE matricula = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, disponible);
            ps.setString(2, matricula);
            ps.executeUpdate();
        }
    }
}