import java.util.List;

/**
 * @author Anas EL MASSAFI
 */
public class Random implements Algorithm {

    @Override
    public int bestMove(List<String> board) {
        return chooseBestMove(getAvailableMoves(board));
    }

    private Integer chooseBestMove(List<String> availableMoves) {
        int i = new java.util.Random().nextInt(availableMoves.size());
        String move = availableMoves.get(i);
        return new Integer(move);
    }
}
