package entities;

import enumerations.PieceType;

public class Board
{
    public static final int BOARD_SIZE = 3;
    private final Cell [][] board = new Cell[BOARD_SIZE][BOARD_SIZE];

    public void initialiseBoard()
    {
        for(int i=0; i<BOARD_SIZE; i++)
        {
            for(int j=0; j<BOARD_SIZE; j++)
            {
                this.board[i][j] = new Cell(i, j, null);
            }
        }
    }

    public void setCell(int x, int y, PieceType pieceType)
    {
        Piece piece = new Piece(pieceType);
        this.board[x][y].setPiece(piece);
    }

    public Cell getCell(int x, int y)
    {
        return this.board[x][y];
    }

    public void displayBoard()
    {
        for(int i=0; i<BOARD_SIZE; i++)
        {
            for(int j=0; j<BOARD_SIZE; j++)
            {
                IO.print((this.board[i][j].getPiece() == null ? "-" : this.board[i][j].getPiece().getSymbol()) + "\t");
            }
            IO.println();
        }
        IO.println("__________________________________________________________________________________________");
    }

    public boolean isBoardFilled()
    {
        for(int i=0; i<BOARD_SIZE; i++)
        {
            for(int j=0; j<BOARD_SIZE; j++)
            {
                if(this.board[i][j].getPiece() == null)
                    return false;
            }
        }
        return true;
    }
}
