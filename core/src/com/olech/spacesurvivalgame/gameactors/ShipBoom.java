package com.olech.spacesurvivalgame.gameactors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class ShipBoom extends BasicActor {
    public ShipBoom(float x, float y, Stage s)
    {
        super(x,y,s);
        loadAnimationFromSheet("boom.png", 2, 5, 0.12f, false);
    }
    public void act(float dt)
    {
        super.act(dt);
        if ( isAnimationFinished() )
            remove();
    }
}
