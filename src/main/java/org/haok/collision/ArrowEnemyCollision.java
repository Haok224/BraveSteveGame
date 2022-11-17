package org.haok.collision;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.geometry.Rectangle2D;
import org.haok.Config;
import org.haok.GameType;

import static com.almasb.fxgl.dsl.FXGL.spawn;

public class ArrowEnemyCollision extends CollisionHandler {
    public ArrowEnemyCollision() {
        super(GameType.ARROW, GameType.ENEMY);
    }

    @Override
    protected void onCollisionBegin(Entity arrow, Entity enemy) {
        spawn("explode", enemy.getCenter().subtract(Config.CELL_SIZE / 2.0, Config.CELL_SIZE / 2.0));
        arrow.removeFromWorld();
        enemy.removeFromWorld();
        FXGL.inc("destroyedEnemyAmount", 1);
        if (FXGLMath.randomBoolean(0.5)) {
            FXGL.spawn("item",
                    FXGLMath.randomPoint(new Rectangle2D(
                            Config.CELL_SIZE, Config.CELL_SIZE
                            , 26 * Config.CELL_SIZE - 24,
                            26 * Config.CELL_SIZE - 24
                    ))
            );
        }
    }
}
