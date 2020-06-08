package com.olech.spacesurvivalgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

public abstract class BaseGame extends Game {
    private static BaseGame game;

    public BaseGame() {
        game = this;
    }

    @Override
    public void create() {
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor( im );
    }

    public static void setActiveScreen(BaseScreen s) {
        game.setScreen(s);
    }
}
