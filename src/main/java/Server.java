import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Server extends UnicastRemoteObject implements ServerDistant {


    //Declare class variables
    private static int count = 0;
    private static boolean check = true;
    private static String TICTAC = "";
    private static int client_count = 0;
    private final List<Client> clients;
    private final List<Client> clientsInWait = new ArrayList<>();
    private final Game game = new Game();
    private boolean notyet = false;

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
    public GameDistant getSessionId() throws RemoteException, MalformedURLException {
        if (game.players.size() == 1) {
        /*    if (notyet) {
                try {
                    Naming.rebind("tictactoe/1",
                            game);
                } catch (RemoteException | MalformedURLException e) {
                    Common.logger.warning(e.getMessage());
                }
            }
            notyet = true;
            return 1;*/
            return game;
        }
        return null;
    }
    public Map<String, Client> players = new HashMap<>();
    /*
        Variables
     */
    private List<String> messages = new ArrayList<>();
    private List<String> moves = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8");


    @Override
    public void sendMessage(String message) throws RemoteException {
        for (Map.Entry<String, Client> entry : players.entrySet()) {
            String key = (String) entry.getKey();
            Client value = (Client) entry.getValue();
            value.addMessage(message);
        }
        messages.add(message);
    }

    @Override
    public void playMove(String mark, int move) throws RemoteException {
        String markAginst = mark.equals("X") ? "O" : "X";
        players.get(markAginst).newMove(move);
        moves.set(move, mark);
        String result = checkWin();
        if ("X".equals(result) || "O".equals(result)) {
            gameDone(result);
        }
    }

    private void gameDone(String result) {
        for (Map.Entry<String, Client> entry : players.entrySet()) {
            String key = (String) entry.getKey();
            Client value = (Client) entry.getValue();
            try {
                value.winner(result);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private String checkWin() {
        String result = "No";
        int checkNumber = 0;
        int i = 0;
        while (checkNumber > 2) {
            //HorizontalChecks +1
            result = checks(i, 1);
            if ("X".equals(result) || "O".equals(result)) {
                return result;
            }
            i += 3;
            checkNumber++;
        }
        checkNumber = 0;
        while (checkNumber > 2) {
            //HorizontalChecks +1
            result = checks(i, 3);
            if ("X".equals(result) || "O".equals(result)) {
                return result;
            }
            i += 1;
            checkNumber++;
        }
        result = checks(0, 4);
        if ("X".equals(result) || "O".equals(result)) {
            return result;
        }
        result = checks(2, 4);
        if ("X".equals(result) || "O".equals(result)) {
            return result;
        }
        return "No";
    }

    //Horizontal checks
    private String checks(int i, int next) {
        if (moves.get(i).equals(moves.get(i + next)) && moves.get(i).equals(moves.get(i + next))) {
            return moves.get(i);
        }
        return "No";
    }
}

