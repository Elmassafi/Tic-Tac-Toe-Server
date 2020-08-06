import java.rmi.Naming;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;


public class Launcher {

    public static void main(String[] args) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        //System.out.println(dateFormat.format(cal.getTime()));
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            Naming.rebind("tictactoe", new Server());
            Common.logger.log(Level.SEVERE, "le serveur a démarré avec succès au port: 1099");
        } catch (Exception e) {
            Common.logger.log(Level.WARNING, "le serveur a échoué avec l'erreur " + e.getMessage());
        }
    }
}
