
import java.rmi.Naming;
import java.util.Scanner;

/**
 * StockRMIClientPriceUpdate updates the stock price
 *
 * @author YutingFan
 */
public class StockRMIClientPriceUpdate {

    /**
     * user enters lines of text in the format "IBM 102.00" to update prices or "!" to quit
     *
     * @param args commands
     */
    public static void main(String[] args) {
        try {
            System.out.println("PRICE UPDATE TERMINAL");
            System.out.println("Enter stock symbol and price or ! to quit.");
            System.out.print(">");
            Scanner sc = new Scanner(System.in);
            String command = sc.nextLine();
            StockRMI c = (StockRMI) Naming.lookup("//localhost/stockService");
            while (!command.equals("!")) {
                System.out.print(">");
                String[] tokens = command.split(" ");
                String stock = tokens[0];
                double price = Double.parseDouble(tokens[1]);
                // each time the user enters a line, server is called and data is passed to a remote method named stockUpdate
                c.stockUpdate(stock, price);
                sc = new Scanner(System.in);
                command = sc.nextLine();
            }
            System.out.println("Bye!");
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
}
