import engine.SoundEffect;

public class SoundEffectFactory {

    public static SoundEffect spellCast() {
        return new SoundEffect("attack.wav");
    }

    public static SoundEffect zombieAttack() {
        return new SoundEffect("zombieAttack.wav");
    }

    public static SoundEffect zombieDeath() {
        return new SoundEffect("zombieDeath.wav");
    }

    public static SoundEffect gameover() {
        return new SoundEffect("gameover.wav");
    }

    public static SoundEffect backgroundMusic() {
        return new SoundEffect("backgroundMusic.wav");
    }
}