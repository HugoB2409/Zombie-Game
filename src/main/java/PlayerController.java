import engine.MovementController;
import java.awt.event.KeyEvent;

public class PlayerController extends MovementController {

    private final int QUIT = KeyEvent.VK_ESCAPE;
    private final int FIRE = KeyEvent.VK_E;
    private final int SCREEN_SWITCH = KeyEvent.VK_F;

    public PlayerController() {
        super.registerKey(QUIT);
        super.registerKey(SCREEN_SWITCH);
        super.registerKey(FIRE);
    }

    public boolean isFirePressed() {
        return super.isKeyPressed(FIRE);
    }

    public boolean isQuitPressed() {
        return super.isKeyPressed(QUIT);
    }

    public boolean isScreenSwitchPressed() {
        return super.isKeyPressed(SCREEN_SWITCH);
    }
}
