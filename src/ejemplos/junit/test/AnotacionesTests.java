package ejemplos.junit.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AnotacionesTests {

    private String modifica(String entrada) {
        return entrada + 1;
    }
    
    @Test
    public void testDeEjemplo() {
        assertTrue(true);
    }

    /* Test parametrizados */

    @ParameterizedTest
    @ValueSource(strings = { "Esto", "es", "un", "test", "parametrizado" })
    public void testParametrizado(String a) {
        assertEquals(a, "es");
    }

    /* Fabrica de tests */
    
    @TestFactory
    public Stream<DynamicTest> testsDinamicos() {
        List<String>            ejemploEntrada = new ArrayList<>(
                Arrays.asList("Hola", "que", "tal"));
        List<String>            ejemploSalida  = new ArrayList<>(
                Arrays.asList("Hola1", "que1", "tal1"));
        Collection<DynamicTest> setDeTests     = new ArrayList<>();
        
        for (int i = 0; i < ejemploEntrada.size(); i++) {
            String      entrada      = ejemploEntrada.get(i);
            String      salida       = ejemploSalida.get(i);
            Executable  aserto       = () -> assertEquals(salida, modifica(entrada));
            String      nombreTest   = "Añade 1 al final de la palabra '" + entrada + "'";
            DynamicTest testDinamico = DynamicTest.dynamicTest(nombreTest, aserto);
            setDeTests.add(testDinamico);
        }
        
        return setDeTests.stream();
    }

    @Disabled
    @Test
    void testDesactivado() {
        assertEquals(2 + 2, 5);
    }

    @DisplayName("Este test")
    @Nested
    class otroTipoDeTests {

        @DisplayName("comprueba algo")
        @Test
        @Tag("A")
        @Tag("B")
        public void otroTestMas() {
            assertTrue(!false);
        }

        @DisplayName("comprueba otra cosa")
        @Test
        @Tag("A")
        public void otroTestMas1() {
            assertTrue(!false);
        }

        @DisplayName("comprueba más cosas todavía")
        @Test
        @Tag("B")
        public void otroTestMas2() {
            assertTrue(!false);
        }

    }

}
