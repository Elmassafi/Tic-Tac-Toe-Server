import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Anas EL Massafi
 * @email anas.elmassafi@gmail.com
 */
public interface Client extends Remote {

    void addMessage(String message) throws RemoteException;

    void newMove(String mark,int move) throws RemoteException;

    String getName() throws RemoteException;

    void winner() throws RemoteException;
    void loser() throws RemoteException;
}
