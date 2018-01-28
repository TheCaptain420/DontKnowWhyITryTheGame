package cap.the;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.handler.CollectibleHandler;
import com.almasb.fxgl.settings.GameSettings;
import javafx.scene.input.KeyCode;

public class Main extends GameApplication {



    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(15*70);
        gameSettings.setHeight(10*70);
    }

    private Entity player;

    @Override
    protected void initInput() {
         getInput().addAction(new UserAction("left") {
             @Override
             protected void onAction() {
                 player.getControl(PlayerControl.class).left();
             }
         }, KeyCode.A);

        getInput().addAction(new UserAction("right") {
            @Override
            protected void onAction() {
                player.getControl(PlayerControl.class).right();
            }
        }, KeyCode.D);

        getInput().addAction(new UserAction("jump") {
            @Override
            protected void onAction() {
                player.getControl(PlayerControl.class).jump();
            }
        }, KeyCode.W);
    }

    @Override
    protected void initGame() {
        getGameWorld().setLevelFromMap("mario.json");
        player = getGameWorld().spawn("player",50,50);//Spawner player på mappet. //50-50 er koordinaterne han spawner.
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(Type.PLAYER,Type.COIN){
            @Override
            protected void onCollisionBegin(Entity player, Entity coin){
                coin.removeFromWorld();
                System.out.println("You have gathered a coin!");

            }

        });
    }

    public static void main(String[] args) {
        launch(args);

        }
}
