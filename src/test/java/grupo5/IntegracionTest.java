package grupo5;

import base.Pedido;
import modelo.Producto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IntegracionTest {

    @Test
    @DisplayName("Integración 1: Caso exitoso - Pedido válido")
    void testCasoExitoso() {
        List<Producto> productos = List.of(
            new Producto("Laptop", 2500.0, 1),
            new Producto("Mouse", 100.0, 2)
        );
        double descuento = 10;

        double total = Pedido.calcularTotalPedido(productos, descuento);
        boolean resultado = Servicio5.verificarLimite(total);

        assertEquals(2430.0, total, 0.01);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Integración 2: Error en función base - Lista vacía")
    void testErrorEnFuncionBase() {
        List<Producto> productos = new ArrayList<>();
        double descuento = 10;

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> Pedido.calcularTotalPedido(productos, descuento)
        );
        
        assertEquals("Error: no hay productos en el pedido", exception.getMessage());
    }

    @Test
    @DisplayName("Integración 3: Error en función secundaria - Total excede límite")
    void testErrorEnFuncionSecundaria() {
        List<Producto> productos = List.of(
            new Producto("Laptop Gaming", 4000.0, 2)
        );
        double descuento = 5;

        double total = Pedido.calcularTotalPedido(productos, descuento);
        boolean dentroLimite = Servicio5.verificarLimite(total);

        assertEquals(7600.0, total, 0.01);
        assertFalse(dentroLimite);
    }

    @Test
    @DisplayName("Integración 4: Valores límite - Total exacto 5000")
    void testValoresLimite() {
        List<Producto> productos = List.of(
            new Producto("Monitor", 2500.0, 2)
        );
        double descuento = 0;

        double total = Pedido.calcularTotalPedido(productos, descuento);
        boolean dentroLimite = Servicio5.verificarLimite(total);

        assertEquals(5000.0, total, 0.01);
        assertTrue(dentroLimite);
    }

    @Test
    @DisplayName("Integración 5: Combinación de validaciones")
    void testCombinacionDeValidaciones() {
        List<Producto> productos = List.of(
            new Producto("Teclado", 150.0, 2),
            new Producto("Audífonos", 200.0, 1)
        );
        double descuento = 20;
        String cliente = "María García";

        boolean stockValido = Servicio5.verificarStock(productos);
        boolean descuentoValido = Servicio5.validarDescuento(descuento);
        boolean clienteValido = Servicio5.validarCliente(cliente);
        
        double total = Pedido.calcularTotalPedido(productos, descuento);
        boolean dentroLimite = Servicio5.verificarLimite(total);
        double totalConIGV = Servicio5.calcularIGV(total);

        assertTrue(stockValido);
        assertTrue(descuentoValido);
        assertTrue(clienteValido);
        assertEquals(400.0, total, 0.01);
        assertTrue(dentroLimite);
        assertEquals(472.0, totalConIGV, 0.01);
    }
}
