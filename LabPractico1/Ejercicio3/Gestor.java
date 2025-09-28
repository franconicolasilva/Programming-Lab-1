package Ejercicio3;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

// Gestor de cursos: alta, baja, listado y cálculo de duraciones.
 
public class Gestor {

    private final HashSet<Curso> listado = new HashSet<>();
    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Gestor() {
    }

    // Muestra el menú principal y maneja las opciones con switch.
     
    public void menu() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- GESTOR DE CURSOS ---");
            System.out.println("1. Agregar curso");
            System.out.println("2. Eliminar curso");
            System.out.println("3. Mostrar todos los cursos");
            System.out.println("4. Calcular duración de todos los cursos");
            System.out.println("0. Salir");
            System.out.print("Elija una opción: ");

            String opcionStr = scanner.nextLine().trim();
            if (opcionStr.isBlank()) {
                System.out.println("Opción no válida. Ingrese un número del menú.");
                continue;
            }

            int opcion;
            try {
                opcion = Integer.parseInt(opcionStr);
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida. Ingrese un número.");
                continue;
            }

            switch (opcion) {
                case 1 -> agregarCurso();
                case 2 -> eliminarCurso();
                case 3 -> mostrar();
                case 4 -> calcularDuracion();
                case 0 -> {
                    System.out.println("Saliendo del gestor. ¡Hasta luego!");
                    salir = true;
                }
                default -> System.out.println("Opción no reconocida. Intente de nuevo.");
            }
        }
    }

    //Agrega cursos repetidamente hasta que el usuario decida no agregar más.
    // Se validan entradas y código único.
     
    public void agregarCurso() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- AGREGAR NUEVO CURSO ---");

            String codigo = leerStringNoVacio("Ingrese el código del curso: ");
            // Verificar unicidad:
            if (existeCodigo(codigo)) {
                System.out.println("Error: ya existe un curso con ese código. Intente con otro código.");
                // preguntar si quiere reintentar con otro código o volver al menú
                if (!leerSiNo("Desea intentar agregar otro curso? (SI/NO): ")) {
                    return;
                } else {
                    continue;
                }
            }

            String nombre = leerStringNoVacio("Ingrese el nombre del curso: ");

            LocalDate fechaInicio = leerFechaValida("Ingrese la fecha de inicio (dd/MM/yyyy): ");
            LocalDate fechaFin;
            while (true) {
                fechaFin = leerFechaValida("Ingrese la fecha de finalización (dd/MM/yyyy): ");
                if (fechaFin.isBefore(fechaInicio) || fechaFin.isEqual(fechaInicio) ) {
                    System.out.println("La fecha de finalización debe ser posterior a la fecha de inicio. Intente nuevamente.");
                } else {
                    break;
                }
            }

            Curso nuevo = new Curso(codigo.trim(), nombre.trim(), fechaInicio, fechaFin);
            listado.add(nuevo);
            System.out.println("Curso agregado correctamente: " + nuevo);

            // Preguntar si agregar otro
            continuar = leerSiNo("Desea agregar otro curso? (SI/NO): ");
        }
    }

    //Elimina un curso si existe; si no existe muestra mensaje.
    // Uso correcto de Iterator para evitar ConcurrentModificationException.
     
    public void eliminarCurso() {
        if (listado.isEmpty()) {
            System.out.println("No hay cursos para eliminar.");
            return;
        }

        System.out.println("\n--- ELIMINAR CURSO ---");
        String codigo = leerStringNoVacio("Ingrese el código del curso a eliminar: ");
        boolean eliminado = false;

        Iterator<Curso> iter = listado.iterator();
        while (iter.hasNext()) {
            Curso c = iter.next();
            if (c.getCodigo().equalsIgnoreCase(codigo.trim())) {
                iter.remove();
                eliminado = true;
                System.out.println("Curso eliminado correctamente: " + c);
                break;
            }
        }

        if (!eliminado) {
            System.out.println("Curso con código '" + codigo + "' no encontrado.");
        }
    }

    // Muestra todos los cursos disponibles.
     
    public void mostrar() {
        System.out.println("\n--- LISTADO DE CURSOS ---");
        if (listado.isEmpty()) {
            System.out.println("No hay cursos registrados.");
            return;
        }
        for (Curso c : listado) {
            System.out.println(c);
        }
    }

    // Para calcular la duración entre fechaInicio y fechaFin para cada curso.
    // Se muestra en Period (años/meses/días) y también en días/horas/minutos usando ChronoUnit.
    
    public void calcularDuracion() {
        System.out.println("\n--- DURACIÓN DE CURSOS ---");
        if (listado.isEmpty()) {
            System.out.println("No hay cursos registrados.");
            return;
        }

        for (Curso c : listado) {
            LocalDate inicio = c.getFechaInicio();
            LocalDate fin = c.getFechaFin();

            // Representación (años, meses, días)
            Period p = Period.between(inicio, fin);

            // Duración en días, horas y minutos (usando atStartOfDay para tener unidades mayores)
            long dias = ChronoUnit.DAYS.between(inicio, fin);
            long horas = ChronoUnit.HOURS.between(inicio.atStartOfDay(), fin.atStartOfDay());
            long minutos = ChronoUnit.MINUTES.between(inicio.atStartOfDay(), fin.atStartOfDay());

            System.out.printf("Curso: %s (%s)%n", c.getNombre(), c.getCodigo());
            System.out.printf("  Period: %d años, %d meses, %d días.%n", p.getYears(), p.getMonths(), p.getDays());
            System.out.printf("  Equivalente: %d días, %d horas, %d minutos.%n", dias, horas, minutos);
        }
    }

    /* ------------------ Métodos auxiliares de validación y lectura ------------------ */

    private String leerStringNoVacio(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            if (line != null && !line.trim().isEmpty()) {
                return line;
            }
            System.out.println("Entrada no válida: no puede ser vacía.");
        }
    }

    private LocalDate leerFechaValida(String prompt) {
        while (true) {
            System.out.print(prompt);
            String entrada = scanner.nextLine().trim();
            try {
                LocalDate fecha = LocalDate.parse(entrada, formato);
                return fecha;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Use dd/MM/yyyy. Ejemplo: 31/12/2025");
            }
        }
    }

    private boolean leerSiNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String r = scanner.nextLine().trim();
            if (r.equalsIgnoreCase("SI") || r.equalsIgnoreCase("S") || r.equalsIgnoreCase("YES") || r.equalsIgnoreCase("Y")) {
                return true;
            }
            if (r.equalsIgnoreCase("NO") || r.equalsIgnoreCase("N")) {
                return false;
            }
            System.out.println("Respuesta no entendida. Ingrese SI o NO.");
        }
    }

    private boolean existeCodigo(String codigo) {
        if (codigo == null) return false;
        for (Curso c : listado) {
            if (c.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return true;
            }
        }
        return false;
    }
}

