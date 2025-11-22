package base;

import modelo.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    private Pedido pedido;
    private Producto laptop;
    private Producto teclado;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
        // Usamos el constructor con 4 parámetros (sku, nombre, precio, cantidad)
        laptop = new Producto("SKU1", "Laptop", 1500.0, 10);
        teclado = new Producto("SKU2", "Teclado", 100.0, 20);
    }

    // ================================
    // PRUEBAS PARA agregarProducto(...)
    // ================================

    @Test
    void agregarProducto_conDatosValidos_debeAgregarYRetornarTrue() {
        boolean resultado = pedido.agregarProducto(laptop, 5);

        assertTrue(resultado);
        List<Producto> detalles = pedido.getDetallesPedido();
        assertEquals(1, detalles.size());
        Producto agregado = detalles.get(0);

        assertEquals("Laptop", agregado.getNombre());
        assertEquals(5, agregado.getCantidad());
        assertEquals(laptop.getPrecio(), agregado.getPrecio());
    }

    @Test
    void agregarProducto_conCantidadCero_debeRetornarFalseYNoAgregar() {
        boolean resultado = pedido.agregarProducto(laptop, 0);

        assertFalse(resultado);
        assertTrue(pedido.getDetallesPedido().isEmpty());
    }

    @Test
    void agregarProducto_conCantidadNegativa_debeRetornarFalseYNoAgregar() {
        boolean resultado = pedido.agregarProducto(laptop, -3);

        assertFalse(resultado);
        assertTrue(pedido.getDetallesPedido().isEmpty());
    }

    @Test
    void agregarProducto_productoDuplicadoPorNombre_debeRetornarFalseYNoDuplicar() {
        // Primer agregado correcto
        boolean primero = pedido.agregarProducto(laptop, 3);
        assertTrue(primero);
        assertEquals(1, pedido.getDetallesPedido().size());

        // Segundo producto con MISMO nombre pero distinto sku
        Producto otroLaptop = new Producto("SKU-OTRO", "Laptop", 2000.0, 5);
        boolean segundo = pedido.agregarProducto(otroLaptop, 2);

        assertFalse(segundo);
        // Debe seguir habiendo solo 1 producto en la lista
        assertEquals(1, pedido.getDetallesPedido().size());
    }

    @Test
    void agregarProducto_dosProductosConNombreDistinto_debeAgregarAmbos() {
        boolean r1 = pedido.agregarProducto(laptop, 3);
        boolean r2 = pedido.agregarProducto(teclado, 2);

        assertTrue(r1);
        assertTrue(r2);
        assertEquals(2, pedido.getDetallesPedido().size());
    }

    // ================================
    // PRUEBAS PARA validarStock(...)
    // ================================

    @Test
    void validarStock_conListaVacia_debeRetornarTrue() {
        // No hay productos en el pedido
        boolean valido = pedido.validarStock(null);

        assertTrue(valido);
    }

    @Test
    void validarStock_todosProductosConCantidadPositiva_debeRetornarTrue() {
        pedido.agregarProducto(laptop, 5);
        pedido.agregarProducto(teclado, 3);

        boolean valido = pedido.validarStock(null);

        assertTrue(valido);
    }

    @Test
    void validarStock_unProductoConCantidadCero_debeRetornarFalse() {
        // Agregamos directamente un producto con cantidad 0 a la lista interna
        pedido.getDetallesPedido().add(
                new Producto("SKU3", "Mouse", 50.0, 0)
        );

        boolean valido = pedido.validarStock(null);

        assertFalse(valido);
    }

    @Test
    void validarStock_mezclaDeCantidadPositivaYCero_debeRetornarFalse() {
        // Uno válido
        pedido.getDetallesPedido().add(
                new Producto("SKU3", "Mouse", 50.0, 5)
        );
        // Uno con cantidad 0
        pedido.getDetallesPedido().add(
                new Producto("SKU4", "Pad Mouse", 20.0, 0)
        );

        boolean valido = pedido.validarStock(null);

        assertFalse(valido);
    }

    @Test
    void validarStock_despuesDeCambiarCantidadA0ConSetter_debeRetornarFalse() {
        // Agregamos por el método normal
        pedido.agregarProducto(laptop, 5);
        assertTrue(pedido.validarStock(null)); // primero es válido

        // Luego, por alguna razón, la cantidad se vuelve 0
        pedido.getDetallesPedido().get(0).setCantidad(0);

        boolean valido = pedido.validarStock(null);

        assertFalse(valido);
    }
}
