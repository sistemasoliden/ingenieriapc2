package grupo5;

import modelo.Producto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Servicio5Test {

    // Prueba integrante 1: verificarLimite
    @Test
    @DisplayName("Test 1: Total dentro del límite retorna true")
    void testVerificarLimiteDentroDelLimite() {
        double total = 4500.0;
        boolean resultado = Servicio5.verificarLimite(total);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Test 2: Total excede límite retorna false")
    void testVerificarLimiteExcede() {
        double total = 5200.0;
        boolean resultado = Servicio5.verificarLimite(total);
        assertFalse(resultado);
    }

    // Prueba integrante 2: validarDescuento
    @Test
    @DisplayName("Test 3: Descuento válido retorna true")
    void testValidarDescuentoValido() {
        double descuento = 25.0;
        boolean resultado = Servicio5.validarDescuento(descuento);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Test 4: Descuento inválido retorna false")
    void testValidarDescuentoInvalido() {
        double descuento = 55.0;
        boolean resultado = Servicio5.validarDescuento(descuento);
        assertFalse(resultado);
    }

    // Prueba integrante 3: calcularIGV
    @Test
    @DisplayName("Test 5: Calcular IGV del 18%")
    void testCalcularIGV() {
        double total = 200.0;
        double resultado = Servicio5.calcularIGV(total);
        assertEquals(236.0, resultado, 0.01);
    }

    // Prueba integrante 4: validarCliente
    @Test
    @DisplayName("Test 6: Cliente válido retorna true")
    void testValidarClienteValido() {
        String nombre = "Juan Pérez";
        boolean resultado = Servicio5.validarCliente(nombre);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Test 7: Cliente vacío retorna false")
    void testValidarClienteVacio() {
        String nombre = "";
        boolean resultado = Servicio5.validarCliente(nombre);
        assertFalse(resultado);
    }

    // Prueba integrante 5: verificarStock
    @Test
    @DisplayName("Test 8: Stock válido retorna true")
    void testVerificarStockValido() {
        List<Producto> productos = List.of(
            new Producto("Laptop", 2500.0, 2),
            new Producto("Mouse", 50.0, 5)
        );
        boolean resultado = Servicio5.verificarStock(productos);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Test 9: Producto sin stock retorna false")
    void testVerificarStockSinStock() {
        List<Producto> productos = List.of(
            new Producto("Laptop", 2500.0, 2),
            new Producto("Mouse", 50.0, 0)
        );
        boolean resultado = Servicio5.verificarStock(productos);
        assertFalse(resultado);
    }

    @Test
    @DisplayName("Test 10: Lista vacía retorna false")
    void testVerificarStockListaVacia() {
        List<Producto> productos = new ArrayList<>();
        boolean resultado = Servicio5.verificarStock(productos);
        assertFalse(resultado);
    }
}
