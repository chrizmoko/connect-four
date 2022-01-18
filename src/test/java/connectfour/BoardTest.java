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

    public void testCountCells1() {
        Board board = new Board(3, 4, 1);

        assertTrue(board.countEmptyCells() == 12);
        assertTrue(board.countFilledCells() == 0);
    }

    public void testCountCells2() {
        Cell[][] cells = {
            {Cell.RED, Cell.EMPTY, Cell.YELLOW},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY}
        };
        Board board = new Board(cells.length, cells[0].length, 1);

        for (int r = 0; r < board.getNumRows(); r++) {
            for (int c = 0; c < board.getNumColumns(); c++) {
                board.setCellAt(cells[r][c], r, c);
            }
        }

        assertTrue(board.countEmptyCells() == 4);
        assertTrue(board.countFilledCells() == 2);
    }

    public void testCountCellsWithBoardChanges1() {
        Cell[][] cells = {
            {Cell.RED, Cell.EMPTY, Cell.YELLOW},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY}
        };
        Board board = new Board(cells.length, cells[0].length, 1);

        for (int r = 0; r < board.getNumRows(); r++) {
            for (int c = 0; c < board.getNumColumns(); c++) {
                board.setCellAt(cells[r][c], r, c);
            }
        }

        board.setCellAt(Cell.EMPTY, 0, 0);
        board.setCellAt(Cell.RED, 0, 2);

        assertTrue(board.countEmptyCells() == 5);
        assertTrue(board.countFilledCells() == 1);
    }

    public void testCountCellsWithBoardChanges2() {
        Cell[][] cells = {
            {Cell.RED, Cell.EMPTY, Cell.YELLOW},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY}
        };
        Board board = new Board(cells.length, cells[0].length, 1);

        for (int r = 0; r < board.getNumRows(); r++) {
            for (int c = 0; c < board.getNumColumns(); c++) {
                board.setCellAt(cells[r][c], r, c);
            }
        }

        board.setCellAt(Cell.YELLOW, 1, 0);
        board.setCellAt(Cell.EMPTY, 0, 1);
        board.setCellAt(Cell.RED, 1, 0);

        assertTrue(board.countEmptyCells() == 3);
        assertTrue(board.countFilledCells() == 3);
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
}
