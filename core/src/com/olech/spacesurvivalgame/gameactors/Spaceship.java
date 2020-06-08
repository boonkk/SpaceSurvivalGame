package com.olech.spacesurvivalgame.gameactors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Spaceship extends BasicActor {
    private Thruster thrusters;
    private Shield shield;
    public int shieldPower;
    private String[] shipFiles = {"ship1.png", "ship2.png", "ship3.png", "ship4.png"};

    public Spaceship(float x, float y, Stage s, int shipNo) {
        super(x, y, s);

        loadTexture( shipFiles[shipNo]);
        setBoundaryPolygon(8);
        setAcceleration(400);
        setMaxSpeed(250);
        setDeceleration(10);
        thrusters = new Thruster(0,0, s);
        addActor(thrusters);
        thrusters.setPosition( -thrusters.getWidth()+5, getHeight()/2 - thrusters.getHeight()/2 );

        shield = new Shield(0,0, s);
        addActor(shield);
        shield.centerAtPosition( getWidth()/2, getHeight()/2 );
        shieldPower = 100;
    }

    public void act(float dt)
    {
        super.act( dt );
        float degreesPerSecond = 120; // rotation speed
        if ( Gdx.input.isKeyPressed(Input.Keys.LEFT))
            rotateBy(degreesPerSecond * dt);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            rotateBy(-degreesPerSecond * dt);
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            accelerateAtAngle( getRotation() );

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            accelerateAtAngle( getRotation() );
            thrusters.setVisible(true);
        } else {
            thrusters.setVisible(false);
        }

        shield.setOpacity(shieldPower / 100f);
        if (shieldPower <= 0)
            shield.setVisible(false);

        //alignCamera();
        applyPhysics(dt);
        wrapAroundWorld();
    }

    public void warp() {
        if ( getStage() == null)
            return;
        Warp warp1 = new Warp(0,0, this.getStage());
        warp1.centerAtActor(this);
        setPosition(MathUtils.random(800), MathUtils.random(600));
        Warp warp2 = new Warp(0,0, this.getStage());
        warp2.centerAtActor(this);
    }

    public void shoot() {
        if ( getStage() == null )
            return;
        Laser laser = new Laser(0,0, this.getStage());
        laser.centerAtActor(this);
        laser.setRotation( this.getRotation() );
        laser.setMotionAngle( this.getRotation() );
    }

}
