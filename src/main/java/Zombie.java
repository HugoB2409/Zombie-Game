import engine.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Zombie extends MovableGameEntity {
    private static final String SPRITE_PATH = "Image/zombie.png";
    private static final int ANIMATION_SPEED = 8;
    private BufferedImage spriteSheet;
    private BufferedImage[] back;
    private BufferedImage[] right;
    private BufferedImage[] front;
    private BufferedImage[] left;
    private Camera camera;

    private int currentAnimationFrame = 0;
    private int nextFrame = ANIMATION_SPEED;
    private int cooldown = 0;
    private int health = 100;
    private int attackCooldown = 0;

    public Zombie(int x, int y) {
        super.setDimension(65, 85);
        super.setSpeed(1);
        loadSpriteSheet();
        loadAnimationFrames();
        super.teleport(x, y);
        CollidableRepository.getInstance().registerEntity(this);
    }

    public void draw(Buffer buffer, Camera camera) {
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
        buffer.drawRectangle(x + camera.getCamX() - 1, y + camera.getCamY() - 10, 60, 10, Color.red);
        buffer.drawRectangle(x + camera.getCamX() - 1, y + camera.getCamY() - 10, (health * 60) / 100 , 10, Color.green);
    }

    public void draw(Buffer buffer) {}

    @Override
    public void update() {}

    public void update(Player player) {
        if (collisionBoundIntersectWith(player)) {
            attackCooldown++;
            if(attackCooldown == 50) {
                player.setHealth(player.getHealth() - 500);
                SoundEffectFactory.zombieAttack().playWithoutInterrupt();
                attackCooldown = 0;
            }

        } else {
            if (player.getX() > x) {
                moveRight();
            } else if (player.getX() < x) {
                moveLeft();
            }
            if (player.getY() > y) {
                moveDown();
            } else if (player.getY() < y){
                moveUp();
            }
            animation();
        }
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

    private void loadSpriteSheet() {
        try {
            spriteSheet = ImageIO.read(this.getClass().getResource(SPRITE_PATH));
        } catch (IOException ex) {}
    }

    private void loadAnimationFrames() {
        front = new BufferedImage[3];
        front[0] = spriteSheet.getSubimage(1, 0, 57, 78);
        front[1] = spriteSheet.getSubimage(80, 0, 57, 78);
        front[2] = spriteSheet.getSubimage(155, 0, 57, 78);

        left = new BufferedImage[3];
        left[0] = spriteSheet.getSubimage(1, 80, 57, 78);
        left[1] = spriteSheet.getSubimage(80, 80, 57, 78);
        left[2] = spriteSheet.getSubimage(155, 80, 57, 78);

        right = new BufferedImage[3];
        right[0] = spriteSheet.getSubimage(1, 156, 57, 78);
        right[1] = spriteSheet.getSubimage(80, 156, 57, 78);
        right[2] = spriteSheet.getSubimage(155, 156, 57, 78);

        back = new BufferedImage[3];
        back[0] = spriteSheet.getSubimage(1, 233, 57, 78);
        back[1] = spriteSheet.getSubimage(80, 233, 57, 78);
        back[2] = spriteSheet.getSubimage(155, 233, 57, 78);
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }
}
