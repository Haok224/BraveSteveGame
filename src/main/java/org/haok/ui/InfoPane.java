package org.haok.ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.haok.Config;
import org.haok.GameType;
import org.haok.component.PlayerLevelComponent;

public class InfoPane extends VBox {
    public InfoPane() {
        setSpacing(25);
        setStyle("-fx-background-color: lightgray");
        setPrefSize(6 * Config.CELL_SIZE, FXGL.getAppHeight());
        setTranslateX(28 * Config.CELL_SIZE);
        setAlignment(Pos.TOP_CENTER);
        TilePane tp = new TilePane();
        tp.setHgap(Config.CELL_SIZE / 2.0);
        tp.setMaxWidth(Config.CELL_SIZE * 2 + 15);
        VBox.setMargin(tp, new Insets(30, 0, 0, 0));
        tp.setVgap(6);
        tp.setPrefHeight(24 * 10 + 15 * 10);
        Texture flagTexture = FXGL.texture("level.png");
        Text levelText = new Text("" + FXGL.geti("level"));
        levelText.setFont(Font.font(26));
        HBox levelBox = new HBox(flagTexture, levelText);
        levelBox.setAlignment(Pos.BOTTOM_CENTER);
        levelBox.setMaxWidth(80);

        HBox arrowLevel = new HBox();
        arrowLevel.setMaxWidth(77);
        for (int c = 0; c <= Config.PLAYER_MAX_LEVEL; c++) {
            Texture tt = FXGL.texture("ui/arrow.png");
            tt.setVisible(c == 0);
            arrowLevel.getChildren().add(tt);
        }
        Entity player = FXGL.getGameWorld().getEntitiesByType(GameType.PLAYER).get(0);
        player.getComponent(PlayerLevelComponent.class).valueProperty().addListener((ob, ov, nv) -> {
            for (int i = 0; i <= nv.intValue(); i++) {
                arrowLevel.getChildren().get(i).setVisible(true);
            }
        });

        for (int i = 0; i < Config.MAX_ENEMY_AMOUNT; i++) {
            tp.getChildren().add(FXGL.texture("zombie.png"));

        }
        getChildren().add(tp);
        getChildren().add(arrowLevel);
        getChildren().add(levelBox);
        FXGL.getip("enemyAmount").addListener((ob, ov, nv) -> {
            ObservableList<Node> nodes = tp.getChildren();
            for (int i = nodes.size() - 1; i >= Config.MAX_ENEMY_AMOUNT - nv.intValue(); i--) {
                nodes.get(i).setVisible(false);
            }
        });
    }
}
