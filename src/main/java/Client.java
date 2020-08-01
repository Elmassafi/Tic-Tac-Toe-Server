import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    void addMessage(String message) throws RemoteException;

    void doMove(String message) throws RemoteException;

    void getBtn(String Btn) throws RemoteException;

    String getName() throws RemoteException;
}
