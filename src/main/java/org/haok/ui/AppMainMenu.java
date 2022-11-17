package org.haok.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.haok.BraveSteveApp;

import java.io.IOException;

public class AppMainMenu extends FXGLMenu {
    public AppMainMenu() {
        super(MenuType.MAIN_MENU);


        Texture texture = FXGL.texture("ui/mainmenu.png");
        Button btnStart = new Button("开始游戏");
        Button btnExit = new Button("退出游戏");
        VBox box = new VBox(30, btnStart, btnExit);
        getContentRoot().getChildren().addAll(texture, box);
        btnStart.setOnAction(actionEvent -> FXGL.getGameController().startNewGame());
        btnExit.setOnAction(actionEvent -> {
            try {
                FXGL.<BraveSteveApp>getAppCast().saveGame();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            FXGL.getGameController().exit();
        });
        box.setLayoutX(350);
        box.setLayoutY(410);
        btnStart.getStyleClass().add("menu-btn");
        btnExit.getStyleClass().add("menu-btn");

    }
}
