package com.haok.effects;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.Effect;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

public class PearlEffect extends Effect {
    Texture texture;

    public PearlEffect() {
        super(Duration.INDEFINITE);
    }

    @Override
    public void onEnd(@NotNull Entity entity) {
        entity.getViewComponent().removeChild(texture);
        entity.setZIndex(0);
        FXGL.getGameScene().setCursor(FXGL.image("ui/cursor.png"), new Point2D(0, 0));
    }

    @Override
    public void onStart(@NotNull Entity entity) {
        FXGL.getGameScene().setCursor(FXGL.image("ui/moy.png"), new Point2D(12, 12));
        texture = FXGL.texture("items/hasPearl.png");
        texture.setTranslateX(-32);
        texture.setTranslateY(-32);
        entity.getViewComponent().addChild(texture);
        entity.setZIndex(2000);
    }
}
