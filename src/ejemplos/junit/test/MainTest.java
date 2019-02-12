package ejemplos.junit.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
    void testEquality() {
        char a = 'a';
        char b = 'b';
        assertNotEquals(a, b);
        assertEquals(a, a);
    }

    @Test
    void testSame() {
        Integer a = Integer.valueOf(1);
        Integer b = Integer.valueOf(1);
        Integer c = Integer.valueOf(2);
        assertEquals(a, b);
        assertNotEquals(c, b);
        assertNotSame(a, c);
        assertSame(a, a);
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
