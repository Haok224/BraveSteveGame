package org.haok.ui;

import com.almasb.fxgl.app.scene.LoadingScene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AppLoadingScean extends LoadingScene {
    public AppLoadingScean() {
        super();
        Image image = new Image("assets/textures/ui/loading.png");
        ImageView imageView = new ImageView(image);
        getContentRoot().getChildren().add(imageView);
    }
}
