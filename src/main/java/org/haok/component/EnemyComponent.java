package org.haok.component;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.entity.component.Component;
import org.haok.Dir;
import org.haok.effects.EnderPearlEffect;

public class EnemyComponent extends Component {
    private PlayerComponent playerComponent;

    @Override
    public void onUpdate(double tpf) {
        if (FXGL.getb("freezeEnemy")) {
            return;
        }
        Dir moveDir = playerComponent.getMoveDir();
        if (FXGLMath.randomBoolean(0.025)) {
            moveDir = FXGLMath.random(Dir.values()).get();
        }
        switch (moveDir) {
            case UP -> playerComponent.moveUp();
            case DOWN -> playerComponent.moveDown();
            case LEFT -> playerComponent.moveLeft();
            case RIGHT -> playerComponent.moveRight();
        }
        if (FXGLMath.randomBoolean(0.03)) {
            playerComponent.shoot();
        }
        if(getEntity().getComponent(EffectComponent.class).hasEffect(EnderPearlEffect.class)){
            if(FXGLMath.randomBoolean(0.3)){
                playerComponent.tp();
            }
        }
    }
}
