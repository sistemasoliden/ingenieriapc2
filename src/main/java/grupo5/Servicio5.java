package grupo5;

import modelo.Producto;
import java.util.List;

public class Servicio5 {

    // Función del integrante 1: Verificar límite de monto
    public static boolean verificarLimite(double total) {
        return total <= 5000;
    }

    // Función del integrante 2: Validar descuento permitido
    public static boolean validarDescuento(double descuento) {
        return descuento >= 0 && descuento <= 50;
    }

    // Función del integrante 3: Calcular IGV (18%)
    public static double calcularIGV(double total) {
        return total + (total * 0.18);
    }

    // Función del integrante 4: Validar cliente
    public static boolean validarCliente(String nombre) {
        return nombre != null && !nombre.trim().isEmpty();
    }

    // Función del integrante 5: Verificar stock
    public static boolean verificarStock(List<Producto> productos) {
        if (productos == null || productos.isEmpty()) {
            return false;
        }
        return productos.stream().allMatch(p -> p.getCantidad() > 0);
    }
}
zz