import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerDistant extends Remote {
    void registerPlayer(Player Player) throws RemoteException;

    void disconnectPlayer(Player Player) throws RemoteException;

    Boolean checkValidName(String name) throws RemoteException;

    int getSessionId(String name) throws RemoteException, MalformedURLException;

    String joinRoom(Player player) throws RemoteException;

    String playVsComputer(Player player) throws RemoteException;
}
