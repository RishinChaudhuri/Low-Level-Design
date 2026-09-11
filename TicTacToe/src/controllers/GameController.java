package controllers;

import entities.Board;
import entities.Player;
import strategies.Strategy;
import strategies.WinnerCheckStrategy;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class GameController
{
    private final Board board = new Board();
    private final Deque<Player> playerDeque = new ArrayDeque<>();
    private final String gameId;

    public GameController(String gameId)
    {
        this.gameId = gameId;
    }

    public void initialiseGame(Player player1, Player player2)
    {
        // Toss logic 1-> player1 2-> player2
        int toss = 1 + (int)Math.round(Math.random());

        if(toss == 1)
        {
            this.playerDeque.addLast(player1);
            this.playerDeque.addLast(player2);
        }
        else
        {
            this.playerDeque.addLast(player2);
            this.playerDeque.addLast(player1);
        }
        this.board.initialiseBoard();
    }

    public void startGame()
    {
        IO.println("Starting Game : " + this.gameId);
        IO.println("__________________________________________________________________________________________");

        Player winner = null;
        Strategy winningStrategy = new WinnerCheckStrategy();
        while(!this.board.isBoardFilled())
        {
            Player player = this.playerDeque.pollFirst();
            Scanner sc = new Scanner(System.in);
            IO.println("Enter the cell ...");
            IO.println("Enter X : ");
            int x = sc.nextInt();
            IO.println("Enter Y : ");
            int y = sc.nextInt();
            if(this.board.getCell(x, y).getPiece() != null)
            {
                IO.println("Cell has already been taken !! Choose some other cell");
                this.board.displayBoard();
                this.playerDeque.addFirst(player);
                continue;
            }
            else
            {
                this.board.setCell(x, y, player.getPieceType());
                this.board.displayBoard();
                if(winningStrategy.check(x, y, this.board))
                {
                    winner = player;
                    break;
                }
                else
                {
                    this.playerDeque.addLast(player);
                }
            }
        }

        if(winner != null)
        {
            IO.println("Winner is Player : " + winner.getName());
        }
        else
        {
            IO.println("Game was a tie");
        }
    }


}
