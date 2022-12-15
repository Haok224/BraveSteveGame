package org.haok.effects;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.Effect;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

public class DispenserEffect extends Effect {
    final Texture texture = FXGL.texture("items/hasDispenser.png");
    public DispenserEffect() {
        super(Duration.INDEFINITE);
    }

    @Override
    public void onEnd(@NotNull Entity entity) {
        entity.getViewComponent().removeChild(texture);
    }

    @Override
    public void onStart(@NotNull Entity entity) {
        texture.setTranslateY(32);
        texture.setTranslateX(32);
        entity.getViewComponent().addChild(texture);
    }
}
