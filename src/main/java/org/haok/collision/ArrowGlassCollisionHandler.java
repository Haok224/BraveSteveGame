package org.haok.collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import org.haok.Config;
import org.haok.GameType;

import java.util.List;

public class ArrowGlassCollisionHandler extends CollisionHandler {
    public ArrowGlassCollisionHandler() {
        super(GameType.ARROW, GameType.GLASS);
    }

    @Override
    protected void onCollision(Entity arrow, Entity glass) {
        List<Entity> entityList = FXGL.getGameWorld().getEntitiesFiltered(entity ->
                arrow.isColliding(entity)
                        && (entity.isType(GameType.STONE)
                        || entity.isType(GameType.GLASS)
                        || entity.isType(GameType.BRICK)
                        || entity.isType(GameType.GRASS))
        );
        boolean isRemoveArrow = false;
        for (Entity entity : entityList) {
            GameType type = (GameType) entity.getType();
            switch (type) {
                case GLASS -> {
                    FXGL.play("boom.wav");
                    entity.removeFromWorld();
                    int level = arrow.getInt("level");
                    if (level != Config.PLAYER_MAX_LEVEL) {
                        isRemoveArrow = true;
                    }
                }
                case STONE, BRICK -> {
                    int level = arrow.getInt("level");
                    if (level == Config.PLAYER_MAX_LEVEL) {
                        FXGL.play("firework.wav");
                        entity.removeFromWorld();
                    }
                    isRemoveArrow = true;
                }
                case GRASS -> {
                    int level = arrow.getInt("level");
                    if (level == Config.PLAYER_MAX_LEVEL) {
                        FXGL.play("grass.wav");
                        entity.removeFromWorld();
                    }
                }
            }
            if (isRemoveArrow) {
                arrow.removeFromWorld();
            }
        }
    }
}