

import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerDistant extends Remote {
    void registerClient(Client Client) throws RemoteException;
    void disconnectClient(String name) throws RemoteException;
    Boolean checkValidName(String name) throws RemoteException;
    GameDistant getSessionId() throws RemoteException, MalformedURLException;
    String joinRoom(Client client)throws RemoteException;
    void sendMessage(String message) throws RemoteException;
    void playMove(String mark, int move) throws RemoteException;
}
