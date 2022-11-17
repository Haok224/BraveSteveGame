package org.haok;


import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.List;

public interface Config {
    int CELL_SIZE = 24;
    double PLAYER_MOVE_SPEED = 150;
    int ARROW_SPEED = 400;
    Duration SHOOT_DELAY = Duration.seconds(0.35);
    Duration EXPLODE_TIME = Duration.seconds(0.35);
    int MAX_HP = 4;
    int PLAYER_MAX_LEVEL = 2;
    Duration FREEZE_TIME = Duration.seconds(10);
    List<Point2D> POINTS = List.of(
            new Point2D(288, 600 - 24),
            new Point2D(312, 600 - 24),
            new Point2D(336, 600 - 24),
            new Point2D(360, 600 - 24),
            new Point2D(288, 624 - 24),
            new Point2D(360, 624 - 24),
            new Point2D(288, 648 - 24),
            new Point2D(360, 648 - 24)
    );
    Duration REINFORCE_TIME = Duration.seconds(15);
    Point2D[] SPAWN_ENEMY_POSITION = new Point2D[]{
            new Point2D(30, 30),
            new Point2D(330, 30),
            new Point2D(588, 30)
    };
    int MAX_ENEMY_AMOUNT = 20;
    int BED_MAX_HP = 2;
    int MAX_LEVEL_AMOUNT = 3;
    Duration PLAYER_FREEZY_TIME = Duration.seconds(5);
    Duration ELYTRA_TIME = Duration.seconds(10);
    Point2D PLAYER_POINT = new Point2D(408.00,624.00);
    long SAVE_TIME = 20*1000;
}
