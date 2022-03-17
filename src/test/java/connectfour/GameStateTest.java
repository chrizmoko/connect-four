package connectfour;

import connectfour.core.GameState;
import connectfour.core.Board;
import connectfour.core.Cell;
import connectfour.core.Player;

import java.util.Arrays;

import junit.framework.TestCase;

public final class GameStateTest extends TestCase {
    public void testGameStateCreation() {
        Board board = new Board(4, 3, 2);
        Player[] players = new Player[]{new Player(null, Cell.YELLOW)};
        GameState gs = new GameState(board, players, 0);

        assertTrue(gs.getBoard().getNumRows() == 4);
        assertTrue(gs.getBoard().getNumColumns() == 3);
        assertTrue(gs.getBoard().getMatchLength() == 2);
        assertTrue(gs.getTurnNumber() == 0);
        assertTrue(Arrays.equals(gs.getAllPlayers(), players));
        assertTrue(gs.getCurrentPlayer().getCell() == Cell.YELLOW);
    }

    public void testMakeMove() {
        Board board = new Board(2, 2, 2);
        Player[] players = new Player[]{new Player(null, Cell.RED), new Player(null, Cell.YELLOW)};
        GameState gs = new GameState(board, players, 0);

        assertTrue(gs.getBoard().getCellAt(1, 0) == Cell.EMPTY);
        assertTrue(gs.getCurrentPlayer().getCell() == Cell.RED);
        assertTrue(gs.getTurnNumber() == 0);

        gs.makeMove(0);

        assertTrue(gs.getBoard().getCellAt(1, 0) == Cell.RED);
        assertTrue(gs.getCurrentPlayer().getCell() == Cell.YELLOW);
        assertTrue(gs.getTurnNumber() == 1);
    }

    public void testMakeMoveCyclesPlayers() {
        Board board = new Board(3, 3, 3);
        Player[] players = new Player[]{new Player(null, Cell.RED), new Player(null, Cell.YELLOW)};
        GameState gs = new GameState(board, players, 0);

        assertTrue(gs.getCurrentPlayer().getCell() == Cell.RED);
        
        gs.makeMove(0);
        assertTrue(gs.getCurrentPlayer().getCell() == Cell.YELLOW);

        gs.makeMove(1);
        assertTrue(gs.getCurrentPlayer().getCell() == Cell.RED);
        
        gs.makeMove(2);
        assertTrue(gs.getCurrentPlayer().getCell() == Cell.YELLOW);
    }

    public void testInitialGameStateCopy() {
        Board board = new Board(2, 2, 2);
        Player[] players = new Player[]{new Player(null, Cell.RED), new Player(null, Cell.YELLOW)};
        GameState gs = new GameState(board, players, 0);
        GameState gsCopy = new GameState(gs);

        gsCopy.makeMove(0);

        assertTrue(gs.getBoard().getCellAt(1, 0) == Cell.EMPTY);
        assertTrue(gs.getCurrentPlayer().getCell() == Cell.RED);
        assertTrue(gs.getTurnNumber() == 0);

        assertTrue(gsCopy.getBoard().getCellAt(1, 0) == Cell.RED);
        assertTrue(gsCopy.getCurrentPlayer().getCell() == Cell.YELLOW);
        assertTrue(gsCopy.getTurnNumber() == 1);
    }

    public void testInvalidMove1() {
        Board board = new Board();
        Player[] players = new Player[]{new Player(null, Cell.RED), new Player(null, Cell.YELLOW)};
        GameState gs = new GameState(board, players, 0);

        assertTrue(!gs.isValidMove(-1));
        assertTrue(gs.isValidMove(0));
        assertTrue(gs.isValidMove(6));
        assertTrue(!gs.isValidMove(100));
    }

    public void testInvalidMove2() {
        Board board = new Board(1, 101, 1);
        Player[] players = new Player[]{new Player(null, Cell.RED)};
        GameState gs = new GameState(board, players, 0);

        assertTrue(!gs.isValidMove(-1));
        assertTrue(gs.isValidMove(0));
        assertTrue(gs.isValidMove(100));
        assertTrue(!gs.isValidMove(101));
    }

    public void testGameCompletionIsMatch() {
        Board board = new Board(2, 2, 2);
        Player[] players = new Player[]{new Player(null, Cell.RED), new Player(null, Cell.YELLOW)};
        GameState gs = new GameState(board, players, 0);

        gs.makeMove(0);
        gs.makeMove(1);
        gs.makeMove(0);

        assertTrue(gs.isGameCompleted());
        assertTrue(gs.getCurrentPlayer().getCell() == Cell.RED);
    }

    public void testGameCompletionIsDraw() {
        Board board = new Board(3, 3, 3);
        Player[] players = new Player[]{new Player(null, Cell.RED), new Player(null, Cell.YELLOW)};
        GameState gs = new GameState(board, players, 0);

        gs.makeMove(0); //
        gs.makeMove(1); //    
        gs.makeMove(2); //   y
        gs.makeMove(1); // r y r

        gs.makeMove(0); //
        gs.makeMove(0); // y   y
        gs.makeMove(2); // r y r
        gs.makeMove(2); // r y r

        gs.makeMove(1);

        Board b = gs.getBoard();
        StringBuilder output = new StringBuilder();
        for (int r = 0; r < b.getNumRows(); r++) {
            for (int c = 0; c < b.getNumColumns(); c++) {
                switch (b.getCellAt(r, c)) {
                case RED:
                    output.append('r');
                    break;
                case YELLOW:
                    output.append('y');
                    break;
                default:
                    break;
                }
            }
            output.append(' ');
        }

        assertTrue(gs.isGameCompleted());
        assertTrue(gs.getCurrentPlayer() == null);
    }
}
