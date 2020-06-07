package com.olech.spacesurvivalgame.gameactors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Explosion extends BasicActor
{
    public Explosion(float x, float y, Stage s)
    {
        super(x,y,s);
        loadAnimationFromSheet("rockexplosion.png", 1, 6, 0.1f, false);
    }
    public void act(float dt)
    {
        super.act(dt);
        if ( isAnimationFinished() )
            remove();
    }
}
