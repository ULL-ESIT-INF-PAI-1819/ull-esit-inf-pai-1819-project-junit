# JUnit

## Contenido

[TOC]


- Introducción: ¿qué son y para qué sirven las pruebas unitarias?
- ¿Qué es JUnit?
- ¿Cómo está estructurado JUnit? Explicar la versión 5: Platform, Jupiter, Vintage
- Ejemplo simple: cómo se escribe un test
  - Anotaciones
  - Aserciones
  - Salida
  - Etiquetas
- Diferencias con versión anterior. 
- Desarrollo dirigido por pruebas.


## Pruebas unitarias

Las pruebas unitarias son comprobaciones de fragmentos de código claramente delimitados, lo que comúnmente asociamos con funciones o métodos. A pesar de esto, lo normal es que, para asegurarnos de tener un código de calidad, se hagan pruebas unitarias del programa completo. Cada una de estas pruebas consiste en asegurarnos de que un fragmento de código da el resultado que nosotros esperemos que dé. Además, también es normal realizar pruebas de excepciones que podrían saltar y otros comportamientos esperados que no tienen que estar relacionados estrictamente con el resultado.

Estas pruebas no intervienen en el código ni modifican su comportamiento. Sólo sirve como herramienta para que el desarrollador pueda hacer un código libre de errores con mayor facilidad. Sin embargo, las pruebas unitarias no comprueban la integración total del código, para lo que habría que realizar otro tipo de pruebas. Además, el desarrollador tiene que planear con cuidado las pruebas para manejar todos los posibles casos que podrían suceder en la ejecución de una función. 

El correcto uso de las pruebas unitarias nos permitirá detectar errores a tiempo y localizarlo fácilmente, lo que permite un ahorro de tiempo y costos a largo plazo.


## ¿Qué es JUnit?

JUnit es uno de los frameworks más famosos para hacer tests unitarios de programas escritos en Java. Actualmente, los tests escritos con JUnit pueden ejecutarse de muchas formas:

- desde un IDE (IntelliJ, Eclipse, Netbeans, VS Code…),
- con un sistema de tareas (Gradle, Ant, Maven),
- o directamente desde la consola usando JUnit Platform.


## ¿Cómo está estructurado JUnit v5?

La versión más reciente de JUnit divide el framework en tres paquetes distintos:

- **JUnit Jupiter.** API para escribir tests que además contiene un motor que los entiende.
- **JUnit Vintage.** Igual que Jupiter pero para tests escritos con versiones anteriores de JUnit.
- **JUnit Platform.** Es la base de JUnit. Presta soporte para la implementación de motores que ejecuten alguna clase de tests.

## Como ejecutar los tests desde la terminal, sin ningún IDE

El primer paso es [descargar JUnit Platform Console Launcher](https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.4.0/) desde el repositorio en Maven (seleccionar la versión [1.4.0](https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.4.0/junit-platform-console-standalone-1.4.0.jar)) y desde ahí, ejecutar el siguiente comando:

```bash
java -jar junit-platform-console-standalone-1.4.0.jar -cp <bin> --scan-classpath
```

donde `<bin>` es el directorio que contiene las clases de los tests ya compiladas.

**Notas**

- Tal y como se menciona, el comando necesita que los tests ya estén compilados. De modo que en ese directorio (o en algunos de sus subdirectorios, porque el comando funciona de manera recursiva) deben estar los ficheros `.class`.
- Es **muy** importante que la notación sea la adecuada: la clase debe empezar por `Test` o acabar por `Test`. Se puede configurar (con expresiones regulares) para que tenga en cuentra otro tipo de nomenclaturas, pero esas son las que vienen por defecto. Para más información consulte la ayuda del comando (`java -jar junit-platform-console-standalone-1.4.0.jar -h`).

:question: ¿Con el JUnit este no es necesario descargar Jupiter? :question: 

## Cómo se escribe un test

### Aserciones

Las aserciones son un conjunto de métodos que nos permiten hacer comprobaciones, para saber si nos da el resultado que nosotros esperamos. 

En JUnit, generalmente, para cada aserción hay otras dos complementarias del mismo nombre. Una añade un *`String`* como último parámetro y otra un *`Supplier<String>`*. Ambas servirán como mensaje de error en caso de que falle la aserción. Además, como veremos, es muy común ver una aserción que significa la negación de otra.

#### `assertNull`, `assertNotNull`

Comprueba si un objeto es nulo o no lo es.

#### `assertFalse`, `assertTrue`

