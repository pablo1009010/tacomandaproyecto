package org.tacomanda.vista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.tacomanda.dao.ComandaDAO;
import org.tacomanda.dao.DetalleComandaDAO;
import org.tacomanda.dao.EmpleadoDAO;
import org.tacomanda.dao.MesaDAO;
import org.tacomanda.dao.PedidoDAO;
import org.tacomanda.dao.PlatilloDAO;
import org.tacomanda.dao.RetroalimentacionDAO;
import org.tacomanda.modelo.Admin;
import org.tacomanda.modelo.Cajero;
import org.tacomanda.modelo.Comanda;
import org.tacomanda.modelo.DetalleComanda;
import org.tacomanda.modelo.Empleado;
import org.tacomanda.modelo.Mesa;
import org.tacomanda.modelo.Mesero;
import org.tacomanda.modelo.Pedido;
import org.tacomanda.modelo.Platillo;
import org.tacomanda.modelo.Retroalimentacion;

public class Menu {
    static EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    static MesaDAO mesaDAO = new MesaDAO();
    static PlatilloDAO platilloDAO = new PlatilloDAO();
    static ComandaDAO comandaDAO = new ComandaDAO();
    static DetalleComandaDAO detalleDAO = new DetalleComandaDAO();
    static PedidoDAO pedidoDAO = new PedidoDAO();
    static RetroalimentacionDAO retroDAO = new RetroalimentacionDAO();
    static BufferedReader leer;

    public Menu() {
        super();
    }

    // =====================================================================
    // MENU PRINCIPAL
    // =====================================================================
    public static void menu() throws IOException {
        int opcion;

        if (!empleadoDAO.existenEmpleados()) {
            crearAdminInicial();
        }

        do {
            System.out.println("\n===== TACOMANDA =====");
            System.out.println("1.- Iniciar sesión");
            System.out.println("2.- Cocina");
            System.out.println("3.- Salir");
            System.out.println("=========================");
            System.out.print("Elige tu opción: ");

            opcion = leerEntero();
            switch (opcion) {
                case 1: iniciarSesion(); break;
                case 2: menuCocina(); break;
                case 3: System.out.println("Cerrando TacoManda..."); break;
                default: System.out.println("Opción inválida");
            }
        } while (opcion != 3);
    }

    private static void crearAdminInicial() throws IOException {
        System.out.println("\n===== Primer uso: crea la cuenta de administrador =====");
        System.out.println("Aún no hay empleados registrados en la base de datos.");
        System.out.print("Nombre del administrador: ");
        String nombre = leer.readLine();
        System.out.print("Teléfono: ");
        String telefono = leer.readLine();
        System.out.print("Contraseña (se guardará encriptada con BCrypt): ");
        String contrasena = leer.readLine();

        Admin admin = new Admin(nombre, telefono, contrasena);
        if (empleadoDAO.registrarEmpleado(admin)) {
            System.out.println("Cuenta de administrador creada. Ya puedes iniciar sesión.");
        }
    }

    // =====================================================================
    // LOGIN Y DESPACHO POR ROL (POLIMORFISMO)
    // =====================================================================
    private static void iniciarSesion() throws IOException {
        System.out.println("\n=== Iniciar sesión ===");
        System.out.print("Número de control: ");
        int noControl = leerEntero();
        System.out.print("Contraseña: ");
        String contrasena = leer.readLine();

        Empleado empleado = empleadoDAO.login(noControl, contrasena);

        if (empleado == null) {
            System.out.println("Número de control o contraseña incorrectos.");
            return;
        }

        System.out.println("\n¡Bienvenido/a " + empleado.getNombre() + "!");
        empleado.mostrarPermisos(); // método abstracto -> polimorfismo

        if (empleado instanceof Mesero) {
            menuMesero((Mesero) empleado);
        } else if (empleado instanceof Cajero) {
            menuCajero((Cajero) empleado);
        } else if (empleado instanceof Admin) {
            menuAdmin((Admin) empleado);
        }
    }

    // =====================================================================
    // MENU MESERO
    // =====================================================================
    private static void menuMesero(Mesero mesero) throws IOException {
        int opcion;
        do {
            System.out.println("\n===== MENÚ MESERO =====");
            System.out.println("1.- Ver mesas");
            System.out.println("2.- Tomar comanda");
            System.out.println("3.- Ver comandas abiertas");
            System.out.println("4.- Dejar retroalimentación de la app");
            System.out.println("5.- Regresar");
            System.out.print("Elige tu opción: ");

            opcion = leerEntero();
            switch (opcion) {
                case 1: mostrarMesas(); break;
                case 2: tomarComanda(mesero); break;
                case 3: mostrarComandasPorEstado("abierta"); break;
                case 4: registrarRetroalimentacion(mesero); break;
                case 5: System.out.println("Cerrando sesión..."); break;
                default: System.out.println("Opción inválida");
            }
        } while (opcion != 5);
    }

