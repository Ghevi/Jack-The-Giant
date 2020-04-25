package player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import helpers.GameInfo;

public class Player extends Sprite {

    private World world;
    private Body body;

    private TextureAtlas playerAtlas;
    private Animation<TextureRegion> animation;
    private float elapsedTime;

    private boolean isWalking;



    public Player(World world, float x, float y){
        super(new Texture("Player/Player 1.png"));
        this.world = world;
        setPosition(x, y);
        createBody();
        playerAtlas = new TextureAtlas("Player Animation/Player Animation.atlas");
    }

    void createBody(){
        BodyDef bodyDef = new BodyDef(); // The player image itself
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);

        body = world.createBody(bodyDef);
        body.setFixedRotation(true);

        PolygonShape shape = new PolygonShape(); // The "hit-box" around the player for collision detection
        shape.setAsBox((getWidth() / 2f - 20) / GameInfo.PPM, (getHeight() / 2f) / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 4f;  // Mass of the body
        fixtureDef.friction = 2f;   // Will make player not slide on surfaces
        fixtureDef.shape = shape;

        Fixture fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }

    public void movePlayer(float x){
        if(x < 0 && !this.isFlipX()){
            // moving left, flip the player to face left
            this.flip(true, false);
        } else if(x > 0 && this.isFlipX()){
            // moving right, flip the player to face right
            this.flip(true, false);
        }

        isWalking = true;
        body.setLinearVelocity(x, body.getLinearVelocity().y);
    }

    public void drawPlayerIdle(SpriteBatch batch){
        if(!isWalking){
            batch.draw(this, getX() + getWidth() / 2f - 4, getY() - getHeight() / 2f);
        }
    }

    public void drawPlayerAnimation(SpriteBatch batch){
        if(isWalking){
            elapsedTime += Gdx.graphics.getDeltaTime();

            Array<TextureAtlas.AtlasRegion> frames = playerAtlas.getRegions();

            for(TextureRegion frame : frames){
                if(body.getLinearVelocity().x < 0 && !frame.isFlipX()){
                    frame.flip(true, false); // only flip the x axe.
                } else if(body.getLinearVelocity().x > 0 && frame.isFlipX()){
                    frame.flip(true, false);
                }
            }

            animation = new Animation<TextureRegion>(1f/10f, playerAtlas.getRegions()); // Each animation frame is a region in the player animation.png
            batch.draw(animation.getKeyFrame(elapsedTime, true), getX() + getWidth() / 2f - 4, getY() - getHeight() / 2f);
        }
    }

    public void updatePlayer(){
        setPosition(body.getPosition().x * GameInfo.PPM, body.getPosition().y * GameInfo.PPM);
    }

    public void setWalking(boolean isWalking) {
        this.isWalking = isWalking;
    }
} // player














