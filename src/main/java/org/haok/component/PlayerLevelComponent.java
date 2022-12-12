package org.haok.component;

import com.almasb.fxgl.dsl.components.RechargeableIntComponent;
import org.haok.Config;

public class PlayerLevelComponent extends RechargeableIntComponent {
    public PlayerLevelComponent() {
        super(Config.PLAYER_MAX_LEVEL);
        setValue(0);
    }

    public void upgrade() {
        restore(1);
    }

    public void gradeFull() {
        restoreFully();
    }
}
