
import java.rmi.Naming;
import java.util.Scanner;

/**
 * StockRMIClientSubscription prompts the user to input user name and subscribe
 * and unsubscribe stocks
 *
 * @author YutingFan
 */
public class StockRMIClientSubscription {

    /**
     * The main routine that prompts the user for input and
     * subscribe/unsubscribe operations.
     *
     * @param args commands
     */
    public static void main(String[] args) {
        try {
            System.out.println("TRANSACTION SCREEN");
            System.out.println("Enter user name:");
            Scanner sc = new Scanner(System.in);
            String name = sc.nextLine();

            System.out.println("Looking up the server in the registry");
            StockRMI c = (StockRMI) Naming.lookup("//localhost/stockService");

            System.out.println("Enter \"S <stock symbol>\" to subscribe or \"U <stock symbol>\" to unsubscribe. Enter \"!\" to quit.");
            System.out.print(">");
            sc = new Scanner(System.in);
            String command = sc.nextLine();
            while (!command.equals("!")) {
                System.out.print(">");
                String[] tokens = command.split(" ");
                String action = tokens[0];
                String symbol = tokens[1];
                switch (action) {
                    case "S":
                        c.subscribe(name, symbol);
                        break;
                    case "U":
                        c.unSubscribe(name, symbol);
                        break;
                }
                sc = new Scanner(System.in);
                command = sc.nextLine();
            }
            System.out.println("Bye!");
            c.deRegisterCallBack(name);
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
}
