import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Anas EL Massafi
 */
public interface Player extends Remote {

    void addMessage(String message) throws RemoteException;

    void newMove(String mark, int move) throws RemoteException;

    String getName() throws RemoteException;

    void winner() throws RemoteException;

    void loser() throws RemoteException;

    void tie() throws RemoteException;

    void meetingRoomRespond(int resp) throws RemoteException;
}
