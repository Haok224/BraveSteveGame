package com.haok.factories;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.geometry.Point2D;
import com.haok.Config;
import com.haok.enums.GameType;

public class SpecialEntityFactory implements EntityFactory {
    @Spawns("arrow")
    public Entity newArrow(SpawnData data) {
        Point2D dir = data.get("dir");
        CollidableComponent collidableComponent = new CollidableComponent(true);
        collidableComponent.addIgnoredType(data.get("type"));
        int level = data.<Integer>get("level");
        Entity arrow = FXGL.entityBuilder(data)
                .type(GameType.ARROW)
                .viewWithBBox("arrow.png")
                .with(new ProjectileComponent(dir, Config.ARROW_SPEED))
                .with(collidableComponent)
                .collidable()
                .build();
        Entity betterArrow = FXGL.entityBuilder(data)
                .type(GameType.ARROW)
                .viewWithBBox("betterArrow.png")
                .with(new ProjectileComponent(dir, Config.ARROW_SPEED))
                .with(collidableComponent)
                .collidable()
                .build();
        Entity bestArrow = FXGL.entityBuilder(data)
                .type(GameType.ARROW)
                .viewWithBBox("bestArrow.png")
                .with(new ProjectileComponent(dir, Config.ARROW_SPEED))
                .with(collidableComponent)
                .collidable()
                .build();
        switch (level) {
            case 0 -> {
                FXGL.play("biu.wav");
                return arrow;
            }
            case 1 -> {
                FXGL.play("biu.wav");
                return betterArrow;
            }
            case 2 -> {
                FXGL.play("launch.wav");
                return bestArrow;
            }
        }
        return arrow;
    }

    @Spawns("explode")
    public Entity newExplode(SpawnData data) {
        FXGL.play("die.wav");
        return FXGL.entityBuilder(data)
                .view("die.png")
                .with(new ExpireCleanComponent(Config.EXPLODE_TIME))//过期清理
                .build();
    }

    @Spawns("die")
    public Entity newDie(SpawnData data) {
        FXGL.play("playerDie.wav");
        return FXGL.entityBuilder(data)
                .view("die.png")
                .with(new ExpireCleanComponent(Config.EXPLODE_TIME))//过期清理
                .build();
    }
}
