package es.rentcarplus.service;

import es.rentcarplus.dao.impl.AlquilerDAOImpl;
import es.rentcarplus.dao.impl.VehiculoDAOImpl;
import es.rentcarplus.model.Alquiler;
import es.rentcarplus.util.ConexionDB;
import java.sql.Connection;
import java.sql.SQLException;

public class RentCarService {

    public void registrarAlquiler(Alquiler alquiler) {
        Connection conn = null;

        try {
            // 1. Obtener conexión y desactivar autoguardado (Inicio Transacción)
            conn = ConexionDB.getInstance().getConnection();
            conn.setAutoCommit(false);

            // 2. Operación A: Guardar el alquiler
            // Pasamos 'conn' para que use la MISMA conexión
            new AlquilerDAOImpl().guardarTransaccional(alquiler, conn);

            // 3. Operación B: Marcar el coche como NO disponible (false)
            new VehiculoDAOImpl().cambiarDisponibilidad(alquiler.getMatricula(), false, conn);

            // 4. Si todo salió bien, guardamos los cambios definitivamente
            conn.commit();
            System.out.println("✅ Transacción completada: Alquiler creado y coche bloqueado.");

        } catch (Exception e) {
            // 5. Si algo falló, deshacemos TODO (Rollback)
            try {
                if (conn != null)
                    conn.rollback();
                System.err.println("❌ Error en la transacción. Se han deshecho los cambios.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // 6. Restaurar el estado normal de la conexión
            try {
                if (conn != null)
                    conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}