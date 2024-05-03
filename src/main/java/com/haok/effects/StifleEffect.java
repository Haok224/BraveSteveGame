package com.haok.effects;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.Effect;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.time.TimerAction;
import com.haok.BraveSteveApp;
import com.haok.Config;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

public class StifleEffect extends Effect {
    TimerAction ac;

    public StifleEffect() {
        super(Duration.INDEFINITE);
    }

    @Override
    public void onEnd(@NotNull Entity entity) {
        ac.expire();
    }

    @Override
    public void onStart(@NotNull Entity entity) {
        ac = FXGL.getGameTimer().runAtInterval(() -> {
            entity.getComponent(HealthIntComponent.class).damage(1);
            if (entity.getComponent(HealthIntComponent.class).isZero()) {
                FXGL.spawn("die", entity.getCenter().subtract(Config.CELL_SIZE / 2.0, Config.CELL_SIZE / 2.0));
                entity.removeFromWorld();
                FXGL.getGameScene().setCursor(FXGL.image("ui/cursor.png"), new Point2D(0, 0));
                FXGL.<BraveSteveApp>getAppCast().failed();
            }
            FXGL.play("hit.wav");
        }, Duration.seconds(1));
    }
}
