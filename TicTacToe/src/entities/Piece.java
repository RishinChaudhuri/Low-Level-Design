package entities;

import enumerations.PieceType;

public class Piece
{
    private PieceType pieceType;

    public Piece(PieceType pieceType)
    {
        this.pieceType = pieceType;
    }

    public String getSymbol()
    {
        return this.pieceType == PieceType.PIECE_X ? "X" : "Y";
    }
}
