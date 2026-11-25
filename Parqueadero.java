import java.util.Scanner;

public class Parqueadero {

    static final int CUPOS = 20;
    static final int TARIFA_CARRO = 3000;
    static final int TARIFA_MOTO = 1500;
    static final int HORAS_PARA_DESCUENTO = 5;
    static final int PORCENTAJE_DESCUENTO = 20;

    static int totalRecaudado = 0;
    static int vehiculosAtendidos = 0;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String[] placas = new String[CUPOS];
        int[] horaEntrada = new int[CUPOS];
        String[] tipos = new String[CUPOS];

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ", 1, 5);

            switch (opcion) {
                case 1:
                    registrarEntrada(placas, horaEntrada, tipos);
                    break;
                case 2:
                    registrarSalida(placas, horaEntrada, tipos);
                    break;
                case 3:
                    mostrarCupos(placas);
                    break;
                case 4:
                    listarVehiculos(placas, horaEntrada, tipos);
                    break;
                case 5:
                    cerrarDia(placas);
                    break;
            }
        } while (opcion != 5);

        System.out.println("Hasta pronto.");
    }

    static void mostrarMenu() {
        System.out.println();
        System.out.println("  PARQUEADERO  ");
        System.out.println("1. Registrar entrada");
        System.out.println("2. Registrar salida");
        System.out.println("3. Ver cupos ocupados y libres");
        System.out.println("4. Listar vehículos dentro");
        System.out.println("5. Cerrar el día y salir");
        System.out.println();
    }

    static int leerEntero(String mensaje, int min, int max) {
        int valor = 0;
        boolean valido = false;
        while (!valido) {
            System.out.print(mensaje);
            String texto = sc.nextLine().trim();
            try {
                valor = Integer.parseInt(texto);
                if (valor >= min && valor <= max) {
                    valido = true;
                } else {
                    System.out.println("  Error: ingrese un valor entre " + min + " y " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("  Error: debe digitar un número entero.");
            }
        }
        return valor;
    }

    static String leerPlaca(String mensaje) {
        String placa = "";
        boolean valida = false;
        while (!valida) {
            System.out.print(mensaje);
            placa = sc.nextLine().trim().toUpperCase();
            if (placa.isEmpty()) {
                System.out.println("  Error: la placa no puede estar vacía.");
            } else if (!placa.matches("[A-Z]{3}[0-9]{3}") && !placa.matches("[A-Z]{3}[0-9]{2}[A-Z]")) {
                System.out.println("  Error: formato inválido. Carro: 3 letras y 3 números (ABC123). "
                        + "Moto: 3 letras, 2 números y 1 letra (ABC12D).");
            } else {
                valida = true;
            }
        }
        return placa;
    }

    static String detectarTipo(String placa) {
        String tipo;
        if (placa.matches("[A-Z]{3}[0-9]{2}[A-Z]")) {
            tipo = "Moto";
        } else {
            tipo = "Carro";
        }
        return tipo;
    }

    static int buscarPlaca(String[] placas, String placa) {
        for (int i = 0; i < placas.length; i++) {
            if (placas[i] != null && placas[i].equals(placa)) {
                return i;
            }
        }
        return -1;
    }

    static int buscarCupoLibre(String[] placas) {
        for (int i = 0; i < placas.length; i++) {
            if (placas[i] == null) {
                return i;
            }
        }
        return -1;
    }

    static int contarOcupados(String[] placas) {
        int contador = 0;
        for (int i = 0; i < placas.length; i++) {
            if (placas[i] != null) {
                contador++;
            }
        }
        return contador;
    }

    static int calcularTarifa(int horaEntrada, int horaSalida, String tipo) {
        int horas = horaSalida - horaEntrada;
        if (horas < 1) {
            horas = 1;
        }

        int tarifaHora;
        if (tipo.equals("Moto")) {
            tarifaHora = TARIFA_MOTO;
        } else {
            tarifaHora = TARIFA_CARRO;
        }

        int valor = horas * tarifaHora;
        if (horas > HORAS_PARA_DESCUENTO) {
            valor = valor - (valor * PORCENTAJE_DESCUENTO / 100);
        }
        return valor;
    }

    static void registrarEntrada(String[] placas, int[] horaEntrada, String[] tipos) {
        System.out.println("\n  REGISTRAR ENTRADA  ");

        int cupo = buscarCupoLibre(placas);
        if (cupo == -1) {
            System.out.println("El parqueadero está lleno. No hay cupos disponibles.");
            return;
        }

        String placa = leerPlaca("Placa del vehículo: ");
        while (buscarPlaca(placas, placa) != -1) {
            System.out.println("  Error: la placa " + placa + " ya está registrada dentro.");
            placa = leerPlaca("Placa del vehículo: ");
        }

        String tipo = detectarTipo(placa);
        int hora = leerEntero("Hora de entrada (0 a 23): ", 0, 23);

        placas[cupo] = placa;
        horaEntrada[cupo] = hora;
        tipos[cupo] = tipo;

        System.out.println("Entrada registrada: " + tipo + " " + placa
                + " en el cupo " + (cupo + 1) + " a las " + hora + ":00.");
    }

    static void registrarSalida(String[] placas, int[] horaEntrada, String[] tipos) {
        System.out.println("\n  REGISTRAR SALIDA  ");

        if (contarOcupados(placas) == 0) {
            System.out.println("No hay vehículos dentro del parqueadero.");
            return;
        }

        String placa = leerPlaca("Placa del vehículo que sale: ");
        int pos = buscarPlaca(placas, placa);
        while (pos == -1) {
            System.out.println("  Error: la placa " + placa + " no está en el parqueadero.");
            placa = leerPlaca("Placa del vehículo que sale: ");
            pos = buscarPlaca(placas, placa);
        }

        int horaSalida = leerEntero("Hora de salida (0 a 23): ", 0, 23);
        while (horaSalida < horaEntrada[pos]) {
            System.out.println("  Error: la salida no puede ser menor que la entrada ("
                    + horaEntrada[pos] + ":00).");
            horaSalida = leerEntero("Hora de salida (0 a 23): ", 0, 23);
        }

        int valor = calcularTarifa(horaEntrada[pos], horaSalida, tipos[pos]);
        int horas = horaSalida - horaEntrada[pos];
        if (horas < 1) {
            horas = 1;
        }

        System.out.println("Vehículo: " + tipos[pos] + " " + placas[pos]);
        System.out.println("Horas cobradas: " + horas);
        if (horas > HORAS_PARA_DESCUENTO) {
            System.out.println("Se aplicó descuento del " + PORCENTAJE_DESCUENTO + "%.");
        }
        System.out.println("TOTAL A PAGAR: $" + formatear(valor));

        totalRecaudado += valor;
        vehiculosAtendidos++;
        placas[pos] = null;
        tipos[pos] = null;
        horaEntrada[pos] = 0;
    }

    static void mostrarCupos(String[] placas) {
        int ocupados = contarOcupados(placas);
        System.out.println("\n  ESTADO DE CUPOS  ");
        System.out.println("Ocupados: " + ocupados);
        System.out.println("Libres:   " + (CUPOS - ocupados));
        System.out.println("Total:    " + CUPOS);
    }

    static void listarVehiculos(String[] placas, int[] horaEntrada, String[] tipos) {
        System.out.println("\n  VEHÍCULOS DENTRO  ");
        if (contarOcupados(placas) == 0) {
            System.out.println("El parqueadero está vacío.");
            return;
        }
        System.out.println("Cupo | Tipo  | Placa   | Entrada");
        for (int i = 0; i < placas.length; i++) {
            if (placas[i] != null) {
                System.out.printf("%4d | %-5s | %-7s | %02d:00%n",
                        (i + 1), tipos[i], placas[i], horaEntrada[i]);
            }
        }
    }

    static void cerrarDia(String[] placas) {
        System.out.println("\n  CIERRE DEL DÍA  ");
        int dentro = contarOcupados(placas);
        if (dentro > 0) {
            System.out.println("Aviso: quedan " + dentro + " vehículo(s) dentro sin registrar salida.");
        }
        System.out.println("Vehículos atendidos: " + vehiculosAtendidos);
        System.out.println("Total recaudado:     $" + formatear(totalRecaudado));
    }

    static String formatear(int valor) {
        String s = String.valueOf(valor);
        String resultado = "";
        int cuenta = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            resultado = s.charAt(i) + resultado;
            cuenta++;
            if (cuenta % 3 == 0 && i != 0) {
                resultado = "." + resultado;
            }
        }
        return resultado;
    }
}