    private static void tomarComanda(Mesero mesero) throws IOException {
        mesero.tomarComanda(); // demuestra el uso de la interfaz Atendible

        ArrayList<Mesa> libres = mesaDAO.extraerMesasLibres();
        if (libres.isEmpty()) {
            System.out.println("No hay mesas libres en este momento.");
            return;
        }

        System.out.println("--- Mesas disponibles ---");
        for (Mesa mesa : libres) {
            System.out.println("Id: " + mesa.getId() + " | " + mesa);
        }

        System.out.print("Id de la mesa a atender: ");
        int idMesa = leerEntero();

        Comanda comanda = new Comanda(idMesa, mesero.getNoControl());
        int idComanda = comandaDAO.crearComanda(comanda);

        if (idComanda == -1) {
            System.out.println("No se pudo crear la comanda.");
            return;
        }

        mesaDAO.actualizarEstado(idMesa, "ocupada");
        System.out.println("Comanda #" + idComanda + " creada. Agrega los platillos:");

        agregarPlatillosAComanda(idComanda);

        System.out.println("Comanda registrada con éxito.");
    }

    private static void agregarPlatillosAComanda(int idComanda) throws IOException {
        ArrayList<Platillo> platillos = platilloDAO.extraerPlatillos();
        String otro;

        do {
            System.out.println("--- Menú disponible ---");
            for (Platillo p : platillos) {
                System.out.println("Id: " + p.getIdPlatillo() + " | " + p.getNombre() + " - $" + p.getPrecio());
            }

            System.out.print("Id del platillo: ");
            int idPlatillo = leerEntero();
            Platillo elegido = platilloDAO.buscarPorId(idPlatillo);

            if (elegido == null) {
                System.out.println("Platillo no encontrado.");
            } else {
                System.out.print("Cantidad: ");
                int cantidad = leerEntero();
                System.out.print("Nota (opcional): ");
                String nota = leer.readLine();

                double subtotal = elegido.getPrecio() * cantidad;
                DetalleComanda detalle = new DetalleComanda(idComanda, idPlatillo, cantidad, nota, subtotal);
                detalleDAO.agregarDetalle(detalle);
                System.out.println("Platillo agregado. Subtotal: $" + detalle.getSubtotal());
            }

            System.out.print("¿Agregar otro platillo? (s/n): ");
            otro = leer.readLine();
        } while (otro != null && otro.equalsIgnoreCase("s"));
    }

    // =====================================================================
    // MENU CAJERO
    // =====================================================================
    private static void menuCajero(Cajero cajero) throws IOException {
        int opcion;
        do {
            System.out.println("\n===== MENÚ CAJERO =====");
            System.out.println("1.- Ver comandas listas para cobrar");
            System.out.println("2.- Cobrar comanda");
            System.out.println("3.- Dejar retroalimentación de la app");
            System.out.println("4.- Regresar");
            System.out.print("Elige tu opción: ");

            opcion = leerEntero();
            switch (opcion) {
                case 1: mostrarComandasPorEstado("lista"); break;
                case 2: cobrarComanda(cajero); break;
                case 3: registrarRetroalimentacion(cajero); break;
                case 4: System.out.println("Cerrando sesión..."); break;
                default: System.out.println("Opción inválida");
            }
        } while (opcion != 4);
    }

