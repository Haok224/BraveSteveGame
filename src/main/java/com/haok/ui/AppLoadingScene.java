package com.haok.ui;

import com.almasb.fxgl.app.scene.LoadingScene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AppLoadingScene extends LoadingScene {
    public AppLoadingScene() {
        super();
        Image image = new Image("assets/textures/ui/loading.png");
        ImageView imageView = new ImageView(image);
        getContentRoot().getChildren().add(imageView);
    }
}
