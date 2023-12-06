import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Main {
    public enum State {
        New, Load
    }

    public static State state = State.New;
    public static List<Airport> airports = new ArrayList<>();
    public static List<Flight> flights = new ArrayList<>();
    public static List<Location> locations = new ArrayList<>();
    public static List<Passenger> passengers = new ArrayList<>();
    public static List<Plane> planes = new ArrayList<>();
    public static List<Terminal> terminals = new ArrayList<>();
    public static List<Ticket> tickets = new ArrayList<>();

    private static void saveToFile(String path) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(airports);
            oos.writeObject(flights);
            oos.writeObject(locations);
            oos.writeObject(passengers);
            oos.writeObject(planes);
            oos.writeObject(terminals);
            oos.writeObject(tickets);
        }

        catch (IOException e) {
            throw new IOException("Error saving data to file", e);
        }
    }

    private static void loadFromFile(String path) throws IOException, FormatException, FileNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            airports = castList(ois.readObject(), Airport.class);
            flights = castList(ois.readObject(), Flight.class);
            locations = castList(ois.readObject(), Location.class);
            passengers = castList(ois.readObject(), Passenger.class);
            planes = castList(ois.readObject(), Plane.class);
            terminals = castList(ois.readObject(), Terminal.class);
            tickets = castList(ois.readObject(), Ticket.class);
            state = State.Load;
        }

        catch (IOException | ClassNotFoundException e) {
            throw new FormatException();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> castList(Object obj, Class<T> _class) {
        if (obj == null)
            return new ArrayList<>();
        if (_class.isInstance(obj))
            return (List<T>) obj;
        throw new ClassCastException("Unable to cast object to List<" + _class.getSimpleName() + ">");
    }

    private static Scanner scanner = new Scanner(System.in);
    private static String path = "air.txt";

    static void menu() throws IOException {
        while (true) {
            printMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    addObject();
                    break;
                case 2:
                    try {
                        saveToFile(path);
                    } catch (SaveException e) {
                        throw new SaveException();
                    }
                    break;
                case 3:
                    try {
                        loadFromFile(path);
                    } catch (Exception e) {
                        throw new LoadException();
                    }
                case 4:
                    printTypes();
                    int index = scanner.nextInt();
                    printTypeDetails(index);
                    break;

                case 5:
                    System.out.println("Exiting the program.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Add new object");
        System.out.println("2. Save data to file");
        System.out.println("3. Load data from file.");
        System.out.println("4. Print from the database.");
        System.out.println("5. Exit");
    }

    private static int getUserChoice() {
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }

    private static void printTypes() {
        System.out.println("1. Airport");
        System.out.println("2. Flight");
        System.out.println("3. Location");
        System.out.println("4. Passenger");
        System.out.println("5. Plane");
        System.out.println("6. Terminal");
        System.out.println("7. Ticket");
    }

    private static void printTypeDetails(int type) {
        switch (type) {
            case 1:
                System.out.println("Airport Details:");
                System.out.println(airports);
                break;
            case 2:
                System.out.println("Flight Details:");
                System.out.println(flights);
                break;
            case 3:
                System.out.println("Location Details:");
                System.out.println(locations);
                break;
            case 4:
                System.out.println("Passenger Details:");
                System.out.println(passengers);
                break;
            case 5:
                System.out.println("Plane Details:");
                System.out.println(planes);
                break;
            case 6:
                System.out.println("Terminal Details:");
                System.out.println(terminals);
                break;
            case 7:
                System.out.println("Ticket Details:");
                System.out.println(tickets);
                break;
            default:
                System.out.println("Invalid type. Please enter a number between 1 and 7.");
        }
    }

    private static void addObject() {
        System.out.println("Choose the type of object to add:");
        printTypes();

        int objectType = scanner.nextInt();
        scanner.nextLine();

        switch (objectType) {
            case 1:
                System.out.print("Enter Airport name: ");
                String airportName = scanner.nextLine();
                Airport airport = new Airport(airportName, null, null);
                airports.add(airport);
                System.out.println("Airport added: " + airport);
                break;

            case 2:
                System.out.print("Enter Flight number: ");
                String flightNumber = scanner.nextLine();
                Flight flight = new Flight(flightNumber, null, null);
                flights.add(flight);
                System.out.println("Flight added: " + flight);
                break;

            case 3:
                System.out.print("Enter Location city: ");
                String city = scanner.nextLine();
                Location location = new Location(city, null, null);
                locations.add(location);
                System.out.println("Location added: " + location);
                break;

            case 4:
                System.out.print("Enter Passenger number and age: ");
                String passengerNumber = scanner.nextLine();
                int age = scanner.nextInt();
                Passenger passenger = new Passenger(passengerNumber, age, null, null);
                passengers.add(passenger);
                System.out.println("Passenger added: " + passenger);
                break;

            case 5:
                System.out.print("Enter Plane ID, model, capacity and top speed: ");
                String planeID = scanner.nextLine();
                String model = scanner.nextLine();
                int capacity = scanner.nextInt();
                float topSpeed = scanner.nextFloat();
                Plane plane = new Plane(planeID, model, capacity, topSpeed);
                planes.add(plane);
                System.out.println("Plane added: " + plane);
                break;

            case 6:
                System.out.print("Enter Terminal name: ");
                String terminalName = scanner.nextLine();
                Terminal terminal = new Terminal(terminalName, null, null);
                terminals.add(terminal);
                System.out.println("Terminal added: " + terminal);
                break;

            case 7:
                System.out.print("Enter Ticket number: ");
                int ticketNumber = scanner.nextInt();
                Ticket ticket = new Ticket(ticketNumber, null, null);
                tickets.add(ticket);
                System.out.println("Ticket added: " + ticket);
                break;

            default:
                System.out.println("Invalid object type. Please try again.");
        }
    }

    public static void main(String[] args) throws IOException {
        menu();
    }
}
