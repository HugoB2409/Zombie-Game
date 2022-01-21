import engine.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ZombieGame extends Game {

    private PlayerController controller;
    private Player player;
    private ArrayList<MagicSpells> magicSpells;
    private ArrayList<Zombie> zombies;
    private Map map;
    private Camera camera;
    private BufferedImage mapImage;
    private static final String MAP_PATH = "Map/mapImage.png";
    private int spawnCooldown = 0;
    private int spawnTime = 800;
    private boolean gameOver = false;
    private int wave = 0;
    private boolean ending = false;

    public void initialize() {
        magicSpells = new ArrayList<>();
        zombies = new ArrayList<>();
        controller = new PlayerController();
        camera = new Camera();
        super.addKeyListener(controller);
        player = new Player(controller, camera);
        player.teleport(200, 300);
        map = new Map(camera);
        map.getColisionPos();
        loadSpriteSheet();
        zombies.add(new Zombie(500, 100));
        SoundEffectFactory.backgroundMusic().setVolume(SoundEffect.Volume.LOW);
        SoundEffectFactory.backgroundMusic().loop();
    }

    private void loadSpriteSheet() {
        try {
            mapImage = ImageIO.read(this.getClass().getResource(MAP_PATH));
        } catch (IOException ex) {}
    }

    public void update() {
        if (controller.isScreenSwitchPressed()) {
            RenderingEngine.getInstance().getScreen().toggleFullScreen();
        }
        if(controller.isQuitPressed()) {
            super.stop();
        }
        if(player.getHealth() > 0) {
            if (controller.isFirePressed() && player.canFire()) {
                SoundEffectFactory.spellCast().play();
                magicSpells.add(player.fire());
            }
            if(player.getX() > 1720 && player.getX() < 2000 && player.getY() > 1640 && player.getY() < 1900) {
                if(player.getHealth() != player.getMaxHealth()) {
                    player.setHealth(player.getHealth() + 5);
                }
            }
            player.update();
            spawnCooldown++;
            if(spawnCooldown == spawnTime) {
                zombies.add(new Zombie(500, 100));
                zombies.add(new Zombie(2800, 125));
                zombies.add(new Zombie(400, 2900));
                zombies.add(new Zombie(2700, 2600));
                wave++;
                spawnCooldown = 0;
                spawnTime -= 10;
            }
            for (Zombie zombie : zombies) {
                zombie.update(player);
            }
            ArrayList<GameEntity> killedEntities = new ArrayList<>();

            for (MagicSpells magicSpells : this.magicSpells) {
                magicSpells.update();
                for (mapObject objet : map.getObjets()) {
                    if (magicSpells.collisionBoundIntersectWith(objet)) {
                        killedEntities.add(magicSpells);
                    }
                }
                for (Zombie zombie : zombies) {
                    if (magicSpells.collisionBoundIntersectWith(zombie)) {
                        zombie.setHealth(zombie.getHealth() - magicSpells.getDegat());
                        if (zombie.getHealth() <= 0) {
                            killedEntities.add(zombie);
                            SoundEffectFactory.zombieDeath().playWithoutInterrupt();
                        }
                        killedEntities.add(magicSpells);
                    }
                }
            }
            for (GameEntity killedEntity : killedEntities) {
                if (killedEntity instanceof Zombie) {
                    zombies.remove(killedEntity);
                } else if (killedEntity instanceof MagicSpells) {
                    magicSpells.remove(killedEntity);
                }
                CollidableRepository.getInstance().unregisterEntity(killedEntity);
            }
        } else {
            gameOver = true;
            conclude();
        }
    }

    @Override
    protected void draw(Buffer buffer) {
        if(!gameOver) {
            buffer.drawImage(mapImage, camera.getCamX(), camera.getCamY());

            for (MagicSpells magicSpells : this.magicSpells) {
                magicSpells.draw(buffer, camera);
            }
            player.draw(buffer);
            for (Zombie zombie : zombies) {
                zombie.draw(buffer, camera);
            }
            buffer.drawText("FPS: " + GameTime.getCurrentFps(), 10, 20, Color.WHITE);
            buffer.drawText("Vague: " + wave, 325, 20, Color.WHITE);
            buffer.drawRectangle(400, 10, 60, 10, Color.BLACK);
            buffer.drawRectangle(400, 10, (spawnCooldown * 60) / 800 , 10, Color.WHITE);
            buffer.drawText("Nombre de zombie: " + zombies.size(), 480, 20, Color.WHITE);
        } else {
            buffer.drawRectangle(0, 0, 800, 600, Color.BLACK);
            buffer.drawText("Game Over", 380, 300, Color.WHITE);
            buffer.drawText("Vous avez atteint la vague : " + wave, 340, 340, Color.WHITE);
        }
    }

    public void conclude() {
        if(!ending) {
            ending();
        }
    }

    private void ending() {
        SoundEffectFactory.backgroundMusic().stop();
        SoundEffectFactory.gameover().playWithoutInterrupt();
        ending = true;
    }

}
