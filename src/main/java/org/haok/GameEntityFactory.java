package org.haok;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.haok.component.*;

import java.util.Locale;
import java.util.Objects;

public class GameEntityFactory implements EntityFactory {
    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        HealthIntComponent healthIntComponent = new HealthIntComponent(Config.MAX_HP);
        healthIntComponent.setValue(Config.MAX_HP);
        ProgressBar hpBar = new ProgressBar();
        hpBar.maxValueProperty().bind(healthIntComponent.maxValueProperty());
        hpBar.currentValueProperty().bind(healthIntComponent.valueProperty());
        hpBar.setWidth(Config.CELL_SIZE);
        hpBar.setTranslateY(32);
        hpBar.setHeight(8);
        hpBar.setFill(Color.LIGHTGREEN);
        return FXGL.entityBuilder(data)
                .with(new PlayerLevelComponent())
                .type(GameType.PLAYER)
                .viewWithBBox("steve.png") //碰撞箱大小
                .view(hpBar)
                .with(new EffectComponent())
                .with(new PlayerComponent())
                .with(new PlayerLevelComponent())
                .with(healthIntComponent)
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("glass")
    public Entity newGlass(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.GLASS)
                .viewWithBBox("map/glass.png")
                .collidable()
                .neverUpdated()
                .build();
    }

    @Spawns("stone")
    public Entity newStone(SpawnData data) {
        return FXGL.entityBuilder(data).
                type(GameType.STONE)
                .viewWithBBox("map/stone.png")
                .collidable()
                .neverUpdated()
                .build();
    }

    @Spawns("brick")
    public Entity newBrick(SpawnData data) {
        return FXGL.entityBuilder(data).
                type(GameType.BRICK)
                .viewWithBBox("map/brick.png")
                .collidable()
                .neverUpdated()
                .build();
    }

    @Spawns("grass")
    public Entity newGrass(SpawnData data) {
        return FXGL.entityBuilder(data).
                type(GameType.GRASS)
                .viewWithBBox("map/grass.png")
                .zIndex(1000)
                .neverUpdated()
                .collidable().build();
    }

    @Spawns("snow")
    public Entity newSnow(SpawnData data) {
        return FXGL.entityBuilder(data).
                type(GameType.SNOW)
                .view("map/snow.png")
                .build();
    }

    @Spawns("border")
    public Entity newBorder(SpawnData data) {

        int height = data.<Integer>get("height");
        int width = data.<Integer>get("width");
        return FXGL.entityBuilder(data)
                .type(GameType.BORDER)
                .viewWithBBox(new Rectangle(width, height, Color.LIGHTGRAY))
                .collidable()
                .neverUpdated()
                .build();
    }

    @Spawns("water")
    public Entity newWater(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.WATER)
                .viewWithBBox("map/water.png")
                .collidable()
                .neverUpdated()
                .build();
    }

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

    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.ENEMY)
                .viewWithBBox("zombie.png") //碰撞箱大小
                .with(new EffectComponent())
                .with(new PlayerComponent())
                .with(new PlayerLevelComponent())
                .with(new EnemyComponent())
                .collidable()
                .build();
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

    @Spawns("item")
    public Entity newItem(SpawnData data) {
        ItemType itemType = FXGLMath.random(ItemType.values()).isPresent() ? FXGLMath.random(ItemType.values()).get() : null;
        data.put("itemType", Objects.requireNonNull(itemType));
        return FXGL.entityBuilder(data)
                .viewWithBBox("items/" + itemType.toString().toLowerCase(Locale.ROOT) + ".png")
                .type(GameType.ITEM)
                .collidable()
                .build();
    }

    @Spawns("bed")
    public Entity newBed(SpawnData data) {
        HealthIntComponent healthIntComponent = new HealthIntComponent(Config.BED_MAX_HP);
        healthIntComponent.setValue(Config.BED_MAX_HP);
        ProgressBar hpBar = new ProgressBar();
        hpBar.maxValueProperty().bind(healthIntComponent.maxValueProperty());
        hpBar.currentValueProperty().bind(healthIntComponent.valueProperty());
        hpBar.setWidth(Config.CELL_SIZE * 2);
        hpBar.setTranslateY(-32);
        hpBar.setHeight(8);
        hpBar.setFill(Color.LIGHTGREEN);
        return FXGL.entityBuilder()
                .type(GameType.BED)
                .viewWithBBox("map/bed.png")
                .view(hpBar)
                .with(new BedComponent())
                .with(healthIntComponent)
                .collidable()
                .build();
    }

    @Spawns("dispenser")
    public Entity newDispenser(SpawnData data) {
        return FXGL.entityBuilder(data)
                .viewWithBBox("dispenser.png")
                .with(new DispenserComponent(BraveSteveApp.player.getComponent(PlayerComponent.class).getMoveDir()))
                .build();
    }
}
