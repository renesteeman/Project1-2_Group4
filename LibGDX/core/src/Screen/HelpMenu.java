package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class HelpMenu implements Screen {

    GameUI helpMenuHelp;
    Texture helpMenuHelpButton;
    Texture helpMenuBackButton;
    Texture helpMenuOkButton;
    Texture helpMenuLine;
    Texture helpMenuLeftButton;
    Texture helpMenuRightButton;
    Texture helpMenuPlusButton;
    Texture helpMenuMinusButton;
    Texture helpMenuShootButton;
    BitmapFont helpMenuFont;
    BitmapFont helpMenuLeftRule;
    BitmapFont helpMenuRightRule;
    BitmapFont helpMenuPlusRule;
    BitmapFont helpMenuMinusRule;
    BitmapFont helpMenuShootRule;

    private static final int helpMenu_HELP_BUTTON_SIZE = 100;
    private static final int helpMenu_BACK_BUTTON_SIZE = 80;
    private static final int helpMenu_OK_BUTTON_WIDTH = 90;
    private static final int helpMenu_OK_BUTTON_HEIGHT = 90;
    private static final int helpMenu_LEFT_BUTTON_WIDTH = 70;
    private static final int helpMenu_LEFT_BUTTON_HEIGHT = 70;
    private static final int helpMenu_RIGHT_BUTTON_WIDTH = 70;
    private static final int helpMenu_RIGHT_BUTTON_HEIGHT = 70;
    private static final int helpMenu_PLUS_BUTTON_SIZE = 70;
    private static final int helpMenu_MINUS_BUTTON_SIZE = 70;
    private static final int helpMenu_SHOOT_BUTTON_SIZE = 70;

    public HelpMenu(GameUI start){
        this.helpMenuHelp = start;
        helpMenuHelpButton = new Texture(("help.png"));
        helpMenuBackButton = new Texture("back.png");
        helpMenuOkButton = new Texture("ok.png");
        helpMenuLeftButton = new Texture(("left.png"));
        helpMenuRightButton = new Texture("right.png");
        helpMenuPlusButton = new Texture("plus.png");
        helpMenuMinusButton = new Texture(("minus.png"));
        helpMenuShootButton = new Texture("shoot.png");
        helpMenuLine = new Texture("line.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        helpMenuHelp.gameUIBatch.begin();

        FreeTypeFontGenerator helpMenuGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter helpMenuParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        helpMenuParameter.size = 50;
        helpMenuParameter.characters = "Command";
        helpMenuFont = helpMenuGenerator.generateFont(helpMenuParameter);
        helpMenuFont.setColor(Color.FOREST);
        helpMenuGenerator.dispose();
        helpMenuFont.draw(helpMenuHelp.gameUIBatch, "Command", GameUI.gameUI_WINDOW_WIDTH-750, GameUI.gameUI_WINDOW_HEIGHT-100);

        if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH-150 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH-150) + helpMenu_OK_BUTTON_WIDTH && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < helpMenu_OK_BUTTON_HEIGHT + GameUI.gameUI_WINDOW_HEIGHT/10 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT/10 ){
            helpMenuHelp.gameUIBatch.draw(helpMenuLine, GameUI.gameUI_WINDOW_WIDTH-150, (GameUI.gameUI_WINDOW_HEIGHT/10)-60, helpMenu_OK_BUTTON_WIDTH, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                helpMenuHelp.setScreen(new MainMenuScreen(helpMenuHelp));
            }
        }
        if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/10 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/10) + helpMenu_BACK_BUTTON_SIZE && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < helpMenu_BACK_BUTTON_SIZE + GameUI.gameUI_WINDOW_HEIGHT-140 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-140 ){
            helpMenuHelp.gameUIBatch.draw(helpMenuLine, (GameUI.gameUI_WINDOW_WIDTH)/10, (GameUI.gameUI_WINDOW_HEIGHT-220), helpMenu_BACK_BUTTON_SIZE, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                helpMenuHelp.setScreen(new MainMenuScreen(helpMenuHelp));
            }
        }

        helpMenuHelp.gameUIBatch.draw(helpMenuHelpButton, GameUI.gameUI_WINDOW_WIDTH-200, GameUI.gameUI_WINDOW_HEIGHT - 150, helpMenu_HELP_BUTTON_SIZE, helpMenu_HELP_BUTTON_SIZE);
        helpMenuHelp.gameUIBatch.draw(helpMenuOkButton, GameUI.gameUI_WINDOW_WIDTH-150, GameUI.gameUI_WINDOW_HEIGHT/10, helpMenu_OK_BUTTON_WIDTH, helpMenu_OK_BUTTON_HEIGHT);
        helpMenuHelp.gameUIBatch.draw(helpMenuBackButton, GameUI.gameUI_WINDOW_WIDTH/10, GameUI.gameUI_WINDOW_HEIGHT-150, helpMenu_BACK_BUTTON_SIZE, helpMenu_BACK_BUTTON_SIZE);
        helpMenuHelp.gameUIBatch.draw(helpMenuLeftButton, GameUI.gameUI_WINDOW_WIDTH/4, GameUI.gameUI_WINDOW_HEIGHT-300, helpMenu_LEFT_BUTTON_WIDTH, helpMenu_LEFT_BUTTON_HEIGHT);
        helpMenuHelp.gameUIBatch.draw(helpMenuRightButton, GameUI.gameUI_WINDOW_WIDTH/4, GameUI.gameUI_WINDOW_HEIGHT - 400, helpMenu_RIGHT_BUTTON_WIDTH, helpMenu_RIGHT_BUTTON_HEIGHT);
        helpMenuHelp.gameUIBatch.draw(helpMenuPlusButton, GameUI.gameUI_WINDOW_WIDTH/4, GameUI.gameUI_WINDOW_HEIGHT-500, helpMenu_PLUS_BUTTON_SIZE, helpMenu_PLUS_BUTTON_SIZE);
        helpMenuHelp.gameUIBatch.draw(helpMenuMinusButton, GameUI.gameUI_WINDOW_WIDTH/4, GameUI.gameUI_WINDOW_HEIGHT-600, helpMenu_MINUS_BUTTON_SIZE, helpMenu_MINUS_BUTTON_SIZE);
        helpMenuHelp.gameUIBatch.draw(helpMenuShootButton, GameUI.gameUI_WINDOW_WIDTH/4, GameUI.gameUI_WINDOW_HEIGHT-700, helpMenu_SHOOT_BUTTON_SIZE, helpMenu_SHOOT_BUTTON_SIZE);

        //write the different rules
        FreeTypeFontGenerator helpMenuWritingStyle = new FreeTypeFontGenerator(Gdx.files.internal("Courier New.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter helpMenuLeftR = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter helpMenuRightR = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter helpMenuPlusR = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter helpMenuMinusR = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter helpMenuShootR = new FreeTypeFontGenerator.FreeTypeFontParameter();

        helpMenuLeftR.size = 30;
        helpMenuRightR.size = 30;
        helpMenuMinusR.size = 30;
        helpMenuPlusR.size = 30;
        helpMenuShootR.size = 30;

        helpMenuLeftR.characters = "Change direction to the left";
        helpMenuRightR.characters = "Change direction to the right";
        helpMenuPlusR.characters = "Increases the power of the shoot";
        helpMenuMinusR.characters = "Decreases the power of the shoot";
        helpMenuShootR.characters = "Shoot the ball";

        helpMenuLeftRule = helpMenuWritingStyle.generateFont(helpMenuLeftR);
        helpMenuRightRule = helpMenuWritingStyle.generateFont(helpMenuRightR);
        helpMenuPlusRule = helpMenuWritingStyle.generateFont(helpMenuPlusR);
        helpMenuMinusRule = helpMenuWritingStyle.generateFont(helpMenuMinusR);
        helpMenuShootRule = helpMenuWritingStyle.generateFont(helpMenuShootR);

        helpMenuLeftRule.setColor(Color.FOREST);
        helpMenuRightRule.setColor(Color.FOREST);
        helpMenuPlusRule.setColor(Color.FOREST);
        helpMenuMinusRule.setColor(Color.FOREST);
        helpMenuShootRule.setColor(Color.FOREST);

        helpMenuWritingStyle.dispose();
        helpMenuLeftRule.draw(helpMenuHelp.gameUIBatch, "Change direction to the left", GameUI.gameUI_WINDOW_WIDTH/3, GameUI.gameUI_WINDOW_HEIGHT-250);
        helpMenuRightRule.draw(helpMenuHelp.gameUIBatch, "Change direction to the right", GameUI.gameUI_WINDOW_WIDTH/3, GameUI.gameUI_WINDOW_HEIGHT-350);
        helpMenuPlusRule.draw(helpMenuHelp.gameUIBatch, "Increases the power of the shoot", GameUI.gameUI_WINDOW_WIDTH/3, GameUI.gameUI_WINDOW_HEIGHT-450);
        helpMenuMinusRule.draw(helpMenuHelp.gameUIBatch, "Decreases the power of the shoot", GameUI.gameUI_WINDOW_WIDTH/3, GameUI.gameUI_WINDOW_HEIGHT-550);
        helpMenuShootRule.draw(helpMenuHelp.gameUIBatch, "Shoot the ball", GameUI.gameUI_WINDOW_WIDTH/3, GameUI.gameUI_WINDOW_HEIGHT-650);

        helpMenuHelp.gameUIBatch.end();
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
