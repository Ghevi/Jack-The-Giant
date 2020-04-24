package clouds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import helpers.GameInfo;

public class Cloud extends Sprite {

    private World world;
    private Body body;
    private String cloudName;

    public Cloud(World world, String cloudName){
        super(new Texture("Clouds/" + cloudName + ".png"));
        this.world = world;
        this.cloudName = cloudName;
    }

    void createBody(){
        BodyDef bodyDef = new BodyDef(); // The cloud image itself
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((getX() - 40) / GameInfo.PPM, getY() / GameInfo.PPM);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape(); // The "hit-box" around the image for collision detection
        shape.setAsBox((getWidth() / 2 - 22) / GameInfo.PPM, (getHeight() / 2) / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        Fixture fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }

    public void setSpritePosition(float x, float y){
        setPosition(x, y);
        createBody();
    }

    public String getCloudName(){
        return this.cloudName;
    }

} // cloud
