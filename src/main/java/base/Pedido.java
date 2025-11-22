package base;

import modelo.Producto;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private List<Producto> detallesPedido = new ArrayList<>();

    public boolean agregarProducto(Producto producto, int cantidad) {
if (cantidad <= 0) {
    return false;
}


        boolean existe = detallesPedido.stream()
                .anyMatch(p -> p.getSku().equals(producto.getSku()));

        if (existe) {
            return false;
        }

        if (!producto.isEsActivo()) {
            return false;
        }

        Producto nuevo = new Producto(
                producto.getNombre(),
                producto.getPrecio(),
                cantidad,
                producto.getSku(),
                producto.getCategoria(),
                producto.isEsActivo(),
                producto.isDescuentoAplicable()
        );

        detallesPedido.add(nuevo);
        return true;
    }

    public boolean validarStock() {
        if (detallesPedido.isEmpty()) {
            return true;
        }
        for (Producto p : detallesPedido) {
            if (p.getCantidad() <= 0) {
                return false;
            }
        }
        return true;
    }

    public List<Producto> getDetallesPedido() {
        return detallesPedido;
    }

    public static double calcularTotalPedido(List<Producto> productos, double descuento) {
        if (productos == null || productos.isEmpty()) {
            throw new IllegalArgumentException("Error: no hay productos en el pedido");
        }

        double subtotal = productos.stream()
                .mapToDouble(p -> p.getPrecio() * p.getCantidad())
                .sum();

        if (subtotal <= 0) {
            throw new IllegalArgumentException("Error: monto inválido");
        }

        return subtotal - (subtotal * (descuento / 100));
    }
}
