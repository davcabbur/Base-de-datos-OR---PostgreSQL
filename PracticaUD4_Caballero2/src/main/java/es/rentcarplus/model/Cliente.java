package es.rentcarplus.model;

public class Cliente {
    private String dni;
    private String nombre;
    private String email;
    private String telefono;
    private Direccion direccion; // Tipo compuesto

    public Cliente(String dni, String nombre, String email, String telefono, Direccion direccion) {
        this.dni = dni;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    @Override
    public String toString() {
        return String.format("Cliente[%s, %s, Dir: %s]", dni, nombre, direccion);
    }
}