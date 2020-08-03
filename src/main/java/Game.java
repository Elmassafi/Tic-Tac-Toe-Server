import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * @author Anas EL Massafi
 * @email anas.elmassafi@gmail.com
 */
public class Game extends UnicastRemoteObject implements GameDistant {

    /*
    Variables
 */
    public Map<String, Client> players;
    private List<String> messages;
    private List<String> moves;
    private String turn;
    private Server server;
    private boolean notyet = true;

    protected Game(Server server) throws RemoteException {
        players = new HashMap<>();
        this.messages = new ArrayList<>();
        this.moves = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8");
        this.turn = "X";
        this.server = server;
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        for (Map.Entry<String, Client> entry : players.entrySet()) {
            String key = entry.getKey();
            Client value = entry.getValue();
            value.addMessage(message);
        }
        messages.add(message);
    }

    @Override
    public void playMove(String mark, int move) throws RemoteException {
        if (notyet) {
            server.fresh();
            notyet = false;
        }
        if (mark.equals(turn)) {
            for (Map.Entry<String, Client> entry : players.entrySet()) {
                String key =  entry.getKey();
                Client value =entry.getValue();
                value.newMove(mark, move);
            }
            moves.set(move, mark);
            this.turn = mark.equals("X") ? "O" : "X";
            String result = checkWin();
            if ("X".equals(result) || "O".equals(result)) {
                gameDone(result);
            }
        }
    }

    public void gameDone(String winnerMark) {
        this.turn = "No";
        String loserMark = winnerMark.equals("X") ? "O" : "X";
        Client winner = players.get(winnerMark);
        Client loser = players.get(loserMark);
        try {
            winner.winner();
            loser.loser();
            sendMessage("Server : " + winner.getName() + "a gagné le match");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private String checkWin() {
        String result = "No";
        int checkNumber = 0;
        int i = 0;
        while (checkNumber < 2) {
            //HorizontalChecks +1
            result = checks(i, 1);
            if ("X".equals(result) || "O".equals(result)) {
                return result;
            }
            i += 3;
            checkNumber++;
        }
        checkNumber = 0;
        while (checkNumber < 2) {
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
        result = checks(2, 2);
        if ("X".equals(result) || "O".equals(result)) {
            return result;
        }
        return result;
    }

    //Horizontal checks
    private String checks(int i, int next) {
        if (moves.get(i).equals(moves.get(i + next)) && moves.get(i).equals(moves.get(i + 2 * next))) {
            return moves.get(i);
        }
        return "No";
    }

    @Override
    public void abandonGame(String name) throws RemoteException {
        String result = "";
        for (Map.Entry<String, Client> entry : players.entrySet()) {
            String key =entry.getKey();
            Client value =  entry.getValue();
            if (value.getName().equals(name)) {
                result = key;
            }
        }
        if ("X".equals(result) || "O".equals(result)) {
            gameDone(result);
            sendMessage("Server : " + name + " a abandonné le jeu");
            server.gameDone();
        }
    }
}
