package com.olech.spacesurvivalgame.gameactors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class PickFrame extends BasicActor {
    public PickFrame(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("pickframe.png");
    }
}
