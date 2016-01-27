
import java.rmi.*;
import java.rmi.registry.*;

/**
 * StockRMIServer is the server that creates a registry and interacts with
 * remote clients
 *
 * @author YutingFan
 */
public class StockRMIServer {

    /**
     * main routine that display message on the server side.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            System.out.println("SERVER SCREEN");
            System.out.println("Server StockRMIServer running");
            StockRMI stockService = new StockRMIServant();
            Registry registry = LocateRegistry.createRegistry(1099);
            Naming.rebind("stockService", stockService);
            System.out.println("Created StockRMIServant object");
            System.out.println("Placing in Registry");
            System.out.println("StockRMIServant ready\n");
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
}
