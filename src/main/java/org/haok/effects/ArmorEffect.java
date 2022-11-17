package org.haok.effects;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.Effect;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import javafx.util.Duration;

public class ArmorEffect extends Effect {
    private Texture texture;

    @Override
    public void onEnd(Entity entity) {
        entity.getViewComponent().removeChild(texture);
    }

    public ArmorEffect() {
        super(Duration.seconds(20.0));
        texture = FXGL.texture("items/hasArmor.png");
    }

    @Override
    public void onStart(Entity entity) {
        texture.setTranslateY(32);
        entity.getViewComponent().addChild(texture);

    }
}
