package entities;

import enumerations.PieceType;

public class Player
{
    private final String id;
    private final String name;
    private final PieceType pieceType;

    public Player(String id, String name, PieceType pieceType)
    {
        this.id = id;
        this.name = name;
        this.pieceType = pieceType;
    }
    public String getId()
    {
        return this.id;
    }
    public String getName()
    {
        return this.name;
    }
    public PieceType getPieceType()
    {
        return this.pieceType;
    }
}
