package com.haok.ui;

import com.almasb.fxgl.app.scene.StartupScene;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class StartScene extends StartupScene {
    public StartScene(int appWidth, int appHeight) {
        super(appWidth, appHeight);
        ImageView image = new ImageView();
        image.setImage(new Image("assets/textures/ui/background.png"));
        VBox box = new VBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.getChildren().add(image);
        getContentRoot().getChildren().add(box);
    }
}
