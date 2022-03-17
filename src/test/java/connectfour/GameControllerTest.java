package connectfour;

import connectfour.ai.HumanPlayer;

import connectfour.core.Board;
import connectfour.core.Cell;
import connectfour.core.ConnectFourException;
import connectfour.core.GameController;
import connectfour.core.Player;

import junit.framework.TestCase;

public final class GameControllerTest extends TestCase {
    public void testInitialGameController() throws ConnectFourException {
        Board board = new Board();
        Player[] players = new Player[] {
            new Player(new HumanPlayer(), Cell.RED),
            new Player(new HumanPlayer(), Cell.YELLOW)
        };

        GameController controller = new GameController(board, players);

        assertTrue(controller.getTotalTurns() == 0);
        assertTrue(!controller.isGameCompleted());
    }

    public void testMoveForward() throws ConnectFourException {
        HumanPlayer ai = new HumanPlayer();

        Board board = new Board();
        Player[] players = new Player[] {
            new Player(ai, Cell.RED)
        };

        GameController controller = new GameController(board, players);

        ai.setNextMove(0);
        
        controller.moveForward();

        Board currentBoard = controller.getStateAtTurn(controller.getTotalTurns()).getBoard();
        assertTrue(currentBoard.getCellAt(currentBoard.getNumRows() - 1, 0) == Cell.RED);
    }

    public void testMoveForwardMany() throws ConnectFourException {
        HumanPlayer redAi =  new HumanPlayer();
        HumanPlayer yellowAi = new HumanPlayer();

        Board board = new Board();
        Player[] players = new Player[] {
            new Player(redAi, Cell.RED),
            new Player(yellowAi, Cell.YELLOW)
        };

        GameController controller = new GameController(board, players);
        int rows = board.getNumRows();
        
        redAi.setNextMove(0);
        controller.moveForward();

        yellowAi.setNextMove(0);
        controller.moveForward();

        redAi.setNextMove(5);
        controller.moveForward();

        yellowAi.setNextMove(6);
        controller.moveForward();

        redAi.setNextMove(6);
        controller.moveForward();

        board = controller.getStateAtTurn(controller.getTotalTurns()).getBoard();

        assertTrue(board.getCellAt(rows - 1, 0) == Cell.RED);
        assertTrue(board.getCellAt(rows - 2, 0) == Cell.YELLOW);
        assertTrue(board.getCellAt(rows - 1, 5) == Cell.RED);
        assertTrue(board.getCellAt(rows - 1, 6) == Cell.YELLOW);
        assertTrue(board.getCellAt(rows - 2, 6) == Cell.RED);

        assertTrue(controller.getTotalTurns() == 5);
    }

    public void testGetStateArbitrarily() throws ConnectFourException {
        HumanPlayer ai = new HumanPlayer();

        Board board = new Board();
        Player[] players = new Player[] {
            new Player(ai, Cell.RED),
        };

        GameController controller = new GameController(board, players);
        int rows = board.getNumRows();

        int[] moves = new int[] {3, 6, 3, 1, 0, 2};
        for (int i = 0; i < moves.length; i++) {
            ai.setNextMove(moves[i]);
            controller.moveForward();
        }

        board = controller.getStateAtTurn(3).getBoard();
        
        assertTrue(board.getCellAt(rows - 1, 3) == Cell.RED);
        assertTrue(board.getCellAt(rows - 1, 6) == Cell.RED);
        assertTrue(board.getCellAt(rows - 2, 3) == Cell.RED);
        assertTrue(board.getCellAt(rows - 1, 1) == Cell.EMPTY);
        assertTrue(board.getCellAt(rows - 1, 0) == Cell.EMPTY);
        assertTrue(board.getCellAt(rows - 1, 2) == Cell.EMPTY);

        board = controller.getStateAtTurn(5).getBoard();

        assertTrue(board.getCellAt(rows - 1, 3) == Cell.RED);
        assertTrue(board.getCellAt(rows - 1, 6) == Cell.RED);
        assertTrue(board.getCellAt(rows - 2, 3) == Cell.RED);
        assertTrue(board.getCellAt(rows - 1, 1) == Cell.RED);
        assertTrue(board.getCellAt(rows - 1, 0) == Cell.RED);
        assertTrue(board.getCellAt(rows - 1, 2) == Cell.EMPTY);

        board = controller.getStateAtTurn(1).getBoard();

        assertTrue(board.getCellAt(rows - 1, 3) == Cell.RED);
        assertTrue(board.getCellAt(rows - 1, 6) == Cell.EMPTY);
        assertTrue(board.getCellAt(rows - 2, 3) == Cell.EMPTY);
        assertTrue(board.getCellAt(rows - 1, 1) == Cell.EMPTY);
        assertTrue(board.getCellAt(rows - 1, 0) == Cell.EMPTY);
        assertTrue(board.getCellAt(rows - 1, 2) == Cell.EMPTY);

        board = controller.getStateAtTurn(1).getBoard();

        assertTrue(board.getCellAt(rows - 1, 3) == Cell.RED);
        assertTrue(board.getCellAt(rows - 1, 6) == Cell.EMPTY);
        assertTrue(board.getCellAt(rows - 2, 3) == Cell.EMPTY);
        assertTrue(board.getCellAt(rows - 1, 1) == Cell.EMPTY);
        assertTrue(board.getCellAt(rows - 1, 0) == Cell.EMPTY);
        assertTrue(board.getCellAt(rows - 1, 2) == Cell.EMPTY);
    }

    public void testMoveForwardFrom() throws ConnectFourException {
        HumanPlayer redAi = new HumanPlayer();
        HumanPlayer yellowAi = new HumanPlayer();

        Board board = new Board();
        Player[] players = new Player[] {
            new Player(redAi, Cell.RED),
            new Player(yellowAi, Cell.YELLOW)
        };

        GameController controller = new GameController(board, players);
        int rows = board.getNumRows();

        for (int i = 0; i < board.getNumColumns(); i++) {
            if (i % players.length == 0) {
                redAi.setNextMove(i);
            } else {
                yellowAi.setNextMove(i);
            }
            controller.moveForward();
        }

        assertTrue(controller.getTotalTurns() == 7);
        

        redAi.setNextMove(6);
        controller.moveForwardFrom(4);
        
        assertTrue(controller.getTotalTurns() == 5);

        board = controller.getStateAtTurn(4).getBoard();

        assertTrue(board.getCellAt(rows - 1, 0) == Cell.RED);
        assertTrue(board.getCellAt(rows - 1, 1) == Cell.YELLOW);
        assertTrue(board.getCellAt(rows - 1, 2) == Cell.RED);
        assertTrue(board.getCellAt(rows - 1, 3) == Cell.YELLOW);
        assertTrue(board.getCellAt(rows - 1, 4) == Cell.EMPTY);
        assertTrue(board.getCellAt(rows - 1, 5) == Cell.EMPTY);
        assertTrue(board.getCellAt(rows - 1, 6) == Cell.EMPTY);
        
        board = controller.getStateAtTurn(5).getBoard();

        assertTrue(board.getCellAt(rows - 1, 0) == Cell.RED);
        assertTrue(board.getCellAt(rows - 1, 1) == Cell.YELLOW);
        assertTrue(board.getCellAt(rows - 1, 2) == Cell.RED);
        assertTrue(board.getCellAt(rows - 1, 3) == Cell.YELLOW);
        assertTrue(board.getCellAt(rows - 1, 4) == Cell.EMPTY);
        assertTrue(board.getCellAt(rows - 1, 5) == Cell.EMPTY);
        assertTrue(board.getCellAt(rows - 1, 6) == Cell.RED);
    }
}
