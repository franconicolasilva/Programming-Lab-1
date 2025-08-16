package Ejercicio5;

import java.time.LocalDate;

public class Producto {
    private String codigo;
    private String nombre;
    private double precio;
    private LocalDate fechaIngreso;

    public Producto(String codigo, String nombre, double precio, LocalDate fechaIngreso) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.fechaIngreso = fechaIngreso;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    @Override
    public String toString() {
        return String.format("Producto{codigo=%s, nombre=%s, precio=%.2f, fechaIngreso=%s}",
                codigo, nombre, precio, fechaIngreso);
    }
}

