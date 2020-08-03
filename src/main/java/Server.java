import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Server extends UnicastRemoteObject implements ServerDistant {


    //Declare class variables
    private static int count = 0;
    private static boolean check = true;
    private static String TICTAC = "";
    private static int client_count = 0;
    private final List<Client> clients;
    private List<Client> clientsInWait = new ArrayList<>();
    private Game game = new Game(this);
    private boolean notyet = true;


    Server() throws RemoteException {
        clients = new ArrayList<>();
    }

    public static int getClients() {
        return client_count;
    }

    public synchronized void registerClient(Client client) throws RemoteException {
        boolean check = isClientExist(client.getName());
        if (!check) {
            this.clients.add(client);
            Common.logger.info("le client " + client.getName() + " a été enregistré");
            client_count++;
        } else {
            Common.logger.info("le client " + client.getName() + " a été enregistré");
            throw new RemoteException("plz change the pseudo name");
        }
    }

    @Override
    public Boolean checkValidName(String name) throws RemoteException {
        return !isClientExist(name);
    }

    private boolean isClientExist(String name) throws RemoteException {
        for (Client client : clients) {
            if (client.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void disconnectClient(String name) {
        client_count--;
        this.clients.removeIf(a -> {
            boolean temp = false;
            try {
                temp = a.getName().equals(name);
                if (temp) Common.logger.info(name + " est déconnecté");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return temp;
        });
        Common.logger.info("il y a " + this.clients.size() + " clients sur le serveur");
    }

    @Override
    public String joinRoom(Client client) throws RemoteException {
        if (clientsInWait.size() == 0) {
            game.players.put("X", client);
            clientsInWait.add(client);
            return "X";
        } else if (clientsInWait.size() == 1) {
            game.players.put("O", client);
            return "O";
        }
        throw new RemoteException("Server is full");
    }

    @Override
    public int getSessionId(String name) throws RemoteException, MalformedURLException {
        if (clientsInWait.size() == 1) {
            if (notyet) {
                try {
                    Naming.rebind("tictactoe/1",
                            game);
                    notyet = false;
                } catch (RemoteException | MalformedURLException e) {
                    Common.logger.warning(e.getMessage());
                }
            }
            return 1;
        }
        return 0;
    }

    public void gameDone() throws RemoteException {
        this.game = new Game(this);
    }
    public void fresh(){
        clientsInWait = new ArrayList<>();
    }
}

