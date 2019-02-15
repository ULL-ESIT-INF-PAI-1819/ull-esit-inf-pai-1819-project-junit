# JUnit

:question: ejemplos!

## Contenido

[TOC]

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

El primer paso es [descargar JUnit Platform Console Launcher](https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.4.0/) desde el repositorio en Maven (la versión más reciente es la [1.4.0](https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.4.0/junit-platform-console-standalone-1.4.0.jar)) y desde ahí, ejecutar el siguiente comando:

```bash
java -jar junit-platform-console-standalone-1.4.0.jar -cp <bin> --scan-classpath
```

donde `<bin>` es el directorio que contiene las clases de los tests ya compiladas.

:::warning
<center><b>JUnit necesita que las pruebas estén compiladas</b></center> <br/>

De modo que en `bin/` o en otro cualquier directorio (o en algunos de los subdirectorios del proyecto, porque el comando funciona de manera recursiva) deben estar los ficheros `.class` de las respectivas clases.
:::

Es muy importante que la notación sea la adecuada: por norma general es que la clase debe empezar por `Test` o acabar por `Test`. JUnit se puede configurar (con expresiones regulares) para que tenga en cuentra otro tipo de nomenclaturas, pero esas son las que vienen por defecto. Para más información consulte la ayuda del comando (`java -jar junit-platform-console-standalone-1.4.0.jar -h`).

:question: ¿Con el JUnit este no es necesario descargar Jupiter? :question: 

## Cómo se escribe un test

### Aserciones

:question: AssertionFailedError

Las aserciones son un conjunto de métodos que nos permiten hacer comprobaciones, para saber si nos da el resultado que nosotros esperamos. 

En JUnit, generalmente, para cada aserción hay otras dos complementarias del mismo nombre. Una añade un `String` como último parámetro y otra un `Supplier<String>`. Ambas servirán como mensaje de error en caso de que falle la aserción. Además, como veremos, es muy común ver una aserción que significa la negación de otra.

#### `assertNull()`, `assertNotNull()`

Comprueba si un objeto es nulo o no lo es.

#### `assertFalse()`, `assertTrue()`

Comprueba si una expresión es falsa o verdadera. Es posible usarse con el tipo primitivo `boolean`, o bien con `BooleanSupplier`.

#### `assertEquals()`, `assertNotEquals()`

Comprueba si dos variables tienen el mismo valor o no. En este caso hay una diferencia entre ambos casos. `assertEquals()` funciona con todos los tipos primitivos y con cualquier objeto (por lo que es recomendable sobrecargar el método `equals()` para que asegurarnos de que la comparación es la correcta), mientras que `assertNotEquals()` sólo funciona con la clase `Object`.

Sin embargo, desde la versión 1.5 de Java se hace el casteo automático de los tipos primitivos a sus respectivas clases. Por lo tanto, aparentemente funcionarán igual aunque internamente no hagan lo mismo.

#### `assertSame()`, `assertNotSame()`

A diferencia de `assertEquals()` ahora sólo funciona con `Object`, porque ahora no comprobamos el valor sino la identificación. En otras palabras, son dos referencias mismo objeto y por lo tanto ocupan el mismo espacio en memoria. En este caso también tenemos su versión negada. 

#### `assertArrayEquals()`, `assertIterableEquals()`

El funcionamiento de `assertArrayEquals()` es muy similar a `assertEquals`, pero en vez de trabajar con un único valor trabajamos con un array de valores. Es decir, los arrays tienen que contener los mismos elementos en las mismas posiciones. Esos valores pueden ser de cualquier tipo primitivo o de la clase `Object`.

Por otro lado, `assertIterableEquals()` hace lo mismo pero funciona con `Iterable<>`. Esto cuenta con la ventaja de que los dos objetos no tienen que ser necesariamente iguales, pueden ser dos clases diferentes derivadas de `Iterable<>`.

#### `fail()`

Hay casos en los que quizás sea más fácil determinar las condiciones de un fallo por nosotros mismos o nos haga falta una opción más avanzada de la que ofrece JUnit. En estos casos podemos usar `fail()`. Con este método directamente decimos que algo ha fallado sin atender a condiciones. 

Con está opción podemos proporcionar un mensaje de error (con una `String` o un `Supplier<String>`) o la causa subyacente con un `Throwable`.

#### `assertTimeout()`, `assertTimeoutPreemptively()`

Ambas opciones permiten comprobar el tiempo que tarda en ejecutarse un fragmento de código en milisegundo. Para ello requiere que le pasemos el tiempo máximo que estimamos que debería tardar. La diferencia es que `assertTimeout` se ejecutará en el mismo hilo en el que fue llamado, por lo que si sobrapasa el tiempo estimado tendremos que esperar a que termine igualmente. Con `assertTimeoutPreemptively`, al ejecutarse en otro hilo, se aborta su ejecución si sobrepasa dicho tiempo.

#### `assertThrows()`, `assertAll()`

En ocasiones es interesante comprobar si un fragmento de código lanza una determinada excepción. Para ello tenemos `assertThrows()`, que recibe la clase de la excepción que creemos que debería saltar y un `Executable` con el código a comprobar.

