package org.haok.component;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import org.haok.Dir;

public class DispenserComponent extends Component {
    Dir dir;
    public DispenserComponent(Dir dir) {
        super();
        this.dir = dir;
    }

    @Override
    public void onUpdate(double tpf) {
        if (FXGLMath.randomBoolean(0.5)){
            FXGL.spawn("arrow", new SpawnData(entity.getCenter().subtract(16 / 2.0, 6 / 2.0))
                    .put("dir", dir.getVector())
                    .put("type", entity.getType())
                    .put("level", entity.getComponent(PlayerLevelComponent.class).getValue())
            );
        }
    }
}
