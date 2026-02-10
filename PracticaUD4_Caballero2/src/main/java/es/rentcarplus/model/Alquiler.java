package es.rentcarplus.model;

import java.time.LocalDate;

public class Alquiler {
    private long id;
    private String dniCliente; // Clave foránea a Clientes
    private String matricula; // Clave foránea a Vehiculos
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    // Constructor completo (para cuando leemos de la BD con ID)
    public Alquiler(long id, String dniCliente, String matricula, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.dniCliente = dniCliente;
        this.matricula = matricula;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Constructor sin ID (para insertar nuevos, la BD genera el ID)
    public Alquiler(String dniCliente, String matricula, LocalDate fechaInicio, LocalDate fechaFin) {
        this.dniCliente = dniCliente;
        this.matricula = matricula;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Getters
    public long getId() {
        return id;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public String getMatricula() {
        return matricula;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    @Override
    public String toString() {
        return String.format("Alquiler #%d: Cliente %s alquiló %s (%s al %s)",
                id, dniCliente, matricula, fechaInicio, fechaFin);
    }
}