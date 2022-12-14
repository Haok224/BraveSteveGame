package org.haok.collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import org.haok.BraveSteveApp;
import org.haok.Config;
import org.haok.enums.GameType;
import org.haok.enums.ItemType;
import org.haok.component.PlayerLevelComponent;
import org.haok.effects.*;

import java.util.List;

public class ItemEnemyCollisionHandler extends CollisionHandler {
    public ItemEnemyCollisionHandler() {
        super(GameType.ITEM, GameType.ENEMY);
    }

    @Override
    protected void onCollisionBegin(Entity item, Entity enemy) {
        ItemType itemType = item.getObject("itemType");
        item.removeFromWorld();
        switch (itemType) {
            case XP_BOTTLE -> {
                enemy.getComponent(PlayerLevelComponent.class).upgrade();
                FXGL.play("item.wav");
            }
            case FIREWORK -> {
                enemy.getComponent(PlayerLevelComponent.class).gradeFull();
                FXGL.play("item.wav");
            }
            case APPLE -> {

                List<Entity> entityList = FXGL.getGameWorld().getEntitiesByType(GameType.PLAYER);
                for (Entity entity : entityList) {
                    if (!entity.isActive()) {
                        return;
                    }
                    HealthIntComponent hp = entity.getComponent(HealthIntComponent.class);
                    hp.damage(1);
                    if (hp.isZero()) {
                        FXGL.spawn("die", entity.getCenter().subtract(Config.CELL_SIZE / 2.0, Config.CELL_SIZE / 2.0));
                        entity.removeFromWorld();
                        FXGL.<BraveSteveApp>getAppCast().failed();
                    }
                }

                FXGL.play("eat.wav");
            }
            case GOLDEN_APPLE -> {
                List<Entity> entityList = FXGL.getGameWorld().getEntitiesByType(GameType.PLAYER);
                for (Entity entity : entityList) {
                    if (!entity.isActive()) {
                        return;
                    }
                    HealthIntComponent hp = entity.getComponent(HealthIntComponent.class);
                    int max = hp.getMaxValue();
                    hp.damage(max / 2);
                    if (hp.isZero()) {
                        FXGL.spawn("die", entity.getCenter().subtract(Config.CELL_SIZE / 2.0, Config.CELL_SIZE / 2.0));
                        entity.removeFromWorld();
                        FXGL.<BraveSteveApp>getAppCast().failed();
                    }
                }

                FXGL.play("eat.wav");
            }
            case TNT -> {
                FXGL.play("bomb.wav");
                List<Entity> entityList = FXGL.getGameWorld().getEntitiesByType(GameType.PLAYER);
                for (Entity entity : entityList) {
                    if (!entity.isActive()) {
                        return;
                    }
                    HealthIntComponent hp = entity.getComponent(HealthIntComponent.class);
                    hp.setValue(1);
                }
            }
            case SHIP -> {
                enemy.getComponent(EffectComponent.class).startEffect(new ShipEffect());
                FXGL.play("drink.wav");
            }
            case TIME_WATCH -> {
                FXGL.play("item.wav");
                FXGL.getGameWorld().getEntitiesByType(GameType.PLAYER).forEach(player -> {
                    if (player.isActive()) {
                        player.getComponent(EffectComponent.class).startEffect(new FreezeEffect());

                    }
                });
            }
            case ARMOR -> {
                enemy.getComponent(EffectComponent.class).startEffect(new ArmorEffect());
                FXGL.play("armor.wav");
            }
            case SHOVEL -> {
                FXGL.play("shovel.wav");
                FXGL.<BraveSteveApp>getAppCast().unReinforce();
            }
            case ELYTRA -> {
                FXGL.play("elytra.wav");
                enemy.getComponent(EffectComponent.class).startEffect(new ElytraEffect());
            }
            case CHORUS_FRUIT -> {
                FXGL.play("item.wav");
                enemy.getComponent(EffectComponent.class).startEffect(new ChorusFruitEffect());
            }case DISPENSER -> enemy.getComponent(EffectComponent.class).startEffect(new DispenserEffect());
        }
    }
}
