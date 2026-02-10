package es.rentcarplus.model;

public class Direccion {
    private TipoVia via;
    private String nombre;
    private int numero;
    private String cp;

    public Direccion(TipoVia via, String nombre, int numero, String cp) {
        this.via = via;
        this.nombre = nombre;
        this.numero = numero;
        this.cp = cp;
    }

    // Getters
    public TipoVia getVia() {
        return via;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumero() {
        return numero;
    }

    public String getCp() {
        return cp;
    }

    @Override
    public String toString() {
        return via + " " + nombre + ", " + numero + " (" + cp + ")";
    }
}