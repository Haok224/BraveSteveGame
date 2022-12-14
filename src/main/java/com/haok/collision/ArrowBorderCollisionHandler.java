package com.haok.collision;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.haok.enums.GameType;

public class ArrowBorderCollisionHandler extends CollisionHandler {
    public ArrowBorderCollisionHandler() {
        super(GameType.ARROW, GameType.BORDER);
    }

    @Override
    protected void onCollisionBegin(Entity arrow, Entity border) {
        arrow.removeFromWorld();
    }
}
