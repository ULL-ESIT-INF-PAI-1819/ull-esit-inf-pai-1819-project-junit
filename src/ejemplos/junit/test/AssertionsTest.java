package ejemplos.junit.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.junit.jupiter.api.Test;

class AssertionsTest {

	@Test
	void estandarComprobaciones() {
		assertTrue(true);
		assertTrue(false,"Esto ta' mal");
		assertTrue(false,() -> "Esto ta' muuuy mal");
	}
	
	@Test
	void comprobacionesBooleanas() {
		int a = 5;
		assertTrue(a == 5);
		assertFalse(a < 2);
	}

	@Test
	void comprobacionesDeNulos() {
		Integer a = null;
		assertNull(a);
		a = 8;
		assertNotNull(a);
	}

	@Test
	void comprobacionesDeIgualdades() {
		assertEquals("prueba", "prueba");
		assertNotEquals("prueba", "prueba2");
		
		String a = "prueba";
		assertSame(a, a);
		assertNotSame(a, new String("prueba"));
		
		int[] b = {1,2,3};
		int[] c = {1,2,3};
		assertArrayEquals(b,c);
		
		List<String> primerIterable = new ArrayList<>();
		primerIterable.add("abc");
		primerIterable.add("segundaPalabra");
		primerIterable.add("última comprobación");
	    Vector<String> segundoIterable = new Vector<>();
	    segundoIterable.add("abc");
	    segundoIterable.add("segundaPalabra");
	    segundoIterable.add("última comprobación");
		assertIterableEquals(primerIterable, segundoIterable);
	}

	@Test
	void comprobacionesDeExcepciones() {
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			int[] a = { 1, 2, 3, 4 };
			System.out.println(a[5] + 2);
		});
		
		assertAll(() -> {
			String a = "Esto no debería dar error";
			String b = a.concat(" pero podría darlo.");
		}, () -> {
			System.out.print("No se me ocurre de que hacer las pruebas");
		});
	}

	@Test
	void comprobacionesDeTiempoDeEjecución() {
		assertTimeout(Duration.ofMillis(500), () -> {
			for(int i = 0; i < 10000; i++) {
				System.out.println("esto tarda bastante, pero suific");
			}
		});
		assertTimeoutPreemptively(Duration.ofMillis(500), () -> {
			for(int i = 0; i < 2147483647; i++) {
				System.out.println(i);
			}
		});
	}

	@Test
	void fallosIncondicionales() {
		fail("Falla porque lo digo yo.");
	}
	
	@Test 
	void asuncionesBooleanas() {
		assumeTrue(true);
		assumeFalse(false);
		assertNull(null);
	}
	
	@Test 
	void asuncionesConEjecuciones() {
		final int a = 5;
		assumingThat(a < 0 , () -> {
			assertEquals(a,5);
		});
		assumingThat(a > 0 , () -> {
			assertEquals(a,5);
		});
	}
}
