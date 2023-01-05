package com.haok.effects;

import com.almasb.fxgl.dsl.components.Effect;
import com.almasb.fxgl.entity.Entity;
import com.haok.Config;
import org.jetbrains.annotations.NotNull;

public class FreezeEffect extends Effect {
    public FreezeEffect() {
        super(Config.PLAYER_FREEZY_TIME);
    }

    @Override
    public void onEnd(@NotNull Entity entity) {

    }

    @Override
    public void onStart(@NotNull Entity entity) {

    }
}
