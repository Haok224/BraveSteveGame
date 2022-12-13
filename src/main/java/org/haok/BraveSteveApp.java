package org.haok;

import com.almasb.fxgl.app.CursorInfo;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.*;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.time.TimerAction;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.haok.collision.*;
import org.haok.component.PlayerComponent;
import org.haok.ui.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.*;
import java.util.List;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static org.haok.Config.*;

public class BraveSteveApp extends GameApplication {
    static String userName = System.getenv().get("USERNAME");
    TimerAction freezeEnemyTimerAction;
    TimerAction reinforceTimerAction;
    TimerAction spawnEnemyTimeAction;
    File gameFile = new File("C:\\Users\\" + userName + "\\AppData\\Roaming\\GameData\\level.txt");
    File gameDir = new File("C:\\Users\\" + userName + "\\AppData\\Roaming\\GameData");
    boolean isGameLoaded = false;
    public static Entity player;

    public static void main(String[] args) {
        launch(args);
    }

    //    int enemyAmount = 0;
    @Override
    protected void initInput() {
        onKey(KeyCode.W, () -> {
            if (playerNotExists()) {
                return;
            }
            PlayerComponent component = player.getComponent(PlayerComponent.class);
            component.moveUp();
        });
        onKey(KeyCode.S, () -> {
            if (playerNotExists()) {
                return;
            }
            PlayerComponent component = player.getComponent(PlayerComponent.class);
            component.moveDown();
        });
        onKey(KeyCode.A, () -> {
            if (playerNotExists()) {
                return;
            }
            PlayerComponent component = player.getComponent(PlayerComponent.class);
            component.moveLeft();
        });
        onKey(KeyCode.D, () -> {
            if (playerNotExists()) {
                return;
            }
            PlayerComponent component = player.getComponent(PlayerComponent.class);
            component.moveRight();
        });
        onKey(KeyCode.SPACE, () -> {
            if (playerNotExists()) {
                return;
            }
            PlayerComponent component = player.getComponent(PlayerComponent.class);
            component.shoot();
        });
        onKey(KeyCode.F, () -> {
            if (playerNotExists()) {
                return;
            }
            PlayerComponent component = player.getComponent(PlayerComponent.class);
            component.tp();
        });
    }

    public boolean playerNotExists() {
        return player == null || !player.isActive();
    }

    @Override
    protected void initPhysics() {
        //子弹与敌人碰撞
        getPhysicsWorld().addCollisionHandler(new ArrowEnemyCollisionHandler());
        //子弹与玩家碰撞
        getPhysicsWorld().addCollisionHandler(new ArrowPlayerCollisionHandler());
        //子弹与子弹碰撞
        getPhysicsWorld().addCollisionHandler(new ArrowArrowCollisionHandler());
        //子弹与边界碰撞
        getPhysicsWorld().addCollisionHandler(new ArrowBorderCollisionHandler());
        CollisionHandler collisionHandler = new ArrowGlassCollisionHandler();
        //子弹与墙碰撞
        getPhysicsWorld().addCollisionHandler(collisionHandler);
        getPhysicsWorld().addCollisionHandler(collisionHandler.copyFor(GameType.ARROW, GameType.STONE));
        getPhysicsWorld().addCollisionHandler(collisionHandler.copyFor(GameType.ARROW, GameType.BRICK));
        getPhysicsWorld().addCollisionHandler(collisionHandler.copyFor(GameType.ARROW, GameType.GRASS));
        //道具碰撞
        getPhysicsWorld().addCollisionHandler(new ItemPlayerCollisionHandler());
        //与基地碰撞
        getPhysicsWorld().addCollisionHandler(new ArrowBedCollisionHandler());
        //敌人与道具碰撞
        getPhysicsWorld().addCollisionHandler(new ItemEnemyCollisionHandler());
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("freezeEnemy", false);
        vars.put("enemyAmount", 0);
        vars.put("destroyedEnemyAmount", 0);
        vars.put("level", 1);
        vars.put("playerLevel", 1);
    }

    public void freezeEnemy() {
        if (freezeEnemyTimerAction != null && freezeEnemyTimerAction.isExpired()) {
            freezeEnemyTimerAction.expire();
        }
        set("freezeEnemy", true);
        freezeEnemyTimerAction = runOnce(() -> set("freezeEnemy", false), Config.FREEZE_TIME);

    }

