package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcControllerImpl;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class MenuView implements FXComponent {
  private final ClassicMvcControllerImpl controller;

  public MenuView(ClassicMvcControllerImpl controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    HBox layout = new HBox();
    layout.getStyleClass().add("menu");
    Button previous = new Button("\u25C0");
    previous.setOnAction(
        (ActionEvent event) -> {
          controller.clickPrevPuzzle();
        });
    layout.getChildren().add(previous);
    Button random = new Button("New"); // NEW

    random.setOnAction( // RANDOM
        (ActionEvent event) -> {
          controller.clickRandPuzzle();
        });
    layout.getChildren().add(random);
    Button reset = new Button("Reset"); // RESET
    reset.setOnAction(
        (ActionEvent event) -> {
          controller.clickResetPuzzle();
        });
    layout.getChildren().add(reset);

    Button next = new Button("\u25B6"); // NEXT
    next.setOnAction(
        (ActionEvent event) -> {
          controller.clickNextPuzzle();
        });
    layout.getChildren().add(next);

    return layout;
  }
}
