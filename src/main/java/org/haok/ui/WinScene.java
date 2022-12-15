package org.haok.ui;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import org.haok.BraveSteveApp;
import org.haok.Config;

public class WinScene extends SubScene {
    final Texture texture = FXGL.texture("ui/gameWin.png");
    final TranslateTransition tt;
    final PauseTransition pt;

    public WinScene(boolean needNextLevel) {
        texture.setLayoutX(28 * Config.CELL_SIZE / 2.0 - texture.getWidth() / 2.0);
        texture.setLayoutY(FXGL.getAppHeight());
        tt = new TranslateTransition(Duration.seconds(2.6), texture);
        tt.setFromY(0);
        tt.setToY(-(FXGL.getAppHeight() - 260));
        tt.setInterpolator(Interpolators.ELASTIC.EASE_OUT());
        getContentRoot().getChildren().add(texture);
        pt = new PauseTransition(Duration.seconds(2));
        pt.setOnFinished(actionEvent -> {
            FXGL.inc("level", 1);
            if (needNextLevel){
                FXGL.<BraveSteveApp>getAppCast().startLevel();
            }
        });
        tt.setOnFinished(actionEvent -> FXGL.getSceneService().popSubScene());
    }

    public void onCreate() {
        texture.setTranslateY(0);
        tt.play();
        pt.play();
    }
}
