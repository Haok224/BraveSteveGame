package org.haok.component;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityGroup;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import org.haok.Config;
import org.haok.Dir;
import org.haok.GameType;
import org.haok.effects.ElytraEffect;
import org.haok.effects.EnderPearlEffect;
import org.haok.effects.FreezeEffect;
import org.haok.effects.ShipEffect;

import java.util.List;

public class PlayerComponent extends Component {
    private boolean moveNow = false;
    private double distance;
    private Dir moveDir = Dir.UP;
    private LocalTimer shootTimer;
    @SuppressWarnings("all")
    private EffectComponent effectComponent;
    private final LazyValue<EntityGroup> blockAllLazyValue = new LazyValue<>(() ->
            FXGL.getGameWorld().getGroup(
                    GameType.BORDER,
                    GameType.STONE,
                    GameType.GLASS,
                    GameType.WATER,
                    GameType.ENEMY,
                    GameType.PLAYER,
                    GameType.BRICK
            ));
    private final LazyValue<EntityGroup> blockLazyValue = new LazyValue<>(() ->
            FXGL.getGameWorld().getGroup(
                    GameType.BORDER,
                    GameType.STONE,
                    GameType.GLASS,
                    GameType.ENEMY,
                    GameType.PLAYER,
                    GameType.BRICK
            ));
    private final LazyValue<EntityGroup> borderValue = new LazyValue<>(() ->
            FXGL.getGameWorld().getGroup(
                    GameType.BORDER
            )
    );

    @Override
    public void onUpdate(double tpf) {
        if (effectComponent.hasEffect(FreezeEffect.class)) {
            return;
        }
        moveNow = false;
        distance = tpf * Config.PLAYER_MOVE_SPEED;
    }

    public void moveUp() {
        if (moveNow) {
            return;
        }
        moveDir = Dir.UP;
        moveNow = true;
        move();
    }

    public void moveDown() {
        if (moveNow) {
            return;
        }
        moveDir = Dir.DOWN;
        moveNow = true;
        move();
    }

    public void moveLeft() {
        if (moveNow) {
            return;
        }
        moveDir = Dir.LEFT;
        moveNow = true;
        move();
    }

    //在组件被实体添加时调用
    @Override
    public void onAdded() {
        shootTimer = FXGL.newLocalTimer();
    }

    public void moveRight() {
        if (moveNow) {
            return;
        }
        moveDir = Dir.RIGHT;
        moveNow = true;
        move();
    }

    public void shoot() {
        if (shootTimer.elapsed(Config.SHOOT_DELAY)) {
            FXGL.spawn("arrow", new SpawnData(entity.getCenter().subtract(16 / 2.0, 6 / 2.0))
                    .put("dir", moveDir.getVector())
                    .put("type", entity.getType())
                    .put("level", entity.getComponent(PlayerLevelComponent.class).getValue())
            );
        }
        shootTimer.capture();
    }

    public Dir getMoveDir() {
        return moveDir;
    }

    public void move() {
        int len = (int) distance;
        boolean b = effectComponent.hasEffect(ShipEffect.class);
        boolean b1 = effectComponent.hasEffect(ElytraEffect.class);
        List<Entity> entityList;

        if (b) {
            entityList = blockLazyValue.get().getEntitiesCopy();
        } else if (b1) {
            entityList = borderValue.get().getEntitiesCopy();
        } else {
            entityList = blockAllLazyValue.get().getEntitiesCopy();
        }
        for (int i = 0; i < len; i++) {
            entity.translate(moveDir.getVector().getX(), moveDir.getVector().getY());
            entityList.remove(entity);
            boolean isCollation = false;
            for (Entity value : entityList) {
                if (entity.isColliding(value)) {
                    isCollation = true;
                    break;
                }
            }
            if (isCollation) {
                entity.translate(-moveDir.getVector().getX(), -moveDir.getVector().getY());
                break;
            }
        }
    }

    public void tp() {
        if (entity.getComponent(EffectComponent.class).hasEffect(EnderPearlEffect.class)) {
            FXGL.play("tp.wav");
            Point2D point = FXGLMath.randomPoint(new Rectangle2D(Config.CELL_SIZE, Config.CELL_SIZE
                    , 26 * Config.CELL_SIZE - 24,
                    26 * Config.CELL_SIZE - 24));
            entity.setX(point.getX());
            entity.setY(point.getY());
            entity.getComponent(EffectComponent.class).endEffect(EnderPearlEffect.class);
        }
    }
}
