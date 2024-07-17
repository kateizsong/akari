package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {

  private final int[][] board;
  private final int width;
  private final int height;

  public PuzzleImpl(int[][] board) {
    if (board == null || board.length == 0 || board[0].length == 0) {
      throw new IllegalArgumentException();
    }
    this.board = board;
    this.height = board.length;
    this.width = board[0].length;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (r < 0 || r >= height || c < 0 || c >= width) {
      throw new IndexOutOfBoundsException();
    }
    int cellValue = board[r][c];
    switch (cellValue) {
      case 0:
        return CellType.CLUE;

      case 1:
        return CellType.CLUE;

      case 2:
        return CellType.CLUE;

      case 3:
        return CellType.CLUE;

      case 4:
        return CellType.CLUE;

      case 5:
        return CellType.WALL;
      case 6:
        return CellType.CORRIDOR;
      default:
        throw new IllegalArgumentException();
    }
  }

  @Override
  public int getClue(int r, int c) {
    if (r < 0 || r >= getHeight() || c < 0 || c >= getWidth()) {
      throw new IndexOutOfBoundsException();
    }
    int cellValue = board[r][c];
    if (cellValue >= 0 && cellValue <= 4) {
      return cellValue;
    } else {
      throw new IllegalArgumentException();
    }
  }
}
