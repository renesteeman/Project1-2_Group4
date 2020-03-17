package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;


public class CustomizedMenu implements Screen, Input.TextInputListener {

    GameUI game;
    Texture backButton;
    Texture line;
    Texture okButton;
    BitmapFont font;
    BitmapFont velocity;
    BitmapFont acceleration;
    BitmapFont coefficient;
    BitmapFont distance;
    BitmapFont mass;
    BitmapFont height;
    String text;
    TextField field;

    private static final int BACK_BUTTON_SIZE = 80;
    private static final int OK_BUTTON_WIDTH = 90;
    private static final int OK_BUTTON_HEIGHT = 90;

    public CustomizedMenu(GameUI game){
        this.game = game;
        backButton = new Texture("back.png");
        line = new Texture("line.png");
        okButton = new Texture("ok.png");
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

        //Different texts
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

        parameter.characters = "Customized Menu";
        velo.characters = "Initial Velocity: ";
        acc.characters = "Acceleration: ";
        mu.characters = "Coefficient of friction: ";
        dist.characters = "Distance from the Hole: ";
        weight.characters = "Mass of the ball: ";
        hauteur.characters = "Equation of the height: ";

        font = generator.generateFont(parameter);
        velocity = writingStyle.generateFont(velo);
        acceleration = writingStyle.generateFont(acc);
        coefficient = writingStyle.generateFont(mu);
        distance = writingStyle.generateFont(dist);
        mass = writingStyle.generateFont(weight);
        height = writingStyle.generateFont(hauteur);

        font.setColor(Color.FOREST);
        velocity.setColor(Color.FOREST);
        acceleration.setColor(Color.FOREST);
        coefficient.setColor(Color.FOREST);
        distance.setColor(Color.FOREST);
        mass.setColor(Color.FOREST);
        height.setColor(Color.FOREST);

        generator.dispose();

        font.draw(game.batch, "Customized Menu", GameUI.WINDOW_WIDTH/4, GameUI.WINDOW_HEIGHT-100);
        velocity.draw(game.batch, "Initial Velocity: ", GameUI.WINDOW_WIDTH/8, GameUI.WINDOW_HEIGHT-300);
        acceleration.draw(game.batch, "Acceleration: ", GameUI.WINDOW_WIDTH/8, GameUI.WINDOW_HEIGHT-350);
        coefficient.draw(game.batch, "Coefficient of friction: ", GameUI.WINDOW_WIDTH/8, GameUI.WINDOW_HEIGHT-400);
        distance.draw(game.batch, "Distance from the Hole: ", GameUI.WINDOW_WIDTH/8, GameUI.WINDOW_HEIGHT-450);
        mass.draw(game.batch, "Mass of the ball: ", GameUI.WINDOW_WIDTH/8, GameUI.WINDOW_HEIGHT-500);
        height.draw(game.batch, "Equation of the height: ", GameUI.WINDOW_WIDTH/8, GameUI.WINDOW_HEIGHT-550);


        //Drawing the line under the Back Button
        if(Gdx.input.getX() > GameUI.WINDOW_WIDTH/10 && Gdx.input.getX() < (GameUI.WINDOW_WIDTH/10) + BACK_BUTTON_SIZE && GameUI.WINDOW_HEIGHT - Gdx.input.getY() < BACK_BUTTON_SIZE + GameUI.WINDOW_HEIGHT-140 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() > GameUI.WINDOW_HEIGHT-140 ){
            game.batch.draw(line, (GameUI.WINDOW_WIDTH)/10, (GameUI.WINDOW_HEIGHT-220), BACK_BUTTON_SIZE, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                game.setScreen(new StartMenu(game));
            }
        }

        //Drawing the line under the Ok button
        if(Gdx.input.getX() > GameUI.WINDOW_WIDTH-150 && Gdx.input.getX() < (GameUI.WINDOW_WIDTH-150) + OK_BUTTON_WIDTH && GameUI.WINDOW_HEIGHT - Gdx.input.getY() < OK_BUTTON_HEIGHT + GameUI.WINDOW_HEIGHT/10 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() > GameUI.WINDOW_HEIGHT/10 ){
            game.batch.draw(line, GameUI.WINDOW_WIDTH-150, (GameUI.WINDOW_HEIGHT/10)-60, OK_BUTTON_WIDTH, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                game.setScreen(new GamePlay(game));
            }
        }
        //Drawing the different buttons
        game.batch.draw(backButton, GameUI.WINDOW_WIDTH/10, GameUI.WINDOW_HEIGHT-150, BACK_BUTTON_SIZE, BACK_BUTTON_SIZE);
        game.batch.draw(okButton, GameUI.WINDOW_WIDTH-150, GameUI.WINDOW_HEIGHT/10, OK_BUTTON_WIDTH, OK_BUTTON_HEIGHT);


        //Drawing the line under the different texts
        //Velocity
        if(Gdx.input.getX() > GameUI.WINDOW_WIDTH/8 && Gdx.input.getX() < (GameUI.WINDOW_WIDTH/8) + 200 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.WINDOW_HEIGHT-300 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() > GameUI.WINDOW_HEIGHT-330 ){
            game.batch.draw(line, GameUI.WINDOW_WIDTH/8, (GameUI.WINDOW_HEIGHT)-380, 200, 150);
            if(Gdx.input.justTouched()){
                //Gdx.input.getTextInput(this, "Velocity", "", "Enter the initial velocity");
            }
        }
        //Acceleration
        if(Gdx.input.getX() > GameUI.WINDOW_WIDTH/8 && Gdx.input.getX() < (GameUI.WINDOW_WIDTH/8) + 200 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.WINDOW_HEIGHT-350 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() > GameUI.WINDOW_HEIGHT-380 ){
            game.batch.draw(line, GameUI.WINDOW_WIDTH/8, (GameUI.WINDOW_HEIGHT)-430, 150, 150);
            if(Gdx.input.justTouched()){
               // Gdx.input.getTextInput(this, "Acceleration", "", "Enter the acceleration");
            }
        }
        //Mu
        if(Gdx.input.getX() > GameUI.WINDOW_WIDTH/8 && Gdx.input.getX() < (GameUI.WINDOW_WIDTH/8) + 200 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.WINDOW_HEIGHT-400 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() > GameUI.WINDOW_HEIGHT-430 ){
            game.batch.draw(line, GameUI.WINDOW_WIDTH/8, (GameUI.WINDOW_HEIGHT)-480, 290, 150);
            if(Gdx.input.justTouched()){
                //Gdx.input.getTextInput(this, "Coefficient of Friction", "", "Enter the coefficient of friction");
            }
        }
        //Distance
        if(Gdx.input.getX() > GameUI.WINDOW_WIDTH/8 && Gdx.input.getX() < (GameUI.WINDOW_WIDTH/8) + 200 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.WINDOW_HEIGHT-450 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() > GameUI.WINDOW_HEIGHT-480 ){
            game.batch.draw(line, GameUI.WINDOW_WIDTH/8, (GameUI.WINDOW_HEIGHT)-530, 290, 150);
            if(Gdx.input.justTouched()){
               // Gdx.input.getTextInput(this, "Distance", "", "Distance from hole for a successful putt");
            }
        }
        //Mass
        if(Gdx.input.getX() > GameUI.WINDOW_WIDTH/8 && Gdx.input.getX() < (GameUI.WINDOW_WIDTH/8) + 200 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.WINDOW_HEIGHT-500 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() > GameUI.WINDOW_HEIGHT-530 ){
            game.batch.draw(line, GameUI.WINDOW_WIDTH/8, (GameUI.WINDOW_HEIGHT)-580, 200, 150);
            if(Gdx.input.justTouched()){
                //Gdx.input.getTextInput(this, "Mass of the ball", "", "Enter the mass of the ball");
            }
        }

        //Answers of the fields
        /*Stage stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        field = new TextField("" , skin);
        field.setPosition(500, 500);
        field.setSize(20, 20);

        stage.addActor(field);`
         */

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

    @Override
    public void input(String text) {
        this.text = text;
    }

    @Override
    public void canceled() {
        text = "Default Value";
    }
}
