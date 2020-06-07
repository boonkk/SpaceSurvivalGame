package com.olech.spacesurvivalgame.gameactors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Laser extends BasicActor
{
    public Laser(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture("laser.png");
        addAction( Actions.delay(0.6f) );
        addAction( Actions.after( Actions.fadeOut(0.4f) ) );
        addAction( Actions.after( Actions.removeActor() ) );
        setSpeed(700);
        setMaxSpeed(700);
        setDeceleration(0);
    }
    public void act(float dt)
    {
        super.act(dt);
        applyPhysics(dt);
        wrapAroundWorld();
    }
}
