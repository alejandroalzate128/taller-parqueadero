# Sistema de Control de Parqueadero

Proyecto final de Lógica de Programación (SD1002) - Institución Universitaria Pascual Bravo.

**Integrantes:** Carlos Quiceno, Alejandro Alzate Naranjo
**Docente:** Alejandro Lopez Oliveros

## Descripción

Programa de consola en Java para controlar un parqueadero de 20 cupos: registra entradas y salidas, calcula el valor a pagar y muestra el recaudo del día.

## Requisitos

- JDK 17 o superior

## Ejecución

```
javac Parqueadero.java
java Parqueadero
```

En Windows (cmd), si las tildes se ven mal, ejecutar antes `chcp 65001`.

## Menú

| Opción | Acción |
|--------|--------|
| 1 | Registrar entrada |
| 2 | Registrar salida y cobrar |
| 3 | Ver cupos ocupados y libres |
| 4 | Listar vehículos dentro |
| 5 | Cerrar el día y salir |

## Reglas

- **Placa carro:** 3 letras + 3 números (ABC123)
- **Placa moto:** 3 letras + 2 números + 1 letra (ABC12D)
- El tipo de vehículo se detecta por el formato de la placa.
- **Tarifa:** carro $3.000/hora, moto $1.500/hora, por hora o fracción (mínimo 1 hora).
- **Descuento:** 20% si la estancia supera las 5 horas.
- Horas válidas: 0 a 23. La salida no puede ser menor que la entrada.
- No se permiten placas repetidas ni entradas con el parqueadero lleno.

## Estructura del código

- Arreglos paralelos: `placas[]`, `horaEntrada[]`, `tipos[]` (cupo libre = `null`).
- Funciones principales: `leerEntero`, `leerPlaca`, `detectarTipo`, `buscarPlaca`, `buscarCupoLibre`, `contarOcupados`, `calcularTarifa`, `registrarEntrada`, `registrarSalida`, `mostrarCupos`, `listarVehiculos`, `cerrarDia`, `formatear`.

## Ejemplo rápido

1. Opción 1: placa `ABC123`, hora `8`.
2. Opción 2: placa `ABC123`, hora `10`.
3. Resultado: total a pagar $6.000.
