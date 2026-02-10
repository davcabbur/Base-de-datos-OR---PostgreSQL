package es.rentcarplus.app;

import es.rentcarplus.dao.impl.*;
import es.rentcarplus.model.*;
import es.rentcarplus.service.RentCarService;
import java.time.LocalDate;
import java.util.List;

public class App {

	public static void main(String[] args) {
		try {
			// 1. ALTA DE CLIENTE
			Direccion dir = new Direccion(TipoVia.avenida, "Cortes Valencianas", 50, "46015");
			Cliente c = new Cliente("23886049C", "David CaPracticaUD4_Caballero (1)ballero", "david@david.com", "675823441", dir);

			try {
				new ClienteDAOImpl().guardar(c);
				System.out.println("Cliente guardado.");
			} catch (Exception e) {
				System.out.println("El cliente ya existe.");
			}

			// 2. ALTA DE VEHÍCULO
			Vehiculo v = new Vehiculo("4901DLM", "Ford", "Focus MK2", true);

			try {
				new VehiculoDAOImpl().guardar(v);
				System.out.println("Vehículo guardado.");
			} catch (Exception e) {
				System.out.println("El vehículo ya existe.");
			}

			// 3. ALQUILER CON TRANSACCIÓN (Datos fijos del PDF)
			Alquiler alq = new Alquiler(
					"34567890C",
					"7788YZA",
					LocalDate.of(2026, 2, 14),
					LocalDate.of(2026, 2, 24));
			new RentCarService().registrarAlquiler(alq);

			// 4. LISTAR VEHÍCULOS DISPONIBLES
			System.out.println("\n--- Vehículos Disponibles ---");
			List<Vehiculo> disponibles = new VehiculoDAOImpl().listarDisponibles();
			for (Vehiculo veh : disponibles) {
				System.out.println(veh);
			}

			// 5. HISTÓRICO DE ALQUILERES (Cliente 34567890C)
			System.out.println("\n--- Historial Cliente 34567890C ---");
			List<Alquiler> historial = new AlquilerDAOImpl().historialPorCliente("34567890C");
			for (Alquiler a : historial) {
				System.out.println(a);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}