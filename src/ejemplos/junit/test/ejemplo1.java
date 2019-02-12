package ejemplos.junit.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ejemplo1 {

    @Test
    void testIguales() {
        assertEquals(5, 10);
    }
    
    @RepeatedTest(10)
    void testIguales2() {
        assertEquals(5, 5);
    }
    
    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3 })
    void testParametrizado(int a) {
        assertEquals(a, 2);
    }

}
