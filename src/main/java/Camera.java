import engine.Direction;

public class Camera {
    private int camX, camY = 0;
    private int lastx, lasty = 0;

    public void update(int x, int y, Direction direction) {
        if(!(x == lastx && y == lasty)) {
            lastx = x;
            lasty = y;

            if (y <= 300 && x <= 400 || y <= 300 && x >= 2900 || y >= 2900 && x <= 400 || y >= 2900 && x >= 2800) {

            } else if (x <= 400 || x >= 2800) {
                if (direction == Direction.UP) {
                    camY = (camY + 4);
                } else if (direction == Direction.DOWN) {
                    camY = (camY - 4);
                }
            } else if (y <= 300 || y >= 2900) {
                if (direction == Direction.LEFT) {
                    camX = (camX + 4);
                } else if (direction == Direction.RIGHT) {
                    camX = (camX - 4);
                }
            } else {
                if (direction == Direction.UP) {
                    camY = (camY + 4);
                } else if (direction == Direction.DOWN) {
                    camY = (camY - 4);
                } else if (direction == Direction.LEFT) {
                    camX = (camX + 4);
                } else if (direction == Direction.RIGHT) {
                    camX = (camX - 4);
                }
            }
        }
    }

    public int getCamX() {
        return camX;
    }

    public int getCamY() {
        return camY;
    }
}
