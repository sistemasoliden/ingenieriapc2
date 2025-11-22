package grupo5;

import base.Pedido;
import modelo.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoTest {

    private Pedido pedido;

    @BeforeEach
    void setup() {
        pedido = new Pedido();
    }

    @Test
    void testAgregarProductoCantidadNoValida() {
        Producto p = new Producto("Laptop", 1200.0, 10, "SKU1", "Tecnologia", true, false);
        boolean r = pedido.agregarProducto(p, 0);
        assertFalse(r);
    }

    @Test
    void testAgregarProductoDuplicadoSku() {
        Producto p = new Producto("Mouse", 20.0, 5, "SKU2", "Accesorios", true, false);
        boolean r1 = pedido.agregarProducto(p, 2);
        boolean r2 = pedido.agregarProducto(p, 3);
        assertTrue(r1);
        assertFalse(r2);
        assertEquals(1, pedido.getDetallesPedido().size());
    }

    @Test
    void testAgregarProductoCorrecto() {
        Producto p = new Producto("Teclado", 50.0, 5, "SKU3", "Accesorios", true, false);
        boolean r = pedido.agregarProducto(p, 4);
        assertTrue(r);
        assertEquals(1, pedido.getDetallesPedido().size());
    }

    @Test
    void testAgregarProductoRespetaAtributos() {
        Producto p = new Producto("Monitor", 800.0, 10, "SKU4", "Tecnologia", true, true);
        pedido.agregarProducto(p, 3);
        Producto agregado = pedido.getDetallesPedido().get(0);
        assertEquals("Monitor", agregado.getNombre());
        assertEquals(800.0, agregado.getPrecio());
        assertEquals("SKU4", agregado.getSku());
        assertEquals("Tecnologia", agregado.getCategoria());
        assertTrue(agregado.isEsActivo());
        assertTrue(agregado.isDescuentoAplicable());
        assertEquals(3, agregado.getCantidad());
    }

    @Test
    void testAgregarProductoInactivo() {
        Producto p = new Producto("Tablet", 600.0, 5, "SKU5", "Tecnologia", false, false);
        boolean r = pedido.agregarProducto(p, 2);
        assertFalse(r);
    }

    @Test
    void testValidarStockListaVacia() {
        assertTrue(pedido.validarStock());
    }

    @Test
    void testValidarStockTodosValidos() {
        Producto p1 = new Producto("Prod1", 10.0, 5, "S1", "A", true, false);
        Producto p2 = new Producto("Prod2", 20.0, 3, "S2", "B", true, false);
        pedido.agregarProducto(p1, 5);
        pedido.agregarProducto(p2, 3);
        assertTrue(pedido.validarStock());
    }

    @Test
    void testValidarStockUnoCero() {
        Producto p = new Producto("Prod3", 10.0, 0, "S3", "A", true, false);

        // Se agrega manualmente porque agregarProducto NO permite cantidad 0
        pedido.getDetallesPedido().add(p);

        assertFalse(pedido.validarStock());
    }

    @Test
    void testValidarStockCantidadNegativa() {
        assertThrows(IllegalArgumentException.class,
                () -> new Producto("Prod4", 10.0, -2, "S4", "A", true, false));
    }

    @Test
    void testValidarStockValoresLimite() {
        Producto p1 = new Producto("Prod5", 10.0, 1, "S5", "A", true, false);
        Producto p2 = new Producto("Prod6", 10.0, 999, "S6", "A", true, false);
        pedido.agregarProducto(p1, 1);
        pedido.agregarProducto(p2, 999);
        assertTrue(pedido.validarStock());
    }
}
