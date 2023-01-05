package com.haok.collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.haok.BraveSteveApp;
import com.haok.Config;
import com.haok.component.PlayerLevelComponent;
import com.haok.effects.*;
import com.haok.enums.GameType;
import com.haok.enums.ItemType;

import java.util.List;

public class ItemPlayerCollisionHandler extends CollisionHandler {
    public ItemPlayerCollisionHandler() {
        super(GameType.ITEM, GameType.PLAYER);
    }

    @Override
    protected void onCollisionBegin(Entity item, Entity player) {
        ItemType itemType = item.getObject("itemType");
        item.removeFromWorld();
        switch (itemType) {
            case XP_BOTTLE -> {
                player.getComponent(PlayerLevelComponent.class).upgrade();
                FXGL.play("item.wav");
            }
            case FIREWORK -> {
                player.getComponent(PlayerLevelComponent.class).gradeFull();
                FXGL.play("item.wav");
            }
            case APPLE -> {
                player.getComponent(HealthIntComponent.class).restore(1);
                FXGL.play("eat.wav");
            }
            case GOLDEN_APPLE -> {
                player.getComponent(HealthIntComponent.class).restoreFully();
                FXGL.play("eat.wav");
            }
            case TNT -> {
                FXGL.play("bomb.wav");
                List<Entity> entityList = FXGL.getGameWorld().getEntitiesByType(GameType.ENEMY);
                for (Entity e : entityList) {
                    FXGL.spawn("explode", e.getCenter().subtract(Config.CELL_SIZE / 2.0, Config.CELL_SIZE / 2.0));
                    e.removeFromWorld();
                    FXGL.inc("destroyedEnemyAmount", 1);
                }
            }
            case SHIP -> {
                player.getComponent(EffectComponent.class).startEffect(new ShipEffect());
                FXGL.play("drink.wav");
            }
            case TIME_WATCH -> {
                FXGL.play("item.wav");
                FXGL.<BraveSteveApp>getAppCast().freezeEnemy();
            }
            case ARMOR -> {
                player.getComponent(EffectComponent.class).startEffect(new ArmorEffect());
                FXGL.play("armor.wav");
            }
            case SHOVEL -> {
                FXGL.play("shovel.wav");
                FXGL.<BraveSteveApp>getAppCast().reinforce();
            }
            case ELYTRA -> {
                FXGL.play("elytra.wav");
                player.getComponent(EffectComponent.class).startEffect(new ElytraEffect());
            }
            case CHORUS_FRUIT -> {
                FXGL.play("item.wav");
                player.getComponent(EffectComponent.class).startEffect(new ChorusFruitEffect());
            }
            case DISPENSER -> player.getComponent(EffectComponent.class).startEffect(new DispenserEffect());
        }
    }
}
