package actions;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.ghevi.jackthegiant.GameMain;

import huds.UIHud;
import scenes.Gameplay;
import scenes.MainMenu;

public class DelayAndFadeAction {

    private GameMain game;
    private Stage stage;
    private UIHud hud;

    private RunnableAction run;

    public DelayAndFadeAction(GameMain game){
        this.game = game;
        run = new RunnableAction();
    }

    public DelayAndFadeAction(GameMain game, Stage stage){
        this.game = game;
        this.stage = stage;
        run = new RunnableAction();
    }

    public DelayAndFadeAction(GameMain game, UIHud hud){
        this.game = game;
        this.hud = hud;
        run = new RunnableAction();
    }

    public void createCustomAction(String scene){
        if(scene.equals("Gameplay")){
            createCustomActionOnGameplay();
        }
        else if(scene.equals("MainMenu")){
            createCustomActionOnMainMenu();
        }
    }

    private void createCustomActionOnGameplay(){
        run.setRunnable(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new Gameplay(game));
            }
        });
    }

    private void createCustomActionOnMainMenu(){
        run.setRunnable(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new MainMenu(game));
            }
        });
    }

    public void addActionsSequenceToStage(float delay){
        SequenceAction sa = new SequenceAction();
        sa.addAction(Actions.delay(delay)); // Delay the main menu
        sa.addAction(Actions.fadeOut(delay)); // Fade out
        sa.addAction(run); // execute the runnable action ( loading main menu or restarting the game)

        stage.addAction(sa);
    }

    public void addActionsSequenceToHud(float delay){
        SequenceAction sa = new SequenceAction();
        sa.addAction(Actions.delay(delay)); // Delay the main menu
        sa.addAction(Actions.fadeOut(delay)); // Fade out
        sa.addAction(run); // execute the runnable action ( loading main menu or restarting the game)

        hud.getStage().addAction(sa);
    }

} // delay and fade action








