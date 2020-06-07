package com.olech.spacesurvivalgame.gameactors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Thruster extends BasicActor
{
    public Thruster(float x, float y, Stage s)
    {
        super(x,y,s);
        loadAnimationFromSheet("fire.png",3,2,0.25f,true);
    }
}
