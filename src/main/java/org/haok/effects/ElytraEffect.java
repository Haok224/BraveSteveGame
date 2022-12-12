package org.haok.effects;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.Effect;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import org.haok.Config;

public class ElytraEffect extends Effect {
    Texture texture;

    public ElytraEffect() {
        super(Config.ELYTRA_TIME);
    }

    @Override
    public void onEnd(Entity entity) {
        entity.getViewComponent().removeChild(texture);
        entity.setZIndex(0);
    }

    @Override
    public void onStart(Entity entity) {
        texture = FXGL.texture("items/hasElytra.png");
        texture.setTranslateX(32);
        entity.getViewComponent().addChild(texture);
        entity.setZIndex(2000);
    }
}
