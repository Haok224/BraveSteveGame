package com.haok.collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.haok.BraveSteveApp;
import com.haok.component.BedComponent;
import com.haok.enums.GameType;

public class ArrowBedCollisionHandler extends CollisionHandler {
    public ArrowBedCollisionHandler() {
        super(GameType.ARROW, GameType.BED);
    }

    @Override
    protected void onCollisionBegin(Entity arrow, Entity bed) {
        GameType type = arrow.getObject("type");
        if (type == GameType.PLAYER){
            return;
        }
        HealthIntComponent hp = bed.getComponent(HealthIntComponent.class);
        hp.damage(1);
        if (hp.isZero()) {
            bed.getComponent(BedComponent.class).onHit();
            arrow.removeFromWorld();
            FXGL.play("bed.wav");
            FXGL.<BraveSteveApp>getAppCast().failed();
        }
    }
}
