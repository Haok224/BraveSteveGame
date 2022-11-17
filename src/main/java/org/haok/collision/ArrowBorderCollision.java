package org.haok.collision;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import org.haok.GameType;

public class ArrowBorderCollision extends CollisionHandler {
    public ArrowBorderCollision() {
        super(GameType.ARROW, GameType.BORDER);
    }

    @Override
    protected void onCollisionBegin(Entity arrow, Entity border) {
        arrow.removeFromWorld();
    }
}
