package com.haok.factories;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.ui.ProgressBar;
import com.haok.component.EnemyComponent;
import com.haok.component.PlayerComponent;
import javafx.scene.paint.Color;
import com.haok.Config;
import com.haok.enums.GameType;
import com.haok.component.PlayerLevelComponent;

public class BiologyEntityFactory implements EntityFactory {
    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.ENEMY)
                .viewWithBBox("zombie.png") //碰撞箱和视图设定
                .with(new EffectComponent())
                .with(new PlayerComponent())
                .with(new PlayerLevelComponent())
                .with(new EnemyComponent())
                .collidable()
                .build();
    }

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
                .viewWithBBox("steve.png") //碰撞箱与视图设定
                .view(hpBar)
                .with(new EffectComponent())
                .with(new PlayerComponent())
                .with(new PlayerLevelComponent())
                .with(healthIntComponent)
                .with(new CollidableComponent(true))
                .build();
    }

}
