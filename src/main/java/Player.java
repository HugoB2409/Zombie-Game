import engine.Buffer;
import engine.ControllableGameEntity;
import engine.Direction;
import engine.MovementController;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends ControllableGameEntity {

    private static final String SPRITE_PATH = "Image/char.png";
    private static final int ANIMATION_SPEED = 8;
    private int currentAnimationFrame = 0;
    private int nextFrame = ANIMATION_SPEED;
    private BufferedImage spriteSheet;
    private BufferedImage[] back;
    private BufferedImage[] right;
    private BufferedImage[] front;
    private BufferedImage[] left;
    private Camera camera;

    private int cooldown = 0;
    private int health = 10000;
    private final int maxHealth = 10000;

    public Player(MovementController controller, Camera camera) {
        super(controller);
        super.setDimension(65, 85);
        super.setSpeed(4);
        loadSpriteSheet();
        loadAnimationFrames();
        this.camera = camera;
    }

    public boolean canFire() {
        return cooldown == 0;
    }

    public MagicSpells fire() {
        cooldown = 40;
        return new MagicSpells(this);
    }

    @Override
    public void update() {
        moveAccordingToHandler();
        camera.update(x, y, super.getDirection());
        animation();
    }

    private void animation() {
        if(super.hasMoved()) {
            --nextFrame;
            if (nextFrame == 0) {
                ++currentAnimationFrame;
                if (currentAnimationFrame >= back.length) {
                    currentAnimationFrame = 0;
                }
                nextFrame = ANIMATION_SPEED;
            }
        } else {
            currentAnimationFrame = 0;
        }
        cooldown--;
        if(cooldown <= 0) {
            cooldown = 0;
        }

    }

    @Override
    public void draw(Buffer buffer) {
        if(super.getDirection().equals(Direction.UP)) {
            buffer.drawImage(back[currentAnimationFrame], x + camera.getCamX(), y + camera.getCamY());
        }
        if(super.getDirection().equals(Direction.RIGHT)) {
            buffer.drawImage(right[currentAnimationFrame], x + camera.getCamX(), y + camera.getCamY());
        }
        if(super.getDirection().equals(Direction.DOWN)) {
            buffer.drawImage(front[currentAnimationFrame], x + camera.getCamX(), y + camera.getCamY());
        }
        if(super.getDirection().equals(Direction.LEFT)) {
            buffer.drawImage(left[currentAnimationFrame], x + camera.getCamX(), y + camera.getCamY());
        }
        buffer.drawRectangle(200, 550, 400, 30, Color.red);
        buffer.drawRectangle(200, 550, (health * 400) / maxHealth , 30, Color.green);
    }

    private void loadSpriteSheet() {
        try {
            spriteSheet = ImageIO.read(this.getClass().getResource(SPRITE_PATH));
        } catch (IOException ex) {}
    }

    private void loadAnimationFrames() {
        back = new BufferedImage[3];
        back[0] = spriteSheet.getSubimage(15, 0, 65, 85);
        back[1] = spriteSheet.getSubimage(110, 0, 65, 85);
        back[2] = spriteSheet.getSubimage(205, 0, 65, 85);

        right = new BufferedImage[3];
        right[0] = spriteSheet.getSubimage(15, 135, 65, 85);
        right[1] = spriteSheet.getSubimage(110, 135, 65, 85);
        right[2] = spriteSheet.getSubimage(205, 135, 65, 85);

        front = new BufferedImage[3];
        front[0] = spriteSheet.getSubimage(15, 260, 65, 85);
        front[1] = spriteSheet.getSubimage(110, 260, 65, 85);
        front[2] = spriteSheet.getSubimage(205, 260, 65, 85);

        left = new BufferedImage[3];
        left[0] = spriteSheet.getSubimage(15, 390, 65, 85);
        left[1] = spriteSheet.getSubimage(110, 390, 65, 85);
        left[2] = spriteSheet.getSubimage(205, 390, 65, 85);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
