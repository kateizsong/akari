package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.ClassicMvcControllerImpl;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    Puzzle puzzle1 = new PuzzleImpl(SamplePuzzles.PUZZLE_01);
    Puzzle puzzle2 = new PuzzleImpl(SamplePuzzles.PUZZLE_02);
    Puzzle puzzle3 = new PuzzleImpl(SamplePuzzles.PUZZLE_03);
    Puzzle puzzle4 = new PuzzleImpl(SamplePuzzles.PUZZLE_04);
    Puzzle puzzle5 = new PuzzleImpl(SamplePuzzles.PUZZLE_05);

    PuzzleLibrary puzzleLibrary = new PuzzleLibraryImpl();

    puzzleLibrary.addPuzzle(puzzle1);
    puzzleLibrary.addPuzzle(puzzle2);
    puzzleLibrary.addPuzzle(puzzle3);
    puzzleLibrary.addPuzzle(puzzle4);
    puzzleLibrary.addPuzzle(puzzle5);

    Model model = new ModelImpl(puzzleLibrary);

    ClassicMvcControllerImpl controller = new ClassicMvcControllerImpl(model);

    PuzzleView puzzleView = new PuzzleView(controller, model);
    MenuView menuView = new MenuView(controller);
    PopUpView popupview = new PopUpView(controller, model);

    StackPane stackPane = new StackPane();
    stackPane.getStyleClass().add("pink");
    VBox layout = new VBox();

    Scene scene = new Scene(layout);

    scene.getStylesheets().add("main.css");

    stage.setScene(scene);

    stage.sizeToScene();

    model.addObserver( // add OBSERVERS TO MODEL
        (Model m) -> {
          final VBox layout2 = new VBox();
          StackPane newStackPane = new StackPane();
          Label updatePuzzle =
              new Label(
                  "Puzzle #"
                      + (model.getActivePuzzleIndex() + 1)
                      + " of "
                      + model.getPuzzleLibrarySize());
          newStackPane.getChildren().add(updatePuzzle);
          newStackPane.getStyleClass().add("text");

          layout2.getChildren().add(newStackPane);
          layout2.getChildren().add(menuView.render());
          layout2.getChildren().add(puzzleView.render());
          layout2.getChildren().add(popupview.render());

          scene.setRoot(layout2);
          stage.sizeToScene();
        });

    Label puzzleNumber =
        new Label(
            "Puzzle #"
                + (model.getActivePuzzleIndex() + 1)
                + " of "
                + model.getPuzzleLibrarySize());
    stackPane.getChildren().add(puzzleNumber);
    stackPane.getStyleClass().add("text");

    layout.getChildren().add(stackPane);

    layout.getChildren().add(menuView.render());
    layout.getChildren().add(puzzleView.render());
    layout.getChildren().add(popupview.render());

    stage.setTitle("Akari Game!");
    stage.show();
  }
}
