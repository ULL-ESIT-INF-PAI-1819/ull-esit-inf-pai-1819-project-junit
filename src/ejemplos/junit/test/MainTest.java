package ejemplos.junit.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.sun.jdi.connect.Connector.Argument;

import java.util.function.*;

class MainTest {

    @BeforeAll
    static void testAntesDeTodos() {
        System.out.println("Antes de todos los tests");
    }

    @BeforeEach
    void testAntesDeCadaUno() {
        System.out.println("Antes del test");
    }

    @DisplayName("Test: diferentes")
    @Test
    void testIguales() {
        assertNotEquals(5, 10);
    }

    @DisplayName("Test: iguales")
    @Test
    void testIguales2() {
        assertEquals(5, 5, "ejemplo");
    }

    @DisplayName("Test parametrizado")
    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3 })
    void testParametrizado(int a) {
        assertEquals(a, 2);
    }
    
    @Test
    void testNotEqual() {
        char a = 'a';
        char b = 'b';
        assertNotEquals(a, b);
        assertEquals(a, a);
    }

    static Arguments define() {
        Integer a = new Integer(1);
        Float   b = new Float(1.0);
        return Arguments.of(a, b);
    }

    @ParameterizedTest
    @MethodSource("define")
    void testNoSonElMismoObjeto(Integer a, Float b) {
        assertNotSame(a, b);
    }

    @ParameterizedTest
    @MethodSource("define") 
    void testTienenElMismoValor(Integer a, Float b) {
        assertEquals(a, b);
    }

    @AfterEach
    void alFinalDeCadaTest() {
        System.out.println("Después de test");
    }

    @AfterAll
    static void alFinalDeTodosLosTests() {
        System.out.println("Final de todos los tests");
    }

    // TODO: mirar @TestFactory y @TestTemplate
}