    private static void cobrarComanda(Cajero cajero) throws IOException {
        cajero.cobrarPedido(); // demuestra el uso de la interfaz Cobrador

        ArrayList<Comanda> listas = comandaDAO.extraerComandasPorEstado("lista");
        if (listas.isEmpty()) {
            System.out.println("No hay comandas listas para cobrar.");
            return;
        }

        for (Comanda c : listas) {
            System.out.println(c);
        }

        System.out.print("Id de la comanda a cobrar: ");
        int idComanda = leerEntero();

        ArrayList<DetalleComanda> detalles = detalleDAO.extraerPorComanda(idComanda);
        if (detalles.isEmpty()) {
            System.out.println("Esa comanda no tiene platillos registrados.");
            return;
        }

        double total = detalleDAO.calcularTotalComanda(idComanda);
        System.out.println("--- Detalle de la comanda ---");
        for (DetalleComanda d : detalles) {
            System.out.println(d);
        }
        System.out.println("TOTAL A PAGAR: $" + total);

        System.out.print("Tipo de pago (efectivo/tarjeta/transferencia): ");
        String tipoPago = leer.readLine();
        System.out.print("Tipo de pedido (en_mesa/para_llevar): ");
        String tipoPedido = leer.readLine();

        double cambio = 0.0;
        if (tipoPago != null && tipoPago.equalsIgnoreCase("efectivo")) {
            System.out.print("Monto con el que paga el cliente: ");
            double monto = leerDecimal();
            cambio = monto - total;
            if (cambio < 0) {
                System.out.println("El monto es insuficiente. Se registrará el cambio como $0.00");
                cambio = 0.0;
            }
        }

        Pedido pedido = new Pedido(idComanda, cambio, tipoPago, tipoPedido);
        pedidoDAO.registrarPedido(pedido);

        comandaDAO.actualizarEstado(idComanda, "cerrada");

        System.out.println("Cobro finalizado. Cambio a entregar: $" + pedido.getCambio());
    }

    // =====================================================================
    // MENU ADMIN
    // =====================================================================
    private static void menuAdmin(Admin admin) throws IOException {
        int opcion;
        do {
            System.out.println("\n===== MENÚ ADMIN =====");
            System.out.println("1.- Registrar empleado");
            System.out.println("2.- Ver empleados");
            System.out.println("3.- Actualizar empleado");
            System.out.println("4.- Dar de baja empleado");
            System.out.println("5.- Registrar platillo");
            System.out.println("6.- Ver platillos");
            System.out.println("7.- Actualizar platillo");
            System.out.println("8.- Dar de baja platillo");
            System.out.println("9.- Ver todas las comandas");
            System.out.println("10.- Ver mesas");
            System.out.println("11.- Ir a Cocina");
            System.out.println("12.- Ver retroalimentación de la app");
            System.out.println("13.- Dejar retroalimentación de la app");
            System.out.println("14.- Regresar");
            System.out.print("Elige tu opción: ");

            opcion = leerEntero();
            switch (opcion) {
                case 1: registrarEmpleado(); break;
                case 2: mostrarEmpleados(); break;
                case 3: actualizarEmpleado(); break;
                case 4: bajaEmpleado(); break;
                case 5: registrarPlatillo(); break;
                case 6: mostrarPlatillos(); break;
                case 7: actualizarPlatillo(); break;
                case 8: bajaPlatillo(); break;
                case 9: mostrarTodasLasComandas(); break;
                case 10: mostrarMesas(); break;
                case 11: menuCocina(); break;
                case 12: mostrarRetroalimentacion(); break;
                case 13: registrarRetroalimentacion(admin); break;
                case 14: System.out.println("Cerrando sesión..."); break;
                default: System.out.println("Opción inválida");
            }
        } while (opcion != 14);

        admin.administrarSistema(); // demuestra el uso de la interfaz Administrador
    }

    private static void registrarEmpleado() throws IOException {
        System.out.println("=== Registrar nuevo empleado ===");
        System.out.print("Nombre: ");
        String nombre = leer.readLine();
        System.out.print("Teléfono: ");
        String telefono = leer.readLine();
        System.out.print("Contraseña: ");
        String contrasena = leer.readLine();
        System.out.print("Tipo (mesero/cajero/admin): ");
        String tipo = leer.readLine();

        Empleado empleado;
        if (tipo == null) {
            System.out.println("Tipo de empleado inválido.");
            return;
        }

        switch (tipo.toLowerCase().trim()) {
            case "mesero":
                empleado = new Mesero(nombre, telefono, contrasena);
                break;
            case "cajero":
                empleado = new Cajero(nombre, telefono, contrasena);
                break;
            case "admin":
                empleado = new Admin(nombre, telefono, contrasena);
                break;
            default:
                System.out.println("Tipo de empleado inválido.");
                return;
        }

        empleadoDAO.registrarEmpleado(empleado);
    }

