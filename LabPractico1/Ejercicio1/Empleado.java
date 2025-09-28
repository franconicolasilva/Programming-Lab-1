package Ejercicio1;

import java.util.Objects;
import java.util.regex.Pattern;

public class Empleado {
    private static final Pattern NOMBRE_PATTERN = Pattern.compile("^[\\p{L} .'-]{2,60}$");
    private final int id;
    private String nombre;
    private double salario;

    public Empleado(int id, String nombre, double salario) {
        validateId(id);
        validateNombre(nombre);
        validateSalario(salario);
        this.id = id;
        this.nombre = nombre.trim();
        this.salario = salario;
    }

    private static void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El id debe ser positivo.");
        }
    }

    public static void validateNombre(String nombre) {
        if (nombre == null) {
            throw new IllegalArgumentException("El nombre no puede ser nulo.");
        }
        String trimmed = nombre.trim();
        if (!NOMBRE_PATTERN.matcher(trimmed).matches()) {
            throw new IllegalArgumentException("Nombre inválido. Use solo letras, espacios y algunos signos (2-60 caracteres).");
        }
    }

    public static void validateSalario(double salario) {
        if (Double.isNaN(salario) || Double.isInfinite(salario) || salario < 0) {
            throw new IllegalArgumentException("Salario inválido. Debe ser número no negativo.");
        }
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        validateNombre(nombre);
        this.nombre = nombre.trim();
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        validateSalario(salario);
        this.salario = salario;
    }

    @Override
    public String toString() {
        return String.format("Empleado{id=%d, nombre='%s', salario=%.2f}", id, nombre, salario);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Empleado)) return false;
        Empleado empleado = (Empleado) o;
        return id == empleado.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
