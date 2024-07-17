package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.Model;

public class ClassicMvcControllerImpl implements ClassicMvcController {
  /** Handles the click action to go to the next puzzle */
  private final Model model;

  public ClassicMvcControllerImpl(Model model) {
    if (model == null) {
      throw new IllegalArgumentException();
    }
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {
    int index = model.getActivePuzzleIndex() + 1;
    if (index < model.getPuzzleLibrarySize()) {
      model.setActivePuzzleIndex(index);
    }
  }

  @Override
  public void clickPrevPuzzle() {
    int index = model.getActivePuzzleIndex() - 1;
    if (index >= 0) {
      model.setActivePuzzleIndex(index);
    }
  }

  @Override
  public void clickRandPuzzle() {
    int index = (int) (Math.random() * model.getPuzzleLibrarySize());
    model.setActivePuzzleIndex(index);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (model.isLamp(r, c)) {
      model.removeLamp(r, c);
    } else {
      model.addLamp(r, c);
    }
  }
}
