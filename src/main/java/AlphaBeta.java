import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Anas EL MASSAFI
 */
public class AlphaBeta implements Algorithm {

    private final Map<String, Integer> scores;
    private final String ai;
    private final String human;

    // List<String> board;
//
    public AlphaBeta(String ai) {
        this.ai = ai;
        this.human = ai.equals("X") ? "O" : "X";
        scores = new HashMap<String, Integer>() {{
            put(ai, 10);
            put(human, -10);
            put("tie", 0);
        }};
    }

    @Override
    public int bestMove(List<String> board) {
        int move = 0;
        if (getAvailableMoves(board).size() > 1) {
            move = new Integer(getAvailableMoves(board).get(0));
            // this.board = clone;
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < board.size(); i++) {
                if (!board.get(i).equals("X") && !board.get(i).equals("O")) {
                    board.set(i, ai);
                    int score = minimax(board, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                    board.set(i, "" + i);
                    if (score > bestScore) {
                        bestScore = score;
                        move = i;
                    }
                }
            }
        }
        return move;
    }

    private int minimax(List<String> board, int depth, int alpha, int beta, boolean isMaximizing) {
        String result = Common.checkWin(board);
        if (!result.equals("No")) {
            return scores.get(result);
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < board.size(); i++) {
                if (!board.get(i).equals("X") && !board.get(i).equals("O")) {
                    board.set(i, ai);
                    int score = minimax(board, depth + 1, alpha, beta, false);
                    board.set(i, "" + i);
                    bestScore = Math.max(score, bestScore);
                    alpha = Math.max(alpha, score);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < board.size(); i++) {
                if (!board.get(i).equals("X") && !board.get(i).equals("O")) {
                    board.set(i, human);
                    int score = minimax(board, depth + 1, alpha, beta, true);
                    board.set(i, "" + i);
                    bestScore = Math.min(score, bestScore);
                    beta = Math.min(score, beta);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return bestScore;
        }
    }

}