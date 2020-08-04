import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Anas EL MASSAFI
 */
public class ComputerPlayer implements Player {

    private Game game;
    private String mark;
    private List<String> moves;
    private String move;
    private Algorithm algorithm;

    public ComputerPlayer(Game game, String mark, Algorithm algorithm) {
        this.game = game;
        this.mark = mark;
        this.moves = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8");
        this.algorithm = algorithm;
    }

    @Override
    public void addMessage(String message) {
    }

    @Override
    public void newMove(String mark, int move) throws RemoteException {
        if (!this.mark.equals(mark)) {
            this.moves.set(move, mark);
            int i = algorithm.bestMove(moves);
            game.playMove(this.mark, i);
        } else {
            this.moves.set(move, mark);
        }
    }

    @Override
    public String getName() {
        return "Computer";
    }

    @Override
    public void winner() {

    }

    @Override
    public void loser() {

    }

    @Override
    public void meetingRoomRespond(int resp) {

    }
}
