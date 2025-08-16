package Ejercicio4;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

// Para administrar eventos: agregar, listar y ordenar.
 
public class MixRepasoEj4 {

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        List<Evento> listado = new ArrayList<>();
        Scanner teclado = new Scanner(System.in);

        System.out.println("=== Gestor de Eventos ===");

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            String opcion = teclado.nextLine().trim();
            switch (opcion) {
                case "1" -> agregarEvento(listado, teclado);
                case "2" -> mostrarEventos(listado);
                case "3" -> mostrarOrdenadosPorProximidad(listado);
                case "4" -> mostrarOrdenadosPorNombre(listado);
                case "5" -> {
                    salir = true;
                    System.out.println("Saliendo. ¡Hasta luego!");
                }
                default -> System.out.println("Opción inválida. Ingrese un número entre 1 y 5.");
            }
        }

   
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("Seleccione una opción:");
        System.out.println("1) Agregar evento");
        System.out.println("2) Mostrar todos los eventos");
        System.out.println("3) Mostrar eventos ordenados por fecha (más cercana primero) y por nombre");
        System.out.println("4) Mostrar eventos ordenados por nombre (alfabético)");
        System.out.println("5) Salir");
        System.out.print("Opción: ");
    }

    private static void agregarEvento(List<Evento> listado, Scanner teclado) {
        String nombre = solicitarCadenaNoVacia("Ingrese el nombre del evento:", teclado);
        LocalDate fecha = solicitarFecha("Ingrese la fecha del evento (dd/MM/yyyy):", teclado);
        String responsable = solicitarCadenaNoVacia("Ingrese la persona responsable:", teclado);

        Evento nuevo = new Evento(nombre, fecha, responsable);
        listado.add(nuevo);
        System.out.println("Evento agregado correctamente.");
    }

    private static String solicitarCadenaNoVacia(String prompt, Scanner teclado) {
        while (true) {
            System.out.println(prompt);
            String entrada = teclado.nextLine().trim();
            if (entrada.isEmpty()) {
                System.out.println("La entrada no puede estar vacía. Intente de nuevo.");
                continue;
            }
            if (entrada.length() > 200) {
                System.out.println("Entrada demasiado larga (máx. 200 caracteres). Intente de nuevo.");
                continue;
            }
            return entrada;
        }
    }

    private static LocalDate solicitarFecha(String prompt, Scanner teclado) {
        while (true) {
            System.out.println(prompt);
            String fechaStr = teclado.nextLine().trim();
            try {
                LocalDate fecha = LocalDate.parse(fechaStr, INPUT_FORMAT);
                return fecha;
            } catch (DateTimeParseException ex) {
                System.out.println("Formato de fecha incorrecto. Use dd/MM/yyyy. Ejemplo: 31/12/2025");
            } catch (Exception ex) {
                System.out.println("Error al leer la fecha. Intente nuevamente.");
            }
        }
    }

    private static void mostrarEventos(List<Evento> listado) {
        if (listado.isEmpty()) {
            System.out.println("No hay eventos cargados.");
            return;
        }
        System.out.println("Lista de eventos:");
        listado.forEach(e -> System.out.println(e.toString()));
    }

    private static void mostrarOrdenadosPorProximidad(List<Evento> listado) {
        if (listado.isEmpty()) {
            System.out.println("No hay eventos cargados.");
            return;
        }

        Comparator<Evento> porProximidadLuegoNombre = Comparator
                .comparingLong((Evento e) -> Math.abs(ChronoUnit.DAYS.between(LocalDate.now(), e.getFecha())))
                .thenComparing(e -> e.getNombre().toLowerCase());

        listado.stream()
                .sorted(porProximidadLuegoNombre)
                .forEach(e -> System.out.println(e.toString()));
    }

    private static void mostrarOrdenadosPorNombre(List<Evento> listado) {
        if (listado.isEmpty()) {
            System.out.println("No hay eventos cargados.");
            return;
        }

        Comparator<Evento> porNombre = Comparator.comparing(e -> e.getNombre().toLowerCase());

        listado.stream()
                .sorted(porNombre)
                .forEach(e -> System.out.println(e.toString()));
    }
}

