package es.rentcarplus.dao.impl;

import es.rentcarplus.model.Cliente;
import es.rentcarplus.model.Direccion;
import es.rentcarplus.model.TipoVia;
import es.rentcarplus.util.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl {

    // 1. GUARDAR CLIENTE (INSERT)
    public void guardar(Cliente c) throws SQLException {
        String sql = "INSERT INTO clientes (dni, nombre, email, telefono, direccion) " +
                "VALUES (?, ?, ?, ?, ROW(?::tipo_via, ?, ?, ?)::direccion)";

        try (Connection conn = ConexionDB.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getDni());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getTelefono());

            // Descomponemos la dirección para el ROW(...)
            Direccion d = c.getDireccion();
            ps.setString(5, d.getVia().name()); // Enum a String
            ps.setString(6, d.getNombre());
            ps.setInt(7, d.getNumero());
            ps.setString(8, d.getCp());

            ps.executeUpdate();
        }
    }

    // 2. LISTAR TODOS (SELECT)
    public List<Cliente> listarTodos() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = ConexionDB.getInstance().getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                // Parseamos la dirección que viene como String: "(calle,Colón,12,46004)"
                String rawDir = rs.getString("direccion");

                // Limpieza rápida: quitar paréntesis y comillas
                String limpio = rawDir.replace("(", "").replace(")", "").replace("\"", "");
                String[] partes = limpio.split(",");

                // Reconstruimos el objeto Dirección
                Direccion dir = new Direccion(
                        TipoVia.valueOf(partes[0]), // String a Enum
                        partes[1], // Nombre calle
                        Integer.parseInt(partes[2]), // Numero
                        partes[3] // CP
                );

                lista.add(new Cliente(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        dir));
            }
        }
        return lista;
    }

    // 3. BUSCAR POR DNI (SELECT WHERE)
    public Cliente buscarPorDni(String dni) throws SQLException {
        for (Cliente c : listarTodos()) {
            if (c.getDni().equals(dni))
                return c;
        }
        return null;
    }
}