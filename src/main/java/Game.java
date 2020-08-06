import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * @author Anas EL Massafi
 */
public class Game extends UnicastRemoteObject implements GameDistant {

    /*
    Variables
 */
    public Map<String, Player> players;
    public GameStatus gameStatus;
    private List<String> messages;
    private List<String> moves;
    private String turn;
    private Server server;

    protected Game(Server server) throws RemoteException {
        this.players = new HashMap<>();
        this.messages = new ArrayList<>();
        this.moves = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8");
        this.turn = "X";
        this.server = server;
        this.gameStatus = GameStatus.New;
        Common.logger.info("Game Has Been Created " + this);
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        if (gameStatus.equals(GameStatus.Running)) {
            for (Map.Entry<String, Player> entry : players.entrySet()) {
                String key = entry.getKey();
                Player value = entry.getValue();
                value.addMessage(message);
            }
            messages.add(message);
        }
    }

    @Override
    public void playMove(String mark, int move) throws RemoteException {
      /*  if (notyet) {
            server.fresh();
            notyet = false;
        }*/
        String result = Common.checkWin(this.moves);
        if (!result.equals("No")) {
            gameDone(result);
        } else if (gameStatus.equals(GameStatus.Running)) {
            if (mark.equals(turn)) {
                this.turn = mark.equals("X") ? "O" : "X";
                moves.set(move, mark);
                result = Common.checkWin(moves);
                if (!result.equals("No")) {
                    gameDone(result);
                }
                for (Map.Entry<String, Player> entry : players.entrySet()) {
                    String key = entry.getKey();
                    Player value = entry.getValue();
                    value.newMove(mark, move);
                }

            }
        }
    }

    public void gameDone(String winnerMark) {
        try {
            this.turn = "No";
            if (winnerMark.equals("tie")) {
                for (Map.Entry<String, Player> entry : players.entrySet()) {
                    String key = entry.getKey();
                    Player value = entry.getValue();
                    value.tie();
                }
            } else {
                String loserMark = winnerMark.equals("X") ? "O" : "X";
                Player winner = players.get(winnerMark);
                Player loser = players.get(loserMark);
                winner.winner();
                loser.loser();
                sendMessage("Server : " + winner.getName() + "a gagné le match");
                gameStatus = GameStatus.Completed;
            }
        } catch (RemoteException e) {
            Common.logger.warning(e.getMessage());
        }
    }

    @Override
    public void abandonGame(String name) throws RemoteException {
        if (gameStatus.equals(GameStatus.Running)) {
            String result = "";
            for (Map.Entry<String, Player> entry : players.entrySet()) {
                String key = entry.getKey();
                Player value = entry.getValue();
                if (value.getName().equals(name)) {
                    result = key;
                }
            }
            if ("X".equals(result) || "O".equals(result)) {
                gameDone(result.equals("X") ? "O" : "X");
                //   server.gameDone();
            }
            gameStatus = GameStatus.Completed;
        }
        sendMessage("Server : " + name + " a abandonné le jeu");

    }

    @Override
    public void informPlayers(int res) throws RemoteException {
        for (Map.Entry<String, Player> entry : players.entrySet()) {
            String key = entry.getKey();
            Player value = entry.getValue();
            sendMessage("Server : " + value.getName() + " rejoint le jeu en tant que joueur " + key);
            Common.logger.info("Server : " + value.getName() + " rejoint le jeu en tant que joueur " + key);
            if (key.equals("X")) {
                value.meetingRoomRespond(1);
            }
        }
        /*playersInWait.get(0).addMessage(names.get(0) + " rejoint le jeu ");
        playersInWait.get(0).addMessage(names.get(1) + );
        playersInWait.get(0).meetingRoomRespond(res);*/
    }
}
