import engine.Buffer;
import engine.CollidableRepository;
import engine.GameEntity;
import java.awt.*;

public class mapObject extends GameEntity {

    public mapObject(int x, int y) {
        super.setDimension(16, 16);
        super.teleport(x, y);
        CollidableRepository.getInstance().registerEntity(this);
    }

    public void draw(Buffer buffer) {}
}

