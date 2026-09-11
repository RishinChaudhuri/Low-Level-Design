package strategies;

import entities.Board;
import entities.Piece;

public class WinnerCheckStrategy implements Strategy
{
    @Override
    public boolean check(int x, int y, Board board)
    {
        Piece piece = board.getCell(x, y).getPiece();

        boolean rowCheck = rowWiseCheck(x, piece, board);
        boolean colCheck = columnWiseCheck(y, piece, board);
        boolean diagCheck = diagonalWiseCheck(x, y, piece, board);

        return rowCheck || colCheck || diagCheck;
    }

    public static boolean rowWiseCheck(int row, Piece piece, Board board)
    {
        for(int i=0; i<Board.BOARD_SIZE; i++)
        {
            Piece curr = board.getCell(row, i).getPiece();
            if( curr == null || !curr.getSymbol().equalsIgnoreCase(piece.getSymbol()))
            {
                return false;
            }
        }
        return true;
    }

    public static boolean columnWiseCheck(int col, Piece piece, Board board)
    {
        for(int i=0; i<Board.BOARD_SIZE; i++)
        {
            Piece curr = board.getCell(i, col).getPiece();
            if( curr == null || !curr.getSymbol().equalsIgnoreCase(piece.getSymbol()))
            {
                return false;
            }
        }
        return true;
    }

    public static boolean diagonalWiseCheck(int row, int col, Piece piece, Board board)
    {
        if(!(row == col || ((row + col) == Board.BOARD_SIZE-1)))
            return false;

        boolean flag = true;
        if(row == col)
        {
            for(int i=0; i<Board.BOARD_SIZE; i++)
            {
                Piece curr = board.getCell(i, i).getPiece();
                if( curr == null || !curr.getSymbol().equalsIgnoreCase(piece.getSymbol()))
                {
                    flag = false;
                    break;
                }
            }
            if(flag)
                return true;
        }
        flag = true;
        if(((row + col) == Board.BOARD_SIZE-1))
        {
            for(int i=0; i<Board.BOARD_SIZE; i++)
            {
                Piece curr = board.getCell(i, Board.BOARD_SIZE-i-1).getPiece();
                if( curr == null || !curr.getSymbol().equalsIgnoreCase(piece.getSymbol()))
                {
                    flag = false;
                    break;
                }
            }
            if(flag)
                return true;
        }

        return false;
    }
}
