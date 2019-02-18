package ejemplos.junit.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Vector;

import org.junit.jupiter.api.Test;

class AsercionesTest {
	
	@Test
	void comprobacionesConMensajesDeError() {
		assertTrue(true);
		assertTrue(false, "Esto está mal");
		assertTrue(false, () -> "Esto está mal, pero con supplier.");
	}
	
	@Test
	void comprobacionesBooleanas() {
		int a = 5;
		assertTrue(a == 5);
		assertFalse(a < 2);
		assertFalse(() -> (a < 2));
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
		assertEquals(5, 5);
		assertNotEquals("prueba", "prueba diferente");
		assertNotEquals(5, 7);
		
		String a = "prueba";
		String d = a;
		assertSame(a, d);
		assertNotSame(a, new String("prueba"));
		
		int[] b = {1,2,3};
		int[] c = {1,2,3};
		assertArrayEquals(b,c);
		
		ArrayList<String> primerIterable = new ArrayList<>();
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
			System.out.println(a[5]);
		});
		
		assertAll(() -> {
			int[] a = { 1, 2, 3, 4 };
			System.out.println(a[2]);
		}, () -> {
			System.out.print("No se me ocurre de que hacer las pruebas");
		});
	}

	@Test
	void comprobacionesDeTiempoDeEjecucion() {
		assertTimeout(Duration.ofMillis(500), () -> {
			int total = 0;
			for(int i = 0; i < 10000; i++) {
				total += i;
				//System.out.println(i);
			}
		}); 
		assertTimeoutPreemptively(Duration.ofMillis(500), () -> {
			int total = 0;
			for(int i = 0; i < 2147483647; i++) {
				total += i;
				//System.out.println(i);
			}
		});
	}

	@Test
	void fallosIncondicionales() {
		fail("Falla porque lo digo yo.");
	}
	
	@Test
	void fallosIncondicionalesConThrowable() {
		int a = -1;
		if(a < 0) { 
			fail(new IllegalArgumentException());
		}
	}
	
	@Test 
	void asuncionesBooleanas() {
		assumeTrue(true);
		assumeFalse(true);
		
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
