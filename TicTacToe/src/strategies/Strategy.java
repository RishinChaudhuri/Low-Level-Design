package strategies;

import entities.Board;

public interface Strategy
{
    public abstract boolean check(int x, int y, Board board);
}