    private static void mostrarEmpleados() {
        ArrayList<Empleado> empleados = empleadoDAO.extraerEmpleados();
        System.out.println("========== LISTA DE EMPLEADOS ==========");
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
        } else {
            for (Empleado e : empleados) {
                System.out.println(e);
            }
        }
    }

    private static void actualizarEmpleado() throws IOException {
        System.out.println("=== Actualizar empleado ===");
        System.out.print("Número de control: ");
        int noControl = leerEntero();

        ArrayList<Empleado> empleados = empleadoDAO.extraerEmpleados();
        Empleado encontrado = null;
        for (Empleado e : empleados) {
            if (e.getNoControl() == noControl) {
                encontrado = e;
                break;
            }
        }

        if (encontrado == null) {
            System.out.println("No se encontró ningún empleado con ese número de control.");
            return;
        }

        encontrado.setNoControl(noControl);
        System.out.print("Nuevo nombre: ");
        encontrado.setNombre(leer.readLine());
        System.out.print("Nuevo teléfono: ");
        encontrado.setTelefono(leer.readLine());
        System.out.print("Nueva contraseña: ");
        encontrado.setContrasena(leer.readLine());

        empleadoDAO.actualizarEmpleado(encontrado);
    }

    private static void bajaEmpleado() throws IOException {
        System.out.println("=== Dar de baja empleado ===");
        System.out.print("Número de control: ");
        int noControl = leerEntero();
        empleadoDAO.darBajaEmpleado(noControl);
    }

    private static void registrarPlatillo() throws IOException {
        System.out.println("=== Registrar nuevo platillo ===");
        System.out.print("Nombre: ");
        String nombre = leer.readLine();
        System.out.print("Categoría (tacos/extras/bebidas/postres): ");
        String categoria = leer.readLine();
        System.out.print("Descripción: ");
        String descripcion = leer.readLine();
        System.out.print("Precio: ");
        double precio = leerDecimal();

        Platillo platillo = new Platillo(nombre, categoria, descripcion, precio, true);
        platilloDAO.registrarPlatillo(platillo);
    }

    private static void mostrarPlatillos() {
        ArrayList<Platillo> platillos = platilloDAO.extraerPlatillos();
        System.out.println("========== MENÚ TACOMANDA ==========");
        if (platillos.isEmpty()) {
            System.out.println("No hay platillos registrados.");
        } else {
            for (Platillo p : platillos) {
                System.out.println(p);
            }
        }
    }

    private static void actualizarPlatillo() throws IOException {
        System.out.println("=== Actualizar platillo ===");
        System.out.print("Id del platillo: ");
        int idPlatillo = leerEntero();

        Platillo platillo = platilloDAO.buscarPorId(idPlatillo);
        if (platillo == null) {
            System.out.println("No se encontró ningún platillo con ese id.");
            return;
        }

        System.out.print("Nuevo nombre: ");
        platillo.setNombre(leer.readLine());
        System.out.print("Nueva categoría: ");
        platillo.setCategoria(leer.readLine());
        System.out.print("Nueva descripción: ");
        platillo.setDescripcion(leer.readLine());
        System.out.print("Nuevo precio: ");
        platillo.setPrecio(leerDecimal());

        platilloDAO.actualizarPlatillo(platillo);
    }

    private static void bajaPlatillo() throws IOException {
        System.out.println("=== Dar de baja platillo ===");
        System.out.print("Id del platillo: ");
        int idPlatillo = leerEntero();
        platilloDAO.darBajaPlatillo(idPlatillo);
    }

    private static void mostrarTodasLasComandas() {
        ArrayList<Comanda> comandas = comandaDAO.extraerTodas();
        System.out.println("========== TODAS LAS COMANDAS ==========");
        if (comandas.isEmpty()) {
            System.out.println("No hay comandas registradas.");
        } else {
            for (Comanda c : comandas) {
                System.out.println(c);
            }
        }
    }

    // =====================================================================
    // SECCIÓN RETROALIMENTACIÓN DE LA APP
    // =====================================================================
    private static void registrarRetroalimentacion(Empleado empleado) throws IOException {
        System.out.println("=== Retroalimentación de la app ===");
        System.out.print("Calificación (1 a 5): ");
        int calificacion = leerEntero();
        System.out.print("Comentario (opcional): ");
        String comentario = leer.readLine();

        Retroalimentacion retro = new Retroalimentacion(empleado.getNoControl(), calificacion, comentario);
        retroDAO.registrar(retro);
    }

    private static void mostrarRetroalimentacion() {
        ArrayList<Retroalimentacion> lista = retroDAO.extraerTodas();
        System.out.println("========== RETROALIMENTACIÓN DE LA APP ==========");
        if (lista.isEmpty()) {
            System.out.println("Aún no hay retroalimentación registrada.");
        } else {
            for (Retroalimentacion r : lista) {
                System.out.println(r);
            }
            System.out.println("Calificación promedio: " + String.format("%.1f", retroDAO.calcularPromedio()) + "/5");
        }
    }

    // =====================================================================
    // SECCIÓN COCINA (solo consulta y avance de estado de las comandas)
    // =====================================================================
    private static void menuCocina() throws IOException {
        int opcion;
        do {
            System.out.println("\n===== COCINA =====");
            System.out.println("1.- Ver comandas abiertas");
            System.out.println("2.- Ver comandas en preparación");
            System.out.println("3.- Marcar comanda como 'en preparación'");
            System.out.println("4.- Marcar comanda como 'lista'");
            System.out.println("5.- Regresar");
            System.out.print("Elige tu opción: ");

            opcion = leerEntero();
            switch (opcion) {
                case 1: mostrarComandasDetalladas("abierta"); break;
                case 2: mostrarComandasDetalladas("en_preparacion"); break;
                case 3: cambiarEstadoComanda("en_preparacion"); break;
                case 4: cambiarEstadoComanda("lista"); break;
                case 5: System.out.println("Saliendo de Cocina..."); break;
                default: System.out.println("Opción inválida");
            }
        } while (opcion != 5);
    }

    /**
     * Vista pensada para Cocina: además del folio y estado de la comanda,
     * muestra la mesa y cada platillo pedido (con cantidad y nota), que es
     * justo lo que necesita cocina para preparar el pedido.
     */
    private static void mostrarComandasDetalladas(String estado) {
        ArrayList<Comanda> comandas = comandaDAO.extraerComandasPorEstado(estado);
        System.out.println("========== COMANDAS: " + estado.toUpperCase() + " ==========");

        if (comandas.isEmpty()) {
            System.out.println("No hay comandas en este estado.");
            return;
        }

        for (Comanda c : comandas) {
            Mesa mesa = mesaDAO.buscarPorId(c.getMesaId());
            System.out.println("-------------------------------------");
            System.out.println("Comanda #" + c.getIdComanda()
                    + " | Mesa: " + (mesa != null ? mesa.getNumero() : c.getMesaId())
                    + " | Estado: " + c.getEstado());

            ArrayList<DetalleComanda> detalles = detalleDAO.extraerPorComanda(c.getIdComanda());
            if (detalles.isEmpty()) {
                System.out.println("  (sin platillos registrados)");
            } else {
                for (DetalleComanda d : detalles) {
                    Platillo p = platilloDAO.buscarPorId(d.getIdPlatillo());
                    String nombrePlatillo = (p != null) ? p.getNombre() : "Platillo #" + d.getIdPlatillo();
                    System.out.println("  - " + nombrePlatillo + " x" + d.getCantidad()
                            + (d.getNota().isBlank() ? "" : " | Nota: " + d.getNota()));
                }
            }
        }
        System.out.println("-------------------------------------");
    }

    private static void cambiarEstadoComanda(String nuevoEstado) throws IOException {
        System.out.print("Id de la comanda: ");
        int idComanda = leerEntero();
        comandaDAO.actualizarEstado(idComanda, nuevoEstado);
    }

    // =====================================================================
    // UTILERÍAS COMPARTIDAS
    // =====================================================================
    private static void mostrarMesas() {
        ArrayList<Mesa> mesas = mesaDAO.extraerMesas();
        System.out.println("========== ESTADO DE MESAS ==========");
        for (Mesa mesa : mesas) {
            System.out.println("Id: " + mesa.getId() + " | " + mesa);
        }
    }

    private static void mostrarComandasPorEstado(String estado) {
        ArrayList<Comanda> comandas = comandaDAO.extraerComandasPorEstado(estado);
        System.out.println("========== COMANDAS: " + estado.toUpperCase() + " ==========");
        if (comandas.isEmpty()) {
            System.out.println("No hay comandas en este estado.");
        } else {
            for (Comanda c : comandas) {
                System.out.println(c);
            }
        }
    }

    private static int leerEntero() throws IOException {
        try {
            return Integer.parseInt(leer.readLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingresa un número válido.");
            return -1;
        }
    }

    private static double leerDecimal() throws IOException {
        try {
            return Double.parseDouble(leer.readLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingresa un número válido.");
            return -1;
        }
    }

    static {
        leer = new BufferedReader(new InputStreamReader(System.in));
    }
}
