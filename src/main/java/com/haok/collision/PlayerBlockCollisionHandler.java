package com.haok.collision;

import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.haok.effects.StifleEffect;
import com.haok.enums.GameType;

public class PlayerBlockCollisionHandler {
    public static CollisionHandler[] getHandler() {
        return new CollisionHandler[]{new PlayerBorderCH(), new PlayerBrickCH(), new PlayerGlassCH(), new PlayerStoneCH(), new PlayerBedCH(), new PlayerWaterCH()};
    }
}

class PlayerBorderCH extends CollisionHandler {
    public PlayerBorderCH() {
        super(GameType.PLAYER, GameType.BORDER);
    }

    @Override
    protected void onCollisionBegin(Entity a, Entity b) {
        super.onCollisionBegin(a, b);
        a.getComponent(EffectComponent.class).startEffect(new StifleEffect());
    }

    @Override
    protected void onCollisionEnd(Entity a, Entity b) {
        super.onCollisionEnd(a, b);
        try {
            a.getComponent(EffectComponent.class).endEffect(new StifleEffect());
        } catch (RuntimeException r) {
            r.printStackTrace();
        }
    }
}

class PlayerBrickCH extends CollisionHandler {
    public PlayerBrickCH() {
        super(GameType.PLAYER, GameType.BRICK);
    }

    @Override
    protected void onCollisionBegin(Entity a, Entity b) {
        super.onCollisionBegin(a, b);
        a.getComponent(EffectComponent.class).startEffect(new StifleEffect());
    }

    @Override
    protected void onCollisionEnd(Entity a, Entity b) {
        super.onCollisionEnd(a, b);
        try {
            a.getComponent(EffectComponent.class).endEffect(new StifleEffect());
        } catch (RuntimeException r) {
            r.printStackTrace();
        }
    }
}

class PlayerWaterCH extends CollisionHandler {
    public PlayerWaterCH() {
        super(GameType.PLAYER, GameType.WATER);
    }

    @Override
    protected void onCollisionBegin(Entity a, Entity b) {
        super.onCollisionBegin(a, b);
        a.getComponent(EffectComponent.class).startEffect(new StifleEffect());
    }

    @Override
    protected void onCollisionEnd(Entity a, Entity b) {
        super.onCollisionEnd(a, b);
        try {
            a.getComponent(EffectComponent.class).endEffect(new StifleEffect());
        } catch (RuntimeException r) {
            r.printStackTrace();
        }
    }
}

class PlayerGlassCH extends CollisionHandler {
    public PlayerGlassCH() {
        super(GameType.PLAYER, GameType.GLASS);
    }

    @Override
    protected void onCollisionBegin(Entity a, Entity b) {
        super.onCollisionBegin(a, b);
        a.getComponent(EffectComponent.class).startEffect(new StifleEffect());
    }

    @Override
    protected void onCollisionEnd(Entity a, Entity b) {
        super.onCollisionEnd(a, b);
        try {
            a.getComponent(EffectComponent.class).endEffect(new StifleEffect());
        } catch (RuntimeException r) {
            r.printStackTrace();
        }
    }
}

class PlayerStoneCH extends CollisionHandler {
    public PlayerStoneCH() {
        super(GameType.PLAYER, GameType.STONE);
    }

    @Override
    protected void onCollisionBegin(Entity a, Entity b) {
        super.onCollisionBegin(a, b);
        a.getComponent(EffectComponent.class).startEffect(new StifleEffect());
    }

    @Override
    protected void onCollisionEnd(Entity a, Entity b) {
        super.onCollisionEnd(a, b);
        try {
            a.getComponent(EffectComponent.class).endEffect(new StifleEffect());
        } catch (RuntimeException r) {
            r.printStackTrace();
        }
    }
}

class PlayerBedCH extends CollisionHandler {
    public PlayerBedCH() {
        super(GameType.PLAYER, GameType.BED);
    }

    @Override
    protected void onCollisionBegin(Entity a, Entity b) {
        super.onCollisionBegin(a, b);
        a.getComponent(EffectComponent.class).startEffect(new StifleEffect());
    }

    @Override
    protected void onCollisionEnd(Entity a, Entity b) {
        super.onCollisionEnd(a, b);
        try {
            a.getComponent(EffectComponent.class).endEffect(new StifleEffect());
        } catch (RuntimeException r) {
            r.printStackTrace();
        }
    }
}