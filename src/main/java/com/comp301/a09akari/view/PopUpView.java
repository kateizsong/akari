package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcControllerImpl;
import com.comp301.a09akari.model.Model;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class PopUpView implements FXComponent {
  private final ClassicMvcControllerImpl controller;
  private final Model model;

  public PopUpView(ClassicMvcControllerImpl controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    StackPane layout = new StackPane();
    layout.setAlignment(Pos.TOP_CENTER);
    layout.getStyleClass().add("popup");
    Label message = new Label("");
    if (model.isSolved()) {
      message.setText("Good Job Puzzle Solved!");
      Image img = new Image("confetti.gif");
      ImageView view = new ImageView(img);
      view.setFitHeight(30);
      view.setFitWidth(16);
      view.setScaleX(1);
      view.setScaleY(1);
      message.setGraphic(view);
      view.setPreserveRatio(true);
    }
    layout.getChildren().add(message);

    return layout;
  }
}
