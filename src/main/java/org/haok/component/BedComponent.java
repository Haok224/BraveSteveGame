package org.haok.component;

import com.almasb.fxgl.entity.component.Component;

public class BedComponent extends Component {
    public void onHit() {
        entity.getViewComponent().clearChildren();

    }
}