    @Override
    protected void initGame() {
        isGameLoaded = true;
        if (!System.getProperty("os.name").startsWith("Windows")) {
            JOptionPane.showConfirmDialog(null, "该程序仅支持Windows系统。", "错误", JOptionPane.YES_NO_OPTION);
            System.exit(1);
        }
        //加载本地文件
        if (gameFile.isFile()) {
            try {
                FileReader fileReader = new FileReader(gameFile);
                BufferedReader reader = new BufferedReader(fileReader);
                set("level", reader.read());
                reader.close();
                System.out.println("加载完成。当前关卡：" + geti("level"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            set("level", 1);
        }
        //1.设置背景灰色
        getGameScene().setBackgroundColor(Color.GREY);

        //2.创建玩家实体
        getGameWorld().addEntityFactory(new GameEntityFactory());
        startLevel();
        getip("destroyedEnemyAmount").addListener((ob, ov, nv) -> {
            if (nv.intValue() == MAX_ENEMY_AMOUNT) {
                runOnce(() -> {
                    if (geti("level") == MAX_LEVEL_AMOUNT) {
                        getNotificationService().pushNotification("~~大获全胜~~");
                        getGameController().gotoMainMenu();
                        set("level", 1);
                        try {
                            saveGame();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        getSceneService().pushSubScene(new WinScene(true));
                    }
                }, Duration.seconds(0.5));
            }
        });
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setMainMenuEnabled(true);
        gameSettings.setTitle("Brave Steve");
        gameSettings.setVersion("1.0");
        gameSettings.setWidth(34 * CELL_SIZE);
        gameSettings.setHeight(28 * CELL_SIZE);
        gameSettings.setDefaultCursor(new CursorInfo("ui/cursor.png", 0, 0));
        gameSettings.setAppIcon("icon.png");
        gameSettings.getCSSList().add("app.css");
        gameSettings.setSceneFactory(new SceneFactory() {
            @NotNull
            @Override
            public StartupScene newStartup(int width, int height) {
                return new StartScene(width, height);
            }

            @NotNull
            @Override
            public LoadingScene newLoadingScene() {
                return new AppLoadingScean();
            }

            @NotNull
            @Override
            public FXGLMenu newGameMenu() {
                return new AppGameMenu();
            }

            @NotNull
            @Override
            public FXGLMenu newMainMenu() {
                return new AppMainMenu();
            }

        });
    }

    public void reinforce() {
        expireTimerAction(freezeEnemyTimerAction);
        reinforceTimerAction = runOnce(() -> updateBed("glass"), Config.REINFORCE_TIME);
        updateBed("stone");
        List<Entity> entityList = getGameWorld().getEntitiesByType(GameType.BED);
        for (Entity entity : entityList) {
            entity.getComponent(HealthIntComponent.class).restoreFully();
        }
    }

    public void unReinforce() {
        for (Point2D point : POINTS) {
            getGameWorld().getEntitiesAt(point).forEach(Entity::removeFromWorld);
        }
    }

    private void expireTimerAction(TimerAction timerAction) {
        if (timerAction != null && timerAction.isExpired()) {
            timerAction.expire();
        }
    }

    private void updateBed(String brick) {
        for (Point2D point : Config.POINTS) {
            getGameWorld().getEntitiesAt(point).forEach(Entity::removeFromWorld);
            spawn(brick, point);
        }
    }

    public void failed() {
        FXGL.getSceneService().pushSubScene(new FailedScene());
    }

    public void startLevel() {
        //重置、初始化工作
        expireTimerAction(spawnEnemyTimeAction);
        expireTimerAction(freezeEnemyTimerAction);
        expireTimerAction(reinforceTimerAction);
        set("freezeEnemy", false);
        set("enemyAmount", 0);
        set("destroyedEnemyAmount", 0);
        setLevelFromMap("level" + geti("level") + ".tmx");
        player = spawn("player", PLAYER_POINT);
        GameView view = new GameView(new InfoPane(), Integer.MAX_VALUE);
        getGameScene().addGameView(view);

        spawnEnemyTimeAction = run(() -> {
            if (geti("enemyAmount") == MAX_ENEMY_AMOUNT) {
                if (spawnEnemyTimeAction != null && spawnEnemyTimeAction.isExpired())
                    spawnEnemyTimeAction.expire();
                return;
            }
            Point2D point = FXGLMath.random(Config.SPAWN_ENEMY_POSITION).isPresent() ? FXGLMath.random(Config.SPAWN_ENEMY_POSITION).get() : null;
            List<Entity> es = null;
            if (point != null) {
                es = getGameWorld().getEntitiesInRange(new Rectangle2D(point.getX(), point.getY(), 39, 39));
            }
            List<Entity> entities = null;
            if (es != null) {
                entities = es.stream().filter(entity ->
                        entity.isType(GameType.ENEMY)
                                || entity.isType(GameType.PLAYER)
                                || entity.isType(GameType.BRICK)
                                || entity.isType(GameType.STONE)
                ).toList();
            }
            if (entities != null && entities.isEmpty()) {
                spawn("enemy", point);
                inc("enemyAmount", 1);
            }
        }, Duration.seconds(1.5));
        try {
            saveGame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveGame() throws IOException {
        if (!isGameLoaded) {
            return;
        }
        System.out.println("保存游戏。");
        System.out.println("游戏文件是否存在：" + gameFile.isFile());
        int level = geti("level");
        System.out.println("创建游戏目录是否成功：" + gameDir.mkdirs());
        System.out.println("创建文件是否成功：" + gameFile.createNewFile());
        System.out.println("当前关卡:" + level);
        FileWriter fileWriter = new FileWriter(gameFile);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        writer.write(level);
        writer.close();
    }
}
