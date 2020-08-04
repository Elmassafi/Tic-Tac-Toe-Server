import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Anas EL MASSAFI
 */
public interface Algorithm {

    int bestMove(List<String> board);

    default List<String> getAvailableMoves(List<String> board) {
        return board.stream().filter(a -> !a.equals("O") && !a.equals("X")).collect(Collectors.toList());
    }
}
