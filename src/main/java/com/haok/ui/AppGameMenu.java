package com.haok.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class AppGameMenu extends FXGLMenu {
    public AppGameMenu() {
        super(MenuType.GAME_MENU);
        Texture texture = FXGL.texture("ui/pause.png");
        Button btn = new Button("继续游戏");
        Button exit = new Button("主菜单");
        btn.getStyleClass().add("menu-btn");
        exit.getStyleClass().add("menu-btn");
        VBox box = new VBox(btn,exit);
        box.setLayoutX(350);
        box.setLayoutY(410);
        btn.setOnAction(actionEvent -> FXGL.getGameController().gotoPlay());
        exit.setOnAction(actionEvent -> FXGL.getGameController().gotoMainMenu());
        getContentRoot().getChildren().addAll(texture, box);
    }
}
