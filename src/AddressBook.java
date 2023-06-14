import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBook {
    private Map<String, String> contactos;
    private String archivoContactos;
    //Constructor que recibe el nombre del archivo de contactos
    public AddressBook(String archivoContactos) {
        this.archivoContactos = archivoContactos;
        this.contactos = new HashMap<>();
    }
    //Método para cargar los contactos desde el archivo
    public void load() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivoContactos));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(",");
                if (partes.length == 2) {
                    String numeroTelefono = partes[0];
                    String name = partes[1];
                    contactos.put(numeroTelefono, name);
                }
            }
            reader.close();
            System.out.println("Contactos cargados exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar los contactos" + e.getMessage());
        }
    }
    //Método para guardar los contactos en el archivo
    public void save() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivoContactos));
            for (Map.Entry<String, String> entry : contactos.entrySet()) {
                String numeroTelefono = entry.getKey();
                String nombre = entry.getValue();
                writer.write(numeroTelefono + "," + nombre);
                writer.newLine();
            }
            writer.close();
            System.out.println("+------------------------------------+");
            System.out.println("|  Contactos guardados exitosamente  |");
            System.out.println("+------------------------------------+");
        } catch (IOException e) {
            System.out.println("Error al guardar los contactos: " + e.getMessage());
        }
    }
    //Método para mostrar la lista de contactos
    public void list() {
        if (contactos.isEmpty()) {
            System.out.println("+------------------------------------+");
            System.out.println("|  Aún no hay contactos en la lista  |");
            System.out.println("+------------------------------------+");
        } else {
            System.out.println("+------------------------------------+");
            System.out.println("|        Lista de contactos:         |");
            for (Map.Entry<String, String> entry : contactos.entrySet()) {
                String numeroTelefono = entry.getKey();
                String nombre = entry.getValue();
                System.out.println("|        " + numeroTelefono + ": " + nombre + "            |");
            }
            System.out.println("+------------------------------------+");
        }
    }
    //Método para agregar un nuevo contacto
    public void create(Scanner scanner) {
        System.out.println("Ingrese el número telefonico: ");
        String numeroTelefono = scanner.nextLine();
        System.out.println("Ingrese el nombre: ");
        String nombre = scanner.nextLine();
        contactos.put(numeroTelefono, nombre);
        System.out.println("+------------------------------------+");
        System.out.println("|     Nuevo contacto añadido!        |");
        System.out.println("+------------------------------------+");
    }
    //Método para eliminar un contacto existente
    public void delete(Scanner scanner) {
        System.out.println("Ingrese el número que desea eliminar: ");
        String numeroTelefono = scanner.nextLine();
        String nombre = contactos.remove(numeroTelefono);
        if (nombre != null) {
            System.out.println("+------------------------------------+");
            System.out.println("|       Contacto eliminado:          |");
            System.out.println("|        " + nombre + ": " + numeroTelefono + "            |");
            System.out.println("+------------------------------------+");
        } else {
            System.out.println("+------------------------------------+");
            System.out.println("|      Contacto no encontrado        |");
            System.out.println("+------------------------------------+");
        }
    }
    //Método para buscar contactos por nombre
    public void buscarPorNombre(String nombre) {
        boolean found = false;
        for (Map.Entry<String, String> entry : contactos.entrySet()) {
            String numeroTelefono = entry.getKey();
            String nombreContacto = entry.getValue();
            if (nombreContacto.equalsIgnoreCase(nombre)) {
                System.out.println("+------------------------------------+");
                System.out.println("|        " + numeroTelefono + ": " + nombreContacto + "            |");
                System.out.println("+------------------------------------+");
                found = true;
            }
        }
        if (!found) {
            System.out.println("+------------------------------------+");
            System.out.println("|    No se encontró el contacto      |");
            System.out.println("+------------------------------------+");
        }
    }
    //Método para editar contacto
    public void editarContacto(Scanner scanner){
        System.out.println("Ingrese el número de teléfono del contacto: ");
        String numeroTelefono = scanner.nextLine();
        if (contactos.containsKey(numeroTelefono)){
            System.out.println("Ingrese el nuevo nombre: ");
            String nuevoNombre = scanner.nextLine();
            contactos.put(numeroTelefono, nuevoNombre);
            System.out.println("+------------------------------------+");
            System.out.println("|    Contacto editado exitosamente   |");
            System.out.println("+------------------------------------+");
        }else {
            System.out.println("+------------------------------------+");
            System.out.println("| No se encontró el número ingresado |");
            System.out.println("+------------------------------------+");
        }
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook("contactos.csv");
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("+---------Agenda Telefónica-----------+");
            System.out.println("|        1. Mostrar contactos         |");
            System.out.println("|        2. Agregar contacto          |");
            System.out.println("|        3. Eliminar contacto         |");
            System.out.println("|   4. Buscar contacto por nombre     |");
            System.out.println("|        5. Editar un contacto        |");
            System.out.println("|        6.Guardar y salir            |");
            System.out.println("+-------------------------------------+");
            System.out.println("Ingrese su opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    addressBook.list();
                    break;
                case 2:
                    addressBook.create(scanner);
                    break;
                case 3:
                    addressBook.delete(scanner);
                    break;
                case 4:
                    System.out.println("Ingrese el nombre: ");
                    String buscarNombre = scanner.nextLine();
                    addressBook.buscarPorNombre(buscarNombre);
                    break;
                case 5:
                    addressBook.editarContacto(scanner);
                    break;
                case 6:
                    addressBook.save();
                    exit = true;
                    break;
                default:
                    System.out.println("Opción Invalida");
                    break;
            }
        }
        scanner.close();
        System.out.println("+------------------------------------+");
        System.out.println("|            Hasta luego!            |");
        System.out.println("+------------------------------------+");

    }
}
