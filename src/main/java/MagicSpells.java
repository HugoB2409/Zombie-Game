import engine.Buffer;
import engine.CollidableRepository;
import engine.Direction;
import engine.MovableGameEntity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MagicSpells extends MovableGameEntity {

    private static final String SPRITE_PATH = "Image/projectile.png";
    private static final int ANIMATION_SPEED = 80;
    private BufferedImage spriteSheet;
    private BufferedImage[] animation;
    private Direction playerDirection;
    private int currentAnimationFrame = 2;
    private int nextFrame = ANIMATION_SPEED;
    private int degat = 40;

    public MagicSpells(Player player) {
        loadSpriteSheet();
        loadAnimationFrames();
        playerDirection = player.getDirection();
        setSpeed(5);

        if (playerDirection == Direction.RIGHT) {
            super.teleport(player.getX() + player.getWidth() + 1, player.getY() + 35);
            super.setDimension(20, 25);
        }else if (playerDirection == Direction.LEFT) {
            super.teleport(player.getX() - 25, player.getY() + 35);
            super.setDimension(20, 25);
        }else if (playerDirection == Direction.DOWN) {
            super.teleport(player.getX() + 20, player.getY() + player.getHeight());
            super.setDimension(20, 25);
        }else if (playerDirection == Direction.UP) {
            super.teleport(player.getX() + 23, player.getY() - 25);
            super.setDimension(20, 25);
        }
        CollidableRepository.getInstance().registerEntity(this);
    }

    @Override
    public void update() {
        move(playerDirection);
    }

    private void loadSpriteSheet() {
        try {
            spriteSheet = ImageIO.read(this.getClass().getResource(SPRITE_PATH));
        } catch (IOException ex) {}
    }

    private void loadAnimationFrames() {
        animation = new BufferedImage[3];
        animation[0] = spriteSheet.getSubimage(10, 6, 27, 30);
        animation[1] = spriteSheet.getSubimage(63, 6, 27, 25);
        animation[2] = spriteSheet.getSubimage(130, 6, 20, 25);
    }

    public void draw(Buffer buffer, Camera camera) {
        buffer.drawImage(animation[currentAnimationFrame], x + camera.getCamX(), y + camera.getCamY());
    }

    public void draw(Buffer buffer) {}

    public int getDegat() {
        return degat;
    }

    public void setDegat(int degat) {
        this.degat = degat;
    }
}
