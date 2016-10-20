package com.gyarbete.advrun;

/**
 * Created by isakms on 2016-10-06.
 */


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.gyarbete.advrun.Map;
import com.gyarbete.advrun.MapRenderer;

public class GameScreen extends AbstractScreen {

    Map map;
    MapRenderer mapRenderer;

    public GameScreen (Game game) {
        super(game);
    }

    @Override
    public void show () {
        map = new Map();
        mapRenderer = new MapRenderer(map);
    }

    @Override
    public void render (float delta) {

        delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());
        map.update(delta);
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.render(delta);

        if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
            game.setScreen(new MainMenu(game));
        }
    }

    @Override
    public void hide () {
        Gdx.app.debug("Platformer", "dispose game screen");

    }
}
