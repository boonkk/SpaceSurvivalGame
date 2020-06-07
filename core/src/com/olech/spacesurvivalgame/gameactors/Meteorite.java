package com.olech.spacesurvivalgame.gameactors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Meteorite extends BasicActor {
    public Meteorite(float x, float y, Stage s) {
        super(x,y,s);
        loadTexture("meteorite.png");
        float random = MathUtils.random(30);
        addAction( Actions.forever( Actions.rotateBy(40 + random, 0.3f) ) );
        addAction( Actions.after( Actions.fadeOut(0.7f) ) );
        addAction(Actions.after(Actions.removeActor(this)));
        setSpeed(1200);
        setMaxSpeed(1200);
        setDeceleration(0);
        setMotionAngle( MathUtils.random(360) );

    }
    public void act(float dt) {
        super.act(dt);
        applyPhysics(dt);
        wrapAroundWorld();
    }
}
