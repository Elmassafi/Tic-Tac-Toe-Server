
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Game extends UnicastRemoteObject implements GameDistant {


    //Declare class variables
    private static int count = 0;
    private static boolean c1 = true, c2 = true, c3 = true, c4 = true, c5 = true, c6 = true, c7 = true, c8 = true, c9 = true;
    private static char[] aMoves = new char[10];
    private static boolean check = true;
    private static String TICTAC = "";

    private final List<Client> clients;
    private static int client_count = 0;

    Game() throws RemoteException {
        clients = new ArrayList<>();
    }

    public synchronized void registerClient(Client client) throws RemoteException {
        boolean check=isClientExist(client.getName());
        if(!check){
            this.clients.add(client);
            Common.logger.info("le client " + client.getName() + " a été enregistré");
            client_count++;
        }else {
            Common.logger.info("le client " + client.getName() + " a été enregistré");
            throw new RemoteException("plz change the pseudo name");
        }
    }

    @Override
    public Boolean checkValidName(String name) throws RemoteException {
        return !isClientExist(name) ;
    }

    private boolean isClientExist(String name) throws RemoteException {
        for (Client client:clients) {
            if (client.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
    public synchronized void broadcastMessage(String message) throws RemoteException {
        int i = 0;
        while (i < clients.size()) {
            clients.get(i++).addMessage(message);

        }
    }

    public synchronized void broadcastMove(String move) throws RemoteException {

        if (count % 2 == 0) {
            if (move.equals("b1") && c1) {
                c1 = false;
                aMoves[1] = 'X';
                count++;
                TICTAC = "X";
            }
            if (move.equals("b2") && c2) {
                c2 = false;
                aMoves[2] = 'X';
                count++;
                TICTAC = "X";
            }

            if (move.equals("b3") && c3) {
                c3 = false;
                aMoves[3] = 'X';
                count++;
                TICTAC = "X";
            }

            if (move.equals("b4") && c4) {
                c4 = false;
                aMoves[4] = 'X';
                count++;
                TICTAC = "X";
            }

            if (move.equals("b5") && c5) {
                c5 = false;
                aMoves[5] = 'X';
                count++;
                TICTAC = "X";
            }

            if (move.equals("b6") && c6) {
                c6 = false;
                aMoves[6] = 'X';
                count++;
                TICTAC = "X";
            }

            if (move.equals("b7") && c7) {
                c7 = false;
                aMoves[7] = 'X';
                count++;
                TICTAC = "X";
            }

            if (move.equals("b8") && c8) {
                c8 = false;
                aMoves[8] = 'X';
                count++;
                TICTAC = "X";
            }

            if (move.equals("b9") && c9) {
                c9 = false;
                aMoves[9] = 'X';
                count++;
                TICTAC = "X";
            }

        } else {

            if (move.equals("b1") && c1) {

                c1 = false;
                aMoves[1] = 'O';
                count++;
                TICTAC = "O";
            }
            if (move.equals("b2") && c2) {
                c2 = false;
                aMoves[2] = 'O';
                count++;
                TICTAC = "O";

            }

            if (move.equals("b3") && c3) {
                c3 = false;
                aMoves[3] = 'O';
                count++;
                TICTAC = "O";

            }

            if (move.equals("b4") && c4) {
                c4 = false;
                aMoves[4] = 'O';
                count++;
                TICTAC = "O";

            }

            if (move.equals("b5") && c5) {
                c5 = false;
                aMoves[5] = 'O';
                count++;
                TICTAC = "O";
            }

            if (move.equals("b6") && c6) {
                c6 = false;
                aMoves[6] = 'O';
                count++;
                TICTAC = "O";
            }

            if (move.equals("b7") && c7) {
                c7 = false;
                aMoves[7] = 'O';
                count++;
                TICTAC = "O";
            }

            if (move.equals("b8") && c8) {
                c8 = false;
                aMoves[8] = 'O';
                count++;
                TICTAC = "O";
            }

            if (move.equals("b9") && c9) {
                c9 = false;
                aMoves[9] = 'O';
                count++;
                TICTAC = "O";
            }
        }


        //Check for whether wins have been checked
        if (check) {

            //Check for whether player X wins
            if (
                //Horizontal checks
                    (aMoves[1] == aMoves[2] && aMoves[1] == aMoves[3] && aMoves[1] == 'X') ||
                            (aMoves[4] == aMoves[5] && aMoves[4] == aMoves[6] && aMoves[4] == 'X') ||
                            (aMoves[7] == aMoves[8] && aMoves[7] == aMoves[9] && aMoves[7] == 'X') ||

                            //Vertical checks
                            (aMoves[1] == aMoves[4] && aMoves[1] == aMoves[7] && aMoves[1] == 'X') ||
                            (aMoves[2] == aMoves[5] && aMoves[2] == aMoves[8] && aMoves[2] == 'X') ||
                            (aMoves[3] == aMoves[6] && aMoves[3] == aMoves[9] && aMoves[3] == 'X') ||

                            //Diagonal checks
                            (aMoves[1] == aMoves[5] && aMoves[1] == aMoves[9] && aMoves[1] == 'X') ||
                            (aMoves[3] == aMoves[5] && aMoves[3] == aMoves[7] && aMoves[3] == 'X')
            ) {

                //For Future Development (PVC)
                //Set as player X won
                //Xwin = true;

                //Set false to stop rechecking
                check = false;

                //Show Message dialog that player X wins
                TICTAC = "X Result: Congratulations!<br>Player X wins!";

                //Stop further clicks on game buttons
                c1 = false;
                c2 = false;
                c3 = false;
                c4 = false;
                c5 = false;
                c6 = false;
                c7 = false;
                c8 = false;
                c9 = false;

            }

            //Check for whether player O wins
            else if (
                //Horizontal checks
                    (aMoves[1] == aMoves[2] && aMoves[1] == aMoves[3] && aMoves[1] == 'O') ||
                            (aMoves[4] == aMoves[5] && aMoves[4] == aMoves[6] && aMoves[4] == 'O') ||
                            (aMoves[7] == aMoves[8] && aMoves[7] == aMoves[9] && aMoves[7] == 'O') ||

                            //Vertical checks
                            (aMoves[1] == aMoves[4] && aMoves[1] == aMoves[7] && aMoves[1] == 'O') ||
                            (aMoves[2] == aMoves[5] && aMoves[2] == aMoves[8] && aMoves[2] == 'O') ||
                            (aMoves[3] == aMoves[6] && aMoves[3] == aMoves[9] && aMoves[3] == 'O') ||

                            //Diagonal checks
                            (aMoves[1] == aMoves[5] && aMoves[1] == aMoves[9] && aMoves[1] == 'O') ||
                            (aMoves[3] == aMoves[5] && aMoves[3] == aMoves[7] && aMoves[3] == 'O')
            ) {

                //For Future Development (PVC)
                //Set as player O won
                //Owin = true;

                //Set false to stop rechecking
                check = false;

                //Show Message dialog that player O wins
                TICTAC = "O Result: Congratulations!<br>Player O wins!";

                //Stop further clicks on game buttons
                c1 = false;
                c2 = false;
                c3 = false;
                c4 = false;
                c5 = false;
                c6 = false;
                c7 = false;
                c8 = false;
                c9 = false;

            }

            //Case of tie
            else if (!(c1 || c2 || c3 || c4 || c5 || c6 || c7 || c8 || c9)) {

                //Set false to stop rechecking
                check = false;

                //Show Message dialog that the game is a tie
                TICTAC = "Result: The game is a tie!";

            }

        }


        int i = 0;
        while (i < clients.size()) {
            clients.get(i++).doMove(TICTAC);

        }
    }

    public static int getClients() {
        return client_count;
    }

    public void disconnectClient(String name)  {
        client_count--;
        this.clients.removeIf(a -> {
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


}
