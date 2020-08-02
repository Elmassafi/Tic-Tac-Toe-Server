import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public interface Test extends Remote {
    public String name() throws RemoteException;
}
class TestImpl extends UnicastRemoteObject implements Test{
    private String name;
    public TestImpl(String name) throws RemoteException {
        this.name=name;
    }

    @Override
    public String name() {
        return this.name;
    }
}
