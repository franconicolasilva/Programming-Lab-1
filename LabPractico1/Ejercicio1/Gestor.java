// Source code is decompiled from a .class file using FernFlower decompiler.
package Ejercicio1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Gestor {
    private final List<Empleado> lista = new ArrayList<>();

    public boolean agregarEmpleado(Empleado e) {
        if (e == null) throw new IllegalArgumentException("Empleado no puede ser nulo.");
        if (buscarPorId(e.getId()).isPresent()) {
            return false; // ya existe
        }
        lista.add(e);
        return true;
    }

    public boolean eliminarPorId(int id) {
        Optional<Empleado> opt = buscarPorId(id);
        if (opt.isPresent()) {
            lista.remove(opt.get());
            return true;
        }
        return false;
    }

    public Optional<Empleado> buscarPorId(int id) {
        return lista.stream().filter(emp -> emp.getId() == id).findFirst();
    }

    public List<Empleado> listarTodos() {
        return new ArrayList<>(lista);
    }

    public void actualizarSalario(int id, double nuevoSalario) {
        Optional<Empleado> opt = buscarPorId(id);
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("Empleado no encontrado con id: " + id);
        }
        Empleado e = opt.get();
        Empleado.validateSalario(nuevoSalario);
        e.setSalario(nuevoSalario);
    }
}

