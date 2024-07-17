package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcControllerImpl;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.Puzzle;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PuzzleView implements FXComponent {
  private final ClassicMvcControllerImpl controller;
  private final Model model;

  public PuzzleView(ClassicMvcControllerImpl controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    GridPane layout = new GridPane();
    layout.getStyleClass().add("grid");
    Puzzle puzzle = model.getActivePuzzle();
    for (int i = 0; i < puzzle.getHeight(); i++) {
      for (int x = 0; x < puzzle.getWidth(); x++) {
        Button button;
        if (puzzle.getCellType(i, x) == CellType.CORRIDOR) {
          button = new Button("");
          button.setPrefSize(60, 60);
          if (model.isLamp(i, x)) {
            Image img = new Image("light-bulb.png");
            ImageView view = new ImageView(img);
            view.setFitHeight(30);
            view.setFitWidth(16);
            view.setScaleX(1);
            view.setScaleY(1);
            button.setGraphic(view);
            view.setPreserveRatio(true);
            if (model.isLampIllegal(i, x)) { // checks  boxes
              button.getStyleClass().add("illegal");
            } else {
              button.getStyleClass().add("satisfied");
            }
          }
          if (model.isLit(i, x)) {
            button.getStyleClass().add("lit");
          }

          final int ii = i;
          final int xx = x;
          button.setOnAction(
              (ActionEvent event) -> {
                controller.clickCell(ii, xx);
              });
        } else if (puzzle.getCellType(i, x) == CellType.WALL) {
          button = new Button("");
          button.setPrefSize(60, 60);
          button.getStyleClass().add("wall");
        } else {
          button = new Button(" " + puzzle.getClue(i, x) + " ");
          button.setPrefSize(60, 60);
          button.getStyleClass().add("wall");
          if (model.isClueSatisfied(i, x)) {
            button.getStyleClass().add("satisfied");
          }
        }
        layout.add(button, x, i, 1, 1);
      }
    }

    if (model.isSolved()) {}

    return layout;
  }
}
