package es.rentcarplus.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionDB {

    // Instancia única
    private static ConexionDB instance;
    private Connection connection;

    // Constructor privado: Carga los datos y conecta
    private ConexionDB() {
        try {
            // 1. Cargar las propiedades desde el archivo
            Properties props = new Properties();
            try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
                if (input == null) {
                    throw new RuntimeException("Error: No se encuentra 'application.properties' en src/main/resources");
                }
                props.load(input);
            }

            // 2. Establecer la conexión con los datos leídos
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");

            this.connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Conexión establecida correctamente a: " + url);

        } catch (IOException | SQLException e) {
            System.err.println("Fallo al conectar con la Base de Datos");
            e.printStackTrace();
            throw new RuntimeException("Error crítico de conexión", e);
        }
    }

    // Método de acceso global
    public static ConexionDB getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new ConexionDB();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instance;
    }

    // Getter para usar la conexión en otros lados
    public Connection getConnection() {
        return connection;
    }
}