package com.c3il.game_of_life;

import com.c3il.game_of_life.obj.Monde;
import com.c3il.game_of_life.obj.Position;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.stream.IntStream;

// Position immutable


// Plateau immutable basé sur Map


public class JeuDeLaVieFX extends Application {

    private static final int TAILLE = 400;
    private static final int CELL_SIZE = 20;

    private ScrollPane scrollPane;
    private Monde monde;
    private Rectangle[][] rects;

    @Override
    public void start(Stage stage) {
        monde = new Monde(TAILLE, TAILLE);

        GridPane grid = new GridPane();
        grid.setMaxWidth(Double.MAX_VALUE);
        grid.setMaxHeight(Double.MAX_VALUE);

        this.scrollPane = new ScrollPane(grid);
        this.scrollPane.setFitToHeight(false);
        this.scrollPane.setFitToWidth(false);
        this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.scrollPane.setPadding(new Insets(10));

        rects = new Rectangle[TAILLE][TAILLE];

        for (int y = 0; y < TAILLE; y++) {
            for (int x = 0; x < TAILLE; x++) {
                Rectangle r = new Rectangle(CELL_SIZE, CELL_SIZE);
                r.setStroke(Color.LIGHTGRAY);
                rects[x][y] = r;
                grid.add(r, x, y);

                int fx = x;
                int fy = y;
                r.setOnMouseClicked(e -> {
                    // A compléter : quand on clique sur une cellule, on change son état
                    monde.changerEtat(new Position(fx, fy));
                    updateView();
                });
            }
        }

        updateView();

        final Button button = new Button("Go");
        button.setMaxSize(CELL_SIZE*2, CELL_SIZE*2);
        grid.add(button, TAILLE, TAILLE);

        button.setOnMouseClicked(event -> {
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), e -> {
                // A compléter : avec ce qui doit se passer à chaque itération du jeu
                monde.prochaineGeneration();
                updateView();
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();

            updateView();
        });

        stage.setScene(new Scene(this.scrollPane, 1080, 920));
        stage.setTitle("Jeu de la vie");
        stage.show();
    }

    private void updateView() {
        // A compléter, en une seule ligne de code, pour mettre à jour l'affichage avec l'état actuel de la grille
        this.monde.getGrille().keySet()
                .stream()
                .filter(p -> this.monde.getGrille().get(p))
                .forEach(p -> this.rects[p.x()][p.y()].setFill(Color.WHITE));
        this.monde.getGrille().keySet()
                .stream()
                .filter(p -> !this.monde.getGrille().get(p))
                .forEach(p -> this.rects[p.x()][p.y()].setFill(Color.BLACK));
    }

    public static void main(String[] args) {
        launch();
    }
}
