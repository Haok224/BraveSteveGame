package org.haok.collision;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import org.haok.GameType;

public class ArrowArrowCollisionHandler extends CollisionHandler {
    public ArrowArrowCollisionHandler() {
        super(GameType.ARROW, GameType.ARROW);
    }

    @Override
    protected void onCollisionBegin(Entity a1, Entity a2) {
        GameType t1 = a1.getObject("type");
        GameType t2 = a2.getObject("type");
        if (t1 != t2) {
            a1.removeFromWorld();
            a2.removeFromWorld();
        }
    }
}
