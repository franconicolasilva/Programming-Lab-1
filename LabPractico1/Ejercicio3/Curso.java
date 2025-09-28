package Ejercicio3;

import java.time.LocalDate;
import java.util.Objects;

// Representa un curso con código único, nombre y fechas.
 
public class Curso {
    private String codigo;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Curso(String codigo, String nombre, LocalDate fechaInicio, LocalDate fechaFin) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
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

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                '}';
    }

    // Igualdad por código (considerado único).
     
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Curso)) return false;
        Curso curso = (Curso) o;
        return codigo != null && codigo.equalsIgnoreCase(curso.codigo);
    }

    @Override
    public int hashCode() {
        return codigo == null ? 0 : codigo.toLowerCase().hashCode();
    }
}

