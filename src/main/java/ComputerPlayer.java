import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Anas EL MASSAFI
 */
public class ComputerPlayer implements Player {


    List<String> messages = Arrays.asList("Nous relevons ce défi.", "Aimeriez vous être amis?", "salut! Mon ami Tibbers veut vous rencontrer", "I can smell your fear!");

    private Game game;
    private String mark;
    private List<String> moves;
    private String move;
    private Algorithm algorithm;

    private boolean me = true;
    private Boolean gameIsOver = false;

    public ComputerPlayer(Game game, String mark, Algorithm algorithm) {
        this.game = game;
        this.mark = mark;
        this.moves = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8");
        this.algorithm = algorithm;
    }

    @Override
    public void addMessage(String message) {
     /*
     TO DO in future ::
     Idea here : Build a ComputerPlayer that respond to every message with a message
     but i think there is a lot to change is the messaging mechanism
     it's not easy as i thought,
     StackOverflow is building up here so I think it needs more time
     :) let's continue
     */
        /*
      if (me && messages.size() > 0) {
            me = false;
            int i = new Random().nextInt(messages.size());
            System.out.println("messages.size()= " + messages.size());
            System.out.println("Random = " + i);
            String send = new String(messages.get(i));
            messages.remove(i);
            game.sendMessage(send);
        }
        me = true;*/
    }

    @Override
    public void newMove(String mark, int move) throws RemoteException {
        if (!gameIsOver) {
            if (!this.mark.equals(mark)) {
                this.moves.set(move, mark);
                int i = algorithm.bestMove(moves);
                game.playMove(this.mark, i);
            } else {
                this.moves.set(move, mark);
            }
        }
    }

    @Override
    public String getName() {
        return "Computer ";
    }

    @Override
    public void winner() throws RemoteException {
        game.sendMessage("Vous serez jugé.");
        game.sendMessage("Les vrais guerriers sont nés sur le champ de bataille!");
        game.sendMessage("Let's play again some time!");
        gameIsOver = true;

    }

    @Override
    public void loser() throws RemoteException {
        game.sendMessage("Je te laisse partir facilement!");
        game.sendMessage("Let's play again some time!");
        gameIsOver = true;
    }

    @Override
    public void tie() throws RemoteException {
        game.sendMessage("J'ai l'impression d'avoir déjà fait ça.");
        game.sendMessage("Let's play again some time!");
        gameIsOver = true;
    }

    @Override
    public void meetingRoomRespond(int resp) {

    }
}
