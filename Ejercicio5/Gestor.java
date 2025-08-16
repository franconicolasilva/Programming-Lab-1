package Ejercicio5;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Gestor {

    private final List<Producto> inventario;
    private final Map<String, Integer> controlStock;
    private final Scanner teclado;
    private final DateTimeFormatter formatoFecha;

    public Gestor() {
        this.controlStock = new HashMap<>();
        this.inventario = new ArrayList<>();
        this.teclado = new Scanner(System.in);
        this.formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    public void run() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENU ---");
            System.out.println("1) Agregar producto (con stock inicial)");
            System.out.println("2) Actualizar stock");
            System.out.println("3) Eliminar producto");
            System.out.println("4) Mostrar productos (por precio y por fecha)");
            System.out.println("5) Mostrar inventario completo (con stock)");
            System.out.println("0) Salir");
            System.out.print("Elija una opción: ");
            String opcion = teclado.nextLine().trim();
            switch (opcion) {
                case "1" -> agregarProducto();
                case "2" -> actualizarStock();
                case "3" -> eliminarProducto();
                case "4" -> mostrarOrdenados();
                case "5" -> mostrarInventario();
                case "0" -> {
                    System.out.println("Saliendo...");
                    salir = true;
                }
                default -> System.out.println("Opción inválida. Ingrese 0-5.");
            }
        }
    }

    private void agregarProducto() {
        System.out.println("\n--- AGREGAR PRODUCTO ---");
        String codigo = leerCadenaNoVacia("Ingrese el código del producto: ").toUpperCase();
        if (existeProducto(codigo)) {
            System.out.println("Ya existe un producto con ese código. Use actualizar o elimine primero si desea reemplazar.");
            return;
        }
        String nombre = leerCadenaNoVacia("Ingrese el nombre del producto: ");
        double precio = leerDouble("Ingrese el precio (ej: 123.45): ");
        LocalDate fecha = leerFecha("Ingrese la fecha de ingreso (dd/MM/yyyy): ");
        int stock = leerIntNoNegativo("Ingrese stock inicial (entero, >= 0): ");

        Producto producto = new Producto(codigo, nombre, precio, fecha);
        inventario.add(producto);
        controlStock.put(codigo, stock);
        System.out.println("Producto agregado correctamente.");
    }

    private void actualizarStock() {
        System.out.println("\n--- ACTUALIZAR STOCK ---");
        String codigo = leerCadenaNoVacia("Ingrese el código del producto a actualizar: ").toUpperCase();
        if (!existeProducto(codigo)) {
            System.out.println("No existe un producto con ese código.");
            return;
        }
        Integer actual = controlStock.getOrDefault(codigo, 0);
        System.out.printf("Stock actual: %d%n", actual);
        int nuevoStock = leerIntNoNegativo("Ingrese el nuevo stock (entero, >= 0): ");
        controlStock.put(codigo, nuevoStock);
        System.out.println("Stock actualizado correctamente.");
    }

    private void eliminarProducto() {
        System.out.println("\n--- ELIMINAR PRODUCTO ---");
        String codigo = leerCadenaNoVacia("Ingrese el código del producto a eliminar: ").toUpperCase();
        boolean eliminado = false;

        // Eliminar de la lista usando iterator para evitar ConcurrentModificationException
        Iterator<Producto> it = inventario.iterator();
        while (it.hasNext()) {
            Producto p = it.next();
            if (codigo.equalsIgnoreCase(p.getCodigo())) {
                it.remove();
                eliminado = true;
                break; // si códigos son únicos, podemos salir
            }
        }

        // Eliminar del HashMap (si existe)
        if (controlStock.containsKey(codigo)) {
            controlStock.remove(codigo);
        }

        if (eliminado) {
            System.out.println("Producto eliminado del inventario y del control de stock.");
        } else {
            System.out.println("No se encontró el producto con el código especificado.");
        }
    }

    private void mostrarOrdenados() {
        if (inventario.isEmpty()) {
            System.out.println("El inventario está vacío.");
            return;
        }

        // Por precio: mayor -> menor
        List<Producto> porPrecio = new ArrayList<>(inventario);
        porPrecio.sort(Comparator.comparingDouble(Producto::getPrecio).reversed()
                // Por si queremos un desempate estable: por nombre asc
                .thenComparing(Producto::getNombre, String.CASE_INSENSITIVE_ORDER));
        System.out.println("\n--- Productos ordenados por PRECIO (mayor -> menor) ---");
        for (Producto p : porPrecio) {
            System.out.printf("%s | Precio: %.2f | Fecha: %s | Stock: %d%n",
                    p.getCodigo(), p.getPrecio(), p.getFechaIngreso(), controlStock.getOrDefault(p.getCodigo(), 0));
        }

        // Por fecha: más reciente -> más antigua
        List<Producto> porFecha = new ArrayList<>(inventario);
        porFecha.sort(Comparator.comparing(Producto::getFechaIngreso).reversed()
                // desempate por nombre
                .thenComparing(Producto::getNombre, String.CASE_INSENSITIVE_ORDER));
        System.out.println("\n--- Productos ordenados por FECHA (más reciente -> más antigua) ---");
        for (Producto p : porFecha) {
            System.out.printf("%s | Fecha: %s | Precio: %.2f | Stock: %d%n",
                    p.getCodigo(), p.getFechaIngreso(), p.getPrecio(), controlStock.getOrDefault(p.getCodigo(), 0));
        }
    }

    private void mostrarInventario() {
        System.out.println("\n--- INVENTARIO COMPLETO ---");
        if (inventario.isEmpty()) {
            System.out.println("No hay productos.");
            return;
        }
        for (Producto p : inventario) {
            System.out.printf("%s | %s | Precio: %.2f | Fecha: %s | Stock: %d%n",
                    p.getCodigo(), p.getNombre(), p.getPrecio(), p.getFechaIngreso(), controlStock.getOrDefault(p.getCodigo(), 0));
        }
    }

    // Helpers y validaciones

    private boolean existeProducto(String codigo) {
        return inventario.stream().anyMatch(p -> p.getCodigo().equalsIgnoreCase(codigo));
    }

    private String leerCadenaNoVacia(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linea = teclado.nextLine();
            if (linea == null) linea = "";
            linea = linea.trim();
            if (!linea.isEmpty()) return linea;
            System.out.println("Entrada vacía. Intente nuevamente.");
        }
    }

    private double leerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linea = teclado.nextLine().trim();
            try {
                double val = Double.parseDouble(linea);
                if (val < 0) {
                    System.out.println("El valor no puede ser negativo. Intente de nuevo.");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Formato inválido. Use números con punto decimal si es necesario (ej: 123.45).");
            }
        }
    }

    private int leerIntNoNegativo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linea = teclado.nextLine().trim();
            try {
                int val = Integer.parseInt(linea);
                if (val < 0) {
                    System.out.println("El número no puede ser negativo. Intente nuevamente.");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número entero (ej: 0, 1, 2...).");
            }
        }
    }

    private LocalDate leerFecha(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linea = teclado.nextLine().trim();
            try {
                return LocalDate.parse(linea, formatoFecha);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Use dd/MM/yyyy (ej: 31/12/2024).");
            }
        }
    }
}

