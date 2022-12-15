package org.haok.effects;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.Effect;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import javafx.util.Duration;

public class ShipEffect extends Effect {
    private final Texture texture;

    @Override
    public void onEnd(Entity entity) {
        entity.getViewComponent().removeChild(texture);
    }

    public ShipEffect() {
        super(Duration.seconds(12.0));
        texture = FXGL.texture("items/waterBreathing.png");
    }

    @Override
    public void onStart(Entity entity) {
        texture.setTranslateY(-32);
        entity.getViewComponent().addChild(texture);

    }
}
