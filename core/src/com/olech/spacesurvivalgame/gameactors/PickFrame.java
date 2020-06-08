package com.olech.spacesurvivalgame.gameactors;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class PickFrame extends BasicActor {
    public PickFrame(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("pickframe.png");
        Action pulse = Actions.sequence(
                Actions.scaleTo(0.9f, 0.9f, 0.7f), Actions.scaleTo(1.1f, 1.1f, 0.7f) );
        addAction( Actions.forever(pulse) );
    }

}
