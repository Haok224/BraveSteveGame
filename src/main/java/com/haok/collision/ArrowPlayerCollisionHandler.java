package com.haok.collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.haok.BraveSteveApp;
import com.haok.Config;
import com.haok.enums.GameType;
import com.haok.effects.ArmorEffect;
import com.haok.effects.ElytraEffect;
import javafx.geometry.Point2D;

public class ArrowPlayerCollisionHandler extends CollisionHandler {
    public ArrowPlayerCollisionHandler() {
        super(GameType.ARROW, GameType.PLAYER);
    }

    @Override
    protected void onCollisionBegin(Entity arrow, Entity player) {
        arrow.removeFromWorld();
        HealthIntComponent hp = player.getComponent(HealthIntComponent.class);
        boolean b = player.getComponent(EffectComponent.class).hasEffect(ArmorEffect.class);
        boolean b2 = player.getComponent(EffectComponent.class).hasEffect(ElytraEffect.class);
        if (b) {
            FXGL.play("armor.wav");
            return;
        } else if (b2) {
            return;
        }
        hp.damage(1);
        FXGL.play("hit.wav");
        if (hp.isZero()) {
            FXGL.spawn("die", player.getCenter().subtract(Config.CELL_SIZE / 2.0, Config.CELL_SIZE / 2.0));
            player.removeFromWorld();
            FXGL.getGameScene().setCursor(FXGL.image("ui/cursor.png"), new Point2D(0, 0));
            FXGL.<BraveSteveApp>getAppCast().failed();
        }
    }
}
