
/**
 * Notifiable is an interface that will be implemented on the client side and
 * available on both client and server sides.
 *
 * @author YutingFan
 */
import java.rmi.*;

public interface Notifiable extends Remote {

    /**
     * notify(String stockSym, double price) method is called by the server and
     * informs the callback client that a change in the stock price has occurred
     *
     * @param stockSym stock symbol
     * @param price stock price
     * @throws RemoteException Exception that occurs on remote network
     */
    public void notify(String stockSym, double price) throws RemoteException;

    /**
     * exit() is also called by the server and tells the callback client that it
     * should cease listening for this client
     *
     * @throws RemoteException
     */
    public void exit() throws RemoteException;
}