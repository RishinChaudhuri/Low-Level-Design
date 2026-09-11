

import controllers.GameController;
import entities.Player;
import enumerations.PieceType;

import java.util.UUID;

public class GameDemo {
    public static void main(String[] args) {

        Player player1 = new Player(UUID.randomUUID().toString(), "Alice", PieceType.PIECE_X);
        Player player2 = new Player(UUID.randomUUID().toString(),"Bob", PieceType.PIECE_O);

        GameController gameController = new GameController("GAME-001");

        gameController.initialiseGame(player1, player2);

        gameController.startGame();
    }
}
