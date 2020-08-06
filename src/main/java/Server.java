import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Anas EL Massafi
 */
public class Server extends UnicastRemoteObject implements ServerDistant {

    private final List<Player> players;
    int sessionId = -1;
    private List<Player> playersInWait = new ArrayList<>();
    private Game game;
    private boolean notyet = true;

    Server() throws RemoteException {
        players = new ArrayList<>();
    }

    public int getClientsNumber() {
        return this.players.size();
    }

    public synchronized void registerPlayer(Player player) throws RemoteException {
        boolean check = isClientExist(player.getName());
        if (!check) {
            this.players.add(player);
            Common.logger.info("le client " + player.getName() + " a été enregistré");
        } else {
            Common.logger.info("le client " + player.getName() + " existe déjà");
            throw new RemoteException("Veuillez changer le pseudo nom");
        }
    }

    @Override
    public Boolean checkValidName(String name) throws RemoteException {
        return !isClientExist(name);
    }

    private boolean isClientExist(String name) throws RemoteException {
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void disconnectPlayer(Player player) throws RemoteException {
        boolean removed = this.players.remove(player);
        playersInWait.remove(player);
        System.gc();
        System.runFinalization();
        if (removed) {
            Common.logger.info("joueur " + player.getName() + "  a quitté le serveur");
        }
        Common.logger.info("il y a " + this.players.size() + " clients sur le serveur");
    }

    public void removeByName(String name) {
        this.players.removeIf(a -> {
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
    public String joinRoom(Player player) throws RemoteException {
        try {
            if (playersInWait.size() == 0) {
                this.game = new Game(this);
                game.gameStatus = GameStatus.Waiting;
                sessionId = -1;
                this.notyet = true;
                playersInWait.add(player);
                game.players.put("X", player);
                return "X";
            } else if (playersInWait.size() == 1) {
                playersInWait.add(player);
                game.players.put("O", player);
                game.gameStatus = GameStatus.Running;
                //informPlayers(1);
                return "O";
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        throw new RemoteException("Server is full");
    }

    @Override
    public int getSessionId(String name) throws RemoteException, MalformedURLException {
        if (playersInWait.size() == 2) {
            if (notyet) {
                try {
                    sessionId = new Random().nextInt(10000) + 1;
                    //   = ThreadLocalRandom.current().nextInt(1, 10000 + 1);
                    Naming.rebind("tictactoe/" + sessionId, game);
                    notyet = false;
                    Common.logger.info("sessionId: " + sessionId);
                    game.informPlayers(1);
                } catch (RemoteException | MalformedURLException e) {
                    Common.logger.warning(e.getMessage());
                    throw e;
                }
            }
        }
        if (sessionId != -1) {
            game.gameStatus = GameStatus.Running;
            fresh();
        }
        return sessionId;
    }

    @Override
    public String playVsComputer(Player player, String algorithmName) throws RemoteException {
        try {
            algorithmName = algorithmName.trim().toLowerCase();
            Algorithm algorithm;
            switch (algorithmName) {
                case "minimax":
                    algorithm = new Minimax("O");
                    break;
                case "alphaBeta":
                    algorithm = new AlphaBeta("O");
                    break;
                default:
                    algorithm = new RandomAlgorithm();
            }
            Game game = new Game(this);
            Player computer = new ComputerPlayer(game, "O", algorithm);
            game.players.put("X", player);
            game.players.put("O", computer);
            game.gameStatus = GameStatus.Running;
            Naming.rebind("tictactoe/" + 0, game);
            return "X";
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "NO";
        }
    }

    private void fresh() {
        playersInWait = new ArrayList<>();
        Common.logger.info("new Clients In Wait");
    }

}

