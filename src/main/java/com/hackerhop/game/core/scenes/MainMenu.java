package com.hackerhop.game.core.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hackerhop.game.core.leaderboards.Leaderboards;
import com.hackerhop.game.core.objects.ui.Button;
import com.hackerhop.game.core.player.Character;
import com.hackerhop.game.core.MainController;
import com.hackerhop.game.core.utils.Options;
import com.hackerhop.game.core.utils.blinkers.SpriteBlinker;
import com.hackerhop.game.core.utils.toggleable.ToggleableSprite;
import org.jbox2d.common.Vec2;


import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static com.hackerhop.game.core.utils.GDXUtils.getMousePosition;

public class MainMenu extends Scene {

	private static final String TAG = MainMenu.class.getName();

	private static final float CHARACTER_ZOOM = 1.2f;

	// Main Screen Textures
	private Texture logo;
	private Texture background;

	private ToggleableSprite rob;
	private ToggleableSprite nick;
	private ToggleableSprite kate;
	private ToggleableSprite ye;

	private Button leaderboardsButton;
	private Button gitHubButton;

	private Sprite textDisplay;
	private SpriteBlinker blinker;

	private ToggleableSprite soundButton;
	private Music music;

	public MainMenu(MainController controller) {
		super(controller);
	}

	@Override
	public void update() {
		blinker.update();
	}

	@Override
	public void loadResources() {
		logo = new Texture("mainMenuScene/Logo.png");
		background = new Texture("background/ShinemanPixel.png");

		final Sprite robSprite = new Sprite(new Texture("player/rob.png"));
		robSprite.setPosition(100, 75);
		this.rob = new ToggleableSprite(
				() -> robSprite.getBoundingRectangle().contains(getMousePosition()),
				upscale(robSprite, CHARACTER_ZOOM),
				robSprite

		);

		final Sprite nickSprite = new Sprite(new Texture("player/Nick.png"));
		nickSprite.setPosition(200, 75);
		this.nick = new ToggleableSprite(
				() -> nickSprite.getBoundingRectangle().contains(getMousePosition()),
				upscale(nickSprite, CHARACTER_ZOOM),
				nickSprite

		);

		final Sprite kateSprite = new Sprite(new Texture("player/Katie.png"));
		kateSprite.setPosition(300, 75);
		this.kate = new ToggleableSprite(
				() -> kateSprite.getBoundingRectangle().contains(getMousePosition()),
				upscale(kateSprite, CHARACTER_ZOOM),
				kateSprite

		);

		final Sprite yeSprite = new Sprite(new Texture("player/Ye.png"));
		yeSprite.setPosition(400, 75);
		this.ye = new ToggleableSprite(
				() -> yeSprite.getBoundingRectangle().contains(getMousePosition()),
				upscale(yeSprite, CHARACTER_ZOOM),
				yeSprite

		);

		this.leaderboardsButton = new Button(
				"mainMenuScene/HighScoreButton.png",
				"mainMenuScene/HighScoreButtonHover.png",
				new Vec2(75, 325)
		);

		this.gitHubButton = new Button(
				"mainMenuScene/GitHubButton.png",
				"mainMenuScene/GitHubButtonHover.png",
				new Vec2(285, 325)
		);

		blinker = new SpriteBlinker(new Sprite(new Texture("mainMenuScene/Arrow.png")), 1f, .5f);
		textDisplay = new Sprite(new Texture("mainMenuScene/textDisplay.png"));

		this.soundButton = new ToggleableSprite(
				Options::sounds,
				new Sprite(new Texture("mainMenuScene/soundButton.png")),
				new Sprite(new Texture("mainMenuScene/soundButtonOff.png"))
		);

		music = Gdx.audio.newMusic(Gdx.files.internal("audio/waves.mp3"));
		music.setLooping(true);
		music.setVolume(Options.sounds() ? 1f : 0f);
		music.play();
	}

	@Override
	public void render(SpriteBatch batch) {

		Gdx.gl.glClearColor(1, .5f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Draw next frame (current scene)
		batch.begin();

		batch.draw(background, 0, 0);
		batch.draw(logo, 0, 75);

		rob.render(batch);
		nick.render(batch);
		kate.render(batch);
		ye.render(batch);

		gitHubButton.render(batch);
		leaderboardsButton.render(batch);
		soundButton.render(batch);

		blinker.render(batch);
		textDisplay.draw(batch);

		batch.end();
	}

	@Override
	public boolean keyDown(int i) {
		return false;
	}

	@Override
	public boolean keyUp(int i) {
		return false;
	}

	@Override
	public boolean keyTyped(char c) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		int y = Gdx.graphics.getHeight() - screenY;
		MainController controller = super.getController();

		if (soundButton.getBoundingRectangle().contains(screenX, y)) {
			try {
				Options.toggleSounds();
			} catch (IOException ignored) {

			}

			music.setVolume(Options.sounds() ? 1f : 0f);
		}

		if (rob.getBoundingRectangle().contains(screenX, y)) {
			controller.setScene(new HelpScene(controller, Character.ROB));

		} else if (nick.getBoundingRectangle().contains(screenX, y)) {
			controller.setScene(new HelpScene(controller, Character.NICK));

		} else if (kate.getBoundingRectangle().contains(screenX, y)) {
			controller.setScene(new HelpScene(controller, Character.KATIE));

		} else if (ye.getBoundingRectangle().contains(screenX, y)) {
			controller.setScene(new HelpScene(controller, Character.YE));

		} else if (gitHubButton.getBoundingRectangle().contains(screenX, y)) {
			try {
				openWebpage(new URL("https://github.com/nicovank/HackerHop"));
			} catch (MalformedURLException ignored) {

			}
		} else if (leaderboardsButton.getBoundingRectangle().contains(screenX, y)) {
			try {
				openWebpage(new URL(Leaderboards.LIST_URL));
			} catch (MalformedURLException ignored) {

			}
		}

		return true;
	}

	@Override
	public boolean touchUp(int i, int i1, int i2, int i3) {
		return false;
	}

	@Override
	public boolean touchDragged(int i, int i1, int i2) {
		return false;
	}

	@Override
	public boolean mouseMoved(int i, int i1) {
		return false;
	}

	@Override
	public boolean scrolled(int i) {
		return false;
	}

	@Override
	public void dispose() {
		logo.dispose();
		background.dispose();

		rob.dispose();
		nick.dispose();
		kate.dispose();
		ye.dispose();

		leaderboardsButton.dispose();
		gitHubButton.dispose();

		blinker.dispose();

		textDisplay.getTexture().dispose();

		soundButton.dispose();

		music.stop();
		music.dispose();
	}

	private static boolean openWebpage(URI uri) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(uri);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	private static boolean openWebpage(URL url) {
		try {
			return openWebpage(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static Sprite upscale(Sprite original, float scale) {
		Sprite upscaled = new Sprite(original.getTexture());
		upscaled.setPosition(original.getX(), original.getY());
		upscaled.setScale(scale);
		return upscaled;
	}
}
