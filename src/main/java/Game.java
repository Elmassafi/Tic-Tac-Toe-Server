import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * @author Anas EL Massafi
 * @email anas.elmassafi@gmail.com
 */
public class Game extends UnicastRemoteObject implements GameDistant {

    public Map<String, Client> players = new HashMap<>();
    /*
        Variables
     */
    private List<String> messages = new ArrayList<>();
    private List<String> moves = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8");

    protected Game() throws RemoteException {

    }

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
