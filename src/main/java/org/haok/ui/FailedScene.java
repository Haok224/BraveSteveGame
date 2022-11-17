package org.haok.ui;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import org.haok.Config;

public class FailedScene extends SubScene {
    Texture texture = FXGL.texture("ui/gameOver.png");
    TranslateTransition tt;

    public FailedScene() {
        texture.setLayoutX(28 * Config.CELL_SIZE / 2.0 - texture.getWidth() / 2.0);
        texture.setLayoutY(FXGL.getAppHeight());
        tt = new TranslateTransition(Duration.seconds(2.6), texture);
        tt.setFromY(0);
        tt.setToY(-(FXGL.getAppHeight() - 260));
        tt.setInterpolator(Interpolators.ELASTIC.EASE_OUT());
        tt.setOnFinished(actionEvent -> {
            FXGL.getGameController().gotoMainMenu();
        });
        getContentRoot().getChildren().add(texture);
    }

    public void onCreate() {
        texture.setTranslateY(0);
        tt.play();
    }
}