Comprueba si una expresión es falsa o verdadera. Es posible usarse con el tipo primitivo *boolean*, o bien con *BooleanSupplier*.

#### `assertEquals`, `assertNotEquals`

Comprueba si dos variables tienen el mismo valor o no. En este caso hay una diferencia entre ambos casos. `assertEquals` funciona con todos los tipos primitivos y con cualquier objeto (por lo que es recomendable sobrecargar el método `equals()` para que asegurarnos de que la comparación es la correcta), mientras que `assertNotEquals` sólo funciona con la clase *Object*.

Sin embargo, desde la versión 1.5 de Java se hace el casteo automático de los tipos primitivos a sus respectivas clases. Por lo tanto, aparentemente funcionarán igual aunque internamente no hagan lo mismo.

#### `assertSame`, `assertNotSame`

A diferencia de `assertEquals` ahora sólo funciona con *Object*, porque ahora no comprobamos el valor sino la identificación. En otras palabras, son dos referencias mismo objeto y por lo tanto ocupan el mismo espacio en memoria. En este caso también tenemos su versión negada. 

#### `assertArrayEquals`

Su funcionamiento es muy similar a *assertEquals*, pero en vez de trabajar con un único valor trabajamos con un array de valores. Esos valores pueden ser de cualquier tipo primitivo o de la clase *Object*.

#### `fail`

Hay casos en los que quizás sea más fácil determinar las condiciones de un fallo por nosotros mismos o nos haga falta una opción más avanzada de la que ofrece JUnit. En estos casos podemos usar `fail`. Con este método directamente decimos que algo ha fallado sin atender a condiciones. 

Con está opción podemos proporcionar un mensaje de error (con una *String* o un *`Supplier<String>`*) o la causa subyacente con un *Throwable*.

#### `Otras aserciones`

Además de las mencionadas en el documento hay otras aserciones (e incluso otras sobrecargas de estas aserciones) que recomendamos que vean[^consultar_API]. Entre las aserciones que faltan se encontrarán con algunas para detectar las excepciones y otras para determinar el tiempo que se espera de la ejecución de un código.

:question: por que funciona assertNotEquals con primitivos

### Anotaciones

:question: Qué es una anotación?

#### Tests

Para ejecutar pruebas simples lo indicamos con la anotación *@Test*. Las denominamos simples ya que no es la única forma especificar que es una prueba.

```java
@Test
void testIguales() {
    assertEquals(5, 10);
}
```

:::info
Hey
:::
   
#### Tests parametrizados (usando @ValueSource) :question:

<!-- Revisar -->

También específica que un método es una prueba. En este caso nos permite ejecutar dicha prueba más de una vez con el uso de parámetros distintos. Para proporcionar dichos valores usamos la etiqueta *@ValueSource*. Esta etiqueta solo admite determinados tipos, que deben escribirse de una manera determinada. Se listan a continuación:

```java
short[] shorts
byte[] bytes
int[] ints
long[] longs
float[] floats
double[] doubles
char[] chars
String[] strings
Class[] classes
```

Hay otras etiquetas que permiten añadir otro tipo de datos pero no es el objetivo de este documento. 

```java
@ParameterizedTest
@ValueSource(ints = { 1, 2, 3 })
void testParametrizado(int a) {
    assertEquals(a, 2);
}
```

#### Fábrica de pruebas:question:

#### Ejecución de métodos antes y después de otros tests

JUnit nos proporciona algunas anotaciones para ejecutar instrucciones antes y después de las pruebas. Además, también nos permite decidir si ejecutarlas una vez antes de cada una de las pruebas o una única vez.
    
### Asunciones

### Salida

## Referencias

- [Beneficios de las pruebas unitarias](https://apiumhub.com/es/tech-blog-barcelona/beneficios-de-las-pruebas-unitarias/)
- [Explicación de la arquitectura de JUnit 5: ¿por qué está dividido en tres módulos?](https://blog.codefx.org/design/architecture/junit-5-architecture-jupiter/#JUnit-5)
- [API de JUnit 5 [HTML]](https://junit.org/junit5/docs/5.0.0/api/org/junit/jupiter/api/package-summary.html)
- [Como empezar rápidamente un proyecto de Maven usando la terminal](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)
- [Dependencias en Maven de la API de JUnit Jupiter (artefactos)](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api/5.4.0)

[^consultar_API]: Consulte la [API de JUnit 5](https://junit.org/junit5/docs/5.0.0/api/org/junit/jupiter/api/package-summary.html) para más información.