También podemos usar `assertAll()` para detectar las excepciones de varios ejecutables. En este caso no concretaremos la excepción, sino que se considerará una prueba satisfactoria si ningún ejecutable lanza excepciones.

#### `assertLinesMatch()`

:question:

#### Otras aserciones

Además de las mencionadas en el documento hay otras aserciones (e incluso otras sobrecargas de estas aserciones) que recomendamos que vean[^consultar_API]. Entre las aserciones que faltan se encontrarán con algunas para detectar las excepciones y otras para determinar el tiempo que se espera de la ejecución de un código.

### Anotaciones

:question: Qué es una anotación? mencionar las experimentales

#### Tests

Para ejecutar pruebas simples lo indicamos con la anotación `@Test`. Las denominamos simples ya que no es la única forma especificar que es una prueba.

```java
@Test
void testIguales() {
    assertEquals(5, 10);
}
```

#### Tests parametrizados 

<!-- Revisar -->

También específica que un método es una prueba. En este caso nos permite ejecutar dicha prueba más de una vez con el uso de parámetros distintos. 

Para proporcionar dichos podemos usar varias etiquetas. La primera de ellas es `@ValueSource`. Esta etiqueta solo admite determinados tipos, que deben escribirse de una manera determinada. Se listan a continuación:


Tipo de valores | Nomenclatura
-- | --
`short[]` | `shorts`
`byte[] `|` bytes`
`int[] `|` ints`
`long[] `|` longs`
`float[] `|` floats`
`double[] `|` doubles`
`char[] `|` chars`
`String[] `|` strings`
`Class[] `|` classes`

También podemos usar otras dos etiquetas que nos permiten proveer al método prueba valores vacíos y nulos, especialmente útiles para comprobar como reacciona nuestro programa ante diferentes entradas: 

- `@NullSource` provee un valor `null` a cualquier parámetro que no sea una primitiva
- `@EmptySource` provee valores vacíos para `String`, `List`, `Set`, `Map`, arrays primitivos (`int[]`, `char[][]`), arrays de objetos (`String[]`), etc.
- `@NullAndEmptySource` es una combinación de las dos anteriores.

:::info
Consulta la API de JUnit en el apartado [*Source of Arguments*](https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-sources) para ver otras maneras de dar valores a los parámetros de los tests.
:::

Un ejemplo simple:

```java
// Este ejemplo ejecuta el tests tres veces: 
// primero con a = 1, luego con a = 2 y finalmente con a = 3.

@ParameterizedTest
@ValueSource(ints = { 1, 2, 3 })
void testParametrizado(int a) {
    assertEquals(a, 2);             // solo acertará una vez
}
```

#### Fábrica de pruebas 

:question: ampliar la descripción 

Hasta ahora hemos visto como escribir tests estáticos. Estos tests están definidos a la hora de compilarlos, y no pueden ser cambiados en tiempo de ejecución.

Sin embargo, podría interesarnos crear un set de tests durante la ejecución de las pruebas. Esto lo hacemos con la anotación `@TestFactory`. 

#### Ejecución de métodos antes y después de otros tests

JUnit nos proporciona algunas anotaciones para ejecutar instrucciones antes y después de las pruebas. Además, también nos permite decidir si ejecutarlas una vez antes de cada una de las pruebas o una única vez. Son las siguientes:

Anotación       | Descripción
------------    | ------------
`@BeforeAll`    | Ejecuta el método una única vez antes de los tests.
`@BeforeEach`   | Ejecuta el método antes de ejecutar cada uno de los tests.
`@AfterAll`     | Ejecuta el método una única vez después de ejecutar todos los tests.
`@AfterEach`    | Ejecuta el método después de ejecutar cada uno de los tests.

#### Desactivar pruebas

En JUnit es posible escribir pruebas pero evitar que sean ejecutadas. Resulta más cómodo que comentarlo o quitar su correspondiente anotación, ya que se podría confundir con otros métodos que nunca han sido tests. De está forma podemos bloquear temporalmente su ejecución de forma más limpia. En el caso de usarlo con clases se desactivan todas las pruebas incluidas en ellas. Lo único que hay que hacer es poner la anotación `@Disabled`. Además, podemos indicar con una cadena de texto el motivo por el que está desactivado.

#### Customizar los nombres de las pruebas

Por defecto, las pruebas se identifican en la salida por el nombre del método, pero quizás sería más claro si se redactará con espacios u otros símbolos que un método no te permite usar. Para ello podemos utilizar `@DisplayName` tanto para métodos como para clases.

#### Pruebas encadenadas

Hasta ahora todas las pruebas estaban al mismo nivel, no podiamos establecer una jerarquía. Realmente si es posible. Dentro de la clase principal podemos crear otra con la anotación `@Nested` para especificar que esa clase también contiene pruebas. Si no lo especificaramos simplemente se ignoraría.

Una práctica muy común es utilizar este encadenamiento para formar frases en relación con el nombre sus métodos y con el nombre de la clase superior. De esta forma se puede leer de forma fluida que es lo que representa cada prueba. 

