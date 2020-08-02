import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Anas el massafi
 */
public interface GameDistant extends Remote {
    void sendMessage(String message) throws RemoteException;

    void playMove(String mark, int move) throws RemoteException;
}
