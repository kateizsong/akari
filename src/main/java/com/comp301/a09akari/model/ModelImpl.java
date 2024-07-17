package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private final PuzzleLibrary puzzleLibrary;
  private int index;
  private List<ModelObserver> observers;
  private List<String> lamps; // use String instead of Integer?

  public ModelImpl(PuzzleLibrary library) {
    if (library == null || library.size() == 0) {
      throw new IllegalArgumentException();
    }

    this.puzzleLibrary = library;
    this.index = 0;
    this.observers = new ArrayList<>();
    this.lamps = new ArrayList<>();
  }

  @Override
  public void addLamp(int r, int c) {
    Puzzle activePuzzle = puzzleLibrary.getPuzzle(index);
    if (activePuzzle.getCellType(r, c) == CellType.CORRIDOR) {
      lamps.add(r + "/" + c);
      notifyObservers();
    } else {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public void removeLamp(int r, int c) {
    Puzzle activePuzzle = puzzleLibrary.getPuzzle(index);
    if (activePuzzle.getCellType(r, c) == CellType.CORRIDOR) {
      for (int i = 0; i < lamps.size(); i++) {
        String lamp = lamps.get(i);
        int middle = lamp.indexOf("/");
        if (lamp.substring(0, middle).equals(String.valueOf(r))
            && lamp.substring(middle + 1).equals(String.valueOf(c))) {
          lamps.remove(i);
        }
      }
      notifyObservers();

    } else {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    Puzzle activePuzzle = puzzleLibrary.getPuzzle(index);
    if (activePuzzle.getCellType(r, c) == CellType.CORRIDOR) {
      if (isLamp(r, c)) {
        return true;
      }

      for (int i = r - 1; i >= 0; i--) {
        if (activePuzzle.getCellType(i, c) != CellType.CORRIDOR) {
          break;
        }
        if (isLamp(i, c)) {
          return true;
        }
      }

      for (int i = r + 1; i < activePuzzle.getHeight(); i++) {
        if (activePuzzle.getCellType(i, c) != CellType.CORRIDOR) {
          break;
        }
        if (isLamp(i, c)) {
          return true;
        }
      }

      for (int i = c - 1; i >= 0; i--) {
        if (activePuzzle.getCellType(r, i) != CellType.CORRIDOR) {
          break;
        }
        if (isLamp(r, i)) {
          return true;
        }
      }

      for (int i = c + 1; i < activePuzzle.getWidth(); i++) {
        if (activePuzzle.getCellType(r, i) != CellType.CORRIDOR) {
          break;
        }
        if (isLamp(r, i)) {
          return true;
        }
      }
    } else {
      throw new IllegalArgumentException();
    }
    return false;
  }

  public boolean isLamp(int r, int c) {
    Puzzle activePuzzle = puzzleLibrary.getPuzzle(index);
    if (activePuzzle.getCellType(r, c) == CellType.CORRIDOR) {
      for (int i = 0; i < lamps.size(); i++) {
        String lamp = lamps.get(i);
        int middle = lamp.indexOf("/");
        if (lamp.substring(0, middle).equals(String.valueOf(r))
            && lamp.substring(middle + 1).equals(String.valueOf(c))) {
          return true;
        }
      }

    } else {
      throw new IllegalArgumentException();
    }
    return false;
  }

  @Override
  public boolean isLampIllegal(int r, int c) { // same as isLit()?
    Puzzle activePuzzle = puzzleLibrary.getPuzzle(index);
    if (isLamp(r, c)) {

      for (int i = r - 1; i >= 0; i--) {
        if (activePuzzle.getCellType(i, c) != CellType.CORRIDOR) {
          break;
        }
        if (isLamp(i, c)) {
          return true;
        }
      }

      for (int i = r + 1; i < activePuzzle.getHeight(); i++) {
        if (activePuzzle.getCellType(i, c) != CellType.CORRIDOR) {
          break;
        }
        if (isLamp(i, c)) {
          return true;
        }
      }

      for (int i = c - 1; i >= 0; i--) {
        if (activePuzzle.getCellType(r, i) != CellType.CORRIDOR) {
          break;
        }
        if (isLamp(r, i)) {
          return true;
        }
      }

      for (int i = c + 1; i < activePuzzle.getWidth(); i++) {
        if (activePuzzle.getCellType(r, i) != CellType.CORRIDOR) {
          break;
        }
        if (isLamp(r, i)) {
          return true;
        }
      }
    } else {
      throw new IllegalArgumentException();
    }
    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return puzzleLibrary.getPuzzle(index);
  }

  @Override
  public int getActivePuzzleIndex() {
    return index;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= puzzleLibrary.size()) {
      throw new IndexOutOfBoundsException();
    }
    this.index = index;
    resetPuzzle();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return puzzleLibrary.size();
  }

  @Override
  public void resetPuzzle() {
    lamps.clear();
    notifyObservers();
  }

  @Override
  public boolean isSolved() {
    Puzzle activePuzzle = puzzleLibrary.getPuzzle(index);

    for (int row = 0; row < activePuzzle.getHeight(); row++) {
      for (int col = 0; col < activePuzzle.getWidth(); col++) {
        CellType cellType = activePuzzle.getCellType(row, col);

        if (cellType == CellType.CORRIDOR) {
          if (isLamp(row, col) && isLampIllegal(row, col)) {
            return false;
          } else if (!isLit(row, col)) {
            return false;
          }
        } else if (cellType == CellType.CLUE && !isClueSatisfied(row, col)) {
          return false;
        }
      }
    }

    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    Puzzle activePuzzle = puzzleLibrary.getPuzzle(index);

    if (activePuzzle.getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }

    int clue = activePuzzle.getClue(r, c);
    int lamps = 0;
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    for (int[] direction : directions) {
      try {
        int row = r + direction[0];
        int col = c + direction[1];
        if (activePuzzle.getCellType(row, col) == CellType.CORRIDOR && isLamp(row, col)) {
          lamps++;
        }
      } catch (IndexOutOfBoundsException e) {
      }
    }

    return clue == lamps;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observers.remove(observer);
  }

  private void notifyObservers() {
    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }
}
