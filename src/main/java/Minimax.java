import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Anas EL MASSAFI
 */
public class Minimax implements Algorithm {

    private final Map<String, Integer> scores;
    private final String ai;
    private final String human;
    // List<String> board;

    public Minimax(String ai) {
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
            System.out.println("Min Value " + bestScore);
            for (int i = 0; i < board.size(); i++) {
                if (!board.get(i).equals("X") && !board.get(i).equals("O")) {
                    board.set(i, ai);
                    System.out.println("---------------- First Time -------------");
                    System.out.println(board);
                    System.out.println("-----------------------------------------");
                    int score = minimax(board, 0, false);
                    board.set(i, "" + i);
                    System.out.println("score: " + score + " &&  bestScore:" + bestScore);
                    if (score > bestScore) {
                        bestScore = score;
                        move = i;
                    }
                }
            }
        }
        System.out.println("Moves Chose by aI is " + move);
        return move;
    }

    private int minimax(List<String> board, int depth, boolean isMaximizing) {
        String result = Common.checkWin(board);
        System.out.println("check Win result:"+result);
        if (!result.equals("No")) {
            return scores.get(result);
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < board.size(); i++) {

                if (!board.get(i).equals("X") && !board.get(i).equals("O")) {
                    board.set(i, ai);
                    int score = minimax(board, depth + 1, false);
                    System.out.println("---------------- First ai -------------");
                    System.out.println(board);
                    System.out.println("-----------------------------------------");
                    board.set(i, "" + i);
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < board.size(); i++) {

                if (!board.get(i).equals("X") && !board.get(i).equals("O")) {
                    board.set(i, human);
                    System.out.println("---------------- First human -------------");
                    System.out.println(board);
                    System.out.println("-----------------------------------------");
                    int score = minimax(board, depth + 1, true);
                    board.set(i, "" + i);
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }

}
