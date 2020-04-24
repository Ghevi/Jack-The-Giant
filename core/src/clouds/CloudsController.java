package clouds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import helpers.GameInfo;

public class CloudsController {

    private World world;

    private final float DISTANCE_BETWEEN_CLOUDS = 250f;
    private float minX, maxX;

    private Random random = new Random();

    private Array<Cloud> clouds = new Array<Cloud>();

    public CloudsController(World world){
        this.world = world;
        minX = GameInfo.WIDTH / 2f - 120;
        maxX = GameInfo.WIDTH / 2f + 120;
        createClouds();
        positionClouds();
    }

    void createClouds() {  // Creates 2 dark clouds and 6 normal clouds
        for(int i = 0; i < 2; i++){
            clouds.add(new Cloud(world, "Dark Cloud"));
        }

        // Iterates trough the 3 normal clouds files in the clouds folder
        for(int i = 0, cloudFileIndex = 1; i < 6; i++){
            clouds.add(new Cloud(world, "Cloud " + cloudFileIndex));
            cloudFileIndex++;

            if(cloudFileIndex == 4)
                cloudFileIndex = 1;
        }

        clouds.shuffle(); // Automatically randomized the clouds order
    }

    public void positionClouds(){

        // make sure the first cloud is never a dark cloud
        while(clouds.get(0).getCloudName().equals("Dark Cloud")){
            clouds.shuffle();
        }

        float positionY = GameInfo.HEIGHT / 2f;

        int controlX = 0;

        for(Cloud c : clouds){
            float tempX = 0;

            // Makes sure clouds are rendered both right and left in respect to the middle of the screen
            if(controlX == 0){
                tempX = randomBetweenNumbers(maxX - 40, maxX);
                controlX = 1;
            } else if(controlX == 1){
                tempX = randomBetweenNumbers(minX + 40, minX);
                controlX = 0;
            }

            c.setSpritePosition(tempX, positionY);
            positionY -= DISTANCE_BETWEEN_CLOUDS;
        }
    }

    public void drawClouds(SpriteBatch batch){
        for(Cloud c : clouds){
            batch.draw(c, c.getX() - c.getWidth() / 2f, c.getY() - c.getHeight() / 2f);
        }
    }

    private float randomBetweenNumbers(float min, float max){
        return random.nextFloat() * (max - min) + min;
    }

} // CloudsController














