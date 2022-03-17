package connectfour;

import connectfour.core.Board;
import connectfour.core.Cell;

import junit.framework.TestCase;

public final class BoardTest extends TestCase {
    public void testDefaultBoardDimensions() {
        Board board = new Board();

        assertTrue(board.getNumRows() == 6);
        assertTrue(board.getNumColumns() == 7);
    }

    public void testCustomBoardDimensions1() {
        Board board = new Board(2, 5, 1);

        assertTrue(board.getNumRows() == 2);
        assertTrue(board.getNumColumns() == 5);
    }

    public void testCustomBoardDimensions2() {
        Board board = new Board(18, 32, 1);
    
        assertTrue(board.getNumRows() == 18);
        assertTrue(board.getNumColumns() == 32);
    }

    public void testDefaultBoardMatchLength() {
        Board board = new Board();

        assertTrue(board.getMatchLength() == 4);
    }

    public void testCustomBoardMatchLength() {
        Board board = new Board(10, 9, 8);

        assertTrue(board.getMatchLength() == 8);
    }

    public void testSettingAndGettingCells() {
        Cell[][] cells = {
            {Cell.RED, Cell.EMPTY, Cell.YELLOW, Cell.YELLOW, Cell.RED},
            {Cell.EMPTY, Cell.YELLOW, Cell.EMPTY, Cell.RED, Cell.RED},
            {Cell.YELLOW, Cell.RED, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY}
        };
        Board board = new Board(cells.length, cells[0].length, 1);

        for (int r = 0; r < cells.length; r++) {
            for (int c = 0; c < cells[0].length; c++) {
                board.setCellAt(cells[r][c], r, c);
            }
        }

        for (int r = 0; r < cells.length; r++) {
            for (int c = 0; c < cells[0].length; c++) {
                assertTrue(board.getCellAt(r, c) == cells[r][c]);
            }
        }
    }

    public void testInitialEmptyBoard1() {
        Board board = new Board();

        for (int r = 0; r < board.getNumRows(); r++) {
            for (int c = 0; c < board.getNumColumns(); c++) {
                assertTrue(board.getCellAt(r, c) == Cell.EMPTY);
            }
        }
    }

    public void testInitialEmptyBoard2() {
        Board board = new Board(20, 2, 1);

        for (int r = 0; r < board.getNumRows(); r++) {
            for (int c = 0; c < board.getNumColumns(); c++) {
                assertTrue(board.getCellAt(r, c) == Cell.EMPTY);
            }
        }
    }

    public void testBoardCopy() {
        Board board = new Board();
        Board boardCopy = new Board(board);

        boardCopy.setCellAt(Cell.RED, 0, 0);

        assertTrue(board.getCellAt(0, 0) == Cell.EMPTY);
        assertTrue(boardCopy.getCellAt(0, 0) == Cell.RED);
    }

    public void testIsColumnFull1() {
        Board board = new Board(2, 1, 1);
        board.dropChip(Cell.RED, 0);
        board.dropChip(Cell.RED, 0);

        assertTrue(board.isColumnFull(0));
    }

    public void testIsColumnFull2() {
        Board board = new Board(2, 1, 1);
        board.dropChip(Cell.RED, 0);

        assertTrue(!board.isColumnFull(0));
    }

    public void testIsColumnEmpty1() {
        Board board = new Board(2, 1, 1);

        assertTrue(board.isColumnEmpty(0));
    }

    public void testIsColumnEmpty2() {
        Board board = new Board(2, 1, 1);
        board.dropChip(Cell.RED, 0);

        assertTrue(!board.isColumnEmpty(0));
    }

    public void testDropChip1() {
        Board board = new Board(3, 1, 1);
        board.dropChip(Cell.RED, 0);
        board.dropChip(Cell.YELLOW, 0);

        assertTrue(board.getCellAt(0, 0) == Cell.EMPTY);
        assertTrue(board.getCellAt(1, 0) == Cell.YELLOW);
        assertTrue(board.getCellAt(2, 0) == Cell.RED);
    }

    public void testDropChip2() {
        Board board = new Board(3, 1, 1);
        board.dropChip(Cell.RED, 0);
        board.dropChip(Cell.EMPTY, 0);
        board.dropChip(Cell.YELLOW, 0);

        assertTrue(board.getCellAt(0, 0) == Cell.EMPTY);
        assertTrue(board.getCellAt(1, 0) == Cell.YELLOW);
        assertTrue(board.getCellAt(2, 0) == Cell.RED);
    }

    public void testDropChip3() {
        Board board = new Board(3, 1, 1);
        board.dropChip(Cell.EMPTY, 0);
        board.dropChip(Cell.EMPTY, 0);
        board.dropChip(Cell.EMPTY, 0);

        assertTrue(board.getCellAt(0, 0) == Cell.EMPTY);
        assertTrue(board.getCellAt(1, 0) ==Cell.EMPTY);
        assertTrue(board.getCellAt(2, 0) == Cell.EMPTY);
    }

    public void testDropChipInNotFullColumn() {
        Board board = new Board(1, 1, 1);
        
        assertTrue(board.dropChip(Cell.RED, 0));
    }

    public void testDropChipInFullColumn() {
        Board board = new Board(1, 1, 1);
        board.dropChip(Cell.RED, 0);

        assertTrue(!board.dropChip(Cell.YELLOW, 0));
    }

    public void testPickupChip1() {
        Board board = new Board(3, 1, 1);

        assertTrue(!board.pickupChip(0));
    }

    public void testPickupChip2() {
        Board board = new Board(3, 1, 1);
        board.dropChip(Cell.RED, 0);
        board.dropChip(Cell.YELLOW, 0);

        assertTrue(board.pickupChip(0));
        assertTrue(board.getCellAt(0, 0) == Cell.EMPTY);
        assertTrue(board.getCellAt(1, 0) == Cell.EMPTY);
        assertTrue(board.getCellAt(2, 0) == Cell.RED);
    }

    public void testLowestEmptyRow1() {
        Board board = new Board(5, 1, 1);

        assertTrue(board.getLowestEmptyRow(0) == 4);
    }

    public void testLowestEmptyRow2() {
        Board board = new Board(5, 1, 1);
        for (int i = 0; i < board.getNumRows(); i++) {
            board.dropChip(Cell.RED, 0);
        }

        assertTrue(board.getLowestEmptyRow(0) == -1);
    }

    public void testLowestEmptyRow3() {
        Board board = new Board(5, 1, 1);
        for (int i = 0; i < 3; i++) {
            board.dropChip(Cell.RED, 0);
        }

        assertTrue(board.getLowestEmptyRow(0) == 1);
    }

    public void testHasDraw() {
        Cell[][] cells = {
            {Cell.RED, Cell.YELLOW, Cell.RED},
            {Cell.YELLOW, Cell.RED, Cell.YELLOW},
            {Cell.YELLOW, Cell.RED, Cell.YELLOW}
        };
        Board board = new Board(cells.length, cells[0].length, 3);

        for (int r = 0; r < cells.length; r++) {
            for (int c = 0; c < cells[0].length; c++) {
                board.setCellAt(cells[r][c], r, c);
            }
        }

        assertTrue(board.hasDraw());
        assertTrue(!board.hasConnectFour());
    }
}
