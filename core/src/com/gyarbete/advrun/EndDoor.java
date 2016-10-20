package com.gyarbete.advrun;

/**
 * Created by isakms on 2016-10-06.
 */
import com.badlogic.gdx.math.Rectangle;

public class EndDoor {
    public Rectangle bounds = new Rectangle();

    public EndDoor (float x, float y) {
        this.bounds.x = x;
        this.bounds.y = y;
        this.bounds.width = this.bounds.height = 1;
    }
}