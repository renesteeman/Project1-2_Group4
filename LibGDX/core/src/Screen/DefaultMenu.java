package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class DefaultMenu implements Screen {

    GameUI DefaultGame;
    Texture DefaultBackButton;
    Texture DefaultLine;
    Texture DefaultOkButton;
    BitmapFont DefaultFont;
    BitmapFont DefaultVelocity;
    BitmapFont DefaultAcceleration;
    BitmapFont DefaultCoefficient;
    BitmapFont DefaultDistance;
    BitmapFont DefaultMass;
    BitmapFont DefaultHeight;
    BitmapFont DefaultVelocity1;
    BitmapFont DefaultAcceleration1;
    BitmapFont DefaultCoefficient1;
    BitmapFont DefaultDistance1;
    BitmapFont DefaultMass1;
    BitmapFont DefaultHeight1;

    private static final int DefaultMenuBACK_BUTTON_SIZE = 80;
    private static final int DefaultMenuOK_BUTTON_WIDTH = 90;
    private static final int DefaultMenuOK_BUTTON_HEIGHT = 90;

    public DefaultMenu(GameUI game){
        this.game = game;
        backButton = new Texture("back.png");
        line = new Texture("line.png");
        DefaultOkButton =new Texture(("ok.png"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));
        FreeTypeFontGenerator writingStyle = new FreeTypeFontGenerator(Gdx.files.internal("Courier New.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter velo = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter acc = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter mu = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter dist = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter weight = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter hauteur = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 60;
        velo.size = 20;
        acc.size = 20;
        mu.size = 20;
        dist.size = 20;
        weight.size = 20;
        hauteur.size = 20;

        parameter.characters = "Default Menu";
        velo.characters = "Initial Velocity: ";
        acc.characters = "Acceleration: ";
        mu.characters = "Coefficient of friction: ";
        dist.characters = "Distance from the Hole: ";
        weight.characters = "Mass of the ball: ";
        hauteur.characters = "Equation of the height: ";

        DefaultFont = generator.generateFont(parameter);
        DefaultVelocity = writingStyle.generateFont(velo);
        DefaultAcceleration = writingStyle.generateFont(acc);
        DefaultCoefficient = writingStyle.generateFont(mu);
        DefaultDistance = writingStyle.generateFont(dist);
        DefaultMass = writingStyle.generateFont(weight);
        DefaultHeight = writingStyle.generateFont(hauteur);

        DefaultFont.setColor(Color.FOREST);
        DefaultVelocity.setColor(Color.FOREST);
        DefaultAcceleration.setColor(Color.FOREST);
        DefaultCoefficient.setColor(Color.FOREST);
        DefaultDistance.setColor(Color.FOREST);
        DefaultMass.setColor(Color.FOREST);
        DefaultHeight.setColor(Color.FOREST);

        generator.dispose();

        DefaultFont.draw(game.batch, "Default Menu", GameUI.WINDOW_WIDTH/4, GameUI.WINDOW_HEIGHT-100);
        DefaultVelocity.draw(game.batch, "Initial Velocity: ", GameUI.WINDOW_WIDTH/8, GameUI.WINDOW_HEIGHT-300);
        DefaultAcceleration.draw(game.batch, "Acceleration: ", GameUI.WINDOW_WIDTH/8, GameUI.WINDOW_HEIGHT-350);
        DefaultCoefficient.draw(game.batch, "Coefficient of friction: ", GameUI.WINDOW_WIDTH/8, GameUI.WINDOW_HEIGHT-400);
        DefaultDistance.draw(game.batch, "Distance from the Hole: ", GameUI.WINDOW_WIDTH/8, GameUI.WINDOW_HEIGHT-450);
        DefaultMass.draw(game.batch, "Mass of the ball: ", GameUI.WINDOW_WIDTH/8, GameUI.WINDOW_HEIGHT-500);
        DefaultHeight.draw(game.batch, "Equation of the height: ", GameUI.WINDOW_WIDTH/8, GameUI.WINDOW_HEIGHT-550);


        //Default Input
        FreeTypeFontGenerator.FreeTypeFontParameter velo1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter acc1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter mu1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter dist1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter weight1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter hauteur1 = new FreeTypeFontGenerator.FreeTypeFontParameter();

        velo1.size = 20;
        acc1.size = 20;
        mu1.size = 20;
        dist1.size = 20;
        weight1.size = 20;
        hauteur1.size = 20;

        velo1.characters = "0 m/s";
        acc1.characters = "9.81 m/s^2";
        mu1.characters = "0.131";
        dist1.characters = "0.02 m";
        weight1.characters = "45.93 g";
        hauteur1.characters = "-0.01*x + 0.003*x^2 + 0.04 * y";

        DefaultVelocity1 = writingStyle.generateFont(velo1);
        DefaultAcceleration1 = writingStyle.generateFont(acc1);
        DefaultCoefficient1 = writingStyle.generateFont(mu1);
        DefaultDistance1 = writingStyle.generateFont(dist1);
        DefaultMass1 = writingStyle.generateFont(weight1);
        DefaultHeight1 = writingStyle.generateFont(hauteur1);

        DefaultVelocity1.setColor(Color.FOREST);
        DefaultAcceleration1.setColor(Color.FOREST);
        DefaultCoefficient1.setColor(Color.FOREST);
        DefaultDistance1.setColor(Color.FOREST);
        DefaultMass1.setColor(Color.FOREST);
        DefaultHeight1.setColor(Color.FOREST);

        DefaultVelocity1.draw(game.batch, "0 m/s", GameUI.WINDOW_WIDTH/2, GameUI.WINDOW_HEIGHT-300);
        DefaultAcceleration1.draw(game.batch, "9.81 m/s^2", GameUI.WINDOW_WIDTH/2, GameUI.WINDOW_HEIGHT-350);
        DefaultCoefficient1.draw(game.batch, "0.131", (GameUI.WINDOW_WIDTH/2) + 50, GameUI.WINDOW_HEIGHT-400);
        DefaultDistance1.draw(game.batch, "0.02 m", (GameUI.WINDOW_WIDTH/2) + 50, GameUI.WINDOW_HEIGHT-450);
        DefaultMass1.draw(game.batch, "45.93 g", (GameUI.WINDOW_WIDTH/2) + 50, GameUI.WINDOW_HEIGHT-500);
        DefaultHeight1.draw(game.batch, "-0.01*x + 0.003*x^2 + 0.04 * y", (GameUI.WINDOW_WIDTH/2), GameUI.WINDOW_HEIGHT-550);

        //Draw the line under the Back Button
        if(Gdx.input.getX() > GameUI.WINDOW_WIDTH/10 && Gdx.input.getX() < (GameUI.WINDOW_WIDTH/10) + BACK_BUTTON_SIZE && GameUI.WINDOW_HEIGHT - Gdx.input.getY() < BACK_BUTTON_SIZE + GameUI.WINDOW_HEIGHT-140 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() > GameUI.WINDOW_HEIGHT-140 ){
            game.batch.draw(line, (GameUI.WINDOW_WIDTH)/10, (GameUI.WINDOW_HEIGHT-220), BACK_BUTTON_SIZE, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                game.setScreen(new StartMenu(game));
            }
        }
        //Draw the line under the Ok button
        if(Gdx.input.getX() > GameUI.WINDOW_WIDTH-150 && Gdx.input.getX() < (GameUI.WINDOW_WIDTH-150) + OK_BUTTON_WIDTH && GameUI.WINDOW_HEIGHT - Gdx.input.getY() < OK_BUTTON_HEIGHT + GameUI.WINDOW_HEIGHT/10 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() > GameUI.WINDOW_HEIGHT/10 ){
            game.batch.draw(line, GameUI.WINDOW_WIDTH-150, (GameUI.WINDOW_HEIGHT/10)-60, OK_BUTTON_WIDTH, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                game.setScreen(new GamePlay(game));
            }
        }

        //Draw the buttons
        game.batch.draw(backButton, GameUI.WINDOW_WIDTH/10, GameUI.WINDOW_HEIGHT-150, BACK_BUTTON_SIZE, BACK_BUTTON_SIZE);
        game.batch.draw(DefaultOkButton, GameUI.WINDOW_WIDTH-150, GameUI.WINDOW_HEIGHT/10, OK_BUTTON_WIDTH, OK_BUTTON_HEIGHT);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
