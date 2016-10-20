package com.gyarbete.advrun;

/**
 * Created by isakms on 2016-10-06.
 */




import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Array;

public class Map {

    static int EMPTY = 0;
    static int TILE = 0xffffff;
    static int START = 0xff0000;
    static int END = 0xff00ff;
    static int DISPENSER = 0xff0100;
    static int SPIKES = 0x00ff00;
    static int ROCKET = 0x0000ff;
    static int MOVING_SPIKES = 0xffff00;
    static int LASER = 0x00ffff;

    int[][] tiles;

    public Bob bob;
    public EndDoor endDoor;

    Array<Dispenser> dispensers;
    Dispenser activeDispenser = null;
    Array<Rocket> rockets = new Array<Rocket>();
    Array<MovingSpikes> movingSpikes = new Array<MovingSpikes>();
    Array<Laser> lasers = new Array<Laser>();
    int score;

    public Map () {
        loadBinary();
        score = 0;
    }

    private void loadBinary () {

        dispensers = new Array<Dispenser>();

        Pixmap pixmap = new Pixmap(Gdx.files.internal("data/levels.png"));

        tiles = new int[pixmap.getWidth()][pixmap.getHeight()];

        for (int y = 0; y < 35; y++) {

            for (int x = 0; x < 150; x++) {

                int pix = (pixmap.getPixel(x, y) >>> 8) & 0xffffff;

                if (match(pix, START)) {
                    Dispenser dispenser = new Dispenser(x, pixmap.getHeight() - 1 - y);
                    dispensers.add(dispenser);
                    activeDispenser = dispenser;
                    bob = new Bob(this, activeDispenser.bounds.x, activeDispenser.bounds.y);
                    bob.state = Bob.SPAWN;
                } else if (match(pix, DISPENSER)) {
                    Dispenser dispenser = new Dispenser(x, pixmap.getHeight() - 1 - y);
                    dispensers.add(dispenser);
                } else if (match(pix, ROCKET)) {
                    Rocket rocket = new Rocket(this, x, pixmap.getHeight() - 1 - y);
                    rockets.add(rocket);
                } else if (match(pix, MOVING_SPIKES)) {
                    movingSpikes.add(new MovingSpikes(this, x, pixmap.getHeight() - 1 - y));
                } else if (match(pix, LASER)) {
                    lasers.add(new Laser(this, x, pixmap.getHeight() - 1 - y));
                } else if (match(pix, END)) {
                    endDoor = new EndDoor(x, pixmap.getHeight() - 1 - y);
                } else {
                    tiles[x][y] = pix;
                }
            }
        }

        for (int i = 0; i < movingSpikes.size; i++) {
            movingSpikes.get(i).init();
        }
        for (int i = 0; i < lasers.size; i++) {
            lasers.get(i).init();
        }

    }

    boolean match (int src, int dst) {
        return src == dst;
    }

    public void update (float deltaTime) {

        if(deltaTime > 0.0){
            bob.update(deltaTime);
        }

        if (bob.state == Bob.DEAD) {
            bob = new Bob(this, activeDispenser.bounds.x, activeDispenser.bounds.y);
        }

        for (int i = 0; i < dispensers.size; i++) {
            if (bob.bounds.overlaps(dispensers.get(i).bounds)) {
                if(activeDispenser != dispensers.get(i)){
                    activeDispenser = dispensers.get(i);
                    score += 1;
                }
            }
        }

        for (int i = 0; i < rockets.size; i++) {
            Rocket rocket = rockets.get(i);
            rocket.update(deltaTime);
        }
        for (int i = 0; i < movingSpikes.size; i++) {
            MovingSpikes spikes = movingSpikes.get(i);
            spikes.update(deltaTime);
        }
        for (int i = 0; i < lasers.size; i++) {
            lasers.get(i).update();
        }
    }

    public boolean isDeadly (int tileId) {
        return tileId == SPIKES;
    }

    public int getScore() {
        return score;
    }
}