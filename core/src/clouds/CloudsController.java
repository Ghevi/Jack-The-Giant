package clouds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import collectables.Collectable;
import helpers.GameInfo;
import helpers.GameManager;
import player.Player;

public class CloudsController {

    private World world;

    private Array<Cloud> clouds;
    private Array<Collectable> collectables;

    private final float DISTANCE_BETWEEN_CLOUDS = 250f;
    private float minX, maxX;
    private float lastCloudPositionY;
    private float cameraY;

    private Random random;

    public CloudsController(World world){
        this.world = world;
        clouds = new Array<>();
        collectables  = new Array<Collectable>();

        random = new Random();

        minX = GameInfo.WIDTH / 2f - 120;
        maxX = GameInfo.WIDTH / 2f + 120;

        createClouds();
        positionClouds(true);
    }

    private void createClouds() {  // Creates 2 dark clouds and 6 normal clouds
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

    public void positionClouds(boolean firstTimeArranging){

        // make sure the first cloud is never a dark cloud
        while(clouds.get(0).getCloudName().equals("Dark Cloud")){
            clouds.shuffle();
        }

        float positionY = 0;

        if(firstTimeArranging){
            positionY = GameInfo.HEIGHT / 2f;
        } else {
            positionY = lastCloudPositionY;
        }

        int controlX = 0;

        for(Cloud c : clouds){
            if(c.getX() == 0 && c.getY() == 0){
                float tempX = 0;

                // Makes sure clouds are rendered both right and left in respect to the middle of the screen
                if(controlX == 0){
                    tempX = randomBetweenNumbers(maxX - 40, maxX);
                    controlX = 1;
                    c.setDrawLeft(false); // Set clouds on the right side
                } else if(controlX == 1){
                    tempX = randomBetweenNumbers(minX + 40, minX);
                    controlX = 0;
                    c.setDrawLeft(true); // Set clouds on the left side
                }

                c.setSpritePosition(tempX, positionY);

                positionY -= DISTANCE_BETWEEN_CLOUDS;
                lastCloudPositionY =  positionY;

                if(!firstTimeArranging && c.getCloudName() != "Dark Cloud"){
                    int rand = random.nextInt(10);

                    if(rand > 5){
                        int randomCollectable = random.nextInt(2);

                        if(randomCollectable == 0){
                            // spawn a life, if the life count is lower than 2
                            if(GameManager.getInstance().lifeScore < 2){
                                Collectable collectable = new Collectable(world, "Life");
                                collectable.setCollectablePosition(c.getX(), c.getY() + 40);
                                collectables.add(collectable);
                            } else {
                                // spawn a coin
                                Collectable collectable = new Collectable(world, "Coin");
                                collectable.setCollectablePosition(c.getX(), c.getY() + 40);
                                collectables.add(collectable);
                            }
                        } else {
                            // spawn a coin
                            Collectable collectable = new Collectable(world, "Coin");
                            collectable.setCollectablePosition(c.getX(), c.getY() + 40);
                            collectables.add(collectable);
                        }
                    }
                }
            }
        }
          // For testing purposes: This will put a collectable item on the first cloud
//        Collectable collectable = new Collectable(world, "Life" /*, "Coin" */);
//        collectable.setCollectablePosition(clouds.get(1).getX(), clouds.get(1).getY() + 40);
//        collectables.add(collectable);
    }

    public void drawClouds(SpriteBatch batch){
        for(Cloud c : clouds){
            if(c.getDrawLeft()){
                batch.draw(c, c.getX() - c.getWidth() / 2f - 20, c.getY() - c.getHeight() / 2f);
            } else {
                batch.draw(c, c.getX() - c.getWidth() / 2f + 10, c.getY() - c.getHeight() / 2f);
            }
        }
    }

    public void drawCollectables(SpriteBatch batch){
        for(Collectable c : collectables){
            c.updateCollectable();
            batch.draw(c, c.getX(), c.getY());
        }
    }

    public void removeCollectables(){
        for(int i = 0; i < collectables.size; i++){
            if(collectables.get(i).getFixture().getUserData() == "Remove"){
                collectables.get(i).changeFilter();
                collectables.get(i).getTexture().dispose();
                collectables.removeIndex(i);
            }
        }
    }

    public void createAndArrangeNewClouds(){
        for(int i = 0; i < clouds.size; i++){
            if((clouds.get(i).getY() - GameInfo.HEIGHT / 2 - 15) > cameraY){
                clouds.get(i).getTexture().dispose();
                clouds.removeIndex(i);
            }
        }

        if(clouds.size == 4){
            createClouds();
            positionClouds(false);
        }
    }

    public void removeOffScreenCollectables(){
        for(int i = 0; i < collectables.size; i++){
            if((collectables.get(i).getY() - GameInfo.HEIGHT / 2f - 15) > cameraY){
                collectables.get(i).getTexture().dispose();
                collectables.removeIndex(i);
            }
        }
    }

    public void setCameraY(float cameraY){
        this.cameraY = cameraY;
    }

    public Player positionThePlayer(Player player){
        player = new Player(world, clouds.get(0).getX(), clouds.get(0).getY() + 78);
        return player;
    }

    private float randomBetweenNumbers(float min, float max){
        return random.nextFloat() * (max - min) + min;
    }

} // CloudsController














