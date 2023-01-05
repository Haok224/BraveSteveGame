package com.haok.effects;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.Effect;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import javafx.util.Duration;

public class ChorusFruitEffect extends Effect {
    Texture texture;

    public ChorusFruitEffect() {
        super(Duration.INDEFINITE);
    }

    @Override
    public void onEnd(Entity entity) {
        entity.getViewComponent().removeChild(texture);
    }

    @Override
    public void onStart(Entity entity) {
        texture = FXGL.texture("items/hasChorus_fruit.png");
        texture.setTranslateX(-32);
        entity.getViewComponent().addChild(texture);
    }
}
