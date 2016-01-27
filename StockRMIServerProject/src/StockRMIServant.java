
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * StockRMIServant implements subscribe, unsubscribe, register and deregister
 * methods on the server side and calls for callback methods on the client side.
 *
 * @author YutingFan
 */
public class StockRMIServant extends UnicastRemoteObject implements StockRMI {

    /**
     * Given a stock, get a list of users that are interested in that stock.
     */
    private static final Map<String, List<String>> stocks = new TreeMap();
    /**
     * Given a user, get the remote object reference to its callback method.
     */
    private static final Map users = new TreeMap();

    /**
     * Default constructor for StockRMIServant
     *
     * @throws RemoteException Exception that occurs on remote network
     */
    public StockRMIServant() throws RemoteException {
    }

    /**
     * If the stock has already been subscribed by other users, add this user to
     * the list; Otherwise, add the stock symbol and user name as a key-value
     * pair in the TreeMap called stocks.
     *
     * @param user user name
     * @param stockSym stock symbol
     * @return true for successful operation
     * @throws RemoteException Exception that occurs on remote network
     */
    @Override
    public boolean subscribe(String user, String stockSym) throws RemoteException {
        System.out.println("User " + user + " subscribes stock " + stockSym);
        if (stocks.containsKey(stockSym)) {
            stocks.get(stockSym).add(user);
        } else {
            List<String> stockUserList = new ArrayList<>();
            stockUserList.add("0.0");//price is put at the zeroth index of the list
            stockUserList.add(user);
            stocks.put(stockSym, stockUserList);
        }
        return true;
    }

    /**
     * If this user is the only one that subscribes this stock, remove this
     * stock from stocks TreeMap; Otherwise, only remove this user from the list
     * of users that subscribe this stock.
     *
     * @param user user name
     * @param stockSym stock symbol
     * @return true for successful operation
     * @throws RemoteException Exception that occurs on remote network
     */
    @Override
    public boolean unSubscribe(String user, String stockSym) throws RemoteException {
        System.out.println("User " + user + " unsubscribes stock " + stockSym);
        if (stocks.get(stockSym).size() == 2) {
            //Besides the price that puts at first, only this user shows interest in this stock
            stocks.remove(stockSym);
        } else {
            stocks.get(stockSym).remove(user);
        }
        return true;
    }

    /**
     * Update the stock price and calls the remote client to display callback
     * message to users.
     *
     * @param stockSym stock symbol
     * @param price stock price
     * @throws RemoteException Exception that occurs on remote network
     */
    @Override
    public void stockUpdate(String stockSym, double price) throws RemoteException {
        System.out.println("Update " + stockSym + " to " + price);
        List<String> userList = stocks.get(stockSym);
        userList.set(0, price + "");
        for (int i = 1; i < userList.size(); i++) {
            ((Notifiable) (users.get(userList.get(i)))).notify(stockSym, price);
        }
    }

    /**
     * Provides callback information on the server side when new user comes in
     *
     * @param remoteClient refers to remote client
     * @param user user name
     * @throws RemoteException Exception that occurs on remote network
     */
    @Override
    public void registerCallBack(Notifiable remoteClient, String user) throws RemoteException {
        if (!users.containsKey(user)) {
            users.put(user, remoteClient);
            System.out.println("New client " + user + " registered");
        }
    }

    /**
     * Provides callback information on the server side when a user unsubscribes
     *
     * @param user user name
     * @throws RemoteException Exception that occurs on remote network
     */
    @Override
    public void deRegisterCallBack(String user) throws RemoteException {
        ((Notifiable) (users.get(user))).exit();
        users.remove(user);
        System.out.println(user + " unregistered.");
    }
}
