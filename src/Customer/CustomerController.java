package Customer;
import org.w3c.dom.ls.LSOutput;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Controller-klass för kundhantering
 * Hanterar användarinteraktion för kundrelaterade operationer
 */
public class CustomerController {

    // Service-lager för kundhantering, hanterar affärslogik
    CustomerService customerService;

    // Scanner för användarinput
    Scanner scanner;

    /**
     * Konstruktor för Customer.CustomerController
     * Initierar service och scanner
     */
    public CustomerController() {
        // Skapa instanser av nödvändiga objekt
        this.customerService = new CustomerService();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Huvudloop för kundhantering
     * Visar meny och hanterar användarval
     */
    public void run() {
        while (true) {
            try {
                // Skriv ut menyalternativ direkt i run-metoden för tydlighet
                System.out.println("\n=== Kundhantering ===");
                System.out.println("1. Visa alla kunder");
                System.out.println("2. Visa en kund");
                System.out.println("3. Registrera ny kund");
                System.out.println("4. Uppdatera kund");
                System.out.println("5. Radera kund");
                System.out.println("0. Avsluta");
                System.out.print("Välj ett alternativ: ");

                // Läs användarens val
                int select = scanner.nextInt();

                // Hantera användarens val
                switch (select) {
                    case 1:
                        // Anropa service-lagret för att visa alla kunder
                        customerService.showAllUsers();
                        break;
                    case 2:
                        System.out.println("Ange ett befintligt id: ");
                        Customer customer = customerService.getCustomerById(scanner.nextInt());
                        if (customer == null) {
                            System.out.println("Inga kund hittades");
                        }
                        System.out.println(customer.toString());
                        break;
                    case 3:
                        scanner.nextLine();
                        System.out.println("Ange namn: ");
                        String namn = scanner.nextLine();
                        System.out.println("Ange email: ");
                        String email = scanner.nextLine();
                        System.out.println("Ange telefon: ");
                        String telefon = scanner.nextLine();
                        System.out.println("Ange adress: ");
                        String adress = scanner.nextLine();
                        System.out.println("Ange lösenord: ");
                        String losenord = scanner.nextLine();
                        customerService.addCustomer(namn, email, telefon, adress, losenord);
                        break;
                    case 4:
                        displayUpdateMenu();
                        break;
                    case 5:
                        System.out.println("Ange kund id");
                        int customerId = scanner.nextInt();
                        customerService.deleteCustomerById(customerId);
                        break;
                    case 0:
                        System.out.println("Avslutar kundhantering...");
                        return;
                    default:
                        System.out.println("Ogiltigt val, försök igen");
                }
            } catch (SQLException e) {
                // Hantera databasfel
                System.out.println("Ett fel uppstod vid databasanrop: " + e.getMessage());
            } catch (Exception e) {
                // Hantera övriga fel (t.ex. felaktig input)
                System.out.println("Ett oväntat fel uppstod: " + e.getMessage());
                scanner.nextLine(); // Rensa scanner-bufferten vid felinmatning
            }
        }
    }

    public void displayUpdateMenu() throws SQLException {
        System.out.println("Ange kundens ID som ska uppdateras: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\nVad vill du uppdatera?");
        System.out.println("1. Namn"); // Metoden finns för att ändra namn
        System.out.println("2. Email"); // Metoden finns för att ändra email
        System.out.println("3. Telefon");
        System.out.println("4. Adress");
        System.out.println("5. Lösenord");
        System.out.println("0. Avsluta");

        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.println("Ange nytt namn: ");
                String newName = scanner.nextLine();
                customerService.updateCustomerName(customerId, newName);
                break;
            case 2:
                System.out.println("Ange ny email: ");
                String newEmail = scanner.nextLine();
                customerService.updateCustomerEmail(customerId, newEmail);
                break;
            case 0:
                System.out.println("Avslutar kundhantering...");
                return;
            default:
                System.out.println("Ogiltigt val, försök igen");
        }


    }
}