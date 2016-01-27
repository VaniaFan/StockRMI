
/**
 * StockRMI is the interface to the remote stock service and will be implemented
 * on the server side.
 *
 * @author YutingFan
 */
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StockRMI extends Remote {

    /**
     * subscribe interface that will be implemented by the server side to add a
     * stock-user to a TreeMap
     *
     * @param user user name
     * @param stockSym stock symbol
     * @return true for successful operation
     * @throws RemoteException Exception that occurs on remote network
     */
    public boolean subscribe(String user, String stockSym) throws RemoteException;

    /**
     * unsubscribe interface that unsubscribe a user from a particular stock
     *
     * @param user user name
     * @param stockSym stock symbol
     * @return true for successful operation
     * @throws RemoteException Exception that occurs on remote network
     */
    boolean unSubscribe(String user, String stockSym) throws RemoteException;

    /**
     * Update the stock price and calls the remote client to display callback
     * message to users.
     *
     * @param stockSym stock symbol
     * @param price stock price
     * @throws RemoteException Exception that occurs on remote network
     */
    public void stockUpdate(String stockSym, double price) throws RemoteException;

    /**
     * Provides callback information on the server side when new user comes in
     *
     * @param remoteClient refers to remote client
     * @param user user name
     * @throws RemoteException Exception that occurs on remote network
     */
    public void registerCallBack(Notifiable remoteClient, String user) throws
            RemoteException;

    /**
     * Provides callback information on the server side when a user unsubscribes
     *
     * @param user user name
     * @throws RemoteException Exception that occurs on remote network
     */
    public void deRegisterCallBack(String user) throws RemoteException;
}