import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Anas EL Massafi
 */
public class Common {
    public static Logger logger = Logger.getLogger("Serveur");


    public static String checkWin(List<String> board) {
        String result;
        int checkNumber = 0;
        int i = 0;
        while (checkNumber < 3) {
            //HorizontalChecks +1
            result = checks(board, i, 1);
            if ("X".equals(result) || "O".equals(result)) {
                return result;
            }
            i += 3;
            checkNumber++;
        }
        checkNumber = 0;
        i = 0;
        while (checkNumber < 3) {
            //Vertical Checks +1
            result = checks(board, i, 3);
            if ("X".equals(result) || "O".equals(result)) {
                return result;
            }
            i += 1;
            checkNumber++;
        }
        // Diagonal check
        result = checks(board, 0, 4);
        if ("X".equals(result) || "O".equals(result)) {
            return result;
        }
        // Diagonal check
        result = checks(board, 2, 2);
        if ("X".equals(result) || "O".equals(result)) {
            return result;
        }

        if ("No".equals(result) && getAvailableMoves(board).size() == 0) {
            result = "tie";
        }
        return result;

    }

    private static List<String> getAvailableMoves(List<String> board) {
        return board.stream().filter(a -> !a.equals("O") && !a.equals("X")).collect(Collectors.toList());
    }

    //Horizontal checks
    private static String checks(List<String> board, int i, int next) {
        if (board.get(i).equals(board.get(i + next)) && board.get(i).equals(board.get(i + 2 * next))) {
            return board.get(i);
        }
        return "No";
    }
}
