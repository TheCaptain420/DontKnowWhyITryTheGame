package cap.the;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.handler.CollectibleHandler;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.ui.TextFlowBuilder;
import com.almasb.fxgl.ui.UI;
import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import javax.sound.midi.Soundbank;
import java.util.Map;

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

        /*Så skærmen følger spiller*/
        getGameScene().getViewport().setBounds(-1500, 0, 1500, getHeight());
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);

        //getGameWorld().spawn("enemy", 470, 50);

    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(Type.PLAYER,Type.COIN){
            @Override
            protected void onCollisionBegin(Entity player, Entity coin){
                coin.removeFromWorld();
                System.out.println("You have gathered a coin!");
                getGameState().increment("Coins Gathered",+1);//adder en coin til UI tæller.

            }

        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(Type.PLAYER,Type.DOOR){
            @Override
            public void onCollisionBegin(Entity player, Entity door){
                System.out.println("You have reached the door! ");
                    getDisplay().showMessageBox("Level Complete! \n" + "Coins Gathered : " + getGameState().getProperties().put("Coins Gathered","yes")); //alert box
            }
        });
    }
    @Override
    protected void initUI() {
        Text textPixels = new Text();
        textPixels.setTranslateX(50);//UI på 50X
        textPixels.setTranslateY(50);//UI på 50Y

        getGameScene().addUINode(textPixels); //tilføjer UI til scenen/spillet.
        textPixels.setText("Coins Gathered: ");
        textPixels.textProperty().bind(getGameState().intProperty("Coins Gathered").asString());//Printer pixelsMoved

    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("text","Coins Gathered ");
        vars.put("Coins Gathered", 0);
    }

    public static void main(String[] args) {
        launch(args);

        }
}
