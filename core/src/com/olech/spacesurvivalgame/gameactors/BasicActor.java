package com.olech.spacesurvivalgame.gameactors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.stream.Stream;

public class BasicActor extends Group {
    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean animationPaused;
    private Vector2 velocityVector;
    private Vector2 accelerationVector;
    private float acceleration;
    private float maxSpeed;
    private float deceleration;
    private Polygon boundaryPolygon;
    private static Rectangle worldBounds;


    public BasicActor(float x, float y, Stage s) {
        super();

        setPosition(x,y);
        s.addActor(this);
        velocityVector = new Vector2(0,0);
        accelerationVector = new Vector2(0,0);
        acceleration = 0;
        maxSpeed = 1000;
        deceleration = 0;


    }

    public void setAnimation(Animation<TextureRegion> anim) {
        animation = anim;
        TextureRegion tr = animation.getKeyFrame(0);
        float w = tr.getRegionWidth();
        float h = tr.getRegionHeight();
        setSize( w, h );
        setOrigin( w/2, h/2 );
        if (boundaryPolygon == null)
            setBoundaryRectangle();
    }

    public void pauseAnimation(boolean pause) {
        animationPaused = pause;
    }

    @Override
    public void act(float dt) {
        super.act( dt );
        if (!animationPaused)
            elapsedTime += dt;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a);
        if ( animation != null && isVisible() )
            batch.draw( animation.getKeyFrame(elapsedTime),
                    getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );
        super.draw( batch, parentAlpha );
    }

    public Animation<TextureRegion> loadAnimationFromSheet(String fileName, int rows, int cols, float frameDuration, boolean loop) {
        Texture texture = new Texture(Gdx.files.internal(fileName), true);
        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() / rows;
        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);
        Array<TextureRegion> textureArray = new Array<>();

        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                textureArray.add( temp[row][col] );
                Animation<TextureRegion> anim = new Animation<>(frameDuration, textureArray);

        if (loop)
            anim.setPlayMode(Animation.PlayMode.LOOP);
        else
            anim.setPlayMode(Animation.PlayMode.NORMAL);
        if (animation == null)
            setAnimation(anim);
        return anim;
    }



    public Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames, float frameDuration, boolean loop) {
        Array<TextureRegion> textureArray = new Array<>();
        Stream.of(fileNames)
                .forEach(fileName -> {
                    Texture texture = new Texture( Gdx.files.internal(fileName) );
                    texture.setFilter( Texture.TextureFilter.Linear, Texture.TextureFilter.Linear );
                    textureArray.add( new TextureRegion( texture ) );
                });

        Animation<TextureRegion> anim = new Animation<>(frameDuration, textureArray);

        if (loop)
            anim.setPlayMode(Animation.PlayMode.LOOP);
        else
            anim.setPlayMode(Animation.PlayMode.NORMAL);

        if (animation == null)
            setAnimation(anim);
        return anim;
    }

    public Animation<TextureRegion> loadTexture(String fileName) {
        String[] fileNames = new String[1];
        fileNames[0] = fileName;
        return loadAnimationFromFiles(fileNames, 1, true);
    }

    public boolean isAnimationFinished() {
        return animation.isAnimationFinished(elapsedTime);
    }

    public void setSpeed(float speed) {
        if ( velocityVector.len() == 0)
            velocityVector.set(speed, 0);
        else
            velocityVector.setLength(speed);
    }

    public float getSpeed() {
        return velocityVector.len();
    }

    public void setMotionAngle(float angle) {
        velocityVector.setAngle(angle);
    }

    public float getMotionAngle() {
        return velocityVector.angle();
    }

    public boolean isMoving() {
        return (getSpeed() > 0);
    }

    public void setAcceleration(float acc) {
        acceleration = acc;
    }

    public void accelerateAtAngle(float angle) {
        accelerationVector.add( new Vector2(acceleration, 0).setAngle(angle) );
    }

    public void accelerateForward() {
        accelerateAtAngle( getRotation() );
    }

    public void setMaxSpeed(float ms) {
        maxSpeed = ms;
    }

    public void setDeceleration(float dec) {
        deceleration = dec;
    }

    public void applyPhysics(float dt) {
        velocityVector.add( accelerationVector.x * dt, accelerationVector.y * dt );
        float speed = getSpeed();
        if ( accelerationVector.len() == 0)
            speed -= deceleration * dt;
        speed = MathUtils.clamp(speed, 0, maxSpeed);
        setSpeed(speed);
        moveBy( velocityVector.x * dt, velocityVector.y * dt );
        accelerationVector.set(0,0);
    }

    public void setBoundaryRectangle() {
        float w = getWidth();
        float h = getHeight();
        float[] vertices = {0,0, w,0, w,h, 0,h};
        boundaryPolygon = new Polygon(vertices);
    }

    public void setBoundaryPolygon(int numSides) {
        float w = getWidth();
        float h = getHeight();
        float[] vertices = new float[2*numSides];
        for (int i = 0; i < numSides; i++) {
            float angle = i * 6.28f / numSides;
            // x-coordinate
            vertices[2*i] = w/2 * MathUtils.cos(angle) + w/2;
            // y-coordinate
            vertices[2*i+1] = h/2 * MathUtils.sin(angle) + h/2;
        }
        boundaryPolygon = new Polygon(vertices);
    }

    public Polygon getBoundaryPolygon() {
        boundaryPolygon.setPosition( getX(), getY() );
        boundaryPolygon.setOrigin( getOriginX(), getOriginY() );
        boundaryPolygon.setRotation ( getRotation() );
        boundaryPolygon.setScale( getScaleX(), getScaleY() );
        return boundaryPolygon;
    }

    public boolean overlaps(BasicActor other) {
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();
        if ( !poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()) )
            return false;
        return Intersector.overlapConvexPolygons( poly1, poly2 );
    }

    public void centerAtPosition(float x, float y) {
        setPosition( x - getWidth()/2 , y - getHeight()/2 );
    }

    public void centerAtActor(BasicActor other) {
        centerAtPosition( other.getX() + other.getWidth()/2 , other.getY() + other.getHeight()/2 );
    }

    public void setOpacity(float opacity) {
        this.getColor().a = opacity;
    }

    public Vector2 preventOverlap(BasicActor other) {
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();
        if ( !poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()) )
            return null;
        MinimumTranslationVector mtv = new MinimumTranslationVector();
        boolean polygonOverlap = Intersector.overlapConvexPolygons(poly1, poly2, mtv);
        if ( !polygonOverlap )
            return null;
        this.moveBy( mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth );
        return mtv.normal;
    }

    public static ArrayList<BasicActor> getList(Stage stage, String className) {
        ArrayList<BasicActor> list = new ArrayList<>();
        Class theClass = null;
        try {
            theClass = Class.forName(className);
        }
        catch (Exception error) {
            error.printStackTrace();
        }
        for (Actor a : stage.getActors()) {
            if (theClass.isInstance( a ))
                list.add( (BasicActor)a );
        }
        return list;
    }


    public static int count(Stage stage, String className) {
        return getList(stage, className).size();
    }

    public static void setWorldBounds(float width, float height) {
        worldBounds = new Rectangle( 0,0, width, height );
    }
    public static void setWorldBounds(BasicActor ba) {
        setWorldBounds( ba.getWidth(), ba.getHeight() );
    }

    public void boundToWorld() {
        if (getX() < 0)
            setX(0);
        if (getX() + getWidth() > worldBounds.width)
            setX(worldBounds.width - getWidth());
        if (getY() < 0)
            setY(0);
        if (getY() + getHeight() > worldBounds.height)
            setY(worldBounds.height - getHeight());
    }

    public void alignCamera() {
        Camera cam = this.getStage().getCamera();
        Viewport v = this.getStage().getViewport();
        cam.position.set( this.getX() + this.getOriginX(), this.getY() + this.getOriginY(), 0 );
        cam.position.x = MathUtils.clamp(cam.position.x,
                cam.viewportWidth/2, worldBounds.width - cam.viewportWidth/2);
        cam.position.y = MathUtils.clamp(cam.position.y,
                cam.viewportHeight/2, worldBounds.height - cam.viewportHeight/2);
        cam.update();
    }

    public void wrapAroundWorld() {
        if (getX() + getWidth() < 0)
            setX( worldBounds.width );
        if (getX() > worldBounds.width)
            setX( -getWidth());
        if (getY() + getHeight() < 0)
            setY( worldBounds.height );
        if (getY() > worldBounds.height)
            setY( -getHeight() );
    }

}
