package es.rentcarplus.model;

public class Vehiculo {
    private String matricula;
    private String marca;
    private String modelo;
    private boolean disponible;

    public Vehiculo(String matricula, String marca, String modelo, boolean disponible) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.disponible = disponible;
    }

    // Getters y Setters
    public String getMatricula() {
        return matricula;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s %s - Disponible: %s", matricula, marca, modelo, disponible ? "SI" : "NO");
    }
}