Sin embargo, no es una regla fija y puede usarse de la forma que se vea conveniente. En el fondo es una forma de agrupar pruebas de forma que se aprecie visualmente en la salida.

#### Etiquetas

Tanto las clases como los métodos de prueba pueden etiquetarse con la anotación `@Tag`. Dicha anotación nos permite decidir qué conjunto de tests queremos ejecutar, por ejemplo.

En general, y de manera simplificada, una etiqueta:

- no puede estar vacía o en blanco,
- no debe tener espacios,
- no debe tener caracteres de control ISO,
- no debe tener ninguno de los siguientes caracteres reservados:

    Símbolo   | Descripción
    --------- | -----------
    `,`       | coma
    `(`       | paréntesis izquierdo 
    `)`       | paréntesis derecho
    `&`       | ampersand
    `|`       | barra vertical
    `!`       | signo de exclamación
    

El motivo por el que estos caracteres están reservados es porque sirven de operadores lógicos para decidir que tests queremos ejecutar.

Este es un ejemplo que hemos extraído y traducido de la API:

Expresión      | Tests seleccionados
-------------- | -------------------
`producto`     | todos los test que tengan la etiqueta `producto`
`catalogo | enviar` | todo los test marcados con `catalogo` **o** `enviar`
`catalogo & enviar` | todo los test marcados con `catalogo` **y** `enviar`
`producto & !puerta-a-puerta` | todos los test marcados con `producto` pero no `puerta-a-puerta`
`(pequeño | rápido) & (producto | enviar)` | todos los tests marcados con `pequeño` o `rápido` que además estén marcados con `producto` o `enviar`.

### Asunciones

Son un conjunto de métodos que permiten abortar tests si no se cumple una condición que se tenía asumido que pasaría. Se creó pensando en aquellos casos en los que las pruebas no tuvieran sentido para un determinado supuesto.

#### `assumeTrue()`, `assumeFalse()`

Comprueba que una expresión booleana es verdadera o falsa, de lo contrario lanzará la excepción `TestAbortedException`, evitando que se ejecuten el resto de tests. También es posible pasarle como parámetro un `String` o `Supplier<String>` como mensaje de la excepción. Además, también permite usar `BooleanSupplier` en vez de un valor `boolean`.

#### `assumingThat()`

Hay casos en los que no queremos que deje de ejecutar esas pruebas sino que necesitamos que haga algo antes de continuar. Para eso usamos `assumingThat()`, que comprueba una condición booleana y ejecuta un `Executable` en el caso de que dicha condición se valide.

### Salida

#### Eclipse

:question: color amarillo

#### Terminal

## Diferencias con versión anterior


## Desarrollo dirigido por pruebas
El TDD *(Test Driven Development)* es una práctica de desarrollo de software que se basa en realizar las pruebas antes del código. Al principio esas pruebas fallarán (al no haber código que comprobar), por lo que el siguiente paso es escribir el código que haga que las pruebas se ejecuten satisfactoriamente. El último paso consiste en refactorizar el código de forma que quede limpio y eficiente.

En síntesis:

1. Escribir las pruebas
2. Verificar que las pruebas fallan (para saber que lo que estamos probando no ha sido implementado antes)
3. Escribir el código que hace que las pruebas se ejecuten correctamente
4. Verificar que ahora las pruebas se ejecutan correctamente
5. Refactorizar

Con esta práctica nos obligamos a hacer un correcto diseño del programa antes de empezar a escribir, algo que no es recomendado aunque muchos lo hayamos hecho más de una vez. 

Por otro lado, también nos permite centrarnos en el problema principal. Es decir, cuando vamos a crear un método intervienen cosas como el manejo de excepciones, pero esto no es su tarea principal. Siguiendo los pasos en el desarrollo de estas pruebas las excepciones se manejarían en el apartado de refactorización.

Aunque no es obligatorio, es común utilizarlo con pruebas unitarias. De está forma se fomenta que todo el código se encuentre bajo el control de las pruebas.

## Referencias

- [Beneficios de las pruebas unitarias](https://apiumhub.com/es/tech-blog-barcelona/beneficios-de-las-pruebas-unitarias/)
- [Explicación de la arquitectura de JUnit 5: ¿por qué está dividido en tres módulos?](https://blog.codefx.org/design/architecture/junit-5-architecture-jupiter/#JUnit-5)
- [API de JUnit 5 [HTML]](https://junit.org/junit5/docs/5.0.0/api/org/junit/jupiter/api/package-summary.html)
- [Cómo empezar rápidamente un proyecto de Maven usando la terminal](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)
- [Dependencias en Maven de la API de JUnit Jupiter (artefactos)](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api/5.4.0)
- [Desarrollo dirigido por pruebas](https://es.wikipedia.org/wiki/Desarrollo_guiado_por_pruebas#Ventajas)

[^consultar_API]: Consulte la [API de JUnit 5](https://junit.org/junit5/docs/5.0.0/api/org/junit/jupiter/api/package-summary.html) para más información.

:question: podemos hacer el repositorio publico para ponerlo en el documento? assertLinesMatch es necesario? y las otras anotaciones? diferencias con version anterior es necesario?