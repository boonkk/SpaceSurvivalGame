package com.olech.spacesurvivalgame.gameactors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Warp extends BasicActor
{
    public Warp(float x, float y, Stage s) {
        super(x,y,s);
        loadAnimationFromSheet("warp.png", 3, 3, 0.1f, true);
        addAction( Actions.delay(1) );
        addAction( Actions.after( Actions.fadeOut(0.5f) ) );
        addAction( Actions.after( Actions.removeActor() ) );
    }
}
