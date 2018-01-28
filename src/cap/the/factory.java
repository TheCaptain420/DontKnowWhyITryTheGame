package cap.the;


import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

@SetEntityFactory
public class factory implements EntityFactory {

    @Spawns("platform")
    public Entity newPlatform(SpawnData data) {
        return Entities.builder()
                .type(Type.PLATFORM)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }
    @Spawns("coin")
    public Entity newCoin(SpawnData data) {
        return Entities.builder()
                .type(Type.COIN)
                .from(data)
                .viewFromNodeWithBBox(new Circle(data.<Integer>get("width")/2, Color.GOLD))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        return Entities.builder()
                .type(Type.PLAYER)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(32, 42)))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new PlayerControl())
                .build();
    }


    @Spawns("door")
    public Entity newDoor(SpawnData data) {
        return Entities.builder()
                .from(data)
                .type(Type.DOOR)
                .viewFromNodeWithBBox(new Circle(data.<Integer>get("width")/2, Color.GOLD))
                .build();
    }



}
