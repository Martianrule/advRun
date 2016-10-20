package com.gyarbete.advrun;

/**
 * Created by isakms on 2016-10-06.
 */

import com.badlogic.gdx.Game;

public class Platformer extends Game {

    @Override
    public void create () {
        setScreen(new MainMenu  (this));
    }

}
