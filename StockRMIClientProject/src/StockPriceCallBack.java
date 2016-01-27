
import java.rmi.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * StockPriceCallBack provides notify and exit methods that can be called by the
 * server and display callback message.
 *
 * @author YutingFan
 */
public class StockPriceCallBack extends UnicastRemoteObject implements Notifiable {

    /**
     * the main routine that call the registerCallBack method on the stock
     * service
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // prompt the user for a user name
            System.out.println("CALLBACK SCREEN");
            System.out.println("Enter user name:");
            Scanner sc = new Scanner(System.in);
            String name = sc.nextLine();

            //look up on the registry
            System.out.println("Looking up the server in the registry");
            StockRMI c = (StockRMI) Naming.lookup("//localhost/stockService");

            //call the registerCallBack method on the stock service
            System.out.println("Creating a callback object to handle calls from the server");
            StockPriceCallBack callbackObj = new StockPriceCallBack();
            c.registerCallBack(callbackObj, name);
            System.out.println("Callback handler for " + name + " ready\n");
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    /**
     * Default constructor, throws RemoteException
     * @throws RemoteException Exception that occurs on remote network
     */
    public StockPriceCallBack() throws RemoteException {

    }

    /**
     * Notify on the callback screen for changes in price of the stock that the user is interested in.
     * @param stockSym the stock symbol
     * @param price the stock price
     * @throws RemoteException Exception that occurs on remote network
     */
    @Override
    public void notify(String stockSym, double price) throws RemoteException {
        System.out.println("Call back:  " + stockSym + " has changed to " + price);
    }

    /**
     * The user exits the process
     * @throws RemoteException Exception that occurs on remote network
     */
    @Override
    public void exit() throws RemoteException {
        try {
            UnicastRemoteObject.unexportObject(this, true);
            System.out.println("StockPriceCallBack exiting");
        } catch (Exception e) {
            System.out.println("Exception thrown" + e);
        }
    }
}
