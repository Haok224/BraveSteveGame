package org.haok.effects;

import com.almasb.fxgl.dsl.components.Effect;
import com.almasb.fxgl.entity.Entity;
import org.haok.Config;

public class FreezeEffect extends Effect {
    public FreezeEffect() {
        super(Config.PLAYER_FREEZY_TIME);
    }

    @Override
    public void onEnd(Entity entity) {

    }

    @Override
    public void onStart(Entity entity) {

    }
}
