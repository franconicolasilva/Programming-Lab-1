/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio4;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

// Representa un evento con nombre, fecha y responsable.

public class Evento {
    private final String nombre;
    private final LocalDate fecha;
    private final String responsable;

    public Evento(String nombre, LocalDate fecha, String responsable) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.responsable = responsable;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getResponsable() {
        return responsable;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaStr = (fecha == null) ? "N/A" : fecha.format(fmt);
        return String.format("Evento{nombre='%s', fecha=%s, responsable='%s'}",
                nombre, fechaStr, responsable);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Evento)) return false;
        Evento evento = (Evento) o;
        return Objects.equals(nombre, evento.nombre) &&
               Objects.equals(fecha, evento.fecha) &&
               Objects.equals(responsable, evento.responsable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, fecha, responsable);
    }
}

