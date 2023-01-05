package com.haok.ui;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import com.haok.Config;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class FailedScene extends SubScene {
    final Texture texture = FXGL.texture("ui/gameOver.png");
    final TranslateTransition tt;

    public FailedScene() {
        texture.setLayoutX(28 * Config.CELL_SIZE / 2.0 - texture.getWidth() / 2.0);
        texture.setLayoutY(FXGL.getAppHeight());
        tt = new TranslateTransition(Duration.seconds(2.6), texture);
        tt.setFromY(0);
        tt.setToY(-(FXGL.getAppHeight() - 260));
        tt.setInterpolator(Interpolators.ELASTIC.EASE_OUT());
        tt.setOnFinished(actionEvent -> FXGL.getGameController().gotoMainMenu());
        getContentRoot().getChildren().add(texture);
    }

    public void onCreate() {
        texture.setTranslateY(0);
        tt.play();
    }